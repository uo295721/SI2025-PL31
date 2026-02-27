DELETE FROM "Historial";
DELETE FROM "Incidencia";
DELETE FROM "Usuario";

INSERT INTO "Usuario" ("id_usuario", "nombre", "apellidos", "email", "rol") VALUES 
('T1', 'Ana', 'García', 'ana.tecnico@oviedo.es', 'TÉCNICO'),
('T2', 'Carlos', 'Pérez', 'carlos.tecnico@oviedo.es', 'TÉCNICO'),
('C1', 'Diego', 'Otero', 'diego.ciudadano@oviedo.es', 'CIUDADANO'),
('O1', 'Omar', 'Lobo', 'omar.operador@oviedo.es', 'OPERADOR');

INSERT INTO "Incidencia" 
("estado", "id_incidencia", "descripcion", "id_ciudadano", "localización", "tipo", "fecha", "id_operador", "id_tecnico", "coste", "horas_estimadas") 
VALUES 
('Validada', 101, 'Falla de alumbrado público en Plaza Mayor', 'C1', 'Plaza Mayor, 1', 'Electricidad', '2026-02-19', 'O1', 'T2', 100, 5),
('Asignada', 103, 'Falla de alumbrado público en Plaza Mayor', 'C1', 'Plaza Mayor, 1', 'Electricidad', '2026-02-19', 'O1', 'T1', 100, 5),
('Proceso', 102, 'Falla de alumbrado público en Plaza Mayor', 'C1', 'Plaza Mayor, 1', 'Electricidad', '2026-02-19', 'O1', 'T1', 220, 8);

INSERT INTO "Historial" ("id_modificacion", "id_incidencia", "id_usuario", "estado_nuevo", "fecha_modificacion", "comentario")
VALUES 
(1, 101, 'O1', 'Validada', '2026-02-19 10:00:00', 'Incidencia recibida y validada correctamente.'),
(2, 103, 'O1', 'Nueva', '2026-02-19 09:00:00', 'Registrada por ciudadano.'),
(3, 103, 'O1', 'Asignada', '2026-02-19 11:30:00', 'Asignada a Ana García para reparación urgente.');