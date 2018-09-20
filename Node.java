package cs_420_project1;

import java.util.ArrayList;


public class Node {
	private int priority; 
	private ArrayList<Integer> puzzle;
	private Node parent;
	private ArrayList<Node> successors = new ArrayList<>();
	 
		public int getPriority(){
			return priority;
		}
		
		public Node(Node parent) { 
	 		this.parent = parent;
	 	} 
	 	
	 	public Node getParent(){
	 		return parent;
	 	}
		
		public void setPriority(int value){
			this.priority = value;
		}	
	 	
	 	public ArrayList<Integer> getPuzzle(){
			return puzzle;
		}
	 	
	 	public void setPuzzle(ArrayList<Integer> puzzle){
			this.puzzle = puzzle;
		}
	 	
	 	public ArrayList<Node> successors(){
	 		return successors;
	 	}
	 
}
