package Trazas.Traza1.entities;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@SuperBuilder
public class Empresa extends EntidadBaseTraza1 {

    @Builder.Default
    private final Set<Long> sucursalesId = new HashSet<>();
    private String razonSocial;
    private String cuit;
    private String logo;


    @Override
    public String toString() {
        String idStr = (this.getId() == null) ? "null" : String.format("%03d", this.getId());
        String sucursalesStr = sucursalesId.stream().map(String::valueOf).collect(Collectors.joining(",", "[", "]"));

        return String.format(
                "Empresa [ID: %s, Nombre: '%s', CUIT: '%s', Razón Social: '%s', Sucursales (IDs): %s]",
                idStr, this.getNombre(), this.cuit, this.razonSocial, sucursalesStr
        );
    }

    public void addSucursalId(Long sucursalId) {
        Objects.requireNonNull(sucursalId, "La sucursal que se está intentado agregar está vacía");
        this.sucursalesId.add(sucursalId);
    }


}