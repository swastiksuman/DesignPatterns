package com.examples.stream;

public class Cart {
	private String cartId;
	private String imei;
	private String mdn;
	
	
	
	public Cart(String cartId, String imei, String mdn) {
		super();
		this.cartId = cartId;
		this.imei = imei;
		this.mdn = mdn;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getCartId() {
		return cartId;
	}

	public void setCartId(String cartId) {
		this.cartId = cartId;
	}

	public String getMdn() {
		return mdn;
	}

	public void setMdn(String mdn) {
		this.mdn = mdn;
	}

}
