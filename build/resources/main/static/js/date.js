document.addEventListener('DOMContentLoaded', function() {

    const submit = document.getElementsByClassName('subway-submit')[0];

    submit.addEventListener("click", function () {
        const keyword = this.id;
        submitsubwayDate(keyword);
    });

    let subwayChart;
    let subwayWeekChart;

    function destroyChart(chart, canvasId) {
        if (chart) {
            chart.destroy();
        }

        // 캔버스 요소를 재설정
        const canvas = document.getElementById(canvasId);
        const parent = canvas.parentNode;
        parent.removeChild(canvas);

        const newCanvas = document.createElement('canvas');
        newCanvas.id = canvasId;
        newCanvas.className = 'graph';
        parent.appendChild(newCanvas);

        return newCanvas.getContext('2d');
    }


    function createChart(ctx, labels, newData1, newData2, label1, label2) {
        return new Chart(ctx, {
            type: 'bar',
            data: {
                labels: labels,
                datasets: [
                    {
                        label: label1,
                        data: newData1,
                        backgroundColor: 'rgba(54, 162, 235, 0.2)',
                        borderColor: 'rgba(54, 162, 235, 1)',
                        borderWidth: 1
                    },
                    {
                        label: label2,
                        data: newData2,
                        backgroundColor: 'rgba(255, 99, 132, 0.2)',
                        borderColor: 'rgba(255, 99, 132, 1)',
                        borderWidth: 1
                    }
                ]
            },
            options: {
                scales: {
                    x: {
                        stacked: true
                    },
                    y: {
                        stacked: true,
                        beginAtZero: true
                    }
                }
            }
        });
    }

    function submitsubwayDate(keyword) {
        const year = document.getElementById('year').value;
        const month = document.getElementById('month').value;
        fetch('/summary/detail/Building/subway/' + keyword + '?year=' + year + '&month=' + month)
            .then(response => {
                if (!response.ok) {
                    throw new Error("Network response was not ok " + response.statusText);
                }
                return response.json();
            })
            .then(data => {
                const subwaytime = data.subwayTime;
                const subwayweek = data.subwayWeek;

                if (!subwaytime) {
                    var elements = document.getElementsByClassName('graph_subway');
                    for (var i = 0; i < elements.length; i++) {
                        elements[i].style.display = 'none';
                    }
                } else {
                    var time = ['05시', '06시', '07시', '08시', '09시', '10시', '11시', '12시', '13시', '14시', '15시', '16시', '17시', '18시', '19시', '20시', '21시', '22시', '23시'];

                    var boarding = [];
                    var alighting = [];

                    time.forEach((hour, index) => {
                        var nextHour = (index < 18) ? time[index + 1].slice(0, -1) : '24';
                        var boardingKey = `boarding_${hour.slice(0, -1)}_${nextHour}`;
                        var alightingKey = `alighting_${hour.slice(0, -1)}_${nextHour}`;

                        boarding.push(subwaytime[boardingKey] || 0);
                        alighting.push(subwaytime[alightingKey] || 0);
                    });

                    var ctx = destroyChart(subwayChart, 'subwayTime');
                    subwayChart = createChart(ctx, time, boarding, alighting, '승차', '하차');

                    var daysOfWeekEng = ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'];
                    var daysOfWeekKor = ['일', '월', '화', '수', '목', '금', '토'];
                    var totalBoarding = new Array(7).fill(0);
                    var totalAlighting = new Array(7).fill(0);

                    subwayweek.forEach(entry => {
                        var dayIndex = daysOfWeekEng.indexOf(entry.dayofWeek);
                        if (dayIndex !== -1) {
                            totalBoarding[dayIndex] += entry.totalBoarding;
                            totalAlighting[dayIndex] += entry.totalAlighting;
                        }
                    });

                    var ctxWeek = destroyChart(subwayWeekChart, 'subwayWeek');
                    subwayWeekChart = createChart(ctxWeek, daysOfWeekKor, totalBoarding, totalAlighting, '총 승차', '총 하차');

                }
            })
            .catch(error => {
                console.error("There has been a problem with your fetch operation:", error);
            });
    }
});



document.addEventListener('DOMContentLoaded', function() {
    let dayChart, genderChart, ageChart;

    function destroyChart(chart, canvasId) {
        if (chart) {
            chart.destroy();
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

    function createweekChart(ctx, newData1) {
        return new Chart(ctx, {
            type: 'bar',
            data: {
                labels: WeekKor,
                datasets: [{
                    label: '요일별 생활 인구수',
                    data: Object.values(newData1),
                    backgroundColor: 'rgba(75, 192, 192, 0.2)',
                    borderColor: 'rgba(75, 192, 192, 1)',
                    borderWidth: 1
                }]
            },
            options: {
                scales: {
                    y: {
                        beginAtZero: true
                    }
                }
            }
        });
    }

    function creategenderChart(ctx, newdata1) {
        return new Chart(ctx, {
            type: 'doughnut',
            data: {
                labels: ['남성', '여성'],
                datasets: [{
                    label: '성별 생활인구 수',
                    data: [newdata1.man, newdata1.girl],
                    backgroundColor: ['rgba(54, 162, 235, 0.2)', 'rgba(255, 99, 132, 0.2)'],
                    borderColor: ['rgba(54, 162, 235, 1)', 'rgba(255, 99, 132, 1)'],
                    borderWidth: 1
                }]
            },
            options: {
                rotation: -90,
                circumference: 180,
                responsive: true
            }
        });
    }

    function createAgeChart(ctx, newData1) {
        return new Chart(ctx, {
            type: 'doughnut',
            data: {
                labels: ['10살이전', '10대', '20대', '30대', '40대', '50대', '60대', '70대'],
                datasets: [{
                    label: '나이대별 인구 수',
                    data: Object.values(newData1),
                    backgroundColor: [
                        'rgba(255, 206, 86, 0.2)',  // Light Yellow
                        'rgba(75, 192, 192, 0.2)',  // Soft Green
                        'rgba(153, 102, 255, 0.2)', // Light Purple
                        'rgba(255, 159, 64, 0.2)',  // Light Orange
                        'rgba(165, 42, 42, 0.2)',   // Brown
                        'rgba(128, 0, 128, 0.2)',   // Purple
                        'rgba(64, 224, 208, 0.2)',  // Turquoise
                        'rgba(255, 99, 71, 0.2)'    // Tomato
                    ],
                    borderColor: [
                        'rgba(255, 206, 86, 1)',  // Light Yellow
                        'rgba(75, 192, 192, 1)',  // Soft Green
                        'rgba(153, 102, 255, 1)', // Light Purple
                        'rgba(255, 159, 64, 1)',  // Light Orange
                        'rgba(165, 42, 42, 1)',   // Brown
                        'rgba(128, 0, 128, 1)',   // Purple
                        'rgba(64, 224, 208, 1)',  // Turquoise
                        'rgba(255, 99, 71, 1)'    // Tomato
                    ],
                    borderWidth: 1
                }]
            },
            options: {
                rotation: -90,
                circumference: 180,
                responsive: true
            }
        });
    }

    const popsubmit = document.getElementsByClassName('population-submit')[0];

    popsubmit.addEventListener("click", function () {
        const id = this.id;
        submitpopDate(id);
    });

    function submitpopDate(id) {
        const year = document.getElementById('pop-year').value;
        const month = document.getElementById('pop-month').value;
        fetch('/summary/detail/Building/population/' + id + '?year=' + year + '&month=' + month)
            .then(response => {
                if (!response.ok) {
                    throw new Error("Network response was not ok " + response.statusText);
                }
                return response.json();
            })
            .then(data => {
                const population = data;

                if (!Array.isArray(population)) {
                    throw new Error('population is not an array');
                }

                const WeekEng = ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'];
                const WeekKor = ['일', '월', '화', '수', '목', '금', '토'];

                // 요일별 데이터
                const daysData = new Array(7).fill(0);
                const genderData = { man: 0, girl: 0 };
                const ageData = {
                    age0: 0,
                    age10: 0,
                    age20: 0,
                    age30: 0,
                    age40: 0,
                    age50: 0,
                    age60: 0,
                    age70: 0
                };

                population.forEach(entry => {
                    // 요일별 데이터 집계
                    var dayIndex = WeekEng.indexOf(entry.weekDay);
                    if (dayIndex !== -1) {
                        daysData[dayIndex] += entry.total;
                    }

                    // 성별 데이터 집계
                    genderData.man += entry.man;
                    genderData.girl += entry.girl;

                    // 나이대별 데이터 집계
                    ageData.age0 += entry.age0;
                    ageData.age10 += entry.age10;
                    ageData.age20 += entry.age20;
                    ageData.age30 += entry.age30;
                    ageData.age40 += entry.age40;
                    ageData.age50 += entry.age50;
                    ageData.age60 += entry.age60;
                    ageData.age70 += entry.age70;
                });

                // 기존 차트가 있으면 삭제 후 새로 생성
                var ctx = destroyChart(dayChart, 'dayChart');
                dayChart = createweekChart(ctx, daysData);

                var vCtx = destroyChart(genderChart, 'genderChart');
                genderChart = creategenderChart(genderCtx, genderData);

                var ageCtx = destroyChart(ageChart, 'ageChart');
                ageChart = createAgeChart(ageCtx, ageData);
            })
            .catch(error => {
                console.error("There has been a problem with your fetch operation:", error);
            });
    };
});