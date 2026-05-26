package org.example.dao;

/**
 * DAO para manejar operaciones CRUD sobre la tabla PRODUCTOS.
 * Se utiliza la clase {@code dao} existente que ya gestiona la conexión y
 * la ejecución de consultas mediante {@code PreparedStatement}.
 */
public class ProductDAO {

    /**
     * Inserta un nuevo producto en la tabla PRODUCTOS.
     */
    public static void insertProduct(int id, String nombre, double precio, String vendedor) {
        String sql = "INSERT INTO PRODUCTOS (ID_PRODUCTO, NOMBRE, PRECIO, VENDEDOR) VALUES (?, ?, ?, ?)";
        dao.preguntar(sql, id, nombre, precio, vendedor);
    }

    /**
     * Lista todos los productos.
     */
    public static void listProducts() {
        dao.preguntar("SELECT ID_PRODUCTO, NOMBRE, PRECIO, VENDEDOR FROM PRODUCTOS");
    }

    /**
     * Busca productos por nombre (búsqueda parcial).
     */
    public static void findByName(String nombre) {
        String sql = "SELECT ID_PRODUCTO, NOMBRE, PRECIO, VENDEDOR FROM PRODUCTOS WHERE NOMBRE LIKE ?";
        dao.preguntar(sql, "%" + nombre + "%");
    }

    /**
     * Modifica el precio de un producto existente.
     */
    public static void updatePrice(int id, double nuevoPrecio) {
        String sql = "UPDATE PRODUCTOS SET PRECIO = ? WHERE ID_PRODUCTO = ?";
        dao.preguntar(sql, nuevoPrecio, id);
    }

    /**
     * Elimina un producto por su ID.
     */
    public static void deleteProduct(int id) {
        String sql = "DELETE FROM PRODUCTOS WHERE ID_PRODUCTO = ?";
        dao.preguntar(sql, id);
    }
}
