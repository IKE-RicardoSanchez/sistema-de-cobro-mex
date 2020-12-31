<%@page contentType="text/html" import="java.io.StringWriter,java.io.PrintWriter"   isErrorPage="true"%>


<html>
    
    
    <%
        String StrXSS = "0";
        
        if (session.getAttribute("XSS")!=null){
            StrXSS = session.getAttribute("XSS").toString();
        }
        
        
        
    %>
    
    <head><title>Error P�gina Web</title>
         <style>
                .ErrorP{
                    	font-family: Verdana, Arial, Helvetica, sans-serif;
                        color: #062F67;
                        font-size: 18px;
                        font-weight: bold;
                }
                
                .divAlert {
                    position: absolute;
                    left: 50%;
                    top: 100px;
                    width: 550px;
                    height: 355px;
                    margin-top: -90px;
                    margin-left: -273px;
                    overflow: auto;
                    background-image: url(../Imagenes/Alert.png);
                    background-repeat: no-repeat;
               }
               
               .divMsgAlert {
                    position: absolute;
                    left: 50%;
                    top: 320px;
                    width: 200px;
                    height: 100px;
                    margin-top: -140px;
                    margin-left: -100px;
                    overflow: auto;
                    font-family: Verdana, Arial, Helvetica, sans-serif;
                    color: #CC0000;
                    font-weight: bold;
                    font-size: 12px;
                    text-transform: uppercase;
                    z-index:2;
                    text-align: center;
               }
            </style>
    </head>
    <body bgcolor="#ecf2f9">
        <div class="divAlert"></div>
        <% if (StrXSS.equalsIgnoreCase("1")){ %>
            <div class="divMsgAlert">Error de Formato.</div>
            
        <% } 
            else {
                //if (session.getAttribute("clUsrApp")!=null){ %>
                    <div class="divMsgAlert">Error en la p�gina Web.</div>
                <%//} else { %>
                    <!--div class="divMsgAlert">SU SESI�N EXPIR�.</div-->
                <% //}
          } %>
    </body>
    
    <%  
        
        //Exception Handler
        System.out.println("<<<<<<<<<<<<<<<<<<< Error de la Pagina >>>>>>>>>>>>>>>>>>>");
        try{
            System.out.println(exception.toString()); 
        }
        catch(Exception e){
            System.out.println(e);
        }
        finally{
            session.removeAttribute("XSS");
        }
        System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<< >>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        
        
    %>
    

</html>
