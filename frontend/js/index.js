const specialProductCardTemplate = document.getElementById("special-product-card-template");
const featuredProducts = document.getElementById("featured-products");
const seasonalProducts = document.getElementById("seasonal-products");

function displaySpecialProducts(products)
{
    console.log(products);
    for (const product of products.content)
    {
        const newCard = specialProductCardTemplate.content.cloneNode(true);

        newCard.querySelector(".product-link").href = `productPage.html?id=${product.id}`;
        newCard.querySelector(".special-product-card-img").src = "https://picsum.photos/175";
        newCard.querySelector(".special-product-card-name").textContent = product.name;
        newCard.querySelector(".special-product-card-price").textContent = "$" + product.price;

        if (product.productType === "FEATURED")
        {
            featuredProducts.querySelector(".special-product-cards").appendChild(newCard);
        }
        else if (product.productType === "SEASONAL")
        {
            seasonalProducts.querySelector(".special-product-cards").appendChild(newCard);
        }
    }
}