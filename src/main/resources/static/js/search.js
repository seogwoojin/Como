document.addEventListener("DOMContentLoaded", function() {
    const searchInput = document.getElementById("search-input");
    const companyItems = document.querySelectorAll(".company-item");

    searchInput.addEventListener("input", function() {
        const searchText = searchInput.value.toLowerCase();
        companyItems.forEach(item => {
            const companyName = item.querySelector("h3").textContent.toLowerCase();
            if (companyName.includes(searchText)) {
                item.style.display = "block";
            } else {
                item.style.display = "none";
            }
        });
    });
});
