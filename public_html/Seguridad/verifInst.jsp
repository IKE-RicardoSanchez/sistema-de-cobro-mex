<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" %>
<%  
    String strValue = "";
    
    if (request.getParameter("value")!=null){
        strValue = request.getParameter("value").toString();
    }
    
    strValue = strValue.replaceAll("(?i)"+" waitfor delay ","");  
    strValue = strValue.replaceAll("(?i)"+" waitfor ","");
    strValue = strValue.replaceAll("(?i)"+" delay ",""); 
    strValue = strValue.replaceAll("(?i)"+" select ","");  
    strValue = strValue.replaceAll("(?i)"+" insert ",""); 
    strValue = strValue.replaceAll("(?i)"+" into ",""); 
    strValue = strValue.replaceAll("(?i)"+" values ",""); 
    strValue = strValue.replaceAll("(?i)"+" delete ",""); 
    strValue = strValue.replaceAll("(?i)"+" update ",""); 
    strValue = strValue.replaceAll("(?i)"+" drop ",""); 
    strValue = strValue.replaceAll("(?i)"+" exec ",""); 
    strValue = strValue.replaceAll("(?i)"+" execute ",""); 
    strValue = strValue.replaceAll("(?i)"+" truncate ",""); 
    strValue = strValue.replaceAll("(?i)"+"'",""); 
    strValue = strValue.replaceAll("(?i)"+"\"",""); 
    strValue = strValue.replaceAll("(?i)"+"<",""); 
    strValue = strValue.replaceAll("(?i)"+">",""); 
 %><%=strValue%><%
    strValue = null;
%>    
        
