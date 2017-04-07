package com.example.ankhiya.findsimilarimages.ui;


import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ankhiya.findsimilarimages.R;

import java.io.File;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DirectoryChooserFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DirectoryChooserFragment extends DialogFragment {

    private static final String KEY_DIRECTORY_PATH = "basePath";

    private String mBasePath;
    private String mCurrentDirectory;
    private ArrayList<String> mListFiles;
    private ListView mListView;
    private ArrayAdapter<String> mListFilesAdapter;

    public DirectoryChooserFragment() {
        // Required empty public constructor
    }

    public static DirectoryChooserFragment newInstance(Bundle bundle) {
        DirectoryChooserFragment fragment = new DirectoryChooserFragment();
        if (bundle != null) {
            fragment.setArguments(bundle);
        }
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mBasePath = getArguments().getString(KEY_DIRECTORY_PATH);
        }
        mListFiles = new ArrayList<>();
        mCurrentDirectory = mBasePath;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_directory_chooser, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mListView = (ListView) view.findViewById(R.id.files_list);
        setUpListView();
        populateListOfFiles();
    }

    private void populateListOfFiles() {
        mListFiles.clear();
        if(mCurrentDirectory == null)
            return;
        File folder = new File(mCurrentDirectory);
        File[] files = folder.listFiles();
        if(files != null) {
            for (File file : files) {
                mListFiles.add(file.getAbsolutePath());
            }
            mListFilesAdapter.notifyDataSetChanged();
        }
    }

    private void setUpListView() {
        mListFilesAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, mListFiles);
        mListView.setAdapter(mListFilesAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String path = mListFiles.get(position);
                onClickOnFile(path);
            }
        });
    }

    private void onClickOnFile(String selectedPath){
        File file = new File(selectedPath);
        if(file != null && file.exists() && file.isDirectory()){
            mCurrentDirectory = selectedPath;
            populateListOfFiles();
        }
        else {
            Toast.makeText(getActivity(),"Please select folder",Toast.LENGTH_LONG).show();
        }
    }

    public static class DirectoryChooserConfig {

        private String directoryPath;

        public DirectoryChooserConfig() {
            File file = Environment.getExternalStorageDirectory();
            if (file != null) {
                directoryPath = file.getAbsolutePath();
            }
        }

        public DirectoryChooserConfig setAbsolutePath(String path) {
            this.directoryPath = path;
            return this;
        }

        public DirectoryChooserFragment build() {
            Bundle bundle = new Bundle();
            bundle.putString(KEY_DIRECTORY_PATH, directoryPath);
            return DirectoryChooserFragment.newInstance(bundle);
        }

    }
}
