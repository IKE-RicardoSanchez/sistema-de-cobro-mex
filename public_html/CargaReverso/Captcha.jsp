<%@page contentType="text/html;" pageEncoding="UTF-8" autoFlush="true" language="java" errorPage="" %>
<%@ page import="java.io.*"%>
<%@ page import="java.awt.*"%>
<%@ page import="java.awt.image.*"%>
<%@ page import="javax.imageio.ImageIO"%>
<%@ page import="java.util.*"%>

<html>
    <head>
        <link href="../StyleClasses/Global.css" rel="stylesheet" type="text/css">
        <title>Captcha</title>
    </head>
 <body>
    <%
    System.out.println("Inicia JSP");
        try {
            int width = 80, height = 30;
            Random rdm = new Random();
            int rl = rdm.nextInt();

            String hash1 = Integer.toHexString(rl);

            String capstr = hash1.substring(0, 5);

            session.setAttribute("CodigoLlave", capstr);
            Color background = new Color(255, 255, 255);
            Color fbl = new Color(0, 0, 102);
            Font fnt = new Font("SansSerif", 2, 18);
            BufferedImage cpimg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

            Graphics g = cpimg.createGraphics();
            g.setColor(background);
            g.fillRect(0, 0, width, height);
            g.setColor(fbl);
            g.setFont(fnt);

            g.drawString(capstr, 10, 20);
            g.setColor(background);
            g.drawLine(1, 9, 80, 9);
            g.drawLine(1, 15, 80, 15);


            File Path = new File("C:/Users/rarodriguez/Documents/NetBeansProjects/COBROS/public_html/CargaBD/Files/Captcha.jpg");
            //Path="/opt/app/apache-tomcat-5.5.15/webapps/SISE_CAPA/CargaBD/Captcha.jpg";

            /*File f=new File(Path);
            ImageIO.write(cpimg,"jpeg",f); */

            //response.setContentType("image/jpeg");
            //ServletOutputStream strm = response.getOutputStream();
            //ImageIO.write(cpimg, "jpeg", strm);
            try{
                    ImageIO.write(cpimg, "jpeg", Path);
                    System.out.println("Imagen fin");
            }catch(IOException i){ System.out.println("Error escritura imagen "+i); }

            cpimg.flush();
            //strm.flush();
            //strm.close();
            //strm = null;
        } catch (Exception ex) {
            System.out.println(ex.getMessage()+" error de jsp");
        }
    %>

    <table border="1">
        <tr>
            <td><img src="Files/Captcha.jpg"></td>
        </tr>
    </table>
   
    </body>
</html>
