package com.example.selfless_care;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Mental_Care#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Mental_Care extends Fragment {
    RecyclerView recyclerView;
    ArrayList<String> name,title,email,mobile_number;
    MyAdapter adapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Mental_Care() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MentalHealthCare.
     */
    // TODO: Rename and change types and number of parameters
    public static Mental_Care newInstance(String param1, String param2) {
        Mental_Care fragment = new Mental_Care();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        name=new ArrayList<>();
        title=new ArrayList<>();
        email=new ArrayList<>();
        mobile_number=new ArrayList<>();
        adapter=new MyAdapter(getContext(),name,email,mobile_number,title);
//        recyclerView.setAdapter(adapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Log.i("name",name.toString());
        displayData();
        Log.i("name",name.toString());
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private void displayData() {
        Database db = new Database(getContext());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            db.getData("Mental Health").thenAccept(querySnapshot -> {
                Log.i("TAG", "QuerySnapshot received: " + querySnapshot);

                // Access documents or other properties from the QuerySnapshot as needed
                List<DocumentSnapshot> documents = querySnapshot.getDocuments();
                for (DocumentSnapshot document : documents) {
                    Map<String, Object> data = document.getData();
                    Log.d("TAG", document.getId() + " => " + data);
                    name.add((String) data.get("Username"));
                    title.add((String) data.get("Title"));
                    email.add((String) data.get("Email"));
                    mobile_number.add((String) data.get("Mobile Number"));
                }
                Log.i("prev",name.toString()+title.toString());
            }).exceptionally(ex -> {
                // Handle exceptions (this code runs on the UI thread)
                Log.e("TAG", "Error getting data", ex);
                return null;
            });
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.chats, container, false);
        recyclerView =view.findViewById(R.id.doc_details);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        if (getArguments() != null) {
        }
        return view;
    }
}