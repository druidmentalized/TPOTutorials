package org.blog.models;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "\"User\"")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "User_ID")
    private Long id;

    @Column(name = "Email", nullable = false, unique = true)
    private String email;

    @OneToMany(mappedBy = "author",cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.EAGER)
    private final Set<Article> articles = new HashSet<>();

    @OneToOne(mappedBy = "manager", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private Blog managedBlog;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "User_Role",
            joinColumns = @JoinColumn(name = "User_ID"),
            inverseJoinColumns = @JoinColumn(name = "Role_ID")
    )
    private Set<Role> roles;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("User {");
        sb.append("id=").append(id);
        sb.append(", email='").append(email).append('\'');

        if (!roles.isEmpty()) {
            sb.append(", roles=[");
            roles.forEach(role -> sb.append(role.getName()).append(", "));
            sb.setLength(sb.length() - 2);
            sb.append(']');
        } else {
            sb.append(", roles=[]");
        }

        if (managedBlog != null) {
            sb.append(", managesBlog='").append(managedBlog.getName()).append('\'');
        } else {
            sb.append(", managesBlog=null");
        }

        if (!articles.isEmpty()) {
            sb.append(", articles=[");
            articles.forEach(article -> sb.append(article.getTitle()).append(", "));
            sb.setLength(sb.length() - 2);
            sb.append(']');
        } else {
            sb.append(", articles=[]");
        }

        sb.append('}');
        return sb.toString();
    }

    @PreRemove
    private void preRemove() {
        for (Role role : roles) {
            role.getUsers().remove(this);
        }
    }
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
