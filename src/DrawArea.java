import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

/**
 * Component for drawing !
 *
 * @author sylsau
 *
 */
public class DrawArea extends JComponent {

    // Image in which we're going to draw
    private BufferedImage image, imageLines, imageCombined;
    // Graphics2D object ==> used to draw on
    private Graphics2D g2;
    private Graphics2D g2_lines;
    // Mouse coordinates
    private int currentX, currentY, oldX, oldY;
    private int slices = 8, count0, count1, count2;
    private final float windowSize = 300f;
    List<float[][]> array0 = new ArrayList<float[][]>();
    List<float[][]> array1 = new ArrayList<float[][]>();
    List<float[][]> array2 = new ArrayList<float[][]>();

    private boolean showSlices = true;

    public DrawArea() {
        checkFolders();
        readData();
        setDoubleBuffered(false);
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                // save coord x,y when mouse is pressed
                oldX = e.getX();
                oldY = e.getY();
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                // coord x,y when drag mouse
                currentX = e.getX();
                currentY = e.getY();

                if (image != null) {
                    // draw line if g2 context not null
                    g2.setStroke(new BasicStroke(20f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                    g2.drawLine(oldX-(getWidth()-(int) windowSize)/2, oldY-(getHeight()-(int) windowSize)/2, currentX-(getWidth()-(int) windowSize)/2, currentY-(getHeight()-(int) windowSize)/2);
                    // refresh draw area to repaint
                    repaint();
                    // store current coords x,y as olds x,y
                    oldX = currentX;
                    oldY = currentY;
                }
            }
        });
    }

    protected void paintComponent(Graphics g) {
        if (image == null) {
            // image to draw null ==> we create
            image = new BufferedImage((int) windowSize, (int) windowSize, BufferedImage.TYPE_BYTE_GRAY);
            imageLines = new BufferedImage((int) windowSize, (int) windowSize, BufferedImage.TYPE_INT_ARGB);
            g2 = (Graphics2D) image.getGraphics();
            g2_lines = (Graphics2D) imageLines.getGraphics();
            g2_lines.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);


            // enable antialiasing
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // clear draw area
            clear();
        }
        g2.setStroke(new BasicStroke(1));
        if (showSlices){
            g2_lines.setPaint(Color.black);
            for (int i = 1; i <= this.slices; i++) {
                Line2D lineX = new Line2D.Float(i*(windowSize/slices),0, i*(windowSize/slices), getHeight());
                Line2D lineY = new Line2D.Float(0,i*(windowSize/slices),  getWidth(),i*(windowSize/slices));
                g2_lines.draw(lineX);
                g2_lines.draw(lineY);
                g.drawImage(image, (getWidth()-(int) windowSize)/2, (getHeight()-(int) windowSize)/2, null);
                g.drawImage(imageLines, (getWidth()-(int) windowSize)/2, (getHeight()-(int) windowSize)/2, null);
            }
        }else{
            g.drawImage(image, (getWidth()-(int) windowSize)/2, (getHeight()-(int) windowSize)/2, null);
        }

    }

    public void clear() {
        image = new BufferedImage((int) windowSize, (int) windowSize, BufferedImage.TYPE_4BYTE_ABGR);
        g2 = (Graphics2D) image.getGraphics();
        // enable antialiasing
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setPaint(Color.white);
        // draw white on entire draw area to clear
        g2.fillRect(0, 0, (int) windowSize, (int) windowSize);
        g2.setPaint(Color.black);
        repaint();
    }

    public void save(int letter) throws IOException {
        switch (letter){
            case 0 -> {
                ImageIO.write((RenderedImage) image, "PNG", new File("./letters/0/" + array0.size() + ".png"));
                array0.add(getArray(image));
            }
            case 1 -> {
                ImageIO.write((RenderedImage) image, "PNG", new File("./letters/1/" + array1.size() + ".png"));
                array1.add(getArray(image));
            }
            case 2 -> {
                ImageIO.write((RenderedImage) image, "PNG", new File("./letters/2/" + array2.size() + ".png"));
                array2.add(getArray(image));

            }
        }
    }

    private void checkFolders(){
        try {
            Files.createDirectories(Paths.get("./letters/0"));
            Files.createDirectories(Paths.get("./letters/1"));
            Files.createDirectories(Paths.get("./letters/2"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void load() throws IOException {
        image = ImageIO.read(new File("filename.png"));
        repaint();

    }
    public void edit(){
        g2 = (Graphics2D) image.getGraphics();
        // enable antialiasing
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        // update panel with new paint image
        g2.setPaint(Color.black);
        repaint();
    }
    public void setSlices(int slices){
        this.slices = slices;
        imageLines =new BufferedImage((int) windowSize, (int) windowSize, BufferedImage.TYPE_INT_ARGB);
        g2_lines = (Graphics2D) imageLines.getGraphics();
        g2_lines.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);


    }

    public void showLines(){
        showSlices = !showSlices;
        repaint();
    }

    private void readData(){
        this.count0 = Objects.requireNonNull(new File("./letters/0").listFiles()).length;
        this.count1 = Objects.requireNonNull(new File("./letters/1").listFiles()).length;
        this.count2 = Objects.requireNonNull(new File("./letters/2").listFiles()).length;
    }
    private float[][] getArray(BufferedImage image) throws IOException {
        float[][] pixelCount = new float[slices][slices];
        for (int y = 0, yi=0; y < image.getHeight()-windowSize/slices; y += windowSize/slices, yi++) {
            for (int x = 0, xi = 0; x < image.getWidth()-windowSize/slices; x += windowSize/slices, xi++) {
                pixelCount[yi][xi] = countPixels(image.getSubimage(x, y,  (int) windowSize/slices,
                        (int)  windowSize/slices));
            }

        }
        return pixelCount;
    }
private float countPixels(BufferedImage image){
    float Width = (float) image.getWidth();
    float Height = (float) image.getHeight();

    int countBlack = 0;
    int countWhite = 0;
    Color testPixel;

    for (int x = 0; x <= Width - 1; x++) {
        int xi = x;
        for (int y = 0; y <= Height - 1; y++) {
            int yi = y;
            testPixel = new Color(image.getRGB(xi, yi));
            double r = testPixel.getRed();
            double g = testPixel.getGreen();
            double b = testPixel.getBlue();

            if (r == 255 && g == 255 && b == 255) {
                countWhite++;
            } else if (r == 0 && g == 0 && b == 0) {
                countBlack++;
            }

        }
    }
    return (countBlack/(Width*Height));

}


}