function formatTime(ms) {
    const minutes = Math.floor(ms / 60000);
    const seconds = Math.floor((ms % 60000) / 1000);


    const centiseconds = Math.round(ms % 1000);

    return `${minutes}:${seconds.toString().padStart(2, "0")}.${centiseconds.toString().padStart(2, "0")}`;
}

const ctx = document.getElementById("resultsChart")

new Chart(ctx, {
    type: "bar",
    data: {
        labels: ["LO1", "LO2", "LO3", "LO4", "LO5"],
        datasets: [{
            label: "Czas",
            data: [75321, 74210, 81234, 65432, 70211],
            backgroundColor: [
                "#0f7c90",
                "#ef8f38",
                "#14213d",
                "#0f7c90",
                "#ef8f38"
            ],
            borderRadius: 6
        }]
    },
    options: {
        indexAxis: "y",
        responsive: true,

        scales: {
            x: {
                beginAtZero: true,

                ticks: {
                    callback: function(value) {
                        return formatTime(value)
                    }
                }
            }
        },

        plugins: {
            tooltip: {
                callbacks: {
                    label: function(context) {
                        return formatTime(context.raw)
                    }
                }
            }
        }
    }
})