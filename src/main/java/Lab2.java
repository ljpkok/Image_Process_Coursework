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
        /* Edited from code in the Lecture Slide Topic 3*/
        int width = timg.getWidth();
        int height = timg.getHeight();
        //  Convert the image to array
        int[][][] imageArray1 = App.convertToArray(timg);
        int[][][] imageArray2 = App.convertToArray(timg);
        // To shift by t and rescale by s without finding the min and the max
        for(int y=0; y<height; y++){
            for(int x=0; x<width; x++){
                imageArray2[x][y][1] = imageArray1[x][y][1]+t;
                imageArray2[x][y][2] = imageArray1[x][y][2]+t;
                imageArray2[x][y][3] = imageArray1[x][y][3]+t;
                if (imageArray2[x][y][1]<0) { imageArray2[x][y][1] = 0; }
                if (imageArray2[x][y][2]<0) { imageArray2[x][y][2] = 0; }
                if (imageArray2[x][y][3]<0) { imageArray2[x][y][3] = 0; }
                if (imageArray2[x][y][1]>255) { imageArray2[x][y][1] = 255; }
                if (imageArray2[x][y][2]>255) { imageArray2[x][y][2] = 255; }
                if (imageArray2[x][y][3]>255) { imageArray2[x][y][3] = 255; }
            }}
        // Convert the array to BufferedImage
        return App.convertToBimage(imageArray2);
    }

    //************************************
    // Image rescaling
    //************************************
    public static BufferedImage imageScaling(BufferedImage timg, float s){
        /* Edited from code in the Lecture Slide Topic 3*/
        int width = timg.getWidth();
        int height = timg.getHeight();
        //  Convert the image to array
        int[][][] imageArray1 = App.convertToArray(timg);
        int[][][] imageArray2 = App.convertToArray(timg);
        // To shift by t and rescale by s without finding the min and the max
        for(int y=0; y<height; y++){
            for(int x=0; x<width; x++){
                imageArray2[x][y][1] = Math.round(s*(imageArray1[x][y][1]));
                imageArray2[x][y][2] = Math.round(s*(imageArray1[x][y][2]));
                imageArray2[x][y][3] = Math.round(s*(imageArray1[x][y][3]));
                if (imageArray2[x][y][1]<0) { imageArray2[x][y][1] = 0; }
                if (imageArray2[x][y][2]<0) { imageArray2[x][y][2] = 0; }
                if (imageArray2[x][y][3]<0) { imageArray2[x][y][3] = 0; }
                if (imageArray2[x][y][1]>255) { imageArray2[x][y][1] = 255; }
                if (imageArray2[x][y][2]>255) { imageArray2[x][y][2] = 255; }
                if (imageArray2[x][y][3]>255) { imageArray2[x][y][3] = 255; }
            }}
        // Convert the array to BufferedImage
        return App.convertToBimage(imageArray2);

    }
    /**
     * Image rescaling and shift
    */
    public static BufferedImage imageScalingAndShift(BufferedImage timg, float s , int t){
        int width = timg.getWidth();
        int height = timg.getHeight();
        int[][][] array1 = App.convertToArray(timg);
        int[][][] array2 = App.convertToArray(timg);
        int rMin = Math.round(s*(array1[0][0][1])+t); int rMax = rMin;
        int gMin = Math.round(s*(array1[0][0][2])+t); int gMax = gMin;
        int bMin = Math.round(s*(array1[0][0][3])+t); int bMax = bMin;
        for(int y=0; y<height; y++){
            for(int x=0; x<width; x++){
                array2[x][y][1] = Math.round(s*(array1[x][y][1])+t);
                array2[x][y][2] = Math.round(s*(array1[x][y][2])+t);
                array2[x][y][3] = Math.round(s*(array1[x][y][3])+t);
                if (rMin>array2[x][y][1]) { rMin = array2[x][y][1]; }
                if (gMin>array2[x][y][2]) { gMin = array2[x][y][2]; }
                if (bMin>array2[x][y][3]) { bMin = array2[x][y][3]; }
                if (rMax<array2[x][y][1]) { rMax = array2[x][y][1]; }
                if (gMax<array2[x][y][2]) { gMax = array2[x][y][2]; }
                if (bMax<array2[x][y][3]) { bMax = array2[x][y][3]; }
            }}
        for(int y=0; y<height; y++){
            for(int x =0; x<width; x++){
                array2[x][y][1] = (rMax-rMin == 0) ? 255*(array2[x][y][1]-rMin) :
                        (255*(array2[x][y][1]-rMin)/(rMax-rMin));
                array2[x][y][2] = (gMax-gMin == 0) ? 255*(array2[x][y][2]-gMin) :
                        (255*(array2[x][y][2]-gMin)/(gMax-gMin));
                array2[x][y][3] = (bMax-bMin == 0) ? 255*(array2[x][y][3]-bMin) :
                        (255*(array2[x][y][3]-bMin)/(bMax-bMin));
            }}
        return App.convertToBimage(array2);
    }

    /**
     * Image rescaling and shift
     */
    public static BufferedImage randomArray(){
        int[][][] array1 = new int[512][521][4];

        for(int y=0; y<512; y++){
            for(int x=0; x<512; x++){
                array1[x][y][1] = 255;
                array1[x][y][1] = (int) Math.round(Math.random()*80f);
                array1[x][y][2] = (int) Math.round(Math.random()*80f);
                array1[x][y][3] = (int) Math.round(Math.random()*80f);

            }}
        return App.convertToBimage(array1);
    }


}
