import java.awt.image.BufferedImage;

/**
 * @author Jiping Lyu
 * Implement lab4 content: Point Processing and Bit Plane Slicing
 */
public class Lab4 {
    // Initialise Look Up table
    private static final int[] LUT = new int[256];
    private static int[][][] ImageArray;
    private static int[][][] ImageArray1;

    // Exercise 1 : Negative Linear Transform
    public static BufferedImage negativeLinearTrans(BufferedImage timg1){
        int w = timg1.getWidth();
        int h = timg1.getHeight();
        ImageArray = App.convertToArray(timg1);
        ImageArray1 = App.convertToArray(timg1);
        // s = L - 1 - r
        for (int r = 0; r <= 255; r++) {
            LUT[r] = 256 - 1 - r;
        }
        for(int y=0; y<h; y++){
            for(int x=0; x<w; x++){
                ImageArray[x][y][1] = LUT[ImageArray1[x][y][1]];
                ImageArray[x][y][2] = LUT[ImageArray1[x][y][2]];
                ImageArray[x][y][3] = LUT[ImageArray1[x][y][3]];
            }
        }
        return App.convertToBimage(ImageArray);
    }

    // Exercise 2 : Logarithmic Function
    public static BufferedImage logarithmicFunction(BufferedImage timg1){
        int w = timg1.getWidth();
        int h = timg1.getHeight();
        ImageArray = App.convertToArray(timg1);
        ImageArray1 = App.convertToArray(timg1);
        // s = c log(1+r)
        //Codes from lecture slide
        // for generating a LUT of 256 levels for logarithmic function
        for(int k=0; k<=255; k++){
            LUT[k] = (int)(Math.log(1+k)*255/Math.log(256));
        }
        for(int y=0; y<h; y++){
            for(int x=0; x<w; x++){
                ImageArray[x][y][1] = LUT[ImageArray1[x][y][1]];
                ImageArray[x][y][2] = LUT[ImageArray1[x][y][2]];
                ImageArray[x][y][3] = LUT[ImageArray1[x][y][3]];
            }
        }
        return App.convertToBimage(ImageArray);
    }

    // Exercise 3 : Power-Law
    public static BufferedImage powerLaw(BufferedImage timg1, float p){
        int w = timg1.getWidth();
        int h = timg1.getHeight();
        ImageArray = App.convertToArray(timg1);
        ImageArray1 = App.convertToArray(timg1);
        // s = c r^p
        //Codes from lecture slide
        // for generating a LUT of 256 levels for power law (p)
        for(int k=0; k<=255; k++){
            LUT[k] = (int)(Math.pow(255,1-p)*Math.pow(k,p));
        }
        for(int y=0; y<h; y++){
            for(int x=0; x<w; x++){
                ImageArray[x][y][1] = LUT[ImageArray1[x][y][1]];
                ImageArray[x][y][2] = LUT[ImageArray1[x][y][2]];
                ImageArray[x][y][3] = LUT[ImageArray1[x][y][3]];
            }
        }
        return App.convertToBimage(ImageArray);
    }

    // Exercise 4 : Random Look-up Table
    public static BufferedImage randomLUT(BufferedImage timg1){
        int w = timg1.getWidth();
        int h = timg1.getHeight();
        ImageArray = App.convertToArray(timg1);
        ImageArray1 = App.convertToArray(timg1);
        // for generating a LUT of random number 0-255
        for (int k = 0; k <= 255; k++) {
            LUT[k] = (int) Math.round (Math.random() * 256);
        }
        for(int y=0; y<h; y++){
            for(int x=0; x<w; x++){
                ImageArray[x][y][1] = LUT[ImageArray1[x][y][1]];
                ImageArray[x][y][2] = LUT[ImageArray1[x][y][2]];
                ImageArray[x][y][3] = LUT[ImageArray1[x][y][3]];
            }
        }
        return App.convertToBimage(ImageArray);
    }

    // Exercise 5 : bit-plane slicing
    public static BufferedImage bitPlaneSlicing(BufferedImage timg1 , int k){
        int w = timg1.getWidth();
        int h = timg1.getHeight();
        ImageArray = App.convertToArray(timg1);
        ImageArray1 = App.convertToArray(timg1);
        //Codes from lecture slides
        // To find the bit plane k of an image, k can be 0,1,2,…,7
        for(int y=0; y<h; y++){
            for(int x=0; x<w; x++){
                //& operation will only be 1 or 0 , if the result is 1 then set the number to 255 otherwise 0
                ImageArray[x][y][1] = (((ImageArray1[x][y][1]>>k)&1) == 1)? 255:0; //r
                ImageArray[x][y][2] = (((ImageArray1[x][y][2]>>k)&1) == 1)? 255:0; //g
                ImageArray[x][y][3] = (((ImageArray1[x][y][3]>>k)&1) == 1)? 255:0; //b

            }
        }
        return App.convertToBimage(ImageArray);
    }



}
