package com.training.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.training.formbean.GoodsOrderForm;
import com.training.model.Goods;
import com.training.model.Member;
import com.training.service.FrontendService;
import com.training.vo.BuyGoodsRtn;
import com.training.vo.Pagination;
import com.training.vo.ShoppingCartGoodsInfo;

public class FrontendAction extends DispatchAction{
	
	private FrontendService frontendservice = FrontendService.getInstance();
	
	public ActionForward buyGoods(ActionMapping mapping, ActionForm form, 
            HttpServletRequest req, HttpServletResponse resp) throws IOException {
		GoodsOrderForm goodsorderform=(GoodsOrderForm)form;	
		HttpSession session = req.getSession();
		ShoppingCartGoodsInfo cartGoodsInfo=(ShoppingCartGoodsInfo)session.getAttribute("cartGoodsInfo");
		BuyGoodsRtn buyRtn=new BuyGoodsRtn();
		if(cartGoodsInfo.getTotalAmount()<goodsorderform.getInputMoney()){//檢查金額是否足夠
			
			buyRtn=frontendservice.BuyGoodsRtn(goodsorderform, cartGoodsInfo);
			return  mapping.findForward("vendingMachine");
		}
		
		Member mem=(Member)session.getAttribute("member");
		buyRtn.setCustomerId(mem.getIdentificationNo());
		buyRtn.setPayprice(goodsorderform.getInputMoney());
		buyRtn.setshoppingCartGoods(cartGoodsInfo.getShoppingCartGoods());
		Map<String,Goods> buyGoods=frontendservice.queryBuyGoods(cartGoodsInfo.getShoppingCartGoods());// 查詢購買品項資料庫資訊
		buyRtn=frontendservice.stockQuantity(buyRtn, buyGoods);	//檢查庫存是否足夠
		Set<Goods> goodsOrders=frontendservice.createGoodsOrder(buyRtn);//建立訂單
		boolean updateResult=frontendservice.updateGoods(goodsOrders);//更新商品庫存
		req.setAttribute("buyRtn", buyRtn);
		String updateMsg=(updateResult)?"商品購買成功":"商品購買失敗";
		return mapping.findForward("vendingMachine");
	}
	public ActionForward searchGoods(ActionMapping mapping, ActionForm form, 
        HttpServletRequest req, HttpServletResponse resp) throws IOException {
		GoodsOrderForm goodsorderform=(GoodsOrderForm)form;
		if(null==goodsorderform.getSearchKeyword()&&null==goodsorderform.getPageNo()){
			goodsorderform.setSearchKeyword("");
			goodsorderform.setPageNo("");
		}else if(null==goodsorderform.getSearchKeyword()){
			goodsorderform.setSearchKeyword("");
		}else if(null==goodsorderform.getPageNo()){
			goodsorderform.setPageNo("");
		}
		Pagination pages =frontendservice.pagInation(goodsorderform.getPageNo(),goodsorderform.getSearchKeyword()); //計算商品頁數
		req.setAttribute("pages", pages);
		List<Goods> goodsList=new ArrayList<>();
		goodsList=frontendservice.pageSearch(pages.getSearchKeyword(),String.valueOf(pages.getCurPage()));
		req.setAttribute("goodsList", goodsList);
		
		return mapping.findForward("vendingMachine");
	}
	
	public ActionForward pagInation(ActionMapping mapping, ActionForm form, 
	        HttpServletRequest req, HttpServletResponse resp) throws IOException {	
		//查詢資料庫資料筆數		
		List<Goods> allgoods = frontendservice.queryAllGoods();
	
			return mapping.findForward("vendingMachine");
		}

		public ActionForward queryallGoods(ActionMapping mapping, ActionForm form, HttpServletRequest req,
				HttpServletResponse resp) throws IOException {

			return mapping.findForward("vendingMachine");
		}
}
