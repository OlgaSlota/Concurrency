package lab4;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import lab4zad.MandelbrotSet;

public class URLs {
	static Random rand = new Random();
	static int N = 10;
	public static String [] links = new String[N];
	
	public static void main(String[] args) {
				
		links[0]="http://docs.oracle.com/javase/8/docs/api/java/util/concurrent/Future.html";
		links[1]="http://docs.oracle.com/javase/8/docs/api/java/util/concurrent/Executors.html"	;
		links[2]="http://www.ki.agh.edu.pl";
		links[3]="https://docs.oracle.com/javase/8/docs/api/java/util/Map.html";
		links[4]="https://docs.oracle.com/javase/8/docs/api/java/security/Provider.html";
		links[5]="https://www.youtube.com/";
		for(int i = 6 ; i<N ; i++)
			links[i]=new String(links[i-5]);
		
		
		long start = System.nanoTime();
        
      
		List<ExecutorService> executors = new ArrayList<>();
		executors.add(Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()));
		executors.add(Executors.newCachedThreadPool());
		executors.add(Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors()));
		
		for (ExecutorService ex : executors){
		for(int i = 0 ; i<URLs.N ; i++){
		Future<String> contents = ex.submit(new URLopener());
		if(contents.isDone())
			System.out.println();
			try {
				System.out.println("Length of downloaded URL : "+ contents.get().length());
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
			}
			ex.shutdown();
			double time = (System.nanoTime()- start)/10e9;
			System.out.println("\n---------total time for "+N+" URL addresses "+time+" s , "+ ex.getClass().getSimpleName() );
			} 
	}
	
}	
	

class URLopener implements Callable<String>{

		public String call() {
			
			StringBuilder result = new StringBuilder();
			URL addres;
			BufferedReader br;
			InputStream is = null;
			String line;
			int i = URLs.rand.nextInt(URLs.N);
				try {
					addres = new URL(URLs.links[i]);
					is= addres.openStream();
					 br = new BufferedReader(new InputStreamReader(is));

				        while ((line = br.readLine()) != null) {
				        	result.append(line).append("\n");
				        }
				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				finally {
					try {
						is.close();
					} catch (IOException e) {
						e.printStackTrace();
					} 
				}
			return result.toString();
		
	}
}
