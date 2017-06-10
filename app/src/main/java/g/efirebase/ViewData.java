package g.efirebase;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

/**
 * Created by Hewlett packard on 08-06-2017.
 */

public class ViewData extends AppCompatActivity
{
    ImageView bimage;
    TextView btext;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewdatalayout);
        bimage = (ImageView)findViewById(R.id.Bimage);
        btext = (TextView)findViewById(R.id.Btxt);
        Intent i = getIntent();
        Bundle extras = getIntent().getExtras();
        String text= String.valueOf(extras.get("id2"));
        String title= String.valueOf(extras.get("id"));
        getSupportActionBar().setTitle(title);
        btext.setText(text);
        String image = String.valueOf(extras.get("id1"));
        Glide.with(this)
                .load(image)
                .crossFade()
                .thumbnail(0.1f)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(bimage);
    }
}
