import java.awt.image.BufferedImage;

/**
 * Implement lab2 content: pixel value re-scaling/shifting
 * @author Jiping Lyu
 */
public class Lab2 {
    //************************************
    // Image Shifting
    //************************************
    public static BufferedImage imageShifting(BufferedImage timg, int t){
        //Edited from code in the Lecture Slide Topic 3
        int width = timg.getWidth();
        int height = timg.getHeight();
        //  Convert the image to array
        int[][][] ImageArray1 = App.convertToArray(timg);
        int[][][] ImageArray2 = App.convertToArray(timg);
        // To shift by t and rescale by s without finding the min and the max
        for(int y=0; y<height; y++){
            for(int x=0; x<width; x++){
                ImageArray2[x][y][1] = ImageArray1[x][y][1]+t; //r
                ImageArray2[x][y][2] = ImageArray1[x][y][2]+t; //g
                ImageArray2[x][y][3] = ImageArray1[x][y][3]+t; //b
                if (ImageArray2[x][y][1]<0) { ImageArray2[x][y][1] = 0; }
                if (ImageArray2[x][y][2]<0) { ImageArray2[x][y][2] = 0; }
                if (ImageArray2[x][y][3]<0) { ImageArray2[x][y][3] = 0; }
                if (ImageArray2[x][y][1]>255) { ImageArray2[x][y][1] = 255; }
                if (ImageArray2[x][y][2]>255) { ImageArray2[x][y][2] = 255; }
                if (ImageArray2[x][y][3]>255) { ImageArray2[x][y][3] = 255; }
            }}
        // Convert the array to BufferedImage
        return App.convertToBimage(ImageArray2);
    }

    //************************************
    // Image rescaling
    //************************************
    public static BufferedImage imageScaling(BufferedImage timg, float s){
        //Edited from code in the Lecture Slide Topic 3
        int width = timg.getWidth();
        int height = timg.getHeight();
        //  Convert the image to array
        int[][][] ImageArray1 = App.convertToArray(timg);
        int[][][] ImageArray2 = App.convertToArray(timg);
        // To shift by t and rescale by s without finding the min and the max
        for(int y=0; y<height; y++){
            for(int x=0; x<width; x++){
                ImageArray2[x][y][1] = Math.round(s*(ImageArray1[x][y][1])); //r
                ImageArray2[x][y][2] = Math.round(s*(ImageArray1[x][y][2])); //g
                ImageArray2[x][y][3] = Math.round(s*(ImageArray1[x][y][3])); //b
                if (ImageArray2[x][y][1]<0) { ImageArray2[x][y][1] = 0; }
                if (ImageArray2[x][y][2]<0) { ImageArray2[x][y][2] = 0; }
                if (ImageArray2[x][y][3]<0) { ImageArray2[x][y][3] = 0; }
                if (ImageArray2[x][y][1]>255) { ImageArray2[x][y][1] = 255; }
                if (ImageArray2[x][y][2]>255) { ImageArray2[x][y][2] = 255; }
                if (ImageArray2[x][y][3]>255) { ImageArray2[x][y][3] = 255; }
            }}
        // Convert the array to BufferedImage
        return App.convertToBimage(ImageArray2);

    }
    /**
     * Image rescaling and shift
    */
    public static BufferedImage imageScalingAndShift(BufferedImage timg, float s , int t){
        int width = timg.getWidth();
        int height = timg.getHeight();
        int[][][] array1 = App.convertToArray(timg);
        int[][][] array2 = App.convertToArray(timg);
        int rmin = Math.round(s*(array1[0][0][1])+t); int rmax = rmin;
        int gmin = Math.round(s*(array1[0][0][2])+t); int gmax = gmin;
        int bmin = Math.round(s*(array1[0][0][3])+t); int bmax = bmin;
        for(int y=0; y<height; y++){
            for(int x=0; x<width; x++){
                array2[x][y][1] = Math.round(s*(array1[x][y][1])+t);
                array2[x][y][2] = Math.round(s*(array1[x][y][2])+t);
                array2[x][y][3] = Math.round(s*(array1[x][y][3])+t);
                if (rmin>array2[x][y][1]) { rmin = array2[x][y][1]; }
                if (gmin>array2[x][y][2]) { gmin = array2[x][y][2]; }
                if (bmin>array2[x][y][3]) { bmin = array2[x][y][3]; }
                if (rmax<array2[x][y][1]) { rmax = array2[x][y][1]; }
                if (gmax<array2[x][y][2]) { gmax = array2[x][y][2]; }
                if (bmax<array2[x][y][3]) { bmax = array2[x][y][3]; }
            }}
        for(int y=0; y<height; y++){
            for(int x =0; x<width; x++){
                array2[x][y][1] = (rmax-rmin == 0) ? 255*(array2[x][y][1]-rmin) :
                        (255*(array2[x][y][1]-rmin)/(rmax-rmin));
                array2[x][y][2] = (gmax-gmin == 0) ? 255*(array2[x][y][2]-gmin) :
                        (255*(array2[x][y][2]-gmin)/(gmax-gmin));
                array2[x][y][3] = (bmax-bmin == 0) ? 255*(array2[x][y][3]-bmin) :
                        (255*(array2[x][y][3]-bmin)/(bmax-bmin));
            }}
        return App.convertToBimage(array2);
    }


}
