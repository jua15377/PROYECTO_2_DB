package Managedor;
import java.sql.*;

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

    public  void closeConnectionToServe  (){
        try {
            c.close();
        }
        catch (SQLException s){

        }

    }


}
