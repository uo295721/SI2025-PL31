DELETE FROM "Historial";
DELETE FROM "Incidencia";
DELETE FROM "Usuario";
DELETE FROM "TipoIncidencia";
DELETE FROM "Zona";
DELETE FROM "Tecnico_Especialidad";
DELETE FROM "Asignacion_Incidencia";

INSERT INTO "TipoIncidencia" ("id_tipo", "nombre") VALUES
(1, 'Electricidad'),
(2, 'Mobiliario'),
(3, 'Fontanería'),
(4, 'Obras');

INSERT INTO "Zona" ("id_zona", "nombre") VALUES 
('Z1', 'Sin zona'),
('Z2', 'Zona Residencial-1'),
('Z3', 'Zona Residencial-2'),
('Z4', 'Zona Portuaria-1'),
('Z5', 'Zona Portuaria-2'),
('Z6', 'Zona Central-1'),
('Z7', 'Zona Central-2');

INSERT INTO "Usuario" ("id_usuario", "nombre", "apellidos", "email", "rol", "id_tipo") VALUES 
('T1', 'Ana', 'García', 'ana.tecnico@oviedo.es', 'TÉCNICO', 1),
('T2', 'Carlos', 'Pérez', 'carlos.tecnico@oviedo.es', 'TÉCNICO', 2),
('T3', 'Emil', 'Bauer', 'emil.bauer@oviedo.es', 'TÉCNICO', 1),
('C1', 'Diego', 'Otero', 'diego.ciudadano@oviedo.es', 'CIUDADANO', NULL),
('C2', 'Marco', 'Diaz', 'marco.ciudadano@oviedo.es', 'CIUDADANO', NULL),
('R1', 'Ines', 'Canteli', 'ines.responsable@oviedo.es', 'RESPONSABLE', 1),
('R2', 'Cristiano', 'Ronaldo', 'cristiano.responsable@oviedo.es', 'RESPONSABLE', 2),
('O1', 'Omar', 'Lobo', 'omar.operador@oviedo.es', 'OPERADOR', NULL),
('O2', 'Marco', 'Estrada', 'marco.operador@oviedo.es', 'OPERADOR', NULL);

INSERT INTO "Incidencia" 
("estado", "id_incidencia", "descripcion", "id_ciudadano", "localizacion", "id_tipo", "fecha", "fecha_resolucion", "id_operador", "id_tecnico", "coste", "horas_estimadas") 
VALUES 
('Validada', 101, 'Falla de alumbrado público en Plaza Mayor', 'C1', 'Zona Residencial-1', 1, '2026-02-19', NULL, 'O1', 'T2', 100, 5),
('Validada', 107, 'Banco roto en el Parque San Francisco', 'C1', 'Zona Portuaria-1', 2, '2026-02-20', NULL, 'O1', 'T2', 50, 2),
('Validada', 108, 'Baldosas sueltas con riesgo de caída', 'C2', 'Zona Residencial-2', 4, '2026-02-21', NULL, 'O2', 'T2', 150, 4),
('Validada', 109, 'Cambio de bombilla en Calle Principal', 'C1', 'Zona Residencial-1', 1, '2026-02-19', NULL, 'O1', 'T2', 100, 5),
('Asignada', 103, 'Falla de alumbrado público en Plaza Mayor', 'C1', 'Zona Residencial-1', 1, '2026-02-19', NULL, 'O1', 'T1', 100, 5),
('Proceso', 102, 'Falla de alumbrado público en Plaza Mayor', 'C2', 'Zona Portuaria-2', 1, '2026-02-19', NULL, 'O1', 'T1', 220, 8),
('Nueva', 104, 'Fuga de agua en Calle Uría', 'C2', 'Zona Central-1', 3, '2026-02-19', NULL, 'O2', 'T2', 100, 5),
('Nueva', 105, 'Socavón en calzada', 'C2', 'Zona Central-2', 4, '2026-02-19', NULL, 'O2', 'T1', 100, 5),
('Resuelta', 106, 'Semáforo fundido', 'C2', 'Zona Central-1', 1, '2026-02-19', '2026-02-19', 'O1', 'T1', 220, 8),
('Resuelta', 113, 'Red de semaforos caía', 'C2', 'Zona Central-2', 1, '2026-02-19', '2026-02-19', 'O1', 'T1', 4900, 20),
('Resuelta', 112, 'Puente en mal estado', 'C2', 'Zona Portuaria-1', 2, '2026-02-19', '2026-02-19', 'O1', 'T1', 50000, 42),
('Rechazada', 110, 'Pintada en monumento histórico', 'C1', 'Zona Central-1', 2, '2026-03-01', NULL, 'O1', NULL, 0, 0),
('Cerrada', 111, 'Limpieza de rastrojo en solares', 'C2', 'Zona Residencial-2', 4, '2026-03-05', '2026-03-10', 'O2', 'T2', 300, 10);

