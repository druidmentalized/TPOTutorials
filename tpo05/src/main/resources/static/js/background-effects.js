const colorClasses = ["color1", "color2", "color3", "color4", "color5"];

function getRandomItem(array) {
    return array[Math.floor(Math.random() * array.length)];
}

function getContainer(containerId = "background-container") {
    return document.getElementById(containerId);
}

function removeAfterDelay(element, delayMs) {
    setTimeout(() => {
        element.remove();
    }, delayMs);
}

function startBubbles(containerId = "background-container", spawnIntervalMs = 500) {
    const container = getContainer(containerId);

    function spawnBubble() {
        const bubble = document.createElement("div");
        bubble.classList.add("bubble");

        const size = Math.random() * 50 + 20;
        bubble.style.width = size + "px";
        bubble.style.height = size + "px";

        bubble.style.left = Math.random() * 100 + "vw";

        const duration = 10 + Math.random() * 10;
        bubble.style.animationDuration = duration + "s";
        bubble.style.animationDelay = Math.random() * 5 + "s";

        bubble.classList.add(getRandomItem(colorClasses));

        container.appendChild(bubble);

        removeAfterDelay(bubble, (duration + 5) * 1000);
    }

    spawnBubble();
    setInterval(spawnBubble, spawnIntervalMs);
}

function startPipes(containerId = "background-container", spawnIntervalMs = 300) {
    const container = getContainer(containerId);
    const directions = ["to-right", "to-left", "to-top", "to-bottom"];

    function spawnPipe() {
        const wrapper = document.createElement("div");
        wrapper.classList.add("pipe-wrapper");

        const pipe = document.createElement("div");
        pipe.classList.add("pipe");

        const direction = getRandomItem(directions);
        pipe.classList.add(direction);

        pipe.classList.add(getRandomItem(colorClasses));

        if (direction === "to-left" || direction === "to-right") {
            wrapper.style.top = Math.random() * 100 + "vh";
            direction === "to-right"
                ? (wrapper.style.left = "0")
                : (wrapper.style.right = "0");
        } else {
            wrapper.style.left = Math.random() * 100 + "vw";
            direction === "to-bottom"
                ? (wrapper.style.top = "0")
                : (wrapper.style.bottom = "0");
        }

        wrapper.appendChild(pipe);
        container.appendChild(wrapper);

        removeAfterDelay(wrapper, 11000);
    }

    setInterval(spawnPipe, spawnIntervalMs);
}

function startTriangles(containerId = "background-container", spawnIntervalMs = 200) {
    const container = getContainer(containerId);
    const edges = ["from-top", "from-bottom", "from-left", "from-right"];
    const sizes = ["small", "medium", "large"];

    function spawnTriangle() {
        const outer = document.createElement("div");
        outer.classList.add("triangle-outer");

        const edge = getRandomItem(edges);
        outer.classList.add(edge);

        if (edge === "from-top") {
            outer.style.left = `calc(${Math.random() * 100}% - 30px)`;
            outer.style.top = `-60px`;
        } else if (edge === "from-bottom") {
            outer.style.left = `calc(${Math.random() * 100}% - 30px)`;
            outer.style.top = `100vh`;
        } else if (edge === "from-left") {
            outer.style.top = `calc(${Math.random() * 100}% - 30px)`;
            outer.style.left = `-60px`;
        } else {
            outer.style.top = `calc(${Math.random() * 100}% - 30px)`;
            outer.style.left = `100vw`;
        }

        const rotation = Math.floor(Math.random() * 360);
        outer.style.transform = `rotate(${rotation}deg)`;

        const triangle = document.createElement("div");
        triangle.classList.add("triangle");

        const spinner = document.createElement("div");
        spinner.classList.add("spinner");

        triangle.classList.add(getRandomItem(sizes), getRandomItem(colorClasses));

        spinner.appendChild(triangle);
        outer.appendChild(spinner);

        const layer = Math.floor(Math.random() * 3);
        outer.style.zIndex = String(layer);

        const duration = (10 + (2 - layer) * 5 + Math.random() * 3).toFixed(2);
        triangle.style.animationDuration = duration + "s";

        const delay = (Math.random() * 3).toFixed(2);
        triangle.style.animationDelay = delay + "s";

        container.appendChild(outer);

        removeAfterDelay(outer, (parseFloat(duration) + parseFloat(delay)) * 1000 + 2000);
    }

    setInterval(spawnTriangle, spawnIntervalMs);
}

window.startBubbles = startBubbles;
window.startPipes = startPipes;
window.startTriangles = startTriangles;