# Ideas Generator Web Application

This repository contains the source code for the Ideas Generator Web Application, a Spring Boot-based project that generates project ideas using APIs and integrates with a language model to enhance idea generation and project management.

## Table of Contents

- [Project Structure](#project-structure)
- [Features](#features)
- [Getting Started](#getting-started)
  - [Prerequisites](#prerequisites)
  - [Installation](#installation)
  - [Running the Application](#running-the-application)
- [API Endpoints](#api-endpoints)
- [License](#license)

## Project Structure

The project is organized into several packages:

- `org.example.ideasgeneratorweb.ChatModelConnector`: Manages the connection to the language model.
- `org.example.ideasgeneratorweb.controller`: Contains the controllers that handle HTTP requests.
- `org.example.ideasgeneratorweb.ideasGenerator`: Includes services and parsers for idea generation.
- `org.example.ideasgeneratorweb.model`: Defines the data models used across the application.
- `org.example.ideasgeneratorweb.pdfManager`: Handles the generation of PDFs for project ideas.

## Features

- **Idea Generation:** Generates project ideas by randomly selecting and combining APIs.
- **Language Model Integration:** Connects to a language model to generate ideas and project-related text content.
- **PDF Export:** Converts project ideas into PDF files.
- **Web Interface:** Provides a web interface for generating and downloading project ideas.
- **REST API:** Exposes endpoints for idea generation and interaction with the language model.

## Getting Started

### Prerequisites

Ensure you have the following installed:

- Java 17 or higher
- Maven
- Spring Boot
- LangChain4j library

### Installation

1. Clone the repository:

    ```sh
    git clone https://github.com/yourusername/ideas-generator-web.git
    ```

2. Navigate to the project directory:

    ```sh
    cd ideas-generator-web
    ```

3. Build the project using Maven:

    ```sh
    mvn clean install
    ```

### Running the Application

Start the application:

```sh
mvn spring-boot:run
