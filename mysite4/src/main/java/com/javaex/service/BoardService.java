package com.javaex.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaex.dao.BoardDao;
import com.javaex.vo.BoardVo;

@Service
public class BoardService {

	@Autowired
	private BoardDao boardDao;
	
	//게시판 리스트 가져오기
	public List<BoardVo> getBoardList(){
		System.out.println("BoardService.getBoardList()");
		
		List<BoardVo> boardList = boardDao.boardSelectList();
		
		return boardList;
		
	}
	
	
	//글 1개 가져오기
	public BoardVo getBoard(int no) {
		System.out.println("BoardService.getBoard()");
		
		//글읽기 비지니스로직
		//    조회수 증가
		int count = boardDao.updateHit(no);
		
		//    글1개가져오기
		BoardVo boardVo = boardDao.boardSelectOne(no);
		
		
		return boardVo;
	}
	
}