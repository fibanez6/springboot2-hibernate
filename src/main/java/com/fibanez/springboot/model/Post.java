package com.fibanez.springboot.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="POST")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    /**
     * The best way to map a @OneToMany association is to rely on the @ManyToOne side to propagate all entity state changes
     */
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostComment> comments = new ArrayList<>();

    /**
     * The tags association in the Post entity only defines the PERSIST and MERGE cascade types.
     * the REMOVE entity state transition doesn't make any sense for a @ManyToMany
     */
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "POST_TAG",
        joinColumns = @JoinColumn(name = "post_id"),
        inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<PostComment> getComments() {
        return comments;
    }

    public void addComment(PostComment comment) {
        comments.add(comment);
        comment.setPost(this);
    }

    public void removeComment(PostComment comment) {
        comments.remove(comment);
        comment.setPost(null);
    }

    public void addTag(Tag tag) {
        tags.add(tag);
        tag.getPosts().add(this);
    }

    public void removeTag(Tag tag) {
        tags.remove(tag);
        tag.getPosts().remove(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return Objects.equals(id, post.id) &&
        Objects.equals(title, post.title) &&
        Objects.equals(comments, post.comments) &&
        Objects.equals(tags, post.tags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, comments, tags);
    }
}
