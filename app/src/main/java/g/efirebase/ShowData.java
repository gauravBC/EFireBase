package g.efirebase;

/**
 * Created by Hewlett packard on 07-06-2017.
 */
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static android.R.attr.direction;
import static java.security.AccessController.getContext;

/**
 * Created by Administrator on 16-03-2017.
 */

public class ShowData extends AppCompatActivity {

    RecyclerView recyclerView;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;
    private FirebaseRecyclerAdapter<ShowDataItems, ShowDataViewHolder> mFirebaseAdapter;

    public ShowData() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_data_layout);
        firebaseDatabase = FirebaseDatabase.getInstance();
        // myRef = firebaseDatabase.getReference("User_Details");
        myRef = FirebaseDatabase.getInstance().getReference("User_Details");
        recyclerView = (RecyclerView)findViewById(R.id.show_data_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(ShowData.this));
        Toast.makeText(ShowData.this, "Wait !  Fetching List...", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onStart() {
        super.onStart();
        //Log.d("LOGGED", "IN onStart ");
        mFirebaseAdapter = new FirebaseRecyclerAdapter<ShowDataItems, ShowDataViewHolder>(ShowDataItems.class, R.layout.show_data_single_item, ShowDataViewHolder.class, myRef) {
            public void populateViewHolder(final ShowDataViewHolder viewHolder, final ShowDataItems model, final int position)
            {
                viewHolder.Image_URL(model.getImage_URL());
                viewHolder.Image_Title(model.getImage_Title());
                viewHolder.Image_Thoughts(model.getImage_Thoughts());
                viewHolder.itemView.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view) {
                        shortclick(view);
                    }

                    private void shortclick(View view)
                    {
                        Intent i=new Intent(ShowData.this,ViewData.class);
                        i.putExtra("id",model.getImage_Title());
                        i.putExtra("id1",model.getImage_URL());
                        i.putExtra("id2",model.getImage_Thoughts());
                        startActivity(i);
                    }
                });
                //OnClick Item
                viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {

                    @Override
                    public boolean onLongClick(View view)
                    {
                        longClick(view);
                       return true;
                    }

                    private void longClick(View view) {

                        AlertDialog.Builder builder = new AlertDialog.Builder(ShowData.this);
                        builder.setMessage("Do you want to Delete this data ?").setCancelable(false)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        int selectedItems = position;
                                        mFirebaseAdapter.getRef(selectedItems).removeValue();
                                        mFirebaseAdapter.notifyItemRemoved(selectedItems);
                                        recyclerView.invalidate();
                                        onStart();
                                    }
                                })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });
                        AlertDialog dialog = builder.create();
                        dialog.setTitle("Confirm");
                        dialog.show();
                    }



                });

            }
        };


        recyclerView.setAdapter(mFirebaseAdapter);
    }


    //View Holder For Recycler View
    public static class ShowDataViewHolder extends RecyclerView.ViewHolder {
        private final TextView image_title;
        private final ImageView image_url;
        private final TextView image_thoughts;

        public ShowDataViewHolder(final View itemView) {
            super(itemView);
            image_url = (ImageView) itemView.findViewById(R.id.fetch_image);
            image_title = (TextView) itemView.findViewById(R.id.fetch_image_title);
            image_thoughts = (TextView) itemView.findViewById(R.id.fetch_thoughts);
        }

        private void Image_Title(String title) {
            image_title.setText(title);
        }

        private void Image_URL(String title) {
            // image_url.setImageResource(R.drawable.loading);
            Glide.with(itemView.getContext())
                    .load(title)
                    .crossFade()
                    .thumbnail(0.1f)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(image_url);
        }
        private void Image_Thoughts(String title) {
            image_thoughts.setText(title);
        }



    }
}
