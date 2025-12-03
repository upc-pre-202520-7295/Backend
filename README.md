# Betalyze - Sports Prediction Platform

<!--toc:start-->

- [Betalyze - Sports Prediction Platform](#betalyze-sports-prediction-platform)
  - [Features](#features)
  - [Architecture](#architecture)
    - [1. User Management Context](#1-user-management-context)
    - [2. Data Retrieval Context](#2-data-retrieval-context)
    - [3. Prediction & Analytics Context](#3-prediction-analytics-context)
    - [4. Notification Context](#4-notification-context)
  - [Technology Stack](#technology-stack)
  - [Prerequisites](#prerequisites)
  - [Setup](#setup)
    - [1. Database Setup](#1-database-setup)
    - [2. Environment Variables](#2-environment-variables)
    - [3. Build and Run](#3-build-and-run)
  - [Scheduled Tasks](#scheduled-tasks)
  - [Project Structure](#project-structure)
  - [Development](#development)
    - [Code Style](#code-style)
  - [Contributing](#contributing)
  - [License](#license)
  - [Support](#support)
  <!--toc:end-->

Betalyze is a comprehensive sports analytics and prediction platform built with Domain-Driven Design (DDD) principles using Spring Boot.

## Features

- **User Management**: Registration, authentication, and favorite teams management
- **Data Retrieval**: Automated fetching of match data, team statistics, and betting odds
- **Prediction & Analytics**: Machine learning-based match outcome predictions
- **Notifications**: Firebase push notifications for favorite team matches

## Architecture

The application follows DDD with four bounded contexts:

### 1. User Management Context

- User registration and authentication
- Favorite teams management
- User preferences

### 2. Data Retrieval Context

- Daily match data fetching
- Team statistics collection
- Betting odds retrieval

### 3. Prediction & Analytics Context

- Machine learning model training
- Match prediction generation
- Model performance metrics

### 4. Notification Context

- Push notification management
- User notification preferences
- Firebase integration

## Technology Stack

- **Backend**: Spring Boot 3.2.0
- **Database**: PostgreSQL
- **Security**: Spring Security + JWT
- **ML**: DeepLearning4J
- **Notifications**: Firebase Cloud Messaging
- **API Documentation**: Swagger/OpenAPI
- **Build Tool**: Maven

## Prerequisites

- Java 17+
- PostgreSQL 14+
- Maven 3.8+
- Firebase account (for notifications)

## Setup

### 1. Database Setup

```bash
# Create database
createdb betalyze

# Update application.properties with your database credentials
```

### 2. Environment Variables

```bash
export DATABASE_URL=jdbc:postgresql://localhost:5432/betalyze
export DATABASE_USERNAME=postgres
export DATABASE_PASSWORD=your_password
export JWT_SECRET=your_jwt_secret
export SPORTS_API_KEY=your_api_key
export FIREBASE_CONFIG_PATH=/path/to/firebase-config.json
```

### 3. Build and Run

```bash
# Build the project
mvn clean install

# Run the application
mvn spring-boot:run

# Or run with production profile
mvn spring-boot:run -Dspring-boot.run.profiles=prod
```

## Scheduled Tasks

The application runs three scheduled tasks:

1. **Data Retrieval** (6:00 AM every monday)
   - Fetches And Update Match Schedules
   - Fetches And Update Team Names

2. **Notifications** (every 5 minutes)
   - Checks for matches with favorite teams
   - Sends push notifications to users

## Project Structure

```bash
src/main/java/com/betalyze/
├── shared/                          # Shared kernel
│   ├── domain/                      # Domain primitives
│   ├── infrastructure/              # Shared infrastructure
│   └── application/                 # Shared DTOs
├── usermanagement/                  # User Management BC
│   ├── domain/                      # Domain models
│   ├── application/                 # Services, DTOs, Commands
│   └── infrastructure/              # Controllers, Security
├── dataretrieval/                   # Data Retrieval BC
│   ├── domain/                      # Domain models
│   ├── application/                 # Services, DTOs
│   └── infrastructure/              # Controllers, API clients
├── predictionandanalytics/          # Prediction BC
│   ├── domain/                      # Domain models
│   ├── application/                 # Services, DTOs
│   └── infrastructure/              # Controllers, ML model
└── notification/                    # Notification BC
├── domain/                      # Domain models
├── application/                 # Services, DTOs
└── infrastructure/              # Controllers, Firebase
```

## Development

### Code Style

The project follows standard Java coding conventions and uses Lombok to reduce boilerplate code.

## Contributing

1. Follow DDD principles
2. Maintain bounded context boundaries
3. Write unit tests for new features
4. Update API documentation

## License

MIT License

## Support

For issues and questions, please create an issue in the repository.
