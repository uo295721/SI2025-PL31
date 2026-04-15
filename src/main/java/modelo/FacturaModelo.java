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
	
	public String crearFacturaDesdeIncidencia(int idIncidencia) {
	    String sqlCheck = "SELECT COUNT(*) FROM Factura WHERE id_incidencia = ? AND estado != 'Anulada'";
	    List<Object[]> resCheck = db.executeQueryArray(sqlCheck, idIncidencia);
	    if (resCheck != null && Integer.parseInt(resCheck.get(0)[0].toString()) > 0) {
	        return "DUPLICADO"; 
	    }

	    String sqlInc = "SELECT descripcion_trabajos, coste FROM Incidencia WHERE id_incidencia = ?";
	    List<Object[]> datosIncidencias = db.executeQueryArray(sqlInc, idIncidencia);
	    
	    if (datosIncidencias == null || datosIncidencias.isEmpty()) return "ERROR_DATOS";

	    Object costeObj = datosIncidencias.get(0)[1];
	    if (costeObj == null || Double.parseDouble(costeObj.toString()) <= 0) {
	        return "SIN_COSTE"; // La BD confirma que no hay dinero cargado
	    }

	    String detalle = (String) datosIncidencias.get(0)[0];
	    double total = Double.parseDouble(costeObj.toString());
	    String numero = generarNumeroFactura();
	    
	    String sql = "INSERT INTO Factura (numero_factura, id_incidencia, fecha_emision, detalle_tecnico, total, estado) "
	               + "VALUES (?, ?, datetime('now','localtime'), ?, ?, 'Activa')";
	    try {
	        db.executeUpdate(sql, numero, idIncidencia, detalle, total);
	        return "OK";
	    } catch (Exception e) {
	        return "ERROR_DB";
	    }
	}
	
	public void anularFactura(int idFactura) {
        String sql = "UPDATE Factura SET estado = 'Anulada' WHERE id_factura = ?";
        db.executeUpdate(sql, idFactura);
    }
	
	public List<FacturaDTO> obtenerTodasLasFacturas(){
		String sql = "SELECT f.id_factura AS idFactura, f.numero_factura AS numeroFactura, " +
                "f.id_incidencia AS idIncidencia, f.fecha_emision AS fechaEmision, " +
                "f.total, f.estado, i.tiempoResolucion " + // Traemos el tiempo de la incidencia
                "FROM Factura f " +
                "JOIN Incidencia i ON f.id_incidencia = i.id_incidencia " +
                "ORDER BY f.id_factura DESC";
		return db.executeQueryPojo(FacturaDTO.class, sql);
	}
}
