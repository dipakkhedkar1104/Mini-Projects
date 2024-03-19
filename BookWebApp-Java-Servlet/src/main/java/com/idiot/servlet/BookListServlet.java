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

@WebServlet("/bookList") 
public class BookListServlet extends HttpServlet {
	
	
private static final String query="select ID, BOOKNAME, BOOKEDITION, BOOKPRICE from bookdata";
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		//get printwriter
		PrintWriter pw=res.getWriter();
		
		//setcontent typees
		
		res.setContentType("text/html");
		
		
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
        	
        	ResultSet rs=ps.executeQuery();
        	pw.println("<link rel=\"stylesheet\" href=\"css/bootstrap.css\">");
    		pw.println("<table border='1' align='center'>");
    		pw.println("<tr>");
    		pw.println("<th>Book ID</th>");
    		pw.println("<th>Book Name</th>");
    		pw.println("<th>Book Edition</th>");
    		pw.println("<th>Book Price</th>");
    		pw.println("<th>Edit</th>");
    		pw.println("<th>Delete</th>");
    		
    		pw.println("</tr>");
    		
        	while(rs.next()) {
        		
        		pw.println("<tr>");
        		pw.println("<td>"+rs.getInt(1)+"</td>");
        		pw.println("<td>"+rs.getString(2)+"</td>");
        		pw.println("<td>"+rs.getString(3)+"</td>");
        		pw.println("<td>"+rs.getFloat(4)+"</td>");
        		pw.println("<td> <a href='editScreen?id="+rs.getInt(1)+"' class='text-success'>Edit</a></td>");
        		pw.println("<td> <a href='deleteurl?id="+rs.getInt(1)+"' class='text-danger'>Delete</a></td>");
        		
        		
        		pw.println("</tr>");
        	
        	}
        	pw.println("/<table>");
        	
            
        } catch (SQLException se) {
            se.printStackTrace();
            pw.println("<h1>"+se.getMessage()+"/h1");
        }
        catch(Exception re) {
        	re.printStackTrace();
        }
        //pw.println("<a href='Home.html' class=''>Home</a>");
        pw.println("<button onclick=\"window.location.href='Home.html'\" class='btn btn-info'>Home</button>");

            
        }
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doGet(req,res);
	}

}
