package Trazas.Traza1.entities;

import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class Provincia extends EntidadBaseTraza1 {
    @ToString.Exclude
    private final Pais pais; // pertenece a


    @ToString.Include(name = "pais")
    public String paisToString() {
        return pais.getNombre();
    }

}
