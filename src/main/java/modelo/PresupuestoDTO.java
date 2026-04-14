package modelo;

public class PresupuestoDTO {
    private int id_presupuesto;
    private int id_tipo;
    private String nombreTipo; 
    private double importe_total;
    private double importe_consumido;
    private String fecha_inicio;
    private String fecha_fin;

    public PresupuestoDTO() {}

    public int getId_presupuesto() { return id_presupuesto; }
    public void setId_presupuesto(int id) { this.id_presupuesto = id; }
    public int getId_tipo() { return id_tipo; }
    public void setId_tipo(int id) { this.id_tipo = id; }
    public String getNombreTipo() { return nombreTipo; }
    public void setNombreTipo(String n) { this.nombreTipo = n; }
    public double getImporte_total() { return importe_total; }
    public void setImporte_total(double t) { this.importe_total = t; }
    public double getImporte_consumido() { return importe_consumido; }
    public void setImporte_consumido(double c) { this.importe_consumido = c; }
    public String getFecha_inicio() { return fecha_inicio; }
    public void setFecha_inicio(String f) { this.fecha_inicio = f; }
    public String getFecha_fin() { return fecha_fin; }
    public void setFecha_fin(String f) { this.fecha_fin = f; }
}