package com.ness.aseatdemo;

import android.os.Bundle;
import android.text.Html;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.ness.aseatdemo.adapters.PagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle(Html.fromHtml("<font color='#000000'>Demo App</font>"));

        List<String> words = new ArrayList<>();
        words.add("Today");
        words.add("Tomorrow");

        PagerAdapter adapter = new PagerAdapter(this, words);
        ViewPager2 pager2 = findViewById(R.id.viewPager);
        pager2.setAdapter(adapter);

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        tabLayout.addOnTabSelectedListener(this);

        TabLayoutMediator mediator = new TabLayoutMediator(tabLayout, pager2,false, true, (tab, position) -> {
            tab.setText(words.get(position));
        });

        mediator.attach();

    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}