DROP TABLE IF EXISTS "Historial";
DROP TABLE IF EXISTS "Incidencia";
DROP TABLE IF EXISTS "Usuario";

CREATE TABLE "Usuario" (
    "id_usuario"    TEXT NOT NULL UNIQUE,
    "nombre"    TEXT,
    "apellidos" TEXT,
    "email" TEXT UNIQUE,
    "rol"   TEXT,
    PRIMARY KEY("id_usuario")
);

CREATE TABLE "Incidencia" (
    "estado"    TEXT NOT NULL,
    "id_incidencia" INTEGER NOT NULL UNIQUE,
    "descripcion"   TEXT,
    "id_ciudadano"    TEXT NOT NULL,
    "localización"  TEXT,
    "tipo"  TEXT NOT NULL,
    "fecha" TEXT NOT NULL,
    "id_operador"   TEXT,
    "id_tecnico"    TEXT,
    "coste" NUMERIC,
    "horas_estimadas"   INTEGER,
    "descripcion_trabajos"  TEXT,
    PRIMARY KEY("id_incidencia")
);

CREATE TABLE "Historial" (
    "id_modificacion"   INTEGER NOT NULL UNIQUE,
    "id_incidencia" INTEGER,
    "id_usuario"    TEXT,
    "estado_nuevo"  TEXT,
    "fecha_modificacion"    TEXT,
    "comentario"    TEXT,
    PRIMARY KEY("id_modificacion"),
    CONSTRAINT "incidencia" FOREIGN KEY("id_incidencia") REFERENCES "Incidencia"("id_incidencia") ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS "TareaDiaria" (
    id_tarea INTEGER PRIMARY KEY AUTOINCREMENT,
    id_incidencia INTEGER,
    id_tecnico TEXT,
    fecha TEXT,
    descripcion_tarea TEXT,
    horas_dedicadas REAL,
    FOREIGN KEY(id_incidencia) REFERENCES Incidencia(id_incidencia)
);