import java.util.Arrays;
class Guia3{
    public static void main(String args[]) {
        int[] numeros = {5, 3, 1, 4, 2};
        int[] numerosOrdenados = orden(numeros, 1);
        System.out.println(Codificacion("hola que tal", 1));       
        System.out.println(DeCodificacion("ipmb!rvf!ubm", 1));  
    }
    public static int coincidencias(char letra, String palabra) {
        int contCoincidencias = 0;
        for(int i=0; i< palabra.length(); i++){
            if(palabra.charAt(i) == letra) contCoincidencias++;
        }
        return contCoincidencias;
    }
    public static int[] orden(int vector[], int opc){
        switch(opc){
            case 0:{
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
            case 1: {
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
/*Utilizando solo tipos primitivos, String (solo usar método length), vectores,
iteraciones simples y condicionales, genere una clase por ejercicio que posea los
siguientes métodos:
a. Dado un String y una letra, que cuente la cantidad de apariciones de la letra en
el String
b. Dados 3 números y un orden (ascendente o decreciente) que ordene los
mismos y los retorne en un vector de 3
c. dado un vector de números, y un número X, que sume todos los números > X y
retorne el resultado 
Genere una clase que tenga los métodos para realizar la codificación y decodificación
de un string, dado un número de desplazamiento.
● Por ejemplo, con desplazo de 1:
“hola que tal” -> “ipmbarvfaubm”
h -> i
o -> p
● con desplazo de 2
“hola que tal” -> “jqncbswgbvcn”
h -> j
o -> q*/