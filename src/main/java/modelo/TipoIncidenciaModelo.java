package modelo;

import java.util.List;

import util.Database;

public class TipoIncidenciaModelo {

	private Database db = new Database();
	
	public TipoIncidenciaModelo() {};
	
	public List<TipoIncidenciaDTO> obtenerTodosLosTipos(){
		
		String sql = "SELECT id_tipo, nombre FROM TipoIncidencia ORDER BY nombre ASC";
		
		return db.executeQueryPojo(TipoIncidenciaDTO.class, sql);
	}
	
}
