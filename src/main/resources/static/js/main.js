// 로그인/회원가입 버튼 요소와 마이페이지 버튼 요소 가져오기
const loginButton = document.getElementById('userButton');
const myPageButton = document.getElementById('myPageButton');
const myPageLink = document.getElementById('myPageLink');
const logoutButton = document.getElementById("logout")

const myPageText = myPageLink.innerText;
const urlParams = new URLSearchParams(window.location.search);
const error = urlParams.get('error');
const seccess = urlParams.get('seccess')

// 사용자가 로그인한 경우에만 마이페이지 버튼을 표시하도록 설정
function updateButtons() {

    if (myPageText != "anonymousUser 님 마이페이지") {
        loginButton.classList.add('hidden');
        myPageButton.classList.remove('hidden');
        logoutButton.classList.remove('hidden');
    } else {
        loginButton.classList.remove('hidden');
        myPageButton.classList.add('hidden');
        logoutButton.classList.add('hidden');
    }
}

// 페이지 로드될 때마다 버튼 상태 업데이트
document.addEventListener('DOMContentLoaded', updateButtons);

// 만약 error 파라미터가 존재하면 메시지 창 띄우기
if (error === 'accessDenied') {
    alert('멤버쉽 가입 회원만 볼 수 있습니다.');
    // 확인을 누르면 로그인 페이지로 이동
    window.location.href = '/';
}

if(error === "notlogin"){
    alert("로그인을 필요로 합니다.")
    window.location.href="/user/login";
}

if( seccess === 'logout'){
    alert("로그아웃 됐습니다.")
    window.location.href="/"
}