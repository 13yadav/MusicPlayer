package com.learning.android.music.data;

import android.graphics.Bitmap;

import java.io.Serializable;

public class Song implements Serializable {
    private String _id;
    private String title;
    private String artist;
    private String data;
    private String album;
    private long album_id;
    private long duration;
    private Bitmap albumArt;

    public Song(String _id, String title, String artist, String data, String album, long album_id, long duration, Bitmap albumArt) {
        this._id = _id;
        this.title = title;
        this.artist = artist;
        this.data = data;
        this.album = album;
        this.album_id = album_id;
        this.duration = duration;
        this.albumArt = albumArt;
    }

    public String getId() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public long getAlbum_id() {
        return album_id;
    }

    public void setAlbum_id(long album_id) {
        this.album_id = album_id;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public Bitmap getAlbumArt() {
        return albumArt;
    }

    public void setAlbumArt(Bitmap albumArt) {
        this.albumArt = albumArt;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }
}
