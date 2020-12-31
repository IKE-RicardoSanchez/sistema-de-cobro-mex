<%@ page contentType="text/html;charset=windows-1252" import="Utilerias.UtileriasBDF, Seguridad.SeguridadC, Combos.*"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>Recarga de Catálogos de Memoria</title>
    <script src='../Utilerias/Util.js'></script>
  </head>
  <body>
  <%
  	if (session.getAttribute("AccesoId")==null){
            %>Debe iniciar sesion<%
            return;
        }else{
            if (session.getAttribute("AccesoId").toString().compareToIgnoreCase("0")==0){
                %>Debe iniciar sesion<%
                return;
            }
        }
        
    if (SeguridadC.verificaRequest(request.getQueryString())==false){ 
        response.sendRedirect("ErrorPage.jsp");
        return;
    }    
              
        
 String StrclUsrApp="0";
 String StrClassName="";
  
 try{

    

    if (session.getAttribute("clUsrApp")!= null)
     {
       StrclUsrApp = session.getAttribute("clUsrApp").toString(); 
       	}else{
            %>Debe iniciar sesión<%
            return; 
	}

    if (SeguridadC.verificaHorarioC(Integer.parseInt(StrclUsrApp)) != true) 
     {%>
       Fuera de Horario
       <%return;  
     } 
     if(request.getParameter("class")!=null){
      StrClassName=request.getParameter("class").toString();
     }
     if (StrClassName.compareToIgnoreCase("cbEntidad")==0){
      cbEntidad.reLoad(); %><script>alert('Entidades Recargadas')</script><%
     }
  }catch(Exception e){
  }
  
  %>
  
  </body>
</html>
