<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" import="Seguridad.SeguridadC" %>
<%
    if (SeguridadC.verificaRequest(request.getQueryString())==false){ 
        response.sendRedirect("ErrorPage.jsp");
        return;
    }
      //background="Imagenes/bgEncabezado.jpg"
%>
<html>
    <head>
    <title>Encabezado</title>
        <script src='Utilerias/Util.js'></script>
    <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
     <link href="../StyleClasses/StyleC.css" rel="stylesheet" type="text/css">
    </head>

        <body background="Imagenes/banner_fondo.png">
            <div id="logo" style="position:absolute; z-index:25; left:0; top:0;">
                <img src="Imagenes/logo_ike_banner.png" alt="Iké Asistencia México"/>
            </div>
            <div id="icon_date" style="position: absolute; z-index:50; left:18px; top:49px;">
                <img src="Imagenes/calendar.png" alt="Fecha">
            </div>
            <div id="icon_clock" style="position: absolute; z-index:50; left:185px; top:49px;">
                <img src="Imagenes/clock.png" alt="Hora">
            </div>
            
            <input id="horas" type="hidden">
            <input id="minutos" type="hidden">
            <input id="segundos" type="hidden">
            <input id="day" type="hidden">
            <input id="month" type="hidden">
            <input id="year" type="hidden">
            
            <div id="date" class="clockStyle" style="position: absolute; z-index:50; left: 40px; top:48px;"></div>
            <div id="clock" class="clockStyle" style="position: absolute; z-index:50; left: 210px; top:48px;"></div>

            <script type="text/javascript">
                function updateClock(){

                    var meses = new Array ("Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre");

                    var Digital= new Date();
                    var day = Digital.getDate();
                    var month =meses[Digital.getMonth()];
                    var year = Digital.getFullYear();
                    var hour = Digital.getHours();
                    var min = Digital.getMinutes();
                    var seg = Digital.getSeconds();
                    var diem = "am";
                    document.all.horas.value = parseInt(hour);
                    document.all.segundos.value = parseInt(seg)+1;

                    setTimeout('updateClock()', 1000 );
                    if (seg ==59){
                    document.all.minutos.value = parseInt(min)+1;
                    document.all.segundos.value = parseInt(0);
                    if (min ==59){
                    document.all.horas.value = parseInt(hour)+1;
                    document.all.minutos.value = parseInt(0);
                    document.all.segundos.value = parseInt(0);
                    if (hour ==12){
                    document.all.horas.value = parseInt(hour)+1;
                    document.all.minutos.value = parseInt(0);
                    document.all.segundos.value = parseInt(0);
                    }
                    }
                    }
                    if(hour == 0){
                    hour = 12;
                    } else if (hour > 12){
                    hour = hour -12;
                    diem="pm";
                    }
                    if (hour < 10) {
                    hour = "0" + hour;
                    }
                    if (min < 10) {
                    min = "0" + min;
                    }
                    if (seg < 10) {
                    seg = "0" + seg;
                    }
                    var myClock = document.getElementById('clock');
                    myClock.textContent = hour + ":" + min + ":" + seg + "" + diem;
                    myClock.innerText = hour + ":" + min + ":" + seg + "" + diem;
                    var myDate= document.getElementById('date');
                    myDate.textContent =day + " - " + month + " - " +year;
                    myDate.innerText = day + " - " + month + " - " +year;
                    }
            updateClock();
    </script>

            <!--<center><b><font color="yellow"> !! D E S A R R O L L O !! </font></b></center>-->
            
        </body>
</html>
