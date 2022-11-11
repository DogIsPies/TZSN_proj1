import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;


public class UI extends JFrame {
        public UI(String string) {
            super(string);
            initialization();

            JSlider sliderSlices = new JSlider(JSlider.HORIZONTAL, 8,64, 8);

            JPanel panelRight = new JPanel();
            JPanel panelLeft = new JPanel(new BorderLayout());
            JPanel panelLeftSouth = new JPanel(new BorderLayout());
            DrawArea drawingArea = new DrawArea();
            panelLeft.add(drawingArea, BorderLayout.CENTER);
            panelLeftSouth.add(sliderSlices, BorderLayout.SOUTH);
            panelLeft.add(panelLeftSouth, BorderLayout.NORTH);
            setLocationRelativeTo(null);

            JButton btnTeach = new JButton("Teach");
            JButton btnSave = new JButton("Save");
            JButton btnEdit = new JButton("Edit");
            JButton btnLoad = new JButton("Load");
            JButton btnClear = new JButton("Clear");
            JButton btnShowLines = new JButton("Show Lines");
            btnClear.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    drawingArea.clear();
                }
            });

            btnSave.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        drawingArea.save();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });

            btnLoad.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        drawingArea.load();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });
            btnEdit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    drawingArea.edit();

                }
            });

            btnShowLines.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    drawingArea.showLines();
                }
            });

            add(panelRight, BorderLayout.EAST);
            add(panelLeft, BorderLayout.CENTER);
            panelRight.setLayout(new BoxLayout(panelRight, BoxLayout.LINE_AXIS));
            panelRight.add(btnTeach);
            panelRight.add(btnLoad);
            panelRight.add(btnEdit);
            panelRight.add(btnSave);
            panelRight.add(btnClear);
            panelLeft.add(btnShowLines, BorderLayout.SOUTH);
            sliderSlices.addChangeListener(new ChangeListener() {
                @Override
                public void stateChanged(ChangeEvent e) {
                    JSlider source = (JSlider) e.getSource();
                    drawingArea.setSlices(source.getValue());
                    drawingArea.repaint();

                    //System.out.println(slices);
                }
            });
            setVisible(true);
    }
    private void initialization(){
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            setLayout(new BorderLayout());
            Toolkit kit=Toolkit.getDefaultToolkit();
            Dimension d=kit.getScreenSize();
            setBounds(d.width/4,d.height/4,d.width/2,d.height/2);
    }
}
