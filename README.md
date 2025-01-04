# Learning Management System

## Introduction

This is a simple Java-based project that aims to build a streamlined, user-friendly Online Learning Management System (LMS) tailored to the needs of small institutions and individual instructors. The project demonstrates how to set up a website for the same while using JDBC for database connectivity. With future enhancements, the project can be expanded to serve larger institutions.

---

## Table of Contents

1. [Requirements](#requirements)
2. [Project Setup](#project-setup)
3. [Database Setup](#database-setup)
4. [Project Structure](#project-structure)
5. [How to Run](#how-to-run)
6. [Future Improvements](#future-improvements)
7. [Troubleshooting](#troubleshooting)

---

## Requirements

To run this project, you'll need:

- **Java Development Kit (JDK) 8 or later** - [Download here](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)
- **MySQL Database Server** - [Download here](https://dev.mysql.com/downloads/installer/)
- **Java IDE** (such as IntelliJ IDEA, Eclipse, or NetBeans)

---

## Project Setup

1. **Clone the Repository**

   ```bash
   git clone https://github.com/yourusername/Online_LMS.git
   cd Online_LMS
   ```

2. **Set Up JDK**

   - Install the JDK following the instructions on the Oracle website.
   - Configure the JDK path in your IDE:
     - In **IntelliJ IDEA**: Go to `File > Project Structure > Project SDK` and set it to your JDK location.
     - In **Eclipse**: Go to `Window > Preferences > Java > Installed JREs` and add the JDK location if needed.

3. **Open Project in IDE**

   - Open the project folder in your chosen IDE.

---

## Database Setup

### Step 1: Install and Configure MySQL

- Download and install MySQL from [here](https://dev.mysql.com/downloads/installer/).
- Set up a root user with a secure password.
- Open the MySQL command line or use a GUI like **MySQL Workbench**.

### Step 2: Create the Database and Tables

1. **Login to MySQL**:

   ```bash
   mysql -u root -p
   ```

2. **Run the SQL Commands to Create Database and Tables**:

   ```sql
   CREATE DATABASE onlinelmsdb;

   USE onlinelmsdb;

   CREATE TABLE Users (
       user_id INT AUTO_INCREMENT PRIMARY KEY,
       username VARCHAR(50) UNIQUE NOT NULL,
       password VARCHAR(255) NOT NULL,
       email VARCHAR(100) UNIQUE NOT NULL,
       role ENUM('Admin', 'Instructor', 'Student') NOT NULL
   );

   CREATE TABLE Admins (
       admin_id INT PRIMARY KEY,
       full_name VARCHAR(100) NOT NULL,
       phone_number VARCHAR(15),
       FOREIGN KEY (admin_id) REFERENCES Users(user_id)
   );

   CREATE TABLE Instructors (
       instructor_id INT PRIMARY KEY,
       full_name VARCHAR(100) NOT NULL,
       qualification VARCHAR(100),
       phone_number VARCHAR(15),
       FOREIGN KEY (instructor_id) REFERENCES Users(user_id)
   );

   CREATE TABLE Students (
       student_id INT PRIMARY KEY,
       full_name VARCHAR(100) NOT NULL,
       enrollment_number VARCHAR(50) UNIQUE NOT NULL,
       date_of_birth DATE,
       phone_number VARCHAR(15),
       FOREIGN KEY (student_id) REFERENCES Users(user_id)
   );

   CREATE TABLE Courses (
       course_id INT AUTO_INCREMENT PRIMARY KEY,
       course_name VARCHAR(100) NOT NULL,
       description TEXT,
       created_by INT NOT NULL,
       FOREIGN KEY (created_by) REFERENCES Instructors(instructor_id)
   );

   CREATE TABLE Enrollments (
       enrollment_id INT AUTO_INCREMENT PRIMARY KEY,
       student_id INT NOT NULL,
       course_id INT NOT NULL,
       enrolled_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
       FOREIGN KEY (student_id) REFERENCES Students(student_id),
       FOREIGN KEY (course_id) REFERENCES Courses(course_id)
   );

   CREATE TABLE Assignments (
       assignment_id INT AUTO_INCREMENT PRIMARY KEY,
       course_id INT NOT NULL,
       title VARCHAR(100) NOT NULL,
       description TEXT,
       due_date DATE,
       FOREIGN KEY (course_id) REFERENCES Courses(course_id)
   );

   CREATE TABLE Submissions (
       submission_id INT AUTO_INCREMENT PRIMARY KEY,
       assignment_id INT NOT NULL,
       student_id INT NOT NULL,
       submission_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
       grade VARCHAR(10),
       FOREIGN KEY (assignment_id) REFERENCES Assignments(assignment_id),
       FOREIGN KEY (student_id) REFERENCES Students(student_id)
   );
   ```

### Step 3: Update Database Credentials

- In the `src/utils/DatabaseConnection.java` file, update the database credentials with your MySQL username and password.

  ```java
  private static final String URL = "jdbc:mysql://localhost:3306/onlinelmsdb";
  private static final String USER = "your_mysql_username";
  private static final String PASSWORD = "your_mysql_password";
  ```

---

## Project Structure

Here's a quick overview of the project structure:

```
Online_LMS/
├── src/
│   ├── dao/               # Contains DAO classes for data access operations
│   ├── models/            # Contains data models like User, Course, Attendance, etc.
│   ├── services/          # Contains service classes for user operations
│   ├── utils/             # Contains utility classes, like DatabaseConnection
│   └── Online_LMS.java    # Main class to run the application
└── README.md
```

---

## How to Run

1. **Compile and Run the Main Class**

   - In your IDE, navigate to the `Online_LMS.java` file, right-click, and select `Run`.
   - Alternatively, you can use the command line:

     ```bash
     javac src/Online_LMS.java
     java -cp src Online_LMS
     ```

---

## Future Improvements

This project is designed for learning and demonstration. Here are some suggested improvements for future iterations:

- Add more functionalities for every user role.
- Implement more sophisticated error handling.
- Create a user interface (UI) for easier interaction.
- Expand the database to support additional details.
- Integrate with cloud databases for scalability.
- Design role-specific dashboards for a personalized experience.

---

## Troubleshooting

- **Database Connection Issues**: Ensure your MySQL server is running, and check your credentials in `DatabaseConnection.java`.
- **Class Not Found Errors**: Confirm that your JDK is correctly set up in your IDE.
- **SQL Errors**: Double-check your SQL syntax and ensure the database schema matches the code.
- **Version Compatibility**: Ensure you are using compatible versions of Java and MySQL.

---

## License

This project is open-source and can be modified as needed.

---

Thank you for exploring this project!
