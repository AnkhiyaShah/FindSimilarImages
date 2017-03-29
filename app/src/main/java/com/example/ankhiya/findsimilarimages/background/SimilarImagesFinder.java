package com.example.ankhiya.findsimilarimages.background;

import android.content.Context;

import com.example.ankhiya.findsimilarimages.utils.ImageHash;
import com.example.ankhiya.findsimilarimages.utils.StringUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by ankhiya on 3/19/17.
 * <p>
 * this class provides you list of paths of images which are similar.
 * you need to pass folder path in which you want to find similar images.
 * </p>
 */

public class SimilarImagesFinder {

    private HashMap<String,String> mImageHashMap;
    private ArrayList<String> imagesToFindForSimilarity; // all images
    private ArrayList<String> similarImages; // this will contain allthe file paths who have similar hash
    private String mFolderPath;
    private OnSearchFinished mOnSearchFinished;
    private Context mContext;

    public interface OnSearchFinished {
        void similarImagesPaths(ArrayList<String> paths);
    }

    public SimilarImagesFinder(Context context) {
        imagesToFindForSimilarity = new ArrayList<>();
        similarImages = new ArrayList<>();
        mImageHashMap = new HashMap<>();
        mContext = context;
    }

    /**
     * call this method to set folder path
     */
    public void setFolderPath(String path) {
        mFolderPath = path;
    }

    public void startFinding() {
        getAllImageFiles();
        // calculate hash for all image files

        for (String file : imagesToFindForSimilarity){

            String hash = ImageHash.md5ForFile(file);

            if(!StringUtils.isNullOrEmpty(hash)){ // hash - null - return true - ! - false
                mImageHashMap.put(file,hash);
            }
        }

        // check for similarity
    }

    private void getAllImageFiles() {
        // ensure that folder path exits
        File folder = new File(mFolderPath);
        // ensure that path is a directory
        if (folder.isDirectory()) {
            // get all files under that directory
            String[] allFiles = folder.list();
            for (String file : allFiles) {
                // check if file is image
                if (ImageHash.isFileContentTypeImage(mContext, file)) {
                    // adding all files in array for further proceed
                    imagesToFindForSimilarity.add(file);
                }
            }
        }
    }

}
