package Trazas.Traza2.entities;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@SuperBuilder()
public abstract class Articulo extends EntidadBaseTraza2 {
    private String denominacion;
    private Double precioVenta;


    //Relaciones
    @Builder.Default
    private Set<Imagen> imagenes = new HashSet<>();
    private UnidadMedida unidadMedida;


}
