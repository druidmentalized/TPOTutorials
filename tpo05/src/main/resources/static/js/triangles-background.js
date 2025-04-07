const container = document.getElementById("background-container");

const edges = ["from-top", "from-bottom", "from-left", "from-right"];
const sizes = ["small", "medium", "large"];
const colorClasses = ["color1", "color2", "color3", "color4", "color5"];

function spawnTriangle() {
    const outer = document.createElement("div");
    outer.classList.add("triangle-outer");

    const edge = edges[Math.floor(Math.random() * edges.length)];
    outer.classList.add(edge);

    if (edge === "from-top") {
        const leftPercent = Math.random() * 100;
        outer.style.left = `calc(${leftPercent}% - 30px)`;
        outer.style.top = `-60px`;
    } else if (edge === "from-bottom") {
        const leftPercent = Math.random() * 100;
        outer.style.left = `calc(${leftPercent}% - 30px)`;
        outer.style.top = `100vh`;
    } else if (edge === "from-left") {
        const topPercent = Math.random() * 100;
        outer.style.top = `calc(${topPercent}% - 30px)`;
        outer.style.left = `-60px`;
    } else if (edge === "from-right") {
        const topPercent = Math.random() * 100;
        outer.style.top = `calc(${topPercent}% - 30px)`;
        outer.style.left = `100vw`;
    }

    const rotation = Math.floor(Math.random() * 360);
    outer.style.transform = `rotate(${rotation}deg)`;

    const triangle = document.createElement("div");
    triangle.classList.add("triangle");

    const spinner = document.createElement("div");
    spinner.classList.add("spinner");

    triangle.classList.add(
        sizes[Math.floor(Math.random() * sizes.length)],
        colorClasses[Math.floor(Math.random() * colorClasses.length)]
    );

    spinner.appendChild(triangle);
    outer.appendChild(spinner);

    const layer = Math.floor(Math.random() * 3);
    outer.style.zIndex = `${layer}`;

    const duration = (10 + (2 - layer) * 5 + Math.random() * 3).toFixed(2);
    triangle.style.animationDuration = `${duration}s`;

    const delay = (Math.random() * 3).toFixed(2);
    triangle.style.animationDelay = `${delay}s`;

    container.appendChild(outer);

    setTimeout(() => outer.remove(), (parseFloat(duration) + parseFloat(delay)) * 1000 + 2000);
}

setInterval(spawnTriangle, 200);