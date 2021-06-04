package servlets;

import entity.ChatMessage;
import entity.ChatUser;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Calendar;

@WebServlet(name = "NewMessageServlet")
public class NewMessageServlet extends ChatServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String message = (String) request.getParameter("message");

        if (message != null && !"".equals(message)){
            ChatUser author = activeUsers.get((String) request.getSession().getAttribute("name"));
            //author.setLastInteractionTime(Calendar.getInstance().getTimeInMillis());
            //пофиксил баг и добавил свою логику
            synchronized (messages){
                messages.addFirst(new ChatMessage(message , author, Calendar.getInstance().getTimeInMillis()));
                author.increaseMessageAmount();
            }
        }

        response.sendRedirect("/chat/compose_message.jsp");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
