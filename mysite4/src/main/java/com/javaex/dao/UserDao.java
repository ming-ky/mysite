package com.javaex.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.javaex.vo.UserVo;

@Repository
public class UserDao {

	// 필드
	@Autowired
	private SqlSession sqlSession;

	// 생성자, 메소드 gs

	// 메소드 일반

	// (1) 로그인용 1명 가져오기
	public UserVo select(UserVo userVo) {
		return sqlSession.selectOne("user.selectAuthUser", userVo);
	}

	// (2) 회원등록
	public void userInsert(UserVo userVo) {
		sqlSession.insert("user.insert", userVo);
	}

	// (3) 회원수정용 1명 가져오기
	public UserVo userUpdateSelect(UserVo userVo) {
		return sqlSession.selectOne("user.selectModifyUser", userVo);
		
	}

	// (4) 회원수정
	public void update(UserVo userVo) {
		sqlSession.update("user.updateUser", userVo);
	}	
	
}
