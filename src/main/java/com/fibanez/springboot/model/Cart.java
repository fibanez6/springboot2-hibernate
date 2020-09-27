package com.fibanez.springboot.model;

import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="CART")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * It's also possible to mark the one-to-many side as the owning side, and many-to-one side as the inverse side.
     * Although this is not a recommended practice.
     */
    @OneToMany(mappedBy="cart")
    private Set<Item> items;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Item> getItems() {
        return items;
    }

    public void addItem(Item item) {
        items.add(item);
        item.setCart(this);
    }
}
