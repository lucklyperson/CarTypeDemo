package com.example.wu.cartypedemo;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by $wu on 2018-01-12 下午 2:03.
 * 介绍：
 */

public class CarBrandAdapter extends RecyclerView.Adapter {

    private List<Car> list;
    private Context context;
    private OnAdapterClickListener listener;
    private int clickPosition = -1;

    public CarBrandAdapter(List<Car> list, Context context) {
        if (list == null) {
            this.list = new ArrayList<>();
        } else {
            this.list = list;
        }
        this.context = context;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_car_brand, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        Car car = list.get(position);
        final MyViewHolder mHolder = (MyViewHolder) holder;
        mHolder.title.setText(car.getName());
        if (!TextUtils.isEmpty(car.getLogo())) {
            Glide.with(context).load(car.getLogo()).asBitmap().diskCacheStrategy(DiskCacheStrategy.RESULT).into(mHolder.ivBrand);
        }

        if (position == clickPosition) {
            mHolder.title.setTextColor(Color.parseColor("#ff6400"));
        } else {
            mHolder.title.setTextColor(Color.BLACK);
        }
        if (position == 0) {
            mHolder.catalog.setVisibility(View.VISIBLE);
            mHolder.catalog.setText(car.getInitial());
        } else {
            if (list.size() > 0) {
                Car leftCar = list.get(position - 1);
                if (TextUtils.equals(leftCar.getInitial(), car.getInitial())) {
                    mHolder.catalog.setVisibility(View.GONE);
                } else {
                    mHolder.catalog.setText(car.getInitial());
                    mHolder.catalog.setVisibility(View.VISIBLE);
                }
            }
        }
        mHolder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(position);
                if (clickPosition != -1) {
                    notifyItemChanged(clickPosition);
                }
                if (position != clickPosition) {
                    clickPosition = position;
                }
                notifyItemChanged(position);

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public int getPositionForSection(String sort) {
        for (int i = 0; i < list.size(); i++) {
            Car car = list.get(i);
            if (TextUtils.equals(String.valueOf(sort), car.getInitial().toUpperCase())) {
                return i;
            }
        }
        return -1;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.catalog)
        TextView catalog;
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.iv_brand)
        ImageView ivBrand;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void setOnItemClickListener(OnAdapterClickListener listener) {
        this.listener = listener;
    }

}
