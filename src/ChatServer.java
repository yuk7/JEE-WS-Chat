import java.util.Set;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/chat")
public class ChatServer {
	@OnOpen
	public void handleOpen(Session s) throws Exception {
		Set<Session> sList = s.getOpenSessions();
		for(Session session: sList) {
			
			ChatContent cc;
			
			if(session == s) {
				cc = new ChatContent("SERVER", "WELCOME!");
			} else {
				cc = new ChatContent("SERVER", "新規参加者あり");
			}
			session.getBasicRemote().sendText(cc.toJson());
		}
	}
	
	@OnMessage
	public void handleMessage(String msg, Session s) throws Exception {
		Set<Session> sList = s.getOpenSessions();
		for(Session session: sList) {
			ChatContent cc;
			if(session == s) {
				cc = new ChatContent("me", msg);
			} else {
				cc = new ChatContent("others", msg);
			}
			session.getBasicRemote().sendText(cc.toJson());
		}
	}
	
	@OnError
	public void handleError(Session s, Throwable e) {
		
	}
	
	@OnClose
	public void handleClose(Session s) throws Exception {
		ChatContent cc = new ChatContent("SERVER", "BYE!");
		String json = cc.toJson();
		Set<Session> sList = s.getOpenSessions();
		for(Session session: sList) {
			session.getBasicRemote().sendText(json);
		}
	}
}
