package com.trabajo.inventario;

public class ListaString {
    public NodoString primero;
    public byte size;
    
    // Crea inventario vacio
    public ListaString() {
        this.primero = null;
        this.size = 0;
    }
    
    /**
     * Verifica si la lista esta vacia
     * 
     * @return true si esta vacia, false si existe el primer elemento
     */
    public boolean estaVacio() {
        return primero == null;
    }
    
    /**
     * Agrega un elemento al final de la lista
     * 
     * @param data el elemento a agregar
     */
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
    
    /**
     * Eliminar un elemento de la lista que coincida con el parametro
     * 
     * @param data el elemento a eliminar
     */
    public void eliminar(String data) {
        if(estaVacio()) {
            return;
        }
        
        NodoString actual = primero;
        while(actual != null) {
            
            // Elimina el producto si coincide
            if(actual.data.equals(data)) {
               size--;
               actual = actual.siguiente;
               return;
            }
            
            // Si no coincide, avanza al siguiente
            actual = actual.siguiente;
        }
        
        // Si recorre toda la lista y no lo encuentra, muestra el mensaje
        System.out.println("No se encontro el producto.");
    }
    
    /**
     * Revisa si existe un elemento dentro de la lista
     * 
     * @param data  el elemento a comparar
     * @return      true si existe, false si no existe o la lista esta vacia
     */
    // 
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
    
    /**
     * Muestra los elementos de la lista
     * 
     * @param modoLista agrega un numero de lista al mensaje
     */
    public void mostrar(boolean modoLista) {
        if(estaVacio()) {
            return;
        }
        
        NodoString actual = primero;
        byte i = 1;
        
        while(actual != null) {
            i++;
            if(modoLista) System.out.println(i + ". " + actual.data);
            else System.out.println(actual.data);
            
            actual = actual.siguiente;
        }
    }
    
    /**
     * Calcula el tamaño en bytes de la lista
     * 
     * @return el tamaño en bytes
     */
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
