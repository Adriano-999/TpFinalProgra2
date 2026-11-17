drop database 2026_C7_Feliziani_Benitez_Raimondo_TP_Final_Progra;
CREATE DATABASE IF NOT EXISTS 2026_C7_Feliziani_Benitez_Raimondo_TP_Final_Progra
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_0900_ai_ci;
use 2026_C7_Feliziani_Benitez_Raimondo_TP_Final_Progra;

-- Create tables de los check table de los conexion: --

create table if not exists usuarios (
    id_usuario int auto_increment primary key not null ,
    usuario varchar(20) unique not null,
    contrasenia varchar(20) not null,
    tipo_usuario varchar(20) not null);

INSERT INTO usuarios (usuario,contrasenia,tipo_usuario)
    SELECT 'admin', 'admin', 'ADMINISTRADOR'
    WHERE NOT EXISTS (SELECT * FROM usuarios);

create table if not exists pasajeros (
    id_pasajero int auto_increment primary key not null ,
    dni varchar(10) not null unique,
    nombre varchar(20) not null,
    origen varchar(20) not null,
    domicilio varchar(20) not null);

create table if not exists habitaciones (
    id_habitacion int auto_increment primary key not null ,
    numero_habitacion int unique,
    precio_por_dia double,
    habitacion_estado varchar(20),
    motivo varchar(50));

create table if not exists reservas (
    id_reserva int auto_increment primary key not null ,
    fecha_inicio date,
    duracion int,
    id_pasajero int,
    id_habitacion int,
    FOREIGN KEY (id_pasajero) REFERENCES pasajeros(id_pasajero) on delete cascade,
    FOREIGN KEY (id_habitacion) REFERENCES habitaciones(id_habitacion) on delete cascade);

-- Inserts con valores predeterminados para pruebas: --

INSERT INTO usuarios (usuario, contrasenia, tipo_usuario) VALUES
    ('juan', 'juan123', 'CONSERJE'),
    ('maria', 'maria123', 'ADMINISTRADOR'),
    ('pedro', 'pedro123', 'CONSERJE'),
    ('lucia', 'lucia123', 'CONSERJE'),
    ('carlos', 'carlos123', 'ADMINISTRADOR'),
    ('ana', 'ana123', 'CONSERJE');

INSERT INTO pasajeros (dni, nombre, origen, domicilio) VALUES
    ('40123456', 'Juan Perez', 'Buenos Aires', 'Calle 123'),
    ('40234567', 'Maria Lopez', 'Cordoba', 'San Martin 456'),
    ('40345678', 'Pedro Gomez', 'Rosario', 'Belgrano 789'),
    ('40456789', 'Lucia Diaz', 'Mendoza', 'Rivadavia 321'),
    ('40567890', 'Carlos Ruiz', 'La Plata', 'Mitre 654'),
    ('40678901', 'Ana Torres', 'Olavarria', 'Moreno 987');

INSERT INTO habitaciones (numero_habitacion, precio_por_dia, habitacion_estado, motivo) VALUES
    (101, 15000, 'DISPONIBLE', ''),
    (102, 18000, 'OCUPADA', ''),
    (103, 20000, 'DISPONIBLE', ''),
    (104, 12000, 'FUERA_DE_SERVICIO', 'Reparacion de baño'),
    (105, 25000, 'DISPONIBLE', ''),
    (106, 22000, 'OCUPADA', '');

INSERT INTO reservas (fecha_inicio, id_pasajero, duracion, id_habitacion) VALUES
    ('2026-11-17', 1,3, 2),
    ('2026-11-18', 2,2, 4),
    ('2026-11-19', 3,1, 1),
    ('2026-11-20', 4,3, 3),
    ('2026-11-21', 5,2, 6),
    ('2026-11-22', 6,1, 5);