import java.awt.image.BufferedImage;
import java.util.Arrays;

/**
 * @author Jiping Lyu
 * Implement lab3 content: arithmetic and Boolean operations
 */
public class Lab3 {

    public static BufferedImage addition(BufferedImage timg1, BufferedImage timg2){
        int w = timg1.getWidth();
        int h = timg1.getHeight();
        // Convert the image to array
        int[][][] ImageArray1 = App.convertToArray(timg1);
        int[][][] ImageArray2 = App.convertToArray(timg2);
        int[][][] ImageArray3 = new int[w][h][4];

        // Add each value of the image together
        for(int y=0; y<h; y++){
            for(int x=0; x<w; x++){
                ImageArray3[x][y][1] = ImageArray1[x][y][1] + ImageArray2[x][y][1];
                ImageArray3[x][y][2] = ImageArray1[x][y][2] + ImageArray2[x][y][2];
                ImageArray3[x][y][3] = ImageArray1[x][y][3] + ImageArray2[x][y][3];
            }
        }
        return App.autoShiftAndRescale(ImageArray3);
    }

    public static BufferedImage subtraction(BufferedImage timg1, BufferedImage timg2){
        int w = timg1.getWidth();
        int h = timg1.getHeight();
        // Convert the image to array
        int[][][] ImageArray1 = App.convertToArray(timg1);
        int[][][] ImageArray2 = App.convertToArray(timg2);
        int[][][] ImageArray3 = new int[w][h][4];

        // Add each value of the image together
        for(int y=0; y<h; y++){
            for(int x=0; x<w; x++){
                ImageArray3[x][y][1] = Math.abs(ImageArray1[x][y][1] - ImageArray2[x][y][1]);
                ImageArray3[x][y][2] = Math.abs(ImageArray1[x][y][2] - ImageArray2[x][y][2]);
                ImageArray3[x][y][3] = Math.abs(ImageArray1[x][y][3] - ImageArray2[x][y][3]);
            }
        }
        return App.autoShiftAndRescale(ImageArray3);
    }

    public static BufferedImage multiplication(BufferedImage timg1, BufferedImage timg2){
        int w = timg1.getWidth();
        int h = timg1.getHeight();
        // Convert the image to array
        int[][][] ImageArray1 = App.convertToArray(timg1);
        int[][][] ImageArray2 = App.convertToArray(timg2);
        int[][][] ImageArray3 = new int[w][h][4];

        // Add each value of the image together
        for(int y=0; y<h; y++){
            for(int x=0; x<w; x++){
                ImageArray3[x][y][1] = (ImageArray1[x][y][1] * ImageArray2[x][y][1]); //r
                ImageArray3[x][y][2] = (ImageArray1[x][y][2] * ImageArray2[x][y][2]); //g
                ImageArray3[x][y][3] = (ImageArray1[x][y][3] * ImageArray2[x][y][3]); //b
            }
        }
        return App.autoShiftAndRescale(ImageArray3);
    }

    public static BufferedImage division(BufferedImage timg1, BufferedImage timg2){
        int w = timg1.getWidth();
        int h = timg1.getHeight();
        // Convert the image to array
        int[][][] ImageArray1 = App.convertToArray(timg1);
        int[][][] ImageArray2 = App.convertToArray(timg2);
        int[][][] ImageArray3 = new int[w][h][4];

        // Add each value of the image together
        for(int y=0; y<h; y++){
            for(int x=0; x<w; x++){
                //Can't divide by 0, so add an if expression
                ImageArray3[x][y][1] = (ImageArray2[x][y][1] == 0 ) ? ImageArray1[x][y][1] :
                        (ImageArray1[x][y][1] / ImageArray2[x][y][1]); //r
                ImageArray3[x][y][2] = (ImageArray2[x][y][2] == 0 ) ? ImageArray1[x][y][2] :
                        (ImageArray1[x][y][2] / ImageArray2[x][y][2]); //r
                ImageArray3[x][y][3] = (ImageArray2[x][y][3] == 0 ) ? ImageArray1[x][y][3] :
                        (ImageArray1[x][y][3] / ImageArray2[x][y][3]); //r
            }
        }
        // Convert the array to BufferedImage with auto rescaling
        return App.autoShiftAndRescale(ImageArray3);
    }

    public static BufferedImage not(BufferedImage timg1){
        int w = timg1.getWidth();
        int h = timg1.getHeight();
        // Convert the image to array
        int[][][] ImageArray1 = App.convertToArray(timg1);
        int[][][] ImageArray2 = new int[w][h][4];
        int r,g,b;
        // Codes from lecture
        for(int y=0; y<h; y++){
            for(int x=0; x<w; x++){
                r = ImageArray1[x][y][1];
                g = ImageArray1[x][y][2];
                b = ImageArray1[x][y][3];
                /*
                  ~ 按位取反运算符翻转操作数的每一位，即0变成1，1变成0。
                  & 如果相对应位都是1，则结果为1，否则为0
                  0xFF 11111111
                 */
                ImageArray2[x][y][1] = (~r)&0xFF;
                ImageArray2[x][y][2] = (~g)&0xFF;
                ImageArray2[x][y][3] = (~b)&0xFF;
            }
        }
        // Convert the array to BufferedImage
        return App.convertToBimage(ImageArray2);
    }

    public static BufferedImage and(BufferedImage timg1, BufferedImage timg2){
        int w = timg1.getWidth();
        int h = timg1.getHeight();
        // Convert the image to array
        int[][][] ImageArray1 = App.convertToArray(timg1);
        int[][][] ImageArray2 = App.convertToArray(timg2);
        int[][][] ImageArray3 = new int[w][h][4];

        // Add each value of the image together
        for(int y=0; y<h; y++){
            for(int x=0; x<w; x++){
                /* & bitwise and */
                ImageArray3[x][y][1] = ImageArray1[x][y][1] & ImageArray2[x][y][1]; //r
                ImageArray3[x][y][2] = ImageArray1[x][y][2] & ImageArray2[x][y][2]; //g
                ImageArray3[x][y][3] = ImageArray1[x][y][3] & ImageArray2[x][y][3]; //b
            }
        }
        return App.autoShiftAndRescale(ImageArray3);
    }

    public static BufferedImage or(BufferedImage timg1, BufferedImage timg2){
        int w = timg1.getWidth();
        int h = timg1.getHeight();
        // Convert the image to array
        int[][][] ImageArray1 = App.convertToArray(timg1);
        int[][][] ImageArray2 = App.convertToArray(timg2);
        int[][][] ImageArray3 = new int[w][h][4];

        // Add each value of the image together
        for(int y=0; y<h; y++){
            for(int x=0; x<w; x++){
                /* | bitwise Inclusive or
                *  如果相对应位都是 0，则结果为 0，否则为 1
                */
                ImageArray3[x][y][1] = ImageArray1[x][y][1] | ImageArray2[x][y][1]; //r
                ImageArray3[x][y][2] = ImageArray1[x][y][2] | ImageArray2[x][y][2]; //g
                ImageArray3[x][y][3] = ImageArray1[x][y][3] | ImageArray2[x][y][3]; //b
            }
        }
        return App.autoShiftAndRescale(ImageArray3);
    }


    public static BufferedImage xor(BufferedImage timg1, BufferedImage timg2){
        int w = timg1.getWidth();
        int h = timg1.getHeight();
        // Convert the image to array
        int[][][] ImageArray1 = App.convertToArray(timg1);
        int[][][] ImageArray2 = App.convertToArray(timg2);
        int[][][] ImageArray3 = new int[w][h][4];

        // Add each value of the image together
        for(int y=0; y<h; y++){
            for(int x=0; x<w; x++){
                /* ^ bitwise exclusive or
                 *  如果相对应位值相同，则结果为0，否则为1
                 */
                ImageArray3[x][y][1] = ImageArray1[x][y][1] ^ ImageArray2[x][y][1];
                ImageArray3[x][y][2] = ImageArray1[x][y][2] ^ ImageArray2[x][y][2];
                ImageArray3[x][y][3] = ImageArray1[x][y][3] ^ ImageArray2[x][y][3];
            }
        }
        return App.autoShiftAndRescale(ImageArray3);
    }

    public static BufferedImage selectedROI(BufferedImage timg1, int[][] roi){
        int w = timg1.getWidth();
        int h = timg1.getHeight();
        // Convert the image to array
        int[][][] ImageArray1 = App.convertToArray(timg1);

        // Add each value of the image together
        for(int y=0; y<h; y++){
            for(int x=0; x<w; x++){
                ImageArray1[x][y][0] = roi[x][y];
            }
        }
//        System.out.println(Arrays.deepToString(ImageArray1));
        return App.convertToBimage(ImageArray1,0);
    }


}
