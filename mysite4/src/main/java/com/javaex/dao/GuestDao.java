package com.javaex.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.javaex.vo.GuestVo;

@Repository
public class GuestDao {

	// 필드
	@Autowired
	private SqlSession sqlSession;

	// 생성자, 메소드 gs

	// 메소드 일반

	// (1) 방명록 리스트
	public List<GuestVo> guestSelect() {
		List<GuestVo> guestList = sqlSession.selectList("guest.selectGuest");
		return guestList;
	}	
	
	// (2) 방명록 등록
	public void listInsert(GuestVo guestVo) {
		sqlSession.insert("guest.listInsert", guestVo);
	}		
	
	// (3) 방명록 삭제
	public void listDelete(GuestVo guestVo) {
		sqlSession.delete("guest.listDelete", guestVo);
	}	
}
