

import java.io.IOException;

import javax.inject.Inject;
import javax.mail.Session;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import chat.RoomList;
import model.User;

/**
 * Servlet implementation class RoomServlet
 */
@WebServlet("/room/*")
public class RoomServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Inject
       private RoomList roomList;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RoomServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		User loginUser = (User) session.getAttribute("loginUser");
		if(loginUser == null) {
			request.getRequestDispatcher("/auth").forward(request, response);
		} else {
			if(request.getPathInfo() == null) {
				request.getRequestDispatcher("/WEB-INF/roomList.jsp").forward(request, response);
			} else {
				String roomId = request.getPathInfo().substring(1);
				String roomName = roomList.getMap().get(roomId);
				request.setAttribute("roomId", roomId);
				request.setAttribute("roomName", roomName);
				request.getRequestDispatcher("/WEB-INF/room.jsp").forward(request, response);
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
