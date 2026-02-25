# 🍔 Food Delivery Platform – Complete Microservices Architecture (Interview Ready)

> 🔥 Complete End-to-End Documentation
> Nothing skipped. Fully structured. Production + Interview focused.

---

# 🧱 1️⃣ Complete Microservices Breakdown

---

## 🔹 1. User Service

### Responsibilities

* Register User
* Login (JWT based authentication)
* Role management:

    * CUSTOMER
    * RESTAURANT
    * DELIVERY
    * ADMIN (optional but recommended)
* Address management (Add / Update / Delete)

### Database

* MySQL

### Key Features

* Password encryption using BCrypt
* JWT token generation
* Role-based access control
* Refresh token support (recommended)

---

## 🔹 2. Restaurant Service

### Responsibilities

* Add restaurant
* Update restaurant details
* Restaurant approval (Admin flow)
* Restaurant listing
* Restaurant status (ACTIVE / INACTIVE)

### Database

* MySQL

### Important

* Only RESTAURANT role can add restaurant
* Only ADMIN can approve

---

## 🔹 3. Menu Service

### Responsibilities

* Add food items
* Update food details
* Update price
* Availability toggle (AVAILABLE / OUT_OF_STOCK)
* Category:

    * Veg
    * Non-Veg

### Database

* MySQL

### Important

* Restaurant can manage only their menu
* Use caching for frequently accessed menu

---

## 🔹 4. Order Service 🔥 (Core Service)

### Responsibilities

* Place order
* Validate menu items
* Calculate total amount
* Maintain order status:

```
PLACED
PREPARING
OUT_FOR_DELIVERY
DELIVERED
CANCELLED
```

* Call Payment Service
* Update status based on events

### Database

* MySQL

### Why Core?

Because it orchestrates:

* Payment
* Delivery
* Notification

---

## 🔹 5. Payment Service

### Responsibilities

* Mock payment integration
* Maintain payment status:

    * INITIATED
    * SUCCESS
    * FAILED
* Publish payment success event to Kafka

### Database

* MySQL

### Important

* Should be idempotent
* Should support retry mechanism

---

## 🔹 6. Delivery Service

### Responsibilities

* Assign delivery partner
* Update delivery status
* Track order location
* Real-time updates

### Database

* MongoDB (better for real-time tracking & flexible schema)

---

## 🔹 7. Notification Service

### Responsibilities

* Send order confirmation
* Send payment success notification
* Send delivery updates
* Kafka consumer

### Database

* Optional (for logging notifications)

---

## 🔹 8. Infrastructure Services

### API Gateway

* Routing
* JWT validation
* Global exception handling
* Rate limiting (optional)

### Service Registry

* Eureka (Service discovery)

### Config Server

* Centralized configuration

### Kafka

* Event-driven communication

### Zipkin

* Distributed tracing

---

# 🔥 2️⃣ Event-Driven Flow (VERY IMPORTANT)

```
User places order →
Order Service creates order →
Order Service calls Payment Service →
Payment success →
Kafka publishes event →
Order Service consumes event →
Order status updated →
Notification Service consumes event →
Notification sent →
Delivery Service assigns partner
```

---

# 🏗️ 3️⃣ Recommended Tech Stack

| Layer             | Technology           |
| ----------------- | -------------------- |
| Backend           | Spring Boot          |
| Service Discovery | Eureka               |
| Gateway           | Spring Cloud Gateway |
| Communication     | REST + Kafka         |
| Security          | JWT                  |
| Database          | MySQL + MongoDB      |
| Caching           | Redis                |
| Containerization  | Docker               |
| Monitoring        | Zipkin               |

---

# 📁 4️⃣ Suggested Project Structure

```
food-delivery-parent
 ├── api-gateway
 ├── service-registry
 ├── config-server
 ├── user-service
 ├── restaurant-service
 ├── menu-service
 ├── order-service
 ├── payment-service
 ├── delivery-service
 └── notification-service
```

### Architecture Style

* Multi-module Maven project
* Database per service pattern
* Event-driven microservices

---

# 🚀 5️⃣ Development Roadmap (Step-by-Step)

---

## ✅ Phase 1 – Basic Microservices Setup

* Create Service Registry (Eureka)
* Create API Gateway
* Create Config Server
* Create User Service
* Implement JWT authentication
* Role-based authorization

---

## ✅ Phase 2 – Core Business Logic

* Implement Restaurant Service
* Implement Menu Service
* Implement Order Service
* Implement Payment Service
* REST-based communication between services

---

## ✅ Phase 3 – Event-Driven Architecture

* Add Kafka
* Publish payment success event
* Consume event in:

    * Order Service
    * Notification Service
* Implement asynchronous status updates

---

## ✅ Phase 4 – Advanced Production Features

* Add Redis caching
* Dockerize all services
* Add Zipkin distributed tracing
* Add Circuit Breaker (Resilience4j)
* Add Retry mechanism
* Add Global Exception handling
* Add Logging (ELK optional)

---

# 🔐 6️⃣ Security Architecture (JWT Flow)

1. User logs in → JWT generated
2. JWT sent in every request:

```
Authorization: Bearer <token>
```

3. API Gateway validates JWT
4. Extract role and userId
5. Forward to respective service

---

# 🧠 7️⃣ Important Design Patterns Used

## 🔹 Database per Service Pattern

Each service owns its database.

## 🔹 SAGA Pattern

Used for distributed transactions (Order + Payment + Delivery)

## 🔹 Event-Driven Architecture

Kafka used for async communication.

## 🔹 Circuit Breaker

Prevents cascading failures.

## 🔹 Idempotency

Prevents duplicate payment/order creation.

---

# 🎯 8️⃣ Interview Booster Topics (Prepare These)

* What is SAGA pattern?
* Orchestration vs Choreography
* Distributed transactions
* API Gateway vs Load Balancer
* Why database per service?
* Event driven architecture
* Idempotency
* Retry mechanism
* Circuit breaker
* JWT validation flow
* Refresh token
* Kafka consumer group
* Exactly-once vs At-least-once delivery

---

# ⚡ 9️⃣ Production-Level Enhancements

* Add rate limiting in gateway
* Add refresh token mechanism
* Use RS256 for JWT
* Use HTTPS
* Add health checks (Actuator)
* Centralized logging
* Monitoring dashboard

---

# 🏁 1️⃣0️⃣ Final Architecture Summary

```
Client
   ↓
API Gateway (JWT validation)
   ↓
Microservices
   ↓
Kafka (Event-driven)
   ↓
Database per service
```

---

# 🏆 Final Interview One-Line Explanation

> "This is a fully event-driven microservices architecture for a food delivery platform using Spring Boot, Eureka, API Gateway, Kafka, JWT security, database-per-service pattern, and SAGA for distributed transactions, designed for scalability, fault tolerance, and production readiness."

---

🔥 This is now a complete, professional, interview-ready Microservices Architecture document.

If you want next:

* Complete ER diagram
* Database schema design
* SAGA implementation example
* Complete JWT implementation
* Kafka configuration
* Docker-compose setup

Tell me 🚀






# 🍔 Food Delivery Platform – Overall High-Level Architecture Flow

> 🔥 Complete Step-by-Step System Flow
> Fully Interview Ready | Nothing Missed | Covers Sync + Async + Failure Handling

---

# 🧭 1️⃣ Step-by-Step Complete Flow

---

# 🟢 STEP 1 – User Login

## Flow

```
Client (React)
    ↓
API Gateway
    ↓
User Service
```

## What Happens Internally?

### 🔹 Client → Gateway

* Sends login request with email & password

### 🔹 Gateway → User Service

* Routes request via Eureka service discovery

### 🔹 User Service

* Authenticate credentials (BCrypt password check)
* If valid → Generate JWT
* Return JWT to client

### 🔹 Response to Client

