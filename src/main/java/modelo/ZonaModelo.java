package modelo;

import java.util.List;

import util.Database;

public class ZonaModelo {

	private Database db = new Database();
	
	public ZonaModelo() {}
	
	public List<ZonaDTO> obtenerZonas() {
	    String sql = "SELECT id_zona, nombre FROM Zona ORDER BY nombre ASC";
	    return db.executeQueryPojo(ZonaDTO.class, sql);
	}
}
