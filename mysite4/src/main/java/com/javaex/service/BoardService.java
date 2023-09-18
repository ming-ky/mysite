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
	
	public List<BoardVo> getBoardList(){
		System.out.println("@Service");
		return boardDao.boardSelect();
	}

	public BoardVo getBoard(int no) {
		// TODO Auto-generated method stub
		return null;
	}

}
