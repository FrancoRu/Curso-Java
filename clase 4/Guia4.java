import java.util.Arrays;
import java.util.Scanner;
import java.util.ArrayList;

class Guia4{
    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Ingrese opcion deseada: ");
        System.out.println("1: Ordenar un vector");
        System.out.println("2: Codificar o Decodificar palabras");
        int x = sc.nextInt();
        switch(x){
            case 1: Ordenar();
            break;
            case 2: Codi();
            break;
        }
        sc.close();
    }
    public static void Ordenar() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Ingrese tamaño del vector. ");
        ArrayList<int> vector = new ArrayList<String>();
        int tam = sc.nextInt();
        for(int i=0; i<tam; i++){
            System.out.println("Ingrese valor "+(i+1)+" del vector. ");
        }

        System.out.println("Ingrese opcion deseada: ");
        System.out.println("Ingrese opcion deseada: ");
        System.out.println("a: Ordenar de forma ascendente.");
        System.out.println("d: Ordenar de forma descendente.");
        String opc = sc.next();
        
        sc.close();
    }
    public static void Codi() {
        
    }
    public static int coincidencias(char letra, String palabra) {
        int contCoincidencias = 0;
        for(int i=0; i< palabra.length(); i++){
            if(palabra.charAt(i) == letra) contCoincidencias++;
        }
        return contCoincidencias;
    }
    public static int[] orden(int vector[], String opc){
        switch(opc){
            case "a":{
            for(int i=0; i<vector.length; i++){
                for(int j=i; j<vector.length; j++){
                   if(vector[i]>vector[j]){
                    int aux = vector[j];
                    vector[j] = vector[i];
                    vector[i] = aux;
                   } 
                }
            }
            return vector;
            }
            case "d": {
                for(int i=0; i<vector.length; i++){
                    for(int j=i; j<vector.length; j++){
                       if(vector[i]<vector[j]){
                        int aux = vector[j];
                        vector[j] = vector[i];
                        vector[i] = aux;
                       } 
                    }
                }
                return vector;
            }
            default: return vector;
            
        }
    }
    public static int sumatoria(int vector[], int x) {
        int contador = 0;
        for(int i=0; i<vector.length; i++){
            if(vector[i]> x) contador+= vector[i];
        }
        return contador;
    }
    public static String Codificacion(String palabra, int desplazamiento) {
        char letra;
        int ascii;
        String newPalabra = "";
        for(int i=0; i<palabra.length(); i++){
            letra = palabra.charAt(i);
            ascii = (int) letra;
            newPalabra += (char) (ascii+desplazamiento);
        }
        return newPalabra;
    }
    public static String DeCodificacion(String palabra, int desplazamiento) {
        char letra;
        int ascii;
        String newPalabra = "";
        for(int i=0; i<palabra.length(); i++){
            letra = palabra.charAt(i);
            ascii = (int) letra;
            newPalabra += (char) (ascii-desplazamiento);
        }
        return newPalabra;
    }
}
/*Tomando los Ejercicios de la clase anterior
a. haga un main, donde por parámetro ponga 3 números y una letra que
represente ascendente o descendente y los muestre ordenados por tal criterio
b. haga lo mismo, pero solicitando los parámetros de a uno por consola
c. lo mismo, pero usando los parámetros si hay alguno (como en a) y haciendo (b)
si no detecta ninguno. Vea si con una función puede evitar repetir código */