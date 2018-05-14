package sample;

public class IngresosXTienda {
    private String sucursal;
    private String ingresos;

    public String getSucursal() {
        return sucursal;
    }

    public void setSucursal(String sucursal) {
        this.sucursal = sucursal;
    }

    public String getIngresos() {
        return ingresos;
    }

    public void setIngresos(String ingresos) {
        this.ingresos = ingresos;
    }

    public IngresosXTienda(String sucursal, String ingresos) {
        this.sucursal = sucursal;
        this.ingresos = ingresos;
    }
}
