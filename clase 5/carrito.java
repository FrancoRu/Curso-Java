import java.util.ArrayList;

public class carrito {
    ArrayList<producto> listado;
    public carrito(){
        this.listado = new ArrayList<producto>();
    }
    public void AgregarItem(producto newProdu){
        this.listado.add(newProdu);
    }
    public double CalcularPrecio(){
        return this.listado.stream().mapToDouble(producto -> producto.getPrecio()).reduce(0, (a, b) -> a + b);
    }
}
