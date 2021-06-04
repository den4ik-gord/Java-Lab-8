package servlets;

import entity.ChatUser;
import servlets.ChatServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;

@WebServlet(name = "LoginServlet")
public class LoginServlet extends ChatServlet {
    private static final long serialVersionUID = 1L;

    private int sessionTimeout = 10 * 60;

    public void init() throws ServletException {
        super.init();

        String value = getServletConfig().getInitParameter("SESSION_TIMEOUT");

        if (value != null) {
            sessionTimeout = Integer.parseInt(value);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String name = (String)request.getParameter("name");
        String errorMessage = null;

        if (name == null || "".equals(name)){
            errorMessage = "Имя пользователя не может быть пустым!";
        }
        else {
            errorMessage = processLogonAttempt(name, request, response);
        }

        if(errorMessage != null){
            request.getSession().setAttribute("name", null);
            request.getSession().setAttribute("error", errorMessage);
            response.sendRedirect(response.encodeRedirectURL("/chat/"));
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        String name = (String) session.getAttribute("name");
        String errorMessage = (String) session.getAttribute("error");

        String previousSessionId = null;

        if (name == null) {
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals("sessionId")) {
                    previousSessionId = cookie.getValue();
                    break;
                }
            }

            if (previousSessionId != null) {
                for (ChatUser user : activeUsers.values()) {
                    if (user.getSessionId().equals(previousSessionId)) {
                        name = user.getName();
                        user.setSessionId(request.getSession().getId());
                    }
                }
            }
        }

        if (name != null && !"".equals(name)) {
            errorMessage = processLogonAttempt(name, request, response);
        }

        response.setCharacterEncoding("utf8");
        PrintWriter pw = response.getWriter();

        pw.println("<html><head><title>Мега-чат!</title><meta http-equiv='Content-Type' content='text/html; charset=utf-8'/></head>");

        if (errorMessage != null) {
            pw.println("<p><font color='red'>" + errorMessage + "</font></p>");
        }

        pw.println("<form action='/chat/login.do' method='post'>Введите имя: <input type='text' name='name' value=''><input type='submit' value='Войти в чат'>");
        pw.println("</form></body></html>");

        request.getSession().setAttribute("error", null);
    }

    String processLogonAttempt(String name,  HttpServletRequest request, HttpServletResponse response) throws  IOException{
        String sessionId = request.getSession().getId();
        ChatUser user = activeUsers.get(name);

        if (user == null){
            user = new ChatUser(name, Calendar.getInstance().getTimeInMillis(), sessionId, 0);
            synchronized (activeUsers){
                activeUsers.put(user.getName(), user);
            }
        }

        if (user.getSessionId().equals(sessionId) || user.getLastInteractionTime() < (Calendar.getInstance().getTimeInMillis()- sessionTimeout*1000)){
            request.getSession().setAttribute("name", name);

            user.setLastInteractionTime(Calendar.getInstance().getTimeInMillis());

            Cookie sessionIdCookie = new Cookie("sessionId", sessionId);
            sessionIdCookie.setMaxAge(60 * 60);
            response.addCookie(sessionIdCookie);

            response.sendRedirect(response.encodeRedirectURL("/chat/view.jsp"));
            return null;
        }

        return "Извините, но имя <strong>" + name + "</strong> уже кем-то занято. Пожалуйста выберите другое имя!";
    }
}
