package lab1;

public class Counter {

	int count;
	public synchronized void increment(){
		count ++;
	}

	public synchronized void decrement(){
		count--;
	}

	}

