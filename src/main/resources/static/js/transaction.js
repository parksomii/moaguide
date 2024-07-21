document.addEventListener('DOMContentLoaded', function() {
    const three = document.getElementById("three-timeTab");
    const six = document.getElementById("six-timeTab");
    const all = document.getElementById("all-timeTab");
    const id = document.getElementById("product").className;

    three.addEventListener("click", () => {
        const date = new Date();
        date.setMonth(date.getMonth() - 3);
        const formattedDate = date.toISOString().split('T')[0]; // YYYY-MM-DD 형식으로 변환
        fetchData('/summary/detail/transaction/'+id+'?date='+formattedDate);
    });

    six.addEventListener("click", () => {
        const date = new Date();
        date.setMonth(date.getMonth() - 6);
        const formattedDate = date.toISOString().split('T')[0]; // YYYY-MM-DD 형식으로 변환
        fetchData('/summary/detail/transaction/'+id+'?date='+formattedDate);
    });

    all.addEventListener("click", () => {
        const date = new Date('2020-01-01');
        const formattedDate = date.toISOString().split('T')[0]; // YYYY-MM-DD 형식으로 변환
        fetchData('/summary/detail/transaction/'+id+'?date='+formattedDate);
    });

    function fetchData(url) {
        fetch(url)
            .then(response => response.json())
            .then(data => {
                updateChart(data); // 데이터가 배열로 전달되도록 수정
            })
            .catch(error => {
                console.error('Error:', error);
            });
    }

    function updateChart(transactions) {
        const canvas = document.getElementById('transaction');
        if (transactions.length === 0) {
            canvas.style.display = 'none';
            return;
        } else {
            canvas.style.display = 'block';
        }

        var formattedData = transactions.map(data => ({
            x: new Date(data.tradeDay + 'T' + data.tradeTime),
            y: data.price
        }));

        var minDate = new Date(Math.min(...formattedData.map(data => data.x)));
        var maxDate = new Date(Math.max(...formattedData.map(data => data.x)));

        var ctx = canvas.getContext('2d');

        if (window.transactionChart) {
            window.transactionChart.destroy(); // 기존 차트를 파괴하여 새로운 데이터로 갱신
        }

        window.transactionChart = new Chart(ctx, {
            type: 'line',
            data: {
                datasets: [{
                    label: 'Price',
                    data: formattedData,
                    borderColor: 'black',
                    borderWidth: 1,
                    fill: false,
                    pointRadius: 0 // 데이터 포인트를 숨깁니다.
                }]
            },
            options: {
                plugins: {
                    tooltip: {
                        mode: 'index',
                        intersect: false,
                        callbacks: {
                            label: function (context) {
                                var date = new Date(context.raw.x);
                                var day = date.toISOString().split('T')[0]; // yyyy-MM-dd 형식
                                var time = date.toTimeString().split(' ')[0]; // HH:mm:ss 형식
                                return [
                                    'Date: ' + day,
                                    'Time: ' + time,
                                    'Price: ' + context.raw.y
                                ];
                            }
                        }
                    },
                    crosshair: {
                        line: {
                            color: 'red', // 가상선 색상
                            width: 1      // 가상선 너비
                        }
                    }
                },
                scales: {
                    x: {
                        type: 'time',
                        time: {
                            unit: 'month', // 각 월의 1일만 표시
                            tooltipFormat: 'yyyy-MM-dd',
                            displayFormats: {
                                month: 'yyyy-MM' // x축에 월 단위로 표시
                            }
                        },
                        title: {
                            display: true,
                            text: 'Date'
                        },
                        min: minDate,
                        max: maxDate
                    },
                    y: {
                        title: {
                            display: true,
                            text: 'Price'
                        }
                    }
                },
                hover: {
                    mode: 'index',
                    intersect: false
                }
            },
            plugins: [{
                id: 'crosshair',
                afterDraw: function (chart) {
                    // 툴팁이 활성화되었는지 확인
                    if (chart.tooltip && chart.tooltip._active && chart.tooltip._active.length) {
                        var ctx = chart.ctx;
                        var x = chart.tooltip._active[0].element.x;
                        var topY = chart.scales.y.top;
                        var bottomY = chart.scales.y.bottom;

                        // 세로선을 그립니다.
                        ctx.save();
                        ctx.beginPath();
                        ctx.moveTo(x, topY);
                        ctx.lineTo(x, bottomY);
                        ctx.lineWidth = 1;
                        ctx.strokeStyle = 'red';
                        ctx.stroke();
                        ctx.restore();
                    }
                }
            }]
        });
    }
});

document.addEventListener('DOMContentLoaded', function() {
    const threeTimeTab = document.getElementById('three-timeTab');
    const sixTimeTab = document.getElementById('six-timeTab');
    const allTimeTab = document.getElementById('all-timeTab');

    threeTimeTab.addEventListener('click', function() {
        activateTab(this, 'three-time');
    });

    sixTimeTab.addEventListener('click', function() {
        activateTab(this, 'six-time');
    });

    allTimeTab.addEventListener('click', function() {
        activateTab(this, 'all-time');
    });

    function activateTab(clickedTab, tabId) {
        // 모든 엘립스를 숨김
        document.querySelectorAll('.ellipse-401').forEach(ellipse => {
            ellipse.classList.add('hidden');
        });

        // 활성 탭과 관련된 엘립스 표시
        document.getElementById(tabId).classList.remove('hidden');

        // check-time 클래스 관리
        document.querySelectorAll('.div25').forEach(tab => {
            tab.classList.remove('check-time');
        });
        clickedTab.classList.add('check-time');
    }
});

