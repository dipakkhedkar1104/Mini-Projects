package com.idiot.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/editurl")
public class EditServlet extends HttpServlet {

	
private static final String query="update bookdata set BOOKNAME=?, BOOKEDITION=?, BOOKPRICE=? where id=?";
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		//get printwriter
		PrintWriter pw=res.getWriter();
		
		//setcontent typees
		
		res.setContentType("text/html");
		
		//Get id of records
		int id=Integer.parseInt(req.getParameter("id"));
		
		//get edite data we want to edit
		String bookName=req.getParameter("bookName");
		String bookEdition=req.getParameter("bookEdition");
		float bookPrice=Float.parseFloat(req.getParameter("bookPrice"));
		
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
        	ps.setString(2, bookEdition);
        	ps.setFloat(3, bookPrice);
        	ps.setInt(4, id);
        	
        	int count=ps.executeUpdate();
        	if(count==1) {
        		pw.println("<h2>Record is edited succesfully!</h2>");
        	}
        	else {
        		pw.println("<h2>Record is not edited!</h2>");

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
