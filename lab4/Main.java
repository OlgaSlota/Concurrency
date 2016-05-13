package lab4zad;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        List<Integer> iterations = new ArrayList<Integer>();
        List<Integer> threads = new ArrayList<Integer>();
        List<String> poolType = new ArrayList<String>();

        iterations.add(1000);
        iterations.add(3000);
        iterations.add(8000);
        
        threads.add(1);
        threads.add(5);
        threads.add(10);
        threads.add(15);
        threads.add(30);
        
        poolType.add("FixedThreadPool");
        poolType.add( "ScheduledThreadPool");
        poolType.add( "CachedThreadPool");

        for (int iteration: iterations) {
        	System.out.println( " \n iterations :  " + iteration );
            for(int thread: threads) {
            	System.out.println(  " \n threads " + thread );
                for(int pool=0; pool<3;pool++) {
                    long start = System.nanoTime();
                    new MandelbrotSet(iteration, thread, pool);
                    long end = System.nanoTime();
                    double time = (end-start)/10e9;
                    System.out.println( poolType.get(pool) +":  ..........." +"time : " + time);
                 }
            }
        }
    }
}
