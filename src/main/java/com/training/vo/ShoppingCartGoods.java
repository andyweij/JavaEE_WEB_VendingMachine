package com.training.vo;


public class ShoppingCartGoods {
	private long goodsID;
	private String goodsName;
	private int goodsPrice;
	private int buyQuantity;
	public long getGoodsID() {
		return goodsID;
	}
	public void setGoodsID(long goodsID) {
		this.goodsID = goodsID;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public int getGoodsPrice() {
		return goodsPrice;
	}
	public void setGoodsPrice(int goodsPrice) {
		this.goodsPrice = goodsPrice;
	}
	public int getBuyQuantity() {
		return buyQuantity;
	}
	public void setBuyQuantity(int buyQuantity) {
		this.buyQuantity = buyQuantity;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (goodsID ^ (goodsID >>> 32));
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ShoppingCartGoods other = (ShoppingCartGoods) obj;
		if (goodsID != other.goodsID)
			return false;
		return true;
	}
	
}
