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

    @OneToMany(mappedBy = "publisher", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private final List<Book> books = new ArrayList<>();

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("Publisher [id=").append(id).
                append(", name=").append(name).
                append(", address=").append(address).
                append(", phoneNumber=").append(phoneNumber).append("]\n");

        for (Book book : books) {
            stringBuilder.append("    ").append(book).append("\n");
        }

        if (books.isEmpty()) {
            stringBuilder.append("    ").append("None. ");
        }

        stringBuilder.delete(stringBuilder.length() - 1, stringBuilder.length());

        return stringBuilder.toString();
    }

    public Long getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getAddress() {
        return address;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
}
