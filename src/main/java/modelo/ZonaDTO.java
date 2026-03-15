package modelo;

public class ZonaDTO {
    private String id_zona;
    private String nombre;

    public ZonaDTO() {}
    
    public ZonaDTO(String id, String n) {
    	this.id_zona = id;
    	this.nombre = n;
    }

    public String getId_zona(){ 
    	return id_zona;
    }
    
    public void setId_zona(String id_zona){
    	this.id_zona = id_zona;
    }

    public String getNombre(){
    	return nombre;
    }
    
    public void setNombre(String nombre){
    	this.nombre = nombre;
    }

    public String toString() {
        return nombre; 
    }
}