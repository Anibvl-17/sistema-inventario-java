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
        
        generarHistorial((byte)0, "");
        
        cargarInventario();
        menuPrincipal();
    }
    
    public static void menuPrincipal() {
        byte opcion;
        byte cantidadProductos;
        Item item;
        
        do  {
            cantidadProductos = inventario.size;
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
                case 1:
                    inventario.mostrar();
                    generarHistorial((byte)3, "Mostrar inventario");
                    break;
                case 2: agregarProducto(); break;
                case 3:
                    item = buscarProducto();
                    
                    if (item != null) menuModificacion(item);
                    break;
                case 4: eliminarProducto(); break;
                case 5: listarPorEstilo(); break;
                case 6: 
                    item = buscarProducto();
                    short tamanio = inventario.calcularTamañoProducto(item);
                    
                    if(item != null) {
                        
                        
                        System.out.println("El tamanio del producto es: " 
                                + tamanio + " bytes.");
                        
                        
                        generarHistorial((byte)3, "Tamanio de " + item.getNombre()
                                + " = " + tamanio);
                    }
                    break;
                case 7: consultarTalla(); break;
                case 8: consultarMaterial(); break;
                case 9:
                    String nuevoTipo = Validador.registrarTipoPrenda(tiposPrenda);
                    generarHistorial((byte)1, "Tipo de prenda \"" + nuevoTipo + "\"");
                    break;
                case 10: historial.mostrar(false); break;
                default: System.out.println("..::: Fin del programa :::.."); break;
            }
            
            // Pausa antes de regresar, siempre que la opción sea distinta de 0 (salir) y
            // distinta de 3 (menu modificar ya hace una pausa)
            if (opcion != 0 && opcion != 3) pausa();
            
        } while(opcion != 0);
    }

    public static void menuModificacion(Item item) {
        byte opcion;
        
        do {
            limpiarConsola();
            System.out.println("..:: Modificar producto \"" 
                    + item.getNombre() + "\" ::..");
            System.out.println("Ingrese el atributo que desea modificar: ");
            System.out.println("1. Nombre   | 5. Color");
            System.out.println("2. Precio   | 6. Marca");
            System.out.println("3. Talla    | 7. Tipo de Prenda");
            System.out.println("4. Material | 8. Estilo");
            System.out.println("0. Volver");

            opcion = Validador.ingresarByte((byte)0, (byte)8);
            
            switch (opcion) {
                case 1: // Nombre
                    // Validar existencia nombre
                    System.out.println("Ingrese el nuevo nombre");
                    
                    String nombreActual = item.getNombre();
                    String nombreNuevo = Validador.ingresarString((byte)35, true);
                    item.setNombre(nombreNuevo);
                    
                    System.out.println("Nombre actualizado.");
                    generarHistorial((byte)4, "Nombre de \"" + nombreActual 
                            + "\" actualizado a \"" + nombreNuevo + "\"");
                    break;
                case 2: // Precio
                    System.out.println("Ingrese el nuevo precio");
                    
                    int precioActual = item.getPrecio();
                    int precioNuevo = Validador.ingresarInt(0, 1000000000);
                    item.setPrecio(precioNuevo);
                    
                    System.out.println("Precio actualizado.");
                    generarHistorial((byte)4, "Precio de \"" + item.getNombre() 
                            + "\" ($"+precioActual+") actualizado a $" + precioNuevo);
                    break;

                case 3: // Talla
                    System.out.println("Ingrese la nueva talla:");
                    
                    byte tallaNueva = Validador.ingresarByte((byte)0, (byte)120);
                    byte tallaActual = item.getTalla();
                    item.setTalla(tallaNueva);
                    
                    System.out.println("Talla actualizada.");
                    generarHistorial((byte)4, "Talla de \"" + item.getNombre() 
                            + "\" (" + tallaActual + ") actualizada a " + tallaNueva);
                    break;

                case 4: // Material
                    System.out.println("Ingrese el nuevo material:");
                    
                    String materialActual = item.getMaterial();
                    String materialNuevo = Validador.ingresarString((byte)30, false);
                    item.setMaterial(materialNuevo);
                    
                    System.out.println("Material actualizado.");
                    generarHistorial((byte)4, "Material de \"" + item.getNombre() 
                            + "\" (" + materialActual + ") actualizado a " + materialNuevo);
                    break;

                case 5: // Color
                    System.out.println("Ingrese el nuevo color:");
                    
                    String colorActual = item.getColor();
                    String colorNuevo = Validador.ingresarString((byte)30, false);
                    item.setColor(colorNuevo);
                    
                    System.out.println("Color actualizado.");
                    generarHistorial((byte)4, "Color de \"" + item.getNombre() 
                            + "\" (" + colorActual + ") actualizado a " + colorNuevo);
                    break;
                    
                case 6: // Marca
                    System.out.println("Ingrese la nueva marca:");
                    
                    String marcaActual = item.getMarca();
                    String marcaNueva = Validador.ingresarString((byte)30, true);
                    item.setMarca(marcaNueva);
                    
                    System.out.println("Marca actualizada.");
                    generarHistorial((byte)4, "Marca de \"" + item.getNombre() 
                            + "\" (" + marcaActual + ") actualizada a " + marcaNueva);
                    break;
                    
                case 7: // Prenda
                    System.out.println("Ingrese el nuevo tipo de prenda:");

                    String tipoActual = item.getTipoPrenda();
                    String tipoNuevo = Validador.ingresarTipoPrenda(tiposPrenda);
                    item.setTipoPrenda(tipoNuevo);

                    System.out.println("Tipo de prenda actualizado.");
                    generarHistorial((byte)4, "Tipo de prenda de \"" + item.getNombre() 
                            + "\" ("+tipoActual+") actualizado a " + tipoNuevo);
                    break;

                case 8: // Estilo
                    System.out.println("Ingrese el nuevo estilo:");

                    byte estiloActual = item.getEstilo();
                    byte estiloNuevo = Validador.ingresarEstilo();
                    item.setEstilo(estiloNuevo);
                    
                    System.out.println("Estilo actualizado");
                    generarHistorial((byte)4, "Estilo de \"" + item.getNombre() 
                            + "\" ("+Validador.obtenerEstilo(estiloActual)+") actualizado a " 
                            + Validador.obtenerEstilo(estiloNuevo));
                    break;
                    
                default: { } break; // Volver 
            }
            
            if(opcion != 0) pausa();
            
        } while(opcion != 0);
    }
    
    public static void agregarProducto() {
        Item item = new Item();

        System.out.println("Ingrese los datos del producto: ");
        
        System.out.print("Nombre ");
        item.setNombre(Validador.ingresarString((byte)35, true));
        
        System.out.print("Precio ");
        item.setPrecio(Validador.ingresarInt(1, 1000000000));
        
        System.out.print("Talla ");
        item.setTalla(Validador.ingresarByte((byte)1, (byte)120));
        
        System.out.print("Material ");
        item.setMaterial(Validador.ingresarString((byte)25, false));
        
        System.out.print("Color ");
        item.setColor(Validador.ingresarString((byte)25, false));
        
        System.out.print("Marca ");
        item.setMarca(Validador.ingresarString((byte)25, true));
        
        System.out.println("Tipo de Prenda:");
        item.setTipoPrenda(Validador.ingresarTipoPrenda(tiposPrenda));
        System.out.println();
        
        System.out.println("Estilo:");
        item.setEstilo(Validador.ingresarEstilo());
        System.out.println("");
        
        inventario.agregar(item);
        System.out.println("El producto \"" + item.getNombre() + 
                "\" se ha agregado con exito.");
        
        generarHistorial((byte)1, "Producto \"" + item.getNombre() + "\".");
    }
    
    public static void eliminarProducto() {
        Item item = buscarProducto();
        
        if(item != null) {
            inventario.eliminar(item);
            System.out.println("El producto \"" + item.getNombre() + "\" se elimino con exito.");

            generarHistorial((byte)2, "Producto \"" + item.getNombre() + "\".");
        }
    }
    
    public static Item buscarProducto() {
        System.out.println("Ingrese el nombre del producto: ");
        String nombre = Validador.ingresarString((byte)35, true);
        
        return inventario.buscarPorNombre(nombre);
    }
    
    public static void consultarTalla() {
        Item item = buscarProducto();
        
        if(item != null) {
            System.out.println("La talla del producto \""+ item.getNombre() +
                "\" es " + item.getTalla() + ".");
        
            generarHistorial((byte)3, "Talla de \"" + item.getNombre()
                + "\" = " + item.getTalla());
        }
    }
   
    public static void consultarMaterial() {
        Item item = buscarProducto();
        
        if(item != null) {
            System.out.println("El material del producto \"" + item.getNombre() + "\" es "
                + item.getMaterial() + ".");
        
            generarHistorial((byte)3, "Material de \"" + item.getNombre() 
                + "\" = " + item.getMaterial());
        }
    }
    
    public static void listarPorEstilo() {
        if(inventario.estaVacio()) {
            System.out.println("El inventario esta vacio.");
            pausa();
            return;
        }
        
        System.out.println("Ingrese el estilo:");
        byte estilo = Validador.ingresarEstilo();
        
        NodoItem actual = inventario.primero;
        boolean existe = false;
        
        while(actual != null) {
            if (actual.data.getEstilo() == estilo) {
                mostrarProducto(actual.data);
                actual.toString();
                existe = true;
            }
            
            actual = actual.siguiente;
        }
        
        if (existe) {
            generarHistorial((byte)3, "Productos listados por estilo \"" 
                    + Validador.obtenerEstilo(estilo) + "\"");
        }
    }
    
    /**
     * Guarda las acciones realizadas en una ListaString a modo de historial
     * 
     * @param accion    0 = Inicio Programa; 1 = Agregar; 2 = Eliminar; 3 = Consultar;
     * @param detalle   detalle de la acción
     */
    public static void generarHistorial(byte accion, String detalle) {
        if(historial == null) historial = new ListaString();
        
        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String fecha = LocalDateTime.now().format(formatoFecha);
        String mensaje ="["+fecha+"] ";
        
        switch(accion) {
            case 0: mensaje += "Programa iniciado."; break;
            case 1: mensaje += "Agregar   : " + detalle; break;
            case 2: mensaje += "Eliminar  : " + detalle; break;
            case 3: mensaje += "Consultar : " + detalle; break;
            case 4: mensaje += "Modificar : " + detalle; break;
            default: mensaje += detalle; break;
        }
        
        historial.agregar(mensaje);
    }
    
    // Pendiente
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
    
    /**
     * Imprime saltos de linea para una mejor experiencia visual.
     */
    public static void limpiarConsola() {
        System.out.println("\n\n\n\n\n\n\n\n\n");
    }
    
    /**
     * Pausa el programa hasta que se presione la tecla Enter
     */
    public static void pausa() {
        System.out.print("\nPresione Enter para volver...");
        sc.nextLine();
    }
    
    /**
     * Muestra todos los detalles del Item
     * 
     * @param item elemento a mostrar
     */
    public static void mostrarProducto(Item item) {
        System.out.println("..:: " + item.getNombre() + " ::..");
        System.out.println("Precio: $" + item.getPrecio());
        System.out.println("Talla: " + item.getTalla());
        System.out.println("Material: " + item.getMaterial());
        System.out.println("Color: " + item.getColor());
        System.out.println("Marca: " + item.getMarca());
        System.out.println("Tipo de Prenda: " + item.getTipoPrenda());
        System.out.println("Estilo: " + Validador.obtenerEstilo(item.getEstilo()));
    }
}