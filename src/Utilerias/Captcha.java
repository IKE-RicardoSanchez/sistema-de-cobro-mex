/*
 * Captcha.java
 *
 * Created on 10 de diciembre de 2010, 09:14 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */


package Utilerias;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Random;
import javax.imageio.ImageIO;

import javax.servlet.*;
import javax.servlet.http.*;


/**
 *
 * @author bsanchez
 */
public class Captcha extends HttpServlet {
    
    /** Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        
         HttpSession sessionH = request.getSession(false);

         try{
            int width=75, height=28;
            Random rdm=new Random();
            int rl=rdm.nextInt();
            
            String hash1 = Integer.toHexString(rl);
            
            String capstr=hash1.substring(0,5);
            
            sessionH.setAttribute("CodigoLlave",capstr);

            Color background = new Color(255,255,255);
            Color fbl = new Color(0,0,102);
            Font fnt=new Font("SansSerif",2,18);
            BufferedImage cpimg =new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
            
            Graphics g = cpimg.createGraphics();
            g.setColor(background);
            g.fillRect(0,0,width,height);
            g.setColor(fbl);
            g.setFont(fnt);
           
            g.drawString(capstr,10,20);
            g.setColor(background);
            g.drawLine(1,9,80,9);
            g.drawLine(1,15,80,15);
            
            response.setContentType("image/jpeg");
            ServletOutputStream strm = response.getOutputStream();
            ImageIO.write(cpimg,"jpeg",strm);
            cpimg.flush();
            strm.flush();
            strm.close();
            strm = null;
        }catch (Exception ex){
            System.out.println (ex.getMessage());
        }
        
    }
    
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }
    
    /** Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }
    
    /** Returns a short description of the servlet.
     */
    public String getServletInfo() {
        return "Short description";
    }
    // </editor-fold>
}
