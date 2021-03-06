package com.example.alab.for_j;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

class main2_adapter extends ArrayAdapter<Review> {
    int numpeople;
    public main2_adapter(Context context, List<Review> reviews) {
        super(context, R.layout.review, reviews);
    }
    TextView idText;
    TextView commentText;
    RatingBar ratingBar;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflator = LayoutInflater.from(getContext());
            convertView = inflator.inflate(R.layout.review, parent, false);
        }
        idText = (TextView) convertView.findViewById(R.id.textView);
        commentText = (TextView) convertView.findViewById(R.id.textView2);
        ratingBar = (RatingBar) convertView.findViewById(R.id.review_ratingbar);

        Review review = getItem(position);
        idText.setText(review.id);
        commentText.setText(review.comment);
        ratingBar.setRating(review.rating);

        return convertView;
    }
}

public class main2 extends AppCompatActivity {
    ListView reviewListView;
    List<Review> reviews = new ArrayList<>();
    ArrayAdapter<Review> reviewListAdapter;

    RatingBar netizen;
    RatingBar expert;
    TextView expert_num;
    TextView netizen_num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.boosan_poster);

        reviewListView = (ListView) findViewById(R.id.listView);
        reviewListAdapter = new main2_adapter(this, reviews);
        reviewListView.setAdapter(reviewListAdapter)
        ;
        expert= (RatingBar) findViewById(R.id.expert_ratingbar);
        expert_num= (TextView) findViewById(R.id.expert_float) ;
        netizen = (RatingBar) findViewById(R.id.netizen_ratingbar);
        netizen_num =(TextView)findViewById(R.id.netizen_float_num);
        expert.setRating((float)3.5);
        expert_num.setText(String.valueOf((float)3.5));
    }

    public void postreview(View v)
    {
        Intent intent = new Intent(this, AddReviewActivity.class);
        startActivityForResult(intent, 1);

    }
    public void boosanhang_trailor(View v)
    {
        String url = "https://www.youtube.com/watch?v=UOTOjA0ngmk";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }


    int numpeople=0;
    float sum= (float) 0.0;
    float realfinal=(float) 0.0;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode==RESULT_OK)
// 액티비티가 정상적으로 종료되었을 경우
        {
            if(requestCode==1)
// InformationInput에서 호출한 경우에만 처리합니다.
            { numpeople++;
// 받아온 이름과 전화번호를 InformationInput 액티비티에 표시합니다.
                Review review = new Review();
                review.id = data.getStringExtra("data_name");
                review.comment =data.getStringExtra("data_review");
                review.rating = data.getFloatExtra("data_rating", 0);

                reviews.add(review);
                reviewListAdapter.notifyDataSetChanged();

                sum = review.rating + sum;
                realfinal=(float) sum/ numpeople;

                netizen.setRating(realfinal);
                netizen_num.setText(String.valueOf(realfinal));

            }
        }
    }
}
