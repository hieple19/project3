PROJECT TITLE: CS150 Project 3 - Maze Game
AUTHOR: Hiep Le

- To run the program, the user needs to call the main 
method on the Controller class 
- The following parameters are required and passed in the
 following order
		- Limit value
		- Number of players of First type
		- Number of players of Random type
		- Number of players of Shortest type
		- Max value of the dice
		- Graph filename ("default.txt")
		- Starting and exit nodes filename ("config.txt")
		
E.g. java Controller 10 1 1 1 5 default.txt config.txt

- Once the program runs, the user will be given instructions
on how to proceed. The program accepts the following
commands:
		- x: exit program
		- c: continue program until completion
		- p: print position of players
		- i: advance one round for all players
		
- When all players have exited, program will stop and 
information about all players will be printed