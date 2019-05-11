'''
Implementation using the built-in python heapq

When do I need to use the heap data structure?
    When I need to quickly pull the max or min or median value from a collection.
'''
import heapq

t1 = [5, 7, 9, 1, 3]

print("The original list is : {}".format(t1))

heapq.heapify(t1)

print("The created heap is : {}".format(list(t1)))

print("Pushes 4:{}".format(heapq.heappush(t1, 4)))

print("The modified heap after push is : {}".format(list(t1)))

print("The smallest element is : {}".format(heapq.heappop(t1)))

print(heapq.heappushpop(t1, 2))
print("After push and pop items simultaneously: {}".format(t1))

print(heapq.heapreplace(t1, 2))
print("Use heapreplace() for push and pop items simultaneously: {}".format(t1))

print("Find the k largest elements : {}".format(heapq.nlargest(3, t1)))
print("Find the k smallest elements : {}".format(heapq.nsmallest(3, t1)))