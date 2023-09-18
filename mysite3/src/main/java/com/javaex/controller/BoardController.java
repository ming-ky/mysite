package com.javaex.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.javaex.dao.BoardDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.UserVo;
import com.javaex.vo.BoardVo;


@WebServlet("/brc")
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
      
	
  
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("brc");
		
		String action = request.getParameter("action");

		if ("list".equals(action)) {
			System.out.println("list");
			
			BoardDao boardDao = new BoardDao();
			List<BoardVo> boardList = boardDao.boardSelect("");

			// request data를 넣는다
			request.setAttribute("bList", boardList);
		
			WebUtil.forword(request, response, "/WEB-INF/views/board/list.jsp");

		} else if ("read".equals(action)) {
			System.out.println("읽기");
			
			BoardDao boardDao = new BoardDao();
			
			int no = Integer.parseInt(request.getParameter("no"));
			
			// 글제목을 눌러 불러올때만 조회수 상승
			String hit = request.getParameter("hit");
			if (hit != null) {
				boardDao.boardHit(no);
			}
						
			BoardVo boardRead = boardDao.boardRead(no);
			
			System.out.println(boardRead);

			// request data를 넣는다
			request.setAttribute("boardRead", boardRead);
			
			
			// 읽기창으로 포워드
			WebUtil.forword(request, response, "/WEB-INF/views/board/read.jsp");
			
			
		} else if ("writeForm".equals(action)) {
			System.out.println("등록폼");
			
			// 등록폼으로 포워드
			WebUtil.forword(request, response, "/WEB-INF/views/board/writeForm.jsp");
			
		} else if ("insert".equals(action)) {
			System.out.println("등록");
			
			HttpSession session = request.getSession();
			UserVo authUser = (UserVo)session.getAttribute("authUser");
			// 세션에서 로그인한 유저의 no값과 파라미터의 title,content 값을 boardVo로 묶기
			int no = authUser.getNo();
			String title = request.getParameter("title");
			String content = request.getParameter("content");
			BoardVo boardVo = new BoardVo();
			boardVo.setUserNo(no);
			boardVo.setTitle(title);
			boardVo.setContent(content);
			
			BoardDao boardDao = new BoardDao();
			boardDao.boardInsert(boardVo);
			
			// 등록후 리다이렉트
			WebUtil.redirect(request, response, "/mysite3/brc?action=list");
			
			
			
		} else if ("modifyForm".equals(action)) {
			System.out.println("수정폼");
			
			int no = Integer.parseInt(request.getParameter("no"));
			System.out.println(no);			
			
			BoardDao boardDao = new BoardDao();
			BoardVo boardRead = boardDao.boardRead(no);
			
			System.out.println(boardRead);

			// request data를 넣는다
			request.setAttribute("boardRead", boardRead);		
			
			// 수정폼으로 포워드
			WebUtil.forword(request, response, "/WEB-INF/views/board/modifyForm.jsp");
		
		} else if ("modify".equals(action)) {
			System.out.println("수정");
			
			int userNo = Integer.parseInt(request.getParameter("userNo"));
			int titleNo = Integer.parseInt(request.getParameter("no"));
			String title = request.getParameter("title");
			String content = request.getParameter("content");
			
			BoardDao boardDao = new BoardDao();
			boardDao.boardUpdate(userNo, titleNo, title, content);
			
			
			// 수정후 리다이렉트
			WebUtil.redirect(request, response, "/mysite3/brc?action=read"+"&no="+titleNo);
			
		} else if ("delete".equals(action)) {
			System.out.println("삭제");
			int userNo = Integer.parseInt(request.getParameter("userNo"));
			int boardNo = Integer.parseInt(request.getParameter("boardNo"));
			
			BoardDao boardDao = new BoardDao();
			boardDao.boardDelete(userNo, boardNo);
			
			// 삭제 실행후 리다이렉트
			WebUtil.redirect(request, response, "/mysite3/brc?action=list");
		
		} else if ("select".equals(action)) {
			System.out.println("검색");
			
			String search = request.getParameter("search");
			BoardDao boardDao = new BoardDao();
			List<BoardVo> searchList = boardDao.boardSearch(search);
			
			// request data를 넣는다
			request.setAttribute("bList", searchList);
			
			WebUtil.forword(request, response, "/WEB-INF/views/board/list.jsp");
			
		} else {
			System.out.println("나머지");
		}

		
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
