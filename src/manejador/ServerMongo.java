package manejador;


import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Indexes;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
            collection = database.getCollection("twitter");

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

    public Document findADocument(String key, String value){
        Document  myDoc = collection.find(eq(key, value)).first();
        System.out.println(myDoc.toJson());
        return myDoc;
    }


    public void insertOn(Document doc){
        collection.insertOne(doc);
    }


    public void fullTextSearch(String query, boolean caseSensitive, boolean diacriticSensitive) {


        try {
            MongoCursor<Document> cursor = null;
            cursor = collection.find(new Document("$text", new Document("$search", query).append("$caseSensitive", new Boolean(caseSensitive)).append("$diacriticSensitive", new Boolean(diacriticSensitive)))).iterator();

            while (cursor.hasNext()) {
                Document article = cursor.next();
                System.out.println(article);
            }

            cursor.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mongoClient.close();
        }

    }

    public void fulltweetSearchTwo(String userName){
//        BasicDBObject query = new BasicDBObject(new BasicDBObject("$match", new BasicDBObject("_id", userName)));
//        query.append(new BasicDBObject("$unwind", "$tweets"));
//
//        System.out.println(query);

    }

}