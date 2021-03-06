package soap.ads.user;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;

public class UserLogin extends HttpServlet {
    private static final String CONTENT_TYPE = "text/html; charset=UTF-8";

    //Initialize global variables
    public void init() throws ServletException {
    }

    //Process the HTTP Get request
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head><title>UserLogin</title></head>");
        out.println("<body bgcolor=\"#ffffff\">");
        out.println("<p>The servlet has received a " + request.getMethod() +
                    ". This is the reply.</p>");
        out.println("</body>");
        out.println("</html>");
        out.close();
    }

    //Process the HTTP Post request
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        doGet(request, response);
    }

    //Clean up resources
    public void destroy() {
    }
}
