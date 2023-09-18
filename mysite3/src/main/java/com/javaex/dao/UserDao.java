package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.javaex.vo.UserVo;

public class UserDao {

	// 필드
	// 0. import java.sql.*;
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;

	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private String id = "webdb";
	private String pw = "webdb";

	// 생성자
	public UserDao() {
	}

	// 메소드 gs

	// 메소드 일반
	
	// (1) 오라클 연결
	private void getConnect() {

		try {
			// 1. JDBC 드라이버 (Oracle) 로딩
			Class.forName(driver);

			// 2. Connection 얻어오기
			conn = DriverManager.getConnection(url, id, pw);

		} catch (ClassNotFoundException e) {
			System.out.println("error: 드라이버 로딩 실패 - " + e);
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

	}

	// (2) 자원처리
	private void close() {
		// 5. 자원정리
		try {
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
	}

	// (3) 회원등록
	public int userInsert(UserVo userVo) {
		int count = -1;
		
		this.getConnect();

		try {
			// SQL문 준비 / 바인딩 / 실행
			// (1) SQL문 준비
			String query = "";
			query += " insert into users ";
			query += " values(seq_users_no.nextval, ?, ?, ?, ?) ";

			pstmt = conn.prepareStatement(query);

			// (2) 바인딩 값을 vo에 setter로 넣어준다
			pstmt.setString(1, userVo.getId());
			pstmt.setString(2, userVo.getPassword());
			pstmt.setString(3, userVo.getName());
			pstmt.setString(4, userVo.getGender());

			// (3) 실행
			count = pstmt.executeUpdate();

			// 결과처리
			System.out.println(count + "건 등록되었습니다.");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		this.close();
		return count;
	}

	// (4) 회원정보가져오기 (로그인용)
	public UserVo userSelect(UserVo userVo) {
		// 기본값을 null로 초기화하여 값이 틀릴때 null로 반환
		UserVo authUser = null;
		this.getConnect();
	
		try {
			// SQL문 준비 / 바인딩 / 실행
			// (1) SQL문 준비
			String query = "";
			query += " SELECT  no ";
			query += "         ,name ";
			query += " FROM users ";
			query += " where id = ? ";
			query += " and password = ? ";

			pstmt = conn.prepareStatement(query);

			// (2) 바인딩 값을 vo에 setter로 넣어준다
			pstmt.setString(1, userVo.getId());
			pstmt.setString(2, userVo.getPassword());

			// (3) 실행
			rs = pstmt.executeQuery();
			
			rs.next();
			int no = rs.getInt(1);	
			String name = rs.getString(2);
			
			// 새로 메모리올려서 받아온 값 넣기
			authUser = new UserVo();
			authUser.setNo(no);
			authUser.setName(name);
			// 결과처리
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		this.close();
		
		return authUser;
	
	}
	
	// (5) 회원정보가져오기 (수정폼용)
	public UserVo userUpdateSelect(UserVo userVo) {
		// 기본값을 null로 초기화하여 값이 틀릴때 null로 반환
		UserVo authUser = null;
		this.getConnect();
	
		try {
			// SQL문 준비 / 바인딩 / 실행
			// (1) SQL문 준비
			String query = "";
			query += " SELECT  no ";
			query += "         ,id ";
			query += "         ,password ";
			query += "         ,name ";
			query += "         ,gender ";
			query += " FROM users ";
			query += " where no = ? ";
			query += " and name = ? ";

			pstmt = conn.prepareStatement(query);

			// (2) 바인딩 값을 vo에 setter로 넣어준다
			pstmt.setInt(1, userVo.getNo());
			pstmt.setString(2, userVo.getName());

			// (3) 실행
			rs = pstmt.executeQuery();
			
			rs.next();
			int no = rs.getInt(1);			
			String id = rs.getString(2);
			String password = rs.getString(3);
			String name = rs.getString(4);
			String gender = rs.getString(5);
			
			// 새로 메모리올려서 받아온 값 넣기
			authUser = new UserVo(no, id, password, name, gender);
			
			// 결과처리
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		this.close();
		
		return authUser;
	
	}
			
	// (6) 회원정보수정하기
	public int userUpdate(UserVo userVo) {
				
		int count = -1;
		
		this.getConnect();
		
		try {
			// SQL문 준비 / 바인딩 / 실행
			// (1) SQL문 준비
			String query = "";
			query += " UPDATE users ";
			query += " set password = ? ";
			query += "     ,name = ? ";
			query += "     ,gender = ? ";
			query += " where id = ? ";

			pstmt = conn.prepareStatement(query);
			
			System.out.println(userVo.getId());

			// (2) 바인딩 값을 vo에 setter로 넣어준다
			pstmt.setString(1, userVo.getPassword());
			pstmt.setString(2, userVo.getName());
			pstmt.setString(3, userVo.getGender());
			pstmt.setString(4, userVo.getId());

			// (3) 실행
			count = pstmt.executeUpdate();			
			// 결과처리
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		this.close();
		return count;
	}
	
	
	
	
	
	
}
