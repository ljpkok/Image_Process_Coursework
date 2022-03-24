import java.awt.image.BufferedImage;

/**
 * @author Jiping Lyu
 * Implement lab6 content: Image Filtering
 */
public class Lab6 {
    public static BufferedImage averaging(BufferedImage timg1, float[][] mask ,int totalWeight) {
        int w = timg1.getWidth();
        int h = timg1.getHeight();
        // Convert the image to array
        int[][][] imageArray = App.convertToArray(timg1);
        int[][][] imageArray1 = App.convertToArray(timg1);
        float r,g,b;
        // Codes from lecture slide
        // for Mask of size 3x3, no border extension
        for(int y=1; y<h-1; y++){
            for(int x=1; x<w-1; x++){
                r = 0; g = 0; b = 0;
                for(int s=-1; s<=1; s++){
                    for(int t=-1; t<=1; t++){
                        r = r + mask[1-s][1-t]*imageArray[x+s][y+t][1]; //r
                        g = g + mask[1-s][1-t]*imageArray[x+s][y+t][2]; //g
                        b = b + mask[1-s][1-t]*imageArray[x+s][y+t][3]; //b
                    }
                }
                imageArray1[x][y][1] = Math.round(Math.abs(r/totalWeight)); //r
                imageArray1[x][y][2] = Math.round(Math.abs(g/totalWeight));//g
                imageArray1[x][y][3] = Math.round(Math.abs(b/totalWeight));//b
            }
        }
        return App.autoShiftAndRescale(imageArray1);
    }

    public static BufferedImage convolution(BufferedImage timg1, float[][] mask) {
        int w = timg1.getWidth();
        int h = timg1.getHeight();
        // Convert the image to array
        int[][][] imageArray = App.convertToArray(timg1);
        int[][][] imageArray1 = App.convertToArray(timg1);
        float r,g,b;
        // Codes from lecture slide
        // for Mask of size 3x3, no border extension
        for(int y=1; y<h-1; y++){
            for(int x=1; x<w-1; x++){
                r = 0; g = 0; b = 0;
                for(int s=-1; s<=1; s++){
                    for(int t=-1; t<=1; t++){
                        r = r + mask[1-s][1-t]*imageArray[x+s][y+t][1]; //r
                        g = g + mask[1-s][1-t]*imageArray[x+s][y+t][2]; //g
                        b = b + mask[1-s][1-t]*imageArray[x+s][y+t][3]; //b
                    }
                }
                imageArray1[x][y][1] = Math.round(Math.abs(r));  //r
                imageArray1[x][y][2] = Math.round(Math.abs(g));//g
                imageArray1[x][y][3] = Math.round(Math.abs(b));//b
            }
        }
        return App.autoShiftAndRescale(imageArray1);
    }

    public static BufferedImage roberts(BufferedImage timg1) {
        float[][] rA = {{0,0,0},{0,0,-1},{0,1,0}};
        float[][] rB = {{0,0,0},{0,-1,-1},{0,0,0}};
        BufferedImage timg0 = convolution(timg1,rA);
        BufferedImage timg2 = convolution(timg1,rB);
        return  Lab3.addition(timg0,timg2);
    }

}
