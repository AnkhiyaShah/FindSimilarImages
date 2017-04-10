package com.example.ankhiya.findsimilarimages.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.ankhiya.findsimilarimages.R;
import com.example.ankhiya.findsimilarimages.operations.SimilarImagesFinder;
import com.example.ankhiya.findsimilarimages.utils.FileModel;
import com.example.ankhiya.findsimilarimages.utils.FilesAdapter;
import com.example.ankhiya.findsimilarimages.utils.SearchMode;

import java.io.File;
import java.util.ArrayList;


/**
 * this activity is main activity to run app.
 */

public class HomeActivity extends AppCompatActivity implements SimilarImagesFinder.OnSearchFinished, FilesAdapter.OnFileSelectedListener {

    private static final String KEY_SEARCH_MODE = "searchMode";
    private String mBasePath;
    private SearchMode mSearchMode;
    private String mCurrentDirectory;
    private ArrayList<FileModel> mListFiles;
    private RecyclerView mRecyclerView;
    private Button mSearchButton;
    private Button mDeleteButton;
    private FilesAdapter mFilesAdapter;
    private ArrayList<String> mPathsSelectionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mPathsSelectionList = new ArrayList<>();
        Intent intent = getIntent();
        if (intent != null) {
            mSearchMode = (SearchMode) intent.getSerializableExtra(KEY_SEARCH_MODE);
        }
        mRecyclerView = (RecyclerView) findViewById(R.id.files_recyclerView);
        mSearchButton = (Button) findViewById(R.id.search_button);
        mDeleteButton = (Button) findViewById(R.id.btn_delete);
        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SimilarImagesFinder(HomeActivity.this, HomeActivity.this).execute(mCurrentDirectory);
            }
        });
        mDeleteButton.setVisibility(View.GONE);
        mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDeleteFiles();
            }
        });
        initData();
        setUpRecyclerView();
        populateListOfFiles();
    }

    private void onDeleteFiles() {
        for (FileModel model : mListFiles) {
            File file = new File(model.getName());
            file.delete();
        }
        mListFiles.clear();
        mCurrentDirectory = mBasePath;
        populateListOfFiles();
        mSearchButton.setVisibility(View.VISIBLE);
        mDeleteButton.setVisibility(View.GONE);
    }

    private void initData() {
        setBasePath();
        mListFiles = new ArrayList<>();
        mCurrentDirectory = mBasePath;
        mPathsSelectionList.add(mBasePath);
    }

    private void setUpRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 3);
        mRecyclerView.setLayoutManager(layoutManager);
        mFilesAdapter = new FilesAdapter(this, mListFiles, this);
        mRecyclerView.setAdapter(mFilesAdapter);
    }

    private void populateListOfFiles() {
        mListFiles.clear();
        if (mCurrentDirectory == null)
            return;
        File folder = new File(mCurrentDirectory);
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                FileModel model = new FileModel();
                model.setName(file.getAbsolutePath());
                mListFiles.add(model);
            }
            mFilesAdapter.setFiles(mListFiles);
        }
    }

    private void setBasePath() {
        File file = Environment.getExternalStorageDirectory();
        if (file != null) {
            mBasePath = file.getAbsolutePath();
        }
    }

    @Override
    public void similarImagesPaths(ArrayList<String> paths) {
        mSearchButton.setVisibility(View.GONE);
        mDeleteButton.setVisibility(View.VISIBLE);
        mListFiles.clear();
        for (String path : paths) {
            FileModel model = new FileModel();
            model.setName(path);
            mListFiles.add(model);
        }
        mFilesAdapter.setFiles(mListFiles);
    }

    @Override
    public void onFileSelectedListener(FileModel model) {
        File file = new File(model.getName());
        if (file != null && file.exists() && file.isDirectory()) {
            mCurrentDirectory = model.getName();
            mPathsSelectionList.add(model.getName());
            populateListOfFiles();
        } else {
            Toast.makeText(this, "Please select folder", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed() {
        if(mPathsSelectionList.size() > 1) {
            mPathsSelectionList.remove(mPathsSelectionList.size() - 1);
            mCurrentDirectory = mPathsSelectionList.get(mPathsSelectionList.size() - 1);
            populateListOfFiles();
        }
        else{
            super.onBackPressed();
        }
    }
}
