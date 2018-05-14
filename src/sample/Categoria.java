package sample;

public class Categoria {

    private String id_categoria;
    private String nombre_cat;

    public Categoria(String id_categoria, String nombre_cat) {
        this.id_categoria = id_categoria;
        this.nombre_cat = nombre_cat;
    }

    public String getId_categoria() {
        return id_categoria;
    }

    public void setId_categoria(String id_categoria) {
        this.id_categoria = id_categoria;
    }

    public String getNombre_cat() {
        return nombre_cat;
    }

    public void setNombre_cat(String nombre_cat) {
        this.nombre_cat = nombre_cat;
    }
}
