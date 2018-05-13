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
            System.out.println("Error en la conexion con Atlas");
        }

    }
    public void closeConnection(){
        mongoClient.close();
    }
    public void insertOn(){
        Document doc = new Document("_id",4)
                .append("name", "MongoDB")
                .append("type", "database")
                .append("count", 1)
                .append("versions", Arrays.asList("v3.2", "v3.0", "v2.6"))
                .append("info", new Document("x", 203).append("y", 102));
        collection.insertOne(doc);

    }
}
