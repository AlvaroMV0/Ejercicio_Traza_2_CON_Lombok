package Trazas.Traza2.entities;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@ToString
public class ArticuloManufacturadoDetalle extends EntidadBaseTraza2 {
    private int cantidad;

    //Relaciones
    private ArticuloInsumo articuloInsumo;
}
