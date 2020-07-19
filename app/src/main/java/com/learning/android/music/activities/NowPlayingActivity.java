package com.learning.android.music.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.learning.android.music.R;
import com.learning.android.music.data.Song;
import com.learning.android.music.fragments.NowPlayingFragment;

import java.util.ArrayList;

public class NowPlayingActivity extends AppCompatActivity {
    private static final String EXTRA_SONG_ID = "com.learning.android.music.song_id";
    private ArrayList<Song> songList;

    private static Intent newIntent(Context packageContext, String id){
        Intent intent = new Intent(packageContext, NowPlayingActivity.class);
        intent.putExtra(EXTRA_SONG_ID, id);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_container);

        FragmentManager fm = getSupportFragmentManager();
        Fragment controllerFragment =fm.findFragmentById(R.id.fragment_container);

        if (controllerFragment == null){
            controllerFragment = new NowPlayingFragment();
            fm.beginTransaction().add(R.id.fragment_container, controllerFragment).commit();
        }
    }
}
