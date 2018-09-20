package com.applicx.androidexercise.ui;

import android.annotation.TargetApi;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.applicx.androidexercise.Cons;
import com.applicx.androidexercise.R;
import com.applicx.androidexercise.model.Cast;
import com.applicx.androidexercise.model.CastResponse;
import com.applicx.androidexercise.model.Movie;
import com.applicx.androidexercise.vm.MovieViewModel;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.target.Target;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MovieDetailsActivity extends AppCompatActivity {

    @BindView(R.id.layout)
    RelativeLayout layoutBackground;
    @BindView(R.id.movie_title)
    TextView tvTitle;
    @BindView(R.id.movie_release_date)
    TextView tvReleaseDate;
    @BindView(R.id.movie_runtime)
    TextView tvRuntime;
    @BindView(R.id.movie_overview)
    TextView tvOverview;
    @BindView(R.id.cast_layout)
    LinearLayout castLayout;
    private Unbinder unbinder;
    private MovieViewModel movieViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        final LinearLayout cast = new LinearLayout(this);
        unbinder = ButterKnife.bind(this);
        movieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        movieViewModel.getMovieLiveData().observe(this, new Observer<Movie>() {
            @Override
            public void onChanged(@Nullable Movie movie) {
                assert movie != null;
                loadImageBackground(movie.getBackdrop());
                tvTitle.setText(movie.getTitle());
                tvReleaseDate.setText(movie.getRealasedate());
                tvRuntime.setText(movie.getRuntime());
                tvOverview.setText(movie.getOverview());
            }
        });
        movieViewModel.getCastLiveData().observe(this, new Observer<CastResponse>() {
            @Override
            public void onChanged(@Nullable CastResponse castResponse) {
                assert castResponse != null;
                List<Cast> castList = castResponse.getCasts();
                for (int i = 0; i < castList.size(); i++) {
                    onAddCast(
                            castList.get(i).getProfile(),
                            castList.get(i).getCharacter(),
                            castList.get(i).getName()
                    );
                }
            }
        });

    }

    private void loadImageBackground(String imageViewPath) {
        Glide.with(this).load(Cons.BASE_PATH + imageViewPath).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                Drawable drawable = new BitmapDrawable(getResources(), resource);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    layoutBackground.setBackground(drawable);
                }
            }
        });
    }

    public void onAddCast(final String _profile, final String _character, final String _name) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.cast, null);
        ConstraintLayout layout = rowView.findViewById(R.id.cast_item_layout);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CastProfileDialog.newInstance().show(getSupportFragmentManager(), "CastProfileDialog");
                CastProfileDialog.setCastDetails(_profile,_character,_name);
            }
        });
        ImageView profile = rowView.findViewById(R.id.iv_cast_profile);
        Glide.with(this).load(Cons.BASE_PATH + _profile).into(profile);
        // Add the new row before the add field button.
        castLayout.addView(rowView, castLayout.getChildCount() - 1);
    }

    @Override
    protected void onStart() {
        super.onStart();
        movieViewModel.loadMovieDetails(getIntent().getIntExtra(MainActivity.MOVIE_ID, 15383));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
