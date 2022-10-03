package edu.northeastern.testapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.zip.Inflater;

import edu.northeastern.testapplication.R;
import edu.northeastern.testapplication.linkCollector.Link;
import edu.northeastern.testapplication.linkCollector.LinkCollectorAdapter;

public class LinkCollectorActivity extends AppCompatActivity {

    private ArrayList<Link> links;
    private LinkCollectorAdapter linkCollectorAdapter;
    private RecyclerView linkCollectorRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_link_collector);


        ArrayList<Link> links = new ArrayList<>();
        linkCollectorAdapter = new LinkCollectorAdapter(links, this);

        //Adding a new person object to the personList arrayList
        linkCollectorRecyclerView = findViewById(R.id.linkCollectorRecyclerView);

        //This defines the way in which the RecyclerView is oriented
        linkCollectorRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Associates the adapter with the RecyclerView
        linkCollectorRecyclerView.setAdapter(new LinkCollectorAdapter(links, this));

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(LinkCollectorActivity.this);
                LayoutInflater layoutInflater = LayoutInflater.from(LinkCollectorActivity.this);
                View dialogView = layoutInflater.inflate(R.layout.layout_link_input_dialog, null);

                builder.setTitle("Please Input Url")
                        .setNegativeButton("Cancel", null)
                        .setView(dialogView)
                        .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            @SuppressLint("NotifyDataSetChanged")
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                EditText urlName = dialogView.findViewById(R.id.inputUrlName);
                                EditText urlValue = dialogView.findViewById(R.id.inputUrlValue);
                                String urlN = urlName.getText().toString();
                                String urlV = urlValue.getText().toString();
                                if (URLUtil.isHttpUrl(urlV) || URLUtil.isHttpsUrl(urlV)) {
                                    links.add(new Link(urlN, urlV));
                                    linkCollectorAdapter.notifyDataSetChanged();
                                } else {
                                    String prompt = "Please input a valid url starts with http or https";
                                    Snackbar snackbar = Snackbar.make(findViewById(R.id.linkCollectorRecyclerView),
                                            prompt, Snackbar.LENGTH_SHORT);
                                    snackbar.show();
                                }
                            }
                        });
                builder.show();
            }
        });
    }
}