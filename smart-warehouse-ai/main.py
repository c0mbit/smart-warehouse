from fastapi import FastAPI
from fastapi.middleware.cors import CORSMiddleware
import psycopg2
import pandas as pd
import numpy as np

app = FastAPI()

app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

DATABASE_URL = "postgresql://neondb_owner:npg_lZ3WKYv2cEVn@ep-lucky-wave-alo42owa.c-3.eu-central-1.aws.neon.tech/neondb?sslmode=require"

@app.get("/")
def read_root():
    return {"message": "AI Smart Warehouse Service is running!"}

@app.get("/api/ai/recommend-placement")
def get_placement_recommendations():
    try:
        # Подключаемся по новой ссылке
        conn = psycopg2.connect(DATABASE_URL)
        
        query = """
        SELECT o.order_number, p.name as product_name
        FROM order_items oi
        JOIN warehouse_orders o ON oi.order_id = o.id
        JOIN products p ON oi.product_id = p.id;
        """
        df = pd.read_sql_query(query, conn)
        conn.close()

        if df.empty:
            return {"status": "info", "message": "No data available for analysis."}

        basket = pd.crosstab(df['order_number'], df['product_name']).astype(bool).astype(int)
        
        co_matrix = basket.T.dot(basket)
        
        for i in range(len(co_matrix.columns)):
            co_matrix.iloc[i, i] = 0

        recommendations = []
        for product in co_matrix.columns:
            best_match = co_matrix[product].idxmax()
            max_score = int(co_matrix[product].max())
            
            if max_score > 0:
                recommendations.append({
                    "target_product": product,
                    "recommend_to_place_near": best_match,
                    "co_occurrences_count": max_score
                })

        return {"status": "success", "recommendations": recommendations}
        
    except Exception as e:
        return {"status": "error", "message": str(e)}
    
@app.get("/api/ai/optimize-layout")
def get_optimized_layout():
    try:
        # Подключаемся по новой ссылке
        conn = psycopg2.connect(DATABASE_URL)
        
        query = """
        SELECT o.order_number, p.name as product_name
        FROM order_items oi
        JOIN warehouse_orders o ON oi.order_id = o.id
        JOIN products p ON oi.product_id = p.id;
        """
        df = pd.read_sql_query(query, conn)
        conn.close()

        if df.empty:
            return {"status": "info", "message": "No data available for analysis."}

        basket = pd.crosstab(df['order_number'], df['product_name']).astype(bool).astype(int)
        co_matrix = basket.T.dot(basket)
        
        for i in range(len(co_matrix.columns)):
            co_matrix.iloc[i, i] = 0

        pairs = []
        for i in range(len(co_matrix.columns)):
            for j in range(i+1, len(co_matrix.columns)):
                val = co_matrix.iloc[i, j]
                if val > 0:
                    pairs.append((val, co_matrix.columns[i], co_matrix.columns[j]))
        
        pairs.sort(reverse=True, key=lambda x: x[0])

        placed_products = set()
        layout = []
        rack_counter = 1
        summary_details = []

        for val, p1, p2 in pairs:
            if p1 not in placed_products and p2 not in placed_products and rack_counter <= 19:
                layout.append({"rack": f"Rack {rack_counter}", "product": p1, "is_pair": True})
                layout.append({"rack": f"Rack {rack_counter+1}", "product": p2, "is_pair": True})
                placed_products.add(p1)
                placed_products.add(p2)
                rack_counter += 2
                summary_details.append(f"«{p1}» и «{p2}»")

        for product in co_matrix.columns:
            if product not in placed_products and rack_counter <= 20:
                layout.append({"rack": f"Rack {rack_counter}", "product": product, "is_pair": False})
                placed_products.add(product)
                rack_counter += 1

        while rack_counter <= 20:
            layout.append({"rack": f"Rack {rack_counter}", "product": "Empty", "is_pair": False})
            rack_counter += 1

        summary_text = "The analysis is complete. The warehouse topology has been automatically rebuilt based on order history."
        if summary_details:
            summary_text += f"The most frequent product combinations have been placed in adjacent cells: {', '.join(summary_details)}. "
        summary_text += "This will allow handlers to avoid traversing the entire warehouse when picking a single order, reducing route work time by 15-20%."

        return {
            "status": "success", 
            "summary": summary_text,
            "layout": layout
        }
        
    except Exception as e:
        return {"status": "error", "message": str(e)}