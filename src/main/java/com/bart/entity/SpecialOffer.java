package com.bart.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "SPECIAL_OFFERS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SpecialOffer implements Serializable {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "UUID", columnDefinition = "BINARY(16)")
    private UUID id;
    @Column(name="OFFER_QTY")
    private Integer offerQty;
    @Column(name="OFFER_PRICE")
    private BigDecimal offerPrice;

    public SpecialOffer(final Integer offerQty, final BigDecimal offerPrice) {
        this.offerQty = offerQty;
        this.offerPrice = offerPrice;
    }

}
