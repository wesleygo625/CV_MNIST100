Weaver project
Solo hobby project

Trying to solve the game weaver, which is somewhat like wordle.
The goal of the game is to connect two four letter words by changing one letter at a time. 
Example: Bear Boar Boat Moat Most Moss

I have 2 olutions to this, a naive approach that implements recursion to search through all possible word trees, and a graph based approach where all words are connected to words that are 1 letter away, then a search is run to find the optimal path. 