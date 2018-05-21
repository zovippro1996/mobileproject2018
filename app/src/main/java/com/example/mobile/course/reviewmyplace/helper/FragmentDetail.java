package com.example.mobile.course.reviewmyplace.helper;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mobile.course.reviewmyplace.EstablishmentDetailActivity;
import com.example.mobile.course.reviewmyplace.R;
import com.example.mobile.course.reviewmyplace.object.Establishment;
import com.facebook.CallbackManager;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareButton;
import com.facebook.share.widget.ShareDialog;
import com.facebook.FacebookSdk;

import java.util.Objects;


public class FragmentDetail extends Fragment {

    //Initialize Component in Fragment Detail
    TextView textView_establishmentName = null;
    TextView textView_userID = null;
    TextView textView_establishmentType = null;
    TextView textView_foodType = null;
    TextView textView_Location = null;

    Establishment establishment;

    CallbackManager callbackManager;
    ShareDialog shareDialog;

    public FragmentDetail(){}


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            establishment = getArguments().getParcelable("establishment");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        shareDialog = new ShareDialog(this);
        View inf = inflater.inflate(R.layout.fragment_detail, container, false);

        textView_establishmentName = inf.findViewById(R.id.textView_establishmentName);
        textView_establishmentName.setText(establishment.getEstablishmentName());

        textView_userID = inf.findViewById(R.id.textView_userID);
        textView_userID.setText(establishment.getUserID());

        textView_establishmentType = inf.findViewById(R.id.textView_establishmentType);
        textView_establishmentType.setText(establishment.getEstablishmentType().name().replace
                ("_"," "));

        textView_foodType = inf.findViewById(R.id.textView_foodType);
        textView_foodType.setText(establishment.getFood());

        textView_Location = inf.findViewById(R.id.textView_Location);
        textView_Location.setText(establishment.getEstablishmentLocation().getDescription());

        //Share Content on Facebook
        Bitmap image = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);

        switch (establishment.getEstablishmentType()){
            case BAR:
                image = BitmapFactory.decodeResource(getResources(), R.drawable.default_bar);
                break;
            case RESTAURANT:
                image = BitmapFactory.decodeResource(getResources(), R.drawable.default_restaurant);
                break;
            case COFFEE_SHOP:
                image = BitmapFactory.decodeResource(getResources(), R.drawable.default_coffeeshop);
                break;
            case NONE:
                break;
        }

        SharePhoto photo = new SharePhoto.Builder()
                .setCaption("My New Destination")
                .setBitmap(image)
                .build();

        SharePhotoContent content = new SharePhotoContent.Builder()
                .addPhoto(photo)
                .build();

        //Attach ShareContent to Share Button
        ShareButton shareButton = (ShareButton)inf.findViewById(R.id.fb_share_button);
        shareButton.setShareContent(content);

        // Inflate the layout for this fragment
        return inf;
    }

    @Override
    public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

}
