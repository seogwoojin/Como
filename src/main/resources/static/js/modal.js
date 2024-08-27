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
        cancelButtonText: 'Normal',
        denyButtonText: 'Hard',
        reverseButtons: true
    }).then((result) => {
        if (result.isConfirmed) {
            sendChoice('easy', company);
        } else if (result.isDenied) {
            sendChoice('hard', company);
        } else if (result.dismiss === Swal.DismissReason.cancel) {
            sendChoice('normal', company);
        }
    });
}



function sendChoice(choice, company) {
    fetch('/problem', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            company: company,
            choice: choice
        })
    })
        .then(response => {
            return response.text();
        })
        .then(html => {
            // Replace the entire content of the page with the received HTML
            document.open();
            document.write(html);
            document.close();
        })
        .catch((html => {
            // Replace the entire content of the page with the received HTML
            document.open();
            document.write(html);
            document.close();
        }))
}



