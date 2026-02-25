# 🌍 Food Delivery Platform – API Base URL & Routing Documentation

> Complete API Base URL Structure
> Gateway Routing + Internal Service URLs
> Production Ready Format
> Nothing Missed

---

# 🌐 1️⃣ Global API Gateway Base URL

Assume:

```
http://localhost:8080
```

👉 This is the **API Gateway Base URL**

All client requests will go through this gateway.

In production:

```
https://api.fooddelivery.com
```

---

# 🧱 2️⃣ User Service

## 🔹 Internal Service URL (Eureka Registered Name)

```
http://USER-SERVICE
```

---

## 🔹 Gateway Base URL

```
http://localhost:8080/api/users
```

---

## 📌 Example Endpoints

```
POST   /api/users/register
POST   /api/users/login
POST   /api/users/refresh
GET    /api/users/profile
PUT    /api/users/profile
POST   /api/users/address
```

---

# 🍽️ 3️⃣ Restaurant Service

## 🔹 Internal Service URL

```
http://RESTAURANT-SERVICE
```

---

## 🔹 Gateway Base URL

```
http://localhost:8080/api/restaurants
```

---

## 📌 Example Endpoints

```
POST   /api/restaurants
PUT    /api/restaurants/{id}
GET    /api/restaurants
GET    /api/restaurants/{id}
PUT    /api/restaurants/{id}/approve
```

---

# 🍕 4️⃣ Menu Service

## 🔹 Internal Service URL

```
http://MENU-SERVICE
```

---

## 🔹 Gateway Base URL

```
http://localhost:8080/api/menus
```

---

## 📌 Example Endpoints

```
POST   /api/menus
PUT    /api/menus/{id}
GET    /api/menus/restaurant/{restaurantId}
PUT    /api/menus/{id}/availability
```

---

# 🧾 5️⃣ Order Service (Core Service)

## 🔹 Internal Service URL

```
http://ORDER-SERVICE
```

---

## 🔹 Gateway Base URL

```
http://localhost:8080/api/orders
```

---

## 📌 Example Endpoints

```
POST   /api/orders
GET    /api/orders/{id}
GET    /api/orders/user/{userId}
PUT    /api/orders/{id}/status
```

---

# 💳 6️⃣ Payment Service

## 🔹 Internal Service URL

```
http://PAYMENT-SERVICE
```

---

## 🔹 Gateway Base URL (Optional Public Exposure)

```
http://localhost:8080/api/payments
```

⚠ Normally Payment Service is internal only
It is typically called by Order Service (REST call).

---

## 📌 Example Endpoints

```
POST   /api/payments/process
GET    /api/payments/{orderId}
```

---

# 🚚 7️⃣ Delivery Service

## 🔹 Internal Service URL

```
http://DELIVERY-SERVICE
```

---

## 🔹 Gateway Base URL

```
http://localhost:8080/api/deliveries
```

---

## 📌 Example Endpoints

```
POST   /api/deliveries/assign
PUT    /api/deliveries/{orderId}/status
GET    /api/deliveries/{orderId}/track
```

---

# 🔔 8️⃣ Notification Service

## 🔹 Internal Service URL

```
http://NOTIFICATION-SERVICE
```

---

## 🔹 Gateway Exposure

Usually NOT publicly exposed.

If exposed:

```
http://localhost:8080/api/notifications
```

Typically works as:

* Kafka consumer
* Event listener
* Email/SMS sender

---

# ⚙️ 9️⃣ Infrastructure Services URLs

| Service       | Default URL           |
| ------------- | --------------------- |
| Eureka        | http://localhost:8761 |
| Config Server | http://localhost:8888 |
| Zipkin        | http://localhost:9411 |
| Kafka         | localhost:9092        |

---

# 🧠 🔟 Spring Cloud Gateway Routing Configuration

Example `application.yml`:

```yaml
spring:
  cloud:
    gateway:
      routes:
        - id: user-service
          uri: lb://USER-SERVICE
          predicates:
            - Path=/api/users/**

        - id: restaurant-service
          uri: lb://RESTAURANT-SERVICE
          predicates:
            - Path=/api/restaurants/**

        - id: menu-service
          uri: lb://MENU-SERVICE
          predicates:
            - Path=/api/menus/**

        - id: order-service
          uri: lb://ORDER-SERVICE
          predicates:
            - Path=/api/orders/**

        - id: payment-service
          uri: lb://PAYMENT-SERVICE
          predicates:
            - Path=/api/payments/**

        - id: delivery-service
          uri: lb://DELIVERY-SERVICE
          predicates:
            - Path=/api/deliveries/**
```

`lb://` → Load balanced via Eureka

---

# 🎯 1️⃣1️⃣ Final Clean Base URL Summary

| Service      | Gateway Base URL     |
| ------------ | -------------------- |
| User         | `/api/users`         |
| Restaurant   | `/api/restaurants`   |
| Menu         | `/api/menus`         |
| Order        | `/api/orders`        |
| Payment      | `/api/payments`      |
| Delivery     | `/api/deliveries`    |
| Notification | `/api/notifications` |

---

# 🌎 1️⃣2️⃣ Full Example URLs

### Local Development

```
http://localhost:8080/api/orders
http://localhost:8080/api/users/login
http://localhost:8080/api/restaurants
```

---

### Production Version

```
https://api.fooddelivery.com/api/orders
https://api.fooddelivery.com/api/users/login
https://api.fooddelivery.com/api/restaurants
```

---

# 🔐 1️⃣3️⃣ Security Reminder

All protected endpoints require:

```
Authorization: Bearer <JWT>
```

JWT is validated at:

* API Gateway
* Internal services (recommended)

---

# 🏁 Final Summary

✔ API Gateway is single entry point
✔ Each service has internal Eureka name
✔ External clients only hit Gateway
✔ Database per service maintained
✔ Payment & Notification often internal
✔ Production uses HTTPS domain

---

🔥 This document now contains complete API base URL structure for your Food Delivery Microservices Architecture.

If you want next:

* Versioning strategy (/api/v1/)
* Swagger URL mapping
* Docker port mapping
* Kubernetes ingress setup
* Production deployment architecture

Tell me 🚀
