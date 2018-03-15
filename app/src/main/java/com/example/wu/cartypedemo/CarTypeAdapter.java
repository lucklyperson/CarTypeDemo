package com.example.wu.cartypedemo;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by $wu on 2018-01-12 下午 4:56.
 * 介绍：汽车类型Adapter
 */

public class CarTypeAdapter extends RecyclerView.Adapter {
    private List<Car> list;
    private Context context;
    private OnAdapterClickListener listener;
    private int clickPosition = -1;

    private static final String TAG = "CarTypeAdapter";

    public CarTypeAdapter(List<Car> list, Context context) {
        if (list == null) {
            this.list = new ArrayList<>();
        } else {
            this.list = list;
        }
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_car_type, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        Car car = list.get(position);

        String result = car.getName();
        final MyViewHolder mHolder = (MyViewHolder) holder;
        mHolder.tvType.setText(result);

        if (position == clickPosition) {
            mHolder.tvType.setTextColor(Color.parseColor("#ff6400"));
        } else {
            mHolder.tvType.setTextColor(Color.BLACK);
        }

        if (position == list.size() - 1) {
            mHolder.tvDivide.setVisibility(View.GONE);
        } else {
            mHolder.tvDivide.setVisibility(View.VISIBLE);
        }


        mHolder.tvType.setOnClickListener(new View.OnClickListener() {
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

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_type)
        TextView tvType;
        @BindView(R.id.tv_divide)
        TextView tvDivide;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void setOnItemClickListener(OnAdapterClickListener listener) {
        this.listener = listener;
    }


}