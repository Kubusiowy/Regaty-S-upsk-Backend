const ctx = document.getElementById("resultsChart");

new Chart(ctx, {
    type: "bar",
    data: {
        labels: ["LO1", "LO2", "LO3", "LO4", "LO5", "Rolniczak", "ELEKTRYK"],
        datasets: [{
            label: "Sekundy",
            data: [82, 74, 23, 54, 21, 67, 48],
            backgroundColor: ["#0f7c90", "#ef8f38", "#14213d", "#0f7c90", "#ef8f38", "#14213d", "#0a4f66"],
            borderRadius: 6
        }]
    },
    options: {
        indexAxis: "y",
        responsive: true,
        scales: {
            x: {
                beginAtZero: true
            }
        }
    }
});
