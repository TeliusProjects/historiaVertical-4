package com.example.admin.prova;


import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import java.io.File;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyList_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyList_Fragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private View rootView = null;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public MyList_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyList_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyList_Fragment newInstance(String param1, String param2) {
        MyList_Fragment fragment = new MyList_Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        fill_List();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_list_, container, false);
    }

    public void fill_List()
    {
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);


        final File[] files = storageDir.listFiles();

        GridView gridViewMyList = (GridView) rootView.findViewById(R.id.gridlist);
        MyListAdapter adapter = new MyListAdapter(getActivity(),files);
        gridViewMyList.setAdapter(adapter);


    }
}
