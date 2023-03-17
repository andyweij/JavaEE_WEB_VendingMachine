package com.training.service;

import com.training.dao.MemberDao;
import com.training.model.Goods;

public class MemberService {
	
	private static MemberService memberService= new MemberService();
			
	private static	MemberDao memberDao=MemberDao.getInstance();
	
	public static MemberService getInstance() {
		
		return memberService;
	}
	
	public Goods queryByGoodsId(Long GoodsId) {
		return memberDao.queryByGoodsId(GoodsId);
	}
}
