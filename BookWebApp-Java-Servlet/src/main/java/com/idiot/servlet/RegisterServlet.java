package com.idiot.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
	
	
	private static final String query="insert into BOOKDATA(BOOKNAME,BOOKEDITION,BOOKPRICE) values (?,?,?)";
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		//get printwriter
		PrintWriter pw=res.getWriter();
		
		//setcontent typees
		
		res.setContentType("text/html");
		
		//GET the book info
		String bookName=req.getParameter("bookName");
		String bookEdition=req.getParameter("bookEdition");
		float bookPrice= Float.parseFloat(req.getParameter("bookPrice")) ;
		
		
		
		//load jdbc
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
		}
		catch(ClassNotFoundException cnf) {
			cnf.printStackTrace();
		}
		
		//generate connection
		
        String jdbcUrl = "jdbc:mysql://localhost:3306/book";
        String username = "root";
        String password = "dipak";

        try (Connection conn = DriverManager.getConnection(jdbcUrl, username, password)) {
            // Insert a record
        	
        	PreparedStatement ps=conn.prepareStatement(query);
        	ps.setString(1, bookName);
        	ps.setString(2,bookEdition);
        	ps.setFloat(3, bookPrice);
        	
        	int count=ps.executeUpdate();
        	if(count==1) {
        		pw.println("<h2>Record insert succesfully</h2>");
        	}
        	else {
        		pw.println("<h2>Record is NOT inserted!!!</h2>");
        	}
            
        } catch (SQLException se) {
            se.printStackTrace();
            pw.println("<h1>"+se.getMessage()+"/h1");
        }
        catch(Exception re) {
        	re.printStackTrace();
        }
        
        pw.println("<a href='Home.html'>Home</a>");
        pw.println("<br>");
        pw.println("<a href='bookList'>Book List</a>");
            
        }
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doGet(req,res);
	}
	

}
