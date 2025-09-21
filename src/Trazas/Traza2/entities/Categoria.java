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
public class Categoria extends EntidadBaseTraza2 {
    private String denominacion;

    // Relaciones
    @Builder.Default
    @ToString.Exclude
    private Set<Articulo> articulos = new HashSet<>();


    @ToString.Include(name = "articulos")
    public String articulosToString() {
        return articulos.stream()
                .map(Articulo::getDenominacion)
                .collect(Collectors.joining(",","[","]"));

    }



    public void addArticulo(Articulo articulo){
        articulos.add(articulo);
    }

}
