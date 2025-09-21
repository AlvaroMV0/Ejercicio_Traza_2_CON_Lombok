package Trazas.Traza1.entities;

import Trazas.EntidadBase;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@SuperBuilder
class EntidadBaseTraza1 extends EntidadBase {
    private String nombre;
}
