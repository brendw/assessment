package treeSerializerDeserializer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/*
 * Assumptions:
a. Serialization should be to a String and from a String.
b. Take into account a “cyclic tree”. Your implementation should throw a RuntimeException when a cyclic connection is found in the Tree.
 */

public class TreeSerializer_Q2 implements TreeSerializer {

	private ArrayList<String> data;
	private int preorder_index;
	Set<Node> seen;
	
	private void preorderSerialize(Node node) {
		
		/* recursively traverse the tree in preorder and record the node values in data arraylist */
		
		if (node != null) {
			
			if (seen.contains(node)) { // if node has already been seen, there is a cycle
				throw new RuntimeException("Cyclic tree encountered");
			}
			else {
				seen.add(node);
			}
			
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
		
		this.data = new ArrayList<String>(); // create a new list to store the node data order
		this.seen = new HashSet<Node>(); // create new hash set to keep track of seen nodes in case of cycle
		
		preorderSerialize(root); // call helper function to traverse tree and populate data arraylist
		
		return String.join(" ", data); // convert the data into a string for return
	}

	
	private Node preorderDeserialize() {
		
		/* recursively traverse the tree as it is built from the root down according to the preorder values */
		
		if (data.get(this.preorder_index) == "n") { // current node is null
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
