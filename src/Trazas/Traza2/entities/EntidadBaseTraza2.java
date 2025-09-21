package Trazas.Traza2.entities;

import Trazas.EntidadBase;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@SuperBuilder
class EntidadBaseTraza2 extends EntidadBase {
    private String denominacion;
}
