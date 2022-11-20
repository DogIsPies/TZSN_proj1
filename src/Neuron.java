import java.util.Random;

//banana
public class Neuron {
	public double [] wagi;
	int liczba_wejsc;

	public double last_output, error;

	public Neuron(){
		liczba_wejsc=0;
		wagi=null;
	}
	public Neuron(int liczba_wejsc){
		this.liczba_wejsc=liczba_wejsc;
		wagi=new double[liczba_wejsc+1];
		generuj();
	}
	private void generuj() {
		Random r=new Random();
		System.out.println(liczba_wejsc);
		for(int i=0;i<=liczba_wejsc;i++){

			//wagi[i]=(r.nextDouble()-0.5)*2.0*10;//do ogladania
			wagi[i]=(r.nextDouble()-0.5)*2.0*0.01;//do projektu

		}

	}
	public double oblicz_wyjscie(double [] wejscia){
		double fi=wagi[0];
		//double fi=0.0;
		for(int i=1;i<=liczba_wejsc;i++)
			fi+=wagi[i]*wejscia[i-1];
		last_output=1.0/(1.0+Math.exp(-fi));// funkcja aktywacji sigma -unip
		//pochodna funkcji sigmoidalnej unipolarnej y'=y(y-1)
		//double wynik=(fi>0.0)?1.0:0.0;//skok jednostkowy
		//double wynik=fi; //f.a. liniowa

		return last_output;
	}


	public void oblicz_blad(double v) {
		error = v-last_output;
	}

	public  void update_weights(){
		for(int i=0;i<=liczba_wejsc;i++){
			wagi[i] = wagi[i] +0.1*error*(last_output*(last_output-1)); //new weight = weight + skok * blad * y'=y(y-1);
		}
	}
}

