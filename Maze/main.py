import string
import networkx as nx
import sys
import time

def createCity(graph, numCities):
    created = 0
    letterList = string.ascii_uppercase + string.ascii_lowercase
    for l in letterList:
        if created == numCities:
            return graph
        else:
            graph.add_node(l)
            created += 1
    for l1 in letterList:
         for l2 in letterList:
             if created == numCities:
                 return graph
             else:
                 graph.add_node(l1 + l2)
                 created += 1
    for l1 in letterList:
         for l2 in letterList:
             for l3 in letterList:
                 if created == numCities:
                     return graph
                 else:
                     graph.add_node(l1 + l2 + l3)
                     created += 1

def sort(neighbors):
    list = []
    for n in neighbors:
        if len(n.split()) == 2:
            list.append(n)
        else:
            list.append('null ' + n)
    list.sort(key = lambda x:x.split()[1])
    for i in range(0,len(list)):
        if list[i].split()[0] == 'null':
            list[i] = list[i].split()[1]
    return iter(list)

count = 0
graph = nx.DiGraph()
cityGraph = nx.Graph()
lines = []
max = 2
t = 0
for line in sys.stdin:
    if count == 0:
        [numTown, numRoute, start, end] = line.split()
        t = time.time()
        numTown = int(numTown)
        numRoute = int(numRoute)
        graph.add_node(start)
        graph.add_node(end)
        max = numRoute
        createCity(cityGraph, numTown)
    elif count <= max:
        lines.append(line)
    if count == max:
        break
    count += 1

#Iterate through every route
for i in range(0,numRoute):
    route = lines[i]
    [rStart, rEnd, rColor, rType] = route.split()
    cityGraph.add_edge(rStart, rEnd)
    if rStart != end:
        graph.add_node(rStart + " " + rEnd, color=rColor, type=rType)
    if rEnd != end:
        graph.add_node(rEnd + " " + rStart, color=rColor, type=rType)

info = graph.nodes.data()

for node in graph.nodes:
    if len(node.split()) == 2:
        if node.split()[0] == start:
            graph.add_edge(start,node)
        if node.split()[1] == end:
            graph.add_edge(node,end)
        else:
            for [a,b] in cityGraph.edges(node.split()[1]):
                node2 = a + " " + b
                if len(node2.split()) == 2:
                    if node.split()[1] == node2.split()[0] and node.split()[0] != node2.split()[1] and \
                            (info.__getitem__(node)['color'] == info.__getitem__(node2)['color'] or
                             info.__getitem__(node)['type'] == info.__getitem__(node2)['type']):
                        graph.add_edge(node,node2)

path = dict(nx.bfs_predecessors(graph, start, sort_neighbors = sort))

if not path.__contains__(end):
    print("NO PATH")
else:
    node = end
    cities = end
    while node != start:
        pred = path[node]
        if pred != start:
            cities = pred.split()[0] + " " + cities
        node = pred

    print(cities)


#print(time.time() - t)
