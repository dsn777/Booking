const btnDescriptions = document.querySelectorAll('.btn-description');
btnDescriptions.forEach(btn => {
    btn.addEventListener('click', () => {
        const roomCard = btn.closest('.room-card');
        const descriptionPopup = roomCard.querySelector('.description-popup');
        descriptionPopup.style.display = 'block';
        
        let currentImage = 0;
        const images = roomCard.querySelectorAll('.carousel img');
        images[currentImage].style.display = 'block';

        nextButton = roomCard.querySelector('.next');
        prevButton = roomCard.querySelector('.prev');

        if (images.length == 1) {
            nextButton.style.display = 'none';
            prevButton.style.display = 'none';
        } else {
            nextButton.addEventListener('click', () => {
                for (let i = 0; i < images.length; i++) {
                    images[i].style.display = 'none';
                }
                //images[currentImage].style.display = 'none';
                currentImage = (currentImage + 1) % images.length;
                images[currentImage].style.display = 'block';
            });

            prevButton.addEventListener('click', () => {
                for (let i = 0; i < images.length; i++) {
                    images[i].style.display = 'none';
                }

                currentImage = (currentImage - 1 + images.length) % images.length;
                images[currentImage].style.display = 'block';
            });
        }
    });
});

const btnCloses = document.querySelectorAll('.close');
btnCloses.forEach(btn => {
    btn.addEventListener('click', () => {
        const descriptionPopup = btn.closest('.description-popup');
        const roomCard = btn.closest('.room-card');

        const images = roomCard.querySelectorAll('.carousel img');
        for (let i = 0; i < images.length; i++) {
            images[i].style.display = 'none';
        }

        descriptionPopup.style.display = 'none';
    });
});

/*
const roomCards = document.querySelectorAll('.room-card');
 roomCards.forEach(card => {
  card.addEventListener('click', () => {
       const button = card.querySelector('.btn-description');
       button.click();
   });
 });*/
