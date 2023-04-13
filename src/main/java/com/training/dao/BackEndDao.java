package com.training.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.training.model.Goods;
import com.training.vo.PageSearchKey;
import com.training.vo.SalesReport;

public class BackEndDao {
	private static BackEndDao backendDao = new BackEndDao();

	public static BackEndDao getInstance() {
		return backendDao;
	}

	public List<Goods> queryGoods() {
		List<Goods> goods = new ArrayList<>();
		// querySQL SQL

		String querySQL = "SELECT goods_id , goods_name , description , price , quantity , image_name , status FROM beverage_goods ";
		// Step1:取得Connection
		try (Connection conn = DBConnectionFactory.getOracleDBConnection();
				// Step2:Create prepareStatement For SQL
				PreparedStatement stmt = conn.prepareStatement(querySQL);
				ResultSet rs = stmt.executeQuery()) {
			while (rs.next()) {
				Goods good = new Goods();
				good.setGoodsID(rs.getString("GOODS_ID"));
				good.setGoodsName(rs.getString("GOODS_NAME"));
				good.setGoodsImageName(rs.getNString("image_name"));
				good.setDESCRIPTION(rs.getNString("description"));
				good.setGoodsPrice(rs.getInt("price"));
				good.setGoodsQuantity(rs.getInt("quantity"));
				good.setStatus(rs.getNString("status"));
				goods.add(good);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return goods;
	}

	public boolean updateGood(Goods good) {
		boolean updateSuccess = false;
		// Step1:取得Connection
		try (Connection conn = DBConnectionFactory.getOracleDBConnection();) {
			// 設置交易不自動提交
			conn.setAutoCommit(false);
			// Update SQL
			String updateSQL = "UPDATE beverage_goods "
					+ "SET price = ? , STATUS = ? , QUANTITY = ? WHERE GOODS_ID = ? ";
			// Step2:Create prepareStatement For SQL
			try (PreparedStatement stmt = conn.prepareStatement(updateSQL)) {
				// Step3:將"資料欄位編號"、"資料值"作為引數傳入
				List<Goods> goods = backendDao.queryGoods();
				for (Goods querygood : goods) {
					if (querygood.getGoodsID().equals(good.getGoodsID())) {
						int quant=querygood.getGoodsQuantity()+good.getGoodsQuantity();
						int count = 1;
						stmt.setInt(count++, good.getGoodsPrice());
						stmt.setString(count++, good.getStatus());
						stmt.setInt(count++, quant);
						stmt.setString(count++, good.getGoodsID());
						// Step4:Execute SQL
						int recordCount = stmt.executeUpdate();
						updateSuccess = (recordCount > 0) ? true : false;
						// Step5:Transaction commit(交易提交)
						conn.commit();
					} else {
						continue;
					}
				}
			} catch (SQLException e) {
				// 若發生錯誤則資料 rollback(回滾)
				conn.rollback();
				throw e;
			}
		} catch (SQLException e) {
			updateSuccess = false;
			e.printStackTrace();
		}

		return updateSuccess;
	}

	public boolean createGood(Goods good) {
		boolean createSuccess = false;
		// Step1:取得Connection
		try (Connection conn = DBConnectionFactory.getOracleDBConnection();) {
			// 設置交易不自動提交
			conn.setAutoCommit(false);
			// Insert SQL
			String insertSQL = "INSERT INTO beverage_goods "
					+ " (  GOODS_ID , goods_name , price , quantity , IMAGE_NAME , status  ) "
				+ " VALUES (beverage_goods_SEQ.NEXTVAL , ? , ? , ? ,?, ? ) ";
			// Step2:Create prepareStatement For SQL
			try (PreparedStatement stmt = conn.prepareStatement(insertSQL)) {
				// Step3:將"資料欄位編號"、"資料值"作為引數傳入
				int count=1;
				stmt.setString(count++, good.getGoodsName());
				stmt.setInt(count++, good.getGoodsPrice());
				stmt.setInt(count++, good.getGoodsQuantity());
				stmt.setString(count++, good.getGoodsImageName());
				stmt.setString(count++, good.getStatus());
				// Step4:Execute SQL
				int recordCount = stmt.executeUpdate();
				createSuccess = (recordCount > 0) ? true : false;
				// Step5:Transaction commit(交易提交)
				conn.commit();
			} catch (SQLException e) {
				// 若發生錯誤則資料 rollback(回滾)
				conn.rollback();
				throw e;
			}
		} catch (SQLException e) {
			createSuccess = false;
			e.printStackTrace();
		}

		return createSuccess;
	}

	public Set<SalesReport> querySalesReport(String queryStartDate,String queryEndDate) {
		Set<SalesReport> reports = new LinkedHashSet<>();
		String insertSQL = "SELECT BO.*,bm.customer_name,BG.GOODS_NAME,bm.IDENTIFICATION_NO  FROM BEVERAGE_ORDER BO  LEFT JOIN BEVERAGE_MEMBER BM ON bo.customer_id=bm.identification_no LEFT JOIN BEVERAGE_GOODS BG ON BG.GOODS_ID=BO.GOODS_ID "
				+ " WHERE order_date > TO_DATE( ? ,'YYYY-MM-DD'  )-INTERVAL '1' DAY AND order_date < TO_DATE( ? , 'YYYY-MM-DD'  )+INTERVAL '1' DAY ORDER BY bo.order_ID ";
		// Step1:取得Connection	
		try (Connection conn = DBConnectionFactory.getOracleDBConnection();
				PreparedStatement stmt = conn.prepareStatement(insertSQL)) {
			// 設置交易不自動提交
			conn.setAutoCommit(false);
				stmt.setString(1,queryStartDate);
				stmt.setString(2, queryEndDate);
				// Step4:Execute SQL
				ResultSet rs=stmt.executeQuery();
				while (rs.next()) {
					SalesReport sr=new SalesReport();		
	    			sr.setBuyQuantity(rs.getInt("BUY_QUANTITY"));
	    			sr.setCustomerName(rs.getString("customer_name"));
	    			sr.setGoodsBuyPrice(rs.getInt("GOODS_BUY_PRICE"));
	    			sr.setBuyAmount(sr.getBuyQuantity()*sr.getGoodsBuyPrice());
	    			sr.setOrderDate(rs.getString("ORDER_DATE"));
	    			sr.setOrderID(rs.getLong("ORDER_ID"));
	    			sr.setGoodsName("GOODS_NAME");
	    			sr.setCustomerid(rs.getString("CUSTOMER_ID"));
	    			reports.add(sr);
				}
		}catch (SQLException e) {
			e.printStackTrace();
		}

		return reports;
	}
	public Goods queryGoods(String goodsId) {
		Goods good=new Goods();
		String querySQL = "SELECT goods_id , goods_name , description , price , quantity , image_name , status FROM beverage_goods WHERE goods_id = ?";
		// Step1:取得Connection
		try (Connection conn = DBConnectionFactory.getOracleDBConnection();
				// Step2:Create prepareStatement For SQL
				PreparedStatement stmt = conn.prepareStatement(querySQL);
				) {
			stmt.setString(1,goodsId);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				good.setGoodsID(rs.getString("GOODS_ID"));
				good.setGoodsName(rs.getString("GOODS_NAME"));
				good.setGoodsImageName(rs.getNString("image_name"));
				good.setDESCRIPTION(rs.getNString("description"));
				good.setGoodsPrice(rs.getInt("price"));
				good.setGoodsQuantity(rs.getInt("quantity"));
				good.setStatus(rs.getNString("status"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return good;
	}
	public List<Goods> queryGoodsBykey(PageSearchKey pagesearchkey) {
		
		List<Goods> goods = new ArrayList<>();
		PageSearchKey searchkey=pagesearchkey;
		StringBuilder sbquerySQL=new StringBuilder();
		sbquerySQL.append(" SELECT ROWNUM ROW_NUM , BG.* FROM beverage_goods BG WHERE  goods_id like ? and  lower(goods_name) like lower(?) ");
//		String querySQL =" SELECT ROWNUM ROW_NUM , BG.* FROM beverage_goods BG WHERE  goods_id like ? and  lower(goods_name) like lower(?) "; 
		String goodsId;
		String goodsName;
//		String pricemin;
//		String pricemax;
//		String status;
//		String order;
//		String quantity;
		
		if(null==searchkey.getGoodsID()||"".equals(searchkey.getGoodsID())){
			goodsId="%%";
		}else{
			goodsId="%"+searchkey.getGoodsID()+"%";
			
		}	
		if(null==searchkey.getGoodsName()||"".equals(searchkey.getGoodsName())){
			goodsName="%%";
		}else{
			goodsName="%"+searchkey.getGoodsName()+"%";
			
		}
		
		if(null==searchkey.getGoodstatus()){
			
		}
		else if(!searchkey.getGoodstatus().isEmpty()){
			sbquerySQL.append(" and status = "+searchkey.getGoodstatus());
//			status=" and status = "+searchkey.getGoodstatus();
//			querySQL+=status;
		}
			
		if(null==searchkey.getStockQuantity()){
			
		}else if(!searchkey.getStockQuantity().isEmpty()){
			sbquerySQL.append(" and QUANTITY < "+searchkey.getStockQuantity());
//			quantity=" and QUANTITY < "+searchkey.getStockQuantity();
//			querySQL+=quantity;
		}
		
		if(null==searchkey.getPriceMax()&&null==searchkey.getPriceMin()){
		}else if(!searchkey.getPriceMin().isEmpty()&&!searchkey.getPriceMax().isEmpty()){
			sbquerySQL.append(" and price between "+searchkey.getPriceMin()+" and "+searchkey.getPriceMax());
//			String pricerange=" and price between "+searchkey.getPriceMin()+" and "+searchkey.getPriceMax() ;
//			querySQL+=pricerange;
		}else{
			if(null==searchkey.getPriceMin()||!searchkey.getPriceMin().isEmpty()){
				sbquerySQL.append(" and price < "+searchkey.getPriceMin());
//				pricemin=" and price < "+searchkey.getPriceMin();
//				querySQL+=pricemin;			
			}else if(null==searchkey.getPriceMax()||!searchkey.getPriceMax().isEmpty()){
				sbquerySQL.append(" and price > "+searchkey.getPriceMax());
//				pricemax=" and price > "+searchkey.getPriceMax();
//				querySQL+=pricemax;
			}
		}
		if(null==searchkey.getPriceOrder()){
		}else if(searchkey.getPriceOrder().equals("0")){
			sbquerySQL.append(" order by price desc ");
//			order=" order by price desc ";
//			querySQL+=order;
		}else if(searchkey.getPriceOrder().equals("1")){
			sbquerySQL.append(" order by price ");
//			order=" order by price ";
//			querySQL+=order;
		}
		// Step1:取得Connection
		try (Connection conn = DBConnectionFactory.getOracleDBConnection();
				// Step2:Create prepareStatement For SQL
				PreparedStatement stmt = conn.prepareStatement(sbquerySQL.toString());){
			int count=1;
			stmt.setString(count++,goodsId);
			stmt.setString(count++,goodsName);
//			stmt.setInt(count++, startRowNo);
//			stmt.setInt(count++, endRowNo);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Goods good=new Goods();
				good.setGoodsID(rs.getString("GOODS_ID"));
				good.setGoodsName(rs.getString("GOODS_NAME"));
				good.setGoodsImageName(rs.getNString("image_name"));
				good.setDESCRIPTION(rs.getNString("description"));
				good.setGoodsPrice(rs.getInt("price"));
				good.setGoodsQuantity(rs.getInt("quantity"));
				good.setStatus(rs.getNString("status"));
				goods.add(good);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return goods;
	}
public List<Goods> paginationBykey(PageSearchKey pagesearchkey) {
		
		List<Goods> goods = new ArrayList<>();
		PageSearchKey searchkey=pagesearchkey;
		String querySQL ; 
		String goodsId;
		String goodsName;
		String pricemin;
		String pricemax;
		String status;
		String order;
		String pageNo;
		String quantity;
		
		if(null==searchkey.getPageNo()||searchkey.getPageNo().isEmpty()){
			querySQL=" SELECT * FROM ( SELECT ROWNUM ROW_NUM , BG.* FROM beverage_goods BG WHERE  goods_id like ? and  lower(goods_name) like lower(?) ";
			pageNo="1";
		}else{
			querySQL=" SELECT * FROM ( SELECT ROWNUM ROW_NUM , BG.* FROM beverage_goods BG WHERE  goods_id like ? and  lower(goods_name) like lower(?) ";
			pageNo=searchkey.getPageNo();
		}
			
		
		int endRowNo=Integer.parseInt(pageNo)*6;
		int startRowNo=endRowNo-5;
		if(null==searchkey.getGoodsID()||"".equals(searchkey.getGoodsID())){
			goodsId="%%";
		}else{
			goodsId="%"+searchkey.getGoodsID()+"%";
			
		}	
		if(null==searchkey.getGoodsName()||"".equals(searchkey.getGoodsName())){
			goodsName="%%";
		}else{
			goodsName="%"+searchkey.getGoodsName()+"%";
			
		}
		
		if(null==searchkey.getGoodstatus()){
			
		}
		else if(!searchkey.getGoodstatus().isEmpty()){
			status=" and status = "+searchkey.getGoodstatus();
			querySQL+=status;
		}
			
		if(null==searchkey.getStockQuantity()){
			
		}else if(!searchkey.getStockQuantity().isEmpty()){
			quantity=" and QUANTITY < "+searchkey.getStockQuantity();
			querySQL+=quantity;
		}
		
		if(null==searchkey.getPriceMax()&&null==searchkey.getPriceMin()){
		}else if(!searchkey.getPriceMin().isEmpty()&&!searchkey.getPriceMax().isEmpty()){
			String pricerange=" and price between "+searchkey.getPriceMin()+" and "+searchkey.getPriceMax() ;
			querySQL+=pricerange;
		}else{
			if(null==searchkey.getPriceMin()||!searchkey.getPriceMin().isEmpty()){
				pricemin=" and price < "+searchkey.getPriceMin();
				querySQL+=pricemin;			
			}else if(null==searchkey.getPriceMax()||!searchkey.getPriceMax().isEmpty()){
				pricemax=" and price > "+searchkey.getPriceMax();
				querySQL+=pricemax;
			}
		}
		if(null==searchkey.getPriceOrder()){
		}else if(searchkey.getPriceOrder().equals("0")){
			order=" order by price desc ";
			querySQL+=order;
		}else if(searchkey.getPriceOrder().equals("1")){
			order=" order by price ";
			querySQL+=order;
		}
		
		querySQL+=" ) WHERE ROW_NUM >= " +startRowNo+" AND ROW_NUM <= "+endRowNo;
		
		// Step1:取得Connection
		try (Connection conn = DBConnectionFactory.getOracleDBConnection();
				// Step2:Create prepareStatement For SQL
				PreparedStatement stmt = conn.prepareStatement(querySQL);){
			int count=1;
			stmt.setString(count++,goodsId);
			stmt.setString(count++,goodsName);
//			stmt.setInt(count++, startRowNo);
//			stmt.setInt(count++, endRowNo);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Goods good=new Goods();
				good.setGoodsID(rs.getString("GOODS_ID"));
				good.setGoodsName(rs.getString("GOODS_NAME"));
				good.setGoodsImageName(rs.getNString("image_name"));
				good.setDESCRIPTION(rs.getNString("description"));
				good.setGoodsPrice(rs.getInt("price"));
				good.setGoodsQuantity(rs.getInt("quantity"));
				good.setStatus(rs.getNString("status"));
				goods.add(good);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return goods;
	}

}
