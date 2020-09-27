package com.fibanez.springboot.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="ITEM")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * it's a good practice to mark many-to-one side as the owning side.
     * In other words, Items would be the owning side and Cart the inverse side.
     * By including the mappedBy attribute in the Cart class, we mark it as the inverse side.
     * At the same time, we also annotate the Items.cart field with @ManyToOne, making Items the owning side.
     * Going back to our “inconsistency” example, now Hibernate knows that the item2‘s reference is more important and will save item2‘s reference to the database.
     */
    @ManyToOne
    @JoinColumn(name="cart_id", nullable=false)
    //@Column(name="cart") not allowed on a @ManyToOne property
    private Cart cart;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }
}
