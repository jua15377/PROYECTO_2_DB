package manejador;


import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerMongo {
    Credenciales credenciales = new Credenciales();
    MongoClient mongoClient ;
    MongoDatabase database;
    MongoCollection<Document> collection;

    public ServerMongo(){
        Logger mongoLogger = Logger.getLogger( "org.mongodb.driver" );
        mongoLogger.setLevel(Level.OFF);
        try {
            this.mongoClient = new MongoClient(credenciales.getConnectionString());
            this.database = mongoClient.getDatabase("crm_compu_twitter");
            collection = database.getCollection("test");

        }
        catch (Exception e){
            System.out.println("Error en la conexion con servidor Mongo");
            System.out.println(e);

        }

    }
    public void closeConnection(){
        mongoClient.close();
    }


    public void insertOn(Document doc){
        collection.insertOne(doc);
    }
}
