import java.util.Scanner;

public class Clase4 {
    public static void main(String args[]){
        Scanner sc = new Scanner(System.in);/*
        System.out.println("Ingrese texto a repetir.");
        String texto = sc.nextLine();
        System.out.println("Ingrese cantidad a repetir.");
        int cantidad = sc.nextInt();
        System.out.println(repetir(texto, cantidad));*/
        
        System.out.println("Primer numero");
        int num1 = sc.nextInt();
        System.out.println("segundo numero");
        int num2 = sc.nextInt();
        System.out.println("operacion a realizar: + ; - ; * ; /");
        String op = sc.next();
        System.out.println(resolver(num1, num2, op));
        sc.close();

    }
    public static String repetir(String variable, int cant){
        return variable.repeat(cant);
    }
    public static int resolver(int num1, int num2, String Operacion){
        
        switch(Operacion){
            case "+" : return num1+num2;

            case "-" : return num1-num2;
            
            case "*": return num1*num2;
            
            case "/": return Math.floorDiv(num1, num2);
            default: return 0;
        }
    }
    public static String resolver_ref(int num1, int num2, String operacion) {
        if (operacion.equals("sumar")) {
            return "Suma: " + num1+num2;
        }
        if(operacion.equals("restar")){
            return "Resta: " + (num1-num2);
        }
        if(operacion.equals("multiplicar")){
            return "Multi: " + num1*num2;
        }
        if(operacion.equals("dividir")){
            return "Divi: " + num1/num2;
        }      
        return "algo salio mal";
    }
}
