package lab4zad;
import java.awt.image.BufferedImage;
import java.util.concurrent.Callable;

class SingleTask implements Callable<Integer> {
    private BufferedImage bufferedImage;
    private int x;
    private int y;
    private int iterLimit;
    private double zoomAmount;

    public SingleTask(BufferedImage bufferedImage, int x, int y, int iterLimit, double zoomAmonut) {
        this.bufferedImage = bufferedImage;
        this.x = x;
        this.y = y;
        this.iterLimit = iterLimit;
        this.zoomAmount = zoomAmonut;
    }


    public Integer call() {
        double zx = 0;
        double zy = 0;
        double cX = (x - 400) / zoomAmount;
        double cY = (y - 300) / zoomAmount;
        int iter = iterLimit;
        double tmp;

        while (zx * zx + zy * zy < 4 && iter > 0) {
            tmp = zx * zx - zy * zy + cX;
            zy = 2.0 * zx * zy + cY;
            zx = tmp;
            iter--;
        }
        bufferedImage.setRGB(x,y,iter | (iter << 8));
        return bufferedImage.getRGB(x,y);
    }
}
