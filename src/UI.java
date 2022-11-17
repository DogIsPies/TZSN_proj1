import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Arrays;


public class UI extends JFrame {
    Siec siec;
        public UI(String string) {
            super(string);
            initialization();

            JSlider sliderSlices = new JSlider(JSlider.HORIZONTAL, 8,64, 8);

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
            int [] tab=new int [3];
            tab[0]=30; tab[1]=10; tab[2]=3;
            siec=new Siec(64,3, tab);
            JButton btnTeach = new JButton("Teach");
            btnTeach.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    double[] wynik;



                    System.out.println("wow");
                    wynik=siec.oblicz_wyjscie(drawingArea.array0t);
                    System.out.println(Arrays.toString(wynik));
                }
            });
            JButton btnSave0 = new JButton("T");
            JButton btnSave1 = new JButton("I");
            JButton btnSave2 = new JButton("P");
            JButton btnEdit = new JButton("Edit");
            JButton btnLoad = new JButton("Load");
            JButton btnClear = new JButton("Clear");
            JButton btnShowLines = new JButton("Show Lines");
            JButton btnColorBlack = new JButton();
            JButton btnColorWhite = new JButton();
            btnColorWhite.setBackground(Color.WHITE);
            btnColorBlack.setBackground(Color.BLACK);
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
            btnColorBlack.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    drawingArea.setDrawColor(Color.BLACK);
                }
            });

            btnColorWhite.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    drawingArea.setDrawColor(Color.WHITE);
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
            panelRightDrawing.add(btnColorBlack);
            panelRightDrawing.add(btnColorWhite);
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
