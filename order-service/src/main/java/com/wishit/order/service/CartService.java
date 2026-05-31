package com.wishit.order.service;

import java.util.Map;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.persistence.Id;

import com.wishit.order.dto.ResponseWrapper;
import com.wishit.order.entity.Cart;
import com.wishit.order.entity.Cart.CartItem;
import com.wishit.order.enums.ErrorCode;
import com.wishit.order.repository.CartRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class CartService {
	
	private static final Logger logger = LoggerFactory.getLogger(CartService.class);
	@Autowired
	private CartRepo cartRepo;	
	
	private boolean isValidPrice(double price) {
        return price >0;
    }
	
	public ResponseWrapper<Cart> getCartByUserId(Cart cartElement) {
		logger.info("Cart request recieved");
		 try {
			 if (cartElement ==null|| cartElement.getUserId()<=0) {
				 
				 logger.error("Invalid cartId or userId");
				 return new ResponseWrapper<>(ErrorCode.INVALID_CART);
				 
			 }

			 
		int userId = cartElement.getUserId();
		
		logger.info("Fetching cart for userId: {}", userId);
		Map<String, CartItem> newItems = cartElement.getItems();      //new item
		Cart cart =  cartRepo.findByUserId(userId).orElse(null);
		if (cart == null) {
			logger.info("No existing cart found. Creating new cart");
		    if (newItems == null ||  newItems.size() != 1 || newItems.isEmpty())  {
		    	logger.error("Multiple products are not allowed");
		        return new ResponseWrapper<>(ErrorCode.MULTIPLE_PRODUCT_NOT_ALLOWED);
		    }	    

		    for (String productId : newItems.keySet()) {
		        CartItem newItem = newItems.get(productId);
		        logger.debug("Processing productId: {}", productId);
		        if (newItem.getProductSign() == null || 
		           (!"+".equals(newItem.getProductSign()) && !"-".equals(newItem.getProductSign()))) {
		        	logger.error("Invalid Sign");
		            return new ResponseWrapper<>(ErrorCode.INVALID_SIGN);
		        }

		        if ("-".equals(newItem.getProductSign())) {
		        	logger.error("Cannot subtract new cart");
		            return new ResponseWrapper<>(ErrorCode.CANNOT_SUBTRACT_NEW_CART);
		        }
		        if(!isValidPrice(newItem.getProductPrice())) {
		        	logger.error("Invalid price");
		        	return new ResponseWrapper<>(ErrorCode.INVALID_PRICE);
		        }
		        if (newItem.getProductName() == null || newItem.getProductName().isBlank() || newItem.getProductName().equals("0") ){
		        	logger.error("Invalid product name");
		            return new ResponseWrapper<>(ErrorCode.INVALID_PRODUCT_NAME);
		        }
		        
		        if (productId == null
		                || productId.trim().isEmpty()
		                || productId.equals("0")
		                || productId.equalsIgnoreCase("null")) {
		        	
		        	logger.error("Invalid product id");
		            return new ResponseWrapper<>(
		                    ErrorCode.INVALID_PRODUCT_ID);
		        }    

		        // valid case
		        
		        newItem.setProductQuantity(1);
		    }
		    
		    logger.info("Saving new cart");
		    return new ResponseWrapper<>(cartRepo.save(cartElement));
		}
		else {
			logger.info("Existing cart found for userId: {}", userId);
			Map<String, CartItem> items = cart.getItems();               //existing cart babbbbbyyyyyyy
			
			for(String productId :newItems.keySet()) {
				if (productId == null
				        || productId.trim().isEmpty()
				        || productId.equals("0")
				        || productId.equalsIgnoreCase("null")) {
					
					logger.error("Invalid product id");
				    return new ResponseWrapper<>(
				            ErrorCode.INVALID_PRODUCT_ID);
				}
				CartItem newItem = newItems.get(productId);
			
				if (newItem.getProductSign()== null || (!"+".equals(newItem.getProductSign()) && !"-".equals(newItem.getProductSign()))) {
					logger.error("Invalid product sign");
					return new ResponseWrapper<>(ErrorCode.INVALID_SIGN);
				}
				if(!isValidPrice(newItem.getProductPrice())) {
					logger.error("Invalid product price");
		        	return new ResponseWrapper<>(ErrorCode.INVALID_PRICE);
		        }
				if(items.containsKey(productId)) {
					logger.info("This product is already in cart");
					CartItem existingItem = items.get(productId);
					int qty = existingItem.getProductQuantity();
					
					if("+".equals(newItem.getProductSign())) {
						qty = qty+1;
						logger.info("Increasing quantity for productId: {}", productId);
					}
					else if("-".equals(newItem.getProductSign())) {
						logger.info("Decreasing quantity for productId: {}", productId);
						qty=qty-1;
					}
					existingItem.setProductQuantity(qty);
					
					
					if(qty<=0) {
						logger.warn("Removing product from the cart: {}", productId);
						items.remove(productId);
					}
				}
				else {
					if("+".equals(newItem.getProductSign())) {
						logger.info("Adding new product to existing cart");
						newItem.setProductQuantity(1);
						items.put(productId, newItem);
					}else {
						logger.error("Cannot subtract for new product");
						return new ResponseWrapper<>(ErrorCode.MULTIPLE_PRODUCT_NOT_ALLOWED);
					}
				}
					
			}
			cart.setItems(items);
			logger.info("Updating existing cart");
			return new ResponseWrapper<>(cartRepo.save(cart));
		}
		
	}catch(Exception e) {
		logger.error("Exception occurred in getCartByUserId", e);
		return new ResponseWrapper<>(ErrorCode.INTERNAL_SERVER_ERROR);
	}
		
	}
}
