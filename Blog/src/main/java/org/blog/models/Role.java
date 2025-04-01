package org.blog.models;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "Role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Role_ID")
    private Long id;

    @Column(name = "Name", nullable = false)
    private String name;

    @ManyToMany(mappedBy = "roles", fetch = FetchType.EAGER)
    private Set<User> users;

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @PreRemove
    private void preRemove() {
        for (User user : users) {
            user.getRoles().remove(this);
        }
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

    public Set<User> getUsers() {
        return users;
    }
}
