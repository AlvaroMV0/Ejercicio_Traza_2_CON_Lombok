package Trazas.Traza1.entities;

import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@ToString(callSuper = true)
@SuperBuilder
public class Domicilio extends EntidadBaseTraza1 {

    private final Integer numero;
    private final Integer cp;
    @ToString.Exclude
    private Localidad localidad;


    @ToString.Include(name = "localidad")
    public String localidadToString() {
        return this.getNombre();
    }

    //package-private
    void setLocalidad(Localidad localidad) {
        this.localidad = localidad;
    }
}
