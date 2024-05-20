import javax.xml.xquery.*;
import java.util.Scanner;

public class Menu {
    private GestorDB gestorDB;

    public Menu(GestorDB gestorDB) {
        this.gestorDB = gestorDB;
    }

    public void mostrarMenu() throws XQException, InterruptedException {
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("Menú:");
            System.out.println("1. Consulta por año y genero de (Festivales.xml)");
            System.out.println("2. Consulta de eventos por año (Festivales.xml)");
            System.out.println("3. Consulta de fabricas por distrito y equipamiento (Fabricas.xml)");
            System.out.println("4. Consulta de total de metros cuadados por año (Fabricas.xml)");
            System.out.println("5. Consulta insertar un nuevo evento (Festivales.xml)");
            System.out.println("6. Consulta modificar un evento existente (Festivales.xml)");
            System.out.println("7. Consulta eliminar un evento existente (Festivales.xml)");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    gestorDB.consultarEventosPorAñoYGenero();
                    break;
                case 2:
                    gestorDB.contarEventosPorAño();
                    break;
                case 3:
                    gestorDB.mostrarFabricasPorDistritoYEquipamiento();
                    break;
                case 4:
                    gestorDB.calcularTotalMetrosCuadradosPorAño();
                    break;
                case 5:
                    gestorDB.insertarNuevoEvento();
                    Thread.sleep(1000);
                    gestorDB.consultarUltimoEvento();
                    break;
                case 6:
                    gestorDB.modificarEvento();
                    break;
                case 7:
                    gestorDB.eliminarEvento();
                    break;
                case 0:
                    System.out.println("Saliendo del programa...");
                    break;
                default:
                    System.out.println("Opción no válida. Inténtelo de nuevo.");
            }
        } while (opcion != 0);

        scanner.close();
    }
}