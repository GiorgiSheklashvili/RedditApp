package com.example.gio.redditapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.gio.redditapp.Response.Child;
import com.example.gio.redditapp.Response.Gif;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;

/**
 * Created by Gio on 11/1/2016.
 */

public class GifRecyclerAdapter extends RecyclerView.Adapter<GifRecyclerAdapter.VH> {
    RecyclerItemClickListener Listener;
    Context context;
    ArrayList<Child> children;
    public GifRecyclerAdapter(Context context, RecyclerItemClickListener listener) {
        this.context = context;
        Listener = listener;
    }
    public void setChildren(ArrayList<Child> children) {
        this.children = children;
    }
    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.gif_recycler_item,parent,false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(final VH holder, int position) {
        String source = getItem(position).getResolutions().get(1).getUrl();
        Log.d("GIF", source);
        holder.progressBar.setVisibility(View.VISIBLE);
        Ion.with(context).load(source).intoImageView(holder.image).setCallback(new FutureCallback<ImageView>() {
            @Override
            public void onCompleted(Exception e, ImageView result) {
                holder.progressBar.setVisibility(View.GONE);
            }
        });
        holder.title.setText(getTitle(position));
    }
public Gif getItem(int pos){
    return children.get(pos).getChildData().getPreview().getImages().get(0).getVariants().getGif();
}

    @Override
    public int getItemCount() {

        if (children == null) return 0;
        return children.size();
    }

    public String getTitle(int pos) {
        return children.get(pos).getChildData().getTitle();
    }

    public class VH extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView title;
        ImageView image;
        ProgressBar progressBar;

        public VH(View itemView) {
            super(itemView);
            progressBar=(ProgressBar) itemView.findViewById(R.id.gif_progress);
            image =(ImageView) itemView.findViewById(R.id.gif_image);
            title=(TextView) itemView.findViewById(R.id.gif_title);
            image.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Listener.onItemClick(getItem(getAdapterPosition()).getResolutions().get(1));
        }
    }
}
