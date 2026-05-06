document.addEventListener("DOMContentLoaded", () => {
    
    // 1. Генерируем "фейковую" карту склада (20 стеллажей)
    const mapContainer = document.getElementById("warehouseMap");
    for (let i = 1; i <= 20; i++) {
        const cell = document.createElement("div");
        cell.className = "grid-cell";
        cell.id = `cell-${i}`;
        cell.innerText = `Rack ${i}`;
        mapContainer.appendChild(cell);
    }

    // 2. Обработка нажатия на кнопку "Run Analysis"
    document.getElementById("runAiBtn").addEventListener("click", () => {
        const btn = document.getElementById("runAiBtn");
        const loading = document.getElementById("aiLoading");
        const list = document.getElementById("recommendationsList");

        btn.disabled = true;
        loading.classList.remove("d-none");
        list.innerHTML = ""; // Очищаем старый список

        // Делаем запрос к PYTHON-сервису
        fetch("http://127.0.0.1:8000/api/ai/recommend-placement")
            .then(response => response.json())
            .then(data => {
                loading.classList.add("d-none");
                btn.disabled = false;

                // Проверяем, есть ли рекомендации
                if (data.status === "error" || !data.recommendations || data.recommendations.length === 0) {
                    list.innerHTML = `<li class="list-group-item bg-dark text-warning">Not enough order data to find patterns. Create more mixed orders!</li>`;
                    return;
                }

                // Отрисовываем каждую рекомендацию
                data.recommendations.forEach((rec, index) => {
                    const li = document.createElement("li");
                    li.className = "list-group-item bg-dark text-light border-secondary d-flex justify-content-between align-items-center";
                    // Добавляем эффект при наведении мыши
                    li.style.cursor = "pointer";
                    li.onmouseover = () => li.style.backgroundColor = "#343a40";
                    li.onmouseout = () => li.style.backgroundColor = "";
                    
                    li.innerHTML = `
                        <div>
                            <strong>${rec.target_product}</strong> <br>
                            <small class="text-muted">Place near: <span class="text-info">${rec.recommend_to_place_near}</span></small>
                        </div>
                        <span class="badge bg-success rounded-pill" title="Co-occurrence score">Score: ${rec.co_occurrences_count}</span>
                    `;

                    // При клике на рекомендацию - подсвечиваем ячейки на карте
                    li.addEventListener("click", () => {
                        highlightGrid(index);
                    });

                    list.appendChild(li);
                });
            })
            .catch(error => {
                console.error("AI Error:", error);
                loading.classList.add("d-none");
                btn.disabled = false;
                list.innerHTML = `<li class="list-group-item bg-dark text-danger">Failed to connect to Python AI on port 8000. Is it running?</li>`;
            });
    });
});

// Функция для визуальной подсветки ячеек склада
function highlightGrid(index) {
    // Сначала сбрасываем цвета со всех ячеек
    document.querySelectorAll(".grid-cell").forEach(cell => {
        cell.classList.remove("highlight-target", "highlight-adjacent");
    });

    // Для демонстрации ИИ выбираем две соседние ячейки (математически на основе индекса списка)
    // В реальном проекте тут бы использовались реальные ID ячеек из Java базы
    const cellA = (index * 3) % 19 + 1; 
    let cellB = cellA + 1;
    
    // Подсвечиваем
    document.getElementById(`cell-${cellA}`).classList.add("highlight-target");
    document.getElementById(`cell-${cellB}`).classList.add("highlight-adjacent");
}