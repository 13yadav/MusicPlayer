package com.learning.android.music;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.tabs.TabLayout;
import com.learning.android.music.adapters.SectionsPagerAdapter;
import com.learning.android.music.fragments.AlbumFragment;
import com.learning.android.music.fragments.MiniPlayerFragment;
import com.learning.android.music.fragments.SongsFragment;


public class MainActivity extends AppCompatActivity {

    private static final String[] PERMISSIONS = {
            Manifest.permission.READ_EXTERNAL_STORAGE
    };
    private static final int REQUEST_PERMISSIONS = 123;
    private static final int PERMISSIONS_COUNT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && arePermissionsDenied()){
            requestPermissions(PERMISSIONS, REQUEST_PERMISSIONS);
            return;
        }

        ViewPager viewPager =findViewById(R.id.viewPager);
        setUpViewPager(viewPager);

        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager, true);

        FragmentManager fm = getSupportFragmentManager();
        Fragment miniPlayerFragment =fm.findFragmentById(R.id.fragment_mini_player);

        if (miniPlayerFragment == null){
            miniPlayerFragment = new MiniPlayerFragment();
            fm.beginTransaction().add(R.id.fragment_mini_player, miniPlayerFragment).commit();
        }
    }

    private void setUpViewPager(ViewPager viewPager){
        SectionsPagerAdapter pagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        pagerAdapter.addFragment(new SongsFragment(), "Songs");
        pagerAdapter.addFragment(new AlbumFragment(), "Albums");
        viewPager.setAdapter(pagerAdapter);
    }

    private boolean arePermissionsDenied(){
        for (int i = 0; i < PERMISSIONS_COUNT; i++){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return checkSelfPermission(PERMISSIONS[i]) != PackageManager.PERMISSION_GRANTED;
            }
        }
        return false;
    }

    @SuppressLint("ServiceCast")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (arePermissionsDenied()){
            ((ActivityManager) (this.getSystemService(ACCOUNT_SERVICE))).clearApplicationUserData();
            recreate();
        } else {
            onResume();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
