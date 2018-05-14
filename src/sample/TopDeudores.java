package sample;

public class TopDeudores {
    private String nombre;
    private String apellido;
    private String edad;
    private String departamento;
    private String ocupacion;
    private String banco;
    private String sucursal_favorita;
    private String categoria_favorita;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getOcupacion() {
        return ocupacion;
    }

    public void setOcupacion(String ocupacion) {
        this.ocupacion = ocupacion;
    }

    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }

    public String getSucursal_favorita() {
        return sucursal_favorita;
    }

    public void setSucursal_favorita(String sucursal_favorita) {
        this.sucursal_favorita = sucursal_favorita;
    }

    public String getCategoria_favorita() {
        return categoria_favorita;
    }

    public void setCategoria_favorita(String categoria_favorita) {
        this.categoria_favorita = categoria_favorita;
    }

    public TopDeudores(String nombre, String apellido, String edad, String departamento, String ocupacion, String banco, String sucursal_favorita, String categoria_favorita) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
        this.departamento = departamento;
        this.ocupacion = ocupacion;
        this.banco = banco;
        this.sucursal_favorita = sucursal_favorita;
        this.categoria_favorita = categoria_favorita;
    }
}
