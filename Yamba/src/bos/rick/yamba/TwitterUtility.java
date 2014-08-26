package bos.rick.yamba;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import android.content.Context;

import android.util.Log;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterUtility {
	private static final String TAG = "TwitterUtility";
	private static ConfigurationBuilder builder_;
	private static TwitterFactory factory_;
	public static String tweet( Context context,String str) {
	
		Twitter twitter = getFactory(context).getInstance();
		try {
			twitter.updateStatus(str);
			return "Successfully posted" ;
		}
		catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Failed"+e.getMessage() ;
		}
	}
	private static TwitterFactory getFactory(Context context) {
		if ( factory_ == null)  {
			factory_ =  new TwitterFactory(getBuilder(context).build());
		}
		return factory_;
		
	}
    private static ConfigurationBuilder getBuilder(Context context) {
    	if ( builder_ == null) {
    		builder_ = new ConfigurationBuilder();
    		Properties props = readProperties(context);
    		Log.d(TAG,"read properties:"+ props.size());
    		builder_.setDebugEnabled(true)
    		  .setOAuthConsumerKey(props.getProperty("oauth.consumerKey"))
    		  .setOAuthConsumerSecret(props.getProperty("oauth.consumerSecret"))
    		  .setOAuthAccessToken(props.getProperty("oauth.accessToken"))
    		  .setOAuthAccessTokenSecret(props.getProperty("oauth.accessTokenSecret"));
    		Log.d(TAG,"created builder");
    	}
    	return builder_;
    	
    }
    private static Properties readProperties(Context context) {
        Properties props = new Properties();
    	try {
    	   InputStream is= context.getResources().openRawResource(R.raw.twitter4j);
    	   props.load(is);
    	    
       }
       catch ( IOException ioe) {
    	  Log.d(TAG,"Error reading properties:"+ ioe.getMessage());
       }
    	return props;
    
    }
}
