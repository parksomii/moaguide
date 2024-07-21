function changeIframeSrc(url) {
    var iframe = document.getElementById('latest-news-iframe');
    iframe.src = url;
}

window.addEventListener('message', function (e) {
    let message = e.data;
    console.log("Received message:", message);
    let iframe = document.getElementById('latest-news-iframe');
    if (message.height) {
        iframe.style.height = message.height + 100 + 'px';
    }
}, false);