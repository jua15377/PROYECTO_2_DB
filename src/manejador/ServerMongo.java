package manejador;


import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.mongodb.client.model.Filters.eq;

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

    public void deleteOneFromKey(String key, String userName){

        try {
            collection.deleteOne(eq(key, userName));
        }
        catch (Exception e){
            System.out.println("Error en la conexion con servidor Mongo");
            System.out.println(e);

        }

    }

    public void findADocument(String key, String value){
        
    }


    public void insertOn(Document doc){
        collection.insertOne(doc);
    }
}
