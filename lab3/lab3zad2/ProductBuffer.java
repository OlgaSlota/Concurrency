package lab3zad2;

import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ProductBuffer {
	
	ProductBuffer(int n){
		this.SIZE =n;
	}
	int SIZE ;

	final Lock lock = new ReentrantLock();
	final Condition notFull  = lock.newCondition(); 
	final Condition notEmpty = lock.newCondition(); 

	ArrayList<String> buffer = new ArrayList<String>();
	
	void addProducts(int n){
		lock.lock();
		try {
			//System.out.println(n +"try to add");
			while(buffer.size()+n>SIZE){
				notFull.await();
			}
			for(int i=0; i<n ; i++)
				buffer.add("Product ");
			System.out.println(n + " added , current state: "+ buffer.size());
			notEmpty.signalAll();
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally{
			lock.unlock();
		}		
	}
	
	void getProducts(int n){
		lock.lock();
		try {
			//System.out.println(n +"try to remove");
			while(buffer.size()<n){				
				notEmpty.await();
			}
			for(int j=0; j<n ; j++)
				buffer.remove(buffer.lastIndexOf("Product "));
			System.out.println(n + "removed, current state: "+ buffer.size());
			notFull.signalAll();
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		finally{
			lock.unlock();
		}
		}	
	}
	
