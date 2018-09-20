package cs_420_project1;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Scanner;

public class Project1 {
	private static int nodesGenerated = 0;
	private static int depthOfSolution = 0;
	private static int pathCost=1;
	

	public static void main(String[] args) {
		
		Scanner in = new Scanner(System.in);
		
		menu();
		
		int flag = 0; 
		while(flag == 0){
			
			
		int option = in.nextInt();
		switch(option){
		case 1: 
				ArrayList<Integer> list = generateRandom();
				
				//heuristic one, number of misplaced tiles
				System.out.println("--------------------------------------------------");
				System.out.println("- For heuristic one (number of misplaced tiles): -");
				System.out.println("--------------------------------------------------");
				A_Star_Search(list, 1);
				
				
				//heuristic two, cost in Manhattan calculations
				System.out.println("--------------------------------------------------");
				System.out.println("- For heuristic two (Manhattan Distances):       -");
				System.out.println("--------------------------------------------------");
				A_Star_Search(list, 2);

				
				System.out.println();
				
				int flag2 = 0;
				while(flag2 ==0){
				prompt();
				String response = in.next().toUpperCase();
				
				switch(response){
				case "Y": menu();
						flag2 = 1;
						break;
				case "N": System.out.println("GOODBYE!");
						flag = 1;
						flag2 = 1;
						break;
				default: System.out.println("Invalid Input. Please select again.");
						break;
				
					}
				}
				
			break;
			
		case 2: System.out.println("Please Enter an 8-puzzle (Please only put the numbers on a single line, NO spaces and NO commas) ");
				System.out.println("EXAMPLE: 012345678\n");
				Scanner userInput = new Scanner(System.in);
				String[] input = userInput.nextLine().split("");
				ArrayList<Integer> intSeq = new ArrayList<Integer>(input.length);
				for(int i = 0; i < input.length; i++) {
				    intSeq.add(i, Integer.parseInt(input[i]));
				}
				
				if(checkIfSolvable(intSeq) == 0){  //This is if the puzzle is NOT Solvable
					System.out.println("The Puzzle Entered is NOT SOLVABLE!");
					int flag3 = 0;
					while(flag3 ==0){
					prompt();
					String response = in.next().toUpperCase();
					
					switch(response){
					case "Y": menu();
							flag3 = 1;
							break;
					case "N": System.out.println("GOODBYE!");
							flag = 1;
							flag3 = 1;
							break;
					default: System.out.println("Invalid Input. Please select again.");
					break;
				}
			}
				
		}else if(checkIfSolvable(intSeq) == 1){ //This is if the puzzle is SOLVABLE
			//heuristic one, number of misplaced tiles
			System.out.println("--------------------------------------------------");
			System.out.println("- For heuristic one (number of misplaced tiles): -");
			System.out.println("--------------------------------------------------");
			A_Star_Search(intSeq, 1);
			
			
			//heuristic two, cost in Manhattan calculations
			System.out.println("--------------------------------------------------");
			System.out.println("- For heuristic two (Manhattan Distances):       -");
			System.out.println("--------------------------------------------------");
			A_Star_Search(intSeq, 2);

			
			System.out.println();
			
			int flag4 = 0;
			while(flag4 ==0){
			prompt();
			String response = in.next().toUpperCase();
			
			switch(response){
			case "Y": menu();
					flag4 = 1;
					break;
			case "N": System.out.println("HAVE A GOOD DAY!");
					flag = 1;
					flag4 = 1;
					break;
			default: System.out.println("Invalid Input. Please select again.");
					break;
			
				}
			}
		}
			break;
			
		default: System.out.println("Invalid Input. Please select again.");
				
		
			}
		}
		
		in.close();
		
	}
	
	public static void menu(){
		System.out.println();
		System.out.println("************************ 8-Puzzle Using A* Search ***************************");
		System.out.println("*                        ------------------------                           *");
		System.out.println("*               Choose an Option by Entering either 1 or 2:                 *");
		System.out.println("* (1) Solve the 8-Puzzle Problem with a RANDOMLY GENERATED puzzle           *");
		System.out.println("*                               OR                                          *");
		System.out.println("* (2) Solve the 8-puzzle problem with a Specific puzzle Inputed by the USER *");
		System.out.println("*                                                                           *");
		System.out.println("*****************************************************************************");
		
	}
	
	public static void prompt(){
		System.out.println("Would you like to play again: Y or N");
	}
	
	//This function generates random solvable puzzle for option 1
	public static ArrayList<Integer> generateRandom(){
		int count = 0;
		ArrayList<Integer> sequence = new ArrayList<Integer>(4);
		Random random = new Random();
		int flag = 0;
		while(count != 9 && flag == 0){
			int randSeq = random.nextInt(9);   //give me a number between 0 and 8
			if(!sequence.contains(randSeq)){   //make sure none of the numbers appear more than once
				sequence.add(randSeq);
				count++;
			if(count == 9 && checkIfSolvable(sequence) == 1){
				flag = 1;
			}
			}
		  }
	
		return sequence;
	}
	
	//This function checks to make sure a puzzle is solvable by counting the number of inversions
	public static int checkIfSolvable(ArrayList<Integer> array){
		int numberOfInversions = 0;
		for(int i = 0; i < array.size(); i++){
			int current = array.get(i);
			if((current != 0) && (current != 1)){
			for(int j = i+1; j < array.size(); j++){
				if((array.get(i) > array.get(j)) && (array.get(j) != 0)){
				numberOfInversions++;
				continue;
				}
			}
		  }
		}
		if(numberOfInversions%2 == 0){
			return 1;  //return one if Solvable
		}
		
		return 0;  //return zero if NOT Solvable
	}
	
	//This function is the actual A* Algorithm the takes in the puzzle and the heuristic type you want to use
	public static int A_Star_Search(ArrayList<Integer> puzzle, int heuristicType){
		double totalTime=0;
		
		pathCost = 1;
		nodesGenerated = 0;
		depthOfSolution = 0;
	
		double startTime = System.nanoTime();
		
		Node initial = new Node(null);
		initial.setPuzzle(puzzle);
		evaluationFunction(initial.getPuzzle(), heuristicType, initial);
		Comparator<Node> sort = new SortQueue();
		PriorityQueue<Node> states = new PriorityQueue<Node>(1000, sort);  
		List<ArrayList<Integer>> explored = new ArrayList<>();
		
		states.add(initial);
		
		while(!states.isEmpty()){
			
			Node currentNode = states.peek();     
			
			if(checkGoal(currentNode) == 0){
				double endTime = System.nanoTime();
				totalTime = endTime - startTime;
				getPath(puzzle,currentNode);
				System.out.println("Depth of Solution: " + depthOfSolution);
				System.out.println("Number of Nodes Generated: " + nodesGenerated);
				System.out.println("Time it took to solve: " + totalTime/1000000 + " milliseconds");
				return 0;  //return zero if reached GOAL
			}
			states.remove();  //remove top of queue, which is the node we will expand
			
		
			explored.add(currentNode.getPuzzle());    //put node that you are about to explore in the explore list
			
			transitionFunction(currentNode, heuristicType);   
			
			for(int i = 0; i < currentNode.successors().size(); i++){     //this puts all the valid successors in the queue
				if(!explored.contains(currentNode.successors().get(i).getPuzzle())){  //successors already in the explored list will not be added to the queue
				states.add(currentNode.successors().get(i));
				nodesGenerated++;
				}
			}

		}
		return 1;   //return one if it is a FAILURE
	}
	
	//This function checks if the puzzle is the goal puzzle state by comparing the index the tile number is at with the tile number
	public static int checkGoal(Node currentNode){    
		ArrayList<Integer> array = currentNode.getPuzzle();
		int status = 0;
		for(int i = 0; i < array.size(); i++){
			int value = array.get(i);
			if(value != i){ //current node is NOT the goal
				status = 1;
				i = array.size(); //to break out of loop when it is not the goal
			}else{
				continue;
			}
		}
		return status; //returns zero if the current node is the goal, one if current node is NOT the goal
	}
	
	@SuppressWarnings("unchecked")
	//This is the transition function which moves the blank space in all of its valid directions. The blank space is moved by swapping with the appropriate index. 
	public static void transitionFunction(Node node, int heuristicType){
		pathCost++;

		ArrayList<Integer> copy1 = (ArrayList<Integer>) node.getPuzzle().clone(); //These copies allow you to keep the state of the parent to make the appropriate move
		ArrayList<Integer> copy2 = (ArrayList<Integer>) node.getPuzzle().clone();
		ArrayList<Integer> copy3 = (ArrayList<Integer>) node.getPuzzle().clone();
		ArrayList<Integer> copy4 = (ArrayList<Integer>) node.getPuzzle().clone();
		
 		int positionOfBlank = 0;       //Find where the blank space is in order to determine which moves are possible
 		for(int i = 0; i < copy1.size(); i++){
 			int current = copy1.get(i);
 			if(current == 0){
 				positionOfBlank = i;
 				i = copy1.size();    //to break out of loop once blank space is found
 			}else{
 				continue;
 			}
 		}
 		
 		//This statement is if the blank space can move left, in which it is moved left
 		if((positionOfBlank == 1)||(positionOfBlank == 2)||(positionOfBlank == 4)||(positionOfBlank == 5)||(positionOfBlank == 7)||(positionOfBlank == 8)){  //move left
 			Node add2 = new Node(node);
 			ArrayList<Integer> puz2 = swap(positionOfBlank, positionOfBlank-1, copy2);  
 			add2.setPuzzle(puz2);
 			evaluationFunction(add2.getPuzzle(), heuristicType, add2);
 			node.successors().add(add2);
 		}

 		//This statement is if the blank space can move up, in which it is moved up
 		 if((positionOfBlank == 3)||(positionOfBlank == 4)||(positionOfBlank == 5)||(positionOfBlank == 6)||(positionOfBlank == 7)||(positionOfBlank == 8)){  //move up
 			Node add3 = new Node(node);
 			ArrayList<Integer> puz3 = swap(positionOfBlank, positionOfBlank-3, copy3);
 			add3.setPuzzle(puz3);
 			evaluationFunction(add3.getPuzzle(), heuristicType, add3);
 			node.successors().add(add3);
 			}

 		//This statement is if the blank space can move right, in which it is moved right
 		 if((positionOfBlank == 0)||(positionOfBlank == 1)||(positionOfBlank == 3)||(positionOfBlank == 4)||(positionOfBlank == 6)||(positionOfBlank == 7)){  //move right
 			Node add = new Node(node);
 			ArrayList<Integer> puz1 = swap(positionOfBlank, positionOfBlank+1, copy1);
 			add.setPuzzle(puz1);
 			evaluationFunction(add.getPuzzle(), heuristicType, add);
 			node.successors().add(add);
 		}
 		
 		//This statement is if the blank space can move down, in which it is moved down
 		 if((positionOfBlank == 0)||(positionOfBlank == 1)||(positionOfBlank == 2)||(positionOfBlank == 3)||(positionOfBlank == 4)||(positionOfBlank == 5)){  //move down
 			Node add4 = new Node(node);
 			ArrayList<Integer> puz4 = swap(positionOfBlank, positionOfBlank+3, copy4);
 			add4.setPuzzle(puz4);
 			evaluationFunction(add4.getPuzzle(), heuristicType, add4);
 			node.successors().add(add4);
 		 }
 	
 	}
 	
	//This function evaluates the total cost of moving in each direction for each type of heuristic
	public static void evaluationFunction(ArrayList<Integer> puzzle, int heuristicType, Node node){
		if(heuristicType == 1){  //heuristic 1, number of misplaced tiles 
			int f = heuristicOne(puzzle) + pathCost;
			node.setPriority(f);
		}
		if(heuristicType == 2){  //heuristic 2, Manhattan
			int f = heuristicTwo(puzzle) + pathCost;
			node.setPriority(f);
		}
		else if(heuristicType!= 1 && heuristicType!= 2){
			System.out.println("Invalid Input");
		}
	}
	
	//This evaluates the heuristic cost for number of misplaces tiles
	public static int heuristicOne(ArrayList<Integer> puzzle){
		int counter = 0;
		for(int i = 0; i < puzzle.size(); i++){   //It compares the index of the tiles with that titles number
			int current = puzzle.get(i);
			if(current == i){
				continue;
			}else if((current != i) && (current != 0)){
				counter++;
			}
		}
		return counter;
	}
	
	//This function helps with heuristic 2, by getting the x value for a certain tile. The puzzle board is imagined as a 3x3 grid to assign an appropriate x value. 
	public static int getx(int index){
		int x = 0;
		switch(index){
		case 0: x = 1; break;
		case 1: x = 2; break;
		case 2: x = 3; break;
		case 3: x = 1; break;
		case 4: x = 2; break;
		case 5: x = 3; break;
		case 6: x = 1; break;
		case 7: x = 2; break;
		case 8: x = 3; break;
		default: System.out.println("Index out of bounds"); break;
		}
		return x;
	}
	
	//This function helps with heuristic 2, by getting the y value for a certain tile. The puzzle board is imagined as a 3x3 grid to assign an appropriate y value. 
	public static int gety(int index){
		int y = 0;
		switch(index){
		case 0: y = 1; break;
		case 1: y = 1; break;
		case 2: y = 1; break;
		case 3: y = 2; break;
		case 4: y = 2; break;
		case 5: y = 2; break;
		case 6: y = 3; break;
		case 7: y = 3; break;
		case 8: y = 3; break;
		default: System.out.println("Index out of bounds"); break;
		}
		return y;
	}
	
	//This evaluates the heuristic cost using Manhattan Distances
	public static int heuristicTwo(ArrayList<Integer> puzzle){
		int counter = 0; 
		for(int i = 0; i < puzzle.size(); i++){
			int current = puzzle.get(i);     
			if(current == i){     //if tile is in correct position, do nothing
				continue;
			}else if ((current != i) && (current != 0)){   
				counter = counter + (Math.abs((getx(current) - getx(i))) + Math.abs((gety(current) - gety(i))));   //Takes the sum of the difference in x and y values
			}
		}
		return counter;
	}
 	
	//This function helps with the transition function to swap the values of two indices to represent a move 
 	public static ArrayList<Integer> swap(int i, int j, ArrayList<Integer> puzzle){
 		int temp = puzzle.get(i);
 		puzzle.set(i, puzzle.get(j));
 		puzzle.set(j, temp);
 		return puzzle;
 	}
 	
 	//This function gets the optimal path from starting node to goal node by getting the parent of each node, working its way to the starting node
 	public static void getPath(ArrayList<Integer> start, Node goal){
 		ArrayList<ArrayList<Integer>> path = new ArrayList<>();
		path.add(start);
		path.add(1,goal.getPuzzle());    
 		Node currentNode = goal;
 		int index = 1;    //each time the node is added to the second spot in the array since we are backtracking
 		while(currentNode.getParent().getPuzzle() != start){
 			path.add(index, currentNode.getParent().getPuzzle());
 			currentNode = currentNode.getParent();
 			}
 		  printArray(path);  
 	}
 	
 	//This function takes in an ArrayList filled with ArrayLists 
 	//The ArrayLists represent the puzzle states 
 	public static void printArray(ArrayList<ArrayList<Integer>> listOfArrays) {  
		depthOfSolution = listOfArrays.size() - 1;     //subtract 1 because we do not include the initial state as a step
 		while(!listOfArrays.isEmpty()){
 	      StringBuilder string = new StringBuilder();
 	      int count = 0;
 	      for (int i = 0; i < listOfArrays.get(0).size(); i++) {
 	         if (i > 0) {
 	        	 if(count%3 != 0){
 	            string.append("  ");
 	        	 }
 	         }
 	         string.append(listOfArrays.get(0).get(i));
 	         count++;
 	         if(count%3 == 0 && count != 0){
 	        	 string.append("\n");
 	        	 if(count%3 != 0){
 	        	 string.append("  ");
 	        	 }
 	         }
 	
 	      }
 	      System.out.println(string.toString());
 	      listOfArrays.remove(0);
 	   }
 	}
	
}
