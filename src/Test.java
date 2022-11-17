import java.awt.*;
import java.util.Arrays;
import javax.swing.*;


public class Test{

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
//					wej[0]=2.0*(x-getWidth()/2)/getWidth();
//					wej[1]=2.0*(y-getHeight()/2)/getHeight();
					wynik=siec.oblicz_wyjscie(drawingArea.array0t);

					
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
		new UI(string);


		/*
		 //1 warstwa 1 neuron
		int []tab=new int [1];
		tab[0]=1;
		siec=new Siec(2,1,tab);
		*/
		
		 //3 warstwy
		
//		int [] tab=new int [3];
//		tab[0]=25; tab[1]=5; tab[2]=1;
//		siec=new Siec(slices,3,drawingArea.array0t);
		
		/*
		 //2 warstwy
		int [] tab=new int [2];
		tab[0]=10; tab[1]=1;
		siec=new Siec(2,2,tab);
		*/

	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				new Test("TIP");
			}
		});
	}

}
