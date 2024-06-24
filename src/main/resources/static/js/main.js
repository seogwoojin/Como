document.addEventListener('DOMContentLoaded', () => {
    const images = document.querySelectorAll('.clickable-image');
    const resultDiv = document.getElementById('result');

    images.forEach(image => {
        image.addEventListener('click', () => {
            const imageId = image.getAttribute('data-id');
            fetchContent(imageId);
        });
    });

    async function fetchContent(imageId) {
        try {
            const response = await axios.post(`?id=${imageId}`);
            displayContent(response.data);
        } catch (error) {
            resultDiv.textContent = 'An error occurred while fetching the data.';
            console.error('Fetch error:', error);
        }
    }

    function displayContent(data) {
        resultDiv.innerHTML = `
            <p>Name: ${data.name}</p>
            <p>Level: ${data.level}</p>
            <p>Link: <a href="${data.link}" target="_blank">${data.link}</a></p>
        `;
    }
});
