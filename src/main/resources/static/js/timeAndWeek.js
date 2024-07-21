document.addEventListener('DOMContentLoaded', function() {
    const timeTab = document.getElementById('timeTab');
    const weekTab = document.getElementById('weekTab');

    timeTab.addEventListener('click', function() {
        activateTab(this, '.graph-time', 'time');
    });

    weekTab.addEventListener('click', function() {
        activateTab(this, '.graph-week', 'week');
    });

    function activateTab(clickedTab, graphClass, tabId) {
        // 모든 그래프를 숨기고
        document.querySelectorAll('.graph-time, .graph-week').forEach(graph => {
            graph.classList.add('hidden');
        });

        // 모든 탭의 엘립스를 숨김
        document.querySelectorAll('.ellipse-401').forEach(ellipse => {
            ellipse.classList.add('hidden');
        });

        // 활성 탭과 관련된 그래프 및 엘립스 표시
        document.querySelector(graphClass).classList.remove('hidden');
        document.getElementById(tabId).classList.remove('hidden');

        // check-graph 클래스 관리
        document.querySelectorAll('.div25').forEach(tab => {
            tab.classList.remove('check-graph');
        });
        clickedTab.classList.add('check-graph');
    }
});
