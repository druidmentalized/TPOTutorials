package org.books.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Book_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Publisher_ID", nullable = false)
    private Publisher publisher;

    @ManyToMany(mappedBy = "books", fetch = FetchType.EAGER)
    private final List<Author> authors = new ArrayList<>();

    @Column(name = "Title", nullable = false, length = 250)
    private String title;

    @Column(name = "PublicationYear", nullable = false)
    private Integer publicationYear;

    @Column(name = "ISBN", unique = true, nullable = false, length = 50)
    private String ISBN;

    public Book() {}

    public Book(String title, Integer publicationYear, String ISBN) {
        this.title = title;
        this.publicationYear = publicationYear;
        this.ISBN = ISBN;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Book [id=").append(id)
                .append(", title=\"").append(title)
                .append("\", publicationYear=").append(publicationYear)
                .append(", ISBN=").append(ISBN)
                .append("]");

        if (publisher != null) {
            sb.append(", Publisher [id=").append(publisher.getId())
                    .append(", name=").append(publisher.getName())
                    .append("]");
        }

        if (!authors.isEmpty()) {
            sb.append(", Authors:");
            for (Author author : authors) {
                // Only print the author id and name to avoid recursion.
                sb.append(" [id=").append(author.getId())
                        .append(", name=").append(author.getName())
                        .append(" ").append(author.getSurname()).append("]");
            }
        } else {
            sb.append(", No authors.");
        }

        return sb.toString();
    }

    public Long getId() {
        return id;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getPublicationYear() {
        return publicationYear;
    }
    public void setPublicationYear(Integer publicationYear) {
        this.publicationYear = publicationYear;
    }

    public String getISBN() {
        return ISBN;
    }
    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }
}
