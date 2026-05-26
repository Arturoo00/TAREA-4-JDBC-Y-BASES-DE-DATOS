package org.example.dao;

// ============================================================
// CLASE DB SEPARADA - Maneja toda la conexión a la base de datos
// Usa: JDBC, Connection, PreparedStatement, ResultSet, try/catch
// ============================================================

import java.sql.*;

public class dao {

    // ============================================================
    // CONFIGURACIÓN DE CONEXIÓN A LA BASE DE DATOS ORACLE
    // ============================================================
    private static String driver = "oracle.jdbc.driver.OracleDriver";
    //private static String host = "jdbc:oracle:thin:@localhost:1521/XEPDB1"; //casa
    private static String host = "jdbc:oracle:thin:@localhost:1521/XE"; // colegio

    // Usuario y contraseña para la base de datos
    private static String usuario = "LOLO";
    private static String pass = System.getenv("DB_PASS");


    // ============================================================
    // MÉTODO dao() - Prueba la conexión a la base de datos
    // Se ejecuta al inicio del programa para verificar que
    // la base de datos está disponible.
    // Si falla, reintenta cada 8 segundos automáticamente.
    // ============================================================
    public static void dao() {
        boolean repit = false;
        do {
            repit = false;
            try {
                // Cargar el driver de Oracle JDBC
                Class.forName(driver);
                // Establecer conexión usando JDBC Connection
                Connection conexion = DriverManager.getConnection(host, usuario, pass);
                System.out.println("¡prueba de Conexión a la Base de Datos exitosa!");

                // Crear Statement para ejecutar consulta de prueba
                Statement stmt = conexion.createStatement();

                // Consulta de prueba usando DUAL (tabla especial de Oracle)
                String query = "SELECT 'Conexion Correcta' FROM DUAL";
                // Ejecutar y obtener ResultSet con la respuesta
                ResultSet rs = stmt.executeQuery(query);
                if (rs.next()) {
                    String mensajeDeLaBD = rs.getString(1);
                    System.out.println("Respuesta de la Base de Datos: " + mensajeDeLaBD);
                }
                // Cerrar la conexión de prueba
                conexion.close();
                System.out.println("conexion cerrada");
                System.out.println();

            // try/catch para manejar errores de conexión
            } catch (SQLException | ClassNotFoundException ex) {
                System.out.println("Error en la conexión de la base de datos");
                repit = true;
                System.out.println("Reintentando en 8 segundos");
                try { Thread.sleep(8000); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            }
        }while (repit);
    }

    // ============================================================
    // MÉTODO preguntar() - Ejecuta cualquier consulta SQL
    // Acepta consultas SELECT (devuelve datos) e INSERT/UPDATE/DELETE
    // Usa PreparedStatement para evitar inyección SQL
    // Usa ResultSet para leer los resultados de los SELECT
    // Usa try/catch para manejar errores
    // Parámetros:
    //   - sqlConsulta: la consulta SQL a ejecutar
    //   - params: parámetros opcionales para PreparedStatement (los ?)
    // ============================================================
    public static void preguntar(String sqlConsulta, Object... params){
        System.out.println("----------------------consulta--------------------------");
        System.out.println(sqlConsulta);
        System.out.println("----------------------------------------------respuesta:");

        // try-with-resources: Connection y PreparedStatement se cierran automáticamente
        try (Connection conexion = DriverManager.getConnection(host, usuario, pass);
             PreparedStatement stmt = conexion.prepareStatement(sqlConsulta)) {

            // Asignar los parámetros (?) al PreparedStatement
            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]);
            }

            // Ejecutar la consulta - devuelve true si es SELECT, false si es INSERT/UPDATE/DELETE
            boolean isSelect = stmt.execute();

            if (isSelect) {
                // Si es un SELECT, procesar el ResultSet con los datos devueltos
                try (ResultSet rs = stmt.getResultSet()) {
                    // Obtener metadatos para saber los nombres de las columnas
                    ResultSetMetaData meta = rs.getMetaData();
                    int columnas = meta.getColumnCount();

                    // Imprimir cabecera con nombres de columnas
                    for (int i = 1; i <= columnas; i++) {
                        System.out.print(meta.getColumnLabel(i) + " | ");
                    }
                    System.out.println("\n------------------------------------------------------------------------------------------------------------------------------------------------------");

                    // Formato para fechas (por si alguna columna tiene fecha)
                    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd-MMM-yy");

                    // Recorrer cada fila del ResultSet
                    while (rs.next()) {
                        for (int i = 1; i <= columnas; i++) {
                            Object valor = rs.getObject(i);

                            // Manejar distintos tipos de datos
                            if (valor instanceof Clob) {
                                // Si es un CLOB (texto largo), leerlo completo
                                Clob clob = (Clob) valor;
                                String texto = clob.getSubString(1, (int) clob.length());
                                String[] lineas = texto.split("\n");
                                for (String linea : lineas) {
                                    System.out.println(linea);
                                }
                            } else if (valor instanceof Date) {
                                // Si es fecha, formatearla
                                System.out.print(sdf.format(valor) + " | ");
                            } else {
                                // Para cualquier otro tipo (String, Number, etc.)
                                System.out.print((valor != null ? valor.toString() : "NULL") + " | ");
                            }
                        }
                        System.out.println();
                    }
                }
            } else {
                // Si es INSERT, UPDATE o DELETE, mostrar filas afectadas
                int filasAfectadas = stmt.getUpdateCount();
                System.out.println(filasAfectadas + " fila(s) afectada(s).");
            }


            System.out.println("--------------------------------------------------------------------------------");
            System.out.println("Conexion finalizada");

        // try/catch para manejar errores SQL
        } catch (SQLException ex) {
            System.out.println("Error en la conexión de la base de datos");
            ex.printStackTrace();
        }
    }

}
