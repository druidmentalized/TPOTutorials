const bubbleContainer = document.getElementById("background-container");
const colorClasses = ["color1", "color2", "color3", "color4", "color5"];

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

    const colorClass = colorClasses[Math.floor(Math.random() * colorClasses.length)];
    bubble.classList.add(colorClass);

    // Append the bubble to the container
    bubbleContainer.appendChild(bubble);

    setTimeout(() => {
        bubble.remove();
    }, (duration + 5) * 1000);
}

spawnBubble();
setInterval(spawnBubble, 500);