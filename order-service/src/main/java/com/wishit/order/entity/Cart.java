package com.wishit.order.entity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.util.Map;
import java.util.UUID;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MapKeyColumn;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;


@Entity
@Table(name= "Cart")

public class Cart {
	@Id
	@GeneratedValue(generator = "UUID")
	
	private UUID cartId;
	private int userId;
	
	@ElementCollection
	private Map<String, CartItem> items;
	
	@Embeddable
	public static class CartItem {
		private String productName;
		private double productPrice;
		private int productQuantity;
		@Transient
		private String productSign;

		public String getProductName() {
			return productName;
		}

		public double getProductPrice() {
			return productPrice;
		}

		public int getProductQuantity() {
			return productQuantity;
		}
		public String getProductSign() {
			return productSign;
		}
		public void setProductQuantity(int productQuantity) {
			this.productQuantity = productQuantity;
		}

		public void setProductSign(String productSign) {
			this.productSign = productSign;
		}

		public void setProductName(String productName) {
			this.productName = productName;
		}

		public void setProductPrice(double productPrice) {
			this.productPrice = productPrice;
		}
	}
	
	public UUID getCartId() {
		return cartId;
	}

	public int getUserId() {
		return userId;
	}
	
	public void setCartId(UUID cartId) {
		this.cartId = cartId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public Map<String, CartItem> getItems() {
	    return items;
	}

	public void setItems(Map<String, CartItem> items) {
	    this.items = items;
	}
}