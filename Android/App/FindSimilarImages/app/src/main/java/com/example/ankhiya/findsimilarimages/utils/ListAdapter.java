package com.example.ankhiya.findsimilarimages.utils;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ankhiya.findsimilarimages.R;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by ankhiya on 4/6/17.
 */

public class ListAdapter extends BaseAdapter {

    private ArrayList<FileModel> fileModels;
    private Context mContext;

    public ListAdapter(Context context, ArrayList<FileModel> models) {
        fileModels = new ArrayList<>();
        fileModels.addAll(models);
        mContext = context;
    }

    @Override
    public int getCount() {
        return fileModels.size();
    }

    @Override
    public FileModel getItem(int position) {
        return fileModels.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.view_file_item,null);
        if(view != null){
            ImageView imageView = (ImageView) view.findViewById(R.id.iv_file_image);
            TextView textView = (TextView) view.findViewById(R.id.tv_file_name);
            FileModel fileModel = fileModels.get(position);
            textView.setText(fileModel.toString());
            if(!fileModel.isDirectory()){
                imageView.setImageURI(Uri.fromFile(new File(fileModel.getName())));
            }
        }
        return view;
    }
}
