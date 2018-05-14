package sample;

public class PersonasPorDepartamento {
    private String departamento;
    private String paisanos;

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getPaisanos() {
        return paisanos;
    }

    public void setPaisanos(String paisanos) {
        this.paisanos = paisanos;
    }

    public PersonasPorDepartamento(String departamento, String paisanos) {
        this.departamento = departamento;
        this.paisanos = paisanos;
    }
}
