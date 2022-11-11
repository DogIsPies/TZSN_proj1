import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

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
    private int slices = 8;
    private int offsetX, offsetY;
    private final int windowSize = 300;

    private boolean showSlices = true;

    public DrawArea() {
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
                    g2.setStroke(new BasicStroke(10));
                    g2.drawLine(oldX-offsetX, oldY-offsetY, currentX-offsetX, currentY-offsetY);
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
            image = new BufferedImage(windowSize, windowSize, BufferedImage.TYPE_INT_ARGB);
            imageLines = new BufferedImage(windowSize, windowSize, BufferedImage.TYPE_INT_ARGB);
            g2 = (Graphics2D) image.getGraphics();
            g2_lines = (Graphics2D) imageLines.getGraphics();

            // enable antialiasing
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // clear draw area
            clear();
        }
        offsetX = (getWidth()-windowSize)/2;
        offsetY = (getHeight()-windowSize)/2;
        g2.setStroke(new BasicStroke(1));
        if (showSlices){
            g2_lines.setPaint(Color.black);
            for (int i = 1; i < this.slices; i++) {
                g2_lines.drawLine(i*(windowSize/slices),0, i*(windowSize/slices), getHeight());
                g2_lines.drawLine(0,i*(windowSize/slices),  getWidth(),i*(windowSize/slices));
                g.drawImage(image, offsetX, offsetY, null);
                g.drawImage(imageLines, offsetX, offsetY, null);
            }
        }else{
            g.drawImage(image, offsetX, offsetY, null);
        }

    }

    // now we create exposed methods
    public void clear() {
        image = new BufferedImage(windowSize, windowSize, BufferedImage.TYPE_4BYTE_ABGR);
        g2 = (Graphics2D) image.getGraphics();
        // enable antialiasing
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setPaint(Color.white);
        // draw white on entire draw area to clear
        g2.fillRect(0, 0, windowSize, windowSize);
        g2.setPaint(Color.black);
        repaint();
    }

    public void save() throws IOException {
        ImageIO.write((RenderedImage) image, "PNG", new File("filename.png"));
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
    }

    public void showLines(){
        showSlices = !showSlices;
        repaint();
    }



}