package com.javaex.controller;

	
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.javaex.service.RBoardService;
import com.javaex.vo.RBoardVo;
import com.javaex.vo.UserVo;

	@Controller
	@RequestMapping("/rboard")
	public class RBoardController {

		@Autowired
		private RBoardService rBoardService;

		//리스트
		@RequestMapping(value="/list", method= { RequestMethod.GET, RequestMethod.POST})
		public String list(Model model) {
			System.out.println("RBoardController.list()");

			List<RBoardVo> boardList =  rBoardService.boardList("");
			
			model.addAttribute("boardList", boardList);
			
			return "rboard/list";
		}
		
		//검색
		@RequestMapping(value="/search", method= { RequestMethod.GET, RequestMethod.POST})
		public String search(@RequestParam(value="keyword") String keyword, Model model) {
			System.out.println("RBoardController.search()");
			
			List<RBoardVo> boardList = rBoardService.boardList(keyword);
			
			model.addAttribute("boardList", boardList);

			return "rboard/list";
		}
		
		//작성폼
		@RequestMapping(value="/writeForm", method= { RequestMethod.GET, RequestMethod.POST})
		public String writeForm() {
			System.out.println("RBoardController.writeForm()");
			
			return "rboard/writeForm";
		}
		
		//작성
		@RequestMapping(value="/write", method= { RequestMethod.GET, RequestMethod.POST})
		public String write(@ModelAttribute RBoardVo rBoardVo, HttpSession session) {
			System.out.println("RBoardController.write()");

			UserVo authUser = (UserVo)session.getAttribute("authUser");
			rBoardVo.setUserNo(authUser);
			
			rBoardService.boardInsert(rBoardVo);

			return "redirect:/rBoard/list";
		}
		
		//상세보기
		@RequestMapping(value="/detail/{no}", method= { RequestMethod.GET, RequestMethod.POST})
		public String detail(@PathVariable(value="no") int boardNo, Model model) {
			System.out.println("RBoardController.detail()");
			
			RBoardVo rBoardVo = rBoardService.boardDetail(boardNo, true);
			
			if (rBoardVo != null) {
				model.addAttribute("boardVo", rBoardVo);
				return "rboard/read";
			} else {
				return "redirect:/rBoard/list";
			}
		}

		//수정폼
		@RequestMapping(value="/modifyForm/{no}", method= { RequestMethod.GET, RequestMethod.POST})
		public String modifyForm(@PathVariable(value="no") int boardNo, Model model) {
			System.out.println("RBoardController.modifyForm()");
			
			RBoardVo rBoardVo = rBoardService.boardDetail(boardNo, false);
			
			model.addAttribute("boardVo", rBoardVo);

			return "rboard/modifyForm";
		}
		
		//수정
		@RequestMapping(value="/modify", method= { RequestMethod.GET, RequestMethod.POST})
		public String modify(@ModelAttribute RBoardVo rBoardVo) {
			System.out.println("RBoardController.modify()");
			
			rBoardService.boardUpdate(rBoardVo);
			
			return "redirect:/rBoard/list";
		}
		
		//삭제
		@RequestMapping(value="/delete/{no}", method= { RequestMethod.GET, RequestMethod.POST})
		public String delete(@PathVariable(value="no") int boardNo, Model model) {
			System.out.println("RBoardController.delete()");
			
			rBoardService.boardDelete(boardNo);

			return "redirect:/rBoard/list";
		}
		
	}