```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

---

## 🔐 Important

After login:

```
Authorization: Bearer <JWT>
```

👉 Every protected request must contain JWT in header.

---

# 🟢 STEP 2 – Browse Restaurants

## Flow

```
Client → Gateway → Restaurant Service
```

## Restaurant Service Responsibilities

* Fetch approved restaurants
* Apply filters (optional)
* Return restaurant list

## Optional Optimization

✔ Use Redis caching
✔ Cache restaurant list for faster response

---

# 🟢 STEP 3 – View Menu

## Flow

```
Client → Gateway → Menu Service
```

## Menu Service Responsibilities

* Fetch food items by `restaurantId`
* Check availability (AVAILABLE / OUT_OF_STOCK)
* Return filtered list

## Optimization

✔ Frequently accessed menus → Cache in Redis
✔ Avoid repeated DB hits

---

# 🟢 STEP 4 – Place Order (🔥 Core Flow)

## Flow

```
Client → Gateway → Order Service
```

---

## Order Service Internal Steps

### 1️⃣ Validate User

* JWT validated at Gateway
* User ID extracted

### 2️⃣ Fetch Menu Items

* Call Menu Service (REST call)
* Validate items exist
* Validate availability

### 3️⃣ Calculate Total

* Price × Quantity
* Add taxes / delivery fee (optional)

### 4️⃣ Save Order

Order status:

```
PENDING_PAYMENT
```

### 5️⃣ Call Payment Service (Sync Call)

```
Order Service → Payment Service
```

---

# 🟢 STEP 5 – Payment Flow

## Flow

```
Order Service → Payment Service
```

---

## Payment Service Responsibilities

* Process payment (Mock integration)
* Save payment record in DB

Payment statuses:

```
INITIATED
SUCCESS
FAILED
```

---

## If Payment SUCCESS

Payment Service publishes Kafka event:

```
Topic: payment-success
Payload:
{
  "orderId": 101
}
```

---

# 🟣 STEP 6 – Event-Driven Flow (🔥 Most Important)

Now system becomes asynchronous.

---

## Kafka Event → Order Service Consumes

### Order Service:

* Listen to `payment-success`
* Update order status:

```
CONFIRMED
```

* Publish new event:

```
Topic: order-confirmed
```

---

## Kafka Event → Notification Service Consumes

### Notification Service:

* Listen to `order-confirmed`
* Send:

    * Email
    * SMS
    * Push notification

---

## Kafka Event → Delivery Service Consumes

### Delivery Service:

* Assign delivery partner
* Update delivery record
* Publish:

```
delivery-assigned
```

---

# 🟡 STEP 7 – Delivery Tracking

## Flow

```
Client → Gateway → Delivery Service
```

---

## Delivery Service Responsibilities

* Provide real-time status
* Update delivery progress

Statuses:

```
PREPARING
OUT_FOR_DELIVERY
DELIVERED
```

---

## Optional Enhancement

✔ Use WebSocket for real-time tracking
✔ Use MongoDB for flexible tracking updates

---

# 🔥 Final Order Status Flow

```
PENDING_PAYMENT
        ↓
CONFIRMED
        ↓
PREPARING
        ↓
OUT_FOR_DELIVERY
        ↓
DELIVERED
```

If cancelled:

```
PENDING_PAYMENT
        ↓
CANCELLED
```

---

# 🧠 2️⃣ Communication Types Used

| Communication | Where Used                                        |
| ------------- | ------------------------------------------------- |
| REST          | Synchronous calls (Order → Payment, Order → Menu) |
| Kafka         | Asynchronous event communication                  |
| JWT           | Authentication & Authorization                    |
| Eureka        | Service Discovery                                 |
| API Gateway   | Single entry point                                |

---

# 🏗️ 3️⃣ Database Strategy (Very Important Interview Topic)

## ✔ Database Per Service Pattern

Each service has its own database.

❌ No direct DB sharing
❌ No cross-database queries

Communication only via:

* REST API
* Kafka Events

---

## Example

| Service            | Database              |
| ------------------ | --------------------- |
| User Service       | user_db               |
| Restaurant Service | restaurant_db         |
| Menu Service       | menu_db               |
| Order Service      | order_db              |
| Payment Service    | payment_db            |
| Delivery Service   | delivery_db (MongoDB) |

---

# 🧨 4️⃣ What Happens If Payment Fails?

## Scenario

Payment Service publishes:

```
Topic: payment-failed
Payload: orderId
```

---

## Order Service Consumes Event

* Update order status:

```
CANCELLED
```

* Optionally publish `order-cancelled`

---

# 🧠 This is SAGA Pattern

Because:

* Distributed transaction
* No global DB transaction
* Each service performs local transaction
* Failure handled via compensating action

---

# 🔥 SAGA in This System

| Step            | Action                      |
| --------------- | --------------------------- |
| Order Created   | Local DB transaction        |
| Payment Success | Confirm order               |
| Payment Failed  | Cancel order (Compensation) |

---

# 🏛️ 5️⃣ Complete Architecture Overview

```
Client (React)
        ↓
API Gateway (JWT Validation)
        ↓
Microservices (Spring Boot)
        ↓
Kafka (Event Bus)
        ↓
Database per service
```

---

# ⚙️ Sync vs Async Summary

| Type              | Example                 |
| ----------------- | ----------------------- |
| Synchronous       | Order → Payment (REST)  |
| Asynchronous      | Payment → Order (Kafka) |
| Authentication    | JWT                     |
| Service Discovery | Eureka                  |

---

# 🚀 Production-Level Enhancements

✔ Redis caching
✔ Circuit breaker (Resilience4j)
✔ Retry mechanism
✔ Idempotency key for payment
✔ Distributed tracing (Zipkin)
✔ Docker containerization
✔ Centralized logging

---

# 🎯 Interview Summary (One-Line Answer)

> "This system follows a database-per-service microservices architecture with REST for synchronous communication and Kafka for event-driven asynchronous flow, secured via JWT, using SAGA pattern for distributed transaction management."

---

🔥 This is now a complete High-Level Architecture Flow document.

If you want next:

* Sequence diagram
* SAGA orchestration vs choreography difference
* Complete DB schema
* Kafka configuration example
* Docker-compose setup
* Production security hardening

Tell me 🚀




# 🍔 Food Delivery Platform – JWT Complete Guide (Microservices)

> 🔥 This document explains **JWT end-to-end flow in Microservices Architecture**
> Nothing is skipped. Interview-ready explanation.

---

# 📌 1️⃣ What is JWT?

**JWT (JSON Web Token)** is a compact, URL-safe token used for authentication and authorization.

It contains:

* User identity
* Roles
* Expiry time
* Signature for validation

JWT is:

* Stateless
* Self-contained
* Digitally signed

---

# 📦 2️⃣ Structure of JWT

JWT has **3 parts**:

```
HEADER.PAYLOAD.SIGNATURE
```

Example:

```
eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9
.
eyJzdWIiOiIxMjMiLCJyb2xlIjoiQ1VTVE9NRVIiLCJleHAiOjE3MDAwMDAwMDB9
.
SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c
```

---

## 🔹 1. Header

```json
{
  "alg": "HS256",
  "typ": "JWT"
}
```

* `alg` → Algorithm used for signature
* `typ` → Token type

---

## 🔹 2. Payload

```json
{
  "sub": "101",
  "email": "user@gmail.com",
  "role": "CUSTOMER",
  "exp": 1700000000
}
```

Contains:

* User ID
* Email
* Role (CUSTOMER / RESTAURANT / DELIVERY / ADMIN)
* Expiry time

⚠️ Payload is Base64 encoded (not encrypted)

---

## 🔹 3. Signature

```
HMACSHA256(
  base64UrlEncode(header) + "." +
  base64UrlEncode(payload),
  SECRET_KEY
)
```

Signature ensures:

* Token not tampered
* Authenticity verified

---

# 🔐 3️⃣ JWT Flow in Microservices (VERY IMPORTANT)

## 🏗️ Step 1: User Login

User sends:

```
POST /auth/login
```

With:

```json
{
  "email": "user@gmail.com",
  "password": "123456"
}
```

---

## 🏗️ Step 2: User Service Validates Credentials

* Check email in DB
* Match password (BCrypt)
* If valid → Generate JWT

---

## 🏗️ Step 3: JWT Generation

JWT contains:

* userId
* role
* issuedAt
* expiry

Signed using:

```
SECRET_KEY
```

Response:

```json
{
  "token": "eyJhbGciOiJIUzI1NiIs..."
}
```

---

# 🌍 4️⃣ After Login – What Happens?

Now every request must contain JWT.

Client sends:

```
Authorization: Bearer eyJhbGciOiJIUzI1NiIs...
```

Example:

```
GET /orders
Authorization: Bearer <JWT>
```

🔥 YES — **Every protected request must send JWT**

---

# 🛡️ 5️⃣ How JWT is Validated in Microservices

There are 2 approaches:

---

## ✅ OPTION 1 (Recommended) – Validate in API Gateway

Flow:

```
Client → API Gateway → Internal Services
```

### Gateway Does:

1. Extract token from header
2. Remove "Bearer "
3. Validate signature
4. Check expiry
5. Extract user details
6. Forward request

If invalid → 401 Unauthorized

If valid → Add headers:

```
X-User-Id
X-User-Role
```

Then forward to internal services.

🔥 This is best practice.

---

## ✅ OPTION 2 – Each Service Validates JWT

Each service:

* Has same SECRET_KEY
* Has JWT filter
* Validates token independently

⚠️ Slightly repetitive but common in small systems.

---

# 🔎 6️⃣ JWT Validation Steps Internally

When request comes:

### Step 1: Extract Token

```java
String authHeader = request.getHeader("Authorization");
```

---

### Step 2: Remove Bearer

```java
String token = authHeader.substring(7);
```

---

### Step 3: Parse Token

```java
Claims claims = Jwts.parser()
    .setSigningKey(SECRET_KEY)
    .parseClaimsJws(token)
    .getBody();
```

---

### Step 4: Check Expiry

JWT automatically throws exception if expired.

---

### Step 5: Extract Role

```java
String role = claims.get("role");
```

---

### Step 6: Set Authentication

```java
UsernamePasswordAuthenticationToken auth =
    new UsernamePasswordAuthenticationToken(user, null, authorities);

SecurityContextHolder.getContext().setAuthentication(auth);
```

Now request is authenticated.

---

# 🔥 7️⃣ Full Microservice Flow with JWT

## 🎯 Example: Place Order

### 1️⃣ User logs in → gets JWT

### 2️⃣ User calls Order Service:

```
POST /orders
Authorization: Bearer <JWT>
```

### 3️⃣ API Gateway validates token

### 4️⃣ Order Service receives authenticated request

### 5️⃣ Order created with userId from token

### 6️⃣ Order calls Payment Service

### 7️⃣ Payment success → Kafka event

### 8️⃣ Order updated

### 9️⃣ Notification sent

JWT is only used for **authentication & authorization**.

---

# 🔐 8️⃣ Role Based Authorization

Example:

| Endpoint         | Allowed Role |
| ---------------- | ------------ |
| /restaurants/add | RESTAURANT   |
| /orders/place    | CUSTOMER     |
| /delivery/update | DELIVERY     |
| /admin/approve   | ADMIN        |

Spring Security config:

```java
.antMatchers("/orders/**").hasRole("CUSTOMER")
.antMatchers("/admin/**").hasRole("ADMIN")
```

---

# ⏳ 9️⃣ Expiry & Refresh Token

Access Token:

* Short expiry (15–30 min)

Refresh Token:

* Long expiry (7 days)

Flow:

1. Access token expires
2. Client sends refresh token
3. Server issues new access token

🔥 Prevents re-login again and again

---

# 🔄 🔟 What If JWT Is Invalid?

Cases:

* Expired
* Tampered
* Wrong signature
* Missing header

Return:

```
401 Unauthorized
```

Never return 500.

---

# 🧠 1️⃣1️⃣ Why JWT is Perfect for Microservices?

Because:

* Stateless
* No session storage
* No central session DB
* Works across distributed systems
* Scalable

---

# 🏗️ 1️⃣2️⃣ JWT + Kafka Interaction

Important:

* JWT is only for HTTP requests.
* Kafka events DO NOT use JWT.
* Services trust internal communication.

---

# ⚡ 1️⃣3️⃣ Best Practices (Interview Important)

✅ Keep SECRET_KEY secure
✅ Use strong algorithm (HS256 / RS256)
✅ Use short expiry
✅ Always validate signature
✅ Use HTTPS
✅ Add Refresh Token
✅ Store token in HTTP-only cookie (production)
✅ Do not store sensitive data in payload

---

# 🧨 1️⃣4️⃣ Common Interview Questions

### ❓ How JWT is stateless?

Because server does not store session.

---

### ❓ How revoke JWT?

Options:

* Blacklist tokens (Redis)
* Reduce expiry
* Change secret key

---

### ❓ What is difference between OAuth and JWT?

JWT = Token format
OAuth = Authorization framework

---

### ❓ What happens if service secret key changes?

All tokens become invalid.

---

# 🧱 1️⃣5️⃣ Production Architecture Summary

```
Client
   ↓
