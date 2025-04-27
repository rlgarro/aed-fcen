package aed;

import java.util.*;

public class ListaEnlazada<T> implements Secuencia<T> {

    private Nodo primero;
    private Nodo ultimo;
    private int size;

    private class Nodo {
        private Nodo siguiente;
        private Nodo anterior;
        private T elem;

        public Nodo(T elem) {
            this.elem = elem;
            this.anterior = null;
            this.siguiente = null;
        }
    }

    public ListaEnlazada() {
        this.primero = null;
        this.ultimo = null;
        this.size = 0;
    }

    public int longitud() {
        return size;
    }

    public void agregarAdelante(T elem) {
        Nodo aAgregar = new Nodo(elem);
        if (primero == null) {
            primero = aAgregar;
            ultimo = primero;
        } else {
            primero.anterior = aAgregar;
            aAgregar.siguiente = primero;
            primero = aAgregar;
        }
        size++;
    }

    public void agregarAtras(T elem) {
        Nodo aAgregar = new Nodo(elem);
        if (primero == null) {
            primero = aAgregar;
            ultimo = primero;
        } else {
            ultimo.siguiente = aAgregar;
            aAgregar.anterior = ultimo;
            ultimo = aAgregar;
        }
        size++;
    }

    public T obtener(int i) {
        return obtenerNodo(i).elem;
    }

    public void eliminar(int i) {

        if (i == 0) {
            if (size == 1) {
                primero = null;
                ultimo = null;
                size--;
                return;
            }

            primero = primero.siguiente;
            primero.anterior = null;
        } else if (i == size - 1) {
            ultimo = ultimo.anterior;
            ultimo.siguiente = null;
        } else {
            Nodo actual = obtenerNodo(i);
            Nodo sig = actual.siguiente;
            Nodo anterior = actual.anterior;
            anterior.siguiente = sig;
            sig.anterior = anterior;
        }
        size--;
    }

    public void modificarPosicion(int indice, T elem) {
        Nodo actual = obtenerNodo(indice);
        actual.elem = elem;
    }

    private Nodo obtenerNodo(int indice) {
        Nodo actual = primero;
        for (int j = 0; j < indice; j++) {
            actual = actual.siguiente;
        }
        return actual;
    }

    public ListaEnlazada(ListaEnlazada<T> lista) {
        if (lista == null) {
            return;
        }

        primero = new Nodo(lista.primero.elem);
        ultimo = new Nodo(lista.ultimo.elem);
        Nodo actual = primero;
        Nodo actualInNewList = lista.primero;
        size = 1;
        for (int i = 1; i < lista.size; i++) {
            actual.siguiente = new Nodo(actualInNewList.siguiente.elem);
            actualInNewList = actualInNewList.siguiente;
            actual = actual.siguiente;
            size++;
        }
    }
    
    @Override
    public String toString() {
        if (primero == null) {
            return "[]";
        }

        Nodo actual = primero;
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        while (actual != null) {
            sb.append(actual.elem);
            if (actual.siguiente != null) {
                sb.append(", ");
            }
            actual = actual.siguiente;
        }
        return sb.append("]").toString();
    }

    private class ListaIterador implements Iterador<T> {
        boolean first = true;
        Nodo nodoActual = null;

        public boolean haySiguiente() {
            if (first && size > 0) {
                return true;
            }

	        return nodoActual != null;
        }
        
        public boolean hayAnterior() {
            if (!first && nodoActual == null && size > 0) {
                return true;
            }

            else if (first) {
                return false;
            }

	        return nodoActual.anterior != null;
        }

        public T siguiente() {
            if (nodoActual == null) {
                nodoActual = primero;
            }

            if (nodoActual == primero) {
                first = false;
            }

	        T elem = nodoActual.elem;
            nodoActual = nodoActual.siguiente;
            return elem;
        }
        

        public T anterior() {
            if (!first && nodoActual == null) {
                nodoActual = ultimo;
            } else if (nodoActual != null && nodoActual.anterior == null) {
                first = true;
            } else {
                nodoActual = nodoActual.anterior;
            }

            return nodoActual.elem;
        }
    }

    public Iterador<T> iterador() {
        return new ListaIterador();
    }

}
