package com.trabajo.inventario;

public class ListaInventario {
    public NodoItem primero;
    public byte size;
    
    // Crea inventario vacio
    public  ListaInventario() {
        this.primero = null;
        this.size = 0;
    }
    
    public boolean estaVacio() {
        return primero == null;
    }
    
    public void agregar(Item producto) {
        NodoItem nuevo = new NodoItem(producto);
        size++;
        
        if(estaVacio()) {
            primero = nuevo;
            return;
        }

        NodoItem actual = this.primero;
        
        while(actual.siguiente != null) {
            actual = actual.siguiente;
        }

        actual.siguiente = nuevo;
    }
    
    public void eliminar(Item producto) {
        if(estaVacio()) {
            return;
        }
        
        if (primero.data.equals(producto)) {
            size--;
            primero = primero.siguiente;
            return;
        }
        
        NodoItem actual = primero;
        while(actual.siguiente != null && !actual.siguiente.data.equals(producto)) {
            actual = actual.siguiente;
        }
        
        // Elimina el producto.
        if(actual.siguiente.data.equals(producto)) {
           size--;
           actual.siguiente = actual.siguiente.siguiente;
           return;
        }
        
        System.out.println("No se encontro el producto.");
    }
    
    // Devuelve un Item que coincida con el parametro "nombre".
    // Devuelve null si la lista esta vacia o si no encuentra el producto.
    public Item buscarPorNombre(String nombre) {
        if(estaVacio())
            return null;

        NodoItem actual = primero;
        while(actual != null) {
            if (actual.data.getNombre().equals(nombre))
                return actual.data;
            
            actual = actual.siguiente;
        }
        
        return null;
    }
    
    public void mostrar() {
        if(estaVacio()) {
            return;
        }
        
        NodoItem actual = primero;
        while(actual != null) {
            System.out.println();
            System.out.println("..:: " + actual.data.getNombre() + " ::..");
            System.out.println("Precio: " + actual.data.getPrecio());
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
    
    // Calcula el tamaño en bytes de la lista
    public short calcularTamaño() {
        if(estaVacio()) {
            return 0;
        }
        
        // Calcula el tamaño del primer elemento
        NodoItem actual = primero;
        short tamaño = calcularTamañoProducto(primero.data);
        
        // Si existen más elementos, los calcula y los agrega a tamaño
        while(actual.siguiente != null) {
            actual = actual.siguiente;
            tamaño += calcularTamañoProducto(actual.data);
        }
        
        return tamaño;
    }
    
    // Calcula el tamaño en bytes del producto, incluyendo el tamaño de la
    // referencia del nodo (constante, 8 bytes)
    public short calcularTamañoProducto(Item producto) {
        if(producto == null) {
            return 0;
        }
        
        // Inicia con el tamaño de la referencia del nodo (8), y los datos
        // "precio" (4), "talla" (1) y "estilo" (1)
        short tamaño = 14;
        
        // Calcula los tamaños de cada String.
        // Tamaño de un String: cantidad de caracteres * 2 bytes.
        tamaño += (short) producto.getNombre().length() * 2;
        tamaño += (short) producto.getMaterial().length() * 2;
        tamaño += (short) producto.getColor().length() * 2;
        tamaño += (short) producto.getMarca().length() * 2;
        tamaño += (short) producto.getTipoPrenda().length() * 2;
        
        return tamaño;
    }
}