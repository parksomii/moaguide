addEventListener("DOMContentLoaded", function() {
    const all = document.getElementById("all");
    const building = document.getElementById("building");
    const music = document.getElementById("music");
    const cow = document.getElementById("cow");
    const art = document.getElementById("art");
    const content = document.getElementById("content");
    const iframe = document.getElementById("contentFrame");


    all.addEventListener("click",function (){
        iframe.src="/summary/list/all";
    })

    building.addEventListener("click",function (){
        iframe.src="/summary/list/building";
    })
    music.addEventListener("click",function (){
        iframe.src="/summary/list/music";
    })
    cow.addEventListener("click",function (){
        iframe.src="/summary/list/cow";
    })
    art.addEventListener("click",function (){
        iframe.src="/summary/list/art.art";
    })
    content.addEventListener("click",function (){
        iframe.src="/summary/list/content";
    })
})


