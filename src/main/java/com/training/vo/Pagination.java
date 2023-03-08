package com.training.vo;

public class Pagination {
private int totalPages;
private int pageSize;
private int curPage;
private String searchKeyword;

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
