package Trazas.Traza1.entities;

import Trazas.IRepository;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class EmpresaService {
    private final IRepository<Empresa> empresaRepository;
    private final IRepository<Sucursal> sucursalRepository;

    public EmpresaService(IRepository<Empresa> empresaRepository, IRepository<Sucursal> sucursalRepository) {
        this.empresaRepository = empresaRepository;
        this.sucursalRepository = sucursalRepository;
    }

    public Set<Sucursal> getSucursalesById(Long empresaId) {

        Empresa empresa = empresaRepository.findById(empresaId).orElseThrow(() -> new RuntimeException("NO se ha encontrado una empresa con ese ID"));

        return empresa.getSucursalesId().stream().map(id -> sucursalRepository.findById(id).orElse(null)).filter(Objects::nonNull).collect(Collectors.toSet());
    }


    public void addSucursalToEmpresa(Long empresaId, Long sucursalId) {


        Empresa empresa = empresaRepository.findById(empresaId).orElseThrow(() -> new RuntimeException("Empresa no encontrada con ID: " + empresaId));

        Sucursal sucursalToAdd = sucursalRepository.findById(sucursalId).orElseThrow(() -> new RuntimeException("Sucursal no encontrada con ID: " + sucursalId));

        // Si la sucursal a agregar está marcada como matriz, primero revisamos si ya existe una
        if (sucursalToAdd.isEs_Casa_Matriz()) {
            Optional<Sucursal> casaMatrizExistente = this.findCasaMatriz(empresaId);
            if (casaMatrizExistente.isPresent()) {
                throw new IllegalArgumentException("No puede existir más de una casa matriz. La empresa ya tiene asignada la aLong: " + casaMatrizExistente.get().getNombre());
            }
        }


        empresa.addSucursalId(sucursalId);
        empresaRepository.update(empresaId, empresa);
    }


    public Optional<Sucursal> findCasaMatriz(Long empresaId) {
        return (getSucursalesById(empresaId).stream().filter(Sucursal::isEs_Casa_Matriz).findFirst());
    }
}
