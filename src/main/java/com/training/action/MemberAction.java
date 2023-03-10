package com.training.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.training.dao.FrontEndDao;
import com.training.formbean.GoodsOrderForm;
import com.training.model.Goods;
import com.training.service.FrontendService;
import com.training.vo.ShoppingCartGoods;
import com.training.vo.ShoppingCartGoodsInfo;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@MultipartConfig
public class MemberAction extends DispatchAction {

	private FrontendService frontendservice = FrontendService.getInstance();

	public ActionForward addCartGoods(ActionMapping mapping, ActionForm form, HttpServletRequest req,
			HttpServletResponse response) throws IOException {

		
		ShoppingCartGoods cartGoods=new ShoppingCartGoods();
		cartGoods.setGoodsID(Long.parseLong(req.getParameter("goodsID")));
		cartGoods.setBuyQuantity(Integer.parseInt(req.getParameter("buyQuantity")) );
		// 查詢資料庫商品並且加入購物車		
		Goods goods = frontendservice.queryByGoodsId(cartGoods.getGoodsID());
		cartGoods.setGoodsName( goods.getGoodsName());
		cartGoods.setGoodsPrice(goods.getGoodsPrice());
		System.out.println("goodsID:" + cartGoods.getGoodsID());
		System.out.println("buyQuantity:" + cartGoods.getBuyQuantity());
		List<ShoppingCartGoods> shoppingCartGoods= new ArrayList<>() ;
		HttpSession session = req.getSession();
		if (null==session.getAttribute("shoppingCartGoods")) {	
			shoppingCartGoods.add(cartGoods);

		} else {
			shoppingCartGoods = (ArrayList<ShoppingCartGoods>)session.getAttribute("shoppingCartGoods");
			if(shoppingCartGoods.contains(cartGoods)) {
				shoppingCartGoods.get(shoppingCartGoods.indexOf(cartGoods)).setBuyQuantity(shoppingCartGoods.get(shoppingCartGoods.indexOf(cartGoods)).getBuyQuantity()+cartGoods.getBuyQuantity());
			}else {
				shoppingCartGoods.add(cartGoods);
			}
		}
		session.setAttribute("shoppingCartGoods", shoppingCartGoods);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		out.println(JSONArray.fromObject(shoppingCartGoods));
		out.flush();
		out.close();
		return null;
	}

	public ActionForward queryCartGoods(ActionMapping mapping, ActionForm form, HttpServletRequest req,
			HttpServletResponse response) throws IOException {
		ShoppingCartGoodsInfo cartGoodsInfo = new ShoppingCartGoodsInfo();
		HttpSession session = req.getSession();
		session.removeAttribute("cartGoodsInfo");
		List<ShoppingCartGoods> shoppingCartGoods=(ArrayList<ShoppingCartGoods>)session.getAttribute("shoppingCartGoods");
		if(null==shoppingCartGoods) {
			System.out.println("購物車無選購商品");
		}else {
			System.out.println("-----購物車商品-----");
			shoppingCartGoods.stream().forEach(i->System.out.println( "商品編號:"+i.getGoodsID()+"\n商品名稱:"+i.getGoodsName()+"\n商品價格:"+i.getGoodsPrice()+"\n購買數量:"+i.getBuyQuantity()));
			cartGoodsInfo.setShoppingCartGoods(shoppingCartGoods.stream().collect(Collectors.toSet()));
			cartGoodsInfo.setTotalAmount(shoppingCartGoods.stream().mapToInt(s->s.getGoodsPrice()*s.getBuyQuantity()).sum());
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/json");
			PrintWriter out = response.getWriter();	
			out.println(JSONObject.fromObject(cartGoodsInfo));
			out.flush();
			out.close();
			session.setAttribute("cartGoodsInfo",cartGoodsInfo);	
		}

		return mapping.findForward("vendingMachine");
	}

	public ActionForward clearCartGoods(ActionMapping mapping, ActionForm form, HttpServletRequest req,
			HttpServletResponse resp) throws IOException {
		HttpSession session = req.getSession();
		System.out.println("購物車已清空!");
		session.removeAttribute("shoppingCartGoods");	
		session.removeAttribute("cartGoodsInfo");
		return null;
	}
}
