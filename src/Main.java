import Trazas.IRepository;
import Trazas.Traza1.Traza1Main;
import Trazas.Traza1.entities.Empresa;
import Trazas.Traza1.entities.EmpresaService;
import Trazas.Traza1.entities.GeneralRepositoryT1;
import Trazas.Traza1.entities.Sucursal;
import Trazas.Traza2.Traza2Main;
import Trazas.Traza2.entities.*;

import java.util.List;


public class Main {
    public static void main(String[] args) {

        // Traza 1
        IRepository<Empresa> empresaRepositoryT1 = new GeneralRepositoryT1<>(Empresa.class);
        IRepository<Sucursal> sucursalRepositoryT1 = new GeneralRepositoryT1<>(Sucursal.class);

        EmpresaService empresaServiceT1 = new EmpresaService(empresaRepositoryT1, sucursalRepositoryT1);

        // Sembramos los repositorios con data de ejemplo
        Traza1Main.setExampleData(empresaRepositoryT1, sucursalRepositoryT1, empresaServiceT1);


        // Traza 2
        IRepository<Articulo> articuloRepositoryT2 = new GeneralRepositoryT2<>(Articulo.class);
        IRepository<Imagen> imagenesRepository = new GeneralRepositoryT2<>(Imagen.class);
        IRepository<UnidadMedida> unidadMedidaRepositoryT2 = new GeneralRepositoryT2<>(UnidadMedida.class);
        IRepository<Categoria> categoriaRepositoryT2 = new GeneralRepositoryT2<>(Categoria.class);

        Traza2Main.setExampleData(
                articuloRepositoryT2,
                imagenesRepository,
                unidadMedidaRepositoryT2,
                categoriaRepositoryT2);




        // UNION traza1 + traza 2

        SucursalArticuloService sucursalArticuloService = new SucursalArticuloService(sucursalRepositoryT1, articuloRepositoryT2);

        Long sucursalCabaId = sucursalRepositoryT1.findByNombre("CABA").orElseThrow(() -> new RuntimeException("No se ha encontrado la sucursal con ese nombre")).getId();
        Long sucursalLaPlataId = sucursalRepositoryT1.findByNombre("La Plata").orElseThrow(() -> new RuntimeException("No se ha encontrado la sucursal con ese nombre")).getId();

        Long pizzaId = articuloRepositoryT2.findByNombre("Pizza Hawaiana").orElseThrow(() -> new RuntimeException("No se ha encontrado el artículo con ese nombre")).getId();
        Long lomoId = articuloRepositoryT2.findByNombre("Lomo Completo").orElseThrow(() -> new RuntimeException("No se ha encontrado el artículo con ese nombre")).getId();
        Long harinaId = articuloRepositoryT2.findByNombre("Harina").orElseThrow(() -> new RuntimeException("No se ha encontrado el artículo con ese nombre")).getId();

        sucursalArticuloService.linkSucursalToArticulo(sucursalCabaId, pizzaId, 50); // 50 pizzas in CABA
        sucursalArticuloService.linkSucursalToArticulo(sucursalCabaId, lomoId, 30);  // 30 lomos in CABA
        sucursalArticuloService.linkSucursalToArticulo(sucursalLaPlataId, pizzaId, 45); // 45 pizzas in La Plata
        sucursalArticuloService.linkSucursalToArticulo(sucursalLaPlataId, harinaId, 200); // 200 units of harina in La Plata


        // Ejemplo 1: Mostrar todos los artículos disponibles en CABA
        System.out.println("Artículos disponibles en la sucursal de CABA:");
        List<Articulo> articulosEnCaba = sucursalArticuloService.getArticulosInSucursal(sucursalCabaId);
        articulosEnCaba.forEach(articulo -> System.out.println("- " + articulo.getDenominacion()));

        // Ejemplo 2: Mostrar stock de cierto artículo en cierta sucursal
        System.out.println("\nConsultando stock específico:");
        int stockDePizzaEnCaba = sucursalArticuloService.getStock(sucursalCabaId, pizzaId);
        System.out.println("- Stock de 'Pizza Hawaiana' en 'CABA': " + stockDePizzaEnCaba);

        int stockDeLomoEnLaPlata = sucursalArticuloService.getStock(sucursalLaPlataId, lomoId);
        System.out.println("- Stock de 'Lomo Completo' en 'La Plata': " + stockDeLomoEnLaPlata + " (debería ser 0)");
    }
}