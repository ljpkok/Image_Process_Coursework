import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.TreeSet;

/**
 * @author Jiping Lyu
 */
public class App extends Component implements ActionListener {

    //************************************
    // List of the options(Original, Negative); correspond to the cases:
    //************************************

    public static int tempI;
    public static float tempF;
    private static final JButton OPEN_BTN = new JButton("Open");
    private static final JButton OPEN_BTN_2 = new JButton("Open2");
    private static final JFrame JFRAME = new JFrame("Image Processing Demo");
    final String[] descs = {
            "Original",
            "Negative",
            "Addition",
            "Subtraction",
            "Multiplication",
            "Division",
            "Negative Linear Transform",
            "Logarithmic Function",
            "Power-Law",
            "Random Look-up Table",
            "bit-plane slicing",
    };
    final String[] shiftAndRescale = {
            "Rescale",
            "Shift",
    };
    final String[] masks = {
            "Averaging",
            "Weighted averaging",
            "4-neighbour Laplacian",
            "8- neighbour Laplacian",
            "4-neighbour Laplacian Enhancement",
            "8-neighbour Laplacian Enhancement",
            "Roberts",
            "Sobel X",
            "Sobel Y",
            "Sobel"
    };
    final String[] arithmeticAndBoolean = {
            "Addition",
            "Subtraction",
            "Multiplication",
            "Division",
            "Not",
            "AND",
            "OR",
            "XOR"
    };

    final String[] lab7 = {
            "Salt-and-Pepper Noise",
            "Min Filtering",
            "Max Filtering",
            "Midpoint Filtering",
            "Median Filtering",
    };

    final String[] histogram = {
            "Find Histogram",
            "Histogram Normalisation",
            "Histogram Equalisation",
    };

    int opIndex ,lastOp;
    int w, h;
    private String filePath;
    /**
     * Define J component
     */
    private final JFileChooser fileChooser = new JFileChooser();
    private BufferedImage bi, biFiltered;
    /**
     * Bi3 is for second image
     */
    private BufferedImage bi3, biFiltered2;

    public App() {

        try {
            /* Get file current location*/
            filePath = System.getProperty("user.dir") + System.getProperty("file.separator") +
                    "\\src\\main\\resources\\images";
            bi = ImageIO.read(new File(filePath + "\\Baboon.bmp"));
            System.out.println(Arrays.deepToString(Lab5.normaliseHistogram(bi)));
            fileChooser.setCurrentDirectory(new File(filePath));
            w = bi.getWidth(null);
            h = bi.getHeight(null);
            if (bi.getType() != BufferedImage.TYPE_INT_RGB) {
                BufferedImage bi2 = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
                Graphics big = bi2.getGraphics();
                big.drawImage(bi, 0, 0, null);
                biFiltered = bi = biFiltered2 = bi3 = bi2;
            }
        } catch (IOException e) {      // deal with the situation that th image has problem;/
            System.out.println("Image could not be read");
            System.exit(1);
        }
    }

    /**
     * Convert the Buffered Image to Array
     */
    public static int[][][] convertToArray(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();

        int[][][] result = new int[width][height][4];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int p = image.getRGB(x, y);
                int a = (p >> 24) & 0xff;
                int r = (p >> 16) & 0xff;
                int g = (p >> 8) & 0xff;
                int b = p & 0xff;

                result[x][y][0] = a;
                result[x][y][1] = r;
                result[x][y][2] = g;
                result[x][y][3] = b;
            }
        }
        return result;
    }

    /************************************
      Convert the  Array to BufferedImage
    */
    public static BufferedImage convertToBimage(int[][][] tmpArray) {

        int width = tmpArray.length;
        int height = tmpArray[0].length;

        BufferedImage tmpimg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int a = tmpArray[x][y][0];
                int r = tmpArray[x][y][1];
                int g = tmpArray[x][y][2];
                int b = tmpArray[x][y][3];

                //set RGB value

                int p = (a << 24) | (r << 16) | (g << 8) | b;
                tmpimg.setRGB(x, y, p);

            }
        }
        return tmpimg;
    }

    /**
     * Try to find maximum dynamic range
     * Set min to 0 and max to 255
     */
    public static BufferedImage autoShiftAndRescale(int[][][] imageArray) {
        int h = imageArray[0].length;
        //  Convert the image to array
        int min, max;
        min = max = imageArray[0][0][1];
        /* Find min and max */
        for (int y = 0; y < h; y++) {
            for (int[][] ints : imageArray) {
                if (min > ints[y][1]) {min = ints[y][1];}
                if (min > ints[y][2]) {min = ints[y][2];}
                if (min > ints[y][3]) {min = ints[y][3];}
                if (max < ints[y][1]) {max = ints[y][1];}
                if (max < ints[y][2]) {max = ints[y][2];}
                if (max < ints[y][3]) {max = ints[y][3];}
            }
        }
        int shiftFactor = -min;
        float rescaleFactor = 255f / (max - min);
        return Lab2.imageScalingAndShift(convertToBimage(imageArray), rescaleFactor, shiftFactor);
    }

    public static void main(String[] s) throws Exception {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        JFRAME.setPreferredSize(new Dimension(1280, 720));
        JFRAME.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        App app = new App();
        JFRAME.add("Center", app);

        JComboBox choices = new JComboBox(app.getDescriptions());
        choices.setActionCommand("SetFilter");
        choices.addActionListener(app);

        JComboBox formats = new JComboBox(app.getFormats());
        formats.setActionCommand("Formats");
        formats.addActionListener(app);

        JComboBox shiftAndRescale = new JComboBox(app.getShiftAndRescale());
        shiftAndRescale.setActionCommand("ShiftAndRescale");
        shiftAndRescale.addActionListener(app);

        JComboBox filtering = new JComboBox(app.getMasks());
        filtering.setActionCommand("Filtering");
        filtering.addActionListener(app);

        JComboBox orderStatisticsFiltering = new JComboBox(app.getLab7());
        orderStatisticsFiltering.setActionCommand("OSF");
        orderStatisticsFiltering.addActionListener(app);

        JComboBox arithmeticAndBoolean = new JComboBox(app.getArithmeticAndBoolean());
        arithmeticAndBoolean.setActionCommand("AAB");
        arithmeticAndBoolean.addActionListener(app);

        JComboBox histogram = new JComboBox(app.getHistogram());
        histogram.setActionCommand("HE");
        histogram.addActionListener(app);


        //Define 2 open file button
        OPEN_BTN.setActionCommand("openBtn");
        OPEN_BTN.addActionListener(app);
        OPEN_BTN_2.setActionCommand("open2Btn");
        OPEN_BTN_2.addActionListener(app);


        JPanel jPanel = new JPanel();

        jPanel.add(OPEN_BTN);
        jPanel.add(choices);
        jPanel.add(shiftAndRescale);
        jPanel.add(arithmeticAndBoolean);
        jPanel.add(filtering);
        jPanel.add(orderStatisticsFiltering);

        jPanel.add(histogram);
        jPanel.add(OPEN_BTN_2);
        jPanel.add(new JLabel("Save As"));
        jPanel.add(formats);


        JFRAME.add("North", jPanel);
        JFRAME.pack();
        JFRAME.setVisible(true);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(w, h);
    }

    String[] getDescriptions() {
        return descs;
    }

    public String[] getHistogram() {
        return histogram;
    }

    public String[] getShiftAndRescale() {
        return shiftAndRescale;
    }

    public String[] getMasks() {
        return masks;
    }

    public String[] getLab7() {
        return lab7;
    }

    public String[] getArithmeticAndBoolean() {
        return arithmeticAndBoolean;
    }

    /**
     * Return the formats sorted alphabetically and in lower case
     */
    public String[] getFormats() {
        String[] formats = {"bmp", "gif", "jpeg", "jpg", "png"};
        TreeSet<String> formatSet = new TreeSet<>();
        for (String s : formats) {
            formatSet.add(s.toLowerCase());
        }
        return formatSet.toArray(new String[0]);
    }

    void setOpIndex(int i) {
        opIndex = i;
    }

    @Override
    public void paint(Graphics g) { //  Repaint will call this function so the image will change.
//        filterImage();
        g.drawImage(biFiltered, 0, 0, null);
        g.drawImage(biFiltered2, biFiltered.getWidth() + 10, 0, null);
    }

    /**
     * Image Negative
    */
    public BufferedImage imageNegative(BufferedImage timg) {
        int width = timg.getWidth();
        int height = timg.getHeight();
        //  Convert the image to array
        int[][][] imageArray = convertToArray(timg);

        // Image Negative Operation:
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                imageArray[x][y][1] = 255 - imageArray[x][y][1];  //r
                imageArray[x][y][2] = 255 - imageArray[x][y][2];  //g
                imageArray[x][y][3] = 255 - imageArray[x][y][3];  //b
            }
        }

        return convertToBimage(imageArray);  // Convert the array to BufferedImage
    }

    /**
     * JChooser methods to choose from shift and rescale
     */
    public void shiftAndRescale(int k) {
        switch (k) {
            /* Rescale */
            case 0: {
                /* get int from pop up */
                new InputPop(JFRAME, "Rescale Factor", 0);
                biFiltered = Lab2.imageScaling(bi, tempF);
                return;
            }
            /* Shift*/
            case 1: {
                /* get int from pop up */
                new InputPop(JFRAME, "Shift Factor", 1);
                biFiltered = Lab2.imageShifting(bi, tempI);
            }
            default:
        }

    }

    public void imageFiltering(int k) {
        switch (k) {
            // Averaging
            case 0:
                float[][] averagingMask = {{1, 1, 1}, {1, 1, 1}, {1, 1, 1}};
                biFiltered = Lab6.averaging(bi, averagingMask, 9);
                return;
            // Weighted averaging
            case 1:
                float[][] weightedMask = {{1, 2, 1}, {2, 4, 2}, {1, 2, 1}};
                biFiltered = Lab6.averaging(bi, weightedMask, 16);
                return;
            // 4-neighbour Laplacian
            case 2:
                float[][] nlMask4 = {{0, -1, 0}, {-1, 4, -1}, {0, -1, 0}};
                biFiltered = Lab6.convolution(bi, nlMask4);
                return;
            // 8-neighbour Laplacian
            case 3:
                float[][] nlMask8 = {{-1, -1, -1}, {-1, 8, -1}, {-1, -1, -1}};
                biFiltered = Lab6.convolution(bi, nlMask8);
                return;
            //4-neighbour Laplacian Enhancement
            case 4:
                float[][] nlemask4 = {{0, -1, 0}, {-1, 5, -1}, {0, -1, 0}};
                biFiltered = Lab6.convolution(bi, nlemask4);
                return;
            //8-neighbour Laplacian Enhancement
            case 5:
                float[][] nlemask8 = {{-1, -1, -1}, {-1, 9, -1}, {-1, -1, -1}};
                biFiltered = Lab6.convolution(bi, nlemask8);
                return;
            /* Roberts */
            case 6:
                biFiltered = Lab6.roberts(bi);
                return;
            /* Sobel X */
            case 7:
                float[][] sobelX = {{-1, 0, 1}, {-2, 0, 2}, {-1, 0, 1}};
                biFiltered = Lab6.convolution(bi, sobelX);
                return;
            /* Sobel Y */
            case 8:
                float[][] sobelY = {{-1, -2, -1}, {0, 0, 0}, {1, 2, 1}};
                biFiltered = Lab6.convolution(bi, sobelY);
                return;
            /* Sobel */
            case 9:
                float[][] sobel1 = {{-1, 0, 1}, {-2, 0, 2}, {-1, 0, 1}};
                float[][] sobel2 = {{-1, -2, -1}, {0, 0, 0}, {1, 2, 1}};
                biFiltered = Lab3.addition(Lab6.convolution(bi, sobel1), Lab6.convolution(bi, sobel2));
            default:
        }
    }

    public void orderStatisticsFiltering(int k) {
        switch (k) {
            // Salt-and-Pepper Noise
            case 0:
                biFiltered = Lab7.saltPepperNoiseGenerator(bi);
                return;
            // Min Filtering
            case 1:
                biFiltered = Lab7.minFiltering(bi);
                return;
            // Max Filtering
            case 2:
                biFiltered = Lab7.maxFiltering(bi);
                return;
            // Midpoint Filtering
            case 3:
                biFiltered = Lab7.midPointFiltering(bi);
                return;
            //Median Filtering
            case 4:
                biFiltered = Lab7.medianFiltering(bi);
                return;
            default:
        }
    }

    public void arithmeticAndBoolean(int k) {
        switch (k) {
            // add
            case 0:
                biFiltered = Lab3.addition(bi,bi3);
                return;
            // sub
            case 1:
                biFiltered = Lab3.subtraction(bi,bi3);
                return;
            // mul
            case 2:
                biFiltered = Lab3.multiplication(bi,bi3);
                return;
            // div
            case 3:
                biFiltered = Lab3.division(bi,bi3);
                return;
            //Not
            case 4:
                biFiltered = Lab3.not(bi);
                return;
            //AND
            case 5:
                biFiltered = Lab3.and(bi,bi3);
                return;
            //OR
            case 6:
                biFiltered = Lab3.or(bi,bi3);
                return;
            //XOR
            case 7:
                biFiltered = Lab3.xor(bi,bi3);
                return;
            default:
        }
    }

    public void histogramEqualisation(int k) {
        switch (k) {
            /* Finding Histogram */
            case 0:
//                biFiltered = Lab3.addition(bi,bi3);
                return;
            /* Histogram Normalisation */
            case 1:
//                biFiltered = Lab3.subtraction(bi,bi3);
                return;
            /* Histogram Equalisation */
            case 2:
                biFiltered = Lab5.histogramEqualisation(bi);
                return;
            default:
        }
    }

    public void filterImage() {
        if (opIndex == lastOp) {
            return;
        }
        lastOp = opIndex;
        switch (opIndex) {
            /* original */
            case 0:
                biFiltered = bi;
                return;
            /* Image Negative */
            case 1:
                biFiltered = imageNegative(bi);
                return;
            // Addition
            case 2:
                biFiltered = Lab3.addition(bi, bi3);
                return;
            // Subtraction
            case 3:
                biFiltered = Lab3.subtraction(bi, bi3);
                return;
            // Negative Linear Transform
            case 6:
                biFiltered = Lab4.negativeLinearTrans(bi);
                return;
            // Logarithmic Function
            case 7:
                biFiltered = Lab4.logarithmicFunction(bi);
                return;
            // Power-Law
            case 8:
                new InputPop(JFRAME, "Power-Law", 0);
                biFiltered = Lab4.powerLaw(bi, tempF);
                return;
            // Random Look-up Table
            case 9:
                biFiltered = Lab4.randomLUT(bi);
                return;
            // bit-plane slicing
            case 10:
                new InputPop(JFRAME, "Bit-plane slicing", 1);
                biFiltered = Lab4.bitPlaneSlicing(bi, tempI);
                return;
            default:
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof JComboBox) {
            JComboBox cb = (JComboBox) e.getSource();
            String setFilter = "SetFilter";
            String formats = "Formats";
            String shiftAndRescale = "ShiftAndRescale";
            String imageFiltering = "Filtering";
            String orderStatisticsFiltering = "OSF";
            String arithmeticAndBoolean = "AAB";
            String histogramEqualisation = "HE";
            if (setFilter.equals(cb.getActionCommand())) {
                setOpIndex(cb.getSelectedIndex());
                filterImage();
            } else if (formats.equals(cb.getActionCommand())) {
                String format = (String) cb.getSelectedItem();
                File saveFile = new File("savedimage." + format);
                JFileChooser chooser = new JFileChooser();
                chooser.setCurrentDirectory(new File(filePath));
                chooser.setSelectedFile(saveFile);
                int rval = chooser.showSaveDialog(cb);
                if (rval == JFileChooser.APPROVE_OPTION) {
                    saveFile = chooser.getSelectedFile();
                    try {
                        assert format != null;
                        ImageIO.write(biFiltered, format, saveFile);
                    } catch (IOException ignored) {
                    }
                }
            }
            else if (shiftAndRescale.equals(cb.getActionCommand())) {
                shiftAndRescale(cb.getSelectedIndex());
            }
            else if (imageFiltering.equals(cb.getActionCommand())) {
                imageFiltering(cb.getSelectedIndex());
            }else if (orderStatisticsFiltering.equals(cb.getActionCommand())) {
                orderStatisticsFiltering(cb.getSelectedIndex());
            }else if (arithmeticAndBoolean.equals(cb.getActionCommand())) {
                arithmeticAndBoolean(cb.getSelectedIndex());
            }else if (histogramEqualisation.equals(cb.getActionCommand())) {
                histogramEqualisation(cb.getSelectedIndex());
            }
        }
        if (e.getSource() == OPEN_BTN) {
            openFile(App.this,0);

        } else if (e.getSource() == OPEN_BTN_2) {
            openFile(App.this,1);
        }
        repaint();
    }

    /** Open file in first image place */
    public void openFile(Component parent , int option) {
        // only file can be chosen
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        // multi choices
        fileChooser.setMultiSelectionEnabled(true);
        //set supported file format
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("images",
                "bmp", "gif", "jpeg", "jpg", "png"));

        int result = fileChooser.showOpenDialog(parent);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try {
                if (option == 0){
                    biFiltered = bi = ImageIO.read(file);
                    repaint();
                } else if (option == 1){
                    biFiltered2 = bi3 = ImageIO.read(file);
                    repaint();
                }
            } catch (IOException ignored) {
            }
        }
    }
}