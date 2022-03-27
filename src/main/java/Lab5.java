import java.awt.image.BufferedImage;
/**
 * @author Jiping Lyu
 * Histogram & Histogram Equalisation
 */
public class Lab5 {

    public static float[][] findHistogram(BufferedImage timg){
        int width = timg.getWidth();
        int height = timg.getHeight();
        int[][][] array = App.convertToArray(timg);
        float [] histogramR = new float[256];
        float [] histogramG = new float[256];
        float [] histogramB = new float[256];
        int r,g,b;
        /* codes from lecture slides */
        // To construct the histograms for RGB components of an image
        for(int y=0; y<height; y++){ // bin histograms
            for(int x=0; x<width; x++){
                r = array[x][y][1]; //r
                g = array[x][y][2]; //g
                b = array[x][y][3]; //b
                histogramR[r]++;
                histogramG[g]++;
                histogramB[b]++;
            }
        }
        float[][] tempA =  {histogramR, histogramG, histogramB};
        return tempA;
    }

    public static float[][] normaliseHistogram(BufferedImage timg) {
        int width = timg.getWidth();
        int height = timg.getHeight();
        float[][] tempA = findHistogram(timg);
        float[] nHistogramR = new float[256];
        float[] nHistogramG = new float[256];
        float[] nHistogramB = new float[256];
        //Codes from lecture
        for(int k=0; k<256; k++){ // Normalisation
            nHistogramR[k] = tempA[0][k]/height/width; // r
            nHistogramG[k] = tempA[1][k]/height/width; // g
            nHistogramB[k] = tempA[2][k]/height/width; // b
        }
        float[][] tempB =  {nHistogramR, nHistogramG, nHistogramB};
        return tempB;
    }

    public static BufferedImage histogramEqualisation(BufferedImage timg) {
        int width = timg.getWidth();
        int height = timg.getHeight();
        int[][][] imageArray1 = App.convertToArray(timg);
        /* Generate Normalised Histogram from The original image */
        float[][] tempA = normaliseHistogram(timg);
        float[] nHistogramR = tempA[0];
        float[] nHistogramG = tempA[1];
        float[] nHistogramB = tempA[2];

        /* Look Up Table to store equalised result */
        float[] histogramRLUT = new float[256];
        float[] histogramGLUT = new float[256];
        float[] histogramBLUT = new float[256];
        histogramRLUT[0] = nHistogramR[0];
        histogramGLUT[0] = nHistogramG[0];
        histogramBLUT[0] = nHistogramB[0];
        /*
        *  Lecture topic 3, slide number 49
        *  Find the cumulative distribution based on the probability.
        */
        for (int i = 1; i < 255; i++) {
            histogramRLUT[i] = histogramRLUT[i-1]+nHistogramR[i];
            histogramGLUT[i] = histogramGLUT[i-1]+nHistogramG[i];
            histogramBLUT[i] = histogramBLUT[i-1]+nHistogramB[i];
        }
        /*
         *  Multiply cumulative values s'k by the
         *  maximum gray-level value L-1 and round the
         *  results to obtain sk.
         */
        for (int i = 1; i < 255; i++) {
            histogramRLUT[i] = Math.round(histogramRLUT[i] * 255);
            histogramGLUT[i] = Math.round(histogramGLUT[i] * 255);
            histogramBLUT[i] = Math.round(histogramBLUT[i] * 255);
        }

        /*
         *  Map the original gray-level value rk to the
         *  resulting value sk obtained in Step 3.
         */
        for(int y=0; y<height; y++){
            for(int x=0; x<width; x++){
                imageArray1[x][y][1] = (int) histogramRLUT[imageArray1[x][y][1]];
                imageArray1[x][y][2] = (int) histogramGLUT[imageArray1[x][y][2]];
                imageArray1[x][y][3] = (int) histogramBLUT[imageArray1[x][y][3]];
            }
        }
        return App.convertToBimage(imageArray1);
    }
}
