const tableBtn = document.getElementById("tableBtn");
const chartBtn = document.getElementById("chartBtn");
const tableView = document.getElementById("tableView");
const chartView = document.getElementById("chartView");
const resultsTable = document.getElementById("resultsTable");
const errorBox = document.getElementById("errorBox");

const adminOpenBtn = document.getElementById("adminOpenBtn");
const adminCloseBtn = document.getElementById("adminCloseBtn");
const adminPanel = document.getElementById("adminPanel");
const loginView = document.getElementById("loginView");
const adminView = document.getElementById("adminView");
const loginForm = document.getElementById("loginForm");
const loginInput = document.getElementById("loginInput");
const passwordInput = document.getElementById("passwordInput");
const categoryForm = document.getElementById("categoryForm");
const categoryNameInput = document.getElementById("categoryNameInput");
const categoryList = document.getElementById("categoryList");
const schoolForm = document.getElementById("schoolForm");
const schoolNameInput = document.getElementById("schoolNameInput");
const schoolList = document.getElementById("schoolList");
const adminScoresTable = document.getElementById("adminScoresTable");
const adminMessage = document.getElementById("adminMessage");
const adminStatus = document.getElementById("adminStatus");
const refreshAdminBtn = document.getElementById("refreshAdminBtn");
const saveScoresBtn = document.getElementById("saveScoresBtn");
const logoutBtn = document.getElementById("logoutBtn");

const CATEGORIES_URL = "/client/categories";
const SCHOOLS_URL = "/client/schools";
const SCORES_URL = "/client/scores";
const AUTH_LOGIN_URL = "/auth/login";
const AUTH_REFRESH_URL = "/auth/refresh";
const ADMIN_CATEGORIES_URL = "/admin/categories";
const ADMIN_SCHOOLS_URL = "/admin/schools";
const ADMIN_SCORES_URL = "/admin/scores";

const ACCESS_TOKEN_STORAGE_KEY = "regatyAdminAccessToken";
const REFRESH_TOKEN_STORAGE_KEY = "regatyAdminRefreshToken";
const TOKEN_REFRESH_INTERVAL_MS = 8 * 60 * 1000;

let chart = null;
let chartSignature = "";
let adminRefreshTimer = null;

const adminState = {
    accessToken: localStorage.getItem(ACCESS_TOKEN_STORAGE_KEY) || "",
    refreshToken: localStorage.getItem(REFRESH_TOKEN_STORAGE_KEY) || "",
    categories: [],
    schools: [],
    scores: [],
    dirtyScores: new Map()
};

function escapeHtml(value) {
    return String(value)
        .replaceAll("&", "&amp;")
        .replaceAll("<", "&lt;")
        .replaceAll(">", "&gt;")
        .replaceAll('"', "&quot;")
        .replaceAll("'", "&#039;");
}

function formatTime(ms) {
    if (ms === null || ms === undefined) return "-";

    const totalMs = Math.max(0, Math.round(Number(ms)));

    if (Number.isNaN(totalMs)) return "-";

    const minutes = Math.floor(totalMs / 60000);
    const seconds = Math.floor((totalMs % 60000) / 1000);
    const milliseconds = String(Math.floor((totalMs % 1000) / 10)).padStart(2, "0");

    return `${minutes} min ${seconds} s ${milliseconds} mil`;
}

const chartValueLabelPlugin = {
    id: "valueLabels",
    afterDatasetsDraw(chartInstance) {
        const { ctx, chartArea } = chartInstance;
        const dataset = chartInstance.data.datasets[0];
        const meta = chartInstance.getDatasetMeta(0);
        const mobile = isMobileViewport();

        if (!dataset || !meta) return;

        ctx.save();
        ctx.fillStyle = "#333";
        ctx.font = `${mobile ? 10 : 12}px Arial, sans-serif`;
        ctx.textBaseline = "middle";

        meta.data.forEach((bar, index) => {
            const label = formatTime(dataset.data[index]);
            const textWidth = ctx.measureText(label).width;
            const gap = mobile ? 4 : 8;
            const x = Math.min(bar.x + gap, chartArea.right - textWidth);

            ctx.fillText(label, Math.max(x, chartArea.left + 4), bar.y);
        });

        ctx.restore();
    }
};

function isMobileViewport() {
    return window.matchMedia("(max-width: 600px)").matches;
}

function getChartOptions() {
    const mobile = isMobileViewport();

    return {
        indexAxis: "y",
        animation: false,
        responsive: true,
        maintainAspectRatio: false,
        layout: {
            padding: {
                right: mobile ? 72 : 120
            }
        },
        plugins: {
            legend: {
                display: false
            },
            tooltip: {
                callbacks: {
                    label: context => formatTime(context.raw)
                }
            }
        },
        scales: {
            x: {
                display: false,
                beginAtZero: true,
                title: {
                    display: false,
                    text: "Czas"
                },
                ticks: {
                    display: false,
                    autoSkip: true,
                    maxTicksLimit: mobile ? 3 : 6,
                    callback: value => formatTime(value)
                }
            },
            y: {
                ticks: {
                    autoSkip: false,
                    font: {
                        size: mobile ? 11 : 12
                    }
                }
            }
        }
    };
}

function getRandomChartColors(count) {
    const startHue = Math.floor(Math.random() * 360);

    return Array.from({ length: count }, (_, index) => {
        const hue = Math.round((startHue + index * 137.508) % 360);

        return {
            background: `hsl(${hue}, 72%, 54%)`,
            border: `hsl(${hue}, 72%, 38%)`
        };
    });
}

function showError(message) {
    errorBox.textContent = message;
    errorBox.classList.remove("hidden");
}

function hideError() {
    errorBox.textContent = "";
    errorBox.classList.add("hidden");
}

function showAdminMessage(message, type = "info") {
    adminMessage.textContent = message;
    adminMessage.className = `message ${type}`;
}

function hideAdminMessage() {
    adminMessage.textContent = "";
    adminMessage.className = "message hidden";
}

function setAdminStatus(message) {
    adminStatus.textContent = message;
}

function setButtonLoading(button, loading, loadingText = "Pracuję...") {
    if (!button) return;

    if (loading) {
        button.dataset.originalText = button.textContent;
        button.textContent = loadingText;
        button.disabled = true;
        return;
    }

    button.textContent = button.dataset.originalText || button.textContent;
    button.disabled = false;
    delete button.dataset.originalText;
}

function persistTokens() {
    if (adminState.accessToken) {
        localStorage.setItem(ACCESS_TOKEN_STORAGE_KEY, adminState.accessToken);
    } else {
        localStorage.removeItem(ACCESS_TOKEN_STORAGE_KEY);
    }

    if (adminState.refreshToken) {
        localStorage.setItem(REFRESH_TOKEN_STORAGE_KEY, adminState.refreshToken);
    } else {
        localStorage.removeItem(REFRESH_TOKEN_STORAGE_KEY);
    }
}

function clearTokens() {
    adminState.accessToken = "";
    adminState.refreshToken = "";
    persistTokens();
}

async function parseResponse(response) {
    if (response.status === 204) return null;

    const text = await response.text();

    if (!text) return null;

    try {
        return JSON.parse(text);
    } catch {
        return text;
    }
}

function formatApiError(response, body, url) {
    if (body && typeof body === "object" && body.error) {
        return body.error;
    }

    if (typeof body === "string" && body.trim()) {
        return body;
    }

    return `HTTP ${response.status} dla ${url}`;
}

async function fetchJson(url) {
    const response = await fetch(url);
    const body = await parseResponse(response);

    if (!response.ok) {
        throw new Error(formatApiError(response, body, url));
    }

    return body;
}

async function requestJson(url, options = {}) {
    const method = options.method || "GET";
    const headers = {
        Accept: "application/json",
        ...(options.headers || {})
    };

    let body;

    if (options.body !== undefined) {
        headers["Content-Type"] = "application/json";
        body = JSON.stringify(options.body);
    }

    if (options.auth) {
        headers.Authorization = `Bearer ${adminState.accessToken}`;
    }

    let response = await fetch(url, { method, headers, body });

    if (response.status === 401 && options.auth && options.retryAuth !== false && adminState.refreshToken) {
        await refreshAccessToken(false);
        headers.Authorization = `Bearer ${adminState.accessToken}`;
        response = await fetch(url, { method, headers, body });
    }

    const responseBody = await parseResponse(response);

    if (!response.ok) {
        throw new Error(formatApiError(response, responseBody, url));
    }

    return responseBody;
}

