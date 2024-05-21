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
 * Use the {@link Req_Services#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Req_Services extends Fragment {

    RecyclerView recyclerView;
    ArrayList<String> servicename,location,mobile_number;
    MyBloodAdapter adapter;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Req_Services() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HospitalAppointmentsandAmbulanceServices.
     */
    // TODO: Rename and change types and number of parameters
    public static Req_Services newInstance(String param1, String param2) {
        Req_Services fragment = new Req_Services();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        servicename=new ArrayList<>();
        location=new ArrayList<>();
        mobile_number=new ArrayList<>();
        adapter=new MyBloodAdapter(getContext(),servicename,location,mobile_number);
//        recyclerView.setAdapter(adapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        displayData();
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private void displayData() {
        Database db = new Database(getContext());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            db.getData("Services").thenAccept(querySnapshot -> {
                Log.i("TAG", "QuerySnapshot received: " + querySnapshot);

                // Access documents or other properties from the QuerySnapshot as needed
                List<DocumentSnapshot> documents = querySnapshot.getDocuments();
                for (DocumentSnapshot document : documents) {
                    Map<String, Object> data = document.getData();
                    Log.d("TAG", document.getId() + " => " + data);
                    servicename.add((String) data.get("Name"));
                    location.add((String) data.get("Location"));
                    mobile_number.add((String) data.get("Mobile Number"));
                }
//                adapter=new MyAdapter(getContext(),name,title,email,mobile_number);
//                recyclerView.setAdapter(adapter);
//                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
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
        View view = inflater.inflate(R.layout.req_services, container, false);
        recyclerView =view.findViewById(R.id.req_services);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        if (getArguments() != null) {
        }
        return view;
    }
}