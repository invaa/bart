package com.bart.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.io.Serializable;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Embeddable
@EqualsAndHashCode
public class CartItemPK implements Serializable {
    @Column(name="CART_ID")
    private UUID cartId;

    @OneToOne
    @JoinColumn(name="UUID")
    private Item item;

}
