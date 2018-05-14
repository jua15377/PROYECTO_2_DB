package sample;

public class Cliente_destacado {
    private String id;
    private String cliente_id;
    private String ultima_compra;

    public Cliente_destacado(String id, String cliente_id, String ultima_compra) {
        this.id = id;
        this.cliente_id = cliente_id;
        this.ultima_compra = ultima_compra;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCliente_id() {
        return cliente_id;
    }

    public void setCliente_id(String cliente_id) {
        this.cliente_id = cliente_id;
    }

    public String getUltima_compra() {
        return ultima_compra;
    }

    public void setUltima_compra(String ultima_compra) {
        this.ultima_compra = ultima_compra;
    }
}
