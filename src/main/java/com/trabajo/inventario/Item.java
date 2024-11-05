package com.trabajo.inventario;

public class Item {
    private String nombre;      // Admite letras y numeros
    private int precio;         // [1, 1.000.000.000]
    private byte talla;         // [1, 127]
    private String material;    // Admite solo letras
    private String color;       // Admite solo letras
    private String marca;       // Admite letras y numeros
    private String tipoPrenda;  // Admite solo letras
    private byte estilo;        // [1, 8]
    
    // Constructor vacio para agregar los datos despues
    public Item() {}
    
    // Constructor
    public Item(String nombre, int precio, byte talla, String material,
                String color, String marca, String tipoPrenda, byte estilo) {
        this.nombre = nombre;
        this.precio = precio;
        this.talla = talla;
        this.material = material;
        this.color = color;
        this.marca = marca;
        this.tipoPrenda = tipoPrenda;
        this.estilo = estilo;
    }
    
    // Getters & Setters
    
    public String getNombre() {
        return this.nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public int getPrecio() {
        return this.precio;
    }
    
    public void setPrecio(int precio) {
        this.precio = precio;
    }
    
    public byte getTalla() {
        return this.talla;
    }
         
    public void setTalla(byte talla) {
        this.talla = talla;
    }
    
    public String getMaterial() {
        return this.material;
    }
    
    public void setMaterial(String material) {
        this.material = material;
    }
    
    public String getColor() {
        return this.color;
    }
    
    public void setColor(String color) {
        this.color = color;
    }
    
    public String getMarca() {
        return this.marca;
    }
    
    public void setMarca(String marca) {
        this.marca = marca;
    }
    
    public String getTipoPrenda() {
        return this.tipoPrenda;
    }
    
    public void setTipoPrenda(String tipoPrenda) {
        this.tipoPrenda = tipoPrenda;
    }
    
    public byte getEstilo() {
        return this.estilo;
    }
    
    public void setEstilo(byte estilo) {
        this.estilo = estilo;
    }
}