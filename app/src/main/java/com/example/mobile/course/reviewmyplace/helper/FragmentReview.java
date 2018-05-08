package com.example.mobile.course.reviewmyplace.helper;

import android.media.Rating;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.mobile.course.reviewmyplace.R;
import com.example.mobile.course.reviewmyplace.object.Review;

public class FragmentReview extends Fragment {

    //Initialize Component in Fragment Review
    long review_number;
    Review review;

    TextView textView_reviewDate = null;
    RatingBar ratingBar = null;
    TextView textView_comment = null;
    TextView textView_viewmore = null;
    TextView textView_noreview = null;

    public FragmentReview(){}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        review_number = getArguments().getLong("review_number",0);
        if(review_number>0){
            review = getArguments().getParcelable("review");
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inf = inflater.inflate(R.layout.fragment_review, container, false);

        textView_reviewDate = (TextView)inf.findViewById(R.id.textView_reviewDate);
        ratingBar = (RatingBar)inf.findViewById(R.id.ratingBar);
        textView_comment = (TextView)inf.findViewById(R.id.textView_comment);
        textView_viewmore = (TextView)inf.findViewById(R.id.textView_viewmore);
        textView_noreview = (TextView)inf.findViewById(R.id.textView_noreview);

        if(review_number<1){
            textView_reviewDate.setVisibility(View.GONE);
            ratingBar.setVisibility(View.GONE);
            textView_comment.setVisibility(View.GONE);
            textView_viewmore.setVisibility(View.GONE);
            textView_noreview.setVisibility(View.VISIBLE);
        } else {
            textView_reviewDate.setVisibility(View.VISIBLE);
            ratingBar.setVisibility(View.VISIBLE);
            textView_comment.setVisibility(View.VISIBLE);
            textView_viewmore.setVisibility(View.VISIBLE);
            textView_noreview.setVisibility(View.GONE);

            textView_reviewDate.setText(review.getStringDate());
            ratingBar.setRating(review.getOverallRating());
            textView_comment.setText(review.getReviewContent());
        }


        // Inflate the layout for this fragment
        return inf;
    }
}
