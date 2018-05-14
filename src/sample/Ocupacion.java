package sample;

public class Ocupacion {

    private String id_ocupacion;
    private String nombre_ocupacion;

    public Ocupacion(String id_ocupacion, String nombre_ocupacion) {
        this.id_ocupacion = id_ocupacion;
        this.nombre_ocupacion = nombre_ocupacion;
    }

    public String getId_ocupacion() {
        return id_ocupacion;
    }

    public void setId_ocupacion(String id_ocupacion) {
        this.id_ocupacion = id_ocupacion;
    }

    public String getNombre_ocupacion() {
        return nombre_ocupacion;
    }

    public void setNombre_ocupacion(String nombre_ocupacion) {
        this.nombre_ocupacion = nombre_ocupacion;
    }
}
