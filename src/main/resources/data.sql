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
("estado", "id_incidencia", "descripcion", "id_ciudadano", "localización", "tipo", "fecha","id_operador",'id_tecnico','coste','horas_estimadas') 
VALUES 
('Validada', 101, 'Falla de alumbrado público en Plaza Mayor', 'C1', 'Plaza Mayor, 1', 'Electricidad', '2026-02-19','O2','T2','100','5'),
('Asignada', 103, 'Falla de alumbrado público en Plaza Mayor', 'C1', 'Plaza Mayor, 1', 'Electricidad', '2026-02-19','O2','T1','100','5'),
('Resuelta', 106, 'Falla de alumbrado público en Plaza Mayor', 'C2', 'Plaza Mayor, 1', 'Electricidad', '2026-02-19','O1','T1','220','8'),
('Proceso', 102, 'Falla de alumbrado público en Plaza Mayor', 'C2', 'Plaza Mayor, 1', 'Electricidad', '2026-02-19','O1','T1','220','8'),
('Nueva', 104, 'Falla de alumbrado público en Plaza Mayor', 'C2', 'Plaza Mayor, 1', 'Electricidad', '2026-02-19','O2','T2','100','5'),
('Nueva', 105, 'Falla de alumbrado público en Plaza Mayor', 'C2', 'Plaza Mayor, 1', 'Electricidad', '2026-02-19','O2','T1','100','5');

INSERT INTO "Historial" ("id_modificacion", "id_incidencia", "id_usuario", "estado_nuevo", "fecha_modificacion", "comentario")
VALUES (1, 101, 'O1', 'Validada', '2026-02-19', 'Incidencia recibida y validada correctamente.');