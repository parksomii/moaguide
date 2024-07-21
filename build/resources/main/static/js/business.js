document.addEventListener('DOMContentLoaded', function() {
    let myChart,vacancyRateChart;
    const keyword = document.getElementsByClassName('subway-submit')[0].id;
    var container = document.getElementById('buttonContainer');
    container.addEventListener('click', function(event) {
        if (event.target.tagName === 'BUTTON') {
            // 모든 'ellipse-401' 요소를 숨김 처리
            var ellipses = document.getElementsByClassName('ellipse-430');
            for (var j = 0; j < ellipses.length; j++) {
                ellipses[j].classList.add('hidden');
            }

            // 선택된 버튼에 대응하는 'ellipse-401' 요소의 'hidden' 클래스 제거
            var selectedEllipseId = event.target.id + '-check';
            var selectedEllipse = document.getElementById(selectedEllipseId);
            if (selectedEllipse) {
                selectedEllipse.classList.remove('hidden');
            }

            // 모든 버튼에서 'check-business' 클래스 제거
            var buttons = container.getElementsByTagName('button');
            for (var i = 0; i < buttons.length; i++) {
                buttons[i].classList.remove('check-business');
            }

            event.target.classList.add('check-business');
            fetch("/summary/detail/Building/business/"+keyword+"?type="+event.target.id)
                .then(response => {
                    if (!response.ok) {
                        throw new Error("Network response was not ok " + response.statusText);
                    }
                    return response.json();
                })
                .then(data => {
                    const Rent = data.rent;
                    const vacancyRate = data.vacancyRate;

                    var labels = [...new Set(Rent.map(item => item.year + '년 ' + item.quarter + '분기'))];

                    // 지역 목록 생성
                    var regions = [...new Set(Rent.map(item => item.region))];

                    // 지역별 데이터 그룹화
                    const datasets = regions.map(region => {
                        return {
                            label: region,
                            data: labels.map(label => {
                                var item = Rent.find(data => (data.year + '년 ' + data.quarter + '분기') === label && data.region === region);
                                return item ? item.rent : 0;
                            }),
                            backgroundColor: getRandomColor(),
                        };
                    });

                    function getRandomColor() {
                        var letters = '0123456789ABCDEF';
                        var color = '#';
                        for (var i = 0; i < 6; i++) {
                            color += letters[Math.floor(Math.random() * 16)];
                        }
                        return color;
                    }

                    // 지역별 데이터 그룹화
                    const Vdatasets = regions.map(region => {
                        return {
                            label: region,
                            data: labels.map(label => {
                                var item = VRATE.find(data => (data.year + '년 ' + data.quarter + '분기') === label && data.region === region);
                                return item ? item.vacancyRate : 0;
                            }),
                            backgroundColor: getRandomColor(),
                            borderWidth: 1,
                            borderRadius: 10,  // 막대의 둥근 모서리 설정
                            barPercentage: 0.5,  // 막대의 두께 조정
                            categoryPercentage: 0.5 // 막대의 두께 조정
                        };
                    });

                    var ctx = destroyChart(myChart, 'Rent');
                    myChart = createChart(ctx, datasets);

                    var vctx = destroyChart(vacancyRateChart, 'VacancyRate');
                    vacancyRateChart = createVChart(vctx, Vdatasets);
                });
        }
    });

    function destroyChart(chart, canvasId) {
        if (chart) {
            chart.destroy();
            chart = null; // chart 변수를 null로 설정하여 완전히 제거
        }

        const canvas = document.getElementById(canvasId);
        const parent = canvas.parentNode;
        parent.removeChild(canvas);

        const newCanvas = document.createElement('canvas');
        newCanvas.id = canvasId;
        newCanvas.className = 'graph';
        parent.appendChild(newCanvas);

        return newCanvas.getContext('2d');
    }

    function createVChart(ctx, newdata1) {
        return new Chart(ctx, {
            type: 'bar',
            data: {
                labels: labels,
                datasets: newdata1
            },
            options: {
                responsive: false,
                scales: {
                    y: {
                        beginAtZero: true
                    }
                },
                plugins: {
                    tooltip: {
                        callbacks: {
                            label: function(context) {
                                return context.raw + '%';
                            }
                        }
                    },
                    datalabels: {
                        anchor: 'end',
                        align: 'end',
                        formatter: function(value, context) {
                            return value + '%';
                        },
                        color: 'blue', // 데이터 레이블 색상 설정
                        font: {
                            weight: 'bold'
                        }
                    },
                    crosshair: false // crosshair 플러그인 비활성화
                }
            },
            plugins: [ChartDataLabels] // 플러그인 추가
        });
    }

    function createChart(ctx, newdata1) {
        return new Chart(ctx, {
            type: 'line',
            data: {
                labels: labels,
                datasets: newdata1
            },
            options: {
                responsive: false,
                scales: {
                    y: {
                        beginAtZero: true
                    }
                },
                plugins: {
                    tooltip: {
                        mode: 'index',
                        intersect: false,
                        callbacks: {
                            label: function (tooltipItem) {
                                return `X: ${tooltipItem.label}, Y: ${tooltipItem.formattedValue}`;
                            }
                        }
                    },
                    crosshair: {
                        line: {
                            color: '#F66',  // crosshair line color
                            width: 1        // crosshair line width
                        },
                        sync: {
                            enabled: false  // disable trace line syncing with other charts
                        },
                        zoom: {
                            enabled: false  // disable zooming
                        }
                    }
                },
                hover: {
                    mode: 'index',
                    intersect: false
                }
            },
        });
    }
});
