package com.example.rummenigged.daggertest.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.example.rummenigged.daggertest.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by rummenigged on 04/01/18.
 */

public class ImagesAdapter extends RecyclerView.Adapter<ImagesAdapter.CatImageVH>{
    private static String TAG = "ImagesRvAdapter";

    public interface ImageOnClick {
        void imageClicked(ImageView view, String url);
    }

    static class CatImageVH extends RecyclerView.ViewHolder {
        private ImageView imageIv;

        public CatImageVH(View itemView) {
            super(itemView);
            imageIv = itemView.findViewById(R.id.image_iv);
        }
    }

    private List<String> imageUrls;
    private ImageOnClick onClickListener;

    public ImagesAdapter(ImageOnClick onClick) {
        super();
        this.onClickListener = onClick;
    }

    public void updateImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
        notifyDataSetChanged(); // This is bad. Used for simplicity.
    }

    @Override
    public CatImageVH onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_cat_image, parent, false);
        return new CatImageVH(view);
    }

    @Override
    public void onBindViewHolder(final CatImageVH holder, final int position) {
        String imageUrl = imageUrls.get(position);
        Log.d(TAG, "Binding: " + imageUrl);
        Picasso picasso = Picasso.with(holder.imageIv.getContext());
        picasso.setIndicatorsEnabled(false);
        picasso.setLoggingEnabled(true);
        picasso.load(imageUrl).into(holder.imageIv);

        if (onClickListener != null) {
            holder.imageIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getAdapterPosition();
                    String url = imageUrls.get(position);
                    onClickListener.imageClicked(holder.imageIv, url);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (imageUrls == null) {
            return 0;
        }
        return imageUrls.size();
    }
}
