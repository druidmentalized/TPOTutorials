package org.blog.models;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "User")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "User_ID")
    private Long id;

    @Column(name = "Email", nullable = false, unique = true)
    private String email;

    @OneToMany(mappedBy = "author", fetch = FetchType.EAGER)
    private final Set<Article> articles = new HashSet<>();

    @OneToOne(mappedBy = "manager")
    private Blog managedBlog;

    @ManyToMany
    @JoinTable(
            name = "User_Role",
            joinColumns = @JoinColumn(name = "User_ID"),
            inverseJoinColumns = @JoinColumn(name = "Role_ID")
    )
    private final Set<Role> roles = new HashSet<>();

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public Blog getManagedBlog() {
        return managedBlog;
    }
    public void setManagedBlog(Blog managedBlog) {
        this.managedBlog = managedBlog;
    }

    public Set<Article> getArticles() {
        return articles;
    }

    public Set<Role> getRoles() {
        return roles;
    }
}
