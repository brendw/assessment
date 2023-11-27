package jsonParser;

import java.util.Map;

public class Driver {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		
		
		String input = "{\"debug\":\"on\",\"window\":{\"title\":\"sample\",\"size\":500}}";

		Map<String, Object> output = JSONParser.parse(input);
		
		assert output.get("debug").equals("on");
	    assert ((Map<String, Object>) output.get("window")).get("title").equals("sample");
	    assert ((Map<String, Object>) output.get("window")).get("size").equals(500);
		
	    
	}

}
