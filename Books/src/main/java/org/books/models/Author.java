package org.books.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Author")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Author_ID")
    private Long id;

    @Column(name = "Name", nullable = false)
    private String name;

    @Column(name = "Surname", nullable = false)
    private String surname;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "Book_Author",
            joinColumns = @JoinColumn(name = "Author_ID"),
            inverseJoinColumns = @JoinColumn(name = "Book_ID")
    )
    private final List<Book> books = new ArrayList<>();

    public Author() {}

    public Author(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Author [id=").append(id)
                .append(", name=").append(name)
                .append(", surname=").append(surname)
                .append("]");

        if (!books.isEmpty()) {
            sb.append("\n  Books:");
            for (Book book : books) {
                // Print a summary for each book.
                sb.append("\n    Book [id=").append(book.getId())
                        .append(", title=").append(book.getTitle())
                        .append("]");
            }
        } else {
            sb.append("\n  No books.");
        }

        return sb.toString();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }

    public List<Book> getBooks() {
        return books;
    }
}
