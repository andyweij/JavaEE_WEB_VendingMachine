package com.training.vo;

import java.util.List;

import com.training.model.Goods;

public class Pagination {
	private int totalPages;
	private int pageSize;
	private int curPage;
	private String searchKeyword;
	private List<Integer> pageNo;
	private List<Goods> goodsList;

	public List<Goods> getGoodsList() {
		return goodsList;
	}

	public void setGoodsList(List<Goods> goodsList) {
		this.goodsList = goodsList;
	}

	public List<Integer> getPageNo() {
		return pageNo;
	}

	public void setPageNo(List<Integer> pageNo) {
		this.pageNo = pageNo;
	}

	public String getSearchKeyword() {
		return searchKeyword;
	}

	public void setSearchKeyword(String searchKeyword) {
		this.searchKeyword = searchKeyword;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getCurPage() {
		return curPage;
	}

	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}

}
