package com.wishit.order.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wishit.order.entity.Cart;

public interface CartRepo extends JpaRepository<Cart,UUID> {
	Optional <Cart> findByUserId(int userId);
}
