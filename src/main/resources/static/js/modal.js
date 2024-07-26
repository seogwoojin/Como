document.addEventListener("DOMContentLoaded", function() {
    const figures = document.querySelectorAll(".tm-open-modal");

    figures.forEach(figure => {
        figure.addEventListener("click", function() {
            const companyName = figure.getAttribute("data-companyname");
            openModal(companyName);
        });
    });

    document.getElementById("confirmStart").addEventListener("click", function() {
        Swal.fire({
            title: '정말로 그렇게 하시겠습니까?',
            text: "다시 되돌릴 수 없습니다. 신중하세요.",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: '승인',
            cancelButtonText: '취소',
            reverseButtons: true,
        }).then((result) => {
            if (result.isConfirmed) {
                Swal.fire(
                    '승인이 완료되었습니다.',
                    '화끈하시네요~!',
                    'success'
                );
            }
        });
    });
});

function openModal(company) {
    Swal.fire({
        title: `Company: ${company}`,
        text: "Select difficulty level:",
        icon: 'info',
        showCancelButton: true,
        showDenyButton: true,
        showConfirmButton: true,
        confirmButtonText: 'Easy',
        denyButtonText: 'Normal',
        cancelButtonText: 'Hard',
        reverseButtons: true
    }).then((result) => {
        if (result.isConfirmed) {
            sendChoice('easy', company);
        } else if (result.isDenied) {
            sendChoice('normal', company);
        } else if (result.dismiss === Swal.DismissReason.cancel) {
            sendChoice('hard', company);
        }
    });
}

function sendChoice(choice, company) {
    window.location.href = `/problem?company=${company}&choice=${choice}`;
}
