import Trazas.IRepository;
import Trazas.Traza1.entities.Sucursal;
import Trazas.Traza2.entities.Articulo;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SucursalArticuloService {


    private final IRepository<Sucursal> sucursalRepository;
    private final IRepository<Articulo> articuloRepository;


    private final List<SucursalArticulo> inventario = new ArrayList<>();

    public SucursalArticuloService(IRepository<Sucursal> sucursalRepository, IRepository<Articulo> articuloRepository) {
        this.sucursalRepository = sucursalRepository;
        this.articuloRepository = articuloRepository;
    }



    public void linkSucursalToArticulo(Long sucursalId, Long articuloId, int stock) {

        Sucursal sucursal = sucursalRepository.findById(sucursalId)
                .orElseThrow(() -> new RuntimeException("Sucursal no encontrada con ID: " + sucursalId));
        Articulo articulo = articuloRepository.findById(articuloId)
                .orElseThrow(() -> new RuntimeException("Articulo no encontrado con ID: " + articuloId));


        SucursalArticulo newLink = new SucursalArticulo(sucursal, articulo, stock);
        this.inventario.add(newLink);
    }


    public List<Articulo> getArticulosInSucursal(Long sucursalId) {
        return inventario.stream()
                .filter(link -> link.getSucursal().getId().equals(sucursalId))
                .map(SucursalArticulo::getArticulo)
                .collect(Collectors.toList());
    }


    public int getStock(Long sucursalId, Long articuloId) {
        return inventario.stream()
                .filter(link -> link.getSucursal().getId().equals(sucursalId) &&
                        link.getArticulo().getId().equals(articuloId))
                .mapToInt(SucursalArticulo::getStock)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No existe un vínculo entre sucursal " + sucursalId + " y artículo " + articuloId));
    }
}