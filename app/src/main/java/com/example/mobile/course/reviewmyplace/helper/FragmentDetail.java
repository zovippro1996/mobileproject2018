package com.example.mobile.course.reviewmyplace.helper;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mobile.course.reviewmyplace.R;
import com.example.mobile.course.reviewmyplace.object.Establishment;


public class FragmentDetail extends Fragment {

    //Initialize Component in Fragment Detail
    TextView textView_establishmentName = null;
    TextView textView_userID = null;
    TextView textView_establishmentType = null;
    TextView textView_foodType = null;
    TextView textView_Location = null;

    Establishment establishment;

    public FragmentDetail(){}


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        establishment = getArguments().getParcelable("establishment");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View inf = inflater.inflate(R.layout.fragment_detail, container, false);

        textView_establishmentName = (TextView) inf.findViewById(R.id.textView_establishmentName);
        textView_establishmentName.setText(establishment.getEstablishmentName());

        textView_userID = (TextView) inf.findViewById(R.id.textView_userID);
        textView_userID.setText(establishment.getUserID());

        textView_establishmentType = (TextView) inf.findViewById(R.id.textView_establishmentType);
        textView_establishmentType.setText(establishment.getEstablishmentType().name().replace
                ("_"," "));

        textView_foodType = (TextView) inf.findViewById(R.id.textView_foodType);
        textView_foodType.setText(establishment.getFood());

        textView_Location = (TextView) inf.findViewById(R.id.textView_Location);
        textView_Location.setText(establishment.getEstablishmentLocation().getDescription());

        // Inflate the layout for this fragment
        return inf;

    }
}
