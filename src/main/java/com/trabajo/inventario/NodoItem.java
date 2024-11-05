package com.trabajo.inventario;

public class NodoItem {
    public Item data;
    public NodoItem siguiente;
    
    public NodoItem(Item data) {
        this.data = data;
        this.siguiente = null;
    }
}