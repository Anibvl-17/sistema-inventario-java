package com.trabajo.inventario;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner; 

public class Main {
    
    private static ListaInventario inventario;
    private static ListaString tiposPrenda;
    private static ListaString historial;
    private static Scanner sc;

    public static void main(String[] args) {
        sc = new Scanner(System.in);
        
        generarHistorial("Programa iniciado","");
        
        cargarInventario();
        menuPrincipal();
    }
    
    public static void menuPrincipal() {
        byte opcion;
        byte cantidadProductos = inventario.size;
        // Variables utilizadas para buscar productos.
        String nombre;
        Item producto;
        
        do  {
            limpiarConsola();
            System.out.println("\t..:: Menu Principal ::..");
            System.out.println("Productos en inventario: " + cantidadProductos
                    + " ("+ inventario.calcularTamaño() +" bytes)");
            System.out.println("Ingrese una opcion:");
            System.out.println("1. Ver productos      | 6. Consultar tamanio producto");
            System.out.println("2. Agregar producto   | 7. Consultar talla");
            System.out.println("3. Modificar producto | 8. Consultar material");
            System.out.println("4. Eliminar producto  | 9. Registrar tipo de prenda");
            System.out.println("5. Listar por estilo  | 10. Mostrar historial");
            System.out.println("0. Salir");

            opcion = Validador.ingresarByte((byte)0, (byte)10);
            
            System.out.println();
            
            switch (opcion) {
                case 1 -> {
                    inventario.mostrar();
                    generarHistorial("Consultar", "Mostrar inventario");
                }
                case 2 -> {
                    agregarProducto();
                    // Actualiza la cantidad de productos
                    cantidadProductos = inventario.size;
                }
                case 3 -> {
                    System.out.println("Ingrese el nombre del producto");
                    nombre = Validador.ingresarString((byte)35, true);
                    producto = inventario.buscarPorNombre(nombre);
                    
                    if(producto == null) {
                        System.out.println("El producto no esta en el inventario");
                        pausa();
                        break;
                    }
                    
                    menuModificacion(producto);
                }
                case 4 -> {
                    eliminarProducto();
                    // Actualiza la cantidad de productos
                    cantidadProductos = inventario.size;
                }
                case 5 -> listarPorEstilo();
                case 6 -> {
                    System.out.println("Ingrese el nombre del producto");
                    nombre = Validador.ingresarString((byte)35, true);
                    producto = inventario.buscarPorNombre(nombre);
                    if(producto==null) {
                        System.out.println("El producto \""+ nombre
                                +"\" no esta en el inventario.");
                    } else {
                        System.out.println("El tamaño del producto es: "
                                + inventario.calcularTamañoProducto(producto) +
                                " bytes.");
                        generarHistorial("Consultar", "Tamanio de " + nombre);
                    }
                }
                case 7 -> consultarTalla();
                case 8 -> consultarMaterial();
                case 9 -> {
                    String nuevoTipo = Validador.registrarTipoPrenda(tiposPrenda);
                    generarHistorial("Agregar", "Tipo de prenda " + nuevoTipo);
                }
                case 10 -> historial.mostrar(false);
                default -> System.out.println("-- Fin del programa --");
            }
            
            // Pausa antes de regresar, siempre que la opción sea distinta de 0 (salir) y
            // distinta de 3 (menu modificar ya hace una pausa)
            if (opcion != 0 && opcion != 3) pausa();
            
        } while(opcion != 0);
    }

    public static void menuModificacion(Item producto) {
        byte opcion;
        
        do {
            limpiarConsola();
            System.out.println("..:: Modificar producto \"" 
                    + producto.getNombre() + "\" ::..");
            System.out.println("Ingrese el atributo que desea modificar: ");
            System.out.println("1. Nombre   | 5. Color");
            System.out.println("2. Precio   | 6. Marca");
            System.out.println("3. Talla    | 7. Tipo de Prenda");
            System.out.println("4. Material | 8. Estilo");
            System.out.println("0. Volver");

            opcion = Validador.ingresarByte((byte)0, (byte)8);
            switch (opcion) {
                case 1 -> { // Modificar nombre
                    // Validar existencia nombre
                    System.out.println("Ingrese el nuevo nombre");
                    String nombreActual = producto.getNombre();
                    String nombreNuevo = Validador.ingresarString((byte)35, true);
                    producto.setNombre(nombreNuevo);
                    System.out.println("Nombre actualizado.");
                    generarHistorial("Modificar", "Nombre de " + nombreActual + " actualizado a " + nombreNuevo);
                }
                case 2 -> { // Modificar precio
                    System.out.println("Ingrese el nuevo precio");
                    int precioActual = producto.getPrecio();
                    int precioNuevo = Validador.ingresarInt(0, 100000000);
                    producto.setPrecio(precioNuevo);
                    System.out.println("Precio actualizado.");
                    generarHistorial("Modificar", "Precio de " + producto.getNombre() + " ($"+precioActual+") actualizado a $" + precioNuevo);
                }
                case 3 -> { // Modificar talla
                    System.out.println("Ingrese la nueva talla:");
                    byte tallaNueva = Validador.ingresarByte((byte)0, (byte)120);
                    byte tallaActual = producto.getTalla();
                    producto.setTalla(tallaNueva);
                    System.out.println("Talla actualizado.");
                    generarHistorial("Modificar", "Talla de " + producto.getNombre() + " ("+tallaActual+") actualizada a " + tallaNueva);
                }
                case 4 -> { // Modificar material
                    System.out.println("Ingrese el nuevo material:");
                    String materialActual = producto.getMaterial();
                    String materialNuevo = Validador.ingresarString((byte)30, false);
                    producto.setMaterial(materialNuevo);
                    System.out.println("Material actualizado.");
                    generarHistorial("Modificar", "Material de " + producto.getNombre() + " ("+materialActual+") actualizado a " + materialNuevo);
                }
                case 5 -> { // Modificar color
                    System.out.println("Ingrese el nuevo color:");
                    String colorActual = producto.getColor();
                    String colorNuevo = Validador.ingresarString((byte)30, false);
                    producto.setColor(colorNuevo);
                    System.out.println("Color actualizado.");
                    generarHistorial("Modificar", "Color de " + producto.getNombre() + " ("+colorActual+") actualizado a " + colorNuevo);
                }
                case 6 -> { // Modificar marca
                    System.out.println("Ingrese la nueva marca:");
                    String marcaActual = producto.getMarca();
                    String marcaNueva = Validador.ingresarString((byte)30, true);
                    producto.setMarca(marcaNueva);
                    System.out.println("Marca actualizada.");
                    generarHistorial("Modificar", "Marca de " + producto.getNombre() + " ("+marcaActual+") actualizada a " + marcaNueva);
                }
                case 7 -> { // Modificar tipo de prenda
                    System.out.println("Ingrese el nuevo tipo de prenda:");
                    String tipoActual = producto.getTipoPrenda();
                    String tipoNuevo = Validador.ingresarTipoPrenda(tiposPrenda);
                    producto.setTipoPrenda(tipoNuevo);
                    System.out.println("Tipo de prenda actualizado.");
                    generarHistorial("Modificar", "Tipo de prenda de " + producto.getNombre() + " ("+tipoActual+") actualizado a " + tipoNuevo);
                }
                case 8 -> { // Modificar estilo
                    System.out.println("Ingrese el nuevo estilo:");
                    byte estiloActual = producto.getEstilo();
                    byte estiloNuevo = Validador.ingresarEstilo();
                    producto.setEstilo(estiloNuevo);
                    System.out.println("Estilo actualizado");
                    generarHistorial("Modificar", "Estilo de " + producto.getNombre() 
                            + " ("+Validador.obtenerEstilo(estiloActual)+") actualizado a " 
                            + Validador.obtenerEstilo(estiloNuevo));
                }
                default -> { } // Volver 
            }
            
            if(opcion != 0) pausa();
        } while(opcion != 0);
    }
    
    public static void agregarProducto() {
        Item nuevoProducto = new Item();
        
        System.out.println("Ingrese los datos del producto: ");
        
        System.out.print("Nombre ");
        nuevoProducto.setNombre(Validador.ingresarString((byte)35, true));
        
        System.out.print("Precio ");
        nuevoProducto.setPrecio(Validador.ingresarInt(0, 10000000));
        
        System.out.print("Talla ");
        nuevoProducto.setTalla(Validador.ingresarByte((byte)0, (byte)100));
        
        System.out.print("Material ");
        nuevoProducto.setMaterial(Validador.ingresarString((byte)30, false));
        
        System.out.print("Color ");
        nuevoProducto.setColor(Validador.ingresarString((byte)30, false));
        
        System.out.print("Marca ");
        nuevoProducto.setMarca(Validador.ingresarString((byte)30, true));
        
        System.out.println("Tipo de Prenda:");
        nuevoProducto.setTipoPrenda(Validador.ingresarTipoPrenda(tiposPrenda));
        
        System.out.println("Estilo:");
        nuevoProducto.setEstilo(Validador.ingresarEstilo());
        
        inventario.agregar(nuevoProducto);
        System.out.println("El producto \"" + nuevoProducto.getNombre() + 
                "\" se ha agregado con exito.");
        
        generarHistorial("Agregar", "Producto " + nuevoProducto.getNombre());
    }
    
    public static void eliminarProducto() {
        
        if(inventario.estaVacio()) {
            System.out.println("El inventario esta vacio");
            pausa();
            return;
        }
        
        System.out.println("Ingrese el nombre del producto: ");
        String nombre = Validador.ingresarString((byte)35, true);
        Item producto = inventario.buscarPorNombre(nombre);
        
        if(producto == null) {
            System.out.println("El producto no esta en el inventario");
            pausa();
            return;
        }
        
        inventario.eliminar(producto);
        System.out.println("El producto se elimino con exito.");
        
        generarHistorial("Eliminar", nombre);
    }
    
    public static void consultarTalla() {
        
        if(inventario.estaVacio()) {
            System.out.println("El inventario esta vacio.");
            pausa();
            return;
        }
        
        System.out.println("Ingrese el nombre del producto: ");
        String nombre = Validador.ingresarString((byte)35, true);
        Item producto = inventario.buscarPorNombre(nombre);
        
        if(producto == null) {
            System.out.println("El producto no esta en el inventario.");
            pausa();
            return;
        }
        
        System.out.println("La talla del producto \""+nombre+"\" es "
                + producto.getTalla() + ".");
        
        generarHistorial("Consultar", "Talla de " + nombre);
    }
   
    public static void consultarMaterial() {
        if(inventario.estaVacio()) {
            System.out.println("El inventario esta vacio.");
            pausa();
            return;
        }
        
        System.out.println("Ingrese el nombre del producto: ");
        String nombre = Validador.ingresarString((byte)35, true);
        Item producto = inventario.buscarPorNombre(nombre);
        
        if(producto == null) {
            System.out.println("El producto no esta en el inventario.");
            pausa();
            return;
        }
        
        System.out.println("El material del producto \""+nombre+"\" es "
                + producto.getMaterial() + ".");
        
        generarHistorial("Consultar", "Material de " + nombre);
    }
    
    public static void listarPorEstilo() {
        if(inventario.estaVacio()) {
            System.out.println("El inventario esta vacio.");
            pausa();
            return;
        }
        
        System.out.println("Ingrese el estilo:");
        System.out.println("1. Casual    | 5. Urbano");
        System.out.println("2. Formal    | 6. Escolar");
        System.out.println("3. Deportivo | 7. Trabajo");
        System.out.println("4. Outdoor   | 8. Ropa interior");
        byte estilo = Validador.ingresarByte((byte)1, (byte)8);
        
        NodoItem actual = inventario.primero;
        
        while(actual != null) {
            if (actual.data.getEstilo() == estilo) {
                mostrarProducto(actual.data);
            }
            
            actual = actual.siguiente;
        }
        
        generarHistorial("Consultar", "Productos listados por estilo " + Validador.obtenerEstilo(estilo));
    }

    public static void generarHistorial(String titulo, String descripcion) {
        if(historial == null) historial = new ListaString();
        
        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String fecha = LocalDateTime.now().format(formatoFecha);
        
        String mensaje ="["+fecha+"] ";
        if(descripcion.isBlank()) mensaje += titulo;
        else mensaje += titulo + ": " + descripcion;
        
        historial.agregar(mensaje);
    }
    
    public static void cargarInventario()  {
        inventario = new ListaInventario();
        
        Item item1 = new Item("Producto 1", 100, (byte)10, "Mat1", "Col1", "Mar1",
            "Tip1", (byte)1);
        Item item2 = new Item("Producto 2", 200, (byte)20, "Mat2", "Col2", "Mar2",
            "Tip2", (byte)2);
        Item item3 = new Item("Producto 3", 300, (byte)30, "Mat3", "Col3", "Mar3",
            "Tip3", (byte)3);
        Item item4 = new Item("Producto 4", 400, (byte)40, "Mat4", "Col4", "Mar4",
            "Tip4", (byte)4);
        
        inventario.agregar(item1);
        inventario.agregar(item2);
        inventario.agregar(item3);
        inventario.agregar(item4);
        
        tiposPrenda = new ListaString();
        tiposPrenda.agregar("Pantalon");
        tiposPrenda.agregar("Vestido");
        tiposPrenda.agregar("Polera");
        tiposPrenda.agregar("Blusa");
        tiposPrenda.agregar("Falda");
    }
    
    // Pendiente
    public static void guardarInventario() {
        // TODO Guardar inventario
    }
    
    public static void limpiarConsola() {
        System.out.println("\n\n\n\n\n\n\n\n\n");
    }
    
    public static void pausa() {
        System.out.println("Presione Enter para volver...");
        sc.nextLine();
    }
    
    public static void mostrarProducto(Item producto) {
        System.out.println("----");

        System.out.print("Nombre: " + producto.getNombre());
        System.out.println("\tPrecio: " + producto.getPrecio());
        System.out.print("Talla: " + producto.getTalla());
        System.out.println("\tMaterial: " + producto.getMaterial());
        System.out.print("Color: " + producto.getColor());
        System.out.println("\tMarca: " + producto.getMarca());
        System.out.print("Tipo de Prenda: " + producto.getTipoPrenda());
        System.out.println("\tEstilo: " + Validador.obtenerEstilo(producto.getEstilo()));
    }
}