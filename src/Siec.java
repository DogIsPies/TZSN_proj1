
public class Siec {
	Warstwa [] warstwy;
	int liczba_warstw;

	double error;
	
	public Siec(){
		warstwy=null;
		this.liczba_warstw=0;
	}
	public Siec(int liczba_wejsc,int liczba_warstw,int [] liczba_neuronow_w_wastwie){
		this.liczba_warstw=liczba_warstw;
		warstwy=new Warstwa[liczba_warstw];
		for(int i=0;i<liczba_warstw;i++)
			warstwy[i]=new Warstwa((i==0)?liczba_wejsc:liczba_neuronow_w_wastwie[i-1],liczba_neuronow_w_wastwie[i]);
	}
	double [] oblicz_wyjscie(double [] wejscia){
		double [] wyjscie=null;
		for(int i=0;i<liczba_warstw;i++)
			wejscia = wyjscie = warstwy[i].oblicz_wyjscie(wejscia);
		return wyjscie;
	}

	public void backprop(double[] wynik, double[] wzorzec) {
		lastLayerError(wzorzec);
		for (int i = liczba_warstw-2; i<=0; i++){
			warstwy[i].oblicz_blad();
		}

	}
	public void lastLayerError(double[] wzorzec){
		for (int i=0; i<wzorzec.length; i++){
			warstwy[liczba_warstw-1].oblicz_blad(wzorzec);
		}
	}


}
