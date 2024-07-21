document.addEventListener("DOMContentLoaded", function() {
    var checkButton = document.querySelector('.check-btn');
    var submitButton = document.querySelector('form .login-form button[type="submit"]');
    var usernameInput = document.getElementById('userId');
    var isUsernameValid = false; // 중복 검사 결과 저장

    checkButton.addEventListener('click', function() {
        var userId = usernameInput.value;
        if (!userId) {
            alert('아이디를 입력해주세요.');
            return;
        }

        fetch('/user/signup/duplicate?userId=' + userId, { // 쿼리 매개변수로 userId 전달
            method: 'POST'
        })
            .then(response => response.text())
            .then(data => {
                if (data === "duplicate") {
                    alert('이미 사용중인 아이디입니다.');
                    isUsernameValid = false; // 중복이면 false
                } else {
                    alert('사용 가능한 아이디입니다.');
                    isUsernameValid = true; // 중복 아니면 true
                }
            })
            .catch(error => {
                console.error('Error:', error);
                alert('서버 오류가 발생했습니다. 나중에 다시 시도해주세요.');
                isUsernameValid = false; // 오류 발생 시 false
            });
    });

    // 폼 제출 이벤트 리스너 추가
    document.querySelector('form').addEventListener('submit', function(event) {
        if (!isUsernameValid) {
            alert('아이디 중복 검사를 통과해야 합니다.');
            event.preventDefault(); // 폼 제출 중단
        }
    });

    usernameInput.addEventListener('input', function() {
        isUsernameValid = false; // 아이디 입력값이 변경되면 유효성 검사를 다시 해야하므로 false로 설정
    });
});
