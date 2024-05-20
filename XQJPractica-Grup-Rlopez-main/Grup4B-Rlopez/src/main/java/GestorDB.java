import javax.xml.xquery.*;
import net.xqj.exist.ExistXQDataSource;
import java.util.Scanner;

public class GestorDB {
    private XQDataSource dataSource;
    private XQConnection conn;

    public GestorDB() throws XQException {
        // Configuración de la conexión a la base de datos
        dataSource = new ExistXQDataSource();
        dataSource.setProperty("serverName", "localhost");
        dataSource.setProperty("port", "8080");
        conn = dataSource.getConnection();
    }

    public void cerrarConexion() throws XQException {
        // Cierra la conexión a la base de datos
        conn.close();
    }

    public void consultarEventosPorAñoYGenero() throws XQException {
        XQExpression expr = conn.createExpression();
        XQResultSequence result = expr.executeQuery(
                "for $evento in doc('/db/XQJPractica/Festivales.xml')//grec_dades_2022 " +
                        "return concat('Año: ', $evento/Any/text(), ', Género: ', $evento/Genere/text())"
        );

        while (result.next()) {
            System.out.println(result.getItemAsString(null));
        }
    }

    public void contarEventosPorAño() throws XQException {
        XQExpression expr = conn.createExpression();
        XQResultSequence result = expr.executeQuery(
                "for $year in distinct-values(doc('/db/XQJPractica/Festivales.xml')//grec_dades_2022/Any) " +
                        "return concat('Año: ', $year, ', Cantidad de eventos: ', count(doc('/db/XQJPractica/Festivales.xml')//grec_dades_2022[Any = $year]))"
        );

        while (result.next()) {
            System.out.println(result.getItemAsString(null));
        }
    }

    public void mostrarFabricasPorDistritoYEquipamiento() throws XQException {
        XQExpression expr = conn.createExpression();
        XQResultSequence result = expr.executeQuery(
                "for $fabrica in doc('/db/XQJPractica/Fabricas.xml')//fabriques_creacio_metres_quadrats_2019 " +
                        "return concat('Distrito: ', $fabrica/Nom_Districte/text(), ', Tipo de equipamiento: ', $fabrica/TipusEquipament/text())"
        );

        while (result.next()) {
            System.out.println(result.getItemAsString(null));
        }
    }

    public void calcularTotalMetrosCuadradosPorAño() throws XQException {
        XQExpression expr = conn.createExpression();
        XQResultSequence result = expr.executeQuery(
                "for $year in distinct-values(doc('/db/XQJPractica/Fabricas.xml')//fabriques_creacio_metres_quadrats_2019/Any) " +
                        "return concat('Año: ', $year, ', Total metros cuadrados: ', sum(doc('/db/XQJPractica/Fabricas.xml')//fabriques_creacio_metres_quadrats_2019[Any = $year]/Valor))"
        );

        while (result.next()) {
            System.out.println(result.getItemAsString(null));
        }
    }
    public void insertarNuevoEvento() throws XQException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Introduce el año del nuevo evento:");
        String año = scanner.nextLine();
        System.out.println("Introduce el indicador del nuevo evento:");
        String indicador = scanner.nextLine();
        System.out.println("Introduce el género del nuevo evento:");
        String género = scanner.nextLine();
        System.out.println("Introduce el valor del nuevo evento:");
        String valor = scanner.nextLine();

        String xquery = String.format(
                "update insert " +
                        "<grec_dades_2022>" +
                        "<Any>%s</Any>" +
                        "<Indicador>%s</Indicador>" +
                        "<Genere>%s</Genere>" +
                        "<Valor>%s</Valor>" +
                        "<Nota/>" +
                        "</grec_dades_2022> into doc('/db/XQJPractica/Festivales.xml')//xml",
                año, indicador, género, valor
        );

        XQExpression expr = conn.createExpression();
        expr.executeCommand(xquery);
        System.out.println("Nuevo evento insertado correctamente.");
    }
    public void consultarUltimoEvento() throws XQException {
        String xquery =
                "for $evento in doc('/db/XQJPractica/Festivales.xml')//grec_dades_2022 " +
                        "order by $evento descending " + // Suponiendo que tengas un campo ID
                        "return $evento";

        XQExpression expr = conn.createExpression();
        XQResultSequence result = expr.executeQuery(xquery);

        if (!result.next()) {
            System.out.println("No se encontraron eventos en la base de datos.");
            return;
        }

        // Mostrar el último evento encontrado
        System.out.println("Último evento encontrado:");
        System.out.println(result.getItemAsString(null));
    }

    public void modificarEvento() throws XQException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Introduce el año del evento a modificar:");
        String año = scanner.nextLine();
        System.out.println("Introduce el indicador del evento a modificar:");
        String indicador = scanner.nextLine();
        System.out.println("Introduce el género del evento a modificar:");
        String género = scanner.nextLine();

        // Consulta para encontrar el evento a modificar
        String consultaBusqueda = String.format(
                "for $evento in doc('/db/XQJPractica/Festivales.xml')//grec_dades_2022 " +
                        "where $evento/Any/text() = '%s' and $evento/Indicador/text() = '%s' and $evento/Genere/text() = '%s' " +
                        "return $evento",
                año, indicador, género
        );

        XQExpression expr = conn.createExpression();
        XQResultSequence result = expr.executeQuery(consultaBusqueda);

        if (!result.next()) {
            System.out.println("No se encontraron eventos que coincidan con los criterios de búsqueda.");
            return;
        }

        // Mostrar el evento encontrado
        System.out.println("Evento encontrado:");
        System.out.println(result.getItemAsString(null));

        // Solicitar al usuario los nuevos valores
        System.out.println("Introduce el nuevo valor para el evento:");
        String nuevoValor = scanner.nextLine();

        // Consulta para actualizar el evento
        String consultaUpdate = String.format(
                "update value " +
                        "doc('/db/XQJPractica/Festivales.xml')//grec_dades_2022[Any='%s' and Indicador='%s' and Genere='%s']/Valor " +
                        "with '%s'",
                año, indicador, género, nuevoValor
        );

        // Ejecutar la actualización
        expr.executeCommand(consultaUpdate);
        System.out.println("Evento modificado correctamente.");
    }



    public void eliminarEvento() throws XQException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Introduce el año del evento a eliminar:");
        String año = scanner.nextLine();
        System.out.println("Introduce el indicador del evento a eliminar:");
        String indicador = scanner.nextLine();
        System.out.println("Introduce el género del evento a eliminar:");
        String género = scanner.nextLine();

        // Consulta para encontrar el evento a eliminar
        String consultaBusqueda = String.format(
                "for $evento in doc('/db/XQJPractica/Festivales.xml')//grec_dades_2022 " +
                        "where $evento/Any/text() = '%s' and $evento/Indicador/text() = '%s' and $evento/Genere/text() = '%s' " +
                        "return $evento",
                año, indicador, género
        );

        XQExpression expr = conn.createExpression();
        XQResultSequence result = expr.executeQuery(consultaBusqueda);

        if (!result.next()) {
            System.out.println("No se encontraron eventos que coincidan con los criterios de búsqueda.");
            return;
        }

        // Mostrar el evento encontrado
        System.out.println("Evento encontrado:");
        System.out.println(result.getItemAsString(null));

        // Confirmar la eliminación
        System.out.println("¿Estás seguro de que deseas eliminar este evento? (s/n)");
        String confirmacion = scanner.nextLine();
        if (!confirmacion.equalsIgnoreCase("s")) {
            System.out.println("Operación cancelada. No se ha eliminado ningún evento.");
            return;
        }

        // Consulta para eliminar el evento
        String consultaDelete = String.format(
                "update delete " +
                        "doc('/db/XQJPractica/Festivales.xml')//grec_dades_2022[Any='%s' and Indicador='%s' and Genere='%s']",
                año, indicador, género
        );

        // Ejecutar la eliminación
        expr.executeCommand(consultaDelete);
        System.out.println("Evento eliminado correctamente.");
    }

}