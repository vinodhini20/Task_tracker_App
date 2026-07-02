Task tracker
This is a comprehensive task management system that empowers users to efficiently organize their tasks. This full-stack application combines the robustness of Spring Boot on the backend, the responsiveness of React.js on the frontend, and the reliability of MySQL as the database.

Tech Stack
Client: React js BootStrap Server: Java, Spring Boot Database: MySQL
Architecture
The Task Management System follows a client-server architecture, with a backend built using Java and Spring Boot serving as the server-side application. The frontend is developed using React, providing a user-friendly interface for interacting with the system. MySQL is used as the database to store task-related data.
Modules
Backend: Contains the Java and Spring Boot application responsible for handling business logic and database operations.
Frontend: Includes the React application responsible for providing a user interface for interacting with the system.
Database: Contains the MySQL database schema and scripts for creating and managing the database.

Backend Setup
Clone the repository.
Navigate to the backend directory.
Configure the MySQL database connection in application.properties.
Run the Spring Boot application using your IDE or the command line.

Frontend Setup
Navigate to the frontend directory.
Install dependencies using npm install.
Start the React application using npm start

Database Setup

install MYSQL database and workbenchDB
Execute the database schema script provided in the database directory to create the necessary tables.
Update the database connection details in the backend application properties.
-- Create Database
CREATE DATABASE IF NOT EXISTS tasktracker;
USE tasktracker;

-- Projects Table
CREATE TABLE IF NOT EXISTS projects (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
        ON UPDATE CURRENT_TIMESTAMP
);

-- Tasks Table
CREATE TABLE IF NOT EXISTS tasks (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    status ENUM('TODO', 'DOING', 'DONE')
        NOT NULL DEFAULT 'TODO',
    priority ENUM('LOW', 'MEDIUM', 'HIGH')
        NOT NULL DEFAULT 'MEDIUM',
    due_date DATETIME,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
        ON UPDATE CURRENT_TIMESTAMP,
    project_id BIGINT,
    CONSTRAINT fk_project
        FOREIGN KEY (project_id)
        REFERENCES projects(id)
        ON DELETE CASCADE
);

-- Sample Projects
INSERT INTO projects (id, name)
VALUES
(1, 'Website Redesign'),
(2, 'Mobile App Launch')
ON DUPLICATE KEY UPDATE
name = VALUES(name);

-- Sample Tasks
INSERT INTO tasks
(title, description, status, priority, due_date, project_id)
VALUES
('Design New Homepage',
 'Create mockups for the new homepage',
 'TODO',
 'HIGH',
 '2026-07-15 23:59:59',
 1),

('Develop API Endpoints',
 'Build the core task management API',
 'DOING',
 'HIGH',
 '2026-07-20 23:59:59',
 1),

('Setup Frontend Project',
 'Initialize React project and basic structure',
 'DONE',
 'MEDIUM',
 '2026-07-10 23:59:59',
 1),

('Plan Marketing Campaign',
 'Outline marketing strategy for the new app',
 'TODO',
 'MEDIUM',
 '2026-08-01 23:59:59',
 2),

('Test Beta Version',
 'Conduct user acceptance testing for the beta release',
 'TODO',
 'HIGH',
 '2026-08-15 23:59:59',
  2);
 
 AI Declaration

designed the pipeline and used 	Chatgpt and goggle Gemini for frontend and backend for related errors

This project was developed with the assistance of AI tools (ChatGPT by OpenAI to support learning and improve development productivity.



