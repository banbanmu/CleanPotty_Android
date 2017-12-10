package banbanmu.github.io.cleanpotty.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;


import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import org.eazegraph.lib.charts.BarChart;
import org.eazegraph.lib.models.BarModel;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import banbanmu.github.io.cleanpotty.R;
import banbanmu.github.io.cleanpotty.gson.GsonUtil;
import banbanmu.github.io.cleanpotty.resource.Data;
import banbanmu.github.io.cleanpotty.resource.Poop;
import banbanmu.github.io.cleanpotty.retrofit.ResponseCode;
import banbanmu.github.io.cleanpotty.retrofit.helper.PoopHelper;
import banbanmu.github.io.cleanpotty.util.ToolbarUtil;
import butterknife.BindView;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.refresh)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.week_graph)
    BarChart weekGraph;
    @BindView(R.id.month_graph)
    BarChart monthGraph;
    @BindView(R.id.txt_total)
    TextView txtTotal;
    @BindView(R.id.txt_recent)
    TextView txtRecent;

    @BindView(R.id.week1)
    TextView week1;
    @BindView(R.id.week2)
    TextView week2;
    @BindView(R.id.week3)
    TextView week3;
    @BindView(R.id.week4)
    TextView week4;
    @BindView(R.id.week5)
    TextView week5;
    @BindView(R.id.week6)
    TextView week6;
    @BindView(R.id.week7)
    TextView week7;

    @BindView(R.id.month1)
    TextView month1;
    @BindView(R.id.month2)
    TextView month2;
    @BindView(R.id.month3)
    TextView month3;
    @BindView(R.id.month4)
    TextView month4;
    @BindView(R.id.month5)
    TextView month5;

    private ToolbarUtil toolbarUtil = new ToolbarUtil();
    private PoopHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        helper = new PoopHelper();
        setGraph();

        toolbarUtil.setToolbar(toolbar, getApplicationContext(), "내가 싸지른 똥", R.menu.menu_toolbar);
        toolbar.setOnMenuItemClickListener(item -> {
            startActivity(new Intent(getApplicationContext(), MapActivity.class));
            return true;
        });

        refreshLayout.setOnRefreshListener(() -> {
            setGraph();
        });
    }

    private void setGraph() {
        SharedPreferences pref = getSharedPreferences("mySharedPreferences", 0);
        helper.getApi().data(pref.getString("Token", ""))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    if(response.getCode().equals(ResponseCode.CODE_SUCCESS)) {
                        JsonElement json = response.getData();
                        JsonElement weekJson = json.getAsJsonObject().get("week");
                        JsonElement monthJson = json.getAsJsonObject().get("month");
                        JsonElement recentJson = json.getAsJsonObject().get("recent");
                        Type collectionType = new TypeToken<List<Data>>() {
                        }.getType();

                        List<Data> perWeek = GsonUtil.gson.fromJson(weekJson, collectionType);
                        List<Data> perMonth = GsonUtil.gson.fromJson(monthJson, collectionType);
                        int total = json.getAsJsonObject().get("total").getAsInt();
                        Poop recent = GsonUtil.gson.fromJson(recentJson, Poop.class);

                        showRecent(recent);
                        showData(perWeek, perMonth);
                        setTotal(total);
                    }
                    if(refreshLayout.isRefreshing()) refreshLayout.setRefreshing(false);
                }, t -> Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT));
    }

    private void showRecent(Poop recent) {
        Geocoder gcd = new Geocoder(getApplicationContext(), Locale.getDefault());
        List<Address> addresses;
        try {
            addresses = gcd.getFromLocation(recent.getPotty().getLat(), recent.getPotty().getLng(), 1);
        } catch (IOException e) {
            addresses = new ArrayList<>();
        }
        if(addresses.size() > 0) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy년 MM월 dd일 HH시 mm분");
            txtRecent.setText(String.format("당신은 %2$s에 %1$s에서 %3$d분 동안 마지막 똥을 쌌습니다.",
                    addresses.get(0).getLocality(),
                    getTimeAgo(recent.getTime()),
                    recent.getTimeSpent() / 60000));
        }
        else {
            txtRecent.setText("뭔가 잘못됐다..");
        }
    }

    private static String getTimeAgo(Date date) {
        long time = date.getTime();

        Date curDate = new Date();
        long now = curDate.getTime();
        if (time > now || time <= 0) {
            return null;
        }

        int dim = getTimeDistanceInMinutes(time);

        String timeAgo = null;

        if (dim == 0) {
            timeAgo = "방금 전";
        } else if (dim >= 1 && dim <= 44) {
            timeAgo = dim + "분 전";
        } else if (dim >= 45 && dim <= 1439) {
            timeAgo = (Math.round(dim / 60)) + "시간 전";
        } else if (dim >= 1440 && dim <= 43199) {
            timeAgo = (Math.round(dim / 1440)) + "일 전";
        } else if (dim >= 43200 && dim <= 525599) {
            timeAgo = (Math.round(dim / 43200)) + "달 전";
        } else {
            timeAgo = (Math.round(dim / 525600)) + "년 전";
        }

        return timeAgo;
    }

    private static int getTimeDistanceInMinutes(long time) {
        long timeDistance = new Date().getTime() - time;
        return Math.round((Math.abs(timeDistance) / 1000) / 60);
    }

    private void showData(List<Data> perWeek, List<Data> perMonth) {
        weekGraph.clearChart();
        monthGraph.clearChart();

        for(Data data : perWeek) weekGraph.addBar(new BarModel("", data.getFloatValue(), R.color.primary_color));
        for(Data data : perMonth) monthGraph.addBar(new BarModel("", data.getFloatValue(), R.color.primary_color));

        weekGraph.startAnimation();
        monthGraph.startAnimation();

        setDate(perWeek, perMonth);
    }

    private void setDate(List<Data> perWeek, List<Data> perMonth) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd", Locale.getDefault());

        week1.setText(simpleDateFormat.format(perWeek.get(0).getDate()));
        week2.setText(simpleDateFormat.format(perWeek.get(1).getDate()));
        week3.setText(simpleDateFormat.format(perWeek.get(2).getDate()));
        week4.setText(simpleDateFormat.format(perWeek.get(3).getDate()));
        week5.setText(simpleDateFormat.format(perWeek.get(4).getDate()));
        week6.setText(simpleDateFormat.format(perWeek.get(5).getDate()));
        week7.setText(simpleDateFormat.format(perWeek.get(6).getDate()));

        month1.setText(simpleDateFormat.format(perMonth.get(0).getDate()));
        month2.setText(simpleDateFormat.format(perMonth.get(1).getDate()));
        month3.setText(simpleDateFormat.format(perMonth.get(2).getDate()));
        month4.setText(simpleDateFormat.format(perMonth.get(3).getDate()));
        month5.setText(simpleDateFormat.format(perMonth.get(4).getDate()));
    }

    private void setTotal(int total) {
        txtTotal.setText(String.format("당신은 지난 한달 동안 인생의 %,d분을 똥 싸는데 썼습니다.", (int)(total / 60000)));
    }
}
