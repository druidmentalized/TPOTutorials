CREATE TABLE Publisher
(
    Publisher_ID INT AUTO_INCREMENT PRIMARY KEY,
    Name         VARCHAR(50) NOT NULL,
    Address      VARCHAR(250) NOT NULL,
    PhoneNumber  VARCHAR(50) NOT NULL
);

CREATE TABLE Author
(
    Author_ID INT AUTO_INCREMENT PRIMARY KEY,
    Name      VARCHAR(250) NOT NULL,
    Surname   VARCHAR(250) NOT NULL
);

CREATE TABLE Book
(
    Book_ID         INT AUTO_INCREMENT PRIMARY KEY,
    Publisher_ID    INT                NOT NULL,
    Author_ID       INT                NOT NULL,
    Title           VARCHAR(250)       NOT NULL,
    PublicationYear INT                NOT NULL,
    ISBN            VARCHAR(50) UNIQUE NOT NULL,
    FOREIGN KEY (Publisher_ID) REFERENCES Publisher (Publisher_ID) ON DELETE CASCADE,
    FOREIGN KEY (Author_ID) REFERENCES Author (Author_ID) ON DELETE CASCADE
);