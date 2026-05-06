document.addEventListener('DOMContentLoaded', async () => {
    const productSelect = document.getElementById('productSelect');

    try {
        const response = await fetch('http://localhost:8080/api/products');
        
        if (!response.ok) {
            throw new Error("Network error");
        }
        
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