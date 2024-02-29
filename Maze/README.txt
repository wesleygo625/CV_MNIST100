Maze project
Solo school project for Algorithms 

Solve a maze with specific rules for going between nodes. 

For this problem we assume a network representing a city with a complicated public transit system. 
The city has 3 rival companies (Red, Green, Blue) that each offer lines of 4 types (Horse, Cable car, Trolley, Bus).
When arriving at a node, you can only leave on an edge which has the same company or type as the edge you arrived on. 
You cannot take one line and then turn around and take the same line back. 
Given a start and end location I generate the fastest possible path following these rules, or state there is no path.

Input Format:
The first line has the number of villiages, the number of lines, starting city, and ending city. (15 5 A D)
After that each newline represents a line with its start city, end city, company, and type (B A R C)

Output Format:
A list of strings representing the town names in order. (A C E F D)

This is solved by doing a breadth first search of the nodes, where nodes are stored for each line, with their edges being cities.
This seems reversed but it makes the type connections much easier to keep trck of. 