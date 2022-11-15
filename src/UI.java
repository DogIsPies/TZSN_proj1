import javax.swing.*;
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

            JSlider sliderSlices = new JSlider(JSlider.HORIZONTAL, 8,16, 8);

            JPanel panelRight = new JPanel();
            JPanel panelRightSaveBar = new JPanel();
            JPanel panelRightDrawing = new JPanel();
            panelRightSaveBar.setLayout(new BoxLayout(panelRightSaveBar, BoxLayout.X_AXIS));
            JPanel panelLeft = new JPanel(new BorderLayout());
            JPanel panelLeftSouth = new JPanel(new BorderLayout());
            DrawArea drawingArea = new DrawArea();
            panelLeft.add(drawingArea, BorderLayout.CENTER);
            panelLeftSouth.add(sliderSlices, BorderLayout.SOUTH);
            panelLeft.add(panelLeftSouth, BorderLayout.NORTH);
            setLocationRelativeTo(null);

            JButton btnTeach = new JButton("Teach");
            JButton btnSave0 = new JButton("T");
            JButton btnSave1 = new JButton("I");
            JButton btnSave2 = new JButton("P");
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

            btnSave0.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        drawingArea.save(0);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });
            btnSave1.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        drawingArea.save(1);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });
            btnSave2.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        drawingArea.save(2);
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
            panelRightDrawing.add(btnTeach);
            panelRightDrawing.add(btnLoad);
            panelRightDrawing.add(btnEdit);
            panelRightDrawing.add(btnClear);
            panelRight.add(panelRightSaveBar);
            panelRight.add(panelRightDrawing);
            panelRightSaveBar.add(btnSave0);
            panelRightSaveBar.add(btnSave1);
            panelRightSaveBar.add(btnSave2);
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