function buildTableData(categories, schools, scores) {
    const schoolRows = schools.map(school => ({
        schoolId: school.id,
        schoolName: school.name,
        resultsByCategory: {},
        totalTimeMs: 0
    }));

    const schoolMap = new Map(
        schoolRows.map(school => [school.schoolId, school])
    );

    scores.forEach(score => {
        const school = schoolMap.get(score.schoolId);

        if (!school) return;

        school.resultsByCategory[score.categoryId] = score.timeMs;
        school.totalTimeMs += score.timeMs;
    });

    return schoolRows.sort((a, b) => a.totalTimeMs - b.totalTimeMs);
}

function renderResultsTable(categories, schoolsRanking) {
    if (schoolsRanking.length === 0) {
        resultsTable.innerHTML = `
            <tr>
                <td>Brak wyników do wyświetlenia.</td>
            </tr>
        `;
        return;
    }

    let html = `
        <thead>
            <tr>
                <th>Miejsce</th>
                <th>Szkoła</th>
    `;

    categories.forEach(category => {
        html += `<th>${escapeHtml(category.name)}</th>`;
    });

    html += `
                <th>Suma</th>
            </tr>
        </thead>
        <tbody>
    `;

    schoolsRanking.forEach((school, index) => {
        html += `
            <tr>
                <td>${index + 1}</td>
                <td class="school-name">${escapeHtml(school.schoolName)}</td>
        `;

        categories.forEach(category => {
            const time = school.resultsByCategory[category.id];

            html += `<td>${formatTime(time)}</td>`;
        });

        html += `
                <td class="total-time">${formatTime(school.totalTimeMs)}</td>
            </tr>
        `;
    });

    html += `</tbody>`;

    resultsTable.innerHTML = html;
}

function renderChart(schoolsRanking) {
    if (typeof Chart === "undefined") return;

    const ctx = document.getElementById("rankingChart");
    const labels = schoolsRanking.map(school => school.schoolName);
    const values = schoolsRanking.map(school => school.totalTimeMs);
    const colors = getRandomChartColors(values.length);
    const nextSignature = JSON.stringify({ labels, values });

    if (chart && chartSignature === nextSignature) {
        chart.options = getChartOptions();
        chart.update("none");
        return;
    }

    if (chart) {
        chart.data.labels = labels;
        chart.data.datasets[0].data = values;
        chart.data.datasets[0].backgroundColor = colors.map(color => color.background);
        chart.data.datasets[0].borderColor = colors.map(color => color.border);
        chart.options = getChartOptions();
        chartSignature = nextSignature;
        chart.update("none");
        return;
    }

    chart = new Chart(ctx, {
        type: "bar",
        data: {
            labels,
            datasets: [
                {
                    label: "Suma czasu",
                    data: values,
                    backgroundColor: colors.map(color => color.background),
                    borderColor: colors.map(color => color.border),
                    borderWidth: 1
                }
            ]
        },
        plugins: [chartValueLabelPlugin],
        options: getChartOptions()
    });

    chartSignature = nextSignature;
}

async function loadResults() {
    try {
        hideError();

        const [categories, schools, scores] = await Promise.all([
            fetchJson(CATEGORIES_URL),
            fetchJson(SCHOOLS_URL),
            fetchJson(SCORES_URL)
        ]);

        const schoolsRanking = buildTableData(categories, schools, scores);

        renderResultsTable(categories, schoolsRanking);
        renderChart(schoolsRanking);
    } catch (error) {
        console.error(error);
        showError("Błąd pobierania wyników.");
    }
}

function scoreKey(schoolId, categoryId) {
    return `${schoolId}:${categoryId}`;
}

function scoreByCell() {
    return new Map(
        adminState.scores.map(score => [scoreKey(score.schoolId, score.categoryId), score])
    );
}

function splitTimeMs(timeMs) {
    if (timeMs === null || timeMs === undefined) {
        return {
            minutes: "",
            seconds: "",
            milliseconds: ""
        };
    }

    return {
        minutes: Math.floor(timeMs / 60000),
        seconds: Math.floor((timeMs % 60000) / 1000),
        milliseconds: String(Math.floor((timeMs % 1000) / 10)).padStart(2, "0")
    };
}

function readTimeParts(group) {
    return {
        minutes: group.querySelector('[data-time-part="minutes"]').value.trim(),
        seconds: group.querySelector('[data-time-part="seconds"]').value.trim(),
        milliseconds: group.querySelector('[data-time-part="milliseconds"]').value.trim()
    };
}

function renderAdminLists() {
    categoryList.innerHTML = renderAdminListItems(
        adminState.categories,
        "Brak kategorii.",
        "category"
    );
    schoolList.innerHTML = renderAdminListItems(
        adminState.schools,
        "Brak szkół.",
        "school"
    );

    categoryList.querySelectorAll("[data-delete-category-id]").forEach(button => {
        button.addEventListener("click", () => deleteCategory(button.dataset.deleteCategoryId));
    });

    schoolList.querySelectorAll("[data-delete-school-id]").forEach(button => {
        button.addEventListener("click", () => deleteSchool(button.dataset.deleteSchoolId));
    });
}

function renderAdminListItems(items, emptyText, type) {
    if (items.length === 0) {
        return `<p class="list-empty">${emptyText}</p>`;
    }

    return `
        <ul>
            ${items.map(item => `
                <li>
                    <span>${escapeHtml(item.name)}</span>
                    <button
                        class="danger-button"
                        type="button"
                        data-delete-${type}-id="${escapeHtml(item.id)}"
                    >
                        Usuń
                    </button>
                </li>
            `).join("")}
        </ul>
    `;
}

function renderAdminScoresTable() {
    adminState.dirtyScores.clear();
    saveScoresBtn.classList.remove("attention");

    if (adminState.categories.length === 0 || adminState.schools.length === 0) {
        adminScoresTable.innerHTML = `
            <tbody>
                <tr>
                    <td class="empty-cell">Dodaj przynajmniej jedną szkołę i jedną kategorię, żeby utworzyć tabelę wyników.</td>
                </tr>
            </tbody>
        `;
        setAdminStatus("Brak pełnych danych do tabeli.");
        return;
    }

    const scores = scoreByCell();

    let html = `
        <thead>
            <tr>
                <th>Szkoła</th>
    `;

    adminState.categories.forEach(category => {
        html += `<th>${escapeHtml(category.name)}</th>`;
    });

    html += `
                <th>Suma</th>
            </tr>
        </thead>
        <tbody>
    `;

    adminState.schools.forEach(school => {
        let total = 0;

        html += `
            <tr>
                <td class="school-name">${escapeHtml(school.name)}</td>
        `;

        adminState.categories.forEach(category => {
            const score = scores.get(scoreKey(school.id, category.id));
            const time = splitTimeMs(score ? score.timeMs : null);

            if (score) total += score.timeMs;

            html += `
                <td class="time-cell">
                    <div
                        class="time-input-group"
                        data-school-id="${escapeHtml(school.id)}"
                        data-category-id="${escapeHtml(category.id)}"
                        data-score-id="${score ? escapeHtml(score.id) : ""}"
                    >
                        <label class="time-part">
                            <span>min</span>
                            <input
                                class="time-part-input"
                                type="number"
                                min="0"
                                step="1"
                                inputmode="numeric"
                                value="${time.minutes}"
                                data-time-part="minutes"
                                aria-label="Minuty: ${escapeHtml(school.name)}, ${escapeHtml(category.name)}"
                            >
                        </label>
                        <label class="time-part">
                            <span>s</span>
                            <input
                                class="time-part-input"
                                type="number"
                                min="0"
                                max="59"
                                step="1"
                                inputmode="numeric"
                                value="${time.seconds}"
                                data-time-part="seconds"
                                aria-label="Sekundy: ${escapeHtml(school.name)}, ${escapeHtml(category.name)}"
                            >
                        </label>
                        <label class="time-part">
                            <span>mil</span>
                            <input
                                class="time-part-input"
                                type="number"
                                min="0"
                                max="99"
                                step="1"
                                inputmode="numeric"
                                value="${time.milliseconds}"
                                data-time-part="milliseconds"
                                aria-label="Milisekundy: ${escapeHtml(school.name)}, ${escapeHtml(category.name)}"
                            >
                        </label>
                    </div>
                </td>
            `;
        });

        html += `
                <td class="total-time">${formatTime(total)}</td>
            </tr>
        `;
    });

    html += `</tbody>`;
    adminScoresTable.innerHTML = html;

    adminScoresTable.querySelectorAll(".time-part-input").forEach(input => {
        input.addEventListener("input", markScoreDirty);
    });

    setAdminStatus(`Tabela gotowa: ${adminState.schools.length} szkół, ${adminState.categories.length} kategorii.`);
}

function markScoreDirty(event) {
    const input = event.target;

    if (input.dataset.timePart === "milliseconds") {
        input.value = input.value.replace(/\D/g, "").slice(0, 2);
    }

    const group = input.closest(".time-input-group");

    if (!group) return;

    const schoolId = group.dataset.schoolId;
    const categoryId = group.dataset.categoryId;
    const key = scoreKey(schoolId, categoryId);
    const timeParts = readTimeParts(group);

    group.classList.add("dirty");
    saveScoresBtn.classList.add("attention");

    adminState.dirtyScores.set(key, {
        schoolId,
        categoryId,
        scoreId: group.dataset.scoreId || "",
        minutes: timeParts.minutes,
        seconds: timeParts.seconds,
        milliseconds: timeParts.milliseconds
    });

    const count = adminState.dirtyScores.size;
    setAdminStatus(`${count} ${count === 1 ? "niezapisana zmiana" : "niezapisane zmiany"}.`);
}

function validateScoreChanges() {
    const changes = [...adminState.dirtyScores.values()];

    changes.forEach(change => {
        const isEmpty = change.minutes === "" && change.seconds === "" && change.milliseconds === "";

        if (isEmpty) {
            change.isEmpty = true;
            return;
        }

        const minutes = parseTimePart(change.minutes, "minuty");
        const seconds = parseTimePart(change.seconds, "sekundy", 59);
        const milliseconds = parseTimePart(change.milliseconds, "milisekundy", 99);

        change.isEmpty = false;
        change.timeMs = minutes * 60000 + seconds * 1000 + milliseconds * 10;
    });

    return changes;
}

function parseTimePart(rawValue, label, maxValue) {
    if (rawValue === "") return 0;

    const value = Number(rawValue);

    if (!Number.isInteger(value) || value < 0) {
        throw new Error(`Pole "${label}" musi być liczbą całkowitą większą lub równą 0.`);
    }

    if (maxValue !== undefined && value > maxValue) {
        throw new Error(`Pole "${label}" może mieć maksymalnie ${maxValue}.`);
    }

    return value;
}

async function saveScoreChanges() {
    if (adminState.dirtyScores.size === 0) {
        showAdminMessage("Nie ma zmian do zapisania.");
        return;
    }

    let changes;

    try {
        changes = validateScoreChanges();
    } catch (error) {
        showAdminMessage(error.message, "error");
        return;
    }

    setButtonLoading(saveScoresBtn, true, "Zapisuję...");

    try {
        for (const change of changes) {
            if (change.isEmpty) {
                if (change.scoreId) {
                    await requestJson(`${ADMIN_SCORES_URL}/${change.scoreId}`, {
                        method: "DELETE",
                        auth: true
                    });
                }

                continue;
            }

            if (change.scoreId) {
                await requestJson(`${ADMIN_SCORES_URL}/${change.scoreId}`, {
                    method: "PUT",
                    auth: true,
                    body: {
                        timeMs: change.timeMs
                    }
                });
            } else {
                await requestJson(`${ADMIN_SCORES_URL}/add`, {
                    method: "POST",
                    auth: true,
                    body: {
                        schoolId: change.schoolId,
                        categoryId: change.categoryId,
                        timeMs: change.timeMs
                    }
                });
            }
        }

        adminState.dirtyScores.clear();
        saveScoresBtn.classList.remove("attention");
        showAdminMessage("Wyniki zapisane.", "success");
        await loadAdminData();
        await loadResults();
    } catch (error) {
        console.error(error);
        showAdminMessage(`Nie udało się zapisać wyników: ${error.message}`, "error");
    } finally {
        setButtonLoading(saveScoresBtn, false);
    }
}

async function loadAdminData() {
    const [categories, schools, scores] = await Promise.all([
        fetchJson(CATEGORIES_URL),
        fetchJson(SCHOOLS_URL),
        fetchJson(SCORES_URL)
    ]);

    adminState.categories = categories;
    adminState.schools = schools;
    adminState.scores = scores;

    renderAdminLists();
    renderAdminScoresTable();
}

function showLoginView() {
    loginInput.value = "";
    passwordInput.value = "";
    loginView.classList.remove("hidden");
    adminView.classList.add("hidden");
    loginInput.focus();
}

function showAdminView() {
    loginView.classList.add("hidden");
    adminView.classList.remove("hidden");
}

function startRefreshTimer() {
    if (adminRefreshTimer) {
        clearInterval(adminRefreshTimer);
    }

    if (!adminState.refreshToken) return;

    adminRefreshTimer = setInterval(() => {
        refreshAccessToken(false).catch(error => {
            console.error(error);
            showAdminMessage("Sesja wygasła. Zaloguj się ponownie.", "error");
            logout(false);
        });
    }, TOKEN_REFRESH_INTERVAL_MS);
}

async function refreshAccessToken(showMessage = true) {
    if (!adminState.refreshToken) {
        throw new Error("Brak refresh tokena. Zaloguj się ponownie.");
    }

    const result = await requestJson(AUTH_REFRESH_URL, {
        method: "POST",
        retryAuth: false,
        body: {
            refreshToken: adminState.refreshToken
        }
    });

    adminState.accessToken = result.accessToken;
    persistTokens();

    if (showMessage) {
        showAdminMessage("Token odświeżony.", "success");
    }

    return result.accessToken;
}

async function restoreSession() {
    if (!adminState.accessToken && !adminState.refreshToken) {
        showLoginView();
        return;
    }

    showAdminView();
    setAdminStatus("Sprawdzam sesję...");

    try {
        if (adminState.refreshToken) {
            await refreshAccessToken(false);
        }

        startRefreshTimer();
        hideAdminMessage();
    } catch (error) {
        console.error(error);
        clearTokens();
        showLoginView();
        showAdminMessage("Nie udało się odświeżyć sesji. Zaloguj się ponownie.", "error");
        return;
    }

    try {
        await loadAdminData();
    } catch (error) {
        console.error(error);
        setAdminStatus("Nie udało się pobrać danych do tabeli.");
        showAdminMessage(`Błąd pobierania danych: ${error.message}`, "error");
    }
}

async function handleLogin(event) {
    event.preventDefault();
    hideAdminMessage();
    setButtonLoading(loginForm.querySelector("button"), true, "Loguję...");

    try {
        const result = await requestJson(AUTH_LOGIN_URL, {
            method: "POST",
            retryAuth: false,
            body: {
                login: loginInput.value.trim(),
                rawPassword: passwordInput.value
            }
        });

        adminState.accessToken = result.accessToken;
        adminState.refreshToken = result.refreshToken || "";
        persistTokens();
        passwordInput.value = "";
        showAdminView();
        startRefreshTimer();
        showAdminMessage("Zalogowano.", "success");
        await loadAdminData();
    } catch (error) {
        console.error(error);
        showAdminMessage(`Błąd logowania: ${error.message}`, "error");
    } finally {
        setButtonLoading(loginForm.querySelector("button"), false);
    }
}

async function handleCreateCategory(event) {
    event.preventDefault();
    const name = categoryNameInput.value.trim();

    if (!name) return;

    const button = categoryForm.querySelector("button");
    setButtonLoading(button, true, "Dodaję...");

    try {
        await requestJson(ADMIN_CATEGORIES_URL, {
            method: "POST",
            auth: true,
            body: { name }
        });

        categoryNameInput.value = "";
        showAdminMessage("Kategoria dodana.", "success");
        await loadAdminData();
        await loadResults();
    } catch (error) {
        console.error(error);
        showAdminMessage(`Nie udało się dodać kategorii: ${error.message}`, "error");
    } finally {
        setButtonLoading(button, false);
    }
}

async function handleCreateSchool(event) {
    event.preventDefault();
    const name = schoolNameInput.value.trim();

    if (!name) return;

    const button = schoolForm.querySelector("button");
    setButtonLoading(button, true, "Dodaję...");

    try {
        await requestJson(ADMIN_SCHOOLS_URL, {
            method: "POST",
            auth: true,
            body: { name }
        });

        schoolNameInput.value = "";
        showAdminMessage("Szkoła dodana.", "success");
        await loadAdminData();
        await loadResults();
    } catch (error) {
        console.error(error);
        showAdminMessage(`Nie udało się dodać szkoły: ${error.message}`, "error");
    } finally {
        setButtonLoading(button, false);
    }
}

