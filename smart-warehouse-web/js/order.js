document.addEventListener('DOMContentLoaded', async () => {
    const productSelect = document.getElementById('productSelect');

    try {
        // 1. Делаем РЕАЛЬНЫЙ запрос к Java-серверу
        const response = await fetch('http://localhost:8080/api/products');
        
        if (!response.ok) {
            throw new Error("Ошибка сети");
        }
        
        const products = await response.json();

        // 2. Очищаем выпадающий список (убираем надпись Loading...)
        productSelect.innerHTML = '<option value="" disabled selected>Choose a product...</option>';

        // 3. Заполняем список товарами из базы данных
        products.forEach(product => {
            const option = document.createElement('option');
            // В value мы прячем ID товара (он понадобится для отправки заказа)
            option.value = product.id; 
            // А пользователю показываем красивый текст
            option.textContent = `${product.sku} - ${product.name}`;
            productSelect.appendChild(option);
        });

    } catch (error) {
        console.error('Ошибка загрузки товаров:', error);
        productSelect.innerHTML = '<option value="" disabled>Error connecting to server</option>';
    }
});