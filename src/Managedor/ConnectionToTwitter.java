package Managedor;


import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

import java.util.List;

public class ConnectionToTwitter {
    ConfigurationBuilder cb = new ConfigurationBuilder();
    Credenciales credenciales = new Credenciales();

    public ConnectionToTwitter(){

        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(credenciales.getTwitterConsumekey())
                .setOAuthConsumerSecret(credenciales.getTwitterConsumeSecret())
                .setOAuthAccessToken(credenciales.getTwitterAccesToken())
                .setOAuthAccessTokenSecret(credenciales.getTwitterAccesTokenSecret());
        TwitterFactory tf = new TwitterFactory(cb.build());
        Twitter twitter = tf.getInstance();

        try {

            List<Status> statuses = twitter.getHomeTimeline();

            for (Status s : statuses){
                System.out.println(s.getUser().getScreenName());
            }
        }
        catch (Exception e){
            System.out.println(e);
        }

    }
}
