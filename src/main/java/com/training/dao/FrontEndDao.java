package com.training.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.training.model.Goods;
import com.training.model.Member;
import com.training.vo.BuyGoodsRtn;
import com.training.vo.ShoppingCartGoods;

public class FrontEndDao {
	private static FrontEndDao frontendDao = new FrontEndDao();

	public static FrontEndDao getInstance() {
		return frontendDao;
	}
	public Member ByIdentificationNo(String identificationNo) {
		Member member = null;
		String querysql = "SELECT * FROM BEVERAGE_MEMBER WHERE identification_no = ?";
		try (Connection conn = DBConnectionFactory.getOracleDBConnection();
				PreparedStatement pstmt = conn.prepareStatement(querysql)) {
			conn.setAutoCommit(false);
			pstmt.setString(1, identificationNo);
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					member = new Member();
					member.setCustomerName(rs.getString("CUSTOMER_NAME"));
					member.setIdentificationNo(rs.getString("IDENTIFICATION_NO"));
					member.setPassword(rs.getString("PASSWORD"));
				}
			} catch (SQLException e) {
				conn.rollback();
				throw e;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return member;
	}

	public Goods queryByGoodsId(Long goodsId) {
		String querysql = "SELECT * FROM BEVERAGE_GOODS WHERE GOODS_ID = ?";
		Goods good = new Goods();
		try (Connection conn = DBConnectionFactory.getOracleDBConnection();
				PreparedStatement pstmt = conn.prepareStatement(querysql)) {
				conn.setAutoCommit(false);			
				int count = 1;
				pstmt.setLong(count, goodsId);
				try (ResultSet rs = pstmt.executeQuery()) {
					while (rs.next()) {
						
						good.setGoodsID(rs.getString("GOODS_ID"));
						good.setGoodsImageName(rs.getString("IMAGE_NAME"));
						good.setGoodsName(rs.getString("GOODS_NAME"));
						good.setGoodsPrice(rs.getInt("PRICE"));
						good.setGoodsQuantity(rs.getInt("QUANTITY"));
						good.setStatus(rs.getString("STATUS"));
					}
				}catch (SQLException e) {
						conn.rollback();
						throw e;
					}

				} catch (SQLException e) {
			e.printStackTrace();
		}
		return good;
	
	}
	
	public Map<String, Goods> queryBuyGoods(Set<String> goodsIDs) {
		// key:商品編號、value:商品
		Map<String, Goods> goods = new LinkedHashMap<>();
		String querysql = "SELECT * FROM BEVERAGE_GOODS WHERE GOODS_ID = ?";
		try (Connection conn = DBConnectionFactory.getOracleDBConnection();
				PreparedStatement pstmt = conn.prepareStatement(querysql)) {
			conn.setAutoCommit(false);
			int count = 1;
			Iterator<String> it = goodsIDs.iterator();
			while (it.hasNext()) {
				pstmt.setString(count, it.next());
				try (ResultSet rs = pstmt.executeQuery()) {
					while (rs.next()) {
						Goods good = new Goods();
						good.setGoodsID(rs.getString("GOODS_ID"));
						good.setGoodsImageName(rs.getString("IMAGE_NAME"));
						good.setGoodsName(rs.getString("GOODS_NAME"));
						good.setGoodsPrice(rs.getInt("PRICE"));
						good.setGoodsQuantity(rs.getInt("QUANTITY"));
						good.setStatus(rs.getString("STATUS"));
						goods.put(rs.getString("GOODS_ID") , good);
					}

				} catch (SQLException e) {
					conn.rollback();
					throw e;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return goods;
	}
	public boolean batchUpdateGoodsQuantity(Map<ShoppingCartGoods,Integer> updateGoods) {
		boolean updateSuccess = false;
		Set<ShoppingCartGoods> good =updateGoods.keySet();
		String updatesql = "UPDATE BEVERAGE_GOODS SET QUANTITY = ? WHERE GOODS_ID = ? ";
		try (Connection conn = DBConnectionFactory.getOracleDBConnection();
				PreparedStatement pstmt = conn.prepareStatement(updatesql)) {
			for (ShoppingCartGoods g : good) {
				pstmt.setInt(1, updateGoods.get(g));
				pstmt.setLong(2,g.getGoodsID());
				pstmt.addBatch();
			}
			int[] insertCounts = pstmt.executeBatch();
//			for (int c : insertCounts) {
//				System.out.println(c);
//			}
			conn.commit();
			updateSuccess = true;
		} catch (SQLException se) {
			se.getStackTrace();
		}
		return updateSuccess;
	}

	/**
	 * 建立訂單資料
	 * 
	 * @param customerID
	 * @param goodsOrders【訂單資料(key:購買商品、value:購買數量)】
	 * @return boolean
	 */
	public boolean batchCreateGoodsOrder(BuyGoodsRtn buyRtn) {
		boolean insertSuccess = false;
		Set<ShoppingCartGoods> cartGoods=buyRtn.getshoppingCartGoods();
		int orderID = 0;
		String[] col = { "ORDER_ID" };
		String insertsql = "INSERT  INTO BEVERAGE_ORDER  VALUES (beverage_order_seq.nextval,SYSDATE,?,?,?,?)";
		try (Connection conn = DBConnectionFactory.getOracleDBConnection()) {
			try (PreparedStatement pstmt = conn.prepareStatement(insertsql, col)) {
				conn.setAutoCommit(false);
				for (ShoppingCartGoods g : cartGoods) {
					int count = 1;
					pstmt.setString(count++, buyRtn.getCustomerId());
					pstmt.setLong(count++, g.getGoodsID());
					pstmt.setInt(count++, g.getGoodsPrice());
					pstmt.setInt(count++, g.getBuyQuantity());
					pstmt.addBatch();
					int[] insertCounts = pstmt.executeBatch();
//					for (int c : insertCounts) {System.out.println(c);}
					ResultSet rsKeys = pstmt.getGeneratedKeys();
					ResultSetMetaData rsmd = rsKeys.getMetaData();
					int columnCount = rsmd.getColumnCount();
					while(rsKeys.next()) {
						for(int i=1;i<=columnCount;i++) {
					orderID = rsKeys.getInt(i);
					System.out.println("新增訂單編號:" + orderID);
					}
						}	
				}		
				conn.commit();
				insertSuccess = true;
			} catch (SQLException e) {
				conn.rollback();
				throw e;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return insertSuccess;
	}
	public List<Goods> pageSerach(String searchKeyword,String pageNo){
		List<Goods> goodsList=new ArrayList<>();
		String sk = "%" + searchKeyword + "%";
		String querysql="SELECT * FROM ( SELECT ROWNUM ROW_NUM, bg.* FROM BEVERAGE_GOODS bg WHERE LOWER(GOODS_NAME) LIKE ? )  ";
		if(pageNo=="") {
			
		}else {		
		int endRowNo=Integer.parseInt(pageNo)*6;
		int startRowNo=endRowNo-5;
		querysql += " WHERE ROW_NUM >= "+startRowNo+" AND ROW_NUM <= "+endRowNo+"AND STATUS=1 ";
		}		
		try (Connection conn = DBConnectionFactory.getOracleDBConnection();
				PreparedStatement pstmt = conn.prepareStatement(querysql)) {
			conn.setAutoCommit(false);
			int count = 1;
			pstmt.setString(count++, sk);
			
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					Goods good = new Goods();
					good.setGoodsID(rs.getString("GOODS_ID"));
					good.setGoodsImageName(rs.getString("IMAGE_NAME"));
					good.setGoodsName(rs.getString("GOODS_NAME"));
					good.setGoodsPrice(rs.getInt("PRICE"));
					good.setGoodsQuantity(rs.getInt("QUANTITY"));
					good.setStatus(rs.getString("STATUS"));
					goodsList.add(good);
				}
			} catch (SQLException e) {
				conn.rollback();
				throw e;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return goodsList;
	}
	public List<Goods> queryAllGoods(){
		List<Goods> goods=new ArrayList<>();
		String querysql = "SELECT * FROM BEVERAGE_GOODS WHERE status=1 ";
		try (Connection conn = DBConnectionFactory.getOracleDBConnection();
				PreparedStatement pstmt = conn.prepareStatement(querysql)) {
			conn.setAutoCommit(false);
				try (ResultSet rs = pstmt.executeQuery()) {
					while (rs.next()) {
						Goods good = new Goods();
						good.setGoodsID(rs.getString("GOODS_ID"));
						good.setGoodsImageName(rs.getString("IMAGE_NAME"));
						good.setGoodsName(rs.getString("GOODS_NAME"));
						good.setGoodsPrice(rs.getInt("PRICE"));
						good.setGoodsQuantity(rs.getInt("QUANTITY"));
						good.setStatus(rs.getString("STATUS"));
						goods.add(good);
					}

				} catch (SQLException e) {
					conn.rollback();
					throw e;
				}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return goods;
	}
}
