package Trazas.Traza2.entities;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@SuperBuilder
@ToString
public class ArticuloManufacturado extends Articulo {
    private String descripcion;
    private Integer tiempoEstimadoMinutos;
    private String preparacion;

    @Builder.Default
    @ToString.Exclude
    private Set<ArticuloManufacturadoDetalle> detalles = new HashSet<>();


    @ToString.Include(name = "detalles")
    public String detallesToString() {
        return detalles.stream().map(e -> {
            return String.format("{NombreInsumo= '%s', cantidad= '%d'}",
                    e.getArticuloInsumo().getDenominacion(),e.getCantidad());
        }).collect(Collectors.joining(",","[","]"));

    }

}
