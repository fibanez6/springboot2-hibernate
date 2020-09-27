package com.fibanez.springboot.model;

import org.hibernate.annotations.NaturalId;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity(name = "Tag")
@Table(name = "TAG")
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NaturalId
    private String name;

    /**
     * The mappedBy attribute of the posts association in the Tag entity marks that,
     * in this bidirectional relationship, the Post entity owns the association.
     * This is needed since only one side can own a relationship, and changes are only propagated
     * to the database from this particular side.
     *
     * The Set is to be preferred, as using a List with @ManyToMany is less efficient.
     */
    @ManyToMany(mappedBy = "tags")
    private Set<Post> posts = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Post> getPosts() {
        return posts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tag tag = (Tag) o;
        return Objects.equals(id, tag.id) &&
        Objects.equals(name, tag.name) &&
        Objects.equals(posts, tag.posts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, posts);
    }
}
