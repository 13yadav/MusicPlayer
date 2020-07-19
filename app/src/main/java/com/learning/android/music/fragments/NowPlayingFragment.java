package com.learning.android.music.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.learning.android.music.R;

public class NowPlayingFragment extends Fragment {

    private ImageView songCover;
    private TextView tvSongName;
    private TextView tvArtistName;
    private SeekBar playerSeekBar;
    private TextView currentPosition;
    private TextView songLength;
    private ImageButton btnPrev;
    private ImageButton btnPlayPause;
    private ImageButton btnNext;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_now_playing, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        songCover = view.findViewById(R.id.songCover);

        tvSongName = view.findViewById(R.id.tvSongName);
        tvSongName.setSelected(true);

        tvArtistName = view.findViewById(R.id.tvArtistName);
        tvArtistName.setSelected(true);

        playerSeekBar = view.findViewById(R.id.playerSeekBar);

        currentPosition = view.findViewById(R.id.currentPosition);
        songLength = view.findViewById(R.id.songLen);

        view.findViewById(R.id.btnPrev).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TO do
            }
        });

        btnPlayPause = view.findViewById(R.id.btnPlayPause);
        btnPlayPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TO DO
            }
        });

        view.findViewById(R.id.btnNext).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TO Do
            }
        });
    }
}
