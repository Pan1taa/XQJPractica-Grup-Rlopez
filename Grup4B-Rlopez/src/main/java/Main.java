import javax.xml.xquery.XQException;

public class Main {
    public static void main(String[] args) {
        try {
            // Crear instancia de GestorDB
            GestorDB gestorDB = new GestorDB();

            // Crear instancia de Menu y ejecutar el menú
            Menu menu = new Menu(gestorDB);
            menu.mostrarMenu();

            // Cerrar la conexión
            gestorDB.cerrarConexion();
        } catch (XQException e) {
            e.printStackTrace();
        }
    }
}