package com.learning.android.music.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;

import com.learning.android.music.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class SongLab {

    public ArrayList<Song> getSongs(Context context){
        ArrayList<Song> songList = new ArrayList<>();

        ContentResolver contentResolver = context.getContentResolver();
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String selection = MediaStore.Audio.Media.IS_MUSIC + "!= 0";
        String sortOrder = MediaStore.Audio.Media.TITLE + " ASC";

        Cursor cursor = contentResolver.query(uri, null, selection,null, sortOrder);

        if (cursor != null && cursor.getCount() > 0){

            while(cursor.moveToNext()){
                String _id = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media._ID));
                String title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
                String artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
                String data = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
                String album = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));
                long album_id = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));
                long duration = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));

                Uri sArtworkUri = Uri.parse("content://media/external/audio/albumart");
                Uri albumArtUri = ContentUris.withAppendedId(sArtworkUri, album_id);

                Bitmap bitmap = null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(contentResolver, albumArtUri);
                    bitmap = Bitmap.createBitmap(bitmap);
                } catch (FileNotFoundException e){
                    e.printStackTrace();
                    bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.cover);
                } catch (IOException io){
                    io.printStackTrace();
                }

                songList.add(new Song(_id, title, artist, data, album, album_id, duration, bitmap));
            }
        }
        cursor.close();
        return songList;
    }

    public Song getSong(Context context, String id){
        ContentResolver contentResolver = context.getContentResolver();
        Uri mediaContentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String selection = MediaStore.Audio.Media._ID + "=?";
        String[] selectionArgs = new String[] {"" + id}; //This is the id you are looking for

        Cursor cursor = contentResolver.query(mediaContentUri, null, selection, selectionArgs, null);

        if(cursor.getCount() >= 0) {
            cursor.moveToPosition(0);
            String _id = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media._ID));
            String title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
            String artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
            String data = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
            String album = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));
            long album_id = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));
            long duration = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));

            Uri sArtworkUri = Uri.parse("content://media/external/audio/albumart");
            Uri albumArtUri = ContentUris.withAppendedId(sArtworkUri, album_id);

            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(contentResolver, albumArtUri);
                bitmap = Bitmap.createBitmap(bitmap);
            } catch (FileNotFoundException e){
                e.printStackTrace();
                bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.cover);
            } catch (IOException io){
                io.printStackTrace();
            }

            return new Song(_id, title, artist, data, album, album_id, duration, bitmap);
        }
        return null;
    }

//    public File getPhotoFile(Crime crime) {
//        File filesDir = context.getFilesDir();
//        return new File(filesDir, crime.getPhotoFilename());
//    }
//
//    public void updateCrime(Crime crime){
//        String uuidString = crime.getID().toString();
//        ContentValues values = getContentValues(crime);
//        database.update(CrimeTable.NAME, values, CrimeTable.Cols.UUID + "= ?", new String[]{ uuidString });
//    }
//
//    public void deleteCrime(Crime crime){
//        String uuidString = crime.getID().toString();
//        database.delete(CrimeTable.NAME,CrimeTable.Cols.UUID + "= ?", new String[]{ uuidString });
//    }
}
