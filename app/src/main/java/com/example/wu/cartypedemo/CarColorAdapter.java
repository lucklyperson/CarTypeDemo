package com.example.wu.cartypedemo;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by $wu on 2018-01-12 上午 11:42.
 * 介绍：
 */

public class CarColorAdapter extends RecyclerView.Adapter {
    private List<String> list;
    private Context context;
    private OnAdapterClickListener listener;

    public CarColorAdapter(List<String> list, Context context) {
        if (list == null) {
            this.list = new ArrayList<>();
        } else {
            this.list = list;
        }
        this.context = context;
    }

    @Override

    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_car_color, parent, false);
        return new MyViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        MyViewHolder mHolder = (MyViewHolder) holder;
        String result = list.get(position);
        switch (result) {
            case "白色":
                mHolder.tvColor.setText("白色");
                mHolder.ivColor.setBackground(context.getResources().getDrawable(R.drawable.button_invalid_grey_shape));
                break;
            case "红色":
                mHolder.tvColor.setText("红色");
                mHolder.ivColor.setBackgroundColor(Color.parseColor("#ff0000"));
                break;
            case "黑色":
                mHolder.tvColor.setText("黑色");
                mHolder.ivColor.setBackgroundColor(Color.parseColor("#000000"));
                break;
            case "银色":
                mHolder.tvColor.setText("银色");
                mHolder.ivColor.setBackgroundColor(Color.parseColor("#cccccc"));
                break;
            case "香槟色":
                mHolder.tvColor.setText("香槟色");
                mHolder.ivColor.setBackgroundColor(Color.parseColor("#e3ca78"));
                break;
            case "灰色":
                mHolder.tvColor.setText("灰色");
                mHolder.ivColor.setBackgroundColor(Color.parseColor("#c3c3c5"));
                break;
            case "蓝色":
                mHolder.tvColor.setText("蓝色");
                mHolder.ivColor.setBackgroundColor(Color.parseColor("#507cb7"));
                break;
            case "黄色":
                mHolder.tvColor.setText("黄色");
                mHolder.ivColor.setBackgroundColor(Color.parseColor("#feee34"));
                break;
            case "绿色":
                mHolder.tvColor.setText("绿色");
                mHolder.ivColor.setBackgroundColor(Color.parseColor("#60a470"));
                break;
            case "咖啡色":
                mHolder.tvColor.setText("咖啡色");
                mHolder.ivColor.setBackgroundColor(Color.parseColor("#946d09"));
                break;
            case "橙色":
                mHolder.tvColor.setText("橙色");
                mHolder.ivColor.setBackgroundColor(Color.parseColor("#fd7108"));
                break;
            case "其他":
                mHolder.tvColor.setText("其他");
                mHolder.ivColor.setBackgroundColor(Color.parseColor("#f7f7f7"));
                break;
            default:
                break;
        }
        if (position == list.size() - 1) {
            mHolder.tvDivide.setVisibility(View.GONE);
        }

        mHolder.rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public void setOnItemClickListener(OnAdapterClickListener listener) {
        this.listener = listener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_color)
        ImageView ivColor;
        @BindView(R.id.tv_color)
        TextView tvColor;
        @BindView(R.id.rl)
        RelativeLayout rl;
        @BindView(R.id.tv_divide)
        TextView tvDivide;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