async function deleteRelatedScores(predicate) {
    const scoresToDelete = adminState.scores.filter(predicate);

    for (const score of scoresToDelete) {
        await requestJson(`${ADMIN_SCORES_URL}/${score.id}`, {
            method: "DELETE",
            auth: true
        });
    }

    return scoresToDelete.length;
}

async function deleteCategory(categoryId) {
    const category = adminState.categories.find(item => item.id === categoryId);

    if (!category) return;
    if (!confirm(`Usunąć kategorię "${category.name}" i jej wyniki?`)) return;

    hideAdminMessage();

    try {
        const deletedScores = await deleteRelatedScores(score => score.categoryId === categoryId);

        await requestJson(`${ADMIN_CATEGORIES_URL}/${categoryId}`, {
            method: "DELETE",
            auth: true
        });

        showAdminMessage(`Kategoria usunięta. Usunięto też wyników: ${deletedScores}.`, "success");
        await loadAdminData();
        await loadResults();
    } catch (error) {
        console.error(error);
        showAdminMessage(`Nie udało się usunąć kategorii: ${error.message}`, "error");
    }
}

async function deleteSchool(schoolId) {
    const school = adminState.schools.find(item => item.id === schoolId);

    if (!school) return;
    if (!confirm(`Usunąć szkołę "${school.name}" i jej wyniki?`)) return;

    hideAdminMessage();

    try {
        const deletedScores = await deleteRelatedScores(score => score.schoolId === schoolId);

        await requestJson(`${ADMIN_SCHOOLS_URL}/${schoolId}`, {
            method: "DELETE",
            auth: true
        });

        showAdminMessage(`Szkoła usunięta. Usunięto też wyników: ${deletedScores}.`, "success");
        await loadAdminData();
        await loadResults();
    } catch (error) {
        console.error(error);
        showAdminMessage(`Nie udało się usunąć szkoły: ${error.message}`, "error");
    }
}

async function openAdminPanel() {
    adminPanel.classList.remove("hidden");
    adminPanel.scrollIntoView({ behavior: "smooth", block: "start" });
    await restoreSession();
}

function closeAdminPanel() {
    adminPanel.classList.add("hidden");
}

function logout(showMessage = true) {
    if (adminRefreshTimer) {
        clearInterval(adminRefreshTimer);
        adminRefreshTimer = null;
    }

    clearTokens();
    adminState.dirtyScores.clear();
    adminScoresTable.innerHTML = "";
    showLoginView();

    if (showMessage) {
        showAdminMessage("Wylogowano.");
    }
}

tableBtn.addEventListener("click", () => {
    tableBtn.classList.add("active");
    chartBtn.classList.remove("active");

    tableView.classList.remove("hidden");
    chartView.classList.add("hidden");
});

chartBtn.addEventListener("click", () => {
    chartBtn.classList.add("active");
    tableBtn.classList.remove("active");

    chartView.classList.remove("hidden");
    tableView.classList.add("hidden");

    if (chart) chart.resize();
});

adminOpenBtn.addEventListener("click", openAdminPanel);
adminCloseBtn.addEventListener("click", closeAdminPanel);
loginForm.addEventListener("submit", handleLogin);
categoryForm.addEventListener("submit", handleCreateCategory);
schoolForm.addEventListener("submit", handleCreateSchool);
saveScoresBtn.addEventListener("click", saveScoreChanges);
refreshAdminBtn.addEventListener("click", async () => {
    setButtonLoading(refreshAdminBtn, true, "Odświeżam...");

    try {
        await refreshAccessToken(true);
        await loadAdminData();
        await loadResults();
    } catch (error) {
        console.error(error);
        showAdminMessage(`Nie udało się odświeżyć danych: ${error.message}`, "error");
    } finally {
        setButtonLoading(refreshAdminBtn, false);
    }
});
logoutBtn.addEventListener("click", () => logout(true));

loadResults();
setInterval(loadResults, 5000);
