import java.util.HashMap;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

public class MapEncoder implements Encoder.Text<String[]> {
	private Jsonb jsonb = null;
	private HashMap<String, String> map = null;
	
	@Override
	public void init(EndpointConfig arg0) {
		jsonb = JsonbBuilder.create();
		map = new HashMap<>();
	}
	
	@Override
	public String encode(String[] list) throws EncodeException {
		map.clear();
		for(int i = 0; i < list.length; i+= 2) {
			map.put(list[i], list[i+1]);
		}
		return jsonb.toJson(map);
	}

	@Override
	public void destroy() {		
	}
	
	
}
