package com.trabajo.inventario;

public class ListaString {
    public NodoString primero;
    public byte size;
    
    // Crea inventario vacio
    public ListaString() {
        this.primero = null;
        this.size = 0;
    }
    
    public boolean estaVacio() {
        return primero == null;
    }
    
    public void agregar(String data) {
        NodoString nuevo = new NodoString(data);
        size++;
        
        if(estaVacio()) {
            primero = nuevo;
        } else {
            NodoString actual = this.primero;
            
            while(actual.siguiente != null) {
                actual = actual.siguiente;
            }
            
            actual.siguiente = nuevo;
        }
    }
    
    public void eliminar(String data) {
        if(estaVacio()) {
            return;
        }
        
        if (primero.data.equals(data)) {
            size--;
            primero = primero.siguiente;
            return;
        }
        
        NodoString actual = primero;
        while(actual.siguiente != null 
                && !actual.siguiente.data.equals(data)) {
            actual = actual.siguiente;
        }
        
        // Elimina el producto.
        if(actual.siguiente.data.equals(data)) {
           size--;
           actual.siguiente = actual.siguiente.siguiente;
           return;
        }
        
        System.out.println("No se encontro el producto.");
    }
    
    // Devuelve un Item que coincida con el parametro "nombre".
    // Devuelve null si la lista esta vacia o si no encuentra el producto.
    public boolean contiene(String data) {
        if(estaVacio())
            return false;

        NodoString actual = primero;
        while(actual != null) {
            if(data.equals(actual.data))
                return true;
            
            actual = actual.siguiente;
        }
        
        return false;
    }
    
    public void mostrar(boolean modoLista) {
        if(estaVacio()) {
            return;
        }
        
        NodoString actual = primero;
        byte i = 1;
        if(modoLista) System.out.println(i + ". " + actual.data);
        else System.out.println(actual.data);
        
        while(actual.siguiente != null) {
            actual = actual.siguiente;
            i++;
            if(modoLista) System.out.println(i + ". " + actual.data);
            else System.out.println(actual.data);
        }
    }
    
    // Calcula el tamaño en bytes de la lista
    public short calcularTamaño() {
        if(estaVacio()) {
            return 0;
        }
        
        NodoString actual = primero;
        short tam = (short) (actual.data.length() * 2);
        
        while(actual.siguiente != null) {
            actual = actual.siguiente;
            tam += (short) (actual.data.length() * 2);
        }
        return tam;
    }
}
