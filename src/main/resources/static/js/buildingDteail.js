$(document).ready(function() {
    $('.categories-text').on('click', function () {
        // 모든 버튼의 'active' 클래스 제거
        $('.categories-text').removeClass('active');
        // 클릭된 버튼에만 'active' 클래스 추가
        $(this).addClass('active');
    });
});
    function changeIframeSrc(url) {
        var iframe = document.getElementById('contentFrame');
        iframe.src = url;
    }

    window.addEventListener('message', function (e) {
        let message = e.data;
        console.log("Received message:", message);
        let iframe = document.getElementById('contentFrame');
        if (message.height) {
            iframe.style.height = message.height + 100 + 'px';
        }
    }, false);