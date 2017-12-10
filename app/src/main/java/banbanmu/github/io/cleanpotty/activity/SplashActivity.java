package banbanmu.github.io.cleanpotty.activity;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import java.util.concurrent.TimeUnit;

import banbanmu.github.io.cleanpotty.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SplashActivity extends AppCompatActivity {

    @BindView(R.id.progressbar)
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ButterKnife.bind(this);

        //setProgressBar();
        moveToMain();
    }

    private void setProgressBar() {
        progressBar.setIndeterminate(true);
        progressBar.getIndeterminateDrawable().setColorFilter(
                ContextCompat.getColor(getApplicationContext(), R.color.white), PorterDuff.Mode.MULTIPLY);

        progressBar.setVisibility(View.VISIBLE);
    }

    private void moveToMain() {
        Observable.just(true)
                .delay(2000, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(m -> {
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                });
    }
}
