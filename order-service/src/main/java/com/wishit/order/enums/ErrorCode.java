package com.wishit.order.enums;

public enum ErrorCode {
	
	INVALID_CART("ERR01", "Invalid cart or UserId"),
	INVALID_SIGN("ERR02", "Invalid sign for productId"),
	MULTIPLE_PRODUCT_NOT_ALLOWED("Err03",
            "Cart items cannot be empty or multiple product for new user not allowed"),
	CANNOT_SUBTRACT_NEW_CART("Err04",
            "Cannot subtract product for new cart"),
    INVALID_PRICE("Err05",
            "Price must be greater than 0"),
    INVALID_PRODUCT_NAME("Err07",
            "Product name cannot be empty"),
    INVALID_PRODUCT_ID("Err08",
            "Invalid productId"),
    PRODUCT_NOT_IN_CART("Err09",
            "Cannot subtract the product, Product is not in cart"),
    INTERNAL_SERVER_ERROR("Err99",
            "Internal server error");
	
	private final String code;
	private final String message;
	
	ErrorCode(String code, String message){
		this.code = code;
		this.message = message;
	}
	
	public String getCode() {
		return code;
	}
	
	public String getMessage() {
		return message;
	}
}
