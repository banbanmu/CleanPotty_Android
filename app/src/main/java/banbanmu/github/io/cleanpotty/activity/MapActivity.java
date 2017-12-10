package banbanmu.github.io.cleanpotty.activity;

import android.content.SharedPreferences;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import banbanmu.github.io.cleanpotty.R;
import banbanmu.github.io.cleanpotty.resource.Potty;
import banbanmu.github.io.cleanpotty.retrofit.helper.PottyHelper;
import banbanmu.github.io.cleanpotty.util.ToolbarUtil;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MapActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private PottyHelper helper;
    private ToolbarUtil toolbarUtil = new ToolbarUtil();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        ButterKnife.bind(this);

        helper = new PottyHelper();

        toolbarUtil.setToolbar(toolbar, getApplicationContext(), "내 영역표시", 0);
        setMap();
    }

    private void setMap() {
        SupportMapFragment mapFragment = new SupportMapFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.box_map, mapFragment);
        transaction.commitAllowingStateLoss();

        SharedPreferences pref = getSharedPreferences("mySharedPreferences", 0);
        helper.list(pref.getString("Token", ""))
                .subscribe(potties -> {
                    mapFragment.getMapAsync(map -> {
                        for (Potty potty : potties) {
                            Marker marker = map.addMarker(new MarkerOptions()
                                    .position(new LatLng(potty.getLat(), potty.getLng()))
                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_map_pin)));
                        }

                        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(37.56117, 126.938808), 10));
                        map.getUiSettings().setScrollGesturesEnabled(true);
                    });
                }, t -> Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT));


    }
}
