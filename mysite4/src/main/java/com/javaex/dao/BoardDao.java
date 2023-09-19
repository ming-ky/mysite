package com.javaex.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.javaex.vo.BoardVo;

@Repository
public class BoardDao {

	// 필드
	@Autowired
	private SqlSession sqlSession;

	// 생성자, 메소드 gs

	// 메소드 일반

	// (1) 게시판 리스트
	public List<BoardVo> boardSelectList() {
		System.out.println("dao");
		List<BoardVo> boardList = sqlSession.selectList("board.selectBoard");
		return boardList;
	}

	// (2) 게시판 등록
	public void boardInsert(BoardVo boardVo) {
		sqlSession.insert("board.insertBoard", boardVo);
	}

	// (3) 게시판 읽기
	public BoardVo boardRead(int no) {
		return sqlSession.selectOne("board.leadBoard", no);
	}

	// (4) 게시판 조회수 올리기
	public void hitUp(int no) {
		sqlSession.update("board.hitUp", no);
	}

	// (5) 게시판 글 수정
	public void boardUpdate(BoardVo boardVo) {
		sqlSession.update("board.updateBoard", boardVo);
	}

	// (6) 게시판 삭제
	public void boardDelete(BoardVo boardVo) {
		sqlSession.delete("board.deleteBoard", boardVo);
	}

	// (7)게시판 검색
	public List<BoardVo> boardSearch(String search) {
		List<BoardVo> boardList = sqlSession.selectList("board.searchBoard", search);
		return boardList;
	}

	// 조회수 증가
	public int updateHit(int no) {
		System.out.println("BoardDao.updateHit()");

		int count = sqlSession.update("board.updateHit", no);

		return count;
	}

	// 글1개 가져오기
	public BoardVo boardSelectOne(int no) {
		System.out.println("BoardDao.boardSelectOne()");

		BoardVo boardVo = sqlSession.selectOne("board.selectOne", no);

		return boardVo;
	}

}
