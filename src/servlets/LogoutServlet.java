package servlets;

import entity.ChatUser;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "LogoutServlet")
public class LogoutServlet extends ChatServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = (String) request.getSession().getAttribute("name");

        if (name != null){
            ChatUser user = activeUsers.get(name);

            if (user.getSessionId().equals((String) request.getSession().getId())){
                synchronized (activeUsers){
                    activeUsers.remove(name);
                }

                request.getSession().setAttribute("name", null);
                response.addCookie(new Cookie("sessionId", null));

                response.sendRedirect(response.encodeRedirectURL("/chat/"));
            }
            else {
                response.sendRedirect(response.encodeRedirectURL("/chat/view.jsp"));
            }
        }
        else {
            response.sendRedirect(response.encodeRedirectURL("/chat/login.do"));
        }
    }
}
