# Stock Management Application - CRUD (Spring Boot + React)

A full-stack inventory management system with PostgreSQL database, Docker support, and JWT authentication using Spring Security.

## Features

- **Backend**: Spring Boot REST API with JPA/Hibernate
- **Frontend**: React.js with functional components and hooks
- **Database**: PostgreSQL with Flyway migrations
- **Containerized**: Dockerized PostgreSQL database
- **Validation**: Input validation on both frontend and backend
- **Error Handling**: Comprehensive error handling and logging
- **Authentication**: JWT-based secure authentication

## Technology Stack

| Component       | Technology Stack |
|----------------|------------------|
| Backend        | Java 17, Spring Boot 3.5,spring Security, Spring Data JPA, Hibernate |
| Frontend       | React 19, React Router, Axios, Bootstrap |
| Database       | PostgreSQL 13, Flyway migrations |
| Infrastructure | Docker, Docker Compose |
| Build Tools    | Maven (Backend), npm (Frontend) |

## Prerequisites

- Java 19 JDK or later
- Node.js 20+ and npm/yarn
- Docker 20.10+ and Docker Compose
- PostgreSQL 13 (or use the Docker version provided)

## Getting Started

### 1. Database Setup

Start the PostgreSQL container:
```bash
docker-compose up -d postgres

