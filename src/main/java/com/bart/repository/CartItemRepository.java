package com.bart.repository;

import com.bart.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, UUID> {
    CartItem findByCartIdAndItemId(final UUID cartId, final UUID itemId);
}