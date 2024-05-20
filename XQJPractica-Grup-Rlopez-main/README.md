# XQJPractica-Grup-Rlopez

## Proyecto XQJPractica

Este proyecto Java es una práctica de consulta de bases de datos XML utilizando XQuery. Permite realizar consultas sobre dos conjuntos de datos XML: Festivales.xml y Fabricas.xml.

### Clases

#### GestorDB

La clase GestorDB gestiona la conexión a la base de datos XML y contiene métodos para realizar consultas sobre los datos de los festivales y las fábricas.

##### Métodos

- `GestorDB()`: Constructor que establece la conexión con la base de datos.
- `cerrarConexion()`: Cierra la conexión a la base de datos.
- `consultarEventosPorAñoYGenero()`: Consulta eventos por año y género en el archivo Festivales.xml.
- `contarEventosPorAño()`: Cuenta los eventos por año en el archivo Festivales.xml.
- `mostrarFabricasPorDistritoYEquipamiento()`: Muestra fábricas por distrito y equipamiento en el archivo Fabricas.xml.
- `calcularTotalMetrosCuadradosPorAño()`: Calcula el total de metros cuadrados por año en el archivo Fabricas.xml.

#### Menu

La clase Menu proporciona un menú interactivo para que el usuario pueda seleccionar qué consulta realizar sobre los datos.

##### Métodos

- `Menu(GestorDB gestorDB)`: Constructor que recibe una instancia de GestorDB.
- `mostrarMenu()`: Muestra un menú interactivo y realiza las consultas según la opción seleccionada por el usuario.

#### Main

La clase Main es la clase principal que instancia y ejecuta el menú para interactuar con las consultas.

### Uso

1. Ejecutar la clase Main.
2. Seleccionar una opción del menú para realizar la consulta deseada.
3. Seguir las instrucciones en pantalla para interactuar con las consultas.
4. La conexión se cerrará automáticamente al salir del programa.

## División de trabajo

- **Daniel**: Se ha encargado de hacer la conexión a la base de datos y una consulta de cada fichero XML diferente.
- **Raul**: Se ha encargado de hacer el menú y una consulta de cada fichero XML diferente.
- **Festivales.xml**: en este fichero hemos hecho 3 consultas de insertar, modificar y eliminar. Aqui hemos trabajado los dos pensando en como maquetar y hacer bien el codigo y la consulta.