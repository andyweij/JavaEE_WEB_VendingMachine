package com.training.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import com.training.dao.FrontEndDao;
import com.training.formbean.GoodsOrderForm;
import com.training.model.Goods;
import com.training.vo.BuyGoodsRtn;
import com.training.vo.ShoppingCartGoods;

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

	public boolean buyGoods(Set<Goods> goodsOrders) {

		boolean updateSuccess = frontenddao.batchUpdateGoodsQuantity(goodsOrders);
		if (updateSuccess) {
			System.out.println("商品庫存更新成功!");
		}
		return updateSuccess;
	}

	public BuyGoodsRtn priceCalc(BuyGoodsRtn buyRtn, Map<String, Goods> queryBuyGoods) {
		int total = 0;
		StringBuffer sb = new StringBuffer();
		Set<ShoppingCartGoods>CartGoods=buyRtn.getshoppingCartGoods();
		for(ShoppingCartGoods good:CartGoods) {
			if (good.getBuyQuantity() > 0 && queryBuyGoods.get(String.valueOf(good.getGoodsID()) ).getGoodsQuantity() >= good.getBuyQuantity()) {
				total += queryBuyGoods.get(String.valueOf(good.getGoodsID())).getGoodsPrice()
						* good.getBuyQuantity();
				sb.append("<br/> 商品名稱：" + queryBuyGoods.get(String.valueOf(good.getGoodsID())).getGoodsName() + " <br/> 商品金額:"
					+ queryBuyGoods.get(String.valueOf(good.getGoodsID())).getGoodsPrice() + " <br/> 購買數量:" +	good.getBuyQuantity() + "\n ");
			} else if (good.getBuyQuantity() > 0 && queryBuyGoods.get(String.valueOf(good.getGoodsID()))
					.getGoodsQuantity() < good.getBuyQuantity()) {
				total += queryBuyGoods.get(String.valueOf(good.getGoodsID())).getGoodsPrice()
						* queryBuyGoods.get(String.valueOf(good.getGoodsID())).getGoodsQuantity();
			sb.append("<br/> 商品名稱：" + queryBuyGoods.get(String.valueOf(good.getGoodsID())).getGoodsName() + "<br/>  商品金額:"
					+ queryBuyGoods.get(String.valueOf(good.getGoodsID())).getGoodsPrice() + "<br/>  購買數量:" +	queryBuyGoods.get(String.valueOf(good.getGoodsID())).getGoodsQuantity() + "\n ");
		}else if(good.getBuyQuantity() > 0 && queryBuyGoods.get(String.valueOf(good.getGoodsID()))
					.getGoodsQuantity() ==0){
			total+=0;
			sb.append("<br/> 商品名稱：" + queryBuyGoods.get(String.valueOf(good.getGoodsID())).getGoodsName() + "<br/>  商品金額:"
					+ queryBuyGoods.get(String.valueOf(good.getGoodsID())).getGoodsPrice() + "<br/>  購買數量:" +	queryBuyGoods.get(String.valueOf(good.getGoodsID())).getGoodsQuantity() + "\n ");
			}
		}

		if(buyRtn.getPayprice()>=total) {
		buyRtn.setTotalsprice(total);
		buyRtn.setReturnprice(buyRtn.getPayprice()-total);
		buyRtn.setGoodsinf(sb.toString());
		}else {
			buyRtn.setTotalsprice(total);
			buyRtn.setReturnprice(buyRtn.getPayprice());
			buyRtn.setGoodsinf("金額不足，無法購買");
		}
		return buyRtn;
	}

	public Set<Goods> createGoodsOrder(BuyGoodsRtn buyRtn) {
		boolean createResult = false;
		Set<String> goodsIDs = new HashSet<>();
		Map<String, Integer> buyGoodsquantity = new HashMap<>();
		Set<Goods> buyGoods=buyRtn.getCarGoods().keySet();
		Map<Goods, Integer> goodsOrders = new HashMap<>();
		buyGoods.stream().forEach(g->{
			if (g.getGoodsQuantity() >= buyRtn.getCarGoods().get(g)) {
				goodsOrders.put(g, buyRtn.getCarGoods().get(g));
			} else if(g.getGoodsQuantity()==0){}else {
				goodsOrders.put(g, g.getGoodsQuantity());
			}
		});
		
		Set<Goods> goods = goodsOrders.keySet();
		for (Goods good : goods) {
			good.setGoodsQuantity(good.getGoodsQuantity() - goodsOrders.get(good));
		}
		// 建立訂單
		createResult = frontenddao.batchCreateGoodsOrder(buyRtn.getCustomerId(), goodsOrders);
		if (createResult) {
			System.out.println("建立訂單成功!");
		}
		return goods;
	}
	public BigDecimal pageCounts() {
		BigDecimal pagecounts=new BigDecimal(frontenddao.queryAllGoods().size()).divide(new BigDecimal("6"), 0, RoundingMode.UP);;		
		return pagecounts;
	}
	
//	public BuyGoodsRtn BuyGoodsRtn(GoodsOrderForm goodsorderform, int totalprice, Map<String, Goods> queryBuyGoods) {
//		BuyGoodsRtn buygoodsRtn = new BuyGoodsRtn();
//		if (goodsorderform.getPayPrice() >= totalprice) {
//			buygoodsRtn.setPayprice(goodsorderform.getPayPrice());
//			buygoodsRtn.setTotalsprice(totalprice);
//			buygoodsRtn.setReturnprice(goodsorderform.getPayPrice() - totalprice);
//			StringBuffer sb = new StringBuffer();
//			
//			queryBuyGoods.values().stream().forEach(g ->
//			sb.append("商品名稱：" + g.getGoodsName() + " 商品金額:"
//					+ g.getGoodsPrice() + " 購買數量:" +	g.getGoodsQuantity() + "\n"));
//			
//			buygoodsRtn.setGoodsinf(sb.toString());
//		} else {
//			buygoodsRtn.setPayprice(goodsorderform.getPayPrice());
//			buygoodsRtn.setTotalsprice(totalprice);
//			buygoodsRtn.setReturnprice(goodsorderform.getPayPrice());
//			buygoodsRtn.setGoodsinf("");
//		}
//		return buygoodsRtn;
//	}
	public Goods queryByGoodsId(Long GoodsId) {
		return frontenddao.queryByGoodsId(GoodsId);
	}

	public List<Goods> pageSearch(String searchKeyword, String pageNo) {
		return frontenddao.pageSerach(searchKeyword, pageNo);
	}
	
	public List<Goods> queryAllGoods(){
		return frontenddao.queryAllGoods();
	}
}
