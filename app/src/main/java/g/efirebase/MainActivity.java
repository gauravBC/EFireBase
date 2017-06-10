package g.efirebase;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.onesignal.OneSignal;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText email, password;
    private Button signin, signup;
    FirebaseUser user;
    static String LoggedIn_User_Email;
    public static FirebaseDatabase mDatabase;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

          OneSignal.startInit(this).init();

        if (mDatabase == null) {
            mDatabase = FirebaseDatabase.getInstance();
            //mDatabase.setPersistenceEnabled(true);
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        }


        mAuth = FirebaseAuth.getInstance(); // important Call

        signin = (Button) findViewById(R.id.signin1);
        signup = (Button) findViewById(R.id.signup1);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Signup.class);
                startActivity(i);
            }
        });
        email = (EditText) findViewById(R.id.etEmail1);
        password = (EditText) findViewById(R.id.etPassword1);
        //Check if User is Already LoggedIn
        if (mAuth.getCurrentUser() != null) {
            //User NOT logged In
            finish();
            startActivity(new Intent(getApplicationContext(), SignIn.class));
        }

        user = mAuth.getCurrentUser();
        Log.d("LOGGED", "user: " + user);


        //Setting the tags for Current User.
        if (user != null) {
            LoggedIn_User_Email = user.getEmail();
        }
        OneSignal.sendTag("User_ID", LoggedIn_User_Email);


        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getemail = email.getText().toString().trim();
                String getepassword = password.getText().toString().trim();
                if (getemail == null && getepassword == null)
                {
                    Toast.makeText(getApplicationContext(), "Enter the fields first", Toast.LENGTH_SHORT).show();

                }
                else
                {
                    callsignin(getemail, getepassword);
                }
            }
        });


    }

    //Now start Sign In Process
    //SignIn Process
    private void callsignin(final String email, final String password) {

        if (email == null && password == null)
        {
            Toast.makeText(getApplicationContext(), "Enter the fields first", Toast.LENGTH_SHORT).show();

        }
        if (email != null && password != null)
        {
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            Log.d("TESTING", "sign In Successful:" + task.isSuccessful());

                            // If sign in fails, display a message to the user. If sign in succeeds
                            // the auth state listener will be notified and logic to handle the
                            // signed in user can be handled in the listener.
                            if (!task.isSuccessful()) {
                                Log.w("TESTING", "signInWithEmail:failed", task.getException());
                                Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                            } else {
                                Intent i = new Intent(MainActivity.this, SignIn.class);
                                finish();
                                startActivity(i);
                            }
                        }
                    });

        }


    }
}