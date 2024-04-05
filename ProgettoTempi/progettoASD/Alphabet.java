package progettoASD;

public class Alphabet {

	// contiene l'indice della posizione di ogni carattere nell'array ridotto
	private int[] alphabet;
	
	// lunghezza dell'array ridotto
	private int length;
	
	/**
	 * costruttore
	 */
	public Alphabet() {
		
		alphabet = new int[256];
		
	}
	
	
	/**
	 * Crea il collegamento fra l'indice di un carattere con il rispettivo indice nell'alfabeto 
	 * ridotto (alfabeto in cui non vengono tenuti in conto i caratteri non utilizzati)
	 * @param input
	 */
	public void setAlph(int[] input){
		
		int j=0;
		for(int i=0; i<256; i++){
			
			if(input[i] != 0){
				alphabet[i] = j;
				j++;
			}
			
		}
		
		length = j;
		
	}
	
	
	/**
	 * Crea l'array del numero di caratteri della parola passata utilizzando l'alfabeto ridotto
	 * @param word
	 * @return
	 */
	public int[] createArray(MyArrayList<Character> word){
		
		int[] ris = new int[length];
		
		for(int i=0; i<word.getLength(); i++){
			
			ris[alphabet[(int)word.getItem(i)]]++;
			
		}
		
		return ris;
		
	}

}
