package manejador;



import org.bson.Document;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ConnectionToTwitter {
    ConfigurationBuilder cb = new ConfigurationBuilder();
    Credenciales credenciales = new Credenciales();
    TwitterFactory tf;
    Twitter twitter;

    public ConnectionToTwitter(){

        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(credenciales.getTwitterConsumekey())
                .setOAuthConsumerSecret(credenciales.getTwitterConsumeSecret())
                .setOAuthAccessToken(credenciales.getTwitterAccesToken())
                .setOAuthAccessTokenSecret(credenciales.getTwitterAccesTokenSecret());
        tf = new TwitterFactory(cb.build());
        twitter = tf.getInstance();
    }

    public String getUserImageLink(String userName){
        String ruta = "";
        try {
            User user = twitter.showUser(userName);
            ruta = user.getBiggerProfileImageURL();
            ruta = ruta.replace("_bigger", "");
            System.out.println(ruta);

        }
        catch (Exception e) {
            System.out.println(e);
            ruta = "";
        }

        return ruta;
    }
    public void  getTweetsFromUserAndInsertOnMongo(String userName){
        try {
            Paging paging= new Paging(1,200);
            List<Status> statuses = twitter.getUserTimeline(userName,paging);
            User user = twitter.showUser(userName);

            ArrayList<Document> tweets = new ArrayList<>();
            //para guardar el contenido del tweet


            Document doc = new Document("_id",userName)
                    .append("name",user.getName())
                    .append("cantidadTweets", user.getStatusesCount())
                    .append("descripcion", user.getDescription())
                    .append("locacion",user.getLocation())
                    .append("seguidores",user.getFollowersCount());



            for (Status status : statuses) {
                System.out.println(status.getUser().getName() + ":" + status.getText() + status.getCreatedAt());
                tweets.add(new Document("texto", status.getText()+"\n")
                        .append("fecha", status.getCreatedAt() + "\n"));

            }

            doc.append("tweets", Arrays.asList(tweets));
            ServerMongo serverMongo = new ServerMongo();
            serverMongo.insertOn(doc);
        }
        catch (Exception e) {
            System.out.println("Error en la recoleccion de twetts");
            System.out.println(e);
        }

    }

}
