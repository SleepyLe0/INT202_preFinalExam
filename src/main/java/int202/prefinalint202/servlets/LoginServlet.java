package int202.prefinalint202.servlets;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import int202.prefinalint202.entities.Customer;
import int202.prefinalint202.repositories.CustomerRepository;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "LoginServlet", value = "/005/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action") == null ? "" : request.getParameter("action");
        if (action.equalsIgnoreCase("logout")) {
            HttpSession session = request.getSession(false);
            session.setAttribute("currentUser", null);
            response.sendRedirect("../");
        } else {
            request.getRequestDispatcher("../login.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");
        HttpSession session = request.getSession();
        CustomerRepository customerRepository = new CustomerRepository();
        Customer currentUser = customerRepository.findByUserName(userName);
        if (currentUser == null) {
            request.setAttribute("error", "wrong username");
            session.setAttribute("error", null);
            doGet(request, response);
        } else {
            Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2d, 16, 16);
            char[] passwordArray = password.toCharArray();
            boolean isCorrect = argon2.verify(currentUser.getPassword(), passwordArray);
            if (isCorrect) {
                session.setAttribute("currentUser", currentUser);
                session.setAttribute("error", null);
                response.sendRedirect("../");
            } else {
                request.setAttribute("error", "wrong password");
                session.setAttribute("error", null);
                doGet(request, response);
            }
        }
    }
}
