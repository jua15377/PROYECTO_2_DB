package Managedor;
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
        return result;
    }

    //#INSERT INTO cliente VALUES (1, 'Diego','Calderon','5/6/1996', null,'','', 12, 4, 2, 3, 16 , 150.85,false, 0,null)
    public void insertCliente(String nombre, String Apellido, String fechaNacimiento, String twUser){

    }

    public  void closeConnectionToServer (){
        try {
            c.close();
        }
        catch (SQLException s){

        }

    }


}
