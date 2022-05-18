package Controller;

import Methods.AccountHandler;
import Methods.AdminControl;
import Other.Security;
import java.io.IOException;
import java.sql.Connection;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import nl.captcha.Captcha;

public class LoginRegister extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Connection conn = (Connection) getServletContext().getAttribute("conn");

        if (conn != null) {

            String action = request.getParameter("action");
            String check = request.getParameter("PassAdmin");
            String username = "";
            String password = "";

            request.setCharacterEncoding("UTF-8");
            Captcha captcha = (Captcha) request.getSession().getAttribute(Captcha.NAME);
            String inCaptcha = request.getParameter("captcha");
            if (inCaptcha != null) {
                if (!captcha.isCorrect(inCaptcha)) {
                    request.setAttribute("error", "Incorrect Captcha");
                    if ("Login".equals(action)) {
                        request.getRequestDispatcher("Login.jsp").forward(request, response);
                    } else {
                        request.getRequestDispatcher("Register.jsp").forward(request, response);
                    }
                }
            }

            if (request.getSession().getAttribute("username") != null && "true".equals(check)) {
                username = (String) request.getSession().getAttribute("username");
                password = (String) request.getSession().getAttribute("password");
            } else {
                username = request.getParameter("username");
                password = request.getParameter("pass");
            }

            if (null == action) {
                request.getRequestDispatcher("Error.jsp").forward(request, response);
            } else {
                switch (action) {
                    case "Login":
                        //LOGIN CODE BLOCK
                        if ("false".equals(check)) {
                            switch (AccountHandler.AccountCheck(username, password, conn)) {
                                case "success":
                                    request.getSession().setAttribute("username", username);
                                    request.getSession().setAttribute("password", password);
                                    request.setAttribute("direct", "Login");
                                    request.getRequestDispatcher("AdminKey.jsp").forward(request, response);//successful login
                                    break;
                                case "password":
                                    request.setAttribute("error", "Incorrect password!");
                                    request.getRequestDispatcher("Login.jsp").forward(request, response);//wrong password
                                    break;
                                case "username":
                                    request.setAttribute("error", "Username does not exist!");
                                    request.getRequestDispatcher("Login.jsp").forward(request, response);//wrong username
                                    break;
                                default:
                                    request.getRequestDispatcher("Error.jsp").forward(request, response);
                                    break;
                            }
                        } else {
                            String key1 = request.getParameter("key1");
                            String key2 = request.getParameter("key2");
                            if (key1 != null && key2 != null && !AdminControl.AdminKeyCheck(key1, key2, conn)) {
                                request.getSession().setAttribute("username", username);
                                request.getSession().setAttribute("password", password);
                                request.setAttribute("error", "Incorrect admin keys!");
                                request.getRequestDispatcher("AdminKey.jsp").forward(request, response);//wrong admin key
                            } else {
                                request.getRequestDispatcher("test.jsp").forward(request, response);//successful register
                            }
                        }
                        break;
                    case "Register":
                        //REGISTER CODE BLOCK
                        String conpassword = "";
                        String role = "";
                        if (request.getSession().getAttribute("conpassword") != null && "true".equals(check)) {
                            conpassword = (String) request.getSession().getAttribute("conpassword");
                            role = (String) request.getSession().getAttribute("role");
                        } else {
                            conpassword = request.getParameter("cpass");
                            role = request.getParameter("role");
                        }
                        if (AccountHandler.UsernameCheck(username, conn) && password.equals(conpassword)) {//check if username unique & if passwords match
                            if ("Admin".equals(role) && "false".equals(check)) {
                                System.out.println();
                                request.getSession().setAttribute("username", username);
                                request.getSession().setAttribute("password", password);
                                request.getSession().setAttribute("conpassword", conpassword);
                                request.getSession().setAttribute("role", role);
                                request.setAttribute("direct", "Register");
                                request.getRequestDispatcher("AdminKey.jsp").forward(request, response);//admin
                            } else {
                                String key1 = request.getParameter("key1");
                                String key2 = request.getParameter("key2");
                                if (key1 != null && key2 != null && !AdminControl.AdminKeyCheck(key1, key2, conn) && "Admin".equals(role)) {
                                    request.getSession().setAttribute("username", username);
                                    request.getSession().setAttribute("password", password);
                                    request.getSession().setAttribute("conpassword", conpassword);
                                    request.getSession().setAttribute("role", role);
                                    request.setAttribute("direct", "Register");
                                    request.setAttribute("error", "Incorrect admin keys!");
                                    request.getRequestDispatcher("AdminKey.jsp").forward(request, response);//wrong admin key
                                } else {
                                    String encrypted = Security.encrypt(password);
                                    AccountHandler.Register(username, encrypted, role, conn);
                                    request.getRequestDispatcher("Login.jsp").forward(request, response);//successful register
                                }
                            }
                        } else {
                            if (!AccountHandler.UsernameCheck(username, conn)) {
                                request.setAttribute("error", "Username already exists!");
                            } else if (!password.equals(conpassword)) {
                                request.setAttribute("error", "Passwords do not match!");
                            }
                            request.getRequestDispatcher("Register.jsp").forward(request, response);//failed register
                        }
                        break;
                    default:
                        request.getRequestDispatcher("Error.jsp").forward(request, response);
                        break;
                }
            }
        } else {
            request.getRequestDispatcher("Error.jsp").forward(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
