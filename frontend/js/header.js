let homeButton;

async function initialize()
{
    await window.headerLoaded;

    homeButton = document.getElementById("home-button");

    homeButton.addEventListener("click", () => {
        window.location.href = "index.html";
    })
}

async function loadHeader() {
    const response = await fetch("header.html");
    const html = await response.text();

    document.getElementById("header").innerHTML = html;
    document.dispatchEvent(new Event("headerLoaded"));
}

window.headerLoaded = loadHeader();

initialize();