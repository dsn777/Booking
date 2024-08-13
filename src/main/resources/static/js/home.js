document
  .getElementById("bookForm")
  .addEventListener("submit", function (event) {
    // Отменяем стандартную отправку формы
    event.preventDefault();
    // Получаем данные из формы
    let checkin = document.getElementById("checkin").value;
    let checkout = document.getElementById("checkout").value;

    let room_params = "";
    let i = 0;
    const adults = document.querySelectorAll('#rooms .adults');
    adults.forEach(span => {
        room_params = room_params + "&adults" + i++ + "=" + span.textContent;
        console.log(span.textContent);
    });

    i = 0;
    const children = document.querySelectorAll('#rooms .children');
    children.forEach(span => {
        room_params = room_params + "&children" + i++ + "=" + span.textContent;
        console.log(span.textContent);
    });

    // Формируем URL с параметрами
    let url =
      "book/rooms?checkin=" +
      checkin +
      "&checkout=" +
      checkout +
      room_params;
    // Перенаправляем на новую страницу
      window.location.href = url;
  });

document.getElementById("guest-button").addEventListener("click", () => {
  const menu = document.getElementById("guest-menu");
  menu.style.display = menu.style.display === "block" ? "none" : "block";
});

document.getElementById("done-button").addEventListener("click", () => {
  document.getElementById("guest-menu").style.display = "none";
});

document.querySelectorAll(".plus").forEach((button) => {
  button.addEventListener("click", () => {
    const span = button.previousElementSibling;
    span.textContent = parseInt(span.textContent) + 1;
  });
});

document.querySelectorAll(".minus").forEach((button) => {
  button.addEventListener("click", () => {
    const span = button.nextElementSibling;
    if (parseInt(span.textContent) > 0) {
      span.textContent = parseInt(span.textContent) - 1;
    }
  });
});

document.getElementById("add-room").addEventListener("click", () => {
  const rooms = document.getElementById("rooms");
  const roomCount = rooms.children.length + 1;
  const newRoom = document.createElement("div");
  newRoom.className = "room";
  newRoom.setAttribute("data-room", roomCount);
  newRoom.innerHTML = `
                <h4>Номер ${roomCount}</h4>
                <button type="button" class="remove-room">Удалить</button>
                <div class="counter">
                    <label>Взрослые</label>
                    <button type="button" class="minus">-</button>
                    <span class="adults">1</span>
                    <button type="button" class="plus">+</button>
                </div>
                <div class="counter">
                     <label>Дети</label>
                     <button type="button" class="minus">-</button>
                     <span class="children">0</span>
                     <button type="button" class="plus">+</button>
                </div>
            `;
  rooms.appendChild(newRoom);

  newRoom.querySelectorAll(".plus").forEach((button) => {
    button.addEventListener("click", () => {
      const span = button.previousElementSibling;
      span.textContent = parseInt(span.textContent) + 1;
    });
  });

  newRoom.querySelectorAll(".minus").forEach((button) => {
    button.addEventListener("click", () => {
      const span = button.nextElementSibling;
      if (parseInt(span.textContent) > 0) {
        span.textContent = parseInt(span.textContent) - 1;
      }
    });
  });

  newRoom.querySelector(".remove-room").addEventListener("click", () => {
    newRoom.remove();
  });
});

document.querySelectorAll(".remove-room").forEach((button) => {
  button.addEventListener("click", () => {
    button.parentElement.remove();
  });
});
