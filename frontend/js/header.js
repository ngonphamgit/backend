let homeButton;
let authButtons;
let profileButtons;
let logoutButton;

async function initialize()
{
    await window.headerLoaded;

    homeButton = document.getElementById("home-button");
    authButtons = document.getElementById("auth-buttons");
    profileButtons = document.getElementById("profile-buttons");
    logoutButton = document.getElementById("logout-button");

    if (localStorage.getItem("jwt") === "") //if user is not logged in, hide user profile buttons
    {
        profileButtons.classList.toggle("hidden");
    }
    else
    {
        authButtons.classList.toggle("hidden");
    }

    homeButton.addEventListener("click", () => {
        window.location.href = "index.html";
    })

    logoutButton.addEventListener("click", () => {
        localStorage.setItem("jwt", "");
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