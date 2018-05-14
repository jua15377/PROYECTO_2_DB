package sample;

public class Deudor {

    private String id;
    private String cliente_id;
    private String cant_credito;

    public Deudor(String id, String cliente_id, String cant_credito) {
        this.id = id;
        this.cliente_id = cliente_id;
        this.cant_credito = cant_credito;
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

    public String getCant_credito() {
        return cant_credito;
    }

    public void setCant_credito(String cant_credito) {
        this.cant_credito = cant_credito;
    }
}
