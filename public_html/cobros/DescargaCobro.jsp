<%@page import="org.omg.PortableInterceptor.SYSTEM_EXCEPTION"%>
<%@ page contentType="text/html; charset=iso-8859-1" language="java"
	 import="java.sql.ResultSet,Utilerias.UtileriasBDF,Seguridad.SeguridadC,java.io.PrintStream,java.io.FileInputStream,java.io.IOException,Utilerias.WriteExcel,
        	 java.io.InputStream,java.io.File,java.net.URL,java.io.BufferedInputStream,java.io.FileInputStream, java.net.URLConnection" errorPage="" %>
<html>
<head>
        <title>Descarga Respuesta Cobro</title>
    <link href="../StyleClasses/Global.css" rel="stylesheet" type="text/css">
    <link href="../StyleClasses/StyleC.css" rel="stylesheet" type="text/css">
</head>
<body class="cssBody">
    <jsp:useBean id="MyUtil" scope="session" class="Utilerias.UtileriasObj"/>
        <script src='../Utilerias/Util.js'></script>
    <%	System.out.println("Descarga Cobro...");

        String StrclUsrApp = "0";

            if (session.getAttribute("clUsrApp") != null) {
                StrclUsrApp = session.getAttribute("clUsrApp").toString();
            }else{
     %>             Debe iniciar sesión
                        <script>    setTimeout("top.location.href='../index.html';", 3000); </script>
    <%             return;
            }
    %>  <%--    if (SeguridadC.verifica__HorarioC(Integer.parseInt(StrclUsrApp)) != true) { %>Fuera de Horario
		<script>    setTimeout("top.location.href='../index.html';", 2000); </script>
		<%  return; }
        --%>
    <%  String StrclPaginaWeb = "280";
            session.setAttribute("clPaginaWebP", StrclPaginaWeb);

        MyUtil.InicializaParametrosC(Integer.parseInt(StrclPaginaWeb), Integer.parseInt(StrclUsrApp));

        String concat = "", tipo="", path="", extension="";
        boolean Proceso= false;

            if (session.getAttribute("proyecto") != null && session.getAttribute("lote") != null && session.getAttribute("archivo") != null){
                    System.out.println("Cuenta: "+session.getAttribute("nombre"));

                    concat= session.getAttribute("concat").toString();
                    tipo= session.getAttribute("pre").toString();

		StringBuffer SQL = new StringBuffer();
		StringBuffer Datos = new StringBuffer();
                ResultSet rs=null;

                    rs= UtileriasBDF.rsSQLNP("sp_tmkgcA_UploadBD");
                        if(rs.next())
                            path= rs.getString("Path").toString()+"/";

                        System.out.println(path);
                String nomFile = "", nomDesc="";

                    if(tipo==""){
                        tipo="0";
                            System.out.println("LayoutMx "+ session.getAttribute("proyecto") +"\nsp_tmkgcA_LayoutCobroMx '"+session.getAttribute("proyecto")+"',"+session.getAttribute("lote"));
                            SQL.append("sp_tmkgcA_LayoutCobroMx '").append(session.getAttribute("proyecto")).append("',").append(session.getAttribute("lote"));
                    }else{
                        tipo="1";
                        System.out.println("Pre validación");

                        SQL.append("sp_tmkgcA_PrevalidacionCobroMx '").append(session.getAttribute("proyecto")).append("',"+StrclUsrApp);
                    }

                    System.out.println("sp_tmkgcA_getInfoResp '"+session.getAttribute("proyecto")+"',"+tipo);
                        rs= UtileriasBDF.rsSQLNP("sp_tmkgcA_getInfoResp '"+session.getAttribute("proyecto")+"',"+tipo);
                            if(rs.next())
                                extension= rs.getString("TipoArchivo").toString();

                    System.out.println("Extensión: "+extension);
                        if(!extension.equals(".xls")){
                            rs = UtileriasBDF.rsSQLNP(SQL.toString());

				SQL.delete(0, SQL.length());
				SQL = null;

                                if (concat.equalsIgnoreCase("1")){
                                        while (rs.next()){
                                            Datos = Datos.append(rs.getString("Layout").toString());    }
                                }else{
                                         while (rs.next()){
                                            Datos = Datos.append(rs.getString("Layout").toString()).append("\n"); }
                                }

                                if (session.getAttribute("archivo").toString().equals("sinNombre.txt")){
                                    nomFile = session.getAttribute("proyecto") + "_Lote" + session.getAttribute("lote") + ".txt";
                                }else{    nomFile = session.getAttribute("archivo").toString(); }

                            nomDesc= nomFile;
                        PrintStream sp = new PrintStream(path + nomFile);
                            sp.print(Datos);
                            sp.flush();
                            sp.close();

                        Proceso= true;
                        }else if(extension.equals(".xls")){
                            /*System.out.println("Pre validación");
                                    if(tipo.contains("pre")){
                                      SQL.append("sp_tmkgcA_PrevalidacionCobroMx '").append(session.getAttribute("proyecto")).append("',"+StrclUsrApp); }
                                    else{   SQL.append("sp_tmkgcA_ProcesosAdicionales ").append(session.getAttribute("proyecto")).append(","+tipo).append(",1");
                                            rs= UtileriasBDF.rsSQLNP(SQL.toString());
                                                if(rs.next()){}
                                    }*/
                            nomFile=WriteExcel.excel(SQL, session.getAttribute("archivo").toString());
                            nomDesc= session.getAttribute("archivo")+".xls";

                            System.out.println("\tOut xls: "+nomFile+"\n\tNombre arch: "+nomDesc);
                                if(nomFile.contains("Error: ")){    Proceso= false; }
                                else    Proceso= true;
                        }

                if(Proceso){
                    System.out.println("\nDescarga activa");
                    response.setContentType("APPLICATION/OCTET-STREAM");
                    response.setHeader("Content-Disposition","attachment;filename=" + nomDesc);
                    out.clearBuffer();

                        File fileToD= null;
                        FileInputStream fileInputS=null;
                    try{
                        fileToD = new File(path + nomFile);
                        fileInputS = new FileInputStream(fileToD);
                        int i;

                            while((i=fileInputS.read())!=-1){   out.write(i);   }
                    }catch(Exception e){    e.printStackTrace();    }
                    finally{
                            out.flush();
                            out.clearBuffer();
                            //out.clear();
                            out.close();
                            fileInputS.close();

                            //System.out.println("Descargable de: "+fileToD.toString());
                            session.setAttribute("path", fileToD.toString());
                    }
                        rs.close();
                        rs = null;
                        //ouputStream=null;
                        //archivoSalida=null;
                        StrclUsrApp = null;
                        Datos.delete(0, Datos.length());
                        Datos = null;
                        SQL.delete(0, SQL.length());
                        SQL=null;
                        nomFile=null;
                }else{
     %>                 <script>    alert('No se ha procesado la respuesta anterior');  </script>
    <%                  Proceso= false;
                }
            }else{
    %>              <h1>ERROR JSP Download</h1>
                         <br> <%=session.getAttribute("proyecto")%>
                         <br> <%=session.getAttribute("lote")%>
                         <br> <%=session.getAttribute("archivo")%>
                         <br> <%=concat%>
    <% }
    %>
</body>
</html>