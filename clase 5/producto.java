import java.lang.reflect.Constructor;

public class producto {
    private String nombre;
    private double precio; 
    public producto(String nom, double precio){
        this.nombre = nom;
        this.precio = precio;
    }
    public String getNombre(){
        return this.nombre;
    }
    public double getPrecio(){
        return this.precio;
    }
}
