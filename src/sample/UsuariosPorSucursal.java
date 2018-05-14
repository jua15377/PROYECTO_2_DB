package sample;

public class UsuariosPorSucursal {
    private String sucursal;
    private String clientes;

    public String getSucursal() {
        return sucursal;
    }

    public void setSucursal(String sucursal) {
        this.sucursal = sucursal;
    }

    public String getClientes() {
        return clientes;
    }

    public void setClientes(String clientes) {
        this.clientes = clientes;
    }

    public UsuariosPorSucursal(String sucursal, String clientes) {
        this.sucursal = sucursal;
        this.clientes = clientes;
    }
}
