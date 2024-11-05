package com.trabajo.inventario;

import java.util.Scanner;

/**
 *
 * @author claud
 */
public class Validador {
    
    private static Scanner sc = new Scanner(System.in);
    
    // No hay necesidad de crear una instancia de esta clase
    // por lo tanto es privada.
    private Validador(){};
    
    // Valida un byte dentro del rango [min, max]
    public static byte ingresarByte(byte min, byte max) {
        boolean flag = false;
        byte b = 0;
        
        do {
            System.out.print("> ");
            if(sc.hasNextByte()) {
               b = sc.nextByte();
               
               if(b >= min && b <= max) {
                   flag = true;
               } else {
                   System.out.println("Error: Valor no esta dentro del rango " +
                           min + ", " + max + ".");
               }
            }
            else {
                System.out.println("Error: Valor incorrecto.");
                // Descarta la entrada incorrecta.
                sc = new Scanner(System.in);
            }
        } while(!flag);
        
        // Descarta el salto de linea
        sc.nextLine();
        return b;
    }
    
    public static int ingresarInt(int min, int max) {
        boolean flag = false;
        int i = 0;
        
        do {
            System.out.print("> ");
            
            // Comprueba si hay un int en el Scanner.
            // Si hay letras: devuelve false
            // Si hay numeros (dentro del rango de un int): devuelve true
            if(sc.hasNextInt()) {
                
               // Guarda el int en i
               i = sc.nextInt();
               
               // Verifica que i este dentro del rango [min, max]
               if(i >= min && i <= max) {
                   flag = true;
               } else {
                   System.out.println("Error: Valor no esta dentro del rango " +
                           min + ", " + max + ".");
               }
            } else {
                System.out.println("Error: Valor incorrecto.");
                // Descarta la entrada incorrecta
                sc = new Scanner(System.in);
            }
        } while(!flag);
        
        // Descarta el salto de linea
        sc.nextLine();
        return i;
    }
    
    // Admite solo letras y espacios si conNumeros = false.
    // Admite numeros, letras y espacios si conNumeros = true.
    public static String ingresarString(byte largo, boolean conNumeros) {
        boolean flag;
        String str;
        
        do {
            flag = true;
            System.out.print("> ");
            str = sc.nextLine();
            
            // Elimina los espacios blancos al principio y al final
            str = str.trim();
            
            if(str.isEmpty()) {
                flag = false;
                System.out.println("Error: No se ingreso el dato.");
            } else if(str.length() > largo) {
                flag = false;
                System.out.println("Error: El dato supera el limite de caracteres ("
                        + str.length() + "/"+ largo +").");
            } else {
                // Recorre el string caracter por caracter
                for(int i = 0; i < str.length(); i++) {
                    // Caracter en la posicion i del string.
                    char c = str.charAt(i);
                    
                    if(conNumeros) {
                        // Permitir letras, numeros y espacios
                        if(!Character.isLetterOrDigit(c) && !Character.isWhitespace(c)) {
                            flag = false;
                            break;
                        }
                    } else {
                        // Permitir letras y espacios
                        if(!Character.isLetter(c) && !Character.isWhitespace(c)) {
                            flag = false;
                            break;
                        }
                    }
                }
                
                if(!flag) {
                    System.out.println("Error: Hay caracteres invalidos.");
                }
            }
        } while(!flag);
        
        return str;
    }

    public static byte ingresarEstilo() {        
        System.out.println("1. Casual    | 5. Urbano");
        System.out.println("2. Formal    | 6. Escolar");
        System.out.println("3. Deportivo | 7. Trabajo");
        System.out.println("4. Outdoor   | 8. Ropa interior");
        return ingresarByte((byte)0, (byte)8);
    }
    
    public static String obtenerEstilo(byte estilo) {
        switch(estilo) {
            case 1: return "Casual";
            case 2: return "Formal";
            case 3: return "Deportivo";
            case 4: return "Outdoor";
            case 5: return "Urbano";
            case 6: return "Escolar";
            case 7: return "Trabajo";
            case 8: return "Ropa interior";
            default: return "Casual";
        }
    }
    
    public static String ingresarTipoPrenda(ListaString lista) {
        lista.mostrar(true);
        System.out.println("0. Agregar un tipo de prenda");
        byte opcion = Validador.ingresarByte((byte)0, lista.size);
        NodoString actual = lista.primero;
        if(opcion == 0) {
            registrarTipoPrenda(lista);
            while(actual.siguiente != null) actual = actual.siguiente;
            
            return actual.data;
        } else {
            for(byte i = 1; i < opcion; i++) actual = actual.siguiente;
            return actual.data;
        }
    }
    
    public static String registrarTipoPrenda(ListaString lista)  {
        System.out.println("Ingrese el tipo de prenda:");
        String tipoNuevo;
        boolean flag = false;
        
        do {
            tipoNuevo = Validador.ingresarString((byte)25, false);
            if(!lista.contiene(tipoNuevo)) {
                lista.agregar(tipoNuevo);
                System.out.println("El tipo de prenda se agrego con exito.");
                flag = true;
            } else {
                System.out.println("El tipo de prenda \""
                        + tipoNuevo + "\" ya existe");
            }
        } while(!flag);
        
        return tipoNuevo;
    }
}