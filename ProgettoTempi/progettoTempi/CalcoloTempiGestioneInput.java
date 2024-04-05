package progettoTempi;

import java.io.ByteArrayInputStream;

import progettoASD.MyArrayList;

public class CalcoloTempiGestioneInput {

	public static double[] misurazione(int d, int c, double za, double tMin, double DeltaGreco){
		
		double t = 0;
		double sum2 = 0;
		int cn = 0;
		double delta = 0;
		double e = 0;
		
		do{
			
			for(int i=0; i<c; i++){
				
				double m = tempoMedioNetto(d, tMin);
				t = t + m;
				sum2 = sum2 + m*m;
				
			}
			
			cn = cn + c;
			
			e = t / cn;
			
			double s = Math.sqrt(sum2/cn - e*e);
			
			delta = (1/Math.sqrt(cn)) * za * s;
			
		}while(delta >= DeltaGreco);
		
		return new double[] {e,delta};
		
	}
	
	
	public static double tempoMedioNetto( int d , double tMin){
		
		int ripTara = calcolaRipTara( d, tMin);
		int ripLordo = calcolaRipLordo( d, tMin);

		
		long t0 = System.currentTimeMillis();
		
		for(int i=0; i<ripTara; i++){
			
			// eseguo la preparazione dell'input su un input di lunghezza d
			
			String s = createInput(d);
			
			ByteArrayInputStream bais = new ByteArrayInputStream(s.getBytes());
			System.setIn(bais);	        
			
		}
		
		long t1 = System.currentTimeMillis();
		
		long ttara = t1 - t0;
		
		t0 = System.currentTimeMillis();
		
		for(int i=0; i<ripLordo; i++){
			
			// eseguo la preparazione dell'input ed il programma ta misurare su un input di lunghezza d
			
			String s = createInput(d);
			
			ByteArrayInputStream bais = new ByteArrayInputStream(s.getBytes());
			System.setIn(bais);
			
			// Array delle parole da acquisire
			
			MyArrayList<MyArrayList<Character>> parole = new MyArrayList<MyArrayList<Character>>();
	        
			
			// Ottenimento e salvataggio parole, con creazione alfabeto delle presenze ( totalAlph )
			
			int[] totalAlph = progettoASD.ProgettoASD.acquisizioneInput(parole);
	        
			
	        // Ordinamento delle parole acquisite e cancellazione dei doppioni
	        
	        MyArrayList<MyArrayList<MyArrayList<Character>>> words = progettoASD.ProgettoASD.ordinamentoECancellazioneDoppioni( parole);
			
			
		}
		
		t1 = System.currentTimeMillis();
		
		long tlordo = t1 - t0;
		
		double tmedio = ((double)tlordo/ripLordo) - ((double)ttara/ripTara);
		
		return tmedio;
		
	}
	
	
	private static String createInput(int n){
		
		long num;
		StringBuilder s = new StringBuilder();
		
		RandomGenerator r = new RandomGenerator(14081996);
		
		// creo n caratteri nei quali lo spazio compare con una probabilita' di almeno 18%
		
		for(int i=0; i<n; i++){
			
			num = Math.round(r.get() * 99);
			
			if(num < 18){
				
				s.append((char)32);
				
			}else{
				
				num = Math.round(r.get() * 255);
				s.append((char)num);
				
			}
			
		}
		
		
		return s.toString();
		
	}
	
	private static String createInputMax40(int n){
		
		long num;
		StringBuilder s = new StringBuilder();
		
		RandomGenerator r = new RandomGenerator(14081996);
		
		// creo parole di lunghezza <= 40
		
		while(s.length() < n - 40){
			
			num = 1 + Math.round(r.get() * 39); // parole di lunghezza da 1 a 40
			
			for(int i=0; i<num; i++){
				
				char c = (char)(r.get()*255);
				
				if(c != ' '){
					s.append(c);
				}else{
					i++;
				}
				
			}
			
			s.append(' ');
		}
		
		while(s.length() < n){
			
			s.append((char)(r.get()*255));
			
		}
		
		
		return s.toString();
		
	}
	
	
	private static int calcolaRipTara(int d, double tMin){
		
		long t0 = 0,t1 = 0;
		int rip = 1;
		
		while((t1 - t0) <= tMin){
			
			rip *= 2;
			t0 = System.currentTimeMillis();
			
			for(int i=0; i<rip; i++){
				
				// preparo un input di lunghezza d
				
				String s = createInput(d);
				
				ByteArrayInputStream bais = new ByteArrayInputStream(s.getBytes());
				System.setIn(bais);
				
			}
			
			t1 = System.currentTimeMillis();
			
		}
		
		// ricerca esatta del numero di ripetizioni per bisezione
		// approssiamiamo a 5 cicli
		
		int max = rip;
		int min = (int) (rip / 2);
		int cicliErrati = 5;
		
		while((max - min) >= cicliErrati){
			
			rip = (int)((max + min) / 2);
			
			t0 = System.currentTimeMillis();
			
			for(int i=0; i < rip; i++){
				
				// preparo un input di lunghezza d
				
				String s = createInput(d);
				
				ByteArrayInputStream bais = new ByteArrayInputStream(s.getBytes());
				System.setIn(bais);
				
			}
			
			t1 = System.currentTimeMillis();
			
			if((t1 - t0) <= tMin){
				
				min = rip;
				
			}else{
				
				max = rip;
				
			}
			
		}
		
		return max;
		
	}
	
	
	private static int calcolaRipLordo(int d, double tMin){
		
		long t0 = 0,t1 = 0;
		int rip = 1;
		
		while((t1 - t0) <= tMin){
			
			rip *= 2;
			t0 = System.currentTimeMillis();
			
			for(int i=0; i<rip; i++){
				
				// eseguo preparazione dell'input ed il programma su un input di lunghezza d
				
				String s = createInput(d);
				
				ByteArrayInputStream bais = new ByteArrayInputStream(s.getBytes());
				System.setIn(bais);
				
				// Array delle parole da acquisire
				
				MyArrayList<MyArrayList<Character>> parole = new MyArrayList<MyArrayList<Character>>();
		        
				
				// Ottenimento e salvataggio parole, con creazione alfabeto delle presenze ( totalAlph )
				
				int[] totalAlph = progettoASD.ProgettoASD.acquisizioneInput(parole);
		        
				
		        // Ordinamento delle parole acquisite e cancellazione dei doppioni
		        
		        MyArrayList<MyArrayList<MyArrayList<Character>>> words = progettoASD.ProgettoASD.ordinamentoECancellazioneDoppioni( parole);
				
			}
			
			t1 = System.currentTimeMillis();
			
		}
		
		// ricerca esatta del numero di ripetizioni per bisezione
		// approssiamiamo a 5 cicli
		
		int max = rip;
		int min = (int) (rip / 2);
		int cicliErrati = 5;
		
		while((max - min) >= cicliErrati){
			
			rip = (int)((max + min) / 2);
			
			t0 = System.currentTimeMillis();
			
			for(int i=0; i < rip; i++){
				
				// eseguo preparazione dell'input ed il programma su un input di lunghezza d
				
				String s = createInput(d);
				
				ByteArrayInputStream bais = new ByteArrayInputStream(s.getBytes());
				System.setIn(bais);
				
				// Array delle parole da acquisire
				
				MyArrayList<MyArrayList<Character>> parole = new MyArrayList<MyArrayList<Character>>();
		        
				
				// Ottenimento e salvataggio parole, con creazione alfabeto delle presenze ( totalAlph )
				
				int[] totalAlph = progettoASD.ProgettoASD.acquisizioneInput(parole);
		        
				
		        // Ordinamento delle parole acquisite e cancellazione dei doppioni
		        
		        MyArrayList<MyArrayList<MyArrayList<Character>>> words = progettoASD.ProgettoASD.ordinamentoECancellazioneDoppioni( parole);
				
			}
			
			t1 = System.currentTimeMillis();
			
			if((t1 - t0) <= tMin){
				
				min = rip;
				
			}else{
				
				max = rip;
				
			}
			
		}
		
		return max;
		
	}

}
