import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class PostgreSQLJDBC {
    public static void main(String args[]) {
        Credenciales credenciales = new Credenciales();
        Connection c = null;
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection(credenciales.getUrlPostgres(), credenciales.getUser(),credenciales.getPwd());
            System.out.println("Opened database successfully");

            Statement st = c.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM departamento");
            while (rs.next())
            {
                System.out.print(rs.getString(1)+ "\t");
                System.out.print(rs.getString(2)+ "\t");
                System.out.println(rs.getString(3));
            }
            rs.close();
            st.close();



        }
        catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }

    }
}