package lab2;

import java.util.ArrayList;

public class ProductBuffer {
	
	ProductBuffer(int n){
		this.SIZE =n;
	}
	int SIZE ;

	ArrayList<String> buffer = new ArrayList<String>();
	
	synchronized void addProduct(String product){
		while(full()){
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		buffer.add(product);
		System.out.println(product + " added");
		notifyAll();
	}
	
	synchronized String getProduct(int i){
		while(empty()){
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		 String elem =buffer.remove(buffer.lastIndexOf("Product "));
		 notifyAll();
		 return elem;
		
	}
	
	synchronized boolean full(){
		if (buffer.size()==SIZE)
			return true;
		return false;
	}
	synchronized boolean empty(){
		if (buffer.size()==0)
			return true;
		return false;
	}
}
