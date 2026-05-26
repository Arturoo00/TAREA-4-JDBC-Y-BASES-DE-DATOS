# SistemaVentasDao

Proyecto basico en Java para practicar JDBC y conexion con una base de datos Oracle.

## Que hace

El programa muestra un menu por consola para trabajar con productos.

Opciones disponibles:

- Listar productos.
- Buscar un producto por nombre.
- Insertar un producto nuevo.
- Modificar el precio de un producto.
- Eliminar un producto.
- Salir del programa.

## Tecnologias usadas

- Java 8.
- Maven.
- JDBC.
- Oracle Database.

## Tabla usada

El proyecto trabaja con la tabla `PRODUCTOS`.

Columnas usadas:

- `ID_PRODUCTO`
- `NOMBRE`
- `PRECIO`
- `VENDEDOR`

## Configuracion de la base de datos

La conexion esta configurada en la clase:

`src/main/java/org/example/dao/dao.java`

Datos actuales:

- Usuario: `LOLO`
- URL: `jdbc:oracle:thin:@localhost:1521/XE`
- Contrasena: se lee desde la variable de entorno `DB_PASS`

Antes de ejecutar el programa hay que tener creada la variable `DB_PASS` con la contrasena de la base de datos.

## Como ejecutar

Compilar el proyecto:

```bash
mvn compile
```

Ejecutar la clase principal:

```bash
mvn exec:java -Dexec.mainClass="org.example.Main"
```

Tambien se puede ejecutar desde IntelliJ abriendo la clase `Main`.
