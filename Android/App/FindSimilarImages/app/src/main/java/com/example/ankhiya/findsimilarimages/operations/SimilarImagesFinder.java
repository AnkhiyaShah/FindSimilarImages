package com.example.ankhiya.findsimilarimages.operations;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.example.ankhiya.findsimilarimages.utils.FileModel;
import com.example.ankhiya.findsimilarimages.utils.ImageHash;
import com.example.ankhiya.findsimilarimages.utils.SearchMode;
import com.example.ankhiya.findsimilarimages.utils.StringUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ankhiya on 3/19/17.
 * <p>
 * this class provides you list of paths of images which are similar.
 * you need to pass folder path in which you want to find similar images.
 * </p>
 */

public class SimilarImagesFinder extends AsyncTask<String, Void, ArrayList<String>> {

    private HashMap<String, String> mImageHashMap;
    private List<FileModel> mAllFilesOfFolder;
    private ArrayList<String> imagesToFindForSimilarity; // all images
    private ArrayList<String> similarImages; // this will contain all the file paths who have similar hash
    private String mFolderPath;
    private OnSearchFinished mOnSearchFinished;
    private Context mContext;
    private SearchMode mSearchMode;
    private ProgressDialog mLoadingProgressDialog;

    public interface OnSearchFinished {
        void similarImagesPaths(ArrayList<String> paths);
    }

    public SimilarImagesFinder(Context context, OnSearchFinished listener, SearchMode searchMode, List<FileModel> allFiles) {
        imagesToFindForSimilarity = new ArrayList<>();
        similarImages = new ArrayList<>();
        mImageHashMap = new HashMap<>();
        mAllFilesOfFolder = new ArrayList<>();
        mAllFilesOfFolder.addAll(allFiles);
        mContext = context;
        mOnSearchFinished = listener;
        mSearchMode = searchMode;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        showProgressDialog("Searching...");
    }

    @Override
    protected void onPostExecute(ArrayList<String> strings) {
        super.onPostExecute(strings);
        dismissProgressDialog();
        if (mOnSearchFinished != null) {
            mOnSearchFinished.similarImagesPaths(strings);
        }
    }

    @Override
    protected ArrayList<String> doInBackground(String... params) {
        if (params != null && params.length > 0) {
            mFolderPath = params[0];
            startFinding();
        }
        return similarImages;
    }

    private void startFinding() {
        if(mSearchMode == SearchMode.IMAGE) {
            getAllImageFiles();
        }
        else{
            getAllAudioFiles();
        }
        // calculate hash for all image files

        for (String file : imagesToFindForSimilarity) {

            String hash = ImageHash.md5ForFile(file);

            if (!StringUtils.isNullOrEmpty(hash)) { // hash - null - return true - ! - false
                mImageHashMap.put(file, hash);
            }
        }

        HashMap<String, String> mImageHashMapTemp = new HashMap<>();
        // check for similarity
        for (Map.Entry<String, String> entry : mImageHashMap.entrySet()) {
            String file = entry.getKey();
            String hash = entry.getValue();

            if (mImageHashMapTemp.containsValue(hash)) {
                similarImages.add(file);
            } else {
                mImageHashMapTemp.put(file, hash);
            }
        }
    }

    private void getAllAudioFiles(){
        for (FileModel file : mAllFilesOfFolder) {
            // check if file is image
            if (file.isAudioFile()) {
                // adding all files in array for further proceed
                imagesToFindForSimilarity.add(file.getName());
            }
        }
    }

    private void getAllImageFiles() {
        // ensure that folder path exits
//        File folder = new File(mFolderPath);
//        // ensure that path is a directory
//        if (folder.isDirectory()) {
//            // get all files under that directory
//            String[] allFiles = folder.list();
            for (FileModel file : mAllFilesOfFolder) {
                // check if file is image
                if (file.isImageFile()) {
                    // adding all files in array for further proceed
                    imagesToFindForSimilarity.add(file.getName());
                }
            }
//        }
    }

    public void showProgressDialog(String msg) {
        if (mLoadingProgressDialog != null) {
            mLoadingProgressDialog = null;
        }
        mLoadingProgressDialog = ProgressDialog.show(mContext, null, msg, false);
    }

    public void dismissProgressDialog() {
        if (mLoadingProgressDialog != null) {
            mLoadingProgressDialog.dismiss();
            mLoadingProgressDialog = null;
        }
    }

}
