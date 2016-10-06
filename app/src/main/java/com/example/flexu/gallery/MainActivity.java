package com.example.flexu.gallery;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    RecyclerGalleryAdatper mGalleryAdapter;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv);
        mGalleryAdapter = new RecyclerGalleryAdatper(this);
        intent =new Intent(this, ShowPhotoActivity.class);
        mRecyclerView.setAdapter(mGalleryAdapter);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        mGalleryAdapter.showPhotos();
        intent =new Intent(this, ShowPhotoActivity.class);
        mGalleryAdapter.setItemClickListener(new ItemsClickListener(){

            @Override
            public void onItemClick(String path) {
                intent.putExtra("path", path);
                startActivity(intent);
            }
        });
    }



    public class RecyclerGalleryAdatper extends RecyclerView.Adapter {

        private ArrayList<String> paths=new ArrayList<>();
        private Context mContext;
        private ItemsClickListener mItemClickListener;

        private void showPhotos() {
            ArrayList<String> getpaths = new ArrayList<>();
            File a=new File(Environment.getExternalStorageDirectory().getPath()+"/DCIM/Camera");
            boolean b=a.isDirectory();
            File[] fl=a.listFiles();
            for(int i=0;i<fl.length;i++){
                getpaths.add(fl[i].getPath());
            }
            paths=getpaths;
        }

        public RecyclerGalleryAdatper(Context context)  {
            mContext = context;
        }
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gallery_item, parent, false);
            return new GalleryViewHolder(view);
        }

        public void setItemClickListener(ItemsClickListener itemsClickListener) {
            mItemClickListener=itemsClickListener;
        }

        private class GalleryViewHolder extends RecyclerView.ViewHolder{

            ImageView photo;

            public GalleryViewHolder(View itemView) {
                super(itemView);
                photo = (ImageView) itemView.findViewById(R.id.iv);
            }

            public void bind(final String a, final ItemsClickListener listener) {
                File image=new File(a);
                Picasso.with(mContext).load(image).resize(480, 800).into(photo);
                if(listener!=null){
                    itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            listener.onItemClick(a);
                        }
                    });
                }
            }
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if(holder instanceof GalleryViewHolder){
                ((GalleryViewHolder)holder).bind(paths.get(position), mItemClickListener);
            }
        }


        @Override
        public int getItemCount() {
            return paths.size();
        }
    }
}


