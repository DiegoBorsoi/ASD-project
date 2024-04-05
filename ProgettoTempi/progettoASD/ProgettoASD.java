package progettoASD;

import java.io.IOException;

/**
 * @author Diego Borsoi
 *
 */
public class ProgettoASD {

	public static void main(String[] args) {				
		
		// Array delle parole da acquisire
		
		MyArrayList<MyArrayList<Character>> parole = new MyArrayList<MyArrayList<Character>>();
        
		
		// Ottenimento e salvataggio parole, con creazione alfabeto delle presenze ( totalAlph )
		
		int[] totalAlph = acquisizioneInput(parole);
        
		
        // Ordinamento delle parole acquisite e cancellazione dei doppioni
        
        MyArrayList<MyArrayList<MyArrayList<Character>>> words = ordinamentoECancellazioneDoppioni( parole);
        
        
        
        
        //Creazione grafo
        
        Graph grafo = creaGrafoEdArchi( words, totalAlph);
        
        
        
        
        // Ricerca cammino piu' lungo
        
        int longestPath = getLongestPath(grafo);
        
        
        
        
        // Stampa lunghezza cammino piu' lungo

        System.out.println(longestPath);
        
        
        // Stampa del grafo in formato Dot
        
        stampaGrafoDot(words, grafo);
        
	}
	
	
	/**
	 * Acquisizione dell'input e creazione delle parole ( sequenza di caratteri diversi da ' '(32 in ASCII) )
	 * MODIFICA parole.
	 * @param parole : array utilizzato per l'inserimento delle parole
	 * @return alfabeto delle presenze : se la casella alla posizione i vale 1 allora il carattere i e' presente nell'input, altrimenti no
	 */
	public static int[] acquisizioneInput( MyArrayList<MyArrayList<Character>> parole){
		
		// Ottenimento e salvataggio parole, con creazione alfabeto delle presenze ( totalAlph )
		
		int ch;
		
		// alfabeto delle presenze : se la casella alla posizione i vale 1 allora il carattere i e' presente nell'input, altrimenti no
		int[] totalAlph = new int[256];
		
        try {
        	
        	boolean started = false;
        	
        	MyArrayList<Character> word = new MyArrayList<Character>();
        	
			while ((ch = System.in.read()) != -1)
			{
				
				
				
			    if((ch != 32)){	// 32 = ' '
			    	
			    	word.add(new Character((char)ch));
			    	
			    	started = true;
			    	
			    	totalAlph[ch] = 1;
			    	
			    }else{
			    	
			    	if(started){	//fine della parola
			    		
			    		started = false;
			    		
			    		parole.add(word);
			    		
			    		word = new MyArrayList<Character>();
			    		
			    	}			    	
			    	
			    }
			}
			
			if(started){	//fine della parola
	    		
	    		started = false;
	    		
	    		parole.add(word);
	    		
	    		word = new MyArrayList<Character>();
	    		
	    	}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
        
        return totalAlph;
		
	}
	
	/**
	 * Ordina le parole passate per lunghezza, inserendole in un array di gruppi di parole.
	 * Successivamente all'interno di ciascun gruppo le ordina lessicograficamente.
	 * Infine elimina i doppioni eseguendo una scansione di tutte le parole.
	 * @param parole : array di parole
	 * @return un array di gruppi di parole
	 */
	public static MyArrayList<MyArrayList<MyArrayList<Character>>> ordinamentoECancellazioneDoppioni(MyArrayList<MyArrayList<Character>> parole){
		
		//Creazione array delle posizioni per ordinamento
        
        int[][] startingPos = new int[parole.getLength()][2];
        
        for(int i=0; i<startingPos.length;i++){
        	startingPos[i][0] = i;
        	startingPos[i][1] = parole.getItem(i).getLength();
        }
        
        int[][] pos = new int[parole.getLength()][2];
        
        int maxLength = 0;
        
        for(int i=0; i<parole.getLength(); i++){
        	
        	int wLung = parole.getItem(i).getLength();
        	
        	if(wLung > maxLength){
        		maxLength = wLung;
        	}
        	
        }
        
        
        
        countingSort(startingPos,pos,maxLength);		// ordinamento in base alla lunghezza delle parole
        												// nell'array pos[][0] e' contenuto l'ordine degli indici delle parole ordinate secondo la grandezza
        
        
        
        //raggruppamento delle parole per grandezza
        
        MyArrayList<MyArrayList<MyArrayList<Character>>> words = new MyArrayList<MyArrayList<MyArrayList<Character>>>();
        
        int tempL = 0;
        int count = -1;
        
        for(int i=0; i<parole.getLength(); i++){
        	
        	MyArrayList<Character> w = parole.getItem(pos[i][0]);
        	
        	if(tempL != w.getLength()){
        		
        		count++;
        		
        		tempL = w.getLength();
        		
        		words.add(new MyArrayList<MyArrayList<Character>>());
        		words.getItem(count).add(w);
        		
        		
        	}else{
        		
        		words.getItem(count).add(w);
        		
        	}
        	
        }
        
        
        for(int i=0; i<words.getLength(); i++){
        			
        	// ordinamento dei gruppi di parole di lunghezza uguale lessicograficamente
        	// per successiva eliminazione dei doppioni
        	words.setItem(i, radixSort(words.getItem(i)));
        	
        }
        
        
        
        //Eliminazione dei doppioni
        
        for(int i=0; i<words.getLength(); i++){
        	
        	MyArrayList<MyArrayList<Character>> temp = new MyArrayList<MyArrayList<Character>>(words.getItem(i).getLength());
        	temp.add(words.getItem(i).getItem(0));
        	int indice = 0;
        	
        	for(int j=0; j<words.getItem(i).getLength()-1; j++){
        		
        		if(compareCharArray( temp.getItem(indice), words.getItem(i).getItem(j+1)) != 0){
        			
        			temp.add(words.getItem(i).getItem(j+1));
        			indice++;
        			
        		}
        		
        	}
        	
        	words.setItem(i, temp);
        	
        }
        
        return words;
		
	}
	
	/**
	 * Crea un grafo orientato che usa come nodi le parole passate
	 * e crea gli archi secondo la regola del progetto(guardare isRelated()) creando l'alfabeto ridotto da quello passato
	 * @param words : array di parole raggruppate per lunghezza
	 * @param totalAlph : alfabeto delle presenze di tutti i caratteri possibili 
	 * @return
	 */
	public static Graph creaGrafoEdArchi( MyArrayList<MyArrayList<MyArrayList<Character>>> words, int[] totalAlph){
		
		// Creazione oggetto alfabeto
        Alphabet alph = new Alphabet();
        alph.setAlph(totalAlph);
		
        
        // Creazione oggeto grafo
		Graph grafo = new Graph();
        
        
        //Creazione nodi e l'array dei caratteri per ogni parola
        
        int nParola = 0;
        
        MyArrayList<MyArrayList<int[]>> contChars = new MyArrayList<MyArrayList<int[]>>();
        
        for(int i=0; i<words.getLength(); i++){
        	
        	contChars.add(new MyArrayList<int[]>());
        	
        	for(int j=0; j<words.getItem(i).getLength(); j++){
        		
        		contChars.getItem(i).add(alph.createArray(words.getItem(i).getItem(j)));
        		
        		grafo.addNode(nParola);
        		nParola++;
        		
        	}
        }
        
        nParola--;
        
        
        // creazione archi
        
        for(int i=words.getLength()-1; i>0; i--){
        	
        	for(int j=words.getItem(i).getLength()-1; j>=0; j--){
        		
        		int[] first = contChars.getItem(i).getItem(j);
        		
        		int nParola2 = 0;
        		
        		for(int m=0; m<i; m++){
        			
        			for(int n=0; n<words.getItem(m).getLength(); n++){
        				
        				int[] second = contChars.getItem(m).getItem(n);
        				
        				if(isRelated( first, second)){
        					
        					grafo.addEdge(nParola, nParola2);
        					
        				}
        				
        				nParola2++;
        			}
        		}
        		
        		nParola--;
        	}
        }
		
        
        return grafo;
        
	}
	
		
	/**
	 * Restituisce il percorso piu' lungo possibile fra due nodi del grafo dato
	 * @param g : grafo orientato e aciclico
	 * @return
	 */
	public static int getLongestPath(Graph g){
		
		int[] color = new int[g.getNodeLength()];	// 0 = bianco, 1 = grigio, 2 = nero
		int[] lung = new int[g.getNodeLength()];
		
		for(int i=0; i<color.length; i++){
			
			color[i] = 0;
			lung[i] = 0;
			
		}
		
		int max = 0;
		
		for(int i=color.length-1; i>=0; i--){
			
			if(color[i] == 0){
				
				lung[i] = dfsVisit( g, i, lung, color);
				
				if(lung[i] > max){
					
					max = lung[i];
					
				}
				
				
			}
			
		}
		
		return max;
		
	}
	
	
	/**
	 * Stampa su standard ouput in formato dot il grafo passato.
	 * I nodi del grafo hanno come etichetta il numero della parola da stampare.
	 * @param words
	 * @param grafo
	 */
	public static void stampaGrafoDot( MyArrayList<MyArrayList<MyArrayList<Character>>> words, Graph grafo){
		
		// Stampa in output del grafo
        
        System.out.println("digraph G_T {");
        
        
        // Stampa nodi con relativi indici
        
        int position = 0;
        
        for(int i=0; i<words.getLength(); i++){
        	
        	for(int j=0; j<words.getItem(i).getLength(); j++){
        		
        		System.out.print(position + " [label=\"");
        		
        		for(int m=0; m<words.getItem(i).getItem(j).getLength(); m++){
        			
        			char c = words.getItem(i).getItem(j).getItem(m);
        			
        			if(c == 10){					// 10 = '\n' : new feed(line)
        				System.out.print("\\n");
        			}else if(c == 13){				// 13 = '\r' : carriage return
        				System.out.print("\\r");
        			}else{
        				System.out.write(c);
        			}
        			
        		}
        		
        		System.out.println("\"];");
        		
        		position++;
        		
        	}
        	
        }
        
        
        // Stampa archi
        
        position = 0;
        
        for(int i=0; i<words.getLength(); i++){

        	for(int j=0; j<words.getItem(i).getLength(); j++){

        		for(int m=0; m<grafo.getAdj(position).getLength(); m++){
        			
        			System.out.print(position + " -> " + grafo.getAdj(position).getItem(m) + ";\n");
        			
        		}
        		
        		position++;

        	}

        }
        
        
        System.out.println("}");
		
	}
	
	
	/**
	 * Ordina le parole contenute in data, secondo l'ordine lessicografico. Utilizza counting sort.
	 * @param data
	 * @return l'array di parole ordinato
	 */
	private static MyArrayList<MyArrayList<Character>> radixSort(MyArrayList<MyArrayList<Character>> data){

		for(int i=data.getItem(0).getLength()-1; i>=0; i--){
			
			int max = 0;
			
			int[][] startPos = new int[data.getLength()][2];
			int[][] endPos = new int[data.getLength()][2];
			
			for(int j=0; j<data.getLength(); j++){
				
				int c = (int)data.getItem(j).getItem(i);
				
				startPos[j][0] = j;
				startPos[j][1] = c;
				
				if( c > max){
					
					max = c;
					
				}
				
			}
			
			countingSort( startPos, endPos, max);
			
			//riordino le parole secondo l'ordinamento ottenuto
			MyArrayList<MyArrayList<Character>> temp = new MyArrayList<MyArrayList<Character>>(data.getLength());
			
			for(int j=0; j<data.getLength(); j++){
				
				temp.add(data.getItem(endPos[j][0]));
				
			}
			
			data = temp;
			
		}
		
		return data;
		
	}
	
	
	/**
	 * Ordina la riga 0 della matrice input (cioe' l'array input[][0]) inserendo il risultato nella matrice ris. 
	 * Al momento della copiatura su ris viene copiata anche la casella corrispondente di input[][1].
	 * MODIFICA ris.
	 * @param input : matrice a due righe: la prima contiene i valori da ordinare, la seconda contiene la posizione iniziale.
	 * @param ris : matrice a due righe : la prima contiene i valori ordinati, la seconda le nuove posizioni.
	 * @param max : valore massimo dei valori da ordinare
	 */
	private static void countingSort(int[][] input, int[][] ris, int max){
		
		int[] temp = new int[max+1];
		
		for(int i=0; i<max+1; i++){
			
			temp[i] = 0;
			
		}
		
		for(int i=0; i<input.length; i++){
			
			temp[input[i][1]]++;
			
		}
		
		for(int i=1; i<max+1; i++){
			
			temp[i] += temp[i-1];
			
		}
		
		for(int i=input.length-1; i>=0; i--){
			
			ris[temp[input[i][1]]-1] = input[i];
			
			temp[input[i][1]]--;
			
		}
		
	}
	
	
	/**
	 * Confronta due MyArrayList di Character della stessa lunghezza 
	 * e ritorna un integer indicante chi dei due precede l'altro secondo l'ordine lessicografico (utilizzando la tabella ASCII estesa). 
	 * @param first 
	 * @param second
	 * @return 0 se i due array rappresentano la medesima lista di Characters, un numero negativo se first precede second ed un numero positivo altrimenti.
	 */
	private static int compareCharArray(MyArrayList<Character> first, MyArrayList<Character> second){

		for(int i=0; i<first.getLength(); i++){
			
			if((int)(first.getItem(i)) != (int)(second.getItem(i))){
				return first.getItem(i) - second.getItem(i);
			}
			
		}
		return 0;
	}
	
	
	/**
	 * Confronta due array di int restituendo se rispettano e meno la proprieta' del progetto: 
	 * 		per essere vero il primo array deve avere ogni casella con un valore >= a quello della rispettiva del secondo 
	 * 		ed avere almeno una casella con valore > a quello della rispettiva del secondo array
	 * @param first
	 * @param second
	 * @return
	 */
	private static boolean isRelated( int[] first, int[] second){
		
		boolean ris = false;
		
		for(int i=0; i<first.length; i++){
			
			if(first[i] < second[i]){
				
				return false;
				
			}else if(first[i] > second[i]){
				
				ris = true;
				
			}
			
		}
		
		return ris;
		
	}
	
	
	/**
	 * Esegue una visita dfs (DeepFirstSearch) che permette di scandire ogni nodo del grafo raggiungibile dal nodo dato.
	 * Durante la visita aggiorna il colore del nodo e la lunghezza del piu' lungo cammino percorribile da esso.
	 * @param g
	 * @param node
	 * @param lung
	 * @param color
	 * @return
	 */
	private static int dfsVisit( Graph g, int node, int[] lung, int[] color){
		
		color[node] = 1;
		
		for(int i=0; i<g.getAdj(node).getLength(); i++){
			
			int temp = 0;
			
			if(color[g.getAdj(node).getItem(i)] == 0){
				
				temp = 1 + dfsVisit( g, g.getAdj(node).getItem(i), lung, color);
				
			}else{
				
				temp = 1 + lung[g.getAdj(node).getItem(i)];
				
			}
			
			if(temp > lung[node]){

				lung[node] = temp;

			}

		}
		
		color[node] = 2;
		
		
		return lung[node];
		
	}
	
}
