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

    private class PadreHijoNodo {
        private Nodo padre, hijo;
        private PadreHijoNodo(Nodo padre, Nodo hijo) {
            this.padre = padre;
            this.hijo = hijo;
        }
    }

    public ABB() {
        this.raiz = null;
    }

    public int cardinal() {
        return cardinal;
    }

    public T minimo(){
        PadreHijoNodo min = minimo(this.raiz);
        if (min == null) {
            return null;
        }
    
        return min.hijo.value;
    }

    public PadreHijoNodo minimo(Nodo raiz){
        if (raiz == null) {
            return null;
        }

        Nodo nodoActual = raiz;
        Nodo padre = null;
        while (nodoActual != null) {
            if (nodoActual.izquierda != null) {
                padre = nodoActual;
                nodoActual = nodoActual.izquierda;
            } else {
                break;
            }
        }

        return new PadreHijoNodo(padre, nodoActual);
    }

    public T maximo(){
        if (raiz == null) {
            return null;
        }

        Nodo nodoActual = raiz;
        while (nodoActual != null) {
            if (nodoActual.derecha != null) {
                nodoActual = nodoActual.derecha;
            } else {
                break;
            }
        }

        return nodoActual.value;
    }

    public void insertar(T elem){
        if (raiz == null) {
            raiz = new Nodo(elem);
            //raiz.value = elem;
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

    public PadreHijoNodo encontrar(T elem) {
        Nodo raizTmp = raiz;
        Nodo padre = null;
        boolean found = false;
        while(raiz != null && raiz.value != null) {
            int comparisson = raiz.value.compareTo(elem);
            if (comparisson == 0) {
                found = true;
                break;
            }

            // Raiz es menor, voy a la derecha
            padre = raiz;
            if (comparisson < 0) {
                raiz = raiz.derecha;
            } else {
                raiz = raiz.izquierda;
            }
        }

        Nodo target = raiz;
        raiz = raizTmp;
        return new PadreHijoNodo(padre, target);
    }

    public boolean pertenece(T elem) {
        Nodo res = encontrar(elem).hijo;
        return res != null;
    }

    public void eliminar(T elem){
        PadreHijoNodo padreHijoNodo = encontrar(elem);
        if (padreHijoNodo == null) {
            return;
        }

        cardinal--;
        removerNodo(padreHijoNodo.hijo, padreHijoNodo.padre);
    }

    private void removerNodo(Nodo nodo, Nodo padre) {
        // Caso 1: Ningun hijo
        if (nodo.izquierda == null && nodo.derecha == null) {
            // Si es la raiz entonces seteo raiz a null
            if (nodo.value.compareTo(raiz.value) == 0) {
                raiz = null;
                return;
            }

            // Sino al padre le remuevo la coneccion a este nodo.
            if (padre.izquierda != null && padre.izquierda.value.compareTo(nodo.value) == 0) {
                padre.izquierda = null;
            } else {
                padre.derecha = null;
            }

            return;
        }
    
        // Caso 2.1: El de la derecha es null pero la izq no.
        if (nodo.izquierda != null && nodo.derecha == null) {
            // Como el padre sigue apuntando a "nodo" reemplazo los valores.
            nodo.value = nodo.izquierda.value;
            nodo.derecha = nodo.izquierda.derecha;
            nodo.izquierda = nodo.izquierda.izquierda;
            return;
        }

        // Caso 2.2: El de la izq es null pero el de la derecha no.
        if (nodo.izquierda == null && nodo.derecha != null) {
            nodo.value = nodo.derecha.value;
            nodo.izquierda = nodo.derecha.izquierda;
            nodo.derecha = nodo.derecha.derecha;
            return;
        }

        // Caso 3: Tiene dos nodos hijos.
        Nodo sucessorSearch = nodo.derecha;
        PadreHijoNodo sucessorPair = minimo(sucessorSearch);
        if (sucessorPair.padre == null) {
            // No iteramos mas de una vez dentro de minimo.
            sucessorPair.padre = nodo;
        }
        T val = sucessorPair.hijo.value;
        removerNodo(sucessorPair.hijo, sucessorPair.padre);
        nodo.value = val;
    }

    private Nodo sucesor() {
        return minimo(raiz).hijo;
    }

    public String toString(){
        throw new UnsupportedOperationException("No implementada aun");
    }

    private class ABB_Iterador implements Iterador<T> {
        private Nodo _actual;

        public boolean haySiguiente() {            
            return sucesor() != null;
        }
    
        public T siguiente() {
            Nodo sucesor = sucesor();
            _actual = sucesor;
            return sucesor.value;
        }


        public ABB_Iterador() {
            this._actual = raiz;
        }
    }

    public Iterador<T> iterador() {
        return new ABB_Iterador();
    }

}
