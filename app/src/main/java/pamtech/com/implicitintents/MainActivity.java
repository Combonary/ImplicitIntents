package pamtech.com.implicitintents;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private EditText etLocation;
    private EditText etWebsite;
    private EditText etShareText;
    private Button btnOpenWebsite;
    private Button btnShowLocation;
    private Button btnShareText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etWebsite = findViewById(R.id.website_edittext);
        etLocation = findViewById(R.id.location_edittext);
        etShareText = findViewById(R.id.share_text_edittext);

        btnOpenWebsite = findViewById(R.id.open_website_button);
        btnShowLocation = findViewById(R.id.open_location_button);
        btnShareText = findViewById(R.id.share_button);

        btnOpenWebsite.setOnClickListener(v -> openWebsite());
        btnShowLocation.setOnClickListener(v -> openLocation());
        btnShareText.setOnClickListener(v -> shareText());
    }

    private void openWebsite(){
        Log.d(TAG, "openWebsite: opening website");
        String url = etWebsite.getText().toString();
        Uri webPage = Uri.parse("http://" + url);

        Intent webIntent = new Intent(Intent.ACTION_VIEW, webPage);

        if(webIntent.resolveActivity(getPackageManager()) != null){
           startActivity(webIntent);
        }
        else{
            Log.d(TAG, "openWebsite: can't handle this");
        }
    }

    public void openLocation() {
        // Get the string indicating a location. Input is not validated; it is
        // passed to the location handler intact.
        String loc = etLocation.getText().toString();

        // Parse the location and create the intent.
        Uri addressUri = Uri.parse("geo:0,0?q=" + loc);
        Intent intent = new Intent(Intent.ACTION_VIEW, addressUri);

        // Find an activity to handle the intent, and start that activity.
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Log.d(TAG, "Can't handle this intent!");
        }
    }

    /**
     * using intent builder to make an implicit intent
     */
    public void shareText() {
        String txt = etShareText.getText().toString();
        String mimeType = "text/plain";
        ShareCompat.IntentBuilder
                .from(this)
                .setType(mimeType)
                .setChooserTitle(R.string.share_text_with)
                .setText(txt)
                .startChooser();
    }
}
