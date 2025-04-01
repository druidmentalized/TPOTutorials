package org.blog.models;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Blog")
public class Blog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Blog_ID")
    private Long id;

    @Column(name = "Name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "blog", fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private final Set<Article> articles = new HashSet<>();

    @OneToOne
    @JoinColumn(name = "Manager_ID")
    private User manager;

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Blog {")
                .append("id=").append(id)
                .append(", name='").append(name).append('\'');

        if (manager != null) {
            builder.append(", managerEmail='").append(manager.getEmail()).append('\'');
        } else {
            builder.append(", managerEmail=null");
        }

        if (!articles.isEmpty()) {
            builder.append(", articles=[");
            articles.forEach(article -> builder.append(article.getTitle()).append(", "));
            builder.setLength(builder.length() - 2); // remove last comma and space
            builder.append("]");
        } else {
            builder.append(", articles=[]");
        }

        builder.append("}");
        return builder.toString();
    }

    @PreRemove
    private void preRemove() {
        manager.setManagedBlog(null);
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

    public Set<Article> getArticles() {
        return articles;
    }

    public User getManager() {
        return manager;
    }
    public void setManager(User manager) {
        this.manager = manager;
    }
}
