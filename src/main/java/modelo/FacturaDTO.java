package modelo;

public class FacturaDTO {

	private int idFactura;
	private String numeroFactura;
	private int idIncidencia;
	private String fechaEmision;
	private String detalleTecnico;
	private double total;
	private String estado;
	
	public FacturaDTO() {}

	public int getIdFactura() {
		return idFactura;
	}

	public String getNumeroFactura() {
		return numeroFactura;
	}

	public int getIdIncidencia() {
		return idIncidencia;
	}

	public String getFechaEmision() {
		return fechaEmision;
	}

	public String getDetalleTecnico() {
		return detalleTecnico;
	}

	public double getTotal() {
		return total;
	}

	public String getEstado() {
		return estado;
	}

	public void setIdFactura(int idFactura) {
		this.idFactura = idFactura;
	}

	public void setNumeroFactura(String numeroFactura) {
		this.numeroFactura = numeroFactura;
	}

	public void setIdIncidencia(int idIncidencia) {
		this.idIncidencia = idIncidencia;
	}

	public void setFechaEmision(String fechaEmision) {
		this.fechaEmision = fechaEmision;
	}

	public void setDetalleTecnico(String detalleTecnico) {
		this.detalleTecnico = detalleTecnico;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
}