API Gateway (JWT Validation)
   ↓
Microservices
   ↓
Database
```

JWT validation ideally happens at:

✔ API Gateway
✔ OR Each service (if needed)

---

# 🎯 FINAL SUMMARY

✔ User logs in → JWT generated
✔ Every request sends JWT
✔ JWT validated via secret key
✔ Role extracted
✔ Authorization applied
✔ No session stored
✔ Scalable & microservice friendly

---

# 🚀 Interview One-Line Answer

> "JWT is a stateless authentication mechanism where the server generates a signed token after login, and every subsequent request carries that token in the Authorization header. The token is validated using a secret key either at API Gateway or individual services."

---

🔥 Now you fully understand JWT in Microservices.

If you want next:

* Complete Spring Boot JWT implementation
* Gateway level JWT filter code
* Refresh token implementation
* RSA based JWT
* SAGA + JWT integration

Tell me 🚀


# 🔐 JWT in Microservices – Complete Explanation (Nothing Missed)

> This document explains JWT from **basic concept → login → validation → microservices flow → refresh token → production best practices**.
> Fully interview-ready.

---

# 🧠 1️⃣ Basic Idea of JWT

**JWT = JSON Web Token**

A JWT is a **digitally signed token** generated by the server during login.
The client sends this token with every protected request.

It is:

* Stateless
* Self-contained
* Digitally signed
* Compact and URL safe

---

# 🟢 2️⃣ Step 1 – What Happens at Login?

## Flow

```
Client → API Gateway → User Service
```

---

## 🔹 Inside User Service

1. Verify username/email and password
2. If valid:

    * Generate JWT
    * Include required data in token
3. Return JWT to client

---

## 🔹 Example JWT Payload

```json
{
  "sub": "101",
  "role": "CUSTOMER",
  "exp": 1710000000
}
```

### Fields Meaning

| Field | Meaning          |
| ----- | ---------------- |
| sub   | User ID          |
| role  | User role        |
| exp   | Expiry timestamp |

---

## 🔹 Token Sent Back to Client

```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

Now client stores this token (preferably in HTTP-only cookie in production).

---

# 🔐 3️⃣ JWT Structure

JWT consists of 3 parts:

```
HEADER.PAYLOAD.SIGNATURE
```

Example:

```
eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9
.
eyJzdWIiOiIxMDEiLCJyb2xlIjoiQ1VTVE9NRVIifQ
.
abc123signature
```

---

## 🔹 1. Header

```json
{
  "alg": "HS256",
  "typ": "JWT"
}
```

* alg → Signing algorithm
* typ → Token type

---

## 🔹 2. Payload

Contains claims:

* userId
* role
* expiry

⚠ Payload is encoded, NOT encrypted.

---

## 🔹 3. Signature

Signature ensures token is not tampered.

Generated using:

```
HMACSHA256(
  base64UrlEncode(header) + "." +
  base64UrlEncode(payload),
  SECRET_KEY
)
```

If payload changes → signature invalid.

---

# 🟢 4️⃣ Step 2 – How JWT Is Sent in Every Request

Client sends JWT in header:

```
Authorization: Bearer <JWT_TOKEN>
```

Example:

