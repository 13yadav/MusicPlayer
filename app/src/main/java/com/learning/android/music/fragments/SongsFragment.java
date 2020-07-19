package com.learning.android.music.fragments;

import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.learning.android.music.MediaPlayerService;
import com.learning.android.music.R;
import com.learning.android.music.data.SongLab;
import com.learning.android.music.utils.StorageUtil;
import com.learning.android.music.data.Song;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SongsFragment extends Fragment {
    private MediaPlayerService playerService;
    private boolean serviceBound = false;
    private static final String SERVICE_STATE = "serviceState";
    private ArrayList<Song> songList;
    private RecyclerView audioRecyclerView;
    private AudioAdapter adapter;
    public static final String BROADCAST_PLAY_NEW_AUDIO = "com.learning.android.music.PLAY_NEW_AUDIO";


    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MediaPlayerService.LocalBinder binder = (MediaPlayerService.LocalBinder) service;
            playerService = binder.getService();
            serviceBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            serviceBound = false;
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SongLab songLab = new SongLab();
        songList = songLab.getSongs(getContext());

//        new GetMusicListAsyncTask(getActivity()).execute((Void) null);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_songs, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        audioRecyclerView = view.findViewById(R.id.songsRecyclerView);
        audioRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();
    }

    private void updateUI(){
        if (adapter == null) {
            adapter = new AudioAdapter(songList);
            audioRecyclerView.setAdapter(adapter);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle savedInstance) {
        super.onSaveInstanceState(savedInstance);
        savedInstance.putBoolean(SERVICE_STATE, serviceBound);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (serviceBound){
            getActivity().unbindService(serviceConnection);
            playerService.stopSelf();
        }
    }

    private class AudioHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Song mSong;
        private TextView songName;
        private TextView artistName;
        private ImageView imgListCover;

        public AudioHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.item_list_songs, parent, false));
            itemView.setOnClickListener(this);
            songName = itemView.findViewById(R.id.tvSongName);
            artistName = itemView.findViewById(R.id.tvArtistName);
            imgListCover = itemView.findViewById(R.id.imgListCover);
        }

        public void bind(Song song){
            mSong = song;
            songName.setText(mSong.getTitle());
            artistName.setText(mSong.getArtist());
            Glide.with(getView()).load(mSong.getAlbumArt()).into(imgListCover);
        }

        @Override
        public void onClick(View v) {
            playAudio(getAdapterPosition());
            mSong.getId();
        }
    }

    private class AudioAdapter extends RecyclerView.Adapter<AudioHolder> {
        private List<Song> songList;

        public AudioAdapter(List<Song> songList){
            this.songList = songList;
        }

        @NonNull
        @Override
        public AudioHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            return new AudioHolder(inflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull AudioHolder holder, int position) {
            Song song = songList.get(position);
            holder.bind(song);
        }

        @Override
        public int getItemCount() {
            return songList.size();
        }
    }


    private void playAudio(int audioIndex){
        if (!serviceBound){
            StorageUtil storage = new StorageUtil(getActivity().getApplicationContext());
            storage.storeAudio(songList);
            storage.storeAudioIndex(audioIndex);
            Intent playerIntent = new Intent(getActivity(), MediaPlayerService.class);
            getActivity().startService(playerIntent);
            getActivity().bindService(playerIntent, serviceConnection, Context.BIND_AUTO_CREATE);
        } else {
            StorageUtil storage = new StorageUtil(getActivity().getApplicationContext());
            storage.storeAudioIndex(audioIndex);

            Intent broadcastIntent = new Intent(BROADCAST_PLAY_NEW_AUDIO);
            getActivity().sendBroadcast(broadcastIntent);
        }
    }

    private class GetMusicListAsyncTask extends AsyncTask<Void, Void, Boolean>{
        private Context context;

        public GetMusicListAsyncTask(Context context){
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
//                loadAudio();
                return true;
            } catch (Exception e){
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
        }
    }
}
