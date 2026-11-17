import Exception.*;
import Model.*;
import Model.BDD.ConexionPasajero;
import Model.BDD.ConexionUsuario;
import Enum.*;

import java.sql.Date;
import java.util.*;

public class Main {
    public static void main(String args[]) {

        boolean end = false;
        int choice;
        Scanner sc = new Scanner(System.in);
        Usuario usuario = null;
        GestorHabitaciones gestorHabitaciones = new GestorHabitaciones();
        GestorPasajeros gestorPasajeros = new GestorPasajeros();
        GestorReservas gestorReservas = new GestorReservas();
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
                                System.out.println("3: Buscar habitacion por numero"); // muestra la habitacion, y se daran mas opciones
                                System.out.println("4: Cerrar");
                                choice = sc.nextInt();
                                sc.nextLine();

                                switch (choice) {
                                    case 1:
                                        System.out.println("Ingrese el nombre del pasajero: ");
                                        String nuevoNombre = sc.nextLine();
                                        System.out.println("Ingrese DNI: ");
                                        String nuevoDNI = sc.nextLine();
                                        System.out.println("Ingrese nacionalidad: ");
                                        String nuevoOrigen = sc.nextLine();
                                        System.out.println("Ingrese domicilio: ");
                                        String nuevoDomicilio= sc.nextLine();
                                        Pasajero nuevoPasajero = new Pasajero(nuevoDNI,nuevoNombre,nuevoOrigen,nuevoDomicilio);
                                        Pasajero.validarPasajero(nuevoPasajero);
                                        gestorPasajeros.guardar(nuevoPasajero);
                                        break;
                                    case 2:
                                        System.out.println("Ingrese el nombre del pasajero a buscar: ");
                                        String pasajeroBuscado = sc.nextLine();
                                        ArrayList<Pasajero> pasajeros = gestorPasajeros.buscarPorNombre(pasajeroBuscado);
                                        for (Pasajero item : pasajeros){
                                            System.out.println((pasajeros.indexOf(item)+1)+") "+item);
                                        }
                                        int encontrar = sc.nextInt();
                                        sc.nextLine();
                                        if (encontrar < 0 || encontrar > pasajeros.size()){
                                            throw new DatosInvalidosException();
                                        }
                                        Pasajero encontrado = pasajeros.get(encontrar-1);
                                        boolean regresar = false;
                                        while(!regresar) {
                                            encontrado = gestorPasajeros.buscarPorId(encontrado.getDni());
                                            System.out.println(encontrado);
                                            System.out.println("1: Crear nueva reserva"); // asociada al pasajero seleccionado
                                            System.out.println("2: Cambiar DNI"); // para editar dato ingresado incorectamente
                                            System.out.println("3: Cambiar nombre"); // para editar dato ingresado incorectamente
                                            System.out.println("4: Cambiar origen"); // para editar dato ingresado incorectamente
                                            System.out.println("5: Cambiar domicilio"); // para editar dato ingresado incorectamente
                                            System.out.println("6: Listar reservas"); // se listan los resultados y una vez selecionada se daran mas opciones:
                                            System.out.println("7: Volver al menu anterior");
                                            int opcion = sc.nextInt();
                                            sc.nextLine();

                                            switch (opcion) {
                                                case 1:
                                                    System.out.println("Ingrese el numero de habitacion: ");
                                                    int nuevoNumero = sc.nextInt();
                                                    sc.nextLine();
                                                    System.out.println("Ingrese la fecha de inicio (en formato yyyy-[m]m-[d]d): ");
                                                    String nuevaFecha = sc.nextLine();
                                                    Date nuevaDate = Date.valueOf(nuevaFecha);
                                                    System.out.println("Duracion de la estadia: ");
                                                    int nuevaDuracion = sc.nextInt();
                                                    sc.nextLine();
                                                    Reserva nuevaReserva = new Reserva(
                                                            ConexionPasajero.obtenerId(encontrado.getDni()),
                                                            gestorHabitaciones.buscarHabitacionPorNumero(nuevoNumero).getIdHabitacion(),
                                                            nuevaDate,
                                                            nuevaDuracion,
                                                            -1);
                                                    gestorReservas.guardar(nuevaReserva);

                                                    break;
                                                case 2:
                                                    System.out.println("Ingrese el nuevo DNI: ");
                                                    String dniNuevo = sc.nextLine();
                                                    Pasajero pasajeroConDNI = new Pasajero(dniNuevo,encontrado.getNombre(),encontrado.getOrigen(),encontrado.getDomicilio());
                                                    ConexionPasajero.modificarPasajero(pasajeroConDNI,encontrado.getDni());
                                                    encontrado = pasajeroConDNI;
                                                    break;
                                                case 3:
                                                    System.out.println("Ingrese el nuevo nombre: ");
                                                    String nombreNuevo = sc.nextLine();
                                                    Pasajero pasajeroConNombre = new Pasajero(encontrado.getDni(),nombreNuevo,encontrado.getOrigen(),encontrado.getDomicilio());
                                                    gestorPasajeros.guardar(pasajeroConNombre);
                                                    break;
                                                case 4:
                                                    System.out.println("Ingrese el nuevo origen: ");
                                                    String origenNuevo = sc.nextLine();
                                                    Pasajero pasajeroConOrigen = new Pasajero(encontrado.getDni(), encontrado.getNombre(), origenNuevo,encontrado.getDomicilio());
                                                    gestorPasajeros.guardar(pasajeroConOrigen);
                                                    break;
                                                case 5:
                                                    System.out.println("Ingrese el nuevo domicilio: ");
                                                    String domiciliouevo = sc.nextLine();
                                                    Pasajero pasajeroConDomicilio = new Pasajero(encontrado.getDni(), encontrado.getNombre(), encontrado.getOrigen(), domiciliouevo);
                                                    gestorPasajeros.guardar(pasajeroConDomicilio);
                                                    break;
                                                case 6:
                                                    ArrayList<Reserva> reservas = gestorReservas.buscarReservasPorPasajero(encontrado);
                                                    for (Reserva item : reservas){
                                                        System.out.println((reservas.indexOf(item)+1)+") "+item);
                                                    }
                                                    encontrar = sc.nextInt();
                                                    sc.nextLine();
                                                    if (encontrar < 0 || encontrar > reservas.size()){
                                                        throw new DatosInvalidosException();
                                                    }
                                                    Reserva eleguida = reservas.get(encontrar-1);
                                                    boolean regresarReserva = false;
                                                    while(!regresarReserva) {
                                                        eleguida = gestorReservas.buscarPorId(eleguida.getId());
                                                        System.out.println("1: Cambiar fecha inicio"); // para editar dato ingresado incorectamente
                                                        System.out.println("2: Cambiar duracion"); // para editar dato ingresado incorectamente
                                                        System.out.println("3: Cambiar habitacion asignada"); // para editar dato ingresado incorectamente
                                                        System.out.println("4: Borrar reserva");
                                                        System.out.println("5: Volver al menu anterior");
                                                        opcion = sc.nextInt();
                                                        sc.nextLine();
                                                        switch (opcion){
                                                            case 1:
                                                                System.out.println("Nueva fecha (en formato yyyy-[m]m-[d]d):");
                                                                eleguida.setFechaInicio(Date.valueOf(sc.nextLine()));
                                                                sc.nextLine();
                                                                gestorReservas.guardar(eleguida);
                                                                break;
                                                            case 2:
                                                                System.out.println("Nueva duracion en dias:");
                                                                eleguida.setDuracion(sc.nextInt());
                                                                sc.nextLine();
                                                                gestorReservas.guardar(eleguida);
                                                                break;
                                                            case 3:
                                                                System.out.println("Numero de habitacion:");
                                                                Habitacion nuevaHabitacion = gestorHabitaciones.buscarHabitacionPorNumero(sc.nextInt());
                                                                sc.nextLine();
                                                                eleguida.setHabitacion_id(nuevaHabitacion.getIdHabitacion());
                                                                gestorReservas.guardar(eleguida);
                                                                break;
                                                            case 4:
                                                                gestorReservas.eliminar(eleguida.getId());
                                                                regresarReserva = true;
                                                                break;
                                                            case 5:
                                                                regresarReserva = true;
                                                                break;
                                                            default:
                                                                throw new DatosInvalidosException();
                                                        }
                                                    }
                                                    break;
                                                case 7:
                                                    regresar = true;
                                                    break;
                                                default:
                                                    throw new DatosInvalidosException();
                                            }
                                        }
                                        break;
                                    case 3:
                                        System.out.println("Ingrese el numero de habitacion: ");
                                        int encontrarHabitacion = sc.nextInt();
                                        sc.nextLine();
                                        boolean regresarHabitacion = false;
                                        while(!regresarHabitacion) {
                                            Habitacion encontrada = gestorHabitaciones.buscarHabitacionPorNumero(encontrarHabitacion);
                                            System.out.println(encontrada);
                                            if (encontrada.getHabitacionEstado() == HabitacionEstado.FUERA_DE_SERVICIO){
                                                System.out.println("1: Asignar como fuera de servicio"); // Deja introducir un mensaje como motivo
                                            } else {
                                                System.out.println("1: Asignar como disponible o ocupada");
                                            }
                                            System.out.println("2: Selecionar una reserva"); // se listan las reservas relacionadas y una vez selecionada se dan las mismas opciones que antes para la reserva.
                                            System.out.println("3: Volver al menu anterior");
                                            int opcion = sc.nextInt();
                                            sc.nextLine();
                                            switch(opcion){
                                                case 1:
                                                    if (encontrada.getHabitacionEstado() == HabitacionEstado.FUERA_DE_SERVICIO){
                                                        System.out.println("Describir motivo del estado:");
                                                        String nuevoMotivo = sc.nextLine();
                                                        encontrada.setMotivo(nuevoMotivo);
                                                        encontrada.setHabitacionEstado(HabitacionEstado.FUERA_DE_SERVICIO);
                                                        System.out.println("La habitacion " + encontrada.getNumeroHabitacion() + " ha sido dada de fuera de servicio");
                                                    } else {
                                                        System.out.println("1: Asignar como ocupada");
                                                        System.out.println("2: Asignar como disponible");
                                                        opcion = sc.nextInt();
                                                        sc.nextLine();
                                                        if (opcion == 1 || opcion == 2){
                                                            if (opcion == 1){
                                                                encontrada.setHabitacionEstado(HabitacionEstado.OCUPADA);
                                                            } else {
                                                                encontrada.setHabitacionEstado(HabitacionEstado.DISPONIBLE);
                                                            }
                                                        }else {
                                                            throw new DatosInvalidosException();
                                                        }
                                                        encontrada.setHabitacionEstado(HabitacionEstado.DISPONIBLE);
                                                    }
                                                    gestorHabitaciones.guardar(encontrada);
                                                    break;
                                                case 2:
                                                    ArrayList<Reserva> reservas = gestorReservas.buscarReservasPorHabitacion(encontrada);
                                                    for (Reserva item : reservas){
                                                        System.out.println((reservas.indexOf(item)+1)+") "+item);
                                                    }
                                                    encontrar = sc.nextInt();
                                                    sc.nextLine();
                                                    if (encontrar < 0 || encontrar > reservas.size()){
                                                        throw new DatosInvalidosException();
                                                    }
                                                    Reserva eleguida = reservas.get(encontrar-1);
                                                    boolean regresarReserva = false;
                                                    while(!regresarReserva) {
                                                        eleguida = gestorReservas.buscarPorId(eleguida.getId());
                                                        System.out.println("1: Cambiar fecha inicio"); // para editar dato ingresado incorectamente
                                                        System.out.println("2: Cambiar duracion"); // para editar dato ingresado incorectamente
                                                        System.out.println("3: Cambiar pasajero asignado"); // para editar dato ingresado incorectamente
                                                        System.out.println("4: Borrar reserva");
                                                        System.out.println("5: Volver al menu anterior");
                                                        opcion = sc.nextInt();
                                                        sc.nextLine();
                                                        switch (opcion){
                                                            case 1:
                                                                System.out.println("Nueva fecha (en formato yyyy-[m]m-[d]d):");
                                                                eleguida.setFechaInicio(Date.valueOf(sc.nextLine()));
                                                                sc.nextLine();
                                                                gestorReservas.guardar(eleguida);
                                                                break;
                                                            case 2:
                                                                System.out.println("Nueva duracion en dias:");
                                                                eleguida.setDuracion(sc.nextInt());
                                                                sc.nextLine();
                                                                gestorReservas.guardar(eleguida);
                                                                break;
                                                            case 3:
                                                                System.out.println("Ingrese el nombre del pasajero a buscar: ");
                                                                pasajeroBuscado = sc.nextLine();
                                                                pasajeros = gestorPasajeros.buscarPorNombre(pasajeroBuscado);
                                                                for (Pasajero item : pasajeros){
                                                                    System.out.println((pasajeros.indexOf(item)+1)+") "+item);
                                                                }
                                                                encontrar = sc.nextInt();
                                                                sc.nextLine();
                                                                if (encontrar < 0 || encontrar > pasajeros.size()){
                                                                    throw new DatosInvalidosException();
                                                                }
                                                                encontrado = pasajeros.get(encontrar-1);
                                                                eleguida.setPasajero_id(ConexionPasajero.obtenerId(encontrado.getDni()));
                                                                gestorReservas.guardar(eleguida);
                                                                break;
                                                            case 4:
                                                                gestorReservas.eliminar(eleguida.getId());
                                                                regresarReserva = true;
                                                                break;
                                                            case 5:
                                                                regresarReserva = true;
                                                                break;
                                                            default:
                                                                throw new DatosInvalidosException();
                                                        }
                                                    }
                                                    break;
                                                case 3:
                                                    regresarHabitacion=true;
                                                    break;
                                                default:
                                                    throw new DatosInvalidosException();
                                            }
                                        }

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
                                System.out.println("2: Buscar usuario");// por nombre. se listan los resultados y una vez selecionado se daran mas opciones
                                System.out.println("3: Crear nueva habitacion");
                                // despues mismas opciones que conserje, pero con un par mas de opciones en los menus despues de selccionar, como borrar pasajero o habitacion
                                System.out.println("4: Aniadir nuevo pasajero");
                                System.out.println("5: Buscar pasajero por nombre"); // se listan los resultados y una vez selecionado se daran mas opciones:
                                System.out.println("6: Buscar habitacion por numero"); // muestra la habitacion, y se daran mas opciones
                                System.out.println("9: Cerrar");
                                choice = sc.nextInt();
                                sc.nextLine();
                                switch (choice) {
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
                                        ArrayList<Usuario> usuarios = ConexionUsuario.buscarUsuarios(usuarioBuscado);

                                        for (Usuario item : usuarios){
                                            System.out.println((usuarios.indexOf(item)+1)+") "+item);
                                        }
                                        int encontrar = sc.nextInt();
                                        sc.nextLine();
                                        if (encontrar < 0 || encontrar > usuarios.size()){
                                            throw new DatosInvalidosException();
                                        }
                                        Usuario encontrado = usuarios.get(encontrar-1);
                                        boolean regresar = false;
                                        System.out.println(encontrado);
                                        while(!regresar){
                                            encontrado = ConexionUsuario.buscarUsuario(encontrado.getUsuario());
                                            System.out.println(encontrado);
                                            System.out.println("Opciones:");
                                            System.out.println("1. Cambiar nombre.");
                                            System.out.println("2. Cambiar contrasenia.");
                                            System.out.println("3. Cambiar tipo.");
                                            System.out.println("4. Eliminar usuario.");
                                            System.out.println("5. Volver al menu anterior.");
                                            int opcion = sc.nextInt();
                                            sc.nextLine();

                                            switch(opcion){
                                                case 1:
                                                    System.out.println("Ingrese el nuevo nombre: ");
                                                    String nombreNuevo = sc.nextLine();

                                                    Usuario usuarioConNuevoNombre = new Usuario(encontrado.getId_usuario(), nombreNuevo,
                                                            encontrado.getContrasenia(), encontrado.getTipo_usuario());

                                                    if(ConexionUsuario.modificarUsuario(usuarioConNuevoNombre, encontrado.getUsuario())){
                                                        encontrado = usuarioConNuevoNombre;
                                                        System.out.println("Se logro el cambio de nombre a " + encontrado.getUsuario());
                                                    } else{
                                                        System.out.println("No se logro el cambio de nombre.");
                                                    }
                                                    break;
                                                case 2:
                                                    System.out.println("Ingrese la nueva contrasenia: ");
                                                    String contraseniaNueva = sc.nextLine();
                                                    Usuario usuarioNuevaContrasenia = new Usuario(encontrado.getId_usuario(),
                                                            encontrado.getUsuario(), contraseniaNueva, encontrado.getTipo_usuario());
                                                    if(ConexionUsuario.modificarUsuario(usuarioNuevaContrasenia, encontrado.getUsuario())){
                                                        encontrado = usuarioNuevaContrasenia;
                                                        System.out.println("Se logro el cambio de contrasenia, la nueva contraseña es: " + encontrado.getContrasenia());
                                                    } else{
                                                        System.out.println("No se logro el cambio de usuario.");
                                                    }
                                                case 3:
                                                    System.out.println("Elija el nuevo tipo de usuario: ");
                                                    System.out.println("1. Administrador");
                                                    System.out.println("2. Conserje");
                                                    int tipoEleccion = sc.nextInt();
                                                    sc.nextLine();

                                                    TipoUsuario tipoNuevo;
                                                    if(tipoEleccion == 1){
                                                        tipoNuevo = TipoUsuario.ADMINISTRADOR;
                                                    } else if(tipoEleccion == 2){
                                                        tipoNuevo = TipoUsuario.CONSERJE;
                                                    } else{
                                                        throw new DatosInvalidosException();
                                                    }

                                                    Usuario usuarioConTipoActualizado = new Usuario(encontrado.getId_usuario(), encontrado.getUsuario()
                                                            ,encontrado.getContrasenia(), tipoNuevo);
                                                    if(ConexionUsuario.modificarUsuario(usuarioConTipoActualizado, encontrado.getUsuario())){
                                                        encontrado = usuarioConTipoActualizado;
                                                        System.out.println("Se logro el cambio. " + encontrado.getUsuario() + " ahora es de tipo: " + encontrado.getTipo_usuario());
                                                    } else{
                                                        System.out.println("No se logro realizar el cambio.");
                                                    }
                                                case 4:
                                                    System.out.println("Confirme si desea eliminar al usuario: " + encontrado.getUsuario());
                                                    System.out.println("1. Confirmar");
                                                    System.out.println("1. Cancelar");
                                                    int confirmar = sc.nextInt();
                                                    sc.nextLine();
                                                    if (confirmar == 1) {
                                                        ConexionUsuario.eliminarUsuario(encontrado.getId_usuario());
                                                        System.out.println("Se elimino al usuario " + encontrado.getUsuario());
                                                        regresar = true;
                                                    } else{
                                                        System.out.println("No se elimino al usuario.");
                                                    }
                                                    break;
                                                case 5:
                                                    regresar = true;
                                                    break;

                                                default:
                                                    throw new DatosInvalidosException();
                                            }
                                        }

                                        break;
                                    case 3:
                                        System.out.println("Ingrese el numero de la habitacion: ");
                                        int numHabitacion = sc.nextInt();
                                        sc.nextLine();
                                        System.out.println("Ingrese el precio por dia: ");
                                        double precioXDia = sc.nextDouble();
                                        sc.nextLine();

                                        Habitacion nuevaHabitacion = new Habitacion(numHabitacion, precioXDia);
                                        if(gestorHabitaciones.guardar(nuevaHabitacion)){
                                            gestorHabitaciones.listarDisponibles();
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
