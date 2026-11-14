import Exception.*;
import Model.BDD.ConexionUsuario;
import Model.Habitacion;
import Model.Usuario;
import Enum.*;
import Model.GestorHabitaciones;

import java.util.InputMismatchException;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static void main(String args[]) {

        boolean end = false;
        int choice;
        Scanner sc = new Scanner(System.in);
        Usuario usuario = null;
        GestorHabitaciones gestorHabitaciones = new GestorHabitaciones();
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
                    try {
                        switch (usuario.getTipo_usuario()) {
                            case CONSERJE -> {
                                System.out.println("Menu Empleado:"); // conserjes y recepcionistas
                                System.out.println("1: Aniadir nuevo pasajero");
                                System.out.println("2: Buscar pasajero por nombre"); // se listan los resultados y una vez selecionado se daran mas opciones:
                                    // System.out.println("1: Crear nueva reserva"); // asociada al pasajero seleccionado
                                        // se verifican los valores ingresados (la fecha inicial es en la fecha actual o despues, y la habitacion se seleciona de una lista de habitaciones disponibles durante la duracion de esta reserva)
                                    // System.out.println("2: Cambiar DNI"); // para editar dato ingresado incorectamente
                                    // System.out.println("3: Cambiar nombre"); // para editar dato ingresado incorectamente
                                    // System.out.println("4: Cambiar origen"); // para editar dato ingresado incorectamente
                                    // System.out.println("5: Cambiar domicilio"); // para editar dato ingresado incorectamente
                                    // System.out.println("6: Volver al menu anterior");
                                System.out.println("3: Buscar reserva por fecha");// se listan los resultados en el rango de fechas dado y una vez selecionada se daran mas opciones:
                                    // System.out.println("1: Crear nueva reserva"); // asociada al pasajero seleccionado
                                    // System.out.println("2: Cambiar fecha inicio"); // para editar dato ingresado incorectamente
                                    // System.out.println("3: Cambiar duracion"); // para editar dato ingresado incorectamente
                                    // System.out.println("4: Cambiar habitacion asignada"); // para editar dato ingresado incorectamente
                                    // System.out.println("5: Borrar reserva"); // para editar dato ingresado incorectamente
                                    // System.out.println("6: Volver al menu anterior");
                                System.out.println("4: Buscar habitacion por numero"); // muestra la habitacion, su estado(en caso de no ser fuera de servicio el mismo es calculado automaticamente con la fecha actual y las reservas (si una de las reservas de esta habitacion tiene su duracion en el momento actual entonces la misma esta ocupada) y sus reservas asignadas
                                    // System.out.println("1: Asignar como fuera de servicio"); // Deja introducir un mensaje como motivo, si la habitacion ya esta en ese estado en vez dice: "1: Asignar como disponible"
                                    // System.out.println("2: Selecionar una reserva"); // se listan las reservas relacionadas y una vez selecionada se dan las mismas opciones que antes para la reserva.
                                    // System.out.println("3: Volver al menu anterior");
                                System.out.println("5: Cerrar");
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
                            }
                            case ADMINISTRADOR -> {
                                System.out.println("Menu Admin:"); // usuario inicial/Admins
                                System.out.println("1: Crear nuevo usuario");
                                System.out.println("2: Buscar usuario");// por nombre? se listan los resultados y una vez selecionado se daran mas opciones
                                    // System.out.println("1: Cambiar nombre"); // para editar dato ingresado incorectamente
                                    // System.out.println("2: Cambiar contrasenia"); // para editar dato ingresado incorectamente
                                    // System.out.println("3: Cambiar tipo"); // para editar dato ingresado incorectamente
                                    // System.out.println("4: Volver al menu anterior");
                                System.out.println("3: Crear nueva habitacion");
                                // despues mismas opciones que conserje? pero con un par mas de opciones en los menus despues de selccionar? como borrar pasajero o habitacion
                                System.out.println("9: Cerrar");
                                choice = sc.nextInt();
                                sc.nextLine();
                                switch (choice) {
                                    case 0:
                                        break;
                                    case 1:
                                        System.out.println("Ingrese el nombre de usuario: ");
                                        String nuevoUser = sc.nextLine();
                                        System.out.println("Ingrese la contrasenia: ");
                                        String nuevaContrasenia = sc.nextLine();
                                        System.out.println("Escoja el tipo de Usuario:");
                                        System.out.println("1. Administrador");
                                        System.out.println("2. Conserje");
                                        int tipo = sc.nextInt();
                                        sc.nextLine();

                                        TipoUsuario tipoUsuario;
                                        if(tipo == 1){
                                            tipoUsuario = TipoUsuario.ADMINISTRADOR;
                                        } else if (tipo == 2){
                                            tipoUsuario = TipoUsuario.CONSERJE;
                                        } else{
                                            throw new DatosInvalidosException();
                                        }
                                        if(ConexionUsuario.crearUsuario(nuevoUser,nuevaContrasenia,tipoUsuario)){
                                            System.out.println("Se ha creado al usuario " + nuevoUser + " correctamente.");
                                        } else{
                                            System.out.println("No se pudo crear al usuario " + nuevoUser);
                                        }

                                        break;
                                    case 2:
                                        System.out.println("Ingrese el nombre del usuario a buscar: ");
                                        String usuarioBuscado = sc.nextLine();

                                        Usuario encontrado = ConexionUsuario.buscarUsuario(usuarioBuscado);
                                        if(encontrado == null){
                                            System.out.println("No existe un usuario con el nombre " + usuarioBuscado);
                                        } else{
                                            System.out.println(encontrado);
                                        }
                                        break;
                                    case 3:
                                        //sin terminar
                                        System.out.println("Ingrese el numero de la habitacion: ");
                                        int numHabitacion = sc.nextInt();
                                        sc.nextLine();
                                        System.out.println("Ingrese el precio por dia: ");
                                        double precioXDia = sc.nextDouble();
                                        sc.nextLine();

                                        Habitacion nuevaHabitacion = new Habitacion(numHabitacion, precioXDia);
                                        if(gestorHabitaciones.guardar(nuevaHabitacion)){
                                            System.out.println("Se creo correctamente la habitacion numero "
                                                    + nuevaHabitacion.getNumeroHabitacion() + " con el precio por dia establecido en: " + nuevaHabitacion.getPrecioPorDia());
                                        } else{
                                            System.out.println("No se pudo crear la habitacion");
                                        }
                                        break;
                                    case 9:
                                        end = true;
                                        break;
                                    default:
                                        throw new DatosInvalidosException();
                                }
                            }
                            case null, default -> {
                                throw new NoEncontradoException();
                            }

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
