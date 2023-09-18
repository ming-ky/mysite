package com.javaex.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.dao.GuestDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.GuestVo;

@WebServlet("/gbc")
public class GuestbookController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("gbc");

		String action = request.getParameter("action");

		if ("addList".equals(action)) {
			System.out.println("addList");
			
			GuestDao guestDao = new GuestDao();
			List<GuestVo> guestList = guestDao.guestSelect("");

			// request data를 넣는다
			request.setAttribute("gList", guestList);
			
			WebUtil.forword(request, response, "/WEB-INF/views/guestbook/addList.jsp");
		
		} else if ("listInsert".equals(action)) {
			System.out.println("listInsert");
			
			String name = request.getParameter("name");
			String password = request.getParameter("pw");
			String content = request.getParameter("content");
			
			GuestVo guestVo = new GuestVo();
			guestVo.setName(name);
			guestVo.setPassword(password);
			guestVo.setContent(content);
			
			GuestDao guestDao = new GuestDao();
			guestDao.guestInsert(guestVo);
			
			// 모두 처리됐으면 리스트로 돌아가기
			response.sendRedirect("./gbc?action=addList");
				
		} else if ("deleteFrom".equals(action)) {
			System.out.println("deleteFrom");
			
			request.getParameter("no");
			
			WebUtil.forword(request, response, "/WEB-INF/views/guestbook/deleteFrom.jsp");
						
		} else if ("delete".equals(action)) {
			System.out.println("delete");
			
			int no = Integer.parseInt(request.getParameter("no"));
			String password = request.getParameter("pw");
			
			GuestDao guestDao = new GuestDao();
			guestDao.guestDelete(no, password);
			
			// 모두 처리됐으면 리스트로 돌아가기
			response.sendRedirect("./gbc?action=addList");			
			
		} else {
			System.out.println("나머지");

		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
