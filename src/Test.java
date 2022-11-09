import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class Test extends JFrame{

	MojKomponent komponent;
	Siec siec;
	Integer slices = 8;
	DrawArea drawingArea;

	public class MojKomponent extends JComponent{

		@Override
		protected void paintComponent(Graphics g) {
			double [] wej=new double[2];
			double [] wynik;
			for(int x=0;x<getWidth();x++)
				for(int y=0;y<getHeight();y++){
					wej[0]=2.0*(x-getWidth()/2)/getWidth();
					wej[1]=2.0*(y-getHeight()/2)/getHeight();
					wynik=siec.oblicz_wyjscie(wej);
					
					/*
					g.setColor(new Color((wynik[0]<0.5)?0:(int)Math.round((wynik[0]-0.5)*510),
							             (wynik[0]>0.5)?0:(int)Math.round((0.5-wynik[0])*510),
							            	 255));
					  */          	 
					g.setColor((wynik[0]>0.5)?Color.RED:Color.GREEN);
					g.fillRect(x, y, 1, 1);	
				}
			super.paintComponent(g);
		}
		
	}
	public Test(String string) {
		super(string);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		Toolkit kit=Toolkit.getDefaultToolkit();
		Dimension d=kit.getScreenSize();
		setBounds(d.width/4,d.height/4,d.width/2,d.height/2);
		JSlider sliderSlices = new JSlider(JSlider.HORIZONTAL, 8,64, 8);

		JPanel panelRight = new JPanel();
		JPanel panelLeft = new JPanel(new BorderLayout());
		JPanel panelDrawing = new JPanel(new BorderLayout());
		panelDrawing.setBounds(0, 0, 100, 100);
		//drawingArea = new DrawArea();
		drawingArea = new DrawArea();
		drawingArea.setBounds(0,0,200,200);

		panelDrawing.setBorder(new EmptyBorder(100, 100, 100, 100));
		panelDrawing.add(drawingArea, BorderLayout.CENTER);
		panelLeft.add(panelDrawing, BorderLayout.CENTER);
		panelLeft.add(sliderSlices, BorderLayout.SOUTH);

		JButton btnTeach = new JButton("Teach");
		JButton btnSave = new JButton("Save");
		JButton btnEdit = new JButton("Edit");
		JButton btnLoad = new JButton("Load");
		JButton btnClear = new JButton("Clear");
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

		add(panelRight, BorderLayout.EAST);
		add(panelLeft, BorderLayout.CENTER);
		panelRight.setLayout(new BoxLayout(panelRight, BoxLayout.LINE_AXIS));
		panelRight.add(btnTeach);
		panelRight.add(btnLoad);
		panelRight.add(btnEdit);
		panelRight.add(btnSave);
		panelRight.add(btnClear);
		sliderSlices.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider) e.getSource();
				slices = source.getValue();
				drawingArea.repaint();

				System.out.println(slices);
			}
		});

		/*
		 //1 warstwa 1 neuron
		int []tab=new int [1];
		tab[0]=1;
		siec=new Siec(2,1,tab);
		*/
		
		 //3 warstwy
		
		int [] tab=new int [3];
		tab[0]=25; tab[1]=5; tab[2]=1;
		siec=new Siec(2,3,tab);
		
		/*
		 //2 warstwy
		int [] tab=new int [2];
		tab[0]=10; tab[1]=1;
		siec=new Siec(2,2,tab);
		*/
		setVisible(true);
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				new Test("neurony");
			}
		});
	}

}
