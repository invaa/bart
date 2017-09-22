package com.bart.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "CART_ITEMS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@NamedQuery(name = "CartItem.findByCartIdAndItemId",
        query = "select ci from CartItem ci where ci.cartItemPK.cartId = ?1 AND ci.cartItemPK.item.id = ?2")
public class CartItem implements Serializable {
    @EmbeddedId
    private CartItemPK cartItemPK;

    @Column(name="QTY")
    private Integer qty;
}
