package manejador;

import java.sql.Date;

public class PostgreSQLJDBC {
    public static void main(String args[]) {

        ServerSQL serverSQL = new ServerSQL();
        ///Scanner s = new Scanner(System.in);
        //String a = s.nextLine();
        //serverSQL.executeSelectAllCliente();
        Date d = Date.valueOf("2010-10-10");
        serverSQL.insertCliente("alguien","x",d, "sebastian_g_c","","", "Guatemala", "Estudiante", "BAC", "Express", "Teclados" , 150.85,false, 0);
        //serverSQL.closeConnectionToServer();
        //serverSQL.closeConnectionToServe();
        //ServerMongo serverMongo = new ServerMongo();
        //serverMongo.insertOn();
        //serverMongo.closeConnection();
//        ConnectionToTwitter connectionToTwitter = new ConnectionToTwitter();
//        connectionToTwitter.getUserImageLink("geektenango");

    }
}