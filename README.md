# 🏭 Smart Warehouse AI - Cloud-Native Slotting Optimization System

![Java](https://img.shields.io/badge/Java-17-orange.svg?style=flat-square&logo=java)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.2+-brightgreen.svg?style=flat-square&logo=spring)
![Python](https://img.shields.io/badge/Python-3.10+-blue.svg?style=flat-square&logo=python)
![FastAPI](https://img.shields.io/badge/FastAPI-0.100+-teal.svg?style=flat-square&logo=fastapi)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15+-blue.svg?style=flat-square&logo=postgresql)
![Docker](https://img.shields.io/badge/Docker-Enabled-2496ED.svg?style=flat-square&logo=docker)
![Render](https://img.shields.io/badge/Deployed_on-Render-black.svg?style=flat-square&logo=render)

A full-stack, cloud-native Warehouse Management System (WMS) built on a **Microservices Architecture**. It leverages **Machine Learning (Market Basket Analysis)** to dynamically optimize product placement on the warehouse floor based on real-time order history.

🌐 **[Live Demo: Smart Warehouse AI](https://smart-warehouse-web.onrender.com)**

---

## 📖 The Problem & The AI Solution

**The Problem:** In traditional warehouses, order pickers spend up to 60% of their working time simply walking between distant racks to collect items for a single order. If a customer frequently buys "Motor Oil" and an "Oil Filter", but they are stored on opposite sides of the warehouse, the company loses significant time and money.

**The Solution:** This system automates **Warehouse Slotting**. It acts as a smart brain for the warehouse:
* It securely records every order into a cloud database.
* An independent AI Microservice analyzes historical order data to find hidden correlation patterns.
* It automatically generates an optimized warehouse layout, grouping frequently bought items in adjacent racks, reducing picker travel time by up to 20%.

---

## 🏗️ Cloud & Microservices Architecture

This project is built as a fully deployed **Cloud-Native Application**, consisting of interconnected components:

1. **The Core Backend (Java Spring Boot - Dockerized) ☕**
   Acts as the transactional system. Manages inventory, securely processes orders, and exposes REST APIs. Containerized via **Docker** and deployed on Render.
   
2. **The AI Engine (Python FastAPI) 🐍**
   Acts as the analytical microservice. Queries the database directly, builds a co-occurrence matrix using **Pandas & Numpy**, and clusters products.

3. **Cloud Database (PostgreSQL on Neon.tech) 🐘**
   A scalable serverless Postgres database hosted in Frankfurt, ensuring fast data synchronization between the Java and Python microservices.

4. **The Web Dashboard (HTML/JS/Bootstrap) 🌐**
   A responsive frontend hosted as a Static Site that communicates with both backend services via the Fetch API.

---

## 🛠️ Technology Stack

* **Backend:** Java 17, Spring Boot, Spring Data JPA, Hibernate
* **AI / Data Science:** Python 3.10+, FastAPI, Uvicorn, Pandas, Numpy, Psycopg2
* **Database:** PostgreSQL (Cloud instance via Neon.tech)
* **Frontend:** HTML5, Vanilla JavaScript (Fetch API), Bootstrap 5, CSS3
* **DevOps & Deployment:** Docker, Render.com (CI/CD Pipeline)

---

## 🌐 Live Demo Scenario (How to Test)

Want to see the AI in action? Visit the [Live Demo](https://smart-warehouse-web.onrender.com) and follow these steps:

**1. Clean Slate**
Go to the **Topology & AI** tab and click the red `Clear Warehouse` button. This performs a hard reset of the database orders via a direct SQL Truncate command.

**2. Create Patterns**
Go to the **Order Assembly** tab. Create a few orders that make logical sense. For example:
* *Order 1:* Brake Pads + Brake Fluid
* *Order 2:* Motor Oil + Oil Filter
* *Order 3:* Brake Pads + Brake Fluid

**3. Run AI Optimization**
Return to the **Topology & AI** tab and click `Run AI Analysis`. The Python microservice will analyze the data on the fly. 

**4. See the Magic**
The grid will instantly update, placing your frequently bought items side-by-side, highlighted in green, accompanied by an AI-generated topology report.
*(Note: Since the backend services are hosted on Render's free tier, the very first request might take 30-50 seconds to wake the servers up).*

---

## ⚙️ Step-by-Step Local Setup Guide

If you want to run this project locally on your machine, follow these instructions.

### Prerequisites
* Java Development Kit (JDK) 17+
* Python 3.10+
* IDE of your choice (IntelliJ IDEA, VS Code)

### Step 1: Database Setup
You can use a local PostgreSQL instance or a free cloud database like Neon.tech. Create a new empty database. You do not need to create tables manually; Spring Boot will auto-generate them upon startup (`ddl-auto=update`).

### Step 2: Start the Java Core Service
Open the `demo` folder in your IDE. Open the application properties file (`src/main/resources/application.properties`) and update your database credentials:

```properties
spring.datasource.url=jdbc:postgresql://YOUR_DB_URL
spring.datasource.username=YOUR_USER
spring.datasource.password=YOUR_PASSWORD
```

Run `DemoApplication.java`. The Java server will start on `http://localhost:8080`.

### Step 3: Start the Python AI Service
Open the `smart-warehouse-ai` folder in a terminal and install the required libraries:

```bash
pip install -r requirements.txt
```

Open `main.py` and update the `DATABASE_URL` variable with your PostgreSQL connection string. Then, start the AI server:

```bash
uvicorn main:app --reload --port 10000
```

The AI server will start on `http://localhost:10000`.

### Step 4: Launch the User Interface
Before running locally, ensure the `fetch` API URLs in `warehouse.html` and other JS files point to your `localhost` instead of the Render cloud URLs. Then, open the `smart-warehouse-web` folder in VS Code, right-click on `index.html`, and select **"Open with Live Server"**.

---

## 👨‍💻 Developer
**[Holovanov Oleksandr]** *IT Student | Backend Developer | Aspiring Software Engineer*