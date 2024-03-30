## Feedback Collection System
## Table of Contents
1. [Introduction](#introduction)
2. [Software Scope](#software-scope)
3. [Software Architecture](#software-architecture)
4. [Test Cases](#test-cases)
5. [Used Tools](#used-tools)
6. [Project Structure](#project-structure)
7. [Deployment](#deployment)
8. [Design](#design)



1. ## Introduction 
   The Feedback Collection System is a web application that allows users to submit feedback anonymously or as registered users. It includes features for feedback submission, user analytics, and administrative access. This document provides an overview of the project, its architecture, and deployment instructions.

2. ## Software Scope 
   The system comprises three main components:

   Postgres Database Service: Utilizes the Postgres 13-alpine image to store feedback data persistently.

   Spring Boot Application Service: Built using OpenJDK 19, this service handles the application logic, exposing endpoints for feedback submission, analytics, and user management.
3. ## Software Architecture 
   The software follows a microservices architecture. The Spring Boot application interacts with the Postgres database for data storage and retrieval.

4. ## Test Cases 
   The project includes unit tests for the critical components, ensuring the correctness of functionalities such as feedback submission and analytics calculations.

5. ## Used Tools 
   Postgres Database: Version 13
   Spring Boot: Version 2.5.4
   OpenJDK: Version 19
6. ## Project Structure 
   The project is structured as follows:

   src/main/docker: Contains Docker-related files.
   src/main/java: Java source code files.
   src/main/resources: Configuration files and static resources.
7. ## Deployment 
   The docker-compose.yml file defines two services: postgres and app. It ensures proper communication between the Spring Boot application and the Postgres database.
   
   Running the Application

   Run the following command in the project root:

   <span style="color: red;">docker-compose up</span>

   Access the application at http://localhost:8080.

   Ensure Docker is installed.
8. ## Design
   Application supports low level front-end for better user experience.
   <img src="C:\Users\marat\IdeaProjects\Feedback Collection System\src\main\resources\static\design.jpg">