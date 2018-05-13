package manejador;


import com.sun.jndi.toolkit.url.Uri;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.conf.ConfigurationBuilder;

import java.net.URL;
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
        catch (Exception e){
            System.out.println(e);
            ruta = "";
        }


        return ruta;
    }
}
