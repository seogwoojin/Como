document.addEventListener("DOMContentLoaded", function() {
    document.body.classList.add("loaded");

    var modal = document.getElementById("myModal");
    var modalCompanyInput = document.getElementById("modalCompany");

    window.openModal = function(companyName) {
        modalCompanyInput.value = companyName;
        modal.style.display = "block";
    }

    window.closeModal = function() {
        modal.style.display = "none";
    }

    window.sendChoice = function(level) {
        var company = modalCompanyInput.value;
        alert("Company: " + company + "\nLevel: " + level);
        closeModal();
    }
});

document.addEventListener("DOMContentLoaded", function() {
    const loaderWrapper = document.getElementById('loader-wrapper');
    loaderWrapper.style.display = 'none';
});
