import Trazas.Traza1.entities.Sucursal;
import Trazas.Traza2.entities.Articulo;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@ToString
public class SucursalArticulo {
    private final Sucursal sucursal;
    private final Articulo articulo;
    @Setter
    private int stock;

    public SucursalArticulo(Sucursal sucursal, Articulo articulo, int stock) {
        this.sucursal = sucursal;
        this.articulo = articulo;
        this.stock = stock;
    }


}