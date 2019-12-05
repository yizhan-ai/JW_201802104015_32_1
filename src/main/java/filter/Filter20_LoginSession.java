package filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "Filter 20", urlPatterns = {"/*"})
public class Filter20_LoginSession implements Filter {
    public void destroy(){

    }
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        System.out.println("Filter 20-LoginSessionFilter begin");
        HttpServletRequest request = (HttpServletRequest)req;
        HttpServletResponse response = (HttpServletResponse)resp;
        HttpSession session = request.getSession(false);
        String path = request.getRequestURI();
        if (path.contains("/login.ctl")|| path.contains("logout.ctl")){
            chain.doFilter(request,resp);
            System.out.println("Filter 20-LoginSessionFilter ehds");
        }else if (session != null&& session.getAttribute("currentUser") != null){
            chain.doFilter(request,resp);
            System.out.println("Filter 20-LoginSessionFilter ehds");
        }else {
            response.getWriter().println("您未登录，请先登录");
        }

    }

}
