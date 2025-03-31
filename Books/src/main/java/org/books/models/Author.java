package org.books.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "Author")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Author_ID")
    private int id;

    @Column(name = "Name", nullable = false)
    private String name;

    @Column(name = "Surname", nullable = false)
    private String surname;

    @ManyToMany
    @JoinTable(
            name = "Book_Author",
            joinColumns = @JoinColumn(name = "Author_ID"),
            inverseJoinColumns = @JoinColumn(name = "Book_ID")
    )
    private List<Book> books;


    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(id).append(". ").append(name).append(" ").append(surname).append("\n");

        //showing all books made by author
        for (Book book : books) {
            stringBuilder.append("    ").append(book).append("\n");
        }

        return stringBuilder.toString();
    }

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getSurname() {
        return surname;
    }
    public List<Book> getBooks() {
        return books;
    }
}
