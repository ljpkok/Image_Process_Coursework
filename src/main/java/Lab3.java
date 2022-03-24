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
        int[][][] ImageArray1 = App.convertToArray(timg1);
        int[][][] ImageArray2 = App.convertToArray(timg2);
        int[][][] ImageArray3 = new int[w][h][4];

        // Add each value of the image together
        for(int y=0; y<h; y++){
            for(int x=0; x<w; x++){
                ImageArray3[x][y][0] = ImageArray1[x][y][0];
                ImageArray3[x][y][1] = ImageArray1[x][y][1] + ImageArray2[x][y][1]; //r
                ImageArray3[x][y][2] = ImageArray1[x][y][2] + ImageArray2[x][y][2]; //g
                ImageArray3[x][y][3] = ImageArray1[x][y][3] + ImageArray2[x][y][3]; //b
//                if (ImageArray3[x][y][1]<0) { ImageArray3[x][y][1] = 0; }
//                if (ImageArray3[x][y][2]<0) { ImageArray3[x][y][2] = 0; }
//                if (ImageArray3[x][y][3]<0) { ImageArray3[x][y][3] = 0; }
//                if (ImageArray3[x][y][1]>255) { ImageArray3[x][y][1] = 255; }
//                if (ImageArray3[x][y][2]>255) { ImageArray3[x][y][2] = 255; }
//                if (ImageArray3[x][y][3]>255) { ImageArray3[x][y][3] = 255; }
            }
        }
        return App.autoShiftAndRescale(ImageArray3);  // Convert the array to BufferedImage
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
                ImageArray3[x][y][0] = ImageArray1[x][y][0];
                ImageArray3[x][y][1] = Math.abs(ImageArray1[x][y][1] - ImageArray2[x][y][1]); //r
                ImageArray3[x][y][2] = Math.abs(ImageArray1[x][y][2] - ImageArray2[x][y][2]); //g
                ImageArray3[x][y][3] = Math.abs(ImageArray1[x][y][3] - ImageArray2[x][y][3]); //b
            }
        }
        return App.autoShiftAndRescale(ImageArray3);  // Convert the array to BufferedImage
    }


}
