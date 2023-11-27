package treeSerializerDeserializer;


/*
 * Suggest or implement changes that should be done in order to support any data type (as opposed to only an int data type)
 * 
 * 
 
 public class Node2 {
	Node left;
	Node right;
	Object num;
 }

 public interface TreeSerializer2 {

	 String serialize(Node2 root);
	 Node2 deserialize(String str);
	
 }
 
 public class TreeSerializer_Q3 implements TreeSerializer2 {
 
 
 }

 * when serializing, an extra int can be appended to the string representation of the tree data 
 * to represent the data type of num in Node2 which is used during deserialization to recreate the data in num 
 * of Node2 when building up the tree 
 * 
 * 
 */

public class TreeSerializer_Q3 implements TreeSerializer {

	@Override
	public String serialize(Node root) {

		return null;
	}

	@Override
	public Node deserialize(String str) {

		return null;
	}



}