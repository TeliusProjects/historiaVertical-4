package com.example.admin.prova;


import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

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
    private ImageView userImagev;
    private View rootView = null;
    private TextView userTextV;
    private TextView emailTextV;
    GridView gridViewMyList;

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

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_my_list_, container, false);

        return rootView;
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle newBundle = this.getArguments();
        String imageURL = newBundle.getString("profileImage");
        String username = newBundle.getString("username");
        String user_email = newBundle.getString("useremail");
        userImagev = (ImageView) rootView.findViewById(R.id.myListImage);
        userTextV = (TextView)  rootView.findViewById(R.id.list_User);
        emailTextV = (TextView) rootView.findViewById(R.id.list_EmailUser);
        gridViewMyList = (GridView) rootView.findViewById(R.id.gridlist);

        Picasso
                .with(getActivity())
                .load(imageURL).resize(512,512)
                .transform(new CircleTransform())
                .into(userImagev);
        userTextV.setText(username);
        emailTextV.setText(user_email);

        fill_List();
    }

    public void fill_List()
    {
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);


        final File[] files = storageDir.listFiles();


        MyListAdapter adapter = new MyListAdapter(rootView.getContext(),files);
        gridViewMyList.setAdapter(adapter);


    }
}
