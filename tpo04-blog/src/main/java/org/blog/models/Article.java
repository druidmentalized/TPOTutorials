package org.blog.models;

import jakarta.persistence.*;

@Entity
@Table(name = "Article")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Article_ID")
    private Long id;

    @Column(name = "Title", nullable = false)
    private String title;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Author_ID")
    private User author;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Blog_ID")
    private Blog blog;

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author=" + (author != null ? "User{id=" + author.getId() + ", email=" + author.getEmail() + "}" : "null") +
                ", blog=" + (blog != null ? "Blog{id=" + blog.getId() + ", name=" + blog.getName() + "}" : "null") +
                '}';
    }

    @PreRemove
    private void preRemove() {
        author.getArticles().remove(this);
        blog.getArticles().remove(this);
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public User getAuthor() {
        return author;
    }
    public void setAuthor(User author) {
        this.author = author;
    }

    public Blog getBlog() {
        return blog;
    }
    public void setBlog(Blog blog) {
        this.blog = blog;
    }
}
