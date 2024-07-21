function drawGraph(graphData) {
    const canvasId = graphData ? graphData[0].productId : '';
    if (canvasId) {
        const ctx = document.getElementById(canvasId).getContext('2d');

        const labels = graphData.map(dataPoint => dataPoint.tradeTime);
        const data = graphData.map(dataPoint => dataPoint.price);

        new Chart(ctx, {
            type: 'line', // 그래프 타입 (라인 그래프)
            data: {
                labels: labels,
                datasets: [{
                    label: 'Price over Time',
                    data: data,
                    backgroundColor: 'rgba(75, 192, 192, 0.2)',
                    borderColor: 'rgba(75, 192, 192, 1)',
                    borderWidth: 1,
                    fill: false
                }]
            },
            options: {
                responsive: false,
                plugins: {
                    legend: {
                        display: false // 레전드(범례) 비활성화
                    },
                    tooltip: {
                        enabled: false // 툴팁 비활성화
                    }
                },
                scales: {
                    x: {
                        display: false // X축 비활성화
                    },
                    y: {
                        display: false, // Y축 비활성화
                        beginAtZero: true
                    }
                },
                elements: {
                    point: {
                        radius: 0 // 포인트 비활성화
                    }
                }
            }
        });
    }
}
