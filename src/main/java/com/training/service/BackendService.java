package com.training.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Set;

import com.training.dao.BackEndDao;
import com.training.model.Goods;
import com.training.vo.PageSearchKey;
import com.training.vo.Pagination;
import com.training.vo.SalesReport;

public class BackendService {

	private static BackendService backendservice = new BackendService();
	private static BackEndDao backenddao = BackEndDao.getInstance();

	private BackendService() {
	}

	public static BackendService getInstance() {
		return backendservice;

	}

	public List<Goods> queryGoods() {

		return backenddao.queryGoods();
	}

	public boolean modifyGood(Goods good) {

		return backenddao.updateGood(good);
	}

	public boolean createGood(Goods good) {

		return backenddao.createGood(good);
	}

	public Set<SalesReport> querySalesReport(String queryStartDate,String queryEndDate) {

		return backenddao.querySalesReport(queryStartDate,queryEndDate);
	}
	public Goods queryGoodsById(String goodsId) {
		
		return backenddao.queryGoods(goodsId);
	}
	public List<Goods> queryGoodsBykey(PageSearchKey pagesearchkey) {
		
		return backenddao.queryGoodsBykey(pagesearchkey);
	}
	
	public Pagination pagInation(String pageNo) {
		Pagination pages = new Pagination();
		pages.setPageSize(6);//每頁顯示筆數
		if(null==pageNo||pageNo==""){
			pages.setCurPage(1);
		}else{
		pages.setCurPage(Integer.parseInt(pageNo));
		}
//		pages.setTotalPages(new BigDecimal(backenddao.queryGoods().size()).divide(new BigDecimal(pages.getPageSize()), 0, RoundingMode.UP).intValue());//總頁數
		pages.setTotalPages((int)Math.ceil(backenddao.queryGoods().size()/pages.getPageSize()));//總頁數
		return pages;
	}
}
