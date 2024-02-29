AlgoBowl Project:
Team project from Algorithms class, designed as competition against other teams.
Code is designed to find the least possible number of additions to sum to every number in a list (NP hard problem).
Verifier code is also given, which takes in an input and output file and confirms that the solution is valid. 

Input is given as a file with a number at the start to represent number of total target numbers (1-1000), followed by the list of numbers.
Output should start with the number of calculations needed, followed by the numbers added together in each step. 

Rules:
Addition can only be done with "known" numbers, with the only starting number being one.
Must have a sum in output that equals every number in input. 

The heuristic we started with aimed to represent each number in binary and get all of the sums of powers of 2 off the bat, (1+1, 2+2, ...).
Once we had these we could just add them together as needed to get the target numbers. 

Example:
Input: (Tells us we have 3 numbers to sum to: 3, 5, and 12)
3 
2 5

Output: (Should take 3 calculations 1+1=2, 2+2=4, and 4+1=5)
3
1 1
2 2
4 1