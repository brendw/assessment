package treeSerializerDeserializer;

public class Driver {

	public static void main(String[] args) {
		
		TreeSerializer_Q1 t_1 = new TreeSerializer_Q1();
		TreeSerializer_Q2 t_2 = new TreeSerializer_Q2();
		
		Node tree = new Node();
		tree.num = 1;
		tree.left = new Node();
		tree.right = new Node();
		tree.left.num = 2;
		tree.right.num = 3;
		tree.right.left = new Node();
		tree.right.right = new Node();
		tree.right.left.num = 4;
		tree.right.right.num = 5;
		
		Node tree_cycle = new Node();
		tree_cycle.num = 6;
		tree_cycle.left = new Node();
		tree_cycle.right = new Node();
		tree_cycle.left.num = 7;
		tree_cycle.right.num = 8;
		tree_cycle.right.right = tree_cycle;
		
		Node t_1_output = t_1.deserialize(t_1.serialize(tree)); // Q1
		
        try {
        	
        	Node t_2_output = t_2.deserialize(t_2.serialize(tree_cycle)); //  Q2 - throws exception
        	
        }
        catch (RuntimeException e) {
            System.out.println("Cycle found; Exiting");
        }

	}

}
