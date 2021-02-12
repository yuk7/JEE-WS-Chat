package chat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

@Named
@ApplicationScoped
public class RoomList {
	private HashMap<String, String> map;
	private HashMap<String, List<String[]>> history;
	private final int maxHistory = 50;
	
	@PostConstruct
	private void init() {
		map = new HashMap<>();
		map.put("1", "ルーム1");
		map.put("2", "ルーム2");
		map.put("3", "ルーム3");
		
		history = new HashMap<>();
		history.put("1", Collections.synchronizedList(new ArrayList<String[]>()));
		history.put("2", Collections.synchronizedList(new ArrayList<String[]>()));
		history.put("3", Collections.synchronizedList(new ArrayList<String[]>()));
	}

	public HashMap<String, String> getMap() {
		return map;
	}

	public void setMap(HashMap<String, String> map) {
		this.map = map;
	}

	public String[][] getHistory(String key) {
		 List<String[]> list = history.get(key);
		 return (String[][]) list.toArray();
	}

	public void addHistory(String key, String[] message) {
		List<String[]> list = history.get(key);
		if(list.size() >= maxHistory) {
			list.remove(0);
		}
		list.add(message);
	}
}
