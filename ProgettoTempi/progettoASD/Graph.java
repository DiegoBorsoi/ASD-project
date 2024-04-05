package progettoASD;

public class Graph {

	private MyArrayList<Integer> nodes;
	private MyArrayList<MyArrayList<Integer>> adj;
	
	/**
	 * costruttore
	 */
	public Graph() {
		
		nodes = new MyArrayList<Integer>();
		adj = new MyArrayList<MyArrayList<Integer>>();
		
	}

	
	/**
	 * Aggiunge un nodo al grafo, creando anche la rispettiva lista di adiacenza
	 * @param indice
	 */
	public void addNode(int indice){
		
		nodes.add(indice);
		adj.add(new MyArrayList<Integer>());
		
	}
	
	
	/**
	 * Aggiunge un arco orientato al grafo
	 * @param startingNode
	 * @param endingNode
	 */
	public void addEdge(int startingNode, int endingNode){
		
		try{
			
			adj.getItem(startingNode).add(endingNode);
			
		}catch(IndexOutOfBoundsException e){
			throw new IndexOutOfBoundsException("Nodo non presente nel grafo");
		}
		
	}
	
	
	/**
	 * @param node
	 * @return la lista di adiacenza del nodo passato, se presente
	 */
	public MyArrayList<Integer> getAdj(int node){
		
		try{
			
			return this.adj.getItem(node);
			
		}catch(IndexOutOfBoundsException e){
			throw new IndexOutOfBoundsException("Nodo non presente nel grafo");
		}
		
	}
	
	
	/**
	 * @return il numero di nodi presenti nel grafo
	 */
	public int getNodeLength(){
		
		return this.nodes.getLength();
		
	}
	
}
