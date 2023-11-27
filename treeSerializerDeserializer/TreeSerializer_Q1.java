package treeSerializerDeserializer;

import java.util.ArrayList;
import java.util.Arrays;

/*
 * Assumptions:
a. Serialization should be to a String and from a String.
b. There are no cyclic connections in the tree
 */

public class TreeSerializer_Q1 implements TreeSerializer {

	private ArrayList<String> data;
	private int preorder_index;
	
	private void preorderSerialize(Node node) {
		
		/* recursively traverse the tree in preorder and record the node values in data arraylist */
		if (node != null) {
			this.data.add(String.valueOf(node.num)); // record current node's val as string
			preorderSerialize(node.left); // visit left child
			preorderSerialize(node.right); // visit right child
		}
		else {
			this.data.add("n"); // current node is null so record "n" in data to keep its place
		}
	}
	
	
	@Override
	public String serialize(Node root) {
		
		/* call preorder helper function to represent the tree as a string */
		
		this.data = new ArrayList<>(); // create a new list to store the node data order
		
		preorderSerialize(root); // call helper function to traverse tree and populate data arraylist
		
		String output = String.join(" ", data); // convert the data into a string for return
		return output; 
	}

	
	private Node preorderDeserialize() {
		
		/* recursively traverse the tree as it is built from the root down according to the preorder values */
		
		if (data.get(this.preorder_index).equals("n")) { // current node is null
			this.preorder_index += 1; // no node to be built but increment idx
			return null;
		}
		Node root = new Node(); // create new node
		root.num = Integer.parseInt(data.get(this.preorder_index)); // and set its val based on current idx in the serialized data 
		this.preorder_index += 1;
		root.left = preorderDeserialize(); // visit left child
		root.right = preorderDeserialize(); // visit right child
		return root;
		
	}
	
	@Override
	public Node deserialize(String str) {
		
		/* call perorder helper function to build up tree as represented from the previously serialised string */
		
		this.data =  new ArrayList<>(Arrays.asList(str.split(" "))); // convert serialized string into arraylist of node values 
		
		this.preorder_index = 0; // initialize the idx to the beginning
		
		return preorderDeserialize(); // call helper function and return root of the created tree
	}

}
