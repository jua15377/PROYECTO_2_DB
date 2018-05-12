package Managedor;

import java.util.Scanner;

public class PostgreSQLJDBC {
    public static void main(String args[]) {

        ServerSQL serverSQL = new ServerSQL();
        ///Scanner s = new Scanner(System.in);
        //String a = s.nextLine();
        //serverSQL.executeSelectAllCliente();
        System.out.println(serverSQL.getNamesFromCatalog("asdf"));
        serverSQL.closeConnectionToServer();
        //serverSQL.closeConnectionToServe();
        //ServerMongo serverMongo = new ServerMongo();
        //serverMongo.insertOn();
        //serverMongo.closeConnection();
        //ConnectionToTwitter connectionToTwitter = new ConnectionToTwitter();

    }
}