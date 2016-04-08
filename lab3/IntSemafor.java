package lab3;

public class IntSemafor {

	int signals  ;
	
	
	public IntSemafor(int c){
		this.signals = c;
		}
	
	public synchronized void acquire() {
		System.out.println("before decr "+ signals);
		if(signals >0) {
			signals--;
		}
		else{
			while(signals<1){
				try {	
					wait();
					} 
				catch (InterruptedException e) {
					e.printStackTrace();
			}
		}	
		signals--;
	}
	}
	
	public synchronized void release() {
		System.out.println("before inc " +signals);
		signals ++;
		notifyAll();
		}
}