DELETE FROM "Historial";
DELETE FROM "Incidencia";
DELETE FROM "Usuario";

INSERT INTO "Usuario" ("id_usuario", "nombre", "apellidos", "email", "rol") VALUES 
('T1', 'Ana', 'García', 'ana.tecnico@oviedo.es', 'TÉCNICO'),
('T2', 'Carlos', 'Pérez', 'carlos.tecnico@oviedo.es', 'TÉCNICO'),
('C1', 'Diego', 'Otero', 'diego.ciudadano@oviedo.es', 'CIUDADANO'),
('O1', 'Omar', 'Lobo', 'omar.operador@oviedo.es', 'OPERADOR');

INSERT INTO "Incidencia" 
("estado", "id_incidencia", "descripcion", "id_ciudadano", "localización", "tipo", "fecha") 
VALUES 
('Validada', 101, 'Falla de alumbrado público en Plaza Mayor', 'C1', 'Plaza Mayor, 1', 'Electricidad', '2026-02-19');

INSERT INTO "Historial" ("id_modificacion", "id_incidencia", "id_usuario", "estado_nuevo", "fecha_modificacion", "comentario")
VALUES (1, 101, 'O1', 'Validada', '2026-02-19', 'Incidencia recibida y validada correctamente.');