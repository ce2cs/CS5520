package edu.northeastern.testapplication.linkCollector;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import edu.northeastern.testapplication.R;

public class LinkCollectorAdapter extends RecyclerView.Adapter<LinkViewHolder> {
    private final List<Link> links;
    private final Context context;

    public LinkCollectorAdapter(ArrayList<Link> links, Context context) {
        this.links = links;
        this.context = context;
    }

    @NonNull
    @Override
    public LinkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Create an instance of the viewholder by passing it the layout inflated as view and no root.
        return new LinkViewHolder(LayoutInflater.from(context).inflate(R.layout.link_view_holder, null));
    }

    @Override
    public void onBindViewHolder(@NonNull LinkViewHolder holder, int position) {
        // sets the name of the person to the name textview of the viewholder.
        holder.name.setText(links.get(position).getName());
        // sets the age of the person to the age textview of the viewholder.
        holder.url.setText(links.get(position).getUrl());

        // set a click event on the whole itemView (every element of the recyclerview).
//        holder.itemView.setOnClickListener(view -> {
//            Toast.makeText(context, links.get(position).getName(), Toast.LENGTH_SHORT).show();
//        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Link link = links.get(position);
                Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                String website = link.getUrl();
                browserIntent.setData(Uri.parse(website));
                context.startActivity(browserIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        // Returns the size of the recyclerview that is the list of the arraylist.
        return links.size();
    }
}
