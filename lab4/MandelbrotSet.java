package lab4zad;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.*;

public class MandelbrotSet extends JFrame {
  
	private static final long serialVersionUID = 1L;
	private static int ZOOM = 150;
    private BufferedImage bufferedImage;

    public MandelbrotSet(int iterationLimit, int threadsNumber, int whichThreadPool) {
        super("Mandelbrot Set");
        setBounds(100, 100, 800, 600);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        bufferedImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);

        ExecutorService pool;
        if(whichThreadPool == 0) {
            pool = Executors.newFixedThreadPool(threadsNumber);
        } else if(whichThreadPool == 1) {
            pool = Executors.newScheduledThreadPool(threadsNumber);
        } else {
            pool = Executors.newCachedThreadPool();
        }

        Set<Future<Integer>> set = new HashSet<>();
        for(int y=0; y<getHeight(); y++) {
            for(int x=0; x<getWidth(); x++) {
                Callable<Integer> callabe = new SingleTask(bufferedImage, x, y, iterationLimit, ZOOM);
                Future<Integer> future = pool.submit(callabe);
                set.add(future);
            }
        }

        for(Future<Integer> future: set) {
            try {
                future.get();
            } catch(InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        setVisible(false);
        dispose();
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(bufferedImage, 0, 0, this);
    }

}
