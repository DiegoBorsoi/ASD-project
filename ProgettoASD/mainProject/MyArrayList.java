package mainProject;

public class MyArrayList<E> {
	
	/*
	 * Classe che permette di avere un array di oggetti di qualunque tipo.
	 * 
	 */

	// array contenente i vari elementi, ha lunghezza >= al numero di elementi salvati
	private Object[] data;
	
	private int length;
	
	/**
	 * costruttore
	 */
	public MyArrayList() {

		this.data = new Object[10];
		this.length = 0;

	}
	
	
	/**
	 * costruttore con possibilità di assegnare la lunghezza dell'array
	 * (permette una piu' veloce aggiunta di elementi se ne si conosce in partenza il numero)
	 * @param lung
	 */
	public MyArrayList(int lung){
		
		if(lung <= 0){
			
			throw new IllegalArgumentException("La lunghezza deve essere almeno 1.");
			
		}
		
		this.data = new Object[lung];
		this.length = 0;
		
	}
	
	
	/**
	 * Restituisce l'elemento alla posizione passata.
	 * Ha complessita' theta(1)
	 * @param pos
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public E getItem(int pos){
		
		if(pos >= length){
			throw new IndexOutOfBoundsException();
		}
		
		return (E)data[pos];
		
	}
	
	
	/**
	 * @return numero degli elementi presenti nell'array
	 */
	public int getLength(){
		
		return this.length;
		
	}
	
	
	/**
	 * Aggiunge l'elemento passasto all'array.
	 * Ha complessita' O grande(numero elementi presenti)
	 * @param elem
	 */
	public void add(E elem){
		
		if(data.length == this.length){		// l'array degli elementi e' pieno, devo allungarlo prima di aggiungere il nuovo valore
			
			data = raddoppiaLung(data);
			
		}
		
		this.data[this.length] = elem;
		length++;
		
	}
	
	
	/**
	 * Funzione interna alla classe. Viene chiamata durante l'inserimento se 
	 * la lunghezza dell'array fittizio non basta per l'inserimento di un nuovo elemento.
	 * Genera un nuovo array di lunghezza doppia al precedente con gli stessi elementi.
	 * @param d
	 * @return
	 */
	private static Object[]  raddoppiaLung(Object[] d){
		
		Object[] ris = new Object[d.length * 2];
		
		for(int i=0; i<d.length; i++){
			ris[i] = d[i];
		}
		
		return ris;
		
	}
	
	
	/**
	 * Permette di modificare il valore dell'elemento ad una specifica posizione.
	 * @param pos : indice dell'elemento da modificare
	 * @param elem : nuovo elemento da inserire
	 */
	public void setItem(int pos, E elem){
		
		if(pos >= length){
			throw new IndexOutOfBoundsException();
		}
		
		this.data[pos] = elem;
		
	}

}
