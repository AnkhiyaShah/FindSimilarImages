package com.example.ankhiya.findsimilarimages.utils;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ankhiya.findsimilarimages.R;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by ankhiya on 4/8/17.
 */

public class FilesAdapter extends RecyclerView.Adapter<FilesAdapter.FileViewHolder> {

    private Context mContext;
    private ArrayList<FileModel> mFiles;
    private OnFileSelectedListener mOnFileSelectedListener;

    public FilesAdapter(Context context,ArrayList<FileModel> models, OnFileSelectedListener listener){
        mContext = context;
        mFiles = new ArrayList<>();
        mFiles.addAll(models);
        mOnFileSelectedListener = listener;
    }

    public void setFiles(ArrayList<FileModel> models){
        mFiles.clear();
        mFiles.addAll(models);
        notifyDataSetChanged();
    }

    @Override
    public FileViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new FileViewHolder(LayoutInflater.from(mContext).inflate(R.layout.view_file_item,parent,false));
    }

    @Override
    public void onBindViewHolder(FileViewHolder holder, int position) {
        if(holder != null){
            holder.configureView(mFiles.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return mFiles.size();
    }

    public class FileViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView mIVFileImage;
        private TextView mTVFileName;

        public FileViewHolder(View itemView) {
            super(itemView);
            mIVFileImage = (ImageView) itemView.findViewById(R.id.iv_file_image);
            mTVFileName = (TextView) itemView.findViewById(R.id.tv_file_name);
            itemView.setOnClickListener(this);
        }

        public void configureView(FileModel model){
            mTVFileName.setText(model.toString());
            if(!model.isDirectory()){
                mIVFileImage.setImageURI(Uri.fromFile(new File(model.getName())));
            }
        }

        @Override
        public void onClick(View v) {
            if(mOnFileSelectedListener != null){
                mOnFileSelectedListener.onFileSelectedListener(mFiles.get(getAdapterPosition()));
            }
        }
    }

    public interface OnFileSelectedListener{
        void onFileSelectedListener(FileModel model);
    }
}
