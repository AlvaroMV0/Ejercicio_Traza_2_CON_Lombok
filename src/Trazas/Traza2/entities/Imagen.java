package Trazas.Traza2.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@ToString
public class Imagen extends EntidadBaseTraza2 {

    private String denominacion;

}
