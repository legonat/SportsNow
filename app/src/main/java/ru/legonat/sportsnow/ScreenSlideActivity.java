package ru.legonat.sportsnow;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

public class ScreenSlideActivity extends ActionBarActivity {
    /**
     * The number of pages (wizard steps) to show in this demo.
     */
    private static final int NUM_PAGES = 2;

    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private ViewPager mPager;
    private Handler mHandler;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
            */
            private PagerAdapter mPagerAdapter;

            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_screen_slide);

                mPager = (ViewPager) findViewById(R.id.pager);
                mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
                mPager.setAdapter(mPagerAdapter);
                Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
                toolbar.setTitle("SportsNow");
                // Set an OnMenuItemClickListener to handle menu item clicks
                toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        // Handle the menu item
                        int id = item.getItemId();
                        if (id == R.id.next) {  if (mPager.getCurrentItem() == 1) {

                   int chosenClub = Constants.chosenClub;
                   Intent answerIntent=new Intent();
                   answerIntent.putExtra("CLUB", chosenClub);// pass Extra to MainActivity
                   setResult(RESULT_OK, answerIntent);// show, that Extras are put
                   finish();// closing activity
               }
                else mPager.setCurrentItem(mPager.getCurrentItem() + 1);
                return true;
                        }
                        return true;
                    }
                });
                toolbar.inflateMenu(R.menu.menu_selection);
            }

    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            moveTaskToBack(true); // !выход из приложения на домашний экран
        } else {
            // Otherwise, select the previous step.
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }


    /**
     * A simple pager adapter that represents 2 ScreenSlidePageFragment objects, in
     * sequence.
     */
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }


        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new Greeting();
                case 1:
                    return new FragmentClubsList();

                default:
                    break;
            }
            return null;
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }
}

