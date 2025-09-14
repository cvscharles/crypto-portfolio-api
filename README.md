# 🚀 Crypto Portfolio API

A Spring Boot REST API for managing cryptocurrency holdings, calculating portfolio value, and retrieving filtered data. Built with **Java, Spring Boot, Spring Data JPA**, and **MySQL**, with **Swagger/OpenAPI documentation.**

## 🗒 Background / User Story

As an active cryptocurrency holder, I wanted a simple way to track all my holdings in one place. Previously, I relied on a spreadsheet — **manually** entering quantities, checking **multiple** exchanges for the latest prices, and then calculating the total portfolio value **by hand**.

This process was: 
* ⏳ **Time‑consuming** — switching between tabs and apps 
* ⚠️ **Error‑prone** — easy to mistype numbers or forget to update prices
* 📉 **Inefficient** — no quick way to see my portfolio’s real‑time value

I created the Crypto Portfolio API to solve this problem through: 
* 📊 Storing all holdings in a **central database**
* 🔄 **Easily** calculating total portfolio value 
* 🔍 Retrieving holdings **instantly**, with **filtering** if necessary 
* 🌐 Accessing everything through a **clean, documented REST API**

Now, instead of juggling spreadsheets and exchange tabs, I can get my portfolio’s value in a **single** API call!

---

## ✨ Features

* 📊 **CRUD operations** for holdings (Create, Read, Update, Delete) 
* 💰 **Portfolio valuation** using current market prices obtained from CoinGecko's public API 
* 🔍 **Filtered GET endpoint** using a Spring Data JPA derived query
* 📜 **Swagger UI** for interactive API documentation 
* 🗄 **Database dump file** for easy setup with sample data

---

## 🛠 Tech Stack

- ☕ Java 21 
- 🌱 Spring Boot 3.5.4 
- 🗃 Spring Data JPA 
- 🐬 MySQL 8 
- 📦 Maven 
- 📖 Springdoc-openapi 2.8.13

---

## 🚦 Getting Started

#### 1️⃣ Clone the repository

`git clone https://github.com/your-username/crypto-portfolio-api.git`

`cd crypto-portfolio-api`

#### 2️⃣ Database setup

- Create a new MySQL database:

`CREATE DATABASE crypto_portfolio;`

- Import the dump file from the project root: 

`mysql -u root -p crypto_portfolio < crypto_portfolio_dump.sql`

- Update your local `src/main/resources/application.properties` **(not committed to Git)** with your DB credentials

#### 3️⃣ Run the application

`./mvnw spring-boot:run`

**The API will start at: http://localhost:8082**

---

## 📚 API Documentation (Swagger UI)

Once the app is running, open:

**http://localhost:8082/swagger-ui/index.html**

From here you can: 
- 📄 View all endpoints 
- 📝 See parameter descriptions and example requests/responses 
- ▶️ Try out API calls directly from the browser

---

## 🔑 Endpoint Overview

| Method | Endpoint                                         | Description                                           |
|--------|--------------------------------------------------|-------------------------------------------------------|
| GET    | `/api/holdings/allholdings`                      | Shows all holdings                                    |
| GET    | `/api/holdings/{name}`                           | Gets a holding by name (filtered via JPA query)       |
| GET    | `/api/holdings/portfolio`                        | Gets total portfolio value                            |
| POST   | `/api/holdings/create`                           | Creates a new holding record                          |
| POST   | `/api/holdings/updateholding`                    | Updates a holding's quantity                          |
| DELETE | `/api/holdings/deleteholding?name=Bitcoin`       | Deletes a holding by name                             |


---

## 📌 Example Requests

#### ➕ Create Holding

`POST /api/holdings/create` 

`Content-Type: application/json`

```{ "name": "Bitcoin", "quantity": 1.5, "purchaseDate": "2024-05-10" }```

#### ✏️ Update Holding

`POST /api/holdings/updateholding `

`Content-Type: application/json`

`{ "name": "Bitcoin", "quantity": 2.0 }`

#### 🗑 Delete Holding

`DELETE /api/holdings/deleteholding?name=Bitcoin`

---

## 🧪 Running Tests

`./mvnw test`

 At least one unit test **(ValuationServiceTest)** is included and passes ✅

 ---

## 🔒 Security

🛡 **application.properties** is in **.gitignore** to protect credentials 

🔑 No secrets are committed to the repository

---

## 🔮 Future Improvements & Roadmap

This project is **Version 1** — intentionally built to be the most efficient, simple, and functional version possible for the assessment. I focused on delivering a working, clean, and maintainable prototype without unnecessarily over‑complicating the initial build.

With more time, I would expand the API with the following enhancements:

#### 💹 Profit & Loss (P/L): 
Calculation Compare the purchase price of each asset (from the purchase date) with its current market price. Show both absolute P/L (in currency) and percentage change for each holding. Provide a portfolio‑wide P/L summary so users can instantly see overall gains or losses.

#### 🥧 Portfolio Exposure:
 Analysis Calculate the percentage share of each asset relative to the total portfolio value. Present this as a breakdown *(e.g., BTC = 45%, ETH = 30%, ADA = 25%)*. Enable users to quickly assess diversification and risk concentration.

#### 🛡 More robust data validation: 
Enforce stricter input rules (e.g., quantity > 0, valid date formats, asset name constraints). Provide clearer, user‑friendly error messages.

#### 🧪 Comprehensive testing:
 Add more unit tests to cover edge cases. Introduce integration tests to verify end‑to‑end behaviour between layers.

#### 👥 Multi‑user support:
 Implement authentication & authorisation. Allow each user to manage their own portfolio securely.

#### 🏦 Asset class inheritance:
 Create a base Asset class with shared fields. Extend into subclasses like *Cryptocurrency, Stock, Bond, etc.*, enabling a more diverse portfolio.

#### 🗄 More robust database design:
 Add relational tables for asset types, transactions, and historical prices. Optimise queries for performance at scale.

#### 📈 These improvements would transform the API from a single‑user crypto tracker into a scalable, multi‑asset portfolio management platform.
