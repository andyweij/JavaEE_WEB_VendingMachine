package com.training.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.training.model.Goods;
import com.training.model.Member;

public class MemberDao {
	private static MemberDao memberDao = new MemberDao();
	
	public static MemberDao getInstance() {
		return memberDao;
	}
	public Goods queryByGoodsId(Long goodsId) {
		String querysql = "SELECT * FROM BEVERAGE_GOODS WHERE GOODS_ID = ?";
		Goods good = new Goods();
		try (Connection conn = DBConnectionFactory.getOracleDBConnection();
				PreparedStatement pstmt = conn.prepareStatement(querysql)) {
//				conn.setAutoCommit(false);			
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
						e.printStackTrace();
					}

				} catch (SQLException e) {
			e.printStackTrace();
		}
		return good;
	
	}
	public Member ByIdentificationNo(String identificationNo) {
		Member member = null;
		String querysql = "SELECT * FROM BEVERAGE_MEMBER WHERE identification_no = ?";
		try (Connection conn = DBConnectionFactory.getOracleDBConnection();
				PreparedStatement pstmt = conn.prepareStatement(querysql)) {
//			conn.setAutoCommit(false);
			pstmt.setString(1, identificationNo);
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					member = new Member();
					member.setCustomerName(rs.getString("CUSTOMER_NAME"));
					member.setIdentificationNo(rs.getString("IDENTIFICATION_NO"));
					member.setPassword(rs.getString("PASSWORD"));
				}
			} catch (SQLException e) {
				e.printStackTrace();				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return member;
	}
	public boolean registerAccount(Member newmember) {
		
		boolean createSuccess = false;
		String querysql = "INSERT INTO beverage_member ( identification_no , password , customer_name ) VALUES ( ? , ? , ? ) ";
		try (Connection conn = DBConnectionFactory.getOracleDBConnection()) {
			conn.setAutoCommit(false);
			try(PreparedStatement pstmt = conn.prepareStatement(querysql)){
			pstmt.setString(1, newmember.getIdentificationNo());
			pstmt.setString(2, newmember.getPassword());
			pstmt.setString(3, newmember.getCustomerName());		
			int recordCount = pstmt.executeUpdate();
			createSuccess = (recordCount > 0) ? true : false;
			// Step5:Transaction commit(交易提交)
			conn.commit();
			}catch(SQLException e) {
				conn.rollback();
				throw e;
			}
		} catch (SQLException e) {
			createSuccess=false;
			e.printStackTrace();	
		}
		return createSuccess;
	}
}
