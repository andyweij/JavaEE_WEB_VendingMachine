package com.training.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.training.dao.FrontEndDao;
import com.training.formbean.GoodsOrderForm;
import com.training.model.Goods;
import com.training.vo.BuyGoodsRtn;
import com.training.vo.Pagination;
import com.training.vo.ShoppingCartGoods;
import com.training.vo.ShoppingCartGoodsInfo;

public class FrontendService {
	private static FrontendService frontendservice = new FrontendService();
	private static FrontEndDao frontenddao = FrontEndDao.getInstance();

	private FrontendService() {
	}

	public static FrontendService getInstance() {
		return frontendservice;
	}

	public Map<String, Goods> queryBuyGoods(Set<ShoppingCartGoods> shoppingCartGoods) {
		Set<String> goodsIDs = new HashSet<>();
		shoppingCartGoods.stream().forEach(g->goodsIDs.add(String.valueOf(g.getGoodsID())));
		return frontenddao.queryBuyGoods(goodsIDs);
	}

	public boolean updateGoods(BuyGoodsRtn buyRtn,Map<String,Goods> buyGoods) {
		Set<ShoppingCartGoods> cartGoods=buyRtn.getshoppingCartGoods();
		Map<ShoppingCartGoods,Integer> updateGoods=new HashMap<>();//商品：更新數量
		cartGoods.stream().forEach(g->updateGoods.put(g,(buyGoods.get(String.valueOf(g.getGoodsID())).getGoodsQuantity()-g.getBuyQuantity())));
		boolean updateSuccess = frontenddao.batchUpdateGoodsQuantity(updateGoods);
		if (updateSuccess) {
			System.out.println("商品庫存更新成功!");
		}
		return updateSuccess;
	}

	public BuyGoodsRtn stockQuantity(BuyGoodsRtn buyRtn, Map<String, Goods> queryBuyGoods) {
		int total = 0;
		Set<ShoppingCartGoods> cartGoods=buyRtn.getshoppingCartGoods();
		cartGoods.stream().forEach(g->{
			if(g.getBuyQuantity()>queryBuyGoods.get(String.valueOf(g.getGoodsID())).getGoodsQuantity()&&queryBuyGoods.get(String.valueOf(g.getGoodsID())).getGoodsQuantity()>0){	//購買數量>庫存數量=>就將購物車數量更新為庫存數量
				g.setBuyQuantity(queryBuyGoods.get(String.valueOf(g.getGoodsID())).getGoodsQuantity());
			}else if(queryBuyGoods.get(String.valueOf(g.getGoodsID())).getGoodsQuantity()==0) {
				g.setBuyQuantity(0);
			}
		});
		total = cartGoods.stream().mapToInt(g->g.getGoodsPrice()*g.getBuyQuantity()).sum();	//計算總金額
		Iterator<ShoppingCartGoods> it=cartGoods.iterator();
		//移除購買數量為0的商品
		while(it.hasNext()) {
			if(it.next().getBuyQuantity()==0) {
				it.remove();
			}
		}
		buyRtn.setTotalsprice(total);
		buyRtn.setReturnprice(buyRtn.getPayprice()-buyRtn.getTotalsprice());
		buyRtn.setshoppingCartGoods(cartGoods);//更新正確數量
				
		return buyRtn;
	}

	public boolean createGoodsOrder(BuyGoodsRtn buyRtn) {
		boolean createResult = false;
		// 建立訂單
		createResult = frontenddao.batchCreateGoodsOrder(buyRtn);
		if (createResult) {
			System.out.println("建立訂單成功!");
		}
		return createResult;
	}
	public Pagination pagInation(String pageNo,String searchkeyword) {
		Pagination pages = new Pagination();
		pages.setPageSize(6);//每頁顯示筆數
		pages.setSearchKeyword(searchkeyword);	
		if(null==pageNo||pageNo.equals("")){
			pages.setCurPage(1);
		}else{
		pages.setCurPage(Integer.parseInt(pageNo));
		}
		if(null==pages.getSearchKeyword()||""==pages.getSearchKeyword()){
			pages.setTotalPages(new BigDecimal(frontenddao.queryAllGoods().size()).divide(new BigDecimal(pages.getPageSize()), 0, RoundingMode.UP).intValue());//總頁數
		}else if(null!=pages.getSearchKeyword()||""!=pages.getSearchKeyword()){
			pages.setTotalPages(new BigDecimal(frontenddao.pageSerach(pages.getSearchKeyword(),"").size()).divide(new BigDecimal(pages.getPageSize()), 0, RoundingMode.UP).intValue());//總帶搜尋字頁數
		}	
		List<Integer> pageNoList=new ArrayList<>();
		if(pages.getTotalPages()<3){
			for(int i=1;i<=pages.getTotalPages();i++){
				pageNoList.add(i);
			}
		}else{
			if(pages.getCurPage()==1){
				for(int i=1;i<=3;i++){
					pageNoList.add(i);	
				}
			}else if(pages.getCurPage()==pages.getTotalPages()){
				for(int i=pages.getTotalPages()-2;i<=pages.getTotalPages();i++){
					pageNoList.add(i);	
				}
			}else{
				for(int i=pages.getCurPage()-1;i<=pages.getCurPage()+1;i++){
					pageNoList.add(i);	
				}
			}
		}
		pages.setPageNo(pageNoList);
		
		return pages;
	}
	
	public BuyGoodsRtn BuyGoodsRtn(GoodsOrderForm goodsorderform, ShoppingCartGoodsInfo cartGoodsInfo) {
		BuyGoodsRtn buygoodsRtn = new BuyGoodsRtn();
		buygoodsRtn.setPayprice(goodsorderform.getInputMoney());
		buygoodsRtn.setReturnprice(buygoodsRtn.getPayprice()-buygoodsRtn.getTotalsprice());
		buygoodsRtn.setshoppingCartGoods(cartGoodsInfo.getShoppingCartGoods());
		return buygoodsRtn;
	}


	public List<Goods> pageSearch(String searchKeyword, String pageNo) {
		return frontenddao.pageSerach(searchKeyword, pageNo);
	}
	
	public List<Goods> queryAllGoods(){
		return frontenddao.queryAllGoods();
	}
}
