package lab1;

public class DecrIncr {

	public static void main( String [] args){
		Counter c = new Counter();

		long start = System.currentTimeMillis();
		
		Thread t1 = new Thread() {
			public void run() {
				for (int i =0 ; i<10000000; i++)
					c.increment();
				
			}
		};
		

		Thread t2 = new Thread() {
			public void run() {
				for (int i =0 ; i<10000000; i++)
				c.decrement();
			
			}
		};
		t1.start();
		t2.start();
		
		try {
			t1.join();
			t2.join();
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		long end = System.currentTimeMillis();
		System.out.println(end-start);
		System.out.println("after joins: " +c.count);

	}
}
