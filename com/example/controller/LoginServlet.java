package lk.ijse.gdse.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lk.ijse.gdse.model.LoginModel;

import java.io.IOException;

@WebServlet("/userLoginServlet")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        LoginModel loginDao = new LoginModel();
        String role = loginDao.checkLogin(username, password);

        if (role != null) {
            HttpSession session = req.getSession();
            session.setAttribute("username", username);

            if (role.equals("admin")) {
                resp.sendRedirect("adminShow.jsp");
            } else if (role.equals("user")) {
                resp.sendRedirect("addComplaint.jsp");
            } else {
                req.setAttribute("error", "Invalid user role");
                req.getRequestDispatcher("userLogin.jsp").forward(req, resp);
            }
        } else {
            req.setAttribute("error", "Invalid username or password");
            req.getRequestDispatcher("userLogin.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect("userLogin.jsp");
    }
}
