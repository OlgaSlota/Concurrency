package lab3zad2;

import java.util.ArrayList;
import java.util.Random;

public class ProductManager {
	
	public static void main(String[] args) {
		
		Random rand = new Random();
		ProductBuffer buf = new ProductBuffer(5);
		
		ArrayList<Thread> producents = new ArrayList<Thread>();
		ArrayList<Thread> consuments = new ArrayList<Thread>();
		
		for(int j=0 ; j< 20;j++){
			producents.add(new Thread(){
				
			public void run() {
				while(true){
					int number = rand.nextInt(3)+1;
					if(number==3)
						Thread.currentThread().setPriority(MAX_PRIORITY-2);
					buf.addProducts(number);
				}}});
				
		}
		
		
		for(int j=0 ; j< 20;j++){
			consuments.add(new Thread(){
			
			public void run() {
				while(true){
					int number = rand.nextInt(3)+1;
					if(number== 3)
						Thread.currentThread().setPriority(MAX_PRIORITY-2);
					buf.getProducts(number) ;
				}
			}
		});
		}
		
		
		for(int j=0 ; j< 20;j++){
			producents.get(j).start();
			consuments.get(j).start();
		}
	}
}
