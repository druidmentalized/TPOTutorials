package org.books.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "Publisher")
public class Publisher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Publisher_ID")
    private Integer id;

    @Column(name = "Name", nullable = false)
    private String name;

    @Column(name = "Address", nullable = false)
    private String address;

    @Column(name = "PhoneNumber", nullable = false)
    private String phoneNumber;

    @OneToMany(mappedBy = "publisher", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Book> books;

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(id).append(". ").append(name).append("\n")
                .append("Address: ").append(address).append("\n")
                .append("PhoneNumber: ").append(phoneNumber).append("\n")
                .append("Books: \n");

        for (Book book : books) {
            stringBuilder.append("    ").append(book).append("\n");
        }

        return stringBuilder.toString();
    }

    public Integer getId() {
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
