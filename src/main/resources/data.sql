DELETE FROM "Historial";
DELETE FROM "Incidencia";
DELETE FROM "Usuario";
DELETE FROM "TipoIncidencia";

INSERT INTO "Usuario" ("id_usuario", "nombre", "apellidos", "email", "rol", "id_tipo") VALUES 
('T1', 'Ana', 'García', 'ana.tecnico@oviedo.es', 'TÉCNICO', 1),
('T2', 'Carlos', 'Pérez', 'carlos.tecnico@oviedo.es', 'TÉCNICO', 2),
('C1', 'Diego', 'Otero', 'diego.ciudadano@oviedo.es', 'CIUDADANO', NULL),
('C2', 'Marco', 'Diaz', 'marco.ciudadano@oviedo.es', 'CIUDADANO', NULL),
('R1', 'Ines', 'Canteli', 'ines.responsable@oviedo.es', 'RESPONSABLE', 1),
('O1', 'Omar', 'Lobo', 'omar.operador@oviedo.es', 'OPERADOR', NULL),
('O2', 'Marco', 'Estrada', 'marco.operador@oviedo.es', 'OPERADOR', NULL);

INSERT INTO "Incidencia" 
("estado", "id_incidencia", "descripcion", "id_ciudadano", "localización", "zona", "id_tipo", "fecha", "fecha_resolucion", "id_operador", "id_tecnico", "coste", "horas_estimadas") 
VALUES 
('Validada', 101, 'Falla de alumbrado público en Plaza Mayor', 'C1', 'Plaza Mayor, 1', 'Centro', 1, '2026-02-19', NULL, 'O1', 'T2', 100, 5),
('Validada', 107, 'Banco roto en el Parque San Francisco', 'C1', 'Parque San Francisco', 'Centro', 2, '2026-02-20', NULL, 'O1', 'T2', 50, 2),
('Validada', 108, 'Baldosas sueltas con riesgo de caída', 'C2', 'Calle Melquiades Álvarez, 4', 'Centro', 4, '2026-02-21', NULL, 'O2', 'T2', 150, 4),
('Asignada', 103, 'Falla de alumbrado público en Plaza Mayor', 'C1', 'Plaza Mayor, 1', 'Centro', 1, '2026-02-19', NULL, 'O1', 'T1', 100, 5),
('Proceso', 102, 'Falla de alumbrado público en Plaza Mayor', 'C2', 'Plaza Mayor, 1', 'Centro', 1, '2026-02-19', NULL, 'O1', 'T1', 220, 8),
('Nueva', 104, 'Fuga de agua en Calle Uría', 'C2', 'Calle Uría, 10', 'Centro', 'Fontanería', '2026-02-19', NULL, 'O2', 'T2', 100, 5),
('Nueva', 105, 'Socavón en calzada', 'C2', 'Avenida Galicia, 5', 'Oeste', 'Obras', '2026-02-19', NULL, 'O2', 'T1', 100, 5),
('Resuelta', 106, 'Semáforo fundido', 'C2', 'Calle Jovellanos, 2', 'Centro', 1, '2026-02-19', '2026-02-19', 'O1', 'T1', 220, 8);

INSERT INTO "Historial" ("id_modificacion", "id_incidencia", "id_usuario", "estado_nuevo", "fecha_modificacion", "comentario")
VALUES 
(1, 101, 'O1', 'Validada', '2026-02-19 10:00:00', 'Incidencia recibida y validada correctamente.'),
(10, 106, 'T1', 'Resuelta', '2026-02-19 20:00:00', 'Reparación finalizada con éxito.');

INSERT INTO "TipoIncidencia" ("nombre") VALUES
('Electricidad'),
('Mobiliario'),
('Fontanería'),
('Obras');