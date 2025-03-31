package org.books.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "Book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Book_ID")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Publisher_ID", nullable = false)
    private Publisher publisher;

    @OneToOne
    @JoinColumn(name = "Author_id", nullable = false)
    private Author author;

    @Column(name = "Title", nullable = false, length = 250)
    private String title;

    @Column(name = "PublicationYear", nullable = false)
    private Integer publicationYear;

    @Column(name = "ISBN", unique = true, nullable = false, length = 50)
    private String ISBN;

    public Integer getId() {
        return id;
    }
    public Publisher getPublisher() {
        return publisher;
    }
    public Author getAuthor() {
        return author;
    }
    public String getTitle() {
        return title;
    }
    public Integer getPublicationYear() {
        return publicationYear;
    }
    public String getISBN() {
        return ISBN;
    }
}
