package security;

import com.alibaba.fastjson.JSONObject;
import domain.User;
import service.UserService;
import util.JSONUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/login.ctl")
public class LoginController extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException{
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        //创建JSON对象message，以便往前端响应信息
        JSONObject message = new JSONObject();
        try {
            User loggedUser = UserService.getInstance().login(username,password);
            if (loggedUser!=null){
                message.put("message","登陆成功");
                HttpSession session = req.getSession();
                //10分钟没有操作，则使session失效
                session.setMaxInactiveInterval(10*60);

                session.setAttribute("currentUser",loggedUser);
                resp.getWriter().println(message);
                //重新定向到索引页（菜单页）
                return;
            }else {
                message.put("message","用户名或密码错误");
                resp.getWriter().println(message);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            message.put("message","数据库操作异常");
        }catch(Exception e){
            message.put("message", "网络异常");
        }
    }
}
