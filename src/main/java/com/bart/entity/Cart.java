package com.bart.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "CARTS")
@Getter
@Setter
@NoArgsConstructor
public class Cart implements Serializable {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "UUID", columnDefinition = "BINARY(16)")
    private UUID id;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)//, mappedBy = "cartItemPK.cart")
    @JoinColumn(name="CART_ID")
    private List<CartItem> cartItems;
    @Column(name = "CHECKED_OUT")
    private Boolean checkedOut = false;
}
