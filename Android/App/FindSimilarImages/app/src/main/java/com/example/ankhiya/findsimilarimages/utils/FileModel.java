package com.example.ankhiya.findsimilarimages.utils;

import android.graphics.Path;

import java.io.File;

/**
 * Created by ankhiya on 4/6/17.
 */

public class FileModel {

    private String name;
    private boolean isDirectory;
    private boolean isImageFile;
    private boolean isAudioFile;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        if(this.name != null){
            File file = new File(this.name);
            isDirectory = file.isDirectory();
        }
    }

    public boolean isImageFile() {
        return isImageFile;
    }

    public void setImageFile(boolean imageFile) {
        isImageFile = imageFile;
    }

    public boolean isAudioFile() {
        return isAudioFile;
    }

    public void setAudioFile(boolean audioFile) {
        isAudioFile = audioFile;
    }

    public boolean isDirectory() {
        return isDirectory;
    }

    public void setDirectory(boolean directory) {
        isDirectory = directory;
    }

    @Override
    public String toString() {
       File file = new File(name);
        return file.getName();
    }
}
