package sample;

public class CreditosPorBanco {
    private String banco;
    private String creditos_otorgados;

    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }

    public String getCreditos_otorgados() {
        return creditos_otorgados;
    }

    public void setCreditos_otorgados(String creditos_otorgados) {
        this.creditos_otorgados = creditos_otorgados;
    }

    public CreditosPorBanco(String banco, String creditos_otorgados) {
        this.banco = banco;
        this.creditos_otorgados = creditos_otorgados;
    }
}
