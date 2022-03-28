import java.awt.image.BufferedImage;
import java.util.Arrays;

/**
 * @author Jiping Lyu
 * Order-statistics Filtering
 */
public class Lab7 {

    private static final int[] rWindow = new int[9];
    private static final int[] gWindow = new int[9];
    private static final int[] bWindow = new int[9];
    private static int k;
    public static BufferedImage saltPepperNoiseGenerator(BufferedImage timg){
        int width = timg.getWidth();
        int height = timg.getHeight();
        int[][][] array2 = App.convertToArray(timg);
        for(int y=0; y<height; y++){
            for(int x=0; x<width; x++){
                double rand = Math.random();
                if (rand > 0.9){
                    array2[x][y][1] = array2[x][y][2] =  array2[x][y][3] = 255;
                } else if (rand < 0.1){
                    array2[x][y][1] = array2[x][y][2] =  array2[x][y][3] =  0;
                }
            }}
        return App.convertToBimage(array2);
    }

    public static BufferedImage minFiltering(BufferedImage timg) {
        int width = timg.getWidth();
        int height = timg.getHeight();
        int[][][] array1 = App.convertToArray(timg);
        int[][][] array2 = App.convertToArray(timg);

        /* Codes edited from lecture slides*/
        for (int y = 1; y < height -1 ; y++) {
            for (int x = 1; x < width -1 ; x++) {
                k = 0;
                for (int s = -1; s <= 1; s++) {
                    for (int t = -1; t <= 1; t++) {
                        rWindow[k] = array1[x + s][y + t][1];
                        gWindow[k] = array1[x + s][y + t][2];
                        bWindow[k] = array1[x + s][y + t][3];
                        k++;
                    }
                }
                Arrays.sort(rWindow);
                Arrays.sort(gWindow);
                Arrays.sort(bWindow);
                array2[x][y][1] = rWindow[0]; //r
                array2[x][y][2] = gWindow[0]; //g
                array2[x][y][3] = bWindow[0]; //b
            }
        }
        return App.convertToBimage(array2);
    }

    public static BufferedImage maxFiltering(BufferedImage timg){
        int width = timg.getWidth();
        int height = timg.getHeight();
        int[][][] array1 = App.convertToArray(timg);
        int[][][] array2 = App.convertToArray(timg);

        /* Codes edited from lecture slides*/
        for (int y = 1; y < height -1 ; y++) {
            for (int x = 1; x < width - 1; x++) {
                k = 0;
                for (int s = -1; s <= 1; s++) {
                    for (int t = -1; t <= 1; t++) {
                        rWindow[k] = array1[x + s][y + t][1]; //r
                        gWindow[k] = array1[x + s][y + t][2]; //g
                        bWindow[k] = array1[x + s][y + t][3]; //b
                        k++;
                    }
                }
                Arrays.sort(rWindow);
                Arrays.sort(gWindow);
                Arrays.sort(bWindow);
                array2[x][y][1] = rWindow[8]; //r
                array2[x][y][2] = gWindow[8]; //g
                array2[x][y][3] = bWindow[8]; //b
            }
        }
        return App.convertToBimage(array2);
    }

    public static BufferedImage midPointFiltering(BufferedImage timg){
        int width = timg.getWidth();
        int height = timg.getHeight();
        int[][][] array1 = App.convertToArray(timg);
        int[][][] array2 = App.convertToArray(timg);
        /* Codes edited from lecture slides*/
        for (int y = 1; y < height -1 ; y++) {
            for (int x = 1; x < width -1 ; x++) {
                k = 0;
                for (int s = -1; s <= 1; s++) {
                    for (int t = -1; t <= 1; t++) {
                        rWindow[k] = array1[x + s][y + t][1]; //r
                        gWindow[k] = array1[x + s][y + t][2]; //g
                        bWindow[k] = array1[x + s][y + t][3]; //b
                        k++;
                    }
                }
                Arrays.sort(rWindow);
                Arrays.sort(gWindow);
                Arrays.sort(bWindow);
                array2[x][y][1] = (rWindow[0]+rWindow[8])/2; //r
                array2[x][y][2] = (gWindow[0]+gWindow[8])/2; //g
                array2[x][y][3] = (bWindow[0]+bWindow[8])/2; //b
            }
        }
        return App.convertToBimage(array2);
    }

    public static BufferedImage medianFiltering(BufferedImage timg){
        int width = timg.getWidth();
        int height = timg.getHeight();
        int[][][] array1 = App.convertToArray(timg);
        int[][][] array2 = App.convertToArray(timg);
        /* Codes edited from lecture slides*/
        for (int y = 1; y < height - 1 ; y++) {
            for (int x = 1; x < width - 1; x++) {
                k = 0;
                for (int s = -1; s <= 1; s++) {
                    for (int t = -1; t <= 1; t++) {
                        rWindow[k] = array1[x + s][y + t][1]; //r
                        gWindow[k] = array1[x + s][y + t][2]; //g
                        bWindow[k] = array1[x + s][y + t][3]; //b
                        k++;
                    }
                }
                Arrays.sort(rWindow);
                Arrays.sort(gWindow);
                Arrays.sort(bWindow);
                array2[x][y][1] = rWindow[4]; //r
                array2[x][y][2] = gWindow[4]; //g
                array2[x][y][3] = bWindow[4]; //b
            }
        }
        return App.convertToBimage(array2);
    }
}
