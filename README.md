# Concurrency

Java implementation of several concurrency problems using some popular patterns:

lab1 - Incement / decrement counter problem :
- usung synchronized methods 
- using java.util.concurrent.atomic 

lab2 - Producers/ consumers problem with buffer :
- using monitors (synchronized, wait(), notifyAll() )
- using java.util.concurrent.ArrayBlockingQueue 

lab3 - Semaphores and locks to solve producers/ consumers problem with limited buffer:
- counting semaphore, binary semaphore usage
- locks and condition variables usage (adding/ removing random number of elements from bufer avoiding starvation)

lab4 - Executor pattern
