package com.example.prasachd.tinyurl;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.Toast;

import java.util.HashMap;

public class MainActivity extends Activity {

    private RequestTinyUrlFragment requestTinyUrlFragment;
    private ShowTinyUrlFragment showTinyUrlFragment;

    private final String RequestTinyUrlFragmentTag = "RequestTinyUrlFragmentTag";
    private final String showTinyUrlFragmentTag= "ShowTinyUrlFragmentTag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        System.out.println("MainActivity onCreate");

        addRequestTinyUrlFragment();
    }

    public boolean isTinyUrlPresent(String originalUrl){
        return CacheHandler.getInstance().isTinyUrlPresent(originalUrl);
    }

    public String getTinyUrl(String key){
        return CacheHandler.getInstance().getTinyUrl(key);
    }

    public void addResultToCache(String key, String value){
        CacheHandler.getInstance().addResultToCache(key, value);
    }

    public void addShowTinyUrlFragment(String url){
        System.out.println("MainActivity addShowTinyUrlFragment");
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        /* Look for existing fragment */
        showTinyUrlFragment = (ShowTinyUrlFragment)fragmentManager.
                findFragmentByTag(this.showTinyUrlFragmentTag);

        /* Instantiate fragment to be shown if not found */
        if(showTinyUrlFragment==null) {
            showTinyUrlFragment = new ShowTinyUrlFragment();
        }

        /* Remove other fragments if attached */
        if(requestTinyUrlFragment!=null && requestTinyUrlFragment.isAdded()){
            System.out.println("MainActivity detach requestTinyUrlFragment");
            fragmentTransaction.detach(requestTinyUrlFragment);
        }


        if(showTinyUrlFragment.isDetached()){
            Bundle bundle = showTinyUrlFragment.getArguments();
            bundle.putString("url", url);
            System.out.println("MainActivity attach showTinyUrlFragment");
            fragmentTransaction.attach(showTinyUrlFragment);
        }else if(!showTinyUrlFragment.isAdded()) {
            //Setting input data
            Bundle bundle = new Bundle();
            bundle.putString("url", url);
            /**
             * Supply the construction arguments for this fragment.  This can only
             * be called before the fragment has been attached to its activity; that
             * is, you should call it immediately after constructing the fragment.  The
             * arguments supplied here will be retained across fragment destroy and
             * creation.
             */
            showTinyUrlFragment.setArguments(bundle);
            System.out.println("MainActivity add showTinyUrlFragment");
            fragmentTransaction.add(R.id.fragmentHolder, showTinyUrlFragment, this.showTinyUrlFragmentTag);
        }
        //fragmentTransaction.replace(R.id.fragmentHolder, showTinyUrlFragment);
        fragmentTransaction.commit();
        fragmentManager.executePendingTransactions();

    }

    public void addRequestTinyUrlFragment(){
        System.out.println("MainActivity addRequestTinyUrlFragment");
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        /* Look for existing fragment */
        requestTinyUrlFragment = (RequestTinyUrlFragment)fragmentManager.
        findFragmentByTag(this.RequestTinyUrlFragmentTag);

        /* Instantiate fragment to be shown if not found */
        if(requestTinyUrlFragment==null) {
            requestTinyUrlFragment = new RequestTinyUrlFragment();
        }

        /* Remove other fragments if attached */
        if(showTinyUrlFragment!=null && showTinyUrlFragment.isAdded()){
            System.out.println("MainActivity detach showTinyUrlFragment");
            fragmentTransaction.detach(showTinyUrlFragment);
        }

        if(requestTinyUrlFragment.isDetached()){
            System.out.println("MainActivity attach requestTinyUrlFragment");
            fragmentTransaction.attach(requestTinyUrlFragment);
        }else if(!requestTinyUrlFragment.isAdded()) {
            System.out.println("MainActivity add requestTinyUrlFragment");
            fragmentTransaction.add(R.id.fragmentHolder, requestTinyUrlFragment, this.RequestTinyUrlFragmentTag);
        }
        //fragmentTransaction.replace(R.id.fragmentHolder, requestTinyUrlFragment);
        fragmentTransaction.commit();
        fragmentManager.executePendingTransactions();
    }

    @Override
    public void onBackPressed() {
        if(showTinyUrlFragment!=null && showTinyUrlFragment.isAdded()){
            addRequestTinyUrlFragment();
        }else {
            super.onBackPressed();
            CacheHandler.getInstance().closeDb();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        System.out.println("MainActivity onConfigurationChanged");
        super.onConfigurationChanged(newConfig);

        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();
        }
    }
}
