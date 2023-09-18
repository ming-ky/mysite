package com.javaex.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.javaex.dao.BoardDao;
import com.javaex.service.BoardService;
import com.javaex.vo.BoardVo;
import com.javaex.vo.UserVo;

@Controller
@RequestMapping("/brc")
public class BoardController {
	
	@Autowired
	private BoardDao boardDao;
	
	
	@RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST })
	public String addList(Model model) {
		System.out.println("게시판 리스트");	
		List<BoardVo> bList = boardDao.boardSelect();
		model.addAttribute("bList", bList);
		return "board/list";
	}

	@RequestMapping(value = "/writeForm", method = { RequestMethod.GET, RequestMethod.POST })
	public String writeForm() {
		System.out.println("게시판 작성폼");
		return "board/writeForm";
	}

	@RequestMapping(value = "/boardInsert", method = { RequestMethod.GET, RequestMethod.POST })
	public String boardInsert(@ModelAttribute BoardVo boardVo, HttpSession session) {
		System.out.println("게시판 등록");
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		boardVo.setUserNo(authUser.getNo());
		boardDao.boardInsert(boardVo);
		return "redirect:list";
	}
	

	@RequestMapping(value = "/read", method = { RequestMethod.GET, RequestMethod.POST })
	public String boardRead(@RequestParam("no") int no, @RequestParam("hit") int hit, Model model) {
		System.out.println("게시판 글읽기");
		// 파라미터 hit에 1이 들어있을때만 조회수 상승, 수정이나 수정후 게시글창에서 조회수가 상승하지 않기 위함
		if (hit == 1) {
			boardDao.hitUp(no);
			System.out.println("조회수 1 상승");
		}
		BoardVo boardVo = boardDao.boardRead(no);
		model.addAttribute("boardRead", boardVo);
		return "board/read";
	}

	@RequestMapping(value = "/modifyForm", method = { RequestMethod.GET, RequestMethod.POST })
	public String modifyForm(@RequestParam("no") int no, Model model) {
		System.out.println("게시판 글 수정폼");
		BoardVo boardVo = boardDao.boardRead(no);
		model.addAttribute("boardRead", boardVo);
		return "board/modifyForm";
	}

	@RequestMapping(value = "/modify", method = { RequestMethod.GET, RequestMethod.POST })
	public String modify(@ModelAttribute BoardVo boardVo) {
		System.out.println("게시판 글 수정");
		boardDao.boardUpdate(boardVo);
		int no = boardVo.getNo();
		return "redirect:read?no=" + no + "&hit=0";
	}

	@RequestMapping(value = "/delete", method = { RequestMethod.GET, RequestMethod.POST })
	public String boardDelete(@ModelAttribute BoardVo boardVo) {
		System.out.println("게시판 글 삭제");
		boardDao.boardDelete(boardVo);
		return "redirect:list";
	}

	@RequestMapping(value = "/search", method = { RequestMethod.GET, RequestMethod.POST })
	public String boardSearch(@RequestParam("search") String search, Model model) {
		System.out.println("게시판 글 검색");
		List<BoardVo> sList = boardDao.boardSearch(search);
		model.addAttribute("bList", sList);
		return "board/list";
	}

}
