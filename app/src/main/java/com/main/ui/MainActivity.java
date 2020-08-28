package com.main.ui;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.main.R;
import com.main.home.HomeFragment;
import com.main.notification.NotificationFragment;
import com.main.profile.ProfileFragment;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

public class MainActivity extends BaseActivity implements BottomNavigationView.OnNavigationItemSelectedListener{


  private ConstraintLayout container;
  private FrameLayout fragmentContainer;
  private BottomNavigationView navigation;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    initView();
    navigation.setOnNavigationItemSelectedListener(this);

    Fragment fragment = new HomeFragment();
    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment)
    .commit();

  }


  private void initView() {
    container = findViewById(R.id.container);
    fragmentContainer = findViewById(R.id.fragment_container);
    navigation = findViewById(R.id.navigation);
  }


  @Override
  public boolean onNavigationItemSelected(@NonNull MenuItem item) {
    Fragment fragment = null;

    switch (item.getItemId()) {
      case R.id.item0:
        fragment = new HomeFragment();
        break;

      case R.id.item1:
        fragment = new NotificationFragment();
        break;

      case R.id.item3:
        fragment = new ProfileFragment();
        break;
    }

    return loadFragment(fragment);
  }

  private boolean loadFragment(Fragment fragment) {
    if (fragment != null) {
      getSupportFragmentManager()
          .beginTransaction()
          .replace(R.id.fragment_container, fragment)
          .commit();
      return true;
    }
    return false;
  }

}