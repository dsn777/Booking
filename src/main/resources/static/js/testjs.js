let currentImage = 0;
const images = document.querySelectorAll('.carousel img');
images[currentImage].style.display = 'block';
document.querySelector('.next').addEventListener('click', () => {
    images[currentImage].style.display = 'none';
    currentImage = (currentImage + 1) % images.length;
    images[currentImage].style.display = 'block';
});

document.querySelector('.prev').addEventListener('click', () => {
    images[currentImage].style.display = 'none';
    currentImage = (currentImage - 1 + images.length) % images.length;
    images[currentImage].style.display = 'block';
});


document.querySelector(".btn-description").addEventListener("click", function() {
    document.querySelector(".description-popup").style.display = "block";
});

document.querySelector(".close").addEventListener("click", function() {
    document.querySelector(".description-popup").style.display = "none";
});
