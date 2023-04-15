import java.util.Scanner;

class Guia1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Ingrese un número entero: ");
        int numero1 = sc.nextInt();
        System.out.print("Ingrese un número entero: ");
        int numero2 = sc.nextInt();
        System.out.println("Elija que hacer:");
        System.out.println("1: impares");
        System.out.println("2: pares");
        System.out.println("3: Todos");
        System.out.println("4: Invertir");
        sc.close();
        int des = sc.nextInt();
        switch(des){
            case 1:  rangeNotPair(numero1, numero2);
            break;
            case 2: rangePair(numero1, numero2);
            break;
            case 3: range(numero1, numero2);
            break;
            case 4: inverted(numero1, numero2);
            break;
        }
    }
    public static void range(int a, int b){
        System.out.println("Numeros orden normal...");
        for(int i = a; i<=b ; i++){
            System.out.println("Numero: "+i);
        }
    }
    public static void inverted(int a, int b){
        System.out.println("Numeros orden invertido...");
        for(int i = b; i>= a ; i--){
            System.out.println("Numero: "+i);
        }
    }
    public static void rangePair(int a, int b){
        System.out.println("Numeros pares...");
        for(int i = a; i<= b ; i++){
            if(i%2==0) System.out.println("Numero: "+i);
        }
    }
    public static void rangeNotPair(int a, int b){
        System.out.println("Numeros impares...");
        for(int i = a; i<= b ; i++){
            if(i%2!=0) System.out.println("Numero: "+i);
        }
    }
}