package progettoTempi;

import java.io.*;

public class Main {

	public static void main(String[] args)
	{
		
		int start = 1000;
		int max = 70000;
		int passo = 1000;
		
		int num = start;
		while(num <= max){
			
			
			long gran = granularita();
			
			double tMin = gran / 0.05;
			
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			System.setOut(new PrintStream(baos));

			// calcolo tempi programma totale
			
			double tsing = CalcoloTempiTotale.tempoMedioNetto(num, tMin);
			
			double[] ris = CalcoloTempiTotale.misurazione(num , 5, 1.96, tMin, tsing/10);
			
			//**********************
			
			
			// calcolo tempi gestione dell'input
			
			//double tsing = CalcoloTempiGestioneInput.tempoMedioNetto(num, tMin);

			//double[] ris = CalcoloTempiGestioneInput.misurazione(num , 5, 1.96, tMin, tsing/10);
			
			//**********************
			
			
			// calcolo tempi creazione del grafo
			
			//double tsing = CalcoloTempiCreazioneGrafo.tempoMedioNetto(num, tMin);

			//double[] ris = CalcoloTempiCreazioneGrafo.misurazione(num , 5, 1.96, tMin, tsing/10);
			
			//**********************
			
			System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
			
			System.out.println(num + "\t" + ris[0] + "\t" + ris[1]);
			
			
			num += passo;
			
			
		}
		
		
	}
	
	private static long granularita(){
		
		long t0 = System.currentTimeMillis();
		long t1 = System.currentTimeMillis();
		
		while(t1 == t0){
			
			t1 = System.currentTimeMillis();
			
		}
		
		return (t1-t0);
		
	}

}
