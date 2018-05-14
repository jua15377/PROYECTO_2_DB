package sample;

public class PersonasPorCategoria {
    private String categoria;
    private String clientes;

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getClientes() {
        return clientes;
    }

    public void setClientes(String clientes) {
        this.clientes = clientes;
    }

    public PersonasPorCategoria(String categoria, String clientes) {
        this.categoria = categoria;
        this.clientes = clientes;
    }
}
