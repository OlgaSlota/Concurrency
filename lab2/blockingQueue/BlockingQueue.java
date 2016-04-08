package blockingQueue;

import java.util.concurrent.ArrayBlockingQueue;

public class BlockingQueue {
		
	static int N=5;
	
public static void main(String[] args) {
		
	ArrayBlockingQueue<String> products = new ArrayBlockingQueue<String>(N);
		
		Thread producent = new Thread(){
			public void run() {
				for(int i=0;i<100 ; i++){
					try {
						products.put(i+" Product");
						System.out.println(i+" Product added");
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
			}
		};
		Thread consumer = new Thread(){
			public void run() {
				for(int i=0;i<100 ; i++){
					try {
						System.out.println( products.take()+ " removed");
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
		
		producent.start();
		consumer.start();
		
	}

}

