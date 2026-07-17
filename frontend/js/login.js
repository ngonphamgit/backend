const usernameInput = document.getElementById("username-input");
const passwordInput = document.getElementById("password-input");
const loginButton = document.getElementById("login=button");

loginButton.addEventListener("click", async () => {
    const newUsername = usernameInput.value;
    const newPassword = passwordInput.value;
    const data = {
        "username" : usernameInput.value,
        "password" : passwordInput.value
    };
})

