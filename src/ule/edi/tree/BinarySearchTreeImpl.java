package ule.edi.tree;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.Stack;

/**
 * arbol binario de busqueda (binary search tree, BST).
 * 
 * El codigo fuente esta en UTF-8, y la constante EMPTY_TREE_MARK definida en
 * AbstractTreeADT del proyecto API deberia ser el simbolo de conjunto vacio:
 * ∅
 * 
 * Si aparecen caracteres "raros", es porque el proyecto no esta bien
 * configurado en Eclipse para usar esa codificacion de caracteres.
 *
 * En el toString() que esta ya implementado en AbstractTreeADT se usa el
 * formato:
 * 
 * Un arbol vaci­o se representa como "∅". Un Ã¡rbol no vacio como
 * "{(informacion rai­z), sub-arbol 1, sub-arbol 2, ...}".
 * 
 * Por ejemplo, {A, {B, ∅, ∅}, ∅} es un arbol binario con rai­z "A" y un
 * unico sub-arbol, a su izquierda, con rai­z "B".
 * 
 * El metodo render() tambien representa un arbol, pero con otro formato; por
 * ejemplo, un arbol {M, {E, ∅, ∅}, {S, ∅, ∅}} se muestra como:
 * 
 * M 
 * | E
 * | | ∅
 * | | ∅ 
 * | S 
 * | | ∅
 * | | ∅
 * 
 * Cualquier nodo puede llevar asociados pares (clave,valor) para adjuntar
 * informacion extra. Si es el caso, tanto toString() como render() mostraran
 * los pares asociados a cada nodo.
 * 
 * Con {@link #setTag(String, Object)} se inserta un par (clave,valor) y con
 * {@link #getTag(String)} se consulta.
 * 
 * 
 * Con <T extends Comparable<? super T>> se pide que exista un orden en los
 * elementos. Se necesita para poder comparar elementos al insertar.
 * 
 * Si se usara <T extends Comparable<T>> seria muy restrictivo; en su lugar se
 * permiten tipos que sean comparables no solo con exactamente T sino tambien
 * con tipos por encima de T en la herencia.
 * 
 * @param <T> tipo de la informacion en cada nodo, comparable.
 */
public class BinarySearchTreeImpl<T extends Comparable<? super T>> extends AbstractBinaryTreeADT<T> {

	BinarySearchTreeImpl<T> father; // referencia a su nodo padre)
	int count;  // contador de instancias 

	/**
	 * Devuelve el arbol binario de busqueda izquierdo.
	 */
	protected BinarySearchTreeImpl<T> getLeftBST() {
		// El atributo leftSubtree es de tipo AbstractBinaryTreeADT<T> pero
		// aqui­ se sabe que es ademas BST (binario de busqueda)
		//
		return (BinarySearchTreeImpl<T>) leftSubtree;
	}

	protected void setLeftBST(BinarySearchTreeImpl<T> left) {
		this.leftSubtree = left;
	}

	/**
	 * Devuelve el arbol binario de busqueda derecho.
     */
	protected BinarySearchTreeImpl<T> getRightBST() {
		return (BinarySearchTreeImpl<T>) rightSubtree;
	}

	protected void setRightBST(BinarySearchTreeImpl<T> right) {
		this.rightSubtree = right;
	}

	/**
	 * arbol BST vaci­o
	 */
	public BinarySearchTreeImpl() {
		this.content = null;
		this.leftSubtree = null;
		this.rightSubtree = null;
		this.count = 0;
	}

	public BinarySearchTreeImpl(BinarySearchTreeImpl<T> father) {
		this.content = null;
		this.leftSubtree = null;
		this.rightSubtree = null;
		this.father = father;
		this.count = 0;
	}

	private BinarySearchTreeImpl<T> emptyBST(BinarySearchTreeImpl<T> father) {
		
		return new BinarySearchTreeImpl<T>(father);
	}

	
	
	/**
	 * Inserta los elementos que no sean null, de una coleccion en el arbol. 
	 * (si alguno es 'null', no lo inserta)
	 * 
	 * No se permiten elementos null.
	 * 
	 * @param elements valores a insertar.
	 * @return numero de elementos insertados en el arbol (elementos diferentes de null)
	 */
	public int insert(Collection<T> elements) {
		int count = 0;
		Iterator <T> colec = elements.iterator();
		while(colec.hasNext()) {
			T elem = colec.next();
			if(elem != null) {
				insert(elem);
				count++;
			}
		}
		return count;
	
	}
	/**
	 * Inserta los elementos que no sean null, de un array en el arbol. 
	 * (si alguno es 'null', no lo inserta)
	 * 
	 * No se permiten elementos null.
	 * 
	 * @param elements elementos a insertar.
	 * @return  	numero de elementos insertados en el arbol (elementos diferentes de null)
	 */
	public int insert(T... elements) {
		int count = 0;
		for(int i=0;i<elements.length;i++) {
			if(elements[i] != null) {
				insert(elements[i]);
				count++;
			}
		}
		return count;
	}
	/**
	 * Inserta (como hoja) un nuevo elemento en el arbol de busqueda.
	 * 
	 * Debe asignarse valor a su atributo father (referencia a su nodo padre o null
	 * si es la rai­z)
	 * 
	 * No se permiten elementos null. Si element es null dispara excepcion:IllegalArgumentException 
	 * Si el elemento ya existe en el arbol
	 *  no inserta un nodo nuevo, sino que incrementa el atributo count del nodo que tiene igual contenido.
	 * 
	 * @param element valor a insertar.
	 * @return true si se insertó en un nuevo nodo (no existia ese elemento en el arbol),
	 *         false en caso contrario
	 * @throws IllegalArgumentException si element es null
	 */
	public boolean insert(T element) {
		boolean insrt;
		if(element == null) {
			throw new IllegalArgumentException();
		}
		if(!isEmpty()) {
			if(element.compareTo(this.content)<0) {
				insrt = getLeftBST().insert(element);
			}else
			if(element.compareTo(this.content)>0) {
				insrt = getRightBST().insert(element);
			}else {
				count++;
				insrt = false;
			}
		}else {
			this.content = element;
			this.count++;
			setLeftBST(new BinarySearchTreeImpl<T>(this));
			setRightBST(new BinarySearchTreeImpl<T>(this));
			insrt = true;
		}
	 return true;
	}

	/**
	 * Busca el elemento en el arbol.
	 * 
	 * No se permiten elementos null.
	 * 
	 * @param element valor a buscar.
	 * @return true si el elemento esta en el arbol, false en caso contrario
	 * @throws IllegalArgumentException si element es null
	 *
	 */
	public boolean contains(T element) {
		boolean contns;
		if(element == null) {
			throw new IllegalArgumentException();
		}
		if(!isEmpty()) {
			if(element.compareTo(this.content)<0) {
				contns = getLeftBST().contains(element);
			}else
			if(element.compareTo(this.content)>0) {
				contns = getRightBST().contains(element);
			}else {
				contns = true;
			}
		}else {
			contns = false;
		}
		return contns;
	}
	
	/**
	 *  devuelve la cadena formada por el contenido del árbol teniendo en cuenta que 
	 *  si un nodo tiene su atributo count>1 pone entre paréntesis su valor justo detrás del atributo elem
	 *  También debe mostrar las etiquetas que tenga el nodo (si las tiene)
	 *  
	 *  CONSEJO: REVISAR LA IMPLEMENTACIÓN DE TOSTRING DE LA CLASE AbstractTreeADT 
	 * 
	 * Por ejemplo: {M, {E(2), ∅, ∅}, {K(5), ∅, ∅}}
	 * 
	 * @return cadena con el contenido del árbol incluyendo su atributo count entre paréntesis si elemento tiene más de 1 instancia
	 */
	public String toString() {
		if (! isEmpty()) {

			StringBuffer result = new StringBuffer();
				

			result.append("{" + content.toString());
			
			if (! tags.isEmpty()) {
				result.append(" [");
				
				List<String> sk = new LinkedList<String>(tags.keySet());
				
				Collections.sort(sk);
				for (String k : sk) {
					result.append("(" + k + ", " + tags.get(k) + "), ");
				}
				result.delete(result.length() - 2, result.length());
				result.append("]");
			}
			

			for (int i = 0; i < getMaxDegree(); i++) {
				result.append(", " + getSubtree(i).toString());
			}

			result.append("}");
			
			return result.toString();
		} else {
			return AbstractTreeADT.EMPTY_TREE_MARK;
		}
	}

		/**
	 * Importante: Solamente se puede recorrer el arbol una vez
	 * 
	 * Etiqueta cada nodo hoja con la etiqueta "height" y el valor correspondiente a la
	 * altura del nodo.
	 * 
	 * Por ejemplo, sea un arbol "A":
	 * 
	 * {10, {5, {2, ∅, ∅}, ∅}, {20, {15, ∅, ∅}, {30, ∅, ∅}}}
	 * 
	 * 10
	 * | 5 
	 * | | 2
	 * | | | ∅ 
	 * | | | ∅ 
	 * | | ∅ 
	 * | 20 
	 * | | 15 
	 * | | | ∅
	 * | | | ∅ 
	 * | | 30 
	 * | | | ∅ 
	 * | | | ∅
	 * 
	 * 
	 * el arbol quedara etiquetado:
	 * 
	 * {10, 
	 * {5, {2 [(height, 3)],∅, ∅}, ∅}, 
	 * {20,{15, {12 [(height, 4)], ∅, ∅}, ∅}, ∅}
	 * }
	 * 
	 */
	public void tagHeightLeaf() {
		int height = 1;
		tagHeightLeafRec(height);
	}
	public void tagHeightLeafRec(int height) {
		if(isLeaf()) {
			setTag("height",height);
			}
		else {
			if(!isEmpty()) {
				height++;
				getLeftBST().tagHeightLeafRec(height);
				getRightBST().tagHeightLeafRec(height);
			}
		}
	}


	
	
	/**
	 * Devuelve un iterador que recorre los elementos (sin tener en cuenta el número de instancias)del arbol por niveles segun
	 * el recorrido en anchura
	 * 
	 * Por ejemplo, con el arbol
	 * 
	 * {50, {30(2), {10, ∅, ∅}, {40, ∅, ∅}}, {80(2), {60, ∅, ∅}, ∅}}
	 * 
	 * y devolvera el iterador que recorrera los nodos en el orden: 50, 30, 80, 10, 40, 60
	 * 
	 * 
	 * 
	 * @return iterador para el recorrido en anchura
	 */
    public Iterator<T> iteratorWidth() {
    	LinkedList<BinarySearchTreeImpl<T>> cola=new LinkedList<BinarySearchTreeImpl<T>>();
    	LinkedList<T> resultados = new LinkedList<T>();
    	BinarySearchTreeImpl<T> aux;
    	T elemento;
    	if(!this.isEmpty()) {
    	cola.addLast(this);
    	}
    	while(!(cola.isEmpty())) {
    		aux=cola.removeFirst();
    		elemento=aux.content;
    		resultados.add(elemento);
    		
    		if(!aux.getLeftBST().isEmpty()) {
    			cola.addLast(aux.getLeftBST());
    		}
    		if(!aux.getRightBST().isEmpty()) {
    			cola.addLast(aux.getRightBST());
    		}
    	}
    	return resultados.iterator();
	}
	
	/**
	 * Devuelve un iterador que recorre los elementos (teniendo en cuenta el número de instancias)del arbol por niveles segun
	 * el recorrido en anchura
	 * 
	 * Por ejemplo, con el arbol
	 * 
	 * {50, {30(2), {10, ∅, ∅}, {40, ∅, ∅}}, {80(2), {60, ∅, ∅}, ∅}}
	 * 
	 * y devolvera el iterador que recorrera los nodos en el orden: 50, 30, 30, 80, 80, 10, 40, 60
	 *  
	 * @return iterador para el recorrido en anchura
	 */
     public Iterator<T> iteratorWidthInstances() {
    	LinkedList<BinarySearchTreeImpl<T>> cola=new LinkedList<BinarySearchTreeImpl<T>>();
    	LinkedList<T> resultados = new LinkedList<T>();
    	BinarySearchTreeImpl<T> aux;
    	T elemento;
    	if(!this.isEmpty()) {
    	cola.addLast(this);
    	}
    	while(!(cola.isEmpty())) {
    		aux=cola.removeFirst();
    		elemento=aux.content;
    		resultados.add(elemento);
    		
    		if(!aux.getLeftBST().isEmpty()) {
    			cola.addLast(aux.getLeftBST());
    		}
    		if(!aux.getRightBST().isEmpty()) {
    			cola.addLast(aux.getRightBST());
    		}
    	}
    	return resultados.iterator();
	 }
	
		
	/**
	 * Cuenta el número de elementos diferentes del arbol (no tiene en cuenta las instancias)
	 * 
	 * Por ejemplo, con el arbol
	 * 
	 * {50, {30(2), {10, ∅, ∅}, {40(4), ∅, ∅}}, {80(2), {60, ∅, ∅}, ∅}}
	 * 
	 * la llamada a ejemplo.instancesCount() devolvera 6
	 * 
	 * @return el numero de elementos diferentes del arbol 
	 */
    public int size() {
		return this.instancesCount();
	}
	
    /**
	 * Cuenta el número de instancias de elementos diferentes del arbol 
	 * 
	 * Por ejemplo, con el arbol ejemplo=
	 * 
	 * {50, {30(2), {10, ∅, ∅}, {40(4), ∅, ∅}}, {80(2), {60, ∅, ∅}, ∅}}
	 * 
	 * la llamada a ejemplo.instancesCount() devolvera 11
	 * 
	 * @return el número de instancias de elementos del arbol 
	 */
	public int instancesCount() {
		if(!isEmpty()) {
			return 1 + this.getLeftBST().instancesCount() + this.getRightBST().instancesCount();
		}
		return 0;
	}
	
	/**
	 * Devuelve el sub-árbol indicado. (para tests)
	 * path será el camino para obtener el sub-arbol. Está formado por L y R.
	 * Si se codifica "bajar por la izquierda" como "L" y
	 * "bajar por la derecha" como "R", el camino desde un 
	 * nodo N hasta un nodo M (en uno de sus sub-árboles) será la
	 * cadena de Ls y Rs que indica cómo llegar desde N hasta M.
     *
     * Se define también el camino vacío desde un nodo N hasta
     * él mismo, como cadena vacía.
     *  
     *  Por ejemplo, con el arbol ejemplo=
	 * 
	 * {50, {30(2), {10, ∅, ∅}, {40(4), ∅, ∅}}, {80(2), {60, ∅, ∅}, ∅}}
	 * 
	 * la llamada a ejemplo.getSubtreeWithPath("LL").toString() devolvera "{10, ∅, ∅}"
	 * la llamada a ejemplo.getSubtreeWithPath("R").toString() devolvera "{80(2), {60, ∅, ∅}, ∅}"
	 * la llamada a ejemplo.getSubtreeWithPath("").toString() devolvera "{50, {30(2), {10, ∅, ∅}, {40(4), ∅, ∅}}, {80(2), {60, ∅, ∅}, ∅}}"
	 * la llamada a ejemplo.getSubtreeWithPath("RR").toString() disparará excepción NoSuchElementException
	 * 
	 * Si el subarbol no existe lanzará la excepción NoSuchElementException.
	 * 
	 * @param path 
	 * @return
	 * @throws NoSuchElementException si el subarbol no existe
	 */
	public BinarySearchTreeImpl<T> getSubtreeWithPath(String path) {
		LinkedList<String> lista= new LinkedList<String>();
		 if (!isEmpty()) {
		 String camino="";
		 getSubtreeREc(camino, lista,path);
		 }
		 return father;
		}
	
	private void getSubtreeREc(String camino, LinkedList<String> lista,String path) {
		 if (!isEmpty()) {

		 this.getLeftBST().getSubtreeREc(camino+"L", lista,path);

		
		 this.getRightBST().getSubtreeREc(camino+"R", lista,path);
		}
	} 
	
	/**
	 * Devuelve el String que representa el camino formado por L's y R's desde 
	 * la raiz hasta el elemento pasado como parámetro.
	 * Se codifica "bajar por la izquierda" como "L" y
	 * "bajar por la derecha" como "R", el camino desde un 
	 * nodo N hasta un nodo M (en uno de sus sub-árboles) será la
	 * cadena de Ls y Rs que indica cómo llegar desde N hasta M.
     *
     * Se define también el camino vacío desde un nodo N hasta
     * él mismo, como cadena vacía.
     *  
     *  Por ejemplo, con el arbol ejemplo=
	 * 
	 * {50, {30(2), {10, ∅, ∅}, {40(4), ∅, ∅}}, {80(2), {60, ∅, ∅}, ∅}}
	 * 
	 * la llamada a ejemplo.getPath(10) devolvera "LL"
	 * la llamada a ejemplo.getPath(60) devolvera "RL"
	 * la llamada a ejemplo.getPath(50) devolvera ""
	 * la llamada a ejemplo.getPath(100) disparará excepción NoSuchElementException
	 * 
	 * Si el elemento no existe lanzará la excepción NoSuchElementException.
	 * 
	 * @param elem 
	 * @return camino hasta el elemento
	 * @throws NoSuchElementException si el elemento no existe
	 */
	public String getPath(T elem) {
		LinkedList<String> lista= new LinkedList<String>();
		 if (!isEmpty()) {
		 String camino="";
		 caminoR(camino, lista,elem);
		 }
		 return lista.toString();
		}
	
	private void caminoR(String camino, LinkedList<String> lista,T elem) {
		 if (!isEmpty()) {

		 this.getLeftBST().caminoR(camino+"L", lista,elem);

		 
		 if (this.content.compareTo(elem)==0) {
		 lista.add( " camino: "+camino);
		 }

		
		 else this.getRightBST().caminoR(camino+"R", lista,elem);
		}
		} 
	/**
	 * Importante: Solamente se puede recorrer el arbol una vez
	 * 
	 * Recorre en orden descendente el arbol etiquetando todos sus nodos con la etiqueta "descend" y
	 * el valor correspondiente a la posición en dicho recorrido.
	 * 
	 * 
	 * Por ejemplo, sea el arbol ejemplo
	 * 
	 * {50, {30(2), {10, ∅, ∅}, {40(4), ∅, ∅}}, {80(2), {60, ∅, ∅}, ∅}}
	 * 
	 * la llamada a ejemplo.tagPosDescend() el arbol quedaria etiquetado:
	 * 
     *  {50 [(descend, 3)], {30(2) [(descend, 5)], {10 [(descend, 6)], ∅, ∅}, 
     *   {40(4) [(descend, 4)], ∅, ∅}}, {80(2) [(descend, 1)], {60 [(descend, 2)], ∅, ∅}, ∅}}
	 * 
	 */
	public void tagPosDescend() {
		int[] pos = {0};
		tagRec(pos);
	}
	
	private void tagRec(int[] pos) {
		 if (!isEmpty()) {

		 this.getLeftBST().tagRec(pos);


		 pos[0]++;
		 this.setTag("descend", pos);
		 

		 
		  this.getRightBST().tagRec(pos);
		} 
	}
	
	/**
	 * Importante: Solamente se puede recorrer el arbol una vez
	 * 
	 * Calcula y devuelve el numero de nodos internos del árbol (no sean hojas) y etiqueta cada
	 * nodo interno con la etiqueta "internal" y el valor correspondiente a su posicion segun el
	 * recorrido inorden en este arbol.
	 * 
	 * La rai­z se considera nodo interno.
	 * 
	 * Por ejemplo, sea un arbol ejemplo:
	 * 
	 * {30, {10, {5, {2, ∅, ∅}, ∅}, {20, {15, {12, ∅, ∅}, ∅}, ∅}, ∅}
	 * 
	 *tras la llamada a ejemplo.tagInternalInorder() devolvería 5 y dejaría el arbol etiquetado:
	 * 
	 * {30[(internal, 7)], {10 [(internal, 3)], {5[(internal, 2)], {2, ∅, ∅}, ∅}, {20 [(internal, 6)], 
	 *  {15 [(internal, 5)], {12, ∅, ∅}, ∅}, ∅}, ∅}
	 
	 * 
	 */
	public int tagInternalInorder() {
		int[] pos = {0};
		int[] num = {0};
		return tagInternalRec(pos,num)[0];
		
	}
	
	private int[] tagInternalRec(int[] pos, int[] num) {
		 if (!isEmpty()) {

		 this.getLeftBST().tagInternalRec(pos,num);


		 pos[0]++;
		 if(!this.isLeaf()) {
		 this.setTag("internal", pos);
		 num[0]++;
		 }

		 
		 else {this.getRightBST().tagInternalRec(pos,num);}
		} 
		 return num;
	}
	
	/**
	 * Importante: Solamente se puede recorrer el arbol una vez
	 * 
	 * Calcula y devuelve el numero de nodos que son hijos unicos y etiqueta cada
	 * nodo que sea hijo unico (no tenga hermano hijo del mismo padre) con la
	 * etiqueta "onlySon" y el valor correspondiente a su posicion segun el
	 * recorrido preorden en este arbol.
	 * 
	 * La rai­z no se considera hijo unico.
	 * 
	 * Por ejemplo, sea un arbol ejemplo, que tiene 3 hijos unicos, 
	 * la llamada a ejemplo.tagOnlySonPreorder() devuelve 3 y los va etiquetando
	 * segun su recorrido en preorden.
	 * 
	 * {30, {10, {5, {2, ∅, ∅}, ∅}, {20, {15, {12, ∅, ∅}, ∅}, ∅}, ∅}
	 * 
	 *
	 * el arbol quedari­a etiquetado:
	 * 
	 * {30, {10 [(onlySon, 2)], {5, {2 [(onlySon, 4)], ∅, ∅}, ∅}, {20, {15 [(onlySon, 6)], {12
	 * [(onlySon, 7)], ∅, ∅}, ∅}, ∅}, ∅}
	 * 
	 */
	public int tagOnlySonPreorder() {
		int[] pos = {0};
		int[] num = {0};
		return tagOnlySonPreOrder(pos,num)[0];
		
	}
	
	private int[] tagOnlySonPreOrder(int[] pos, int[] num) {
		 if (!isEmpty()) {

		 pos[0]++;
		 if((this.father.leftSubtree == null && this.father.rightSubtree != null) || (this.father.leftSubtree != null && this.father.rightSubtree == null)) {
		 this.setTag("onlyson", pos);
		 num[0]++;
		 }

		this.getLeftBST().tagInternalRec(pos,num);
		this.getRightBST().tagInternalRec(pos,num);
		} 
		 return num;
	}

	
	/**
	 * Busca y devuelve a partir del nodo que contiene el elemento pasado como parámetro 
	 * el elemento que está up posiciones hacia arriba y right hacia abajo bajando por la rama derecha. 
	 * Primero debe encontrar el elemento y despues comprueba si el nodo que contiene ese elemento
	 * tiene nodo a través del camino indicado por los otros dos parámetros.
     * Debe etiquetar desde el nodo que contiene el elemento,  hasta su objetivo,  los nodos del camino 
     * con un número consecutivo empezando por el 1 en el elemento buscado. 
     * 
     * Por ejemplo: para el árbol ejemplo= {10, {5, {2, ∅, ∅}, {7,∅, ∅},}, {20, {15, {12, ∅, ∅}, ∅ },{30, ∅, ∅}}}. 
     * 
     * Si se hace ejemplo.getRoadUpRight("7",2,2) devolverá el elemento 30 y etiquetará los nodos 7, 5, 10, 20, 30 con numeros consecutivos
     *  y la etiqueta road. 
     *  
     * Así el árbol quedaría etiquetado: 10 [(road, 3)],{5[(road, 2)], {2, ∅, ∅}, {7 [(road, 1)],∅, ∅},}, {20 [(road, 4)], {15, {12, ∅, ∅}, ∅},{30 [(road, 5)], ∅, ∅}}}
     *  siendo el nodo que contiene el 30 el nodo que devuelve.
	 * 
	 * @throws NoSuchElementException si el elemento a comprobar no esta en el arbol	
	 * @throws IllegalArgumentException si element es null
	 */
	public T getRoadUpRight(T elem, int up, int right) {
		int[] pos = {0};
		return getRoadUpRightR(pos,elem);
		
	}
	
	private T getRoadUpRightR(int[] pos, T elem) {
		 if (!isEmpty()) {

		 this.getLeftBST().getRoadUpRightR(pos,elem);


		 pos[0]++;
		 if(this.content.compareTo(elem)==0) {
		 this.setTag("road", pos);
		 }

		 
		 else {this.getRightBST().getRoadUpRightR(pos,elem);}
		} 
		 return elem;
	}
		
	
	/**
	 * Crea y devuelve un árbol exactamente igual que el this
	 * 
	 * @return un arbol exactamente igual (misma estructura y contenido) que el arbol this
	 */
	public BinarySearchTreeImpl<T> copy(){
		return father;
		
	}
	
	/**
	 * Elimina los valores en un array del Arbol.
	 * Devuelve el número de elementos que pudo eliminar del árbol
	 *  (no podrá eliminar los elemenots 'null' o que no los contiene el arbol)
	 * 
	 * return numero de elementos eliminados del arbol
	 */
	public int  remove(T... elements) {
		int count = 0;
		for(int i=0; i<elements.length;i++) {
			count = count + removeR(i,elements);
		}
		return count;
	}
	
	public int removeR(int i,T[]element) {
		if(!isEmpty()) {
			if(element[i].compareTo(this.content)<0) {
				this.getLeftBST().removeR(i, element);
			}else {
				this.getRightBST().removeR(i, element);
			}
		}else {
			if(element[i]!= null) {
				if(element[i].compareTo(this.content)==0) {
					remove(this.content);
					return 1;
				}
			}
		return 0;
		}
		return 0;
	}
	/**
	 * Elimina un elemento del arbol. Si el atributo count del nodo que contiene el elemento es >1, simplemente se decrementará este valor en una unidad
	 * 
	 * Si hay que eliminar el nodo, y tiene dos hijos, se tomara el criterio de sustituir el
	 * elemento por el menor de sus mayores y eliminar el menor de los mayores.
	 * 
	 * @throws NoSuchElementException si el elemento a eliminar no esta en el arbol
	 * @throws IllegalArgumentException si element es null
     *
	 */
	public void remove(T element) {
		if(isEmpty()) {
			throw new NoSuchElementException();
		}
		if(element == null) {
			throw new IllegalArgumentException();
		}
		if(content.compareTo(element)!=0) {
			if(content.compareTo(element) >0) {
			getRightBST().remove(element);
			}else if(content.compareTo(element) >0) {
			getLeftBST().remove(element);
			}
		}else if(content.compareTo(element) == 0) {
			if(count == 1) {
				if(isLeaf()) {
					this.content = null;
					this.count = 0;
					setLeftBST(null);
					setRightBST(null);
				}else if(getLeftBST().content == null || getRightBST().content == null) {
			
					if(getLeftBST().content == null) {
						content = getRightBST().content;
						count = getRightBST().count;
						this.leftSubtree = getRightBST().getLeftBST();
						this.rightSubtree = getRightBST().getLeftBST();
						getLeftBST().father = this;
						getRightBST().father = this;
					}else {
						content = getLeftBST().content;
						count = getLeftBST().count;
						this.leftSubtree = getLeftBST().getRightBST();
						this.rightSubtree = getLeftBST().getRightBST();
						getLeftBST().father = this;
						getRightBST().father = this;
				}
				}else {
					BinarySearchTreeImpl<T> aux = this;
					aux = aux.getRightBST();
					while(aux.getLeftBST().content != null) {
						aux = aux.getRightBST();
					}
					T result = aux.content;
					int cont = aux.count;
					removeAll(result);
					this.content = result;
					this.count = cont;
					
				}
			}else {
				count = count-1;
			}
		}
	}
	
	/**
	 * Decrementa el número de instancias del elemento en num unidades.
	 * Si count queda en cero o negativo, se elimina el elemento del arbol. 
	 * 
	 * 
	 * Si hay que eliminar el nodo, y tiene dos hijos, se tomara el criterio de sustituir el
	 * elemento por el menor de sus mayores y eliminar el menor de los mayores.
	 * 
	 * @throws NoSuchElementException si el elemento a eliminar no esta en el arbol	
	 * @throws IllegalArgumentException si element es null
	 */
	public void remove(T element, int num) {
		if(isEmpty()) {
			throw new NoSuchElementException();
		}
		if(element == null) {
			throw new IllegalArgumentException();
		}
		if(content.compareTo(element)!=0) {
			if(content.compareTo(element) >0) {
			getRightBST().remove(element);
			}else if(content.compareTo(element) >0) {
			getLeftBST().remove(element);
			}
		}else if(content.compareTo(element) == 0) {
			if(count == 1) {
				if(isLeaf()) {
					this.content = null;
					this.count = count - num;
					setLeftBST(null);
					setRightBST(null);
				}else if(getLeftBST().content == null || getRightBST().content == null) {
			
					if(getLeftBST().content == null) {
						content = getRightBST().content;
						count = getRightBST().count;
						this.leftSubtree = getRightBST().getLeftBST();
						this.rightSubtree = getRightBST().getLeftBST();
						getLeftBST().father = this;
						getRightBST().father = this;
					}else {
						content = getLeftBST().content;
						count = getLeftBST().count;
						this.leftSubtree = getLeftBST().getRightBST();
						this.rightSubtree = getLeftBST().getRightBST();
						getLeftBST().father = this;
						getRightBST().father = this;
				}
				}else {
					BinarySearchTreeImpl<T> aux = this;
					aux = aux.getRightBST();
					while(aux.getLeftBST().content != null) {
						aux = aux.getRightBST();
					}
					T result = aux.content;
					int cont = aux.count;
					removeAll(result);
					this.content = result;
					this.count = cont;
					
				}
			}else {
				count = count-1;
			}
		}
	}
	

	
	/**
	 * Elimina todas las instancias del elemento en el árbol 
	 * eliminando del arbol el nodo que contiene el elemento .
	 * 
	 * 
	 * Se tomara el criterio de sustituir el elemento por el menor de sus mayores 
	 * y eliminar el menor de los mayores.
	 * 
	 * @throws NoSuchElementException si el elemento a eliminar no esta en el arbol	
	 * @throws IllegalArgumentException si element es null
	 */
	public int removeAll(T element) {
		if(isEmpty()) {
			throw new NoSuchElementException();
		}
		if(element == null) {
			throw new IllegalArgumentException();
		}
		if(content.compareTo(element)!=0) {
			if(content.compareTo(element) >0) {
			getRightBST().remove(element);
			}else if(content.compareTo(element) >0) {
			getLeftBST().remove(element);
			}
		}else if(content.compareTo(element) == 0) {
			if(count == 1) {
				if(isLeaf()) {
					this.content = null;
					this.count = 0;
					setLeftBST(null);
					setRightBST(null);
				}else if(getLeftBST().content == null || getRightBST().content == null) {
			
					if(getLeftBST().content == null) {
						content = getRightBST().content;
						count = getRightBST().count;
						this.leftSubtree = getRightBST().getLeftBST();
						this.rightSubtree = getRightBST().getLeftBST();
						getLeftBST().father = this;
						getRightBST().father = this;
					}else {
						content = getLeftBST().content;
						count = getLeftBST().count;
						this.leftSubtree = getLeftBST().getRightBST();
						this.rightSubtree = getLeftBST().getRightBST();
						getLeftBST().father = this;
						getRightBST().father = this;
				}
				}else {
					BinarySearchTreeImpl<T> aux = this;
					aux = aux.getRightBST();
					while(aux.getLeftBST().content != null) {
						aux = aux.getRightBST();
					}
					T result = aux.content;
					int cont = aux.count;
					removeAll(result);
					this.content = result;
					this.count = cont;
					
				}
			}else {
				count = count-1;
			}
		}
		return count;
	}

	
}
	
	