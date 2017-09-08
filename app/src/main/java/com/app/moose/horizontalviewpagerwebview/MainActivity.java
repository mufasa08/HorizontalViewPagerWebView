package com.app.moose.horizontalviewpagerwebview;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;

import com.smooz_app.moose.horizontalviewpagerwebview.R;

public class MainActivity extends AppCompatActivity implements  ParentRequestInterface {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private CustomViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(),mViewPager, this);

        // Set up the ViewPager with the sections adapter.
        mViewPager = (CustomViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setOffscreenPageLimit(2);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setViewPagerStatus(Boolean b) {
        mViewPager.setPagingEnabled(b);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";
        int sectionNumber;
        MainActivity activity;
        CustomViewPager viewpager;
        MainActivity parentActivity;

        public void setActivity(MainActivity activity) {
            this.activity = activity;
        }

        public void setPager(CustomViewPager viewpager) {
            this.viewpager = viewpager;
        }

        public PlaceholderFragment() {

        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            Bundle arguments = getArguments();
            sectionNumber = arguments.getInt(ARG_SECTION_NUMBER);

            parentActivity = (MainActivity) getActivity();
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            CustomWebView webView=rootView.findViewById(R.id.webView);
            webView.setFragment(this);
            //load test url
            String url="";
            switch (sectionNumber) {
                case 1:
                    url="https://www.google.com/search?q=news";
                    break;
                case 2:
                    url="https://www.google.com/search?q=random";
                    break;
                case 3:
                    url="https://www.amazon.com/Hoover-UH20040-QuickVac-Bagless-Upright/dp/B004N64GH0/ref=br_dig_pdt-1?pf_rd_m=ATVPDKIKX0DER&pf_rd_s=&pf_rd_r=0MZDRMZPKF0R2NT18GQ1&pf_rd_t=36701&pf_rd_p=abc0a418-efdb-4eb8-923e-3437111895cf&pf_rd_i=desktop";
                    break;
            }

            WebSettings settings=webView.getSettings();
            webView.setWebChromeClient(new WebChromeClient());
            webView.setWebViewClient(new WebViewClient());
            settings.setJavaScriptEnabled(true);
            webView.setScrollContainer(false);
            webView.setVerticalScrollBarEnabled(false);
            webView.setHorizontalScrollBarEnabled(false);
            settings.setBuiltInZoomControls(true);
            settings.setSupportZoom(true);
            settings.setDisplayZoomControls(false);
            settings.setLoadWithOverviewMode(true);
            settings.setUseWideViewPort(true);
            webView.loadUrl(url);
            return rootView;
        }




        public void setViewPager(boolean b) {
            parentActivity.setViewPagerStatus(b);
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        CustomViewPager viewPager;
        MainActivity activity;
        public SectionsPagerAdapter(FragmentManager fm, CustomViewPager viewPager, MainActivity activity) {
            super(fm);
            this.viewPager=viewPager;
            this.activity=activity;

        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            PlaceholderFragment fragment=PlaceholderFragment.newInstance(position + 1);
            fragment.setActivity(activity);
            fragment.setPager(viewPager);
            return fragment;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "SECTION 1";
                case 1:
                    return "SECTION 2";
                case 2:
                    return "SECTION 3";
            }
            return null;
        }
    }
}
