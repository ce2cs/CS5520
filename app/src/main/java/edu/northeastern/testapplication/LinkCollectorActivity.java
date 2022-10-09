package edu.northeastern.testapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.zip.Inflater;

import edu.northeastern.testapplication.R;
import edu.northeastern.testapplication.linkCollector.Link;
import edu.northeastern.testapplication.linkCollector.LinkCollectorAdapter;

public class LinkCollectorActivity extends AppCompatActivity {

    private ArrayList<Link> links = new ArrayList<>();
    private LinkCollectorAdapter linkCollectorAdapter = new LinkCollectorAdapter(links, this);
    private LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
    private RecyclerView linkCollectorRecyclerView;
    private Parcelable recyclerViewState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("onCreate!");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_link_collector);

        //Adding a new person object to the personList arrayList
        linkCollectorRecyclerView = findViewById(R.id.linkCollectorRecyclerView);

        //This defines the way in which the RecyclerView is oriented
        linkCollectorRecyclerView.setLayoutManager(linearLayoutManager);

        //Associates the adapter with the RecyclerView
        linkCollectorRecyclerView.setAdapter(linkCollectorAdapter);

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


        ItemTouchHelper mIth = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN,
                        ItemTouchHelper.LEFT) {
                    @Override
                    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {


                    }
                });
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        links = savedInstanceState.getParcelableArrayList("links");
        for (Link l: links) {
            System.out.println(l.getName() + l.getUrl());
        }
        linkCollectorAdapter.setLinks(links);
        for (Link l: linkCollectorAdapter.getLinks()) {
            System.out.println(l.getName() + l.getUrl());
        }
        linkCollectorAdapter.notifyDataSetChanged();
        recyclerViewState = savedInstanceState.getParcelable("recyclerViewState");
        if (recyclerViewState != null) {
            linkCollectorRecyclerView.getLayoutManager().onRestoreInstanceState(recyclerViewState);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList("links", links);
        recyclerViewState = linkCollectorRecyclerView.getLayoutManager().onSaveInstanceState();
        if (recyclerViewState != null) {
            outState.putParcelable("recyclerViewState", recyclerViewState);
        }
        super.onSaveInstanceState(outState);
    }
}