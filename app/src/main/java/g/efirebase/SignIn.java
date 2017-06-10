package g.efirebase;

/**
 * Created by Hewlett packard on 07-06-2017.
 */

import android.*;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by Administrator on 16-03-2017.
 */

public class SignIn extends AppCompatActivity {
    Button signout, upload_bttn, showData, chat;
    Uri uri;
    String text;
    private FirebaseAuth mAuth;
    Story s=new Story();
    TextView username;
    static String LoggedIn_User_Email;
    int flag=1,reqcode,rescode;
    Intent data,i;
    Context c;

    public static int Device_Width;

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);
        i=getIntent();
        reqcode=i.getIntExtra("reqcode",0);
        rescode=i.getIntExtra("rescode",0);
        data= (Intent) getIntent().getSerializableExtra("data");
        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.containsKey("imageUri")) {
            uri= Uri.parse(extras.getString("imageUri"));
        }

        if (extras != null && extras.containsKey("text"))
        {
            text = extras.getString("text");
        }
        if(text == null || uri == null )
        {
            flag=0;
        }
        signout = (Button) findViewById(R.id.signout);
        username = (TextView) findViewById(R.id.tvName);
        upload_bttn = (Button) findViewById(R.id.upload);
        showData = (Button) findViewById(R.id.show_data);
        chat = (Button) findViewById(R.id.chat);


        if (MainActivity.mDatabase == null) {
            MainActivity.mDatabase = FirebaseDatabase.getInstance();
            //mDatabase.setPersistenceEnabled(true);
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        }


        mAuth = FirebaseAuth.getInstance(); // important Call
        //Again check if the user is Already Logged in or Not

        if (mAuth.getCurrentUser() == null) {
            //User NOT logged In
            this.finish();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }


        //Fetch the Display name of current User
        FirebaseUser user = mAuth.getCurrentUser();
        Log.d("LOGGED", "FirebaseUser: " + user);

        if (user != null) {
            username.setText("Welcome, " + user.getDisplayName());


            LoggedIn_User_Email = user.getEmail();


        }

        DisplayMetrics metrics = getApplicationContext().getResources().getDisplayMetrics();
        Device_Width = metrics.widthPixels;


        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                finish();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

        upload_bttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), UploadInfo.class));
            }
        });

        showData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ShowData.class));
            }
        });



      /*  notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendNotification();

            }
        });
*/


        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ChatActivity.class));
            }
        });


    }

   /* private void sendNotification()
    {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                int SDK_INT = android.os.Build.VERSION.SDK_INT;
                if (SDK_INT > 8) {
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                            .permitAll().build();
                    StrictMode.setThreadPolicy(policy);
                    String send_email;

                    //This is a Simple Logic to Send Notification different Device Programmatically....
                    if (MainActivity.LoggedIn_User_Email.equals("user1@gmail.com")) {
                        send_email = "user2@gmail.com";
                    } else {
                        send_email = "user1@gmail.com";
                    }

                    try {
                        String jsonResponse;

                        URL url = new URL("https://onesignal.com/api/v1/notifications");
                        HttpURLConnection con = (HttpURLConnection) url.openConnection();
                        con.setUseCaches(false);
                        con.setDoOutput(true);
                        con.setDoInput(true);

                        con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                        con.setRequestProperty("Authorization", "Basic YTUzMWE2M2UtMTZiYy00M2FhLWEyMjItYWQ5YWI1MDgzM2U2");
                        con.setRequestMethod("POST");

                        String strJsonBody = "{"
                                + "\"app_id\": \"0523d5af-d75a-4916-a8dd-3e9109e0f10b\","

                                + "\"filters\": [{\"field\": \"tag\", \"key\": \"User_ID\", \"relation\": \"=\", \"value\": \"" + send_email + "\"}],"

                                + "\"data\": {\"foo\": \"bar\"},"
                                + "\"contents\": {\"en\": \"English Message\"}"
                                + "}";


                        System.out.println("strJsonBody:\n" + strJsonBody);

                        byte[] sendBytes = strJsonBody.getBytes("UTF-8");
                        con.setFixedLengthStreamingMode(sendBytes.length);

                        OutputStream outputStream = con.getOutputStream();
                        outputStream.write(sendBytes);

                        int httpResponse = con.getResponseCode();
                        System.out.println("httpResponse: " + httpResponse);

                        if (httpResponse >= HttpURLConnection.HTTP_OK
                                && httpResponse < HttpURLConnection.HTTP_BAD_REQUEST) {
                            Scanner scanner = new Scanner(con.getInputStream(), "UTF-8");
                            jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                            scanner.close();
                        } else {
                            Scanner scanner = new Scanner(con.getErrorStream(), "UTF-8");
                            jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                            scanner.close();
                        }
                        System.out.println("jsonResponse:\n" + jsonResponse);

                    } catch (Throwable t) {
                        t.printStackTrace();
                    }
                }
            }
        });*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.about:
                aboutUs();
                return true;
            case R.id.addstory:
                addstory();
                return true;
            case R.id.viewstory:
                showStory();
                return true;
            case R.id.destroystory:
                destroyStory();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    public void destroyStory() {
        if (flag == 0)
        {
            Toast.makeText(getApplicationContext(), "First set a story", Toast.LENGTH_SHORT).show();
        }
        else
        {
            s.destroyed();
        }
    }

    private void showStory() {
        if (flag == 0 || uri == null)
        {
            Toast.makeText(getApplicationContext(), "First set a story", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Intent i= new Intent(this,Story.class);
            i.putExtra("imageUri",uri.toString());
            i.putExtra("text",text);
            startActivity(i);
        }
    }


    private void addstory()
    {
    Intent i=new Intent(this,Splash.class);
        startActivity(i);
    }

    private void aboutUs()
    {
        Intent i= new Intent(this,AboutUs.class);
        startActivity(i);
    }
}


