function toggleInfo() {
    const info = document.getElementById('additional-services');
    if (info.style.display === "none" || info.style.display === "") {
        info.style.display = "block"; // Показываем информацию
    } else {
        info.style.display = "none"; // Скрываем информацию
    }
    const triangle = document.getElementById('triangle');
    triangle.classList.toggle('rotated');
}
