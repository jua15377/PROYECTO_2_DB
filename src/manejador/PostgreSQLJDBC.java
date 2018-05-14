package manejador;

import java.sql.Date;
import java.sql.ResultSet;
import org.json.simple.*;

public class PostgreSQLJDBC {
    public static void main(String args[]) {

//        ServerSQL serverSQL = new ServerSQL();
        ///Scanner s = new Scanner(System.in);
        //String a = s.nextLine();
        //serverSQL.executeSelectAllCliente();
        //Date d = Date.valueOf("2010-10-10");
        //serverSQL.insertCliente("alguien","x",d, "sebastian_g_c","","", "Guatemala", "Estudiante", "BAC", "Express", "Teclados" , 150.85,false, 0);
        //serverSQL.closeConnectionToServer();
        //serverSQL.closeConnectionToServe();
        //ServerMongo serverMongo = new ServerMongo();
        //serverMongo.insertOn();
        //serverMongo.closeConnection();
        ConnectionToTwitter connectionToTwitter = new ConnectionToTwitter();
        //connectionToTwitter.getUserImageLink("geektenango");
//        JSONObject obj = new JSONObject();
//        obj.put("nombre1", "Malon");
//        obj.put("Apodo", 5);
//        JSONObject obj1 = new JSONObject();
//        obj1.put("nombre adentro", "Andrea");
//        obj1.put("booleano", false);
//        obj.put("otroOBjeto",obj1);
//
//        System.out.println(obj.toString()+"\n\n\n\n");
//        System.out.println(obj.toJSONString()+"\n\n\n\n");
        ServerMongo serverMongo = new ServerMongo();
        //serverMongo.fullTextSearch("diego",false,false);
        //serverMongo.fulltweetSearchTwo("usuario");
    }
}