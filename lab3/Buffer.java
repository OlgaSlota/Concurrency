package lab3;
	import java.util.ArrayList;

	public class Buffer {
		
		Buffer(int n){
			this.SIZE =n;
		}
		int SIZE ;

		ArrayList<String> buffer = new ArrayList<String>();
		
		synchronized void addProduct(String product){
			buffer.add(product);
			System.out.println(product + " added");
			}
		
		synchronized String getProduct(){
			 String elem =buffer.remove(buffer.lastIndexOf("Product "));
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