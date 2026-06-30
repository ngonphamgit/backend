const searchButton = document.getElementById("search-button");
const searchBar = document.getElementById("search-bar");

const productCardTemplate = document.getElementById("product-card-template");
const productCards = document.getElementById("product-cards");

const pageNumber = document.getElementById("page-number");
const nextPageButton = document.getElementById("next-page-button");
const previousPageButton = document.getElementById("previous-page-button");

let productPage = 0;
let query = "";
let size = 10;

async function fetchProducts(query, page, size)
{
    console.log(query);
    const response = await fetch(`http://localhost:8080/products/search?query=${encodeURIComponent(query)}&page=${page}&size=${size}`);
    const data = await response.json();
    return data;
}

function displayProducts(products)
{
    console.log(products);
    try
    {
        for (const product of products.content)
        {
            const newCard = productCardTemplate.content.cloneNode(true);

            newCard.querySelector("img").src = "https://picsum.photos/175";
            newCard.querySelector("h2").textContent = product.name;
            newCard.querySelector("p").textContent = product.description;
            newCard.querySelector("h3").textContent = product.price;
            
            productCards.appendChild(newCard);
        }
    }
    catch (error)
    {
        console.log("Error: ", error)
    }
}

async function performSearch()
{
    query = searchBar.value.trim();
    productPage = 0;
    if (window.location.pathname === "/" || window.location.pathname.endsWith("/index.html"))
    {
        
        window.location.href = `productsListing.html?query=${encodeURIComponent(query)}&page=${productPage}&size=10`;
    }
    else
    {
        const url = new URL(window.location);
        url.searchParams.set("query", query);
        url.searchParams.set("page", productPage);
        window.history.pushState({}, "", url);

        productCards.innerHTML = "";
        displayProducts(await fetchProducts(query, productPage, size));
        pageNumber.textContent = productPage + 1;
    }
}

searchButton.addEventListener("click", performSearch);

nextPageButton.addEventListener("click", async () => {
    productPage++;

    const url = new URL(window.location);
    url.searchParams.set("page", productPage);
    window.history.pushState({}, "", url);

    const products = await fetchProducts(query, productPage, size);
    if (products.content.length == 0) {productPage--; return;}

    productCards.innerHTML = "";
    displayProducts(await fetchProducts(query, productPage, size));
    pageNumber.textContent = productPage + 1;
})

previousPageButton.addEventListener("click", async () => {
    if (productPage == 0) {return;}

    productPage--;

    const url = new URL(window.location);
    url.searchParams.set("page", productPage);
    window.history.pushState({}, "", url);

    productCards.innerHTML = "";
    displayProducts(await fetchProducts(query, productPage, size));
    pageNumber.textContent = productPage + 1;
})