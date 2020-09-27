package com.fibanez.springboot.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity(name = "PostDetails")
@Table(name = "POST_DETAILS")
public class PostDetails {

    /**
     * No longer uses a @GeneratedValue annotation
     * since the identifier is populated with the identifier of the post association.
     *
     * In a relational database system, a one-to-one table relationship links two tables
     * based on a Primary Key column in the child which is also a Foreign Key referencing
     * the Primary Key of the parent table row.
     *
     * We can say that the child table shares the Primary Key with the parent table.
     */
    @Id
    private Long id;

    @Column(name = "created_on")
    private Date createdOn;

    @Column(name = "created_by")
    private String createdBy;

    /**
     * The best way to map a @OneToOne relationship is to use @MapsId.
     * This way, you don't even need a bidirectional association since you can always fetch
     * the PostDetails entity by using the Post entity identifier.
     *
     * This way, the id property serves as both Primary Key and Foreign Key.
     * You'll notice that the @Id column no longer uses a @GeneratedValue annotation
     * since the identifier is populated with the identifier of the post association.
     */
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "id")
    private Post post;

    public PostDetails() {}

    public PostDetails(String createdBy) {
        createdOn = new Date();
        this.createdBy = createdBy;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}
