package bos.rick.yamba;





import android.app.Activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class StatusActivity extends Activity implements OnClickListener{
    private static final String TAG = "RickTweet";
	private EditText editStatus;
    private Button buttonTweet;
    private GPSTracker tracker;
    private TextView outText;
    private CheckBox checkBox;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_status);
		editStatus = (EditText)findViewById(R.id.editStatus);
		buttonTweet = (Button)findViewById(R.id.buttonTweet);
		outText = (TextView)findViewById(R.id.outText);
        checkBox = (CheckBox)findViewById(R.id.locationCheckBox);
		
		buttonTweet.setOnClickListener(this);
		tracker = new GPSTracker(getApplicationContext());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.status, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	private GPSTracker getTracker() {
	         if ( tracker == null) {
	        	 tracker = new GPSTracker(getApplicationContext());
	         }
	         return tracker;
	
	}

	@Override
	public void onClick(View v) {
		String status = editStatus.getText().toString();
		Log.d(TAG,"onClicked with status:" + status);
		editStatus.setText(status+".");
		if ( checkBox.isChecked()) {
		   status = status + "\n"+getTracker().getLocationString(getApplicationContext());
		}
		new PostTaskTwitter().execute(status);
		editStatus.setText("");
		outText.setText(status);
		
	}
	private final class PostTaskTwitter extends AsyncTask<String,Void,String> {

		@Override
		protected String doInBackground(String... params) {
			getApplicationContext().getResources().openRawResource(R.raw.twitter4j);
			return TwitterUtility.tweet(getApplicationContext(),params[0]);
			
		}
		
		protected void onPostExecute( String result ) {
			super.onPostExecute(result);
			Toast.makeText(StatusActivity.this,result,Toast.LENGTH_LONG).show();
			
		}
		
		
	
	}
	
	
	
}
