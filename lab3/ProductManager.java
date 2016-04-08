package lab3;

import java.util.ArrayList;

public class ProductManager {

	public static void main(String[] args) {
		
		Buffer buf = new Buffer(5);
		
		IntSemafor semC = new IntSemafor(0);
		IntSemafor semP = new IntSemafor(5);
		BinSemafor mutex = new BinSemafor(true);
		
		ArrayList<Thread> producents = new ArrayList<Thread>();
		ArrayList<Thread> consuments = new ArrayList<Thread>();
		
		for(int j=0 ; j< 20;j++){
			producents.add(new Thread(){
			public void run() {
				while(true){
					
					semP.acquire();
					mutex.acquire();
					buf.addProduct("Product " );
					mutex.release();
					semC.release();
				}}});
		}
		
		for(int j=0 ; j< 20;j++){
			consuments.add( new Thread(){
			public void run() {
				while(true){
					
					semC.acquire();
					mutex.acquire();
					System.out.println(buf.getProduct() + " removed");
					mutex.release();
					semP.release();
				}
			}
			});
		}
		for(int j=0 ; j< 20;j++){
			producents.get(j).start();
			consuments.get(j).start();
		}
	}}