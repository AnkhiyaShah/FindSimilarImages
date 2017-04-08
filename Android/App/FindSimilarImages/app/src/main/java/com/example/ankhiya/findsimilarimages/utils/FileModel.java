package com.example.ankhiya.findsimilarimages.utils;

import android.graphics.Path;

import java.io.File;

/**
 * Created by ankhiya on 4/6/17.
 */

public class FileModel {

    private String name;
    private boolean isDirectory;

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
