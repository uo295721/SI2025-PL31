DROP TABLE IF EXISTS "Historial";
DROP TABLE IF EXISTS "Incidencia";
DROP TABLE IF EXISTS "Usuario";
DROP TABLE IF EXISTS "TipoIncidencia";
DROP TABLE IF EXISTS "Zona";
DROP TABLE IF EXISTS "TareaDiaria";

CREATE TABLE "Usuario" (
    "id_usuario"    TEXT NOT NULL UNIQUE,
    "nombre"    TEXT,
    "apellidos" TEXT,
    "email" TEXT UNIQUE,
    "rol"   TEXT,
    "id_tipo" INTEGER,
    PRIMARY KEY("id_usuario"),
    FOREIGN KEY("id_tipo") REFERENCES "TipoIncidencia"("id_tipo")
);

CREATE TABLE "Incidencia" (
    "estado"    TEXT NOT NULL,
    "id_incidencia" INTEGER NOT NULL UNIQUE,
    "descripcion"   TEXT,
    "id_ciudadano"    TEXT NOT NULL,
    "localización"  TEXT,
    "zona" TEXT,
    "id_tipo" INTEGER NOT NULL,
    "fecha" TEXT NOT NULL,
    "fecha_resolucion" TEXT,
    "id_operador"   TEXT,
    "id_tecnico"    TEXT,
    "coste" NUMERIC,
    "horas_estimadas"   INTEGER,
    "descripcion_trabajos"  TEXT,
    PRIMARY KEY("id_incidencia"),
    FOREIGN KEY("zona") REFERENCES "Zona"("nombre"),
    FOREIGN KEY("id_tipo") REFERENCES "TipoIncidencia"("id_tipo")
);

CREATE TABLE "Historial" (
    "id_modificacion"   INTEGER NOT NULL UNIQUE,
    "id_incidencia" INTEGER,
    "id_usuario"    TEXT,
    "estado_nuevo"  TEXT,
    "fecha_modificacion"    TEXT,
    "comentario"    TEXT,
    PRIMARY KEY("id_modificacion"),
    FOREIGN KEY("id_usuario") REFERENCES "Usuario"("id_usuario")
    CONSTRAINT "incidencia" FOREIGN KEY("id_incidencia") REFERENCES "Incidencia"("id_incidencia") ON DELETE CASCADE
);

CREATE TABLE "TareaDiaria" (
    "id_tarea" INTEGER PRIMARY KEY,
    "id_incidencia" INTEGER,
    "id_tecnico" TEXT,
    "fecha" TEXT,
    "descripcion_tarea" TEXT,
    "horas_dedicadas" REAL,
    FOREIGN KEY("id_tecnico") REFERENCES "Usuario"("id_usuario")
    FOREIGN KEY("id_incidencia") REFERENCES "Incidencia"("id_incidencia")
);

CREATE TABLE "TipoIncidencia" (
    "id_tipo" INTEGER PRIMARY KEY,
    "nombre" TEXT NOT NULL UNIQUE
);

CREATE TABLE "Zona" (
    "id_zona"    TEXT NOT NULL UNIQUE,
    "nombre"    TEXT NOT NULL UNIQUE,
    PRIMARY KEY("id_zona")
);