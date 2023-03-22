package com.training.vo;

import java.util.List;

public class Pagination {
private int totalPages;
private int pageSize;
private int curPage;
private String searchKeyword;
private List<Integer> pageNo;
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
