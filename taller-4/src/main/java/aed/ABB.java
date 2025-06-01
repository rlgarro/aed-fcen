package aed;

import java.util.*;

// Todos los tipos de datos "Comparables" tienen el método compareTo()
// elem1.compareTo(elem2) devuelve un entero. Si es mayor a 0, entonces elem1 > elem2
public class ABB<T extends Comparable<T>> implements Conjunto<T> {
    private Nodo raiz = null;
    private int cardinal = 0;

    private class Nodo {
        private Nodo derecha;
        private Nodo izquierda;
        private T value;

        private Nodo(T newVal) {
            this.derecha = null;
            this.izquierda = null;
            this.value = newVal;
        }
    }

    public ABB() {
        this.raiz = new Nodo(null);
    }

    public int cardinal() {
        return cardinal;
    }

    public T minimo(){
        if (raiz == null) {
            return null;
        }

        Nodo nodoActual = raiz;
        while (nodoActual != null) {
            if (nodoActual.izquierda != null) {
                nodoActual = nodoActual.izquierda;
            } else {
                break;
            }
        }

        return nodoActual.value;
    }

    public T maximo(){
        if (raiz == null) {
            return null;
        }

        Nodo nodoActual = raiz;
        while (nodoActual != null) {
            if (nodoActual.derecha != null) {
                nodoActual = nodoActual.derecha;
            }
            break;
        }

        return nodoActual.value;
    }

    public void insertar(T elem){
        if (raiz.value == null) {
            raiz.value = elem;
            cardinal++;
            return;
        } else if (pertenece(elem)) {
            return;
        }

        Nodo raizTmp = raiz;
        while(raiz != null) {
            // Como ya chequeamos si pertenecia, no nos importa chequear el 0.
            int comparisson = raiz.value.compareTo(elem);
            
            // Raiz es menor, voy a la derecha
            if (comparisson < 0) {
                if (raiz.derecha == null) {
                    raiz.derecha = new Nodo(elem);
                    break;
                }

                raiz = raiz.derecha;
            } else {
                if (raiz.izquierda == null) {
                    raiz.izquierda = new Nodo(elem);
                    break;
                }
                raiz = raiz.izquierda;
            }
        }

        //raiz = new Nodo(elem);
        raiz = raizTmp;
        cardinal++;
    }

    public boolean pertenece(T elem) {
        Nodo raizTmp = raiz;
        boolean found = false;
        while(raiz != null && raiz.value != null) {
            int comparisson = raiz.value.compareTo(elem);
            if (comparisson == 0) {
                found = true;
                break;
            }

            // Raiz es menor, voy a la derecha
            if (comparisson < 0) {
                raiz = raiz.derecha;
            } else {
                raiz = raiz.izquierda;
            }
        }

        raiz = raizTmp;
        return found;
    }

    public void eliminar(T elem){
        Nodo raizTmp = raiz;
        Nodo padre = null;
        boolean found = false;
        while(raiz != null && raiz.value != null) {
            int comparisson = raiz.value.compareTo(elem);
            if (comparisson == 0) {
                Nodo toRemove = raiz;
                raiz = raizTmp;
                cardinal--;
                removerNodo(toRemove, padre);
                return;
            }

            // Raiz es menor, voy a la derecha
            padre = raiz;
            if (comparisson < 0) {
                raiz = raiz.derecha;
            } else {
                raiz = raiz.izquierda;
            }
        }

        raiz = raizTmp;
    }

    private void removerNodo(Nodo nodo, Nodo padre) {
        // Caso 1: Ningun hijo
        final boolean isLeftNode = padre.izquierda != null && padre.izquierda.value.compareTo(nodo.value) == 0;
        if (nodo.izquierda == null && nodo.derecha == null) {
            // Busco que nodo del padre tiene ese valor
            if (isLeftNode) {
                padre.izquierda = null;
            } else {
                padre.derecha = null;
            }
        
            return;
        }
    
        // Caso 2.1: El de la derecha es null pero la izq no.
        if (nodo.izquierda != null && nodo.derecha == null) {
            if (isLeftNode) {
                padre.izquierda = nodo.izquierda;
            } else {
                padre.derecha = nodo.izquierda;
            }
            return;
        }

        // Caso 2.2: El de la izq es null pero el de la derecha no.
        if (nodo.izquierda == null && nodo.derecha != null) {
            if (isLeftNode) {
                padre.izquierda = nodo.derecha;
            } else {
                padre.derecha = nodo.derecha;
            }
            return;
        }
    }

    public String toString(){
        throw new UnsupportedOperationException("No implementada aun");
    }

    private class ABB_Iterador implements Iterador<T> {
        private Nodo _actual;

        public boolean haySiguiente() {            
            throw new UnsupportedOperationException("No implementada aun");
        }
    
        public T siguiente() {
            throw new UnsupportedOperationException("No implementada aun");
        }
    }

    public Iterador<T> iterador() {
        return new ABB_Iterador();
    }

}
