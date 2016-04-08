package lab1ex2;

public class DecrIncrAtomic {

	public static void main( String [] args){
		CounterAtomic ca = new CounterAtomic();
		
		long start = System.currentTimeMillis();

		Thread t1 = new Thread() {
			public void run() {
				for (int i =0 ; i<10000000; i++)
					ca.count.incrementAndGet();
				System.out.println("after inc:  "+ca.count);
			}
		};
		

		Thread t2 = new Thread() {
			public void run() {
				for (int i =0 ; i<10000000; i++)
				ca.count.decrementAndGet();
			System.out.println("after dec:  " +ca.count);
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
		System.out.println(end - start);
		System.out.println("after joins:  " +ca.count);
		

	}

}
