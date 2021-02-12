import java.util.Map;
import java.util.Set;

import javax.inject.Inject;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import chat.RoomList;
import model.User;

@ServerEndpoint(value = "/rchat/{roomId}", encoders = { MapEncoder.class }, configurator = ChatConfigurator.class)
public class RoomChatServer {
	@Inject
    private RoomList roomList;
	
	@OnOpen
	public void handleOpen(@PathParam("roomId") String roomId, Session s, EndpointConfig config) throws Exception {
		Map<String, Object> map = s.getUserProperties();
		map.put("stayIn", roomId);
		
		User loginUser = (User) config.getUserProperties().get("loginUser");
		map.put("loginUser", loginUser);
		String name = loginUser.getName();
		
		Set<Session> sList = s.getOpenSessions();
		for(Session session: sList) {
			if(session.getUserProperties().get("stayIn").equals(roomId)) {
				/*ChatContent cc;
				if(session == s) {
					cc = new ChatContent("SERVER", "WELCOME!");
				} else {
					cc = new ChatContent("SERVER", name + "さんが来ました");
				}
				session.getBasicRemote().sendText(cc.toJson());
				*/
				String m = (s == session ? "WELCOME!" : name + "さんが来ました");
				String[] list = {"from", "SERVER", "body", m};
				session.getBasicRemote().sendObject(list);
			}
		}
		
		String[][] history = roomList.getHistory(roomId);
		for(String[] h : history) {
			if (h[0].equals(name)) {
				String[] list = {"from", "me", "body", h[1]};
				s.getBasicRemote().sendObject(list);
			} else {
				String[] list = {"from", h[0], "body", h[1]};
				s.getBasicRemote().sendObject(list);
			}
		}
	}
	
	@OnMessage
	public void handleMessage(@PathParam("roomId") String roomId, String msg, Session s) throws Exception {
		User user = (User) s.getUserProperties().get("loginUser");
		String name = user.getName();
		
		String[] hList = {name, msg};
		roomList.addHistory(roomId, hList);
		
		
		Set<Session> sList = s.getOpenSessions();
		for(Session session: sList) {
			if(session.getUserProperties().get("stayIn").equals(roomId)) {
				String u = (s == session ? "me" : name);
				String[] list = {"from", u, "body", msg};
				session.getBasicRemote().sendObject(list);
				
				/*ChatContent cc;
				if(session == s) {
					cc = new ChatContent("me", msg);
				} else {
					cc = new ChatContent(name, msg);
				}
				session.getBasicRemote().sendText(cc.toJson());
				*/
			}
		}
	}
	
	@OnError
	public void handleError(Session s, Throwable e) {
		
	}
	
	@OnClose
	public void handleClose(@PathParam("roomId") String roomId, Session s) throws Exception {
		User user = (User) s.getUserProperties().get("loginUser");
		String name = user.getName();
		
		Set<Session> sList = s.getOpenSessions();
		for(Session session: sList) {
			if(session.getUserProperties().get("stayIn").equals(roomId)) {
				String[] list = {"from", "SERVER", "body", name + "さんが退出しました"};
				session.getBasicRemote().sendObject(list);
			}
		}
		
		/* ChatContent cc = new ChatContent("SERVER", name + "さんが退出しました");
		String json = cc.toJson();
		Set<Session> sList = s.getOpenSessions();
		for(Session session: sList) {
			if(session.getUserProperties().get("stayIn").equals(roomId)) {
				session.getBasicRemote().sendText(json);
			}
		}
		*/
	}
}
