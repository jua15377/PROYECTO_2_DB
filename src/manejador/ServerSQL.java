package manejador;
import java.sql.*;
import java.util.ArrayList;

public class ServerSQL {
    private Credenciales credenciales = new Credenciales();
    private Connection c;

    public ServerSQL(){
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection(credenciales.getUrlPostgres(), credenciales.getUser(), credenciales.getPwd());
            System.out.println("Opened database successfully");
        }
        catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
    }
    public void executeSelectAllCliente(){
        try {
            Statement st = c.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM cliente");
            while (rs.next())
            {
                System.out.print(rs.getString(1)+ "\t");
                System.out.print(rs.getString(2)+ "\t");
                System.out.println(rs.getString(3));
            }
            rs.close();
            st.close();
        }
        catch (SQLException e){
            System.out.println("Error durante la ejecuion de query");
        }


    }
    //metodos para llenar los combobox
    public ArrayList<String> getNamesFromCatalog(String catalogName){
        ArrayList<String> result  = new ArrayList<>();
        try {
            Statement st = c.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM "+catalogName);
            while (rs.next())
            {
                result.add(rs.getString(2));
            }
            rs.close();
            st.close();
        }
        catch (SQLException e){
            System.out.println("Error durante la ejecucion de query");
        }
        //System.out.println(result);
        return result;
    }

    //#INSERT INTO cliente VALUES (1, 'Diego','Calderon','5/6/1996', null,'','', 12, 4, 2, 3, 16 , 150.85,false, 0,null)
    //String nombre, String Apellido, String fechaNacimiento, String twUser
    public void insertCliente(String nombre, String apellido, Date fechaNacimiento, String twUser, String rutaLocal, String rutaTw,
                                String depto, String ocupacion,String banco,String sucursal, String categoria,double ultimaCompra, boolean tieneCredito,  double cantidadeCredito ){
        try {
            PreparedStatement st = c.prepareStatement ("INSERT INTO cliente VALUES(" +
                    "(NEXTVAL('sec_cliente'))," +
                    "?, ?, ?, ? , ?, ?," +
                    "(select d.id_departamento from departamento d where departamento = ?)," +
                    "(select ocu.id_ocupacion from ocupacion ocu  where nombre_ocupacion = ?)," +
                    "(select b.id_banco from banco b where banco = ?)," +
                    "(select suc.id_sucursal from sucursal suc where nombre_sucursal = ?)," +
                    "(select cp.id_categoria from categoria_producto cp where nombre_cat = ?)," +
                    "?," +
                    "?," +
                    "?," +
                    "null)");
            st.setString(1,nombre);
            st.setString(2,apellido);
            st.setDate(3,fechaNacimiento);
            st.setString(4, twUser);
            st.setString(5, rutaTw);
            st.setString(6, rutaLocal);
            st.setString(7, depto);
            st.setString(8, ocupacion);
            st.setString(9, banco);
            st.setString(10, sucursal);
            st.setString(11, categoria);
            st.setDouble(12, ultimaCompra);
            st.setBoolean(13,tieneCredito);
            st.setDouble(14, cantidadeCredito);


            st.executeUpdate();
            st.close();
        }
        catch (SQLException e){
            System.out.println("Error durante la ejecuion de query");
            System.out.println(e);
        }
    }

    public ResultSet getUserbyID(int id){
        try {
            PreparedStatement st = c.prepareStatement("SELECT  * FROM cliente WHERE  id = ?");
            st.setInt(1,id);
            ResultSet rs = st.executeQuery();
            return rs;

        }
        catch (SQLException e){
            System.out.println("Error durante la ejecuion de query");
        }
        return null;
    }

    public ResultSet deleteUserbyID(int id){

        try {
            PreparedStatement st = c.prepareStatement("DELETE FROM cliente WHERE  id = ?");
            st.setInt(1,id);
            st.executeUpdate();
            st.close();

        }
        catch (SQLException e){
            System.out.println("Error durante la ejecuion de query");
        }
        return null;
    }

    public  void closeConnectionToServer (){
        try {
            c.close();
        }
        catch (SQLException s){

        }

    }


}
