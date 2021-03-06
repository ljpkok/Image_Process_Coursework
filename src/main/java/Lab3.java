import java.awt.image.BufferedImage;

/**
 * @author Jiping Lyu
 * Implement lab3 content: arithmetic and Boolean operations
 */
public class Lab3 {

    public static BufferedImage addition(BufferedImage timg1, BufferedImage timg2){
        int w = timg1.getWidth();
        int h = timg1.getHeight();
        // Convert the image to array
        int[][][] imageArray1 = App.convertToArray(timg1);
        int[][][] imageArray2 = App.convertToArray(timg2);
        int[][][] imageArray3 = App.convertToArray(timg1);

        // Add each value of the image together
        for(int y=0; y<h; y++){
            for(int x=0; x<w; x++){
                imageArray3[x][y][1] = imageArray1[x][y][1] + imageArray2[x][y][1];
                imageArray3[x][y][2] = imageArray1[x][y][2] + imageArray2[x][y][2];
                imageArray3[x][y][3] = imageArray1[x][y][3] + imageArray2[x][y][3];
            }
        }
        return App.autoShiftAndRescale(imageArray3);
    }

    public static BufferedImage subtraction(BufferedImage timg1, BufferedImage timg2){
        int w = timg1.getWidth();
        int h = timg1.getHeight();
        // Convert the image to array
        int[][][] ImageArray1 = App.convertToArray(timg1);
        int[][][] ImageArray2 = App.convertToArray(timg2);
        int[][][] imageArray3 = App.convertToArray(timg1);

        // Add each value of the image together
        for(int y=0; y<h; y++){
            for(int x=0; x<w; x++){
                imageArray3[x][y][1] = Math.abs(ImageArray1[x][y][1] - ImageArray2[x][y][1]);
                imageArray3[x][y][2] = Math.abs(ImageArray1[x][y][2] - ImageArray2[x][y][2]);
                imageArray3[x][y][3] = Math.abs(ImageArray1[x][y][3] - ImageArray2[x][y][3]);
            }
        }
        return App.autoShiftAndRescale(imageArray3);
    }

    public static BufferedImage multiplication(BufferedImage timg1, BufferedImage timg2){
        int w = timg1.getWidth();
        int h = timg1.getHeight();
        // Convert the image to array
        int[][][] ImageArray1 = App.convertToArray(timg1);
        int[][][] ImageArray2 = App.convertToArray(timg2);
        int[][][] imageArray3 = App.convertToArray(timg1);

        // Add each value of the image together
        for(int y=0; y<h; y++){
            for(int x=0; x<w; x++){
                imageArray3[x][y][1] = (ImageArray1[x][y][1] * ImageArray2[x][y][1]); //r
                imageArray3[x][y][2] = (ImageArray1[x][y][2] * ImageArray2[x][y][2]); //g
                imageArray3[x][y][3] = (ImageArray1[x][y][3] * ImageArray2[x][y][3]); //b
            }
        }
        return App.autoShiftAndRescale(imageArray3);
    }

    public static BufferedImage division(BufferedImage timg1, BufferedImage timg2){
        int w = timg1.getWidth();
        int h = timg1.getHeight();
        // Convert the image to array
        int[][][] ImageArray1 = App.convertToArray(timg1);
        int[][][] ImageArray2 = App.convertToArray(timg2);
        int[][][] imageArray3 = App.convertToArray(timg1);

        // Add each value of the image together
        for(int y=0; y<h; y++){
            for(int x=0; x<w; x++){
                //Can't divide by 0, so add an if expression
                imageArray3[x][y][1] = (ImageArray2[x][y][1] == 0 ) ? ImageArray1[x][y][1] :
                        (ImageArray1[x][y][1] / ImageArray2[x][y][1]); //r
                imageArray3[x][y][2] = (ImageArray2[x][y][2] == 0 ) ? ImageArray1[x][y][2] :
                        (ImageArray1[x][y][2] / ImageArray2[x][y][2]); //r
                imageArray3[x][y][3] = (ImageArray2[x][y][3] == 0 ) ? ImageArray1[x][y][3] :
                        (ImageArray1[x][y][3] / ImageArray2[x][y][3]); //r
            }
        }
        // Convert the array to BufferedImage with auto rescaling
        return App.autoShiftAndRescale(imageArray3);
    }

    public static BufferedImage not(BufferedImage timg1){
        int w = timg1.getWidth();
        int h = timg1.getHeight();
        // Convert the image to array
        int[][][] ImageArray1 = App.convertToArray(timg1);
        int[][][] ImageArray2 = App.convertToArray(timg1);
        int r,g,b;
        // Codes from lecture
        for(int y=0; y<h; y++){
            for(int x=0; x<w; x++){
                r = ImageArray1[x][y][1];
                g = ImageArray1[x][y][2];
                b = ImageArray1[x][y][3];
                /*
                  ~ ??????????????????????????????????????????????????????0??????1???1??????0???
                  & ????????????????????????1???????????????1????????????0
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
        int[][][] ImageArray3 = App.convertToArray(timg1);

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
        int[][][] ImageArray3 = App.convertToArray(timg1);

        // Add each value of the image together
        for(int y=0; y<h; y++){
            for(int x=0; x<w; x++){
                /* | bitwise Inclusive or
                *  ???????????????????????? 0??????????????? 0???????????? 1
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
        int[][][] ImageArray3 = App.convertToArray(timg1);

        // Add each value of the image together
        for(int y=0; y<h; y++){
            for(int x=0; x<w; x++){
                /* ^ bitwise exclusive or
                 *  ??????????????????????????????????????????0????????????1
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
        return App.convertToBimage(ImageArray1);
    }


}
