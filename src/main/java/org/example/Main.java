package org.example;

import java.util.Scanner;
import org.example.dao.dao;
import org.example.dao.ProductDAO;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        dao.dao();

        boolean segir = true;

        do {

            System.out.println("---------------------MENU-------------------");
            System.out.println("1. Listar productos");
            System.out.println("2. Buscar producto por nombre");
            System.out.println("3. Insertar nuevo producto");
            System.out.println("4. Modificar precio de producto");
            System.out.println("5. Eliminar producto");
            System.out.println("6. Salir");
            System.out.println("--------------------------------------------");
            System.out.print("Eliga una opcion: ");

            int opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    System.out.println("Has elegido: Listar productos");
                    ProductDAO.listProducts();
                    break;
                case 2:
                    System.out.println("Has elegido: Buscar producto por nombre");
                    System.out.print("Ingrese nombre a buscar: ");
                    scanner.nextLine(); // consume newline
                    String nombreBuscar = scanner.nextLine();
                    ProductDAO.findByName(nombreBuscar);
                    break;
                case 3:
                    System.out.println("Has elegido: Insertar nuevo producto");
                    System.out.print("id producto: ");
                    int idProd = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("nombre: ");
                    String nombreProd = scanner.nextLine();
                    System.out.print("precio: ");
                    double precioProd = scanner.nextDouble();
                    scanner.nextLine();
                    System.out.print("vendedor: ");
                    String vendedorProd = scanner.nextLine();
                    ProductDAO.insertProduct(idProd, nombreProd, precioProd, vendedorProd);
                    break;
                case 4:
                    System.out.println("Has elegido: Modificar precio de producto");
                    System.out.print("id producto: ");
                    int idMod = scanner.nextInt();
                    System.out.print("nuevo precio: ");
                    double nuevoPrecio = scanner.nextDouble();
                    scanner.nextLine();
                    ProductDAO.updatePrice(idMod, nuevoPrecio);
                    break;
                case 5:
                    System.out.println("Has elegido: Eliminar producto");
                    System.out.print("id producto a eliminar: ");
                    int idDel = scanner.nextInt();
                    scanner.nextLine();
                    ProductDAO.deleteProduct(idDel);
                    break;
                case 6:
                    segir = false;
                    break;
            }

        }while (segir);

        scanner.close();
    }
}
