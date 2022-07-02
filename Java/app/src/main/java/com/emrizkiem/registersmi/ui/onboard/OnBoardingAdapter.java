package com.emrizkiem.registersmi.ui.onboard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.emrizkiem.registersmi.R;
import com.emrizkiem.registersmi.data.model.OnBoarding;

import java.util.ArrayList;

public class OnBoardingAdapter extends PagerAdapter {

    private ArrayList<OnBoarding> listData;

    public OnBoardingAdapter(ArrayList<OnBoarding> listData) {
        this.listData = listData;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) container.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.content_onboarding, null);

        TextView title = layout.findViewById(R.id.tv_title);
        TextView description = layout.findViewById(R.id.tv_description);
        ImageView image = layout.findViewById(R.id.img_icon);

        title.setText(listData.get(position).getTitle());
        description.setText(listData.get(position).getDescription());
        image.setImageResource(listData.get(position).getImg());

        container.addView(layout);
        return layout;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
}
