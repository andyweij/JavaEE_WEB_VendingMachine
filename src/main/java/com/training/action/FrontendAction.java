package com.training.action;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.LinkedHashMap;
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
import com.training.vo.ShoppingCartGoods;
import com.training.vo.ShoppingCartGoodsInfo;

public class FrontendAction extends DispatchAction{
	
	private FrontendService frontendservice = FrontendService.getInstance();
	
	public ActionForward buyGoods(ActionMapping mapping, ActionForm form, 
            HttpServletRequest req, HttpServletResponse resp) throws IOException {
		GoodsOrderForm goodsorderform=(GoodsOrderForm)form;	
		HttpSession session = req.getSession();
		ShoppingCartGoodsInfo cartGoodsInfo=(ShoppingCartGoodsInfo)session.getAttribute("cartGoodsInfo");
		BuyGoodsRtn buyRtn=new BuyGoodsRtn();			
		Member mem=(Member)session.getAttribute("member");
		buyRtn.setCustomerId(mem.getIdentificationNo());
		buyRtn.setPayprice(goodsorderform.getInputMoney());
		buyRtn.setshoppingCartGoods(cartGoodsInfo.getShoppingCartGoods());

		
// 查詢購買品項資料庫資訊	
//檢查金額是否足夠
//建立訂單
//更新商品庫存

		return mapping.findForward("vendingMachine");
	}
	public ActionForward searchGoods(ActionMapping mapping, ActionForm form, 
        HttpServletRequest req, HttpServletResponse resp) throws IOException {
		BigDecimal pageAllCount =frontendservice.pageCounts(); //計算商品頁數
		GoodsOrderForm goodsorderform=(GoodsOrderForm)form;

		
		return mapping.findForward("vendingMachineview");
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
