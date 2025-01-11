## This Project was developed using NetBeans and MySQL.

First you need to clone the prject.

We used swing for the UI and SQL for the backend.

First you have to clone the project.

```
git clone https://github.com/Akagbor/LibraryManagementSwing.git
```

Then drag the folder to the NetBeans shortcut icon on the Desktop.

After connecting NetBeans and MySQL to Xampp you run the following code in MySQL to create the database

```
CREATE DATABASE LibraryDB;

USE LibraryDB;

CREATE TABLE books (
    book_id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255),
    available BOOLEAN DEFAULT TRUE
);

CREATE TABLE issued_books (
    issue_id INT AUTO_INCREMENT PRIMARY KEY,
    book_id INT,
    student_name VARCHAR(255),
    issue_date DATE,
    FOREIGN KEY (book_id) REFERENCES books(book_id)
);

```

Then run the LibraryManagementSwing.java