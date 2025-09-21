package Trazas.Traza2;

import Trazas.EntidadBase;
import Trazas.IRepository;
import Trazas.Traza2.entities.*;

import java.util.List;
import java.util.stream.IntStream;


public class Traza2Main {
    public static void setExampleData(
            IRepository<Articulo> articuloRepository,
            IRepository<Imagen> imagenesRepository,
            IRepository<UnidadMedida> unidadMedidaRepository,
            IRepository<Categoria> categoriaRepository
    ) {


        // Crear categorías
        Categoria pizzas = Categoria.builder().denominacion("Pizzas").build();
        Categoria sandwich = Categoria.builder().denominacion("Sandwich").build();
        Categoria lomos = Categoria.builder().denominacion("lomos").build();
        Categoria insumos = Categoria.builder().denominacion("Insumos").build();

        categoriaRepository.save(pizzas);
        categoriaRepository.save(sandwich);
        categoriaRepository.save(lomos);
        categoriaRepository.save(insumos);



        // Crear unidades de medida
        UnidadMedida kilogramos = UnidadMedida.builder().denominacion("Kilogramos").build();
        UnidadMedida litros = UnidadMedida.builder().denominacion("litros").build();
        UnidadMedida gramos = UnidadMedida.builder().denominacion("gramos").build();

        unidadMedidaRepository.save(kilogramos);
        unidadMedidaRepository.save(litros);
        unidadMedidaRepository.save(gramos);



        // Crear artículos insumo
        System.out.println("Creando insumos...");
        ArticuloInsumo sal = ArticuloInsumo.builder().denominacion("Sal").esParaElaborar(true).unidadMedida(gramos).build();

        ArticuloInsumo aceite = ArticuloInsumo.builder().denominacion("Aceite").esParaElaborar(true).unidadMedida(litros) // Assuming 'litros' exists
                .build();

        ArticuloInsumo carne = ArticuloInsumo.builder().denominacion("Carne").esParaElaborar(true).unidadMedida(kilogramos) // Assuming 'kilogramos' exists
                .build();

        ArticuloInsumo harina = ArticuloInsumo.builder().denominacion("Harina").esParaElaborar(true).unidadMedida(gramos).build();

        articuloRepository.save(sal);
        articuloRepository.save(aceite);
        articuloRepository.save(carne);
        articuloRepository.save(harina);



        // Crear imágenes
        Imagen imagen1 = Imagen.builder().denominacion("ImagenHawainaPizza1.jpg").build();
        Imagen imagen2 = Imagen.builder().denominacion("ImagenHawainaPizza2.jpg").build();
        Imagen imagen3 = Imagen.builder().denominacion("ImagenHawainaPizza3.jpg").build();

        Imagen imagen4 = Imagen.builder().denominacion("ImagenLomoCompleto1.jpg").build();
        Imagen imagen5 = Imagen.builder().denominacion("ImagenLomoCompleto2.jpg").build();
        Imagen imagen6 = Imagen.builder().denominacion("ImagenLomoCompleto3.jpg").build();


        imagenesRepository.save(imagen1);
        imagenesRepository.save(imagen2);
        imagenesRepository.save(imagen3);
        imagenesRepository.save(imagen4);
        imagenesRepository.save(imagen5);
        imagenesRepository.save(imagen6);



        // Detalles para la Pizza Hawaiana
        ArticuloManufacturadoDetalle detallePizza1 = ArticuloManufacturadoDetalle.builder().cantidad(1).articuloInsumo(sal) // Link to the 'sal' object
                .build();
        ArticuloManufacturadoDetalle detallePizza2 = ArticuloManufacturadoDetalle.builder().cantidad(2).articuloInsumo(harina) // Link to the 'harina' object
                .build();
        ArticuloManufacturadoDetalle detallePizza3 = ArticuloManufacturadoDetalle.builder().cantidad(1).articuloInsumo(aceite) // Link to the 'aceite' object
                .build();

        // Detalles para el Lomo Completo
        ArticuloManufacturadoDetalle detalleLomo1 = ArticuloManufacturadoDetalle.builder().cantidad(1).articuloInsumo(sal) // Link to the 'sal' object
                .build();
        ArticuloManufacturadoDetalle detalleLomo2 = ArticuloManufacturadoDetalle.builder().cantidad(1).articuloInsumo(aceite) // Link to the 'aceite' object
                .build();
        ArticuloManufacturadoDetalle detalleLomo3 = ArticuloManufacturadoDetalle.builder().cantidad(1).articuloInsumo(carne) // Link to the 'carne' object
                .build();



        // Crear Pizza Hawaiana
        ArticuloManufacturado pizzaHawaiana = ArticuloManufacturado.builder()
                .denominacion("Pizza Hawaiana")
                .descripcion("Una pizza exótica y deliciosa")
                .tiempoEstimadoMinutos(25)
                .precioVenta(3500.00)
                .build();

        // Agregar imágenes
        pizzaHawaiana.getImagenes().add(imagen1);
        pizzaHawaiana.getImagenes().add(imagen2);
        pizzaHawaiana.getImagenes().add(imagen3);

        // Agregar detalles
        pizzaHawaiana.getDetalles().add(detallePizza1);
        pizzaHawaiana.getDetalles().add(detallePizza2);
        pizzaHawaiana.getDetalles().add(detallePizza3);



        // Crear Lomo Completo
        ArticuloManufacturado lomoCompleto = ArticuloManufacturado.builder()
                .denominacion("Lomo Completo")
                .descripcion("El lomo más completo de Mendoza")
                .tiempoEstimadoMinutos(15)
                .precioVenta(4200.00)
                .build();

        // Agregar imágenes
        lomoCompleto.getImagenes().add(imagen4);
        lomoCompleto.getImagenes().add(imagen5);
        lomoCompleto.getImagenes().add(imagen6);


        // Agregar detalles
        lomoCompleto.getDetalles().add(detalleLomo1);
        lomoCompleto.getDetalles().add(detalleLomo2);
        lomoCompleto.getDetalles().add(detalleLomo3);


        articuloRepository.save(lomoCompleto);
        articuloRepository.save(pizzaHawaiana);



        // Agregar articulos a la categoría
        pizzas.addArticulo(pizzaHawaiana);
        categoriaRepository.update(pizzas.getId(), pizzas);



    }

    public static <T extends EntidadBase> void imprimirLista(List<T> list, String tipo) {
        if (list.isEmpty()) {
            System.out.println("No se ha encontrado ninguna instancia de " + tipo);
        } else {
            IntStream.range(0, list.size()) // Crea un stream de números desde 0 hasta el tamaño de la lista - 1
                    .forEach(i -> { // Itera sobre cada número 'i'
                        System.out.println((i + 1) + ". " + list.get(i));
                    });
        }
    }


    public static void main(String[] args) {

        IRepository<Articulo> articuloRepository = new GeneralRepositoryT2<>(Articulo.class);
        IRepository<Imagen> imagenesRepository = new GeneralRepositoryT2<>(Imagen.class);
        IRepository<UnidadMedida> unidadMedidaRepository = new GeneralRepositoryT2<>(UnidadMedida.class);
        IRepository<Categoria> categoriaRepository = new GeneralRepositoryT2<>(Categoria.class);

        setExampleData(
                articuloRepository,
                imagenesRepository,
                unidadMedidaRepository,
                categoriaRepository);



        // Imprimimos los datos de categoría y ambos tipos de artículos
        System.out.println("Imprimiendo todas las categorías: ");
        imprimirLista(categoriaRepository.retriveAll(), "categoria");

        System.out.println("Imprimiendo todos los Artículos de tipo Insumo: ");
        imprimirLista(articuloRepository.retriveAll().stream()
                .filter( articulo -> articulo instanceof ArticuloInsumo)
                .toList(), "Artículos de tipo Insumo");

        System.out.println("Imprimiendo todos los Artículos de tipo Manufacturado: ");
        imprimirLista(articuloRepository.retriveAll().stream()
                .filter( articulo -> articulo instanceof ArticuloManufacturado)
                .toList(), "Artículos de tipo Manufacturado");



        // Actualizamos pizza Hawaiana

        Long idPizzaHawaiana = articuloRepository.findByNombre("Pizza Hawaiana") // Assuming findByDenominacion exists
                .orElseThrow(() -> new RuntimeException("NO se ha encontrado con ese nombre")).getId();

        ArticuloManufacturado pizzaToUpdate = (ArticuloManufacturado) articuloRepository.findById(idPizzaHawaiana)
                .orElseThrow(() -> new RuntimeException("Pizza not found"));
        pizzaToUpdate.setTiempoEstimadoMinutos(30);
        articuloRepository.update(idPizzaHawaiana, pizzaToUpdate);


        System.out.println("Imprimiendo todos los Artículos de tipo Manufacturado (DESPUES DE UPDATE): ");
        imprimirLista(articuloRepository.retriveAll().stream()
                .filter( articulo -> articulo instanceof ArticuloManufacturado)
                .toList(), "Artículos de tipo Manufacturado");



        // Eliminamos pizza Hawaiana

        articuloRepository.delete(idPizzaHawaiana);


        System.out.println("Imprimiendo todos los Artículos de tipo Manufacturado (DESPUES DE DELETE): ");
        imprimirLista(articuloRepository.retriveAll().stream()
                .filter( articulo -> articulo instanceof ArticuloManufacturado)
                .toList(), "Artículos de tipo Manufacturado");


    }


}
