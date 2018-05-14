package sample;

public class Cliente {

    private String id;
    private String nombre;
    private String apellido;
    private String fecha_nacimiento;
    private String twitter_user;
    private String twitter_image;
    private String local_image;
    private String id_departamento;
    private String id_ocupacio;
    private String id_banco;
    private String sucursal_favorita;
    private String id_categoria_favorita;
    private String ultima_compra;
    private String tiene_creditos;
    private String cant_creditos;
    private String otros;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    public void setFecha_nacimiento(String fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }

    public String getTwitter_user() {
        return twitter_user;
    }

    public void setTwitter_user(String twitter_user) {
        this.twitter_user = twitter_user;
    }

    public String getTwitter_image() {
        return twitter_image;
    }

    public void setTwitter_image(String twitter_image) {
        this.twitter_image = twitter_image;
    }

    public String getLocal_image() {
        return local_image;
    }

    public void setLocal_image(String local_image) {
        this.local_image = local_image;
    }

    public String getId_departamento() {
        return id_departamento;
    }

    public void setId_departamento(String id_departamento) {
        this.id_departamento = id_departamento;
    }

    public String getId_ocupacio() {
        return id_ocupacio;
    }

    public void setId_ocupacio(String id_ocupacio) {
        this.id_ocupacio = id_ocupacio;
    }

    public String getId_banco() {
        return id_banco;
    }

    public void setId_banco(String id_banco) {
        this.id_banco = id_banco;
    }

    public String getSucursal_favorita() {
        return sucursal_favorita;
    }

    public void setSucursal_favorita(String sucursal_favorita) {
        this.sucursal_favorita = sucursal_favorita;
    }

    public String getId_categoria_favorita() {
        return id_categoria_favorita;
    }

    public void setId_categoria_favorita(String id_categoria_favorita) {
        this.id_categoria_favorita = id_categoria_favorita;
    }

    public String getUltima_compra() {
        return ultima_compra;
    }

    public void setUltima_compra(String ultima_compra) {
        this.ultima_compra = ultima_compra;
    }

    public String getTiene_creditos() {
        return tiene_creditos;
    }

    public void setTiene_creditos(String tiene_creditos) {
        this.tiene_creditos = tiene_creditos;
    }

    public String getCant_creditos() {
        return cant_creditos;
    }

    public void setCant_creditos(String cant_creditos) {
        this.cant_creditos = cant_creditos;
    }

    public String getOtros() {
        return otros;
    }

    public void setOtros(String otros) {
        this.otros = otros;
    }

    public Cliente(String id, String nombre, String apellido, String fecha_nacimiento, String twitter_user, String twitter_image, String local_image, String id_departamento, String id_ocupacio, String id_banco, String sucursal_favorita, String id_categoria_favorita, String ultima_compra, String tiene_creditos, String cant_creditos, String otros) {

        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.fecha_nacimiento = fecha_nacimiento;
        this.twitter_user = twitter_user;
        this.twitter_image = twitter_image;
        this.local_image = local_image;
        this.id_departamento = id_departamento;
        this.id_ocupacio = id_ocupacio;
        this.id_banco = id_banco;
        this.sucursal_favorita = sucursal_favorita;
        this.id_categoria_favorita = id_categoria_favorita;
        this.ultima_compra = ultima_compra;
        this.tiene_creditos = tiene_creditos;
        this.cant_creditos = cant_creditos;
        this.otros = otros;
    }
}
