package Trazas.Traza1;


import Trazas.EntidadBase;
import Trazas.IRepository;
import Trazas.Traza1.entities.*;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

public class Traza1Main {

    public static void setExampleData(IRepository<Empresa> empresaRepository, IRepository<Sucursal> sucursalRepository, EmpresaService empresaService) {

        // --- Crear un país (Argentina) ---
        Pais argentina = Pais.builder().nombre("Argentina").build();

        // --- Crear Provincias y relacionarlas con el país ---
        Provincia buenosAires = Provincia.builder().nombre("Buenos Aires").pais(argentina).build();
        Provincia cordoba = Provincia.builder().nombre("Córdoba").pais(argentina).build(); // Corregido: Mendoza -> Córdoba

        // --- Crear Localidades de Buenos Aires y sus domicilios ---
        Localidad caba = Localidad.builder().nombre("CABA").provincia(buenosAires).build();
        Domicilio domicilioCaba = Domicilio.builder().nombre("Av. Corrientes").numero(123).localidad(caba).cp(1043).build();

        Localidad laPlata = Localidad.builder().nombre("La Plata").provincia(buenosAires).build();
        Domicilio domicilioLaPlata = Domicilio.builder().nombre("Calle 7").numero(456).localidad(laPlata).cp(1900).build();

        // --- Crear Localidades de Córdoba y sus domicilios ---
        Localidad cordobaCapital = Localidad.builder().nombre("Córdoba Capital").provincia(cordoba).build();
        Domicilio domicilioCordobaCapital = Domicilio.builder().nombre("Av. Colón").numero(789).localidad(cordobaCapital).cp(5000).build();

        Localidad villaCarlosPaz = Localidad.builder().nombre("Villa Carlos Paz").provincia(cordoba).build();
        Domicilio domicilioVillaCarlosPaz = Domicilio.builder().nombre("Av. San Martín").numero(101).localidad(villaCarlosPaz).cp(5152).build();

        // --- Crear las Sucursales ---
        Sucursal sucursal1 = Sucursal.builder().nombre("CABA").domicilio(domicilioCaba).horarioApertura(LocalTime.of(9, 0)).horarioCierre(LocalTime.of(18, 0)).es_Casa_Matriz(true).build();
        Sucursal sucursal2 = Sucursal.builder().nombre("La Plata").domicilio(domicilioLaPlata).horarioApertura(LocalTime.of(9, 0)).horarioCierre(LocalTime.of(18, 0)).build();
        Sucursal sucursal3 = Sucursal.builder().nombre("Córdoba Capital").domicilio(domicilioCordobaCapital).horarioApertura(LocalTime.of(8, 30)).horarioCierre(LocalTime.of(17, 30)).es_Casa_Matriz(true).build();
        Sucursal sucursal4 = Sucursal.builder().nombre("Villa Carlos Paz").domicilio(domicilioVillaCarlosPaz).horarioApertura(LocalTime.of(10, 0)).horarioCierre(LocalTime.of(20, 0)).build();


        // --- Guardar las Sucursales ---
        sucursalRepository.save(sucursal1);
        sucursalRepository.save(sucursal2);
        sucursalRepository.save(sucursal3);
        sucursalRepository.save(sucursal4);


        // --- Crear las Empresas y relacionarlas con las sucursales ---
        Empresa empresa1 = Empresa.builder().nombre("TecnoCorp").cuit("30-11223344-5").razonSocial("TecnoCorp S.A.").build();
        Empresa empresa2 = Empresa.builder().nombre("LogiSolutions").cuit("30-55667788-9").razonSocial("LogiSolutions S.R.L.").build();

        // --- Guardar empresas en el repositorio ---
        empresaRepository.save(empresa1);
        empresaRepository.save(empresa2);


        // --- Agregar sucursales a las empresas  ---
        empresaService.addSucursalToEmpresa(empresa2.getId(), sucursal3.getId());
        empresaService.addSucursalToEmpresa(empresa2.getId(), sucursal4.getId());

        empresaService.addSucursalToEmpresa(empresa1.getId(), sucursal1.getId());
        empresaService.addSucursalToEmpresa(empresa1.getId(), sucursal2.getId());
    }


    public static void main(String[] args) {

        IRepository<Empresa> empresaRepository = new GeneralRepositoryT1<>(Empresa.class);
        IRepository<Sucursal> sucursalRepository = new GeneralRepositoryT1<>(Sucursal.class);
        EmpresaService empresaService = new EmpresaService(empresaRepository, sucursalRepository);


        Traza1Main.setExampleData(empresaRepository, sucursalRepository, empresaService);


        System.out.println("\n--- DEMOSTRACIÓN DE RESULTADOS (Punto 5) ---\n");

        // a. Mostrar todas las empresas.
        System.out.println("a. Mostrando todas las empresas:");
        List<Empresa> todasLasEmpresas = empresaRepository.retriveAll();
        todasLasEmpresas.forEach(System.out::println);
        System.out.println("\n\n-------------------------------------\n\n");


        // b. Buscar una empresa por su Id.
        System.out.println("b. Buscando empresa con ID = 2:");
        Optional<Empresa> empresaPorId = empresaRepository.findById(2L);
        empresaPorId.ifPresentOrElse(
                System.out::println,
                () -> System.out.println("No se encontró la empresa con ID 2.")
        );
        System.out.println("\n\n-------------------------------------\n\n");


        // c. Buscar una empresa por nombre.
        System.out.println("c. Buscando empresa con nombre = 'TecnoCorp':");
        Optional<Empresa> empresaPorNombre = empresaRepository.findByNombre("TecnoCorp");
        empresaPorNombre.ifPresentOrElse(
                System.out::println,
                () -> System.out.println("No se encontró la empresa con nombre 'TecnoCorp'.")
        );
        System.out.println("\n\n-------------------------------------\n\n");


        // d. Actualizar los datos de una empresa buscando por su ID.
        System.out.println("d. Actualizando el CUIT de la empresa con ID = 1:");
        Empresa datosUpdate = Empresa.builder().cuit("30-99999999-1").nombre("TecnoCorp Global").build(); // Solo creamos un objeto con los datos a cambiar
        Optional<Empresa> empresaActualizada = empresaRepository.update(1L, datosUpdate);
        System.out.println("Resultado de la actualización:");
        empresaActualizada.ifPresentOrElse(
                System.out::println,
                () -> System.out.println("No se pudo actualizar la empresa con ID 1.")
        );


        System.out.println("\n\n-------------------------------------\n\n");


        // e. Eliminar una empresa buscando por su ID.
        System.out.println("e. Eliminando la empresa con ID = 1:");
        Optional<Empresa> empresaEliminada1 = empresaRepository.delete(1L);
        empresaEliminada1.ifPresentOrElse(
                emp -> System.out.println("Se eliminó con éxito la empresa: " + emp.getNombre()),
                () -> System.out.println("No se pudo eliminar la empresa con ID 1.")
        );


        System.out.println("\nLista de empresas después de la eliminación:");

        imprimirLista(empresaRepository.retriveAll(), "empresa");

        System.out.println("\n\n-------------------------------------\n\n");


        System.out.println("e. Eliminando la empresa con ID = 2:");
        Optional<Empresa> empresaEliminada2 = empresaRepository.delete(2L);
        empresaEliminada2.ifPresentOrElse(
                emp -> System.out.println("Se eliminó con éxito la empresa: " + emp.getNombre()),
                () -> System.out.println("No se pudo eliminar la empresa con ID 2.")
        );

        System.out.println("\nLista de empresas después de la segunda eliminación:");

        imprimirLista(empresaRepository.retriveAll(), "empresa");

        System.out.println("\n\n-------------------------------------\n\n");

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
}
