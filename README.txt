       ___________  
      |_READ__ME__|
     

Amanda Garcia

This is an 8-Puzzle Solver using A* Search Algorithm with two different heuristic functions:
    (1) Heuristic 1: Number of Misplaced Titles
    (2) Heuristic 2: Manhattan Distances
    
 
Compilation:
    javac Project1.java
    

Execution:
    java Project1    
    
 When using Solver:
    Once executed, a menu will pop up with 2 options:
      (1) Solve the 8-Puzzle Problem with a RANDOMLY GENERATED puzzle
      (2) Solve the 8-puzzle problem with a Specific puzzle Inputed by the USER
      
    If (1) is selected:
         -A solvable random 8-puzzle will be generated and solved using Heuristic 1 and Heuristic 2
         -All the steps to solving the puzzle will be printed as well as the Depth of the Solution, 
          the Number of Nodes Generated, and the Run Time.
    If (2) is selected:
         -User will be prompted to input an 8-puzzle in the form:
             012345678      
           where the first 3 numbers represent the first row of the 3x3 board, the next 3 numbers represent
           the middle row, and the last 3 numbers represent the last row
         -All the steps to solving the puzzle will be printed as well as the Depth of the Solution, 
          the Number of Nodes Generated, and the Run Time for both Heuristics
          
    After every game, whether you selected option (1) or (2), you will be asked if you want to play again,
    and if so the menu will pop up again and you can make another selection
    This prompt takes an input of either "Y"(for "yes") or "N"(for "no")       
        - Y and N can be either upper case or lower case. 
         
         
         
         
         
         
         
         
             