package com.learning.android.music.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.learning.android.music.activities.NowPlayingActivity;
import com.learning.android.music.R;


public class MiniPlayerFragment extends Fragment {
    private static final String TAG = "MiniPlayerFragment";

    private ConstraintLayout controllerLayout;
    private TextView tvSongName;
    private TextView tvArtistName;
    private ImageView imgAlbumArt;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mini_controller, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvSongName = view.findViewById(R.id.tvSongName);
        tvSongName.setSelected(true);

        tvArtistName = view.findViewById(R.id.tvArtistName);
        tvArtistName.setSelected(true);

        controllerLayout = view.findViewById(R.id.controllerLayout);
        controllerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), NowPlayingActivity.class);
                startActivity(i);
            }
        });
    }
}
