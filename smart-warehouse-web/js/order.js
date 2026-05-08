let cart = [];

document.addEventListener('DOMContentLoaded', async () => {
    const productSelect = document.getElementById('productSelect');

    try {
        const response = await fetch('https://smart-warehouse-java.onrender.com/api/products');
        if (!response.ok) throw new Error("Network response was not ok");
        const products = await response.json();

        productSelect.innerHTML = '<option value="" disabled selected>Choose a product...</option>';
        products.forEach(product => {
            const option = document.createElement('option');
            option.value = product.id; 
            option.textContent = `${product.sku} - ${product.name}`;
            productSelect.appendChild(option);
        });
    } catch (error) {
        console.error('Error loading products:', error);
        productSelect.innerHTML = '<option value="" disabled>Error connecting to server</option>';
    }
});

document.getElementById('addItemForm').addEventListener('submit', (e) => {
    e.preventDefault(); 

    const select = document.getElementById('productSelect');
    const qtyInput = document.getElementById('quantityInput');

    const productId = parseInt(select.value);
    const quantity = parseInt(qtyInput.value);
    const productName = select.options[select.selectedIndex].text;

    cart.push({ productId, quantity, productName });

    updateCartUI();

    select.value = "";
    qtyInput.value = 1;
});

function updateCartUI() {
    const tbody = document.getElementById('cartTableBody');
    tbody.innerHTML = ''; 

    cart.forEach(item => {
        tbody.innerHTML += `
            <tr>
                <td>${item.productId}</td>
                <td>${item.productName}</td>
                <td>${item.quantity}</td>
            </tr>
        `;
    });

    const submitBtn = document.getElementById('submitOrderBtn');
    submitBtn.disabled = cart.length === 0;
}

document.getElementById('submitOrderBtn').addEventListener('click', async () => {
    if (cart.length === 0) return;

    const orderNumber = "ORD-" + Math.floor(Math.random() * 100000);

    const payload = {
        orderNumber: orderNumber,
        items: cart.map(item => ({
            productId: item.productId,
            quantity: item.quantity
        }))
    };

    try {
        const response = await fetch('https://smart-warehouse-java.onrender.com/api/orders', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(payload)
        });

        if (response.ok) {
            alert(`✅ Order ${orderNumber} successfully created!`);
            cart = []; 
            updateCartUI();
        } else {
            alert("❌ Failed to create order. Check backend logs.");
        }
    } catch (error) {
        console.error('Error submitting order:', error);
        alert("❌ Error connecting to Java server.");
    }
});