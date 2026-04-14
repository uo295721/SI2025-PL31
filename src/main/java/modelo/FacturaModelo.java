package modelo;

import java.time.LocalDate;
import java.util.List;

import util.Database;

public class FacturaModelo {
	
	private Database db = new Database();
	
	public boolean existeFacturaParaIncidencia(int idIncidencia) {
		String sql = "SELECT COUNT(*) FROM Factura WHERE id_incidencia = ?";
		List<Object[]> res = db.executeQueryArray(sql, idIncidencia);
		return (res != null && !res.isEmpty() && Integer.parseInt(res.get(0)[0].toString()) > 0);
	}
	
	public String generarNumeroFactura() {
		int añoActual = LocalDate.now().getYear();
		String sql = "SELECT COUNT(*) FROM Factura";
		List<Object[]> res = db.executeQueryArray(sql);
		
		int siguienteId = 1;
		if (res != null && !res.isEmpty()) {
			siguienteId = Integer.parseInt(res.get(0)[0].toString())+1;
		}
		
		return String.format("FAC-%d-%03d",añoActual,siguienteId);
	}
	
	public boolean crearFacturaDesdeIncidencia(int idIncidencia) {
		String sqlInc = "SELECT descripcion_trabajos, coste FROM Incidencia WHERE id_incidencia = ?";
		List<Object[]> datosIncidencias = db.executeQueryArray(sqlInc, idIncidencia);
		
		if (datosIncidencias == null || datosIncidencias.isEmpty())
			return false;
		
		String detalle = (String)datosIncidencias.get(0)[0];
		double total = Double.parseDouble(datosIncidencias.get(0)[1].toString());
		String numero = generarNumeroFactura();
		
		String sql = "INSERT INTO Factura (numero_factura,id_incidencia,fecha_emision,detalle_tecnico,total,estado) "
				   + "VALUES (?,?,datetime('now','localtime'),?,?,'Activa')";
		try {
			db.executeUpdate(sql, numero, idIncidencia, detalle, total);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public void anularFactura(int idFactura) {
		String sql = "UPDATE Factura SET estado = 'Anulada' WHERE id_factura = ?";
		db.executeUpdate(sql, idFactura);
	}
	
	public List<FacturaDTO> obtenerTodasLasFacturas(){
		String sql = "SELECT " +
                "id_factura AS idFactura, " +
                "numero_factura AS numeroFactura, " +
                "id_incidencia AS idIncidencia, " +
                "fecha_emision AS fechaEmision, " +
                "detalle_tecnico AS detalleTecnico, " +
                "total, " +
                "estado " +
                "FROM Factura ORDER BY id_factura DESC";
   
		return db.executeQueryPojo(FacturaDTO.class, sql);
	}
}
