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
            }
            break;
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
        // Si lo removemos, cardinal --;
        throw new UnsupportedOperationException("No implementada aun");
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
