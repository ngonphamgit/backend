const usernameInput = document.getElementById("username-input");
const emailInput = document.getElementById("email-input");
const passwordInput = document.getElementById("password-input");
const registerButton = document.getElementById("register-button");

registerButton.addEventListener("click", async () => {
    const newUsername = usernameInput.value;
    const newPassword = passwordInput.value;
    const data = {
        "username" : usernameInput.value,
        "email" : emailInput.value,
        "password" : passwordInput.value
    };
})






/*
const token = localStorage.getItem("token");

fetch("/api/orders", {
    headers: {
        Authorization: `Bearer ${token}`
    }
});
*/