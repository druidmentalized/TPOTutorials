package org.books.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Publisher")
public class Publisher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Publisher_ID")
    private Long id;

    @Column(name = "Name", nullable = false)
    private String name;

    @Column(name = "Address", nullable = false)
    private String address;

    @Column(name = "PhoneNumber", nullable = false)
    private String phoneNumber;

    @OneToMany(mappedBy = "publisher", fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private final List<Book> books = new ArrayList<>();

    public Publisher() {}

    public Publisher(String name, String address, String phoneNumber) {
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Publisher [id=").append(id)
                .append(", name=").append(name)
                .append(", address=").append(address)
                .append(", phoneNumber=").append(phoneNumber)
                .append("]");

        if (!books.isEmpty()) {
            sb.append("\n  Books:");
            for (Book book : books) {
                // Print only a brief summary of each book.
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

    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<Book> getBooks() {
        return books;
    }
}
