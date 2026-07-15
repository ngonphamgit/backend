const productImage = document.getElementById("product-image");
const productName = document.getElementById("product-name");
const productPrice = document.getElementById("product-price");
const productDesc = document.getElementById("product-desc");
const stockText = document.getElementById("stock-text");

function displayProduct(product)
{
    productImage.src = "https://picsum.photos/300";
    productName.textContent = product.name;
    productPrice.textContent = "$" + product.price;
    productDesc.textContent = product.description;
    stockText.textContent = "Current stock: " + product.quantity;
}