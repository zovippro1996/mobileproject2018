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
    Review review_2;

    TextView textView_reviewDate = null;
    RatingBar ratingBar = null;
    TextView textView_comment = null;
    TextView textView_viewmore = null;
    TextView textView_noreview = null;

    TextView textView_reviewDate2 = null;
    RatingBar ratingBar2 = null;
    TextView textView_comment2 = null;

    public FragmentReview(){}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            review_number = getArguments().getLong("review_number",0);
        }
        if(review_number>0){
            review = getArguments().getParcelable("review");
            if(review_number>1){
                review_2 = getArguments().getParcelable("review_2");
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inf = inflater.inflate(R.layout.fragment_review, container, false);

        textView_reviewDate = (TextView)inf.findViewById(R.id.textView_reviewDate);
        ratingBar = (RatingBar)inf.findViewById(R.id.ratingBar);
        textView_comment = (TextView)inf.findViewById(R.id.textView_comment);
        textView_reviewDate2 = (TextView)inf.findViewById(R.id.textView_reviewDate2);
        ratingBar2 = (RatingBar)inf.findViewById(R.id.ratingBar2);
        textView_comment2 = (TextView)inf.findViewById(R.id.textView_comment2);
        textView_viewmore = (TextView)inf.findViewById(R.id.textView_viewmore);
        textView_noreview = (TextView)inf.findViewById(R.id.textView_noreview);

        if(review_number<1){
            textView_reviewDate.setVisibility(View.GONE);
            ratingBar.setVisibility(View.GONE);
            textView_comment.setVisibility(View.GONE);
            textView_reviewDate2.setVisibility(View.GONE);
            ratingBar2.setVisibility(View.GONE);
            textView_comment2.setVisibility(View.GONE);
            textView_viewmore.setVisibility(View.GONE);
            textView_noreview.setVisibility(View.VISIBLE);


        } else if(review_number==1){
            textView_reviewDate.setVisibility(View.VISIBLE);
            ratingBar.setVisibility(View.VISIBLE);
            textView_comment.setVisibility(View.VISIBLE);
            textView_reviewDate2.setVisibility(View.GONE);
            ratingBar2.setVisibility(View.GONE);
            textView_comment2.setVisibility(View.GONE);
            textView_viewmore.setVisibility(View.VISIBLE);
            textView_noreview.setVisibility(View.GONE);

            textView_reviewDate.setText(review.getStringDate());
            ratingBar.setRating(review.getOverallRating());
            textView_comment.setText(review.getReviewContent());
            textView_comment.setMaxLines(5);
        } else {
            textView_reviewDate.setVisibility(View.VISIBLE);
            ratingBar.setVisibility(View.VISIBLE);
            textView_comment.setVisibility(View.VISIBLE);
            textView_viewmore.setVisibility(View.VISIBLE);
            textView_noreview.setVisibility(View.GONE);

            textView_reviewDate.setText(review.getStringDate());
            ratingBar.setRating(review.getOverallRating());
            textView_comment.setText(review.getReviewContent());
            textView_comment.setMaxLines(2);

            textView_reviewDate2.setText(review_2.getStringDate());
            ratingBar2.setRating(review_2.getOverallRating());
            textView_comment2.setText(review_2.getReviewContent());
            textView_comment2.setMaxLines(2);
        }


        // Inflate the layout for this fragment
        return inf;
    }
}
