# SpringBOOT-Powered Backend Store API: A Comprehensive Overview

In the bustling world of e-commerce, having a reliable backend infrastructure is paramount to delivering seamless user experiences and efficient resource management. Enter the Backend Store API, a meticulously crafted solution empowered by **Spring Boot 3.2.2**. This article aims to provide an in-depth exploration of the Backend Store API, highlighting its core functionalities, supported technologies, and the value it brings to developers and users alike.

## Understanding the Backend Store API

At its heart, the Backend Store API serves as the cornerstone of e-commerce applications, offering a suite of **REST APIs** meticulously engineered to handle diverse aspects of product management, user authentication, cart operations, order management, and more. Developed with a keen focus on scalability, performance, and security, the Electronic Store API empowers developers to build robust, feature-rich e-commerce platforms that meet the demands of modern online retail.

## Exploring the Technology Stack

The Backend Store API harnesses a potent technology stack to deliver exceptional performance and scalability:

- **Spring Boot 3.2.2:** Embraces a lightweight and opinionated framework
- **Spring Security:** Ensures robust authentication and authorization mechanisms, safeguarding sensitive user data and API endpoints from unauthorized access.
- **Docker:** Enables containerization of the Electronic Store API, providing a lightweight and portable solution for packaging applications and their dependencies, ensuring consistency across different environments.
- **AWS Services:** Seamlessly integrates with Amazon Web Services to leverage cloud-based infrastructure, enhancing scalability, reliability, and performance while minimizing operational overhead.
- **Postman:** Empowers developers to test and debug API endpoints effectively by providing a user-friendly interface for sending HTTP requests, inspecting responses, and automating workflows, ensuring the robustness and reliability of the Electronic Store API.
- **JWT Authentication:** JSON Web Token (JWT) is an open standard (RFC 7519) that defines a compact and self-contained way for securely transmitting information between parties as a JSON object. This information can be verified and trusted because it is digitally signed. JWTs can be signed using a secret (with the HMAC algorithm) or a public/private key pair using RSA or ECDSA.

## Key Features and Functionalities

The Electronic Store API boasts an array of features designed to support various e-commerce operations:

- **Product Management:** Offers comprehensive CRUD operations for managing products, including retrieval, creation, update, and deletion, facilitating seamless product lifecycle management.
- **User Authentication:** Implements secure authentication mechanisms, including user login and Google OAuth login, fortified with JWT tokens to ensure secure access to API resources.
- **Cart Operations:** Facilitates the management of user carts, allowing for retrieval, creation, and deletion of carts, along with operations for managing cart items, enhancing the shopping experience for users.
- **Order Management:** Provides robust functionalities for managing orders, including creation, update, retrieval, and removal of orders, as well as retrieval of ordered items, streamlining the order fulfillment process.
- **Category Management:** Supports efficient organization of products into categories, offering CRUD operations for categories and operations for managing category-product relationships, enhancing product discoverability and navigation.

## REST APIs

### Product Controller

- `GET /products/{productId}`: Get Product by ID
- `PUT /products/{productId}`: Update Product Details
- `DELETE /products/{productId}`: Delete Product
- `GET /products`: Get All Products
- `POST /products`: Create Product
- `GET /products/image/{productId}`: Get Product Image
- `POST /products/image/{productId}`: Upload or Update Product Image
- `GET /products/search/{Query}`: Search Products
- `GET /products/live`: Get Live Products

### Cart Controller

- `GET /carts/{userId}`: Retrieve User's Cart
- `POST /carts/{userId}`: Create User's Cart
- `DELETE /carts/{userId}`: Delete User's Cart
- `DELETE /carts/{userId}/item/{itemId}`: Remove Item from User's Cart

### Category Controller

- `GET /categories/{categoryId}`: Get Category by Category ID
- `PUT /categories/{categoryId}`: Update Category
- `DELETE /categories/{categoryId}`: Delete Category
- `PUT /categories/{categoryId}/products/{productId}`: Update Category of Product
- `GET /categories/{categoryId}/products`: Get Products by Category ID
- `POST /categories/{categoryId}/products`: Create Product with Category
- `GET /categories/image/{categoryId}`: Get Category Image
- `POST /categories/image/{categoryId}`: Upload Category Image
- `GET /categories`: Get All Categories
- `GET /categories/search/{Keyword}`: Get Category by Search

### Authentication Controller

- `POST /auth/login`: Get JWT Authentication token to access Apis 
- `GET /auth/current`: Fetch Current User

### Order Controller

- `PUT /orders/{orderId}`: Update Order Status
- `DELETE /orders/{orderId}`: Remove Order Status
- `GET /orders`: Get All Orders
- `POST /orders`: Create Order
- `GET /orders/users/{userId}`: Get Ordered items

### User Controller

- `GET /users/{userId}`: Get User by User ID
- `PUT /users/{userId}`: Update User Details
- `DELETE /users/{userId}`: Delete User
- `GET /users`: Get All Users
- `POST /users`: Create User
- `GET /users/image/{userId}`: Get User's Profile Image
- `POST /users/image/{userId}`: Upload or Update User's Profile Image
- `GET /users/search/{keyword}`: Search Users
- `GET /users/email/{email}`: Get User by Email

## Schemas

- RoleDto
- UserDto
- CategoryDto
- ProductDto
- OrderUpdateRequest
- OrderDto
- OrderItemDto
- ImageResponse
- CreateOrderRequest
- AddItemToCartRequest
- Cart
- CartDto
- CartItem
- Category
- GrantedAuthority
- Order
- OrderItem
- Product
- Role
- User
- JwtRequest
- JwtResponse
- PageableResponseUserDto
- PageableResponseProductDto
- PageableResponseOrderDto
- PageableResponseCategoryDto
- ApiResponseMessage


## Java Concepts Utilized

The Backend Store API leverages several advanced Java concepts to enhance its functionality and maintainability:

- **Lambda Expressions:** Enables concise and expressive syntax for implementing functional interfaces, enhancing code readability and maintainability.
- **Optional:** Facilitates more robust handling of nullable values, reducing the risk of null pointer exceptions and improving code clarity.
- **Collectors:** Provides a powerful mechanism for aggregating elements into collections, enabling streamlined data processing operations.
- **List:** Offers dynamic arrays for storing elements, providing flexibility and efficiency in managing collections of objects.
- **Map:** Facilitates key-value pair associations, allowing efficient retrieval and manipulation of data based on keys.

## Docker Configuration

- **Docker Image Pushed:** The Electronic Store API is containerized and its Docker image is pushed to a repository for easy deployment and distribution.
- **Containers Utilized:** Docker containers are utilized to encapsulate the application and its dependencies, ensuring consistent deployment across different environments.
- **Docker Network for MySQL Database and Spring Boot Application Communication:** Docker networks are established to facilitate communication between the Electronic Store API and its associated MySQL database, ensuring seamless interaction and data persistence.

## AWS Deployment ☁

The Electronic Store API is deployed on an EC2 instance using an AWS Free Tier account, leveraging the scalability, reliability, and performance benefits of Amazon Web Services to deliver a robust and accessible e-commerce solution.

## Conclusion

In conclusion, the Backend Store API is a powerful and versatile solution for driving e-commerce innovation.

For any Queries Regarding the Project, Feel Free to Contact: akashguptaworks@gmail.com or skyrisegupta@gmail.com

[Explore the API Documentation in Swagger deployed on AWS EC2](http://13.126.54.93:9091/swagger-ui/index.html)


