DELETE FROM "Historial";
DELETE FROM "Incidencia";
DELETE FROM "Usuario";

INSERT INTO "Usuario" ("id_usuario", "nombre", "apellidos", "email", "rol") VALUES 
('T1', 'Ana', 'García', 'ana.tecnico@oviedo.es', 'TÉCNICO'),
('T2', 'Carlos', 'Pérez', 'carlos.tecnico@oviedo.es', 'TÉCNICO'),
('C1', 'Diego', 'Otero', 'diego.ciudadano@oviedo.es', 'CIUDADANO'),
('C2', 'Marco', 'Diaz', 'marco.ciudadano@oviedo.es', 'CIUDADANO'),
('R1', 'Ines', 'Canteli', 'ines.responsable@oviedo.es', 'RESPONSABLE'),
('O1', 'Omar', 'Lobo', 'omar.operador@oviedo.es', 'OPERADOR'),
('O2', 'Marco', 'Estrada', 'marco.operador@oviedo.es', 'OPERADOR');

INSERT INTO "Incidencia" 
("estado", "id_incidencia", "descripcion", "id_ciudadano", "localización", "tipo", "fecha", "id_operador", "id_tecnico", "coste", "horas_estimadas") 
VALUES 
('Validada', 101, 'Falla de alumbrado público en Plaza Mayor', 'C1', 'Plaza Mayor, 1', 'Electricidad', '2026-02-19', 'O1', 'T2', 100, 5),
('Validada', 107, 'Banco roto en el Parque San Francisco', 'C1', 'Parque San Francisco', 'Mobiliario', '2026-02-20', 'O1', 'T2', 50, 2),
('Validada', 108, 'Baldosas sueltas con riesgo de caída', 'C2', 'Calle Melquiades Álvarez, 4', 'Obras', '2026-02-21', 'O2', 'T2', 150, 4),
('Asignada', 103, 'Falla de alumbrado público en Plaza Mayor', 'C1', 'Plaza Mayor, 1', 'Electricidad', '2026-02-19', 'O1', 'T1', 100, 5),
('Proceso', 102, 'Falla de alumbrado público en Plaza Mayor', 'C2', 'Plaza Mayor, 1', 'Electricidad', '2026-02-19', 'O1', 'T1', 220, 8),
('Nueva', 104, 'Fuga de agua en Calle Uría', 'C2', 'Calle Uría, 10', 'Fontanería', '2026-02-19', 'O2', 'T2', 100, 5),
('Nueva', 105, 'Socavón en calzada', 'C2', 'Avenida Galicia, 5', 'Obras', '2026-02-19', 'O2', 'T1', 100, 5),
('Resuelta', 106, 'Semáforo fundido', 'C2', 'Calle Jovellanos, 2', 'Electricidad', '2026-02-19', 'O1', 'T1', 220, 8);

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
(10, 106, 'T1', 'Resuelta', '2026-02-19 20:00:00', 'Reparación finalizada con éxito.');

INSERT INTO "Zona" ("id_zona", "nombre") VALUES 
('Z1', 'Sin zona'),
('Z2', 'Zona Residencial-1'),
('Z3', 'Zona Residencial-2'),
('Z4', 'Zona Portuaria-1'),
('Z5', 'Zona Portuaria-2'),
('Z6', 'Zona Central-1'),
('Z7', 'Zona Central-2');