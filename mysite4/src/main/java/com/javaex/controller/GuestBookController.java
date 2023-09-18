package com.javaex.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.javaex.dao.GuestDao;
import com.javaex.vo.GuestVo;

@Controller
@RequestMapping("/gbc")
public class GuestBookController {
	
	@Autowired
	GuestDao guestDao;
	
	
	@RequestMapping(value = "/addList", method = { RequestMethod.GET, RequestMethod.POST })
	public String addList(Model model){
		System.out.println("방명록리스트");
		List<GuestVo> guestList = guestDao.guestSelect();
		model.addAttribute("gList", guestList);		
		return "guestbook/addList";
	}	
		
	@RequestMapping(value = "/listInsert", method = { RequestMethod.GET, RequestMethod.POST })
	public String listInsert(@ModelAttribute GuestVo guestVo){
		System.out.println("방명록등록");
		guestDao.listInsert(guestVo);		
		return "redirect:addList";
	}	
	
	@RequestMapping(value = "/deleteForm", method = { RequestMethod.GET, RequestMethod.POST })
	public String deleteForm(@RequestParam("no") int no ){
		System.out.println("방명록삭제폼");			
		return "guestbook/deleteForm";
	}		
	
	@RequestMapping(value = "/delete", method = { RequestMethod.GET, RequestMethod.POST })
	public String delete(@ModelAttribute GuestVo guestVo){
		System.out.println("방명록삭제");	
		guestDao.listDelete(guestVo);		
		return "redirect:addList";
	}	
	

}
