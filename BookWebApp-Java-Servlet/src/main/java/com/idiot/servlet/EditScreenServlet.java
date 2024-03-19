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

@WebServlet("/editScreen")
public class EditScreenServlet extends HttpServlet {
	
	
private static final String query="select BOOKNAME, BOOKEDITION, BOOKPRICE from bookdata where ID=?";
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		//get printwriter
		PrintWriter pw=res.getWriter();
		
		//setcontent typees
		
		res.setContentType("text/html");
		
		//Get id of records
		int id=Integer.parseInt(req.getParameter("id"));
		
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
        	
        	ps.setInt(1, id);
    	
        	ResultSet rs=ps.executeQuery();
        	rs.next();
        	
        	pw.println("<form action='editurl?id="+id+"' method='post'>");
        	pw.println("<table align='center'>");
        	
        	pw.println("<tr>");
        	pw.println("<td>Book Name </td>");
        	pw.println("<td><input type='text' name='bookName' value='"+rs.getString(1)+"'></td>");	
        	pw.println("</tr>");
        	
        	
        	pw.println("<tr>");
        	pw.println("<td>Book Edition </td>");
        	pw.println("<td><input type='text' name='bookEdition' value='"+rs.getString(2)+"'></td>");	
        	pw.println("</tr>");
        	
        	pw.println("<tr>");       	
        	pw.println("<td>Book Price </td>");
        	pw.println("<td><input type='text' name='bookPrice' value='"+rs.getString(3)+"'></td>");
        	pw.println("</tr>");
        	
        	pw.println("<tr>");
        	pw.println("<td><input type='submit' value='Done'></td>");
        	pw.println("<td><input type='reset' value='cancel'></td>");
        	pw.println("</tr>");
        	pw.println("</table>");
        	
        	pw.println("</form>");
        	        
        } catch (SQLException se) {
            se.printStackTrace();
            pw.println("<h1>"+se.getMessage()+"/h1");
        }
        catch(Exception re) {
        	re.printStackTrace();
        }
        pw.println("<a href='Home.html'>Home</a>");
        
            
        }
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doGet(req,res);
	}

}
