# 🍔 Food Delivery Platform – Microservices Architecture

This project is a **Spring Boot Microservices-based Food Delivery Platform** inspired by real-world systems like Swiggy/Zomato.

The system follows:

* Database per service pattern
* Event-driven architecture
* JWT-based authentication
* REST + Kafka communication
* API Gateway + Service Registry

---

# 🏗️ Microservices Overview

The system is divided into the following independent services:

1. **User Service**
2. **Restaurant Service**
3. **Menu Service**
4. **Order Service**
5. **Payment Service**
6. **Delivery Service**
7. **Notification Service**

Each service has its own database and communicates via REST or Kafka events.

---

# 1️⃣ **USER SERVICE**

Handles authentication, authorization, and user management.

### 📌 User Entity

```java
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true)
    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    private boolean enabled;

    private LocalDateTime createdAt;
}
```

### 📌 Address Entity

```java
@Entity
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String street;
    private String city;
    private String state;
    private String pincode;

    private Long userId;
}
```

### 📌 Role Enum

```java
public enum Role {
    CUSTOMER,
    RESTAURANT,
    DELIVERY,
    ADMIN
}
```

---

# 2️⃣ **RESTAURANT SERVICE**

Manages restaurants and their approvals.

### 📌 Restaurant Entity

```java
@Entity
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private String cuisineType;

    private String city;

    private boolean approved;

    private Long ownerId;

    private Double rating;

    private LocalDateTime createdAt;
}
```

---

# 3️⃣ **MENU SERVICE**

Handles restaurant menu items.

### 📌 MenuItem Entity

```java
@Entity
public class MenuItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long restaurantId;

    private String name;
    private String description;

    private Double price;

    private boolean available;

    private boolean veg;

    private String category;
}
```

---

# 4️⃣ **ORDER SERVICE (Core Service)**

Responsible for order lifecycle and coordination.

### 📌 Order Entity

```java
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private Long restaurantId;

    private Double totalAmount;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private String deliveryAddress;

    private LocalDateTime createdAt;
}
```

### 📌 OrderItem Entity

```java
@Entity
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long orderId;
    private Long menuItemId;

    private Integer quantity;
    private Double price;
}
```

### 📌 OrderStatus Enum

```java
public enum OrderStatus {
    PENDING_PAYMENT,
    CONFIRMED,
    PREPARING,
    OUT_FOR_DELIVERY,
    DELIVERED,
    CANCELLED
}
```

---

# 5️⃣ **PAYMENT SERVICE**

Handles payment processing and transaction tracking.

### 📌 Payment Entity

```java
@Entity
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long orderId;
    private Double amount;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    private String transactionId;

    private LocalDateTime paymentTime;
}
```

### 📌 PaymentStatus Enum

```java
public enum PaymentStatus {
    SUCCESS,
    FAILED,
    PENDING
}
```

---

# 6️⃣ **DELIVERY SERVICE**

Manages delivery assignment and tracking.
(Recommended: MongoDB for real-time updates)

### 📌 Delivery Entity

```java
@Document(collection = "deliveries")
public class Delivery {

    @Id
    private String id;

    private Long orderId;
    private Long deliveryPartnerId;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;

    private String currentLocation;

    private LocalDateTime assignedAt;
}
```

### 📌 DeliveryStatus Enum

```java
public enum DeliveryStatus {
    ASSIGNED,
    PICKED_UP,
    OUT_FOR_DELIVERY,
    DELIVERED
}
```

---

# 7️⃣ **NOTIFICATION SERVICE**

Handles user notifications (email/SMS/push).

### 📌 Notification Entity

```java
@Entity
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private String message;

    @Enumerated(EnumType.STRING)
    private NotificationType type;

    private boolean read;

    private LocalDateTime sentAt;
}
```

### 📌 NotificationType Enum

```java
public enum NotificationType {
    ORDER_CONFIRMED,
    PAYMENT_SUCCESS,
    DELIVERY_ASSIGNED,
    ORDER_DELIVERED
}
```

---

# 🔐 Architecture Principles Followed

* Database per service
* No cross-service JPA relationships
* Communication via REST & Kafka
* JWT-based stateless authentication
* Event-driven order lifecycle
* Scalable and production-ready structure

---

# 🚀 Future Enhancements

* Implement Saga Pattern
* Add Redis caching
* Dockerize all services
* Add Circuit Breaker (Resilience4j)
* Distributed tracing with Zipkin
* Kubernetes deployment

---

# 🎯 Conclusion

This Food Delivery Platform demonstrates:

* Real-world microservices architecture
* Secure JWT authentication
* Event-driven communication
* Independent service scalability

It is designed to be interview-ready and production-oriented.

---
