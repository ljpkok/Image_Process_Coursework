import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;

/**
 * @author Jiping Lyu
 * Thresholding for image segementasion
 */
public class Lab8 {

    /* Calculate mean of image based on histogram from lab5 */
    public static float[] findMean(BufferedImage timg) {
        float[][] tempA = Lab5.normaliseHistogram(timg);
        float[] nHistogramR = tempA[0];
        float[] nHistogramG = tempA[1];
        float[] nHistogramB = tempA[2];

        /* {rMean , gMean , bMean} */
        float[] mean = {0 , 0 , 0};
        /* Calculate mean based on normalised histogram */
        for (int i = 0; i < 255; i++) {
            mean[0] = mean[0] + (nHistogramR[i] * i);
            mean[1] = mean[1] + (nHistogramG[i] * i);
            mean[2] = mean[2] + (nHistogramB[i] * i);
        }
        return mean;
    }

    /* Calculate standard of image based on histogram from lab5 */
    public static float[] findSD(BufferedImage timg) {
        float[][] tempA = Lab5.normaliseHistogram(timg);
        float[] mean = findMean(timg);
        /* {rSd , gSd , bSd} */
        float[] sd = {0 , 0 , 0};
        /*   σ = √(Σ((xi-μ)^2 * P(xi)))
         *   xi: The ith value
         *   μ: The mean of the distribution
         *   P(xi): The probability of the ith value
         * (https://www.statology.org/standard-deviation-of-probability-distribution/)
         * Calculate SD based on probability distribution */
        for (int i = 0; i < 255; i++) {
            sd[0] =sd[0] +  ((i- mean[0])*(i- mean[0])*tempA[0][i]);
            sd[1] =sd[1] +  ((i- mean[1])*(i- mean[1])*tempA[1][i]);
            sd[2] =sd[2] +  ((i- mean[2])*(i- mean[2])*tempA[2][i]);
        }
        sd[0] = (float) Math.sqrt(sd[0]);
        sd[1] = (float) Math.sqrt(sd[1]);
        sd[2] = (float) Math.sqrt(sd[2]);
        return sd;
    }

    /* To segment an image by using a given value as the threshold.
    The threshold could be any number between 0 and 255.*/
    public static BufferedImage simpleT(BufferedImage timg , int k) {
        int width = timg.getWidth();
        int height = timg.getHeight();

        /*
        * Convert image to GrayScale
        * https://stackoverflow.com/questions/9131678/convert-a-rgb-image-to-grayscale-image-reducing-the-memory-in-java
        */
        BufferedImage timg2 = new BufferedImage(width, height,
                BufferedImage.TYPE_BYTE_GRAY);
        Graphics g = timg2.getGraphics();
        g.drawImage(timg, 0, 0, null);
        g.dispose();

        int[][][] array1 = App.convertToArray(timg2);
        for(int y=0; y<height; y++){
            for(int x=0; x<width; x++){
                array1[x][y][1] = array1[x][y][2] = array1[x][y][3]= (array1[x][y][1]<= k) ? 0 : 255;
            }
        }
        return App.convertToBimage(array1);
    }


    /* To automatically find a proper threshold for an image and use it to segment the image.
    The method can be the iterative algorithm for optimal threshold selection..*/
    public static BufferedImage autoT(BufferedImage timg) {
        int width = timg.getWidth();
        int height = timg.getHeight();

        /*
         * Convert image to GrayScale
         * https://stackoverflow.com/questions/9131678/convert-a-rgb-image-to-grayscale-image-reducing-the-memory-in-java
         */
        BufferedImage timg2 = new BufferedImage(width, height,
                BufferedImage.TYPE_BYTE_GRAY);
        Graphics g = timg2.getGraphics();
        g.drawImage(timg, 0, 0, null);
        g.dispose();

        int[][][] array1 = App.convertToArray(timg2);
        float[] mean = findMean(timg2);
        /*
        *  In initiation, assume that
        *   – Background
        *       • Only the four corners
        *   – Object
        *       • The others
        */
        int sumBG = array1[0][0][1] +array1[0][height-1][1]+ array1[width-1][0][1] + array1[width-1][height-1][1];
        int sumObj =  Math.round(mean[0]*height*width) - sumBG;
        float meanBG = sumBG/4f;
        float meanObj = sumObj/(height*width-4f);

        int t0 = Math.round((meanBG + meanObj)/2);
        int tt = t0;
        int tt1 = 0;


        /* While Loop Step 2-4 */
        while(Math.abs(tt1 - tt) >= t0){
            System.out.println("tt:" +tt);
            /* Calculate new T+1*/
            /* We have new t+1 and tt will be old t+1*/
            tt = (tt1 != 0) ? tt1: tt;
            sumBG = sumObj = 0;
            meanBG = meanObj = 0;
            /* Thresholding and count sumBG and sumObj*/
            for(int y=0; y<height; y++){
                for(int x=0; x<width; x++){
                    if (array1[x][y][1] <= tt ){
                        sumBG =  sumBG + array1[x][y][1];
                        meanBG++;
                    } else {
                        sumObj += array1[x][y][1];
                        meanObj++;
                    }
                }
            }
            /* Calculate mean and new T+1 */
            meanBG = (meanBG == 0) ? 0:sumBG/meanBG;
            meanObj = (meanObj == 0) ? 0:sumObj/meanObj;
            tt1 = Math.round((meanBG+meanObj)/2);
        }
        System.out.println("Threshold" + tt1);
        return simpleT(timg,tt1);
    }


}
