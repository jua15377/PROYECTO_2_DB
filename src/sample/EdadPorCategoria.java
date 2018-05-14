package sample;

public class EdadPorCategoria {
    private String categoria;
    private String edad_promedio;

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getEdad_promedio() {
        return edad_promedio;
    }

    public void setEdad_promedio(String edad_promedio) {
        this.edad_promedio = edad_promedio;
    }

    public EdadPorCategoria(String categoria, String edad_promedio) {
        this.categoria = categoria;
        this.edad_promedio = edad_promedio;
    }
}
