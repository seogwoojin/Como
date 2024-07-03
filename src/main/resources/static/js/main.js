/// main.js

// Wait for the DOM to be fully loaded
document.addEventListener('DOMContentLoaded', function() {
    // 배경을 가리는 overlay 요소
    var overlay = document.getElementById('overlay');

    // 선택지를 포함하는 div 요소
    var resultDiv = document.getElementById('result');

    // 모든 clickable-image 클래스를 가진 이미지 요소를 가져옴
    var clickableImages = document.querySelectorAll('.clickable-image');

    // 이미지 클릭 상태 변수
    var imageClicked = false;

    // 이미지를 클릭할 때 선택지 표시
    clickableImages.forEach(function(image) {
        image.addEventListener('click', function() {
            // 이미지 클릭 상태 변경
            imageClicked = true;

            // 배경 overlay 표시
            overlay.style.display = 'block';
            // 선택지 결과 표시
            resultDiv.style.display = 'block';

            // 이미지 클릭 표시
            clickableImages.forEach(function(img) {
                img.classList.remove('clicked');
            });
            image.classList.add('clicked');
        });
    });

    // 배경을 클릭하면 선택지 숨기기
    overlay.addEventListener('click', function() {
        // 배경 overlay 숨기기
        overlay.style.display = 'none';
        // 선택지 숨기기
        resultDiv.style.display = 'none';

        // 이미지 클릭 상태 초기화
        if (imageClicked) {
            clickableImages.forEach(function(img) {
                img.classList.remove('clicked');
            });
            imageClicked = false;
        }
    });

    // 선택지 클릭 시 해당 URL로 이동
    var choiceElements = document.querySelectorAll('.choice');
    choiceElements.forEach(function(choiceElement, index) {
        choiceElement.addEventListener('click', function() {
            var imageId = "company="+document.querySelector('.clickable-image.clicked').getAttribute('data-id');
            var choice = "choice="+choiceElement.getAttribute('data-choice');
            var url="/problem?"+imageId+"&"+choice;
            window.location.href = url;
        });
    });
});
