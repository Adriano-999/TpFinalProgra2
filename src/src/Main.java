import Exception.*;
import Model.BDD.Conexion;
import Model.BDD.ConexionUsuario;
import Model.Usuario;

import java.util.InputMismatchException;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static void main() {

        boolean end = false;
        int choice;
        Scanner sc = new Scanner(System.in);
        Usuario usuario = null;
        while (!end) {
            try {
                if (Objects.isNull(usuario)) {
                    System.out.println("Iniciar Sesion:");
                    System.out.println("Nombre de usuario:");
                    String user = sc.nextLine();
                    System.out.println("Contrasenia:");
                    String password = sc.nextLine();
                    usuario = ConexionUsuario.ingresar(user, password);
                } else {
                    System.out.println("Menu:");
                    System.out.println("1: Opcion 1");
                    System.out.println("2: Opcion 2");
                    System.out.println("3: Opcion 3");
                    System.out.println("9: Cerrar");
                    try {
                        choice = sc.nextInt();
                        sc.nextLine();
                        switch (choice) {
                            case 0:
                                break;
                            case 1:

                                break;
                            case 2:

                                break;
                            case 9:
                                end = true;
                                break;
                            default:
                                throw new DatosInvalidosException();
                        }
                    } catch (InputMismatchException | IndexOutOfBoundsException e) {
                        sc.nextLine();
                        sc = new Scanner(System.in);
                        throw new DatosInvalidosException();
                    }
                }
            } catch (PersistenciaException e) {
                System.out.println("Error de servidor: " + e.getMessage());
            } catch (GestorException e) {
                System.out.println("Error de sistema: " + e.getMessage());
            } catch (LibreriaException e) {
                System.out.println("Error de archivos: " + e.getMessage());
            }
        }

    }
}
