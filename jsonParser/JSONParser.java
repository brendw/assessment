package jsonParser;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JSONParser {

	/*
	 * JSON parser that accepts an input JSON string and produces a Map output structure.
	 */
	
	private static Deque<Map<String,Object>> stack = new ArrayDeque<>();
	private static String key = null;
	private static Object value = null;
	
	private static boolean isNumericRegex(String str) {
		
		/* returns whether the input string is numeric or not */
	    if (str == null) {
	        return false;
	    }
	    return str.matches("-?\\d+");

	} // end isNumericRegex
	
	private static int findEndOfWord(int start, String str) {
		
		/* given a json string and the starting index, find the matching regex pattern and return the index of the end of the found pattern group
		 * first find a pattern that starts with and ends with " representing a string, 
		 * if not found, find the pattern that starts with and is all digits */
		
		Pattern pattern = Pattern.compile("^(\".*?[^\\\\]\")\\s*[:,]?.*"); // find ("key"):remaining or ("key"),remaining or ("key")
	    Matcher matcher = pattern.matcher(str.substring(start)); 
	    
	    if (matcher.find()) {
	    	return start + matcher.end(1) - 1; // return idx of json where current str ends (includes end quote)
	    }
		
	    Pattern pattern2 = Pattern.compile("^([0-9]+).*"); // find (val),remaining or (val)
	    Matcher matcher2 = pattern2.matcher(str.substring(start));
		
	    matcher2.find();
	    return start + matcher2.end(1) - 1; // return idx of json where current idx ends inclusive

	} // end findEndOfWord
	
	public static Map<String,Object> parse(String json) {
		
		/* parse the json string character by character to find the indices for the substrings that represent strings or numbers
		 * which are found in the order of key then value 
		 * 
		 * each object is represented by a map
		 * all maps are kept track of in a stack so that the current object map can be accessed at the top
		 * when a { is encountered, there is a new object so a new map is created and pushed
		 * when a } is encountered, the current object/map is complete it can be popped to access its parent object/map now at the top */
		
		stack.addFirst(new HashMap<String,Object>()); // push initial map
		
		int i = 1; // begin parsing after first {
		while (i < json.length()-1) { // ignore last }
			
			char current = json.charAt(i);
			
			if (current=='{') { // value is another object
				
				Map<String,Object> new_map = new HashMap<>(); // create new map to represent next object
				stack.peekFirst().put(key, new_map); // add kv to current map
				stack.addFirst(new_map); // access the new map at top of stack
				key = null; // reset values
				value = null;
			}
			
			else if (current=='}') { // end of object 
				
				stack.removeFirst(); // pop access to most recent object map
			}
			
			else if (current == '"' || Character.isDigit(current)) {
				
				int j = findEndOfWord(i,json);
				String s = json.substring(i,j+1);
				s = (s.matches("\".*\"")) ? json.substring(i+1,j) : json.substring(i,j+1); // remove surrounding double quotes if needed
				
				if (key==null) {
					key = s;
				} 
				else {
					value = isNumericRegex(s) ? Integer.parseInt(s) : s; // convert str to int if numeric
					
					// key and value found
					stack.peekFirst().put(key, value); // add kv to current map
					key = null; // reset values
					value = null;
				}
				i = j; // update i
			}
			
			i++; //increment index
		}
		
		return stack.removeFirst();
		
	} // end parse
	
}

