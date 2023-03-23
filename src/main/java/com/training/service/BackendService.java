package com.training.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
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
	
	public Pagination pagInation(PageSearchKey page,List<Goods> goodsList) {
		Pagination pages = new Pagination();
		pages.setPageSize(6);//每頁顯示筆數
		if(null==page.getPageNo()||page.getPageNo()==""){
			pages.setCurPage(1);
		}else{
		pages.setCurPage(Integer.parseInt(page.getPageNo()));
		}
		int endRowNo=pages.getCurPage()*6;
		int startRowNo=endRowNo-6;
		List<Goods> goods=new ArrayList<>();
		if(goodsList.size()>endRowNo){
		goods=goodsList.subList(startRowNo, endRowNo);
		}else{
		goods=goodsList.subList(startRowNo,goodsList.size());
		}
		pages.setGoodsList(goods);
//		pages.setTotalPages(new BigDecimal(backenddao.queryGoods().size()).divide(new BigDecimal(pages.getPageSize()), 0, RoundingMode.UP).intValue());//總頁數
		pages.setTotalPages((int)Math.ceil((double)backenddao.queryGoodsBykey(page).size()/pages.getPageSize()));//總頁數
		List<Integer> pageno=new ArrayList<>();
		if(pages.getTotalPages()<3){
			for(int i=1;i<=pages.getTotalPages();i++){
				pageno.add(i);
			}
		}else{
			if(pages.getCurPage()==1){
				for(int i=1;i<=3;i++){
					pageno.add(i);	
				}
			}else if(pages.getCurPage()==pages.getTotalPages()){
				for(int i=pages.getTotalPages()-2;i<=pages.getTotalPages();i++){
					pageno.add(i);	
				}
			}else{
				for(int i=pages.getCurPage()-1;i<=pages.getCurPage()+1;i++){
					pageno.add(i);	
				}
			}
		}
		pages.setPageNo(pageno);
		return pages;
	}
}
