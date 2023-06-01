package com.training.service;

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
	
	public Pagination pagInation(PageSearchKey page) {
		
		Pagination pagination = new Pagination();
		pagination.setPageSize(6);//每頁顯示筆數
		pagination.setTotalPages((int)Math.ceil((double)backenddao.queryGoodsBykey(page).size()/pagination.getPageSize()));//總頁數
		
		if(null==page.getPageNo()||page.getPageNo()==""||Integer.parseInt(page.getPageNo()) >pagination.getTotalPages()){
			pagination.setCurPage(1);
			page.setPageNo("1");
		}else{
			pagination.setCurPage(Integer.parseInt(page.getPageNo()));
		}
		pagination.setGoodsList(backenddao.paginationBykey(page));
		List<Integer> pageNoList=new ArrayList<>();
		if(pagination.getTotalPages()<3){
			for(int i=1;i<=pagination.getTotalPages();i++){
				pageNoList.add(i);
			}
		}else{
			if(pagination.getCurPage()==1){
				for(int i=1;i<=3;i++){
					pageNoList.add(i);	
				}
			}else if(pagination.getCurPage()==pagination.getTotalPages()){
				for(int i=pagination.getTotalPages()-2;i<=pagination.getTotalPages();i++){
					pageNoList.add(i);	
				}
			}else{
				for(int i=pagination.getCurPage()-1;i<=pagination.getCurPage()+1;i++){
					pageNoList.add(i);	
				}
			}
		}
		pagination.setPageNo(pageNoList);
		return pagination;
	}
}
