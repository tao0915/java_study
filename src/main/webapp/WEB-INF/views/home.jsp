<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="java.sql.*" %>
<%@ page import="javax.sql.*" %>
<%@ page import="javax.naming.*" %>
<%
Connection conn = null; 
PreparedStatement pstmt = null; 
ResultSet rs = null; 
try { 
    Context ictx = new InitialContext(); 
    Context ectx = (Context)ictx.lookup("java:comp/env"); 
    DataSource ds = (DataSource)ectx.lookup("jdbc/study"); 
    conn = ds.getConnection(); 
    pstmt = conn.prepareStatement("SELECT * FROM MEMBER_TB"); 
    rs = pstmt.executeQuery(); 
    if(rs.next()) { 
        out.println(rs.getString("admin_id")); 
    } 
} 
catch(Exception e) { 
    System.out.println(e.toString()); 
    out.println(e.toString()); 
} 
finally { 
    if(rs != null) 
        rs.close(); 
    if(pstmt != null) 
        pstmt.close(); 
    if(conn != null) 
        conn.close(); 
    out.println("\n\r finally"); 
} 
%>
