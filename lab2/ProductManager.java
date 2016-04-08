package lab2;

import java.util.ArrayList;

public class ProductManager {

	public static void main(String[] args) {
		
		ProductBuffer buf = new ProductBuffer(3);
		
		ArrayList<Thread> producents = new ArrayList<Thread>();
		
		for(int j=0 ; j< 10;j++){
			producents.add(new Thread(){
			public void run() {
				for(int i=0;i<100 ; i++){
					buf.addProduct("Product " );
				}}});
		}
		
		Thread consumer = new Thread(){
			public void run() {
				for(int i=0;i<100 ; i++){
					System.out.println(buf.getProduct(i) + " removed");
				}
			}
		};
		for(int j=0 ; j< 10;j++)
			producents.get(j).start();
		consumer.start();
	}
}
