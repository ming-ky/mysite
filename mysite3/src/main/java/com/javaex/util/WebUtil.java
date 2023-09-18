package com.javaex.util;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// 페이지 포워드,리다이렉트 클래스 만들기
public class WebUtil {
	
	// 필드 X
	
	// 생성자 디폴트라 생략 X
	
	// 메소드 gs 필드없어서 X
	
	// 메소드 일반 
	
	
	public static void forword(HttpServletRequest request,
					    HttpServletResponse response,
					    String path) throws ServletException, IOException {
		
		RequestDispatcher rd = request.getRequestDispatcher(path);
		rd.forward(request, response);
		
	}
	
	public static void redirect(HttpServletRequest request,
				         HttpServletResponse response,
				         String url) throws IOException {
		
		response.sendRedirect(url);
	}
	

}
