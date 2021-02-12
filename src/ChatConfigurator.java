import javax.servlet.http.HttpSession;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;

public class ChatConfigurator extends ServerEndpointConfig.Configurator {
	@Override
	public void modifyHandshake(ServerEndpointConfig config, HandshakeRequest hreq, HandshakeResponse hres) {
		HttpSession hs = (HttpSession) hreq.getHttpSession();
		config.getUserProperties().put("loginUser", hs.getAttribute("loginUser"));
	}
}
