package g.efirebase;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Hewlett packard on 08-06-2017.
 */

public class Story extends Activity {
    ImageView iv;
    TextView tv;
    private static int SPLASH_TIME_OUT=50000;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.story_layout);
        iv=(ImageView)findViewById(R.id.storyimg);
        tv=(TextView)findViewById(R.id.storytv);
        Bundle extras = getIntent().getExtras();
        Uri myUri = Uri.parse(extras.getString("imageUri"));
        iv.setImageURI(myUri);
        String text= getIntent().getStringExtra("text");
        tv.setText(text);
        /*new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(getApplicationContext(),SignIn.class);
                startActivity(i);
                finish();
            }
        },SPLASH_TIME_OUT);*/

    }
    public void destroyed()
    {
        Toast.makeText(getApplicationContext(),"Story destroyed",Toast.LENGTH_SHORT).show();
        iv.setImageResource(R.drawable.addstory);
        tv.setText(null);
        Intent intent= new Intent(this,SignIn.class);
        startActivity(intent);
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
}
