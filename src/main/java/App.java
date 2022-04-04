import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeSet;

/**
 * @author Jiping Lyu
 */
public class App extends Component implements ActionListener {

    private static final JButton roiBTN = new JButton("ROI");
    private static final JButton OPEN_BTN = new JButton("Open");
    private static final JButton OPEN_BTN_2 = new JButton("Open2");
    private static final JFrame JFRAME = new JFrame("Image Processing Demo");
    public static int tempI;
    public static float tempF;


    /*
    List of the options(Original, Negative); correspond to the cases:
    */
    final String[] descs = {
            "Original",
            "Negative",
            "Negative Linear Transform",
            "Logarithmic Function",
            "Power-Law",
            "Random Look-up Table",
            "bit-plane slicing",
    };
    final String[] shiftAndRescale = {
            "Rescale",
            "Shift",
            "RandomAdd"
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

    final String[] thresholding = {
            "Mean",
            "Standard Deviation",
            "Simple Thresholding",
            "Automated Thresholding"
    };

    private int[][] ROI;
    int opIndex ,lastOp;
    int w, h;
    private String filePath;
    private static App app;

    /*
     * Define J component
     */
    private final JFileChooser fileChooser = new JFileChooser();
    private BufferedImage bi, biFiltered;
    private ArrayList<BufferedImage> biFilteredList = new ArrayList<BufferedImage>();

    /*
     * Bi3 is for second image
     */
    private BufferedImage bi3, biFiltered2;

    public App() {

        try {
            /* Get file current location*/
            filePath = System.getProperty("user.dir") + System.getProperty("file.separator") +
                    "\\src\\main\\resources\\images";
            bi = ImageIO.read(new File(filePath + "\\Baboon.bmp"));
//            System.out.println(Arrays.deepToString(Lab5.normaliseHistogram(bi)));
            fileChooser.setCurrentDirectory(new File(filePath));
            w = bi.getWidth(null);
            h = bi.getHeight(null);
            if (bi.getType() != BufferedImage.TYPE_INT_RGB) {
                BufferedImage bi2 = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
                Graphics big = bi2.getGraphics();
                big.drawImage(bi, 0, 0, null);
                biFilteredList.add(bi2);
                biFilteredList.add(bi2);
                biFiltered = bi = biFiltered2 = bi3 = bi2;
            }
            ROI = new int[512][512];
            for (int i = 0; i < ROI.length; i++) {
                for (int j = 0; j < ROI[0].length; j++) {
                    if (i>= 50 && i<= 200 ){
                        if (j>= 50 && j<= 200){
                            ROI[i][j] = 255;
                        }else{
                            ROI[i][j] = 0;
                        }
                    }
                    if (i>= 312 && i<= 462 ){
                        if (j>= 312 && j<= 462){
                            ROI[i][j] = 255;
                        }else{
                            ROI[i][j] = 0;
                        }
                    }
                }
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

    /**
      Convert the  Array to BufferedImage
    */
    public static BufferedImage convertToBimage(int[][][] tmpArray) {
        int width = tmpArray.length;
        int height = tmpArray[0].length;
        BufferedImage tmpimg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
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
        /*
        * Shift the min to 0, then make the diff between max and min to 255.
        */
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

        app = new App();
        JFRAME.add("Center", app);

        JComboBox choices = new JComboBox(app.getDescriptions());
        JComboBox formats = new JComboBox(app.getFormats());
        JComboBox shiftAndRescale = new JComboBox(app.getShiftAndRescale());
        JComboBox filtering = new JComboBox(app.getMasks());
        JComboBox orderStatisticsFiltering = new JComboBox(app.getLab7());
        JComboBox arithmeticAndBoolean = new JComboBox(app.getArithmeticAndBoolean());
        JComboBox histogram = new JComboBox(app.getHistogram());
        JComboBox lab8 = new JComboBox(app.getThresholding());

        choices.setActionCommand("SetFilter");
        choices.addActionListener(app);

        formats.setActionCommand("Formats");
        formats.addActionListener(app);

        shiftAndRescale.setActionCommand("ShiftAndRescale");
        shiftAndRescale.addActionListener(app);

        filtering.setActionCommand("Filtering");
        filtering.addActionListener(app);

        orderStatisticsFiltering.setActionCommand("OSF");
        orderStatisticsFiltering.addActionListener(app);

        arithmeticAndBoolean.setActionCommand("AAB");
        arithmeticAndBoolean.addActionListener(app);

        histogram.setActionCommand("HE");
        histogram.addActionListener(app);

        lab8.setActionCommand("Lab8");
        lab8.addActionListener(app);

        /* Define 2 open file button */
        OPEN_BTN.addActionListener(app);
        OPEN_BTN_2.addActionListener(app);
        roiBTN.setActionCommand("ROI");
        roiBTN.addActionListener(app);

        JPanel jPanel = new JPanel();
        jPanel.setLayout(new FlowLayout());
        jPanel.add(new JLabel("point processing/bit-plane slicing"));
        jPanel.add(choices);
        jPanel.add(new JLabel("scale/shift"));
        jPanel.add(shiftAndRescale);
        jPanel.add(new JLabel("arithmetic/boolean"));
        jPanel.add(arithmeticAndBoolean);
        jPanel.add(new JLabel("filtering"));
        jPanel.add(filtering);
        jPanel.add(new JLabel("OrderStatistics"));
        jPanel.add(orderStatisticsFiltering);
        jPanel.add(new JLabel("Histogram"));
        jPanel.add(histogram);
        jPanel.add(new JLabel("Threshold"));
        jPanel.add(lab8);
        jPanel.add(roiBTN);
        jPanel.add(new JLabel("Save As"));
        jPanel.add(formats);

        JMenuBar menuBar = new JMenuBar();
        menuBar.add(createFileMenu());
        menuBar.add(createEditMenu());
        menuBar.setVisible(true);

        JFRAME.setJMenuBar(menuBar);
        JFRAME.setExtendedState(Frame.MAXIMIZED_BOTH);
        JFRAME.add("North", jPanel);
        JFRAME.pack();
        JFRAME.setVisible(true);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(w, h);
    }

    private String[] getDescriptions() {
        return descs;
    }

    private String[] getThresholding() {
        return thresholding;
    }

    private String[] getHistogram() {
        return histogram;
    }

    private String[] getShiftAndRescale() {
        return shiftAndRescale;
    }

    private String[] getMasks() {
        return masks;
    }

    private String[] getLab7() {
        return lab7;
    }

    private String[] getArithmeticAndBoolean() {
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

    private BufferedImage getLast() {
        return biFilteredList.get(biFilteredList.size()-1);
    }


    void setOpIndex(int i) {
        opIndex = i;
    }

    @Override
    public void paint(Graphics g) { //  Repaint will call this function so the image will change.
//        filterImage();
        g.drawImage(getLast(), 0, 0, null);
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
                imageArray[x][y][1] = 255 - imageArray[x][y][1];
                imageArray[x][y][2] = 255 - imageArray[x][y][2];
                imageArray[x][y][3] = 255 - imageArray[x][y][3];
            }
        }
        return convertToBimage(imageArray);
    }

    /**
     * JChooser methods to choose from shift and rescale
     */
    public void shiftAndRescale(int k) {
        switch (k) {
            /* Rescale */
            case 0: {
                /* get float from pop up */
                new InputPop(JFRAME, "Rescale Factor", 0);
                biFilteredList.add(Lab2.imageScaling(getLast(), tempF));
//                biFiltered = Lab2.imageScaling(bi, tempF);
                return;
            }
            /* Shift*/
            case 1: {
                /* get int from pop up */
                new InputPop(JFRAME, "Shift Factor", 1);
                biFilteredList.add(Lab2.imageShifting(getLast(), tempI));
//                biFiltered = Lab2.imageShifting(bi, tempI);
            }
            /* Random and add and rescale and shift*/
            case 2: {
                /* get int from pop up */
                BufferedImage temp = Lab2.randomArray();
                biFilteredList.add(Lab3.addition(getLast(), temp));
//                biFiltered = Lab3.addition(bi, temp);
            }
            default:
        }

    }

    public void imageFiltering(int k) {
        switch (k) {
            // Averaging
            case 0:
                float[][] averagingMask = {{1, 1, 1}, {1, 1, 1}, {1, 1, 1}};
                biFiltered = Lab6.averaging(getLast(), averagingMask, 9);
                biFilteredList.add(biFiltered);
                return;
            // Weighted averaging
            case 1:
                float[][] weightedMask = {{1, 2, 1}, {2, 4, 2}, {1, 2, 1}};
                biFiltered = Lab6.averaging(getLast(), weightedMask, 16);
                biFilteredList.add(biFiltered);
                return;
            // 4-neighbour Laplacian
            case 2:
                float[][] nlMask4 = {{0, -1, 0}, {-1, 4, -1}, {0, -1, 0}};
                biFiltered = Lab6.convolution(getLast(), nlMask4);
                biFilteredList.add(biFiltered);
                return;
            // 8-neighbour Laplacian
            case 3:
                float[][] nlMask8 = {{-1, -1, -1}, {-1, 8, -1}, {-1, -1, -1}};
                biFiltered = Lab6.convolution(getLast(), nlMask8);
                biFilteredList.add(biFiltered);
                return;
            //4-neighbour Laplacian Enhancement
            case 4:
                float[][] nlemask4 = {{0, -1, 0}, {-1, 5, -1}, {0, -1, 0}};
                biFiltered = Lab6.convolution(getLast(), nlemask4);
                biFilteredList.add(biFiltered);
                return;
            //8-neighbour Laplacian Enhancement
            case 5:
                float[][] nlemask8 = {{-1, -1, -1}, {-1, 9, -1}, {-1, -1, -1}};
                biFiltered = Lab6.convolution(getLast(), nlemask8);
                biFilteredList.add(biFiltered);
                return;
            /* Roberts */
            case 6:
                float[][] rA = {{0,0,0},{0,0,-1},{0,1,0}};
                float[][] rB = {{0,0,0},{0,-1,-1},{0,0,0}};
                biFiltered = Lab3.addition(Lab6.convolution(getLast(), rA), Lab6.convolution(getLast(), rB));
                biFilteredList.add(biFiltered);
                return;
            /* Sobel X */
            case 7:
                float[][] sobelX = {{-1, 0, 1}, {-2, 0, 2}, {-1, 0, 1}};
                biFiltered = Lab6.convolution(getLast(), sobelX);
                biFilteredList.add(biFiltered);
                return;
            /* Sobel Y */
            case 8:
                float[][] sobelY = {{-1, -2, -1}, {0, 0, 0}, {1, 2, 1}};
                biFiltered = Lab6.convolution(getLast(), sobelY);
                biFilteredList.add(biFiltered);
                return;
            /* Sobel */
            case 9:
                float[][] sobel1 = {{-1, 0, 1}, {-2, 0, 2}, {-1, 0, 1}};
                float[][] sobel2 = {{-1, -2, -1}, {0, 0, 0}, {1, 2, 1}};
                biFiltered = Lab3.addition(Lab6.convolution(getLast(), sobel1), Lab6.convolution(getLast(), sobel2));
                biFilteredList.add(biFiltered);
            default:
        }
    }

    public void orderStatisticsFiltering(int k) {
        switch (k) {
            // Salt-and-Pepper Noise
            case 0:
                biFiltered = Lab7.saltPepperNoiseGenerator(getLast());
                biFilteredList.add(biFiltered);
                return;
            // Min Filtering
            case 1:
                biFiltered = Lab7.minFiltering(getLast());
                biFilteredList.add(biFiltered);
                return;
            // Max Filtering
            case 2:
                biFiltered = Lab7.maxFiltering(getLast());
                biFilteredList.add(biFiltered);
                return;
            // Midpoint Filtering
            case 3:
                biFiltered = Lab7.midPointFiltering(getLast());
                biFilteredList.add(biFiltered);
                return;
            //Median Filtering
            case 4:
                biFiltered = Lab7.medianFiltering(getLast());
                biFilteredList.add(biFiltered);
                return;
            default:
        }
    }

    public void arithmeticAndBoolean(int k) {
        switch (k) {
            // add
            case 0:
                biFiltered = Lab3.addition(getLast(),bi3);
                biFilteredList.add(biFiltered);
                return;
            // sub
            case 1:
                biFiltered = Lab3.subtraction(getLast(),bi3);
                biFilteredList.add(biFiltered);
                return;
            // mul
            case 2:
                biFiltered = Lab3.multiplication(getLast(),bi3);
                biFilteredList.add(biFiltered);
                return;
            // div
            case 3:
                biFiltered = Lab3.division(getLast(),bi3);
                biFilteredList.add(biFiltered);
                return;
            //Not
            case 4:
                biFiltered = Lab3.not(getLast());
                biFilteredList.add(biFiltered);
                return;
            //AND
            case 5:
                biFiltered = Lab3.and(getLast(),bi3);
                biFilteredList.add(biFiltered);
                return;
            //OR
            case 6:
                biFiltered = Lab3.or(getLast(),bi3);
                biFilteredList.add(biFiltered);
                return;
            //XOR
            case 7:
                biFiltered = Lab3.xor(getLast(),bi3);
                biFilteredList.add(biFiltered);
                return;
            default:
        }
    }

    public void histogramEqualisation(int k) {
        switch (k) {
            /* Finding Histogram */
            case 0:
                System.out.println("Histogram" + Arrays.deepToString(Lab5.findHistogram(getLast())));
                return;
            /* Histogram Normalisation */
            case 1:
                System.out.println("Normalisation" + Arrays.deepToString(Lab5.normaliseHistogram(getLast())));
                return;
            /* Histogram Equalisation */
            case 2:
                biFiltered = Lab5.histogramEqualisation(getLast());
                biFilteredList.add(biFiltered);
                return;
            default:
        }
    }
    public void thresholding(int k) {
        switch (k) {
            /* Mean */
            case 0:
                float[] temp = Lab8.findMean(getLast());
                System.out.println("rMean:" + temp[0] + " gMean:" + temp[1] + " bMean:" + temp[2]);
                return;
            /* Standard Deviation */
            case 1:
                float[] temp2 = Lab8.findSD(getLast());
                System.out.println("rSD:" + temp2[0] + " gSD:" + temp2[1] + " bSD:" + temp2[2]);
                return;
            /* Simple Thresholding */
            case 2:
                new InputPop(JFRAME, "Threshold", 1);
                biFiltered = Lab8.simpleT(getLast() , tempI);
                biFilteredList.add(biFiltered);
                return;
            /* Automated Thresholding */
            case 3:
                biFiltered = Lab8.autoT(getLast());
                biFilteredList.add(biFiltered);
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
                biFilteredList = new ArrayList<BufferedImage>();
                biFilteredList.add(bi);
                biFilteredList.add(bi);
                return;
            /* Image Negative */
            case 1:
                biFiltered = imageNegative(getLast());
                biFilteredList.add(biFiltered);
                return;
            // Negative Linear Transform
            case 2:
                biFiltered = Lab4.negativeLinearTrans(getLast());
                biFilteredList.add(biFiltered);
                return;
            // Logarithmic Function
            case 3:
                biFiltered = Lab4.logarithmicFunction(getLast());
                biFilteredList.add(biFiltered);
                return;
            // Power-Law
            case 4:
                new InputPop(JFRAME, "Power-Law", 0);
                biFiltered = Lab4.powerLaw(getLast(), tempF);
                biFilteredList.add(biFiltered);
                return;
            // Random Look-up Table
            case 5:
                biFiltered = Lab4.randomLUT(getLast());
                biFilteredList.add(biFiltered);
                return;
            // bit-plane slicing
            case 6:
                new InputPop(JFRAME, "Bit-plane slicing", 1);
                biFiltered = Lab4.bitPlaneSlicing(getLast(), tempI);
                biFilteredList.add(biFiltered);
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
            String lab8 = "Lab8";
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
            }else if (lab8.equals(cb.getActionCommand())) {
                thresholding(cb.getSelectedIndex());
            }
        }
        if (e.getSource() == OPEN_BTN) {
            openFile(App.this,0);

        } else if (e.getSource() == OPEN_BTN_2) {
            openFile(App.this,1);
        } else if (e.getSource() == roiBTN) {
            biFiltered =  Lab3.selectedROI(bi,ROI);
            biFilteredList.add(biFiltered);
        }
        if (e.getSource() instanceof JMenuItem) {
            JMenuItem cb = (JMenuItem) e.getSource();
            if ("mOpen1".equals(cb.getActionCommand())) {
                openFile(App.this,0);
            } else if ("mOpen2".equals(cb.getActionCommand())) {
                openFile(App.this,1);
            } else if ("mUndo".equals(cb.getActionCommand())) {
                undo();
            }

        }
        repaint();
    }

    private void undo() {
        biFilteredList.remove(biFilteredList.size()-1);
    }

    /* Menu Creation
     */
    private static JMenu createFileMenu()
    {
        JMenu menu=new JMenu("File(F)");
        menu.setMnemonic(KeyEvent.VK_F);
        JMenuItem item=new JMenuItem("Open(N)",KeyEvent.VK_N);
        item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        item.setActionCommand("mOpen1");
        item.addActionListener(app);
        menu.add(item);
        item=new JMenuItem("Open2(O)",KeyEvent.VK_O);
        item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,ActionEvent.CTRL_MASK));
        item.setActionCommand("mOpen2");
        item.addActionListener(app);
        menu.add(item);
        item=new JMenuItem("Save(S)",KeyEvent.VK_S);
        item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,ActionEvent.CTRL_MASK));
        item.setActionCommand("mSave");
        item.addActionListener(app);
        menu.add(item);
        menu.addSeparator();
        item=new JMenuItem("Exit(E)",KeyEvent.VK_E);
        item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E,ActionEvent.CTRL_MASK));
        item.setActionCommand("mExit");
        item.addActionListener(app);
        menu.add(item);
        return menu;
    }

    private static JMenu createEditMenu()
    {
        JMenu menu=new JMenu("Edit(N)");
        menu.setMnemonic(KeyEvent.VK_F);
        JMenuItem item=new JMenuItem("Undo",KeyEvent.VK_Z);
        item.setActionCommand("mUndo");
        item.addActionListener(app);
        item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z,ActionEvent.CTRL_MASK));
        menu.add(item);
        return menu;
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
                    biFilteredList = new ArrayList<BufferedImage>();
                    biFilteredList.add(bi);
                    biFilteredList.add(bi);
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