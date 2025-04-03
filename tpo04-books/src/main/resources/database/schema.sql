DROP TABLE IF EXISTS Book_Author;
DROP TABLE IF EXISTS Book;
DROP TABLE IF EXISTS Publisher;
DROP TABLE IF EXISTS Author;

-- Create Publisher
CREATE TABLE Publisher
(
    Publisher_ID BIGINT AUTO_INCREMENT PRIMARY KEY,
    Name         VARCHAR(50)  NOT NULL,
    Address      VARCHAR(250) NOT NULL,
    PhoneNumber  VARCHAR(50)  NOT NULL
);

-- Create Author
CREATE TABLE Author
(
    Author_ID BIGINT AUTO_INCREMENT PRIMARY KEY,
    Name      VARCHAR(250) NOT NULL,
    Surname   VARCHAR(250) NOT NULL
);

-- Create Book
CREATE TABLE Book
(
    Book_ID         BIGINT AUTO_INCREMENT PRIMARY KEY,
    Publisher_ID    BIGINT                NOT NULL,
    Title           VARCHAR(250)       NOT NULL,
    PublicationYear BIGINT                NOT NULL,
    ISBN            VARCHAR(50) UNIQUE NOT NULL,
    FOREIGN KEY (Publisher_ID) REFERENCES Publisher (Publisher_ID) ON DELETE CASCADE
);

-- Create Book_Author
CREATE TABLE Book_Author
(
    Book_ID   BIGINT NOT NULL,
    Author_ID BIGINT NOT NULL,
    PRIMARY KEY (Book_ID, Author_ID),
    FOREIGN KEY (Book_ID) REFERENCES Book (Book_ID) ON DELETE CASCADE,
    FOREIGN KEY (Author_ID) REFERENCES Author (Author_ID) ON DELETE CASCADE
);