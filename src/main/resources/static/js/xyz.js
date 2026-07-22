fetch("http://localhost:8080/api/products")
.then(response => response.json())
.then(data => {

let output="";

data.forEach(product=>{

output+=`

<div class="card">

<img src="${product.imageUrl}">

<h3>${product.name}</h3>

<p>${product.description}</p>

<h2>₹${product.price}</h2>

<button onclick="addToCart(${product.id})">
Add To Cart
</button>

</div>

`;

});

document.getElementById("products").innerHTML=output;

});