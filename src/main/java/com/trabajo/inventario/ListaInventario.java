package com.trabajo.inventario;

public class ListaInventario {
    public NodoItem primero;
    public byte size;
    
    // Crea inventario vacio
    public  ListaInventario() {
        this.primero = null;
        this.size = 0;
    }
    
    /**
     * Verifica la existencia del primer elemento para determinar si la lista esta vacia.
     * 
     * @return true si existe el primero, false si no existe.
     */
    public boolean estaVacio() {
        return primero == null;
    }
    
    /**
     * Agrega un item al final de la lista.
     * 
     * @param item el item a agregar
     */
    public void agregar(Item item) {
        NodoItem nuevo = new NodoItem(item);
        size++;
        
        if(estaVacio()) {
            primero = nuevo;
            return;
        }

        NodoItem actual = primero;
        
        while(actual.siguiente != null) 
            actual = actual.siguiente;

        actual.siguiente = nuevo;
    }
    
    /**
     * Elimina el item especificado
     * 
     * @param item el item a eliminar
     */
    public void eliminar(Item item) {
        if(estaVacio()) {
            System.out.println("El inventario esta vacio.");
            return;
        }
        
        // Si el primero coincide con el item, actualizamos el primero
        if (primero.data.equals(item)) {
            primero = primero.siguiente;
            size--;
            return;
        }
        
        NodoItem actual = primero;
        
        while(actual.siguiente != null) {
            if(actual.siguiente.data.equals(item)) {
                actual.siguiente = actual.siguiente.siguiente;
                size--;
                return;
            }
            
            actual = actual.siguiente;
        }
    }
    
    /**
     * Obtiene el Item que coincide con el parametro.
     * 
     * @param nombre  el nombre del Item que se busca
     * @return        el Item encontrado, o null si la lista esta vacia
     *                 o no existe el elemento.
     */
    public Item buscarPorNombre(String nombre) {
        if(estaVacio()) {
            System.out.println("El inventario esta vacio.");
            return null;
        }

        NodoItem actual = primero;
        while(actual != null) {
            if (actual.data.getNombre().equalsIgnoreCase(nombre))
                return actual.data;
            
            actual = actual.siguiente;
        }
        
        System.out.println("El producto \"" + nombre + "\" no existe.");
        return null;
    }
    
    /**
     * Muestra todos los elementos de la lista.
     */
    public void mostrar() {
        if(estaVacio()) {
            System.out.println("El inventario esta vacio.");
            return;
        }
        
        NodoItem actual = primero;
        while(actual != null) {
            System.out.println();
            System.out.println("..:: " + actual.data.getNombre() + " ::..");
            System.out.println("Precio: $" + actual.data.getPrecio());
            System.out.println("Talla: " + actual.data.getTalla());
            System.out.println("Material: " + actual.data.getMaterial());
            System.out.println("Color: " + actual.data.getColor());
            System.out.println("Marca: " + actual.data.getMarca());
            System.out.println("Tipo de Prenda: " + actual.data.getTipoPrenda());
            String estilo = Validador.obtenerEstilo(actual.data.getEstilo());
            System.out.println("Estilo: " + estilo);
            actual = actual.siguiente;
        }
    }
    
    /**
     * Calcula el tamaño de la lista.
     * 
     * @return el tamaño en bytes.
     */
    public short calcularTamaño() {
        if(estaVacio()) {
            System.out.println("El inventario esta vacio.");
            return 0;
        }
        
        NodoItem actual = primero;
        short tamaño = (short) 0;

        while(actual != null) {
            tamaño += calcularTamañoProducto(actual.data);
            actual = actual.siguiente;
        }
        
        return tamaño;
    }

    /**
     * Calcula el tamaño en bytes del item, incluyendo el tamaño de la
     * referencia del nodo (constante, 8 bytes)
     * 
     * @param item  el item al que se le calculara el tamaño
     * @return      el tamaño en bytes
     */
    public short calcularTamañoProducto(Item item) {
        if(item == null) {
            return 0;
        }
        
        // Inicia con el tamaño de la referencia del nodo (8), y los datos
        // "precio" (4), "talla" (1) y "estilo" (1)
        short tamaño = 14;
        
        // Calcula los tamaños de cada String.
        // Tamaño de un String: cantidad de caracteres * 2 bytes.
        tamaño += (short) item.getNombre().length() * 2;
        tamaño += (short) item.getMaterial().length() * 2;
        tamaño += (short) item.getColor().length() * 2;
        tamaño += (short) item.getMarca().length() * 2;
        tamaño += (short) item.getTipoPrenda().length() * 2;
        
        return tamaño;
    }
}