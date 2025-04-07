const container = document.getElementById("background-container");
const directions = ["to-right", "to-left", "to-top", "to-bottom"];
const colorClasses = ["color1", "color2", "color3", "color4", "color5"];

function spawnPipe() {
    const wrapper = document.createElement("div");
    wrapper.classList.add("pipe-wrapper");

    const pipe = document.createElement("div");
    pipe.classList.add("pipe");

    const direction = directions[Math.floor(Math.random() * directions.length)];
    pipe.classList.add(direction);

    const colorClass = colorClasses[Math.floor(Math.random() * colorClasses.length)];
    pipe.classList.add(colorClass);

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

setInterval(spawnPipe, 300);