package com.training.formbean;

import org.apache.struts.action.ActionForm;

public class GoodsListForm  extends ActionForm{
	private String goodsId;
	private String goodsName;
	private String priceMax;
	private String priceMin;
	private String priceOrder;
	private String stockQuantity;
	private String goodstatus;
	private String pageNo;
	public String getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public String getPriceMax() {
		return priceMax;
	}
	public void setPriceMax(String priceMax) {
		this.priceMax = priceMax;
	}
	public String getPriceMin() {
		return priceMin;
	}
	public void setPriceMin(String priceMin) {
		this.priceMin = priceMin;
	}
	public String getPriceOrder() {
		return priceOrder;
	}
	public void setPriceOrder(String priceOrder) {
		this.priceOrder = priceOrder;
	}
	public String getStockQuantity() {
		return stockQuantity;
	}
	public void setStockQuantity(String stockQuantity) {
		this.stockQuantity = stockQuantity;
	}
	public String getGoodstatus() {
		return goodstatus;
	}
	public void setGoodstatus(String goodstatus) {
		this.goodstatus = goodstatus;
	}
	public String getPageNo() {
		return pageNo;
	}
	public void setPageNo(String pageNo) {
		this.pageNo = pageNo;
	}
	
}
