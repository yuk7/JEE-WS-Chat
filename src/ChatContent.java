import javax.json.bind.JsonbBuilder;

public class ChatContent {
	public String from;
	public String body;
	
	public ChatContent(String from, String body) {
		this.from = from;
		this.body = body;
	}
	
	public String toJson() {
		return JsonbBuilder.create().toJson(this);
	}
}