```
GET /orders
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

👉 Every protected endpoint requires this header.

---

# 🏗️ 5️⃣ JWT Flow in Microservices Architecture

🔥 Important interview section.

---

## ❓ Who Validates JWT?

### Option 1 – Each Microservice Validates

Every service:

* Has secret key
* Has JWT filter
* Validates token independently

✔ Simple
❌ Code duplication

---

### Option 2 – API Gateway Validates

Gateway:

* Extracts token
* Validates signature
* Checks expiry
* Extracts user info
* Forwards request

✔ Centralized validation
✔ Better for production

---

# 🟣 Recommended Approach (Production Level)

👉 Validate at Gateway
👉 Also validate in internal services

Why?

* Gateway is external entry point
* Internal services must also remain secure
* Prevent bypass attacks

---

# 🧩 6️⃣ JWT Validation Process (Step-by-Step)

When request arrives:

### 1️⃣ Filter intercepts request

(OncePerRequestFilter)

### 2️⃣ Check Authorization header

### 3️⃣ Extract token

Remove "Bearer " prefix.

### 4️⃣ Verify signature using secret key

If signature mismatch → reject.

### 5️⃣ Check expiry

If expired → reject.

### 6️⃣ Extract username / userId

### 7️⃣ Set Authentication in SecurityContext

Now request is authenticated.

---

## ❌ If Invalid

Cases:

* Expired token
* Wrong signature
* Tampered payload
* Missing header

Return:

```
401 Unauthorized
```

Never return 500.

---

# 🛠 7️⃣ Implementation in Spring Boot

---

## 1️⃣ JwtUtil Class

Responsible for:

* generateToken()
* validateToken()
* extractUsername()
* extractClaims()

---

## 2️⃣ JwtAuthenticationFilter

Extends:

```
OncePerRequestFilter
```

Runs for every request.

Responsibilities:

* Extract token
* Validate
* Set authentication

---

## 3️⃣ SecurityConfig

Register filter:

```java
.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
```

---

# 🔑 8️⃣ Secret Key Sharing in Microservices

🔥 Very important interview question.

---

## Option 1 – Same Secret Key in All Services

* Store in application.yml
* Or store in Config Server

✔ Simple
❌ Less secure

---

## Option 2 – Config Server

* Centralized configuration
* Secret managed in one place

✔ Better management

---

## Option 3 – Asymmetric Keys (Best Practice)

Use:

* Private Key → To sign token
* Public Key → To verify token

✔ More secure
✔ Ideal for production

Recommended for real systems.

---

# 🧠 9️⃣ Full Request Flow Example – Place Order

### Step 1

Client sends request with JWT.

```
Client → Gateway
```

### Step 2

Gateway:

* Validates JWT
* Extracts userId
* Forwards request

### Step 3

Order Service:

* Validates JWT again
* Extracts userId
* Creates order

---

# 💥 1️⃣0️⃣ Important JWT Concepts

| Concept         | Meaning                       |
| --------------- | ----------------------------- |
| Stateless       | Server does not store session |
| Signature       | Prevents tampering            |
| Expiry          | Token lifetime                |
| Claims          | Data stored inside token      |
| Bearer          | Authentication scheme         |
| SecurityContext | Stores authenticated user     |

---

# 🔥 1️⃣1️⃣ Access Token vs Refresh Token

---

## 🔹 Access Token

* Short lifetime (15–30 minutes)
* Sent with every request
* Used for authentication

---

## 🔹 Refresh Token

* Long lifetime (7 days or more)
* Stored in DB
* Used to generate new access token

---

## 🔁 Refresh Flow

1. Access token expires
2. Client sends refresh token
3. Server validates refresh token
4. New access token issued
5. Client continues without re-login

---

# 🔐 1️⃣2️⃣ Refresh Token Security Best Practice

✔ Store refresh token in DB
✔ Rotate refresh tokens
✔ Invalidate on logout
✔ Use HTTP-only cookies
✔ Bind to device (optional advanced security)

---

# 🚀 1️⃣3️⃣ Why JWT Is Perfect for Microservices

* Stateless authentication
* No shared session store
* Scalable
* Works across distributed services
* Lightweight

---

# 🧨 1️⃣4️⃣ Common Interview Questions

### ❓ How to revoke JWT?

Options:

* Blacklist tokens (Redis)
* Change secret key
* Reduce expiry time

---

### ❓ What happens if secret key changes?

All tokens become invalid.

---

### ❓ Is JWT encrypted?

No. It is signed, not encrypted.

---

### ❓ Where should JWT be stored?

Production:

* HTTP-only cookie

Avoid:

* LocalStorage (XSS risk)

---

# 🏁 Final Summary

✔ JWT generated at login
✔ Client sends JWT in every request
✔ Signature verified using secret key
✔ Expiry checked
✔ User extracted and set in SecurityContext
✔ Stateless system
✔ Refresh token for long sessions
✔ Gateway + internal validation recommended

---

# 🎯 Interview One-Line Answer

> "JWT is a stateless, digitally signed token generated at login. The client sends it in the Authorization header for every request. The server validates its signature and expiry before allowing access. In microservices, validation is typically done at the API Gateway and optionally inside each service."

---

🔥 This is the complete JWT explanation for microservices architecture.


