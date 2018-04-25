import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Dijkstra extends Grafo {
	
	/**
	 * Atributo para n�s visitados.
	 */
	private boolean nosVisitados[]; 
	
	/**
	 * Atributos para conter as distancias de cada n�.
	 */
	private int distancias[];
	
	/**
	 * Distancia infinita.
	 */
	public final int OO = 99999; 
	
	/**
	 * Fila de prioridade.
	 */
	private List filaDePrioridade = new ArrayList<Integer>(); 
	
	/**
	 * Representa��o do grafo em texto.
	 */
	private Map<Integer, String> representacaoTexto = new HashMap<Integer, String>();
    {
    	representacaoTexto.put(0, "A");
    	representacaoTexto.put(1, "B");
    	representacaoTexto.put(2, "C");
    	representacaoTexto.put(3, "D");
    	representacaoTexto.put(4, "E");
    	representacaoTexto.put(5, "F");
    	representacaoTexto.put(6, "I");
    	representacaoTexto.put(7, "T");
    };

	/**
	 * Construtor padr�o que informa a quantidade de vertices.
	 * @param qtdVertices
	 * 		  Quantidade de vertices contidos no grafo.
	 * @throws Exception
	 */
	public Dijkstra(int qtdVertices) throws Exception {
		
		// Faz as consistencias da super classe.
		super(qtdVertices);
		
		// Define o tamanho dos n�s que devem ser visitados.
		nosVisitados = new boolean[qtdVertices];
		
		// Define o tamanho das distancias.
		distancias = new int[qtdVertices];
	}
	
	/**
	 * M�todo respons�vel por encontrar o menor caminho.
	 * @param origem
	 * @param destino
	 * @throws Exception
	 */
	public void menorCaminhoEncontrar(int origem, int destino) throws Exception {
		
		// Se origem for zero.
		if (origem < 0) {
			throw new Exception("Origem n�o pode ser negativa");
		}
		
		// Inicia a busca pelo menor caminho.
		iniciaMenorCaminho(origem);
		
		// Extrai o menor caminho.
		extrairMenorCaminho();
		
		System.out.println(distancias[destino]);
	}
	
	/**
	 * Inicia a busca pelo menor caminho.
	 * Onde: 
	 * 1 - Define-se a distancia de todos os n�s como uma distancia infinita.
	 * 2 - Define a distancia da origem como ZERO.
	 * @param origem
	 */
	private void iniciaMenorCaminho(int origem) {
		for(int i = 0 ; i < getQtdVertice(); i++){ 
			distancias[i] = OO; 
			nosVisitados[i] = false; 
			filaDePrioridade.add(new Integer(i));//adiciona a aresta na fila 
		} 
		distancias[origem] = 0;//inicia o vetor de distancias 
	}
	
	/**
	 * Extrai o menor caminho a ser seguido.
	 */
	private void extrairMenorCaminho() {		
		
		// Percorre a lista de prioridade.
		while(!filaDePrioridade.isEmpty()){             
            
			int menorValor = OO;    
	        int verticeDeMenorPeso=0; 
	         
	        Iterator<Integer>it = filaDePrioridade.iterator(); 
	        while(it.hasNext()){ 
	            int verticeAtual = it.next();
	            if(distancias[verticeAtual] < menorValor){ 
	                menorValor = distancias[verticeAtual]; 
	                verticeDeMenorPeso = verticeAtual;
	            }
	        } 
	         
	        if (representacaoTexto.size() > 1) {
	        	System.out.println("Caminho: "+representacaoTexto.remove(verticeDeMenorPeso)+" ("+menorValor+"km)");	
	        }
	        filaDePrioridade.remove(new Integer(verticeDeMenorPeso));
			 
            for(int i = 0 ; i < getQtdVertice() ; i++){
                if(getMatriz()[verticeDeMenorPeso][i] > 0) {
                	distanciaDefinir(verticeDeMenorPeso,i);
                }                    
            }
        } 
	}
	
	 /* 
     * Relaxa arestas no grafo 
     */ 
    private void distanciaDefinir(int u, int v){ 
    	
            if (distancias[v] > distancias[u]+getMatriz()[u][v]){  
                distancias[v] = distancias[u]+getMatriz()[u][v];
            } 
    } 

	public static void main(String[] args) {

		try{ 
            Dijkstra dij = new Dijkstra(8); 
            
            dij.inserirAresta(0, 0, 0);
			dij.inserirAresta(0, 1, 4);
			dij.inserirAresta(0, 2, 4);
			dij.inserirAresta(0, 3, 0);
			dij.inserirAresta(0, 4, 2);
			dij.inserirAresta(0, 5, 0);
			dij.inserirAresta(0, 6, 6);
			dij.inserirAresta(0, 7, 0);
			
			dij.inserirAresta(1, 0, 4);
			dij.inserirAresta(1, 1, 0);
			dij.inserirAresta(1, 2, 2);
			dij.inserirAresta(1, 3, 7);
			dij.inserirAresta(1, 4, 0);
			dij.inserirAresta(1, 5, 0);
			dij.inserirAresta(1, 6, 3);
			dij.inserirAresta(1, 7, 0);
			
			dij.inserirAresta(2, 0, 4);
			dij.inserirAresta(2, 1, 2);
			dij.inserirAresta(2, 2, 0);
			dij.inserirAresta(2, 3, 3);
			dij.inserirAresta(2, 4, 2);
			dij.inserirAresta(2, 5, 0);
			dij.inserirAresta(2, 6, 0);
			dij.inserirAresta(2, 7, 0);

			dij.inserirAresta(3, 0, 0);
			dij.inserirAresta(3, 1, 7);
			dij.inserirAresta(3, 2, 3);
			dij.inserirAresta(3, 3, 0);
			dij.inserirAresta(3, 4, 0);
			dij.inserirAresta(3, 5, 0);
			dij.inserirAresta(3, 6, 0);
			dij.inserirAresta(3, 7, 2);
			
			dij.inserirAresta(4, 0, 2);
			dij.inserirAresta(4, 1, 0);
			dij.inserirAresta(4, 2, 2);
			dij.inserirAresta(4, 3, 0);
			dij.inserirAresta(4, 4, 0);
			dij.inserirAresta(4, 5, 7);
			dij.inserirAresta(4, 6, 0);
			dij.inserirAresta(4, 7, 4);
			
			dij.inserirAresta(5, 0, 0);
			dij.inserirAresta(5, 1, 0);
			dij.inserirAresta(5, 2, 0);
			dij.inserirAresta(5, 3, 0);
			dij.inserirAresta(5, 4, 7);
			dij.inserirAresta(5, 5, 0);
			dij.inserirAresta(5, 6, 0);
			dij.inserirAresta(5, 7, 3);
			
			dij.inserirAresta(6, 0, 6);
			dij.inserirAresta(6, 1, 3);
			dij.inserirAresta(6, 2, 0);
			dij.inserirAresta(6, 3, 0);
			dij.inserirAresta(6, 4, 0);
			dij.inserirAresta(6, 5, 0);
			dij.inserirAresta(6, 6, 0);
			dij.inserirAresta(6, 7, 0);
			
			dij.inserirAresta(7, 0, 0);
			dij.inserirAresta(7, 1, 0);
			dij.inserirAresta(7, 2, 0);
			dij.inserirAresta(7, 3, 2);
			dij.inserirAresta(7, 4, 4);
			dij.inserirAresta(7, 5, 3);
			dij.inserirAresta(7, 6, 0);
			dij.inserirAresta(7, 7, 0);
            
            dij.menorCaminhoEncontrar(1,7);
        }catch(Exception ex){ 
            if(ex.getMessage() == null) 
                  System.out.println("Ocorreu um erro de "+ex+" no main"); 
            else  
                System.out.println(ex.getMessage()+"XXX"); 
        } 

	}

}
