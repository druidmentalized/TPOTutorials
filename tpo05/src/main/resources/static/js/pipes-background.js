const container = document.getElementById("background-container");
const directions = ["to-right", "to-left", "to-top", "to-bottom"];
const colors = ["#b3e5fc", "#81d4fa", "#4fc3f7", "#29b6f6", "#03a9f4"];

function spawnPipe() {
    const wrapper = document.createElement("div");
    wrapper.classList.add("pipe-wrapper");

    const pipe = document.createElement("div");
    pipe.classList.add("pipe");

    const direction = directions[Math.floor(Math.random() * directions.length)];
    pipe.classList.add(direction);

    const color = colors[Math.floor(Math.random() * colors.length)];
    pipe.style.backgroundColor = color;

    if (direction === "to-left" || direction === "to-right") {
        wrapper.style.top = Math.random() * 100 + "vh";
        direction === "to-right"
            ? wrapper.style.left = "0"
            : wrapper.style.right = "0";
    } else {
        wrapper.style.left = Math.random() * 100 + "vw";
        direction === "to-bottom"
            ? wrapper.style.top = "0"
            : wrapper.style.bottom = "0";
    }

    wrapper.appendChild(pipe);
    container.appendChild(wrapper);

    setTimeout(() => wrapper.remove(), 11000);
}

setInterval(spawnPipe, 500);