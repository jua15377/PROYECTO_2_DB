package sample;

public class Banco {

    private String id_banco;
    private String banco;

    public Banco(String id_banco, String banco) {
        this.id_banco = id_banco;
        this.banco = banco;
    }

    public String getId_banco() {
        return id_banco;
    }

    public void setId_banco(String id_banco) {
        this.id_banco = id_banco;
    }

    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }
}
