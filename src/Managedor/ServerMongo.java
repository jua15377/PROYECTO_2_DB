package Managedor;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoClientOptions;

public class ServerMongo {
    Credenciales credenciales = new Credenciales();
    MongoClient mongoClient ;

    public ServerMongo(){
        try {
            this.mongoClient = new MongoClient("mongodb+srv://masterUserEngappsado:@crmcomputwiiter-xeflu.mongodb.net/test?retryWrites=true");
            System.out.println("conexion con Atlas");
        }
        catch (Exception e){
            System.out.println("Error en la conexion con Atlas");
        }

    }
}
