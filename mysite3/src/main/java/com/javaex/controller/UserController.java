package com.javaex.controller;

import java.awt.Desktop.Action;
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.javaex.dao.UserDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.UserVo;

@WebServlet("/user")
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("userC");

		// 업무구분용 파라미터 체크
		String action = request.getParameter("action");

		if ("joinFrom".equals(action)) {
			System.out.println("joinFrom");
			// 회원가입폼(action = joinForm) 포워드
//		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/user/joinFrom.jsp");
//		rd.forward(request, response);
			// 스태틱을 만들어서 포워드 메소드바로 사용
			WebUtil.forword(request, response, "/WEB-INF/views/user/joinFrom.jsp");

		} else if ("join".equals(action)) {
			System.out.println("join");
			// 회원가입(action = join)

			// 파라미터 꺼내기
			String id = request.getParameter("id");
			String password = request.getParameter("password");
			String name = request.getParameter("name");
			String gender = request.getParameter("gender");

			// 1개로 묶기
			UserVo userVo = new UserVo(id, password, name, gender);
			System.out.println(userVo);
			// 등록실행
			UserDao userDao = new UserDao();
			int count = userDao.userInsert(userVo);
			System.out.println(count);

			// 가입성공 페이지
			WebUtil.forword(request, response, "/WEB-INF/views/user/joinOk.jsp");

		} else if ("loginFrom".equals(action)) {

			WebUtil.forword(request, response, "/WEB-INF/views/user/loginFrom.jsp");

		} else if ("login".equals(action)) {
			// 로그인 실행 구현 테스트
			System.out.println("login");
			
			// 값 꺼내기
			String id = request.getParameter("id");
			String password = request.getParameter("pw");
			
			// 값 묶어넣기
			UserVo userVo = new UserVo();
			userVo.setId(id);
			userVo.setPassword(password);
			
			// 아이디,비번 값을 보내서 쿼리문 실행하고 no,이름 값 받아오기
			UserDao userDao = new UserDao();
			UserVo authUser = userDao.userSelect(userVo);
			
			System.out.println(authUser);
			
			if(authUser != null) {
				// 결국 값을 빼올 수 있는건 리퀘스트
				// 그걸 이용해서 어튜리브트가 아닌 세션에 값을 전달해 줘야한다
				HttpSession session = request.getSession();
				session.setAttribute("authUser", authUser);
				
				WebUtil.redirect(request, response, "/mysite3/main");
			} else {
				WebUtil.redirect(request, response, "/mysite3/user?action=loginFrom&result=fail");
			}
		
		} else if ("logout".equals(action)) {
			System.out.println("로그아웃");
			
			HttpSession session = request.getSession();
			session.invalidate();
			
			// 세션삭제(로그아웃처리)후 메인으로 리다이렉트
			WebUtil.redirect(request, response, "/mysite3/main");
			
		} else if ("modifyForm".equals(action)) {
			System.out.println("회원정보수정폼");
			
			HttpSession session = request.getSession();
			UserVo authUser = (UserVo)session.getAttribute("authUser");
			
			System.out.println(authUser);
			
			UserDao userDao = new UserDao();
			UserVo modifyUser = userDao.userUpdateSelect(authUser);
			request.setAttribute("modifyUser", modifyUser);
			
			System.out.println(modifyUser);
			
			
			WebUtil.forword(request, response, "/WEB-INF/views/user/modifyForm.jsp");
			
		} else if ("modify".equals(action)) {
			System.out.println("modify");
			// 파라미터 값빼오기			
			String id = request.getParameter("id");
			String password = request.getParameter("pw");
			String name = request.getParameter("name");
			String gender = request.getParameter("gender");
			// vo로 묶기
			UserVo userVo = new UserVo(id, password, name, gender);
			// 묶은 vo를 dao로 업데이트 실행
			UserDao userDao = new UserDao();
			userDao.userUpdate(userVo);
			UserVo updateUser = userDao.userSelect(userVo);
			// 세션 업데이트
			HttpSession session = request.getSession();
			session.setAttribute("authUser", updateUser);
			
			//회원정보수정후 메인으로 리다이렉트
			WebUtil.redirect(request, response, "/mysite3/main");
		
		} else {
			System.out.println("나머지");
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
