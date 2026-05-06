# 🏭 Smart Warehouse AI - Slotting Optimization System

![Java](https://img.shields.io/badge/Java-17-orange.svg)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.2+-brightgreen.svg)
![Python](https://img.shields.io/badge/Python-3.10+-blue.svg)
![FastAPI](https://img.shields.io/badge/FastAPI-0.100+-teal.svg)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15+-blue.svg)

A full-stack, microservice-based Warehouse Management System (WMS) that leverages **Machine Learning (Market Basket Analysis)** to optimize product placement on the warehouse floor.

---

## 📖 What is this project and why is it needed?

**The Problem:** 
In traditional warehouses, order pickers spend up to 60% of their working time simply walking between distant racks to collect items for a single order. If a customer frequently buys "Motor Oil" and an "Oil Filter", but they are stored on opposite sides of the warehouse, the company loses time and money.

**The Solution (This Project):** 
This system automates **Warehouse Slotting**. It acts as a smart brain for the warehouse:
1. It records every order created by users.
2. An AI Microservice analyzes the historical order data to find hidden patterns (which items are frequently bought together).
3. It automatically generates a new, optimized warehouse layout where correlated items are placed in adjacent racks, dramatically reducing the picker's travel time.

---

## 🚀 How it Works (Under the Hood)

The project consists of three independent parts communicating with each other:

1. **The Core Backend (Java Spring Boot):** Acts as the central nervous system. It manages the inventory, processes transactional orders (ensuring no data is lost), and saves everything to a PostgreSQL database.
2. **The AI Engine (Python FastAPI):** Acts as the analytical brain. It connects to the database, extracts order history, builds a co-occurrence matrix using Pandas, and clusters products based on purchase frequency.
3. **The Web Dashboard (HTML/JS/Bootstrap):** The user interface where managers can create orders and visualize the AI's recommended warehouse topology in real-time.

---

## 🛠️ Technology Stack

* **Backend:** Java 17, Spring Boot, Spring Data JPA, Hibernate
* **Database:** PostgreSQL
* **AI Microservice:** Python 3, FastAPI, Uvicorn, Pandas, Scikit-learn, Psycopg2
* **Frontend:** HTML5, Vanilla JavaScript (Fetch API), Bootstrap 5, CSS3

---

## ⚙️ Step-by-Step Setup Guide

Follow these steps to run the project locally on your machine.

### Prerequisites
Make sure you have installed:
* [Java Development Kit (JDK) 17+](https://adoptium.net/)
* [Python 3.10+](https://www.python.org/downloads/)
* [PostgreSQL](https://www.postgresql.org/download/)
* [VS Code](https://code.visualstudio.com/) (with the "Live Server" extension)

### Step 1: Database Setup
1. Open pgAdmin or your PostgreSQL terminal.
2. Create a new empty database:

       CREATE DATABASE smart_warehouse;

*(Note: You do not need to create tables manually. Spring Boot will auto-generate them upon startup).*

### Step 2: Start the Java Core Service
1. Open the `demo` (Java) folder in your IDE (IntelliJ IDEA or Eclipse).
2. Open `src/main/resources/application.properties` and update your database password:

       spring.datasource.password=YOUR_POSTGRES_PASSWORD

3. Run the `DemoApplication.java` file.
4. *The server will start on `http://localhost:8080`.*

### Step 3: Start the Python AI Service
1. Open the `smart-warehouse-ai` folder in a terminal.
2. Install the required libraries:

       pip install fastapi uvicorn pandas numpy psycopg2-binary

3. Open `main.py` and update the `DB_PARAMS` dictionary with your PostgreSQL password.
4. Start the AI server:

       python -m uvicorn main:app --reload --port 8000

5. *The AI server will start on `http://localhost:8000`.*

### Step 4: Launch the User Interface
1. Open the `smart-warehouse-web` folder in VS Code.
2. Right-click on the `index.html` file and select **"Open with Live Server"**.
3. Your browser will automatically open the dashboard (usually on `http://127.0.0.1:5500`).

---

## 🎮 How to use the App (Demo Scenario)

To see the AI in action, follow this simple scenario:

1. **Populate Database:** Use the provided PowerShell/Postman scripts to add 20 products to the database.
2. **Create Orders:** Go to the **Order Assembly** tab in the web interface. Create a few logical orders (e.g., add "Brake Pads" and "Brake Fluid" to the same cart and submit).
3. **Run AI Optimization:** Go to the **Topology & AI** tab. Click the blue **"Run AI Analysis"** button.
4. **See the Magic:** The grid will instantly update. Items that you bought together will be placed side-by-side and highlighted in green, along with an AI-generated explanation report.

---

## 👨‍💻 Developer
**[Holovanov Oleksandr]**  
*IT Student | Backend Developer | Aspiring Software Engineer*