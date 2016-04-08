package lab3;

public class BinSemafor {

	boolean value = true; //podniesiony
	public BinSemafor(boolean val){
		this.value = val;
	}
		
	public synchronized void acquire() {
		while(value == false){
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		if(value == true) {
			value = false;
		}
	}
	
	public synchronized void release() {
		
		value = true;
		notifyAll();
	}
}