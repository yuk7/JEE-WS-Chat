

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.User;
import model.Users;

/**
 * Servlet implementation class AuthServlet
 */
@WebServlet({"/auth", "/logout"})
public class AuthServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Inject
	private Users users;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AuthServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.getRequestDispatcher("/WEB-INF/auth.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		String userID = request.getParameter("userID");
		String password = request.getParameter("password");
		
		User loginUser = users.auth(userID, password);
		if(loginUser  != null) {
			HttpSession session = request.getSession();
			session.setAttribute("loginUser", loginUser);
			session.setAttribute("message", "ようこそ " + loginUser.getName() + "さん");
			response.sendRedirect("room");
		} else {
			request.setAttribute("message", "ログイン出来ませんでした");
			request.getRequestDispatcher("/WEB-INF/auth.jsp").forward(request, response);
		}
	}

}
