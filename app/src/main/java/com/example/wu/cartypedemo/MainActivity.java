package com.example.wu.cartypedemo;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tv_tip)
    TextView tvTip;
    @BindView(R.id.recyclerView_brand)
    RecyclerView recyclerViewBrand;
    @BindView(R.id.recyclerView_type)
    RecyclerView recyclerViewType;
    @BindView(R.id.rl_type)
    RelativeLayout rlType;
    @BindView(R.id.recyclerView_color)
    RecyclerView recyclerViewColor;

    private int screenWidth;
    private CarColorAdapter carColorAdapter;
    private CarBrandAdapter carBrandAdapter;
    private CarTypeAdapter carTypeAdapter;
    private List<Car> brandList;
    private List<Car> typeList;
    private List<String> colorList;

    private Car carResult;          //选择车型的结果

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
        initColorRecyclerView();
        initBrandRecyclerView();
        initTypeRecyclerView();
        setListener();
    }

    private void initView() {
        //获取屏幕的宽度，设置偏移量
        screenWidth = DeviceScreen.getScreenWidth(this);
        rlType.setTranslationX(screenWidth);
        recyclerViewColor.setTranslationX(screenWidth);
    }

    private void setListener() {
        carBrandAdapter.setOnItemClickListener(new OnAdapterClickListener() {
            @Override
            public void onItemClick(int position) {
                float startX = recyclerViewColor.getTranslationX();
                setAnimation(rlType, screenWidth, screenWidth / 3);
                setAnimation(recyclerViewColor, startX, screenWidth);
                String id = brandList.get(position).getId();
                typeList.clear();
                carTypeAdapter.notifyDataSetChanged();
                getCarTypeByCarId(id);
                carResult = new Car();
                carResult.setName(brandList.get(position).getName());

            }
        });
        carTypeAdapter.setOnItemClickListener(new OnAdapterClickListener() {
            @Override
            public void onItemClick(int position) {
                setAnimation(recyclerViewColor, screenWidth, screenWidth / 3 * 2);
                carResult.setType(typeList.get(position).getName());
            }
        });
        carColorAdapter.setOnItemClickListener(new OnAdapterClickListener() {
            @Override
            public void onItemClick(int position) {
                float startX1 = rlType.getTranslationX();
                float startX2 = recyclerViewColor.getTranslationX();
                setAnimation(rlType, startX1, screenWidth);
                setAnimation(recyclerViewColor, startX2, screenWidth);
                carResult.setColor(colorList.get(position));
                StringBuffer sb = new StringBuffer();
                sb.append(carResult.getName())
                        .append(" · " + carResult.getType())
                        .append(" · " + carResult.getColor());
            }
        });
    }

    private void initBrandRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewBrand.setLayoutManager(linearLayoutManager);
        brandList = new ArrayList<>();
        carBrandAdapter = new CarBrandAdapter(brandList, this);
        recyclerViewBrand.setAdapter(carBrandAdapter);
        recyclerViewBrand.setItemAnimator(new DefaultItemAnimator());
        getCarBrandData();
    }

    private void initTypeRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewType.setLayoutManager(linearLayoutManager);
        typeList = new ArrayList<>();
        carTypeAdapter = new CarTypeAdapter(typeList, this);
        recyclerViewType.setAdapter(carTypeAdapter);
        recyclerViewType.setItemAnimator(new DefaultItemAnimator());
    }

    private void initColorRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerViewColor.setLayoutManager(linearLayoutManager);

        colorList = new ArrayList<>();
        colorList.add("白色");
        colorList.add("红色");
        colorList.add("黑色");
        colorList.add("银色");
        colorList.add("香槟色");
        colorList.add("灰色");
        colorList.add("蓝色");
        colorList.add("黄色");
        colorList.add("绿色");
        colorList.add("咖啡色");
        colorList.add("橙色");
        colorList.add("其他");
        carColorAdapter = new CarColorAdapter(colorList, this);
        recyclerViewColor.setAdapter(carColorAdapter);
    }


    private void setAnimation(View view, float startOffSet, float offSet) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, "translationX", startOffSet, offSet);
        objectAnimator.setDuration(500);
        objectAnimator.start();
    }

    /**
     * 获取车商标
     */
    private void getCarBrandData() {
        RetrofitService retrofitService = RetrofitServiceUtil.getRetrofitServiceWithHeader();
        retrofitService.getCarBrandList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Result<Car>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("MainActivity", "e = " + e.getMessage());
                    }

                    @Override
                    public void onNext(Result<Car> result) {
                        Log.d("MainActivity", "result = " + result);
                        if (TextUtils.equals("0", result.getStatus())) {
                            List<Car> data = result.getResult();
                            brandList.clear();
                            dataSort(data);
                            brandList.addAll(data);
                            carBrandAdapter.notifyDataSetChanged();
                        }
                    }
                });
    }

    /**
     * 获取车标获取车型
     */
    private void getCarTypeByCarId(String carId) {
        RetrofitService retrofitService = RetrofitServiceUtil.getRetrofitServiceWithHeader();
        retrofitService.getCarTypeById(carId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Result<Car>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(Result<Car> result) {
                        if (TextUtils.equals("0", result.getStatus())) {
                            List<Car> data = result.getResult();
                            for (Car car : data) {
                                List<Car> cars = car.getCarlist();
                                typeList.addAll(cars);
                            }
                            carTypeAdapter.notifyDataSetChanged();
                        }
                    }
                });
    }

    /**
     * 排序
     *
     * @param data data
     */
    private void dataSort(List<Car> data) {
        Comparator<? super Car> comparator = new Comparator<Car>() {
            @Override
            public int compare(Car var1, Car var2) {
                return var1.getInitial().toLowerCase().compareTo(var2.getInitial().toLowerCase());
            }
        };
        Collections.sort(data, comparator);
    }

    public class Result<T> implements Serializable {
        String status;
        String msg;
        List<T> result;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public List<T> getResult() {
            return result;
        }

        public void setResult(List<T> result) {
            this.result = result;
        }

        @Override
        public String toString() {
            return "Result{" +
                    "status='" + status + '\'' +
                    ", msg='" + msg + '\'' +
                    ", result=" + result +
                    '}';
        }
    }

}
