package com.trabajo.inventario;

public class NodoString {
    String data;
    NodoString siguiente;
    
    NodoString(String data) {
        this.data = data;
        this.siguiente = null;
    }
}