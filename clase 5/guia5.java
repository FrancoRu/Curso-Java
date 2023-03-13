import java.util.ArrayList;

class Guia5{
    public static void main(String args[]) {
        producto newProdu = new producto("Fafa", 12.5);
        System.out.println(newProdu.getNombre()+" "+newProdu.getPrecio());
    }
}






/*Implemente usted mismo el ejemplo visto en clase de “carrito de compras”: una clase
Producto (que pueda tener hasta 3 items), otra ItemCarrito, otra Carrito y otra
Descuento, con 2 subclases */