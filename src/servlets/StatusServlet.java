package servlets;

import entity.ChatUser;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;

@WebServlet(name = "StatusServlet")
public class StatusServlet extends ChatServlet {
    private long sessionTimeout = 10 * 60;

    public void init() throws ServletException{
        super.init();

        String value = getServletConfig().getInitParameter("SESSION_TIMEOUT");

        if (value != null) {
            sessionTimeout = Integer.parseInt(value);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("utf8");

//        HttpSession session = request.getSession();
//        ChatUser user = activeUsers.get(session.getAttribute("name"));
//        long delta = -1;
//
//        if (user != null && user.getSessionId() == session.getId()){
//            delta = 1 * 15 * 1000 - (Calendar.getInstance().getTimeInMillis() - user.getLastInteractionTime());
//        }

        PrintWriter pw = response.getWriter();

        pw.println("<html><head><meta http-equiv='Content-Type' content='text/html; charset=utf-8'/><meta http-equiv='refresh' content='5'></head>");
        pw.println("<body>");
        pw.print("<p>ГИГА-ЧАД: <font color='red'>");
//        if (delta < 0){
//            response.sendRedirect(response.encodeRedirectURL("/chat/SessionTimeoutRedirect.jsp"));
//        }
//        else if (delta < 60 * 1000){
//            pw.print("Время сессии закончится меньше чем через минуту");
//        }
//        else if (delta < 3 * 60 * 1000){
//            pw.print("Время сессии закончится меньше чем через 3 минуты");
//        }
//        else if (delta < 5 * 60 * 1000){
//            pw.print("Время сессии закончится меньше чем через 5 минут");
//        }
        pw.println("</font></p></body></html>");
    }
}