INSERT INTO "Historial" ("id_modificacion", "id_incidencia", "id_usuario", "estado_nuevo", "fecha_modificacion", "comentario")
VALUES 
(1, 101, 'O1', 'Validada', '2026-02-19 10:00:00', 'Incidencia recibida y validada correctamente.'),
(2, 103, 'C1', 'Nueva', '2026-02-19 09:00:00', 'Registrada por ciudadano.'),
(3, 103, 'O1', 'Asignada', '2026-02-19 11:30:00', 'Asignada a Ana García para reparación urgente.'),
(4, 108, 'O2', 'Validada', '2026-02-21 08:45:00', 'Validación de desperfectos en pavimento.'),
(5, 107, 'O1', 'Validada', '2026-02-20 12:00:00', 'Validado para reparación de mobiliario.'),
(6, 102, 'C2', 'Nueva', '2026-02-19 08:30:00', 'Reportado por ciudadano.'),
(7, 102, 'O1', 'Proceso', '2026-02-19 14:00:00', 'Iniciada reparación por el equipo técnico.'),
(8, 104, 'C2', 'Nueva', '2026-02-19 07:00:00', 'Incidencia de fontanería registrada.'),
(9, 105, 'C2', 'Nueva', '2026-02-19 07:15:00', 'Aviso de socavón recibido.'),
(10, 106, 'T1', 'Resuelta', '2026-02-19 20:00:00', 'Reparación finalizada con éxito.'),
(11, 110, 'O1', 'Rechazada por Operador', '2026-03-02 09:00:00', 'No es competencia municipal, corresponde a Patrimonio.'),
(12, 111, 'R1', 'Cerrada', '2026-03-10 15:00:00', 'Control de calidad superado. Incidencia archivada.');

INSERT INTO "Tecnico_Especialidad" ("id_usuario", "id_tipo") VALUES ('T1', 1);
INSERT INTO "Tecnico_Especialidad" ("id_usuario", "id_tipo") VALUES ('T2', 2);
INSERT INTO "Tecnico_Especialidad" ("id_usuario", "id_tipo") VALUES ('T1', 3);
INSERT INTO "Tecnico_Especialidad" ("id_usuario", "id_tipo") VALUES ('T3', 1);

INSERT INTO "Asignacion_Incidencia" ("id_incidencia", "id_tecnico") VALUES 
(103, 'T1'), 
(102, 'T1'), 
(104, 'T2'); 

INSERT INTO "TareaDiaria" ("id_incidencia", "id_tecnico", "fecha", "descripcion_tarea", "horas_dedicadas") VALUES 
(101, 'T1', '2026-02-19', 'Revisión cables', 4.0),
(102, 'T3', '2026-02-20', 'Sustitución focos', 6.0),
(107, 'T2', '2026-02-21', 'Barnizar banco', 5.5),
(104, 'T2', '2026-02-19', 'Reparación de fuga en tubería principal', 4.0),
(105, 'T1', '2026-02-20', 'Vallado y preparación del terreno', 5.0),
(111, 'T2', '2026-03-06', 'Desbroce y limpieza mecánica', 8.0);

DELETE FROM "Presupuesto";
INSERT INTO "Presupuesto" ("id_tipo", "importe_total", "importe_consumido", "fecha_inicio", "fecha_fin") 
VALUES (1, 5000.0, 0.0, '2026-01-01', '2026-12-31');
INSERT INTO "Presupuesto" ("id_tipo", "importe_total", "importe_consumido", "fecha_inicio", "fecha_fin") 
VALUES (2, 2000.0, 0.0, '2026-01-01', '2026-12-31');
INSERT INTO "Presupuesto" ("id_tipo", "importe_total", "importe_consumido", "fecha_inicio", "fecha_fin") 
VALUES (3, 3000.0, 0.0, '2026-01-01', '2026-12-31');