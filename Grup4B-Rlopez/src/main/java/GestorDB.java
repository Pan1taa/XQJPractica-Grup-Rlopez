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
}