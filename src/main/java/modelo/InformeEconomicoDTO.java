package modelo;

public class InformeEconomicoDTO {
    private String categoria;
    private int totalIncidencias;
    private double costeTotal;
    private double costeMedio;

    public InformeEconomicoDTO(String categoria, int total, double costeT, double costeM) {
        this.categoria = categoria;
        this.totalIncidencias = total;
        this.costeTotal = costeT;
        this.costeMedio = costeM;
    }

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public int getTotalIncidencias() {
		return totalIncidencias;
	}

	public void setTotalIncidencias(int totalIncidencias) {
		this.totalIncidencias = totalIncidencias;
	}

	public double getCosteTotal() {
		return costeTotal;
	}

	public void setCosteTotal(double costeTotal) {
		this.costeTotal = costeTotal;
	}

	public double getCosteMedio() {
		return costeMedio;
	}

	public void setCosteMedio(double costeMedio) {
		this.costeMedio = costeMedio;
	}
}
