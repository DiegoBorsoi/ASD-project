package progettoTempi;

//
//Classe che genera numeri casuali,
//migliore del random di sistema
//
public class RandomGenerator {
	//
	// get(): restituisce un numero compreso tra 0 e 1 (e aggiorna il seme)
	//
	public double get()
	{
	   //
	   // Costanti
	   //
	   final int a = 16807;
	   final int m = 2147483647;
	   final int q = 127773;
	   final int r = 2836;
	
	   //
	   // Variabili
	   //
	   double lo, hi, test;
	
	   //hi = Math.ceil(seed / q); <-- questo non va bene perche' bisogna eseguire un "troncamento" e non un "soffitto"
	   hi = (int)(seed / q);
	   
	   lo = seed - q * hi;
	   test = a * lo - r * hi;
	   if (test < 0.0) {
	      seed = test + m;
	   } else {
	      seed = test;
	   }
	   return seed / m;
	}
	
	//
	// setSeed(s): imposta il valore del seme a s
	//
	public void setSeed(double s)
	{
		seed = s;
	}
	
	//
	// costruttore della classe, genera un'istanza di RandomGenerator,
	// fissando il seme iniziale a s.
	//
	public RandomGenerator(double s)
	{
		seed = s;
	}
	
	//
	// variabile che tiene memorizzato il seme
	//
	private double seed;

	//
	// restituisce il valore del seme
	//
	public double getSeed()
	{
		return seed;
	}
	
	

}
