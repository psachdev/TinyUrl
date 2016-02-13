package com.example.prasachd.tinyurl;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by prasachd on 2/5/16.
 */
public class RequestTinyUrlFragment extends Fragment {
    EditText editText;
    Button button;
    MainActivity activity;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity=(MainActivity)activity;
        System.out.println("RequestTinyUrlFragment onAttach");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        System.out.println("RequestTinyUrlFragment onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        System.out.println("RequestTinyUrlFragment onCreateView");
        View fragmentView = inflater.inflate(R.layout.user_input_screen,null);
        editText = (EditText)fragmentView.findViewById(R.id.enterUrl);

        button = (Button)fragmentView.findViewById(R.id.requestTinyUrl);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = editText.getText().toString();
                if(activity.isTinyUrlPresent(url)){
                    System.out.println("RequestTinyUrlFragment key found");
                    activity.addShowTinyUrlFragment(activity.getTinyUrl(url));
                }else {
                    System.out.println("RequestTinyUrlFragment requesting tinyUrl");
                    NetworkHandler networkHandler = new NetworkHandler(activity, url);
                    networkHandler.execute();
                }
            }
        });
        return fragmentView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        System.out.println("RequestTinyUrlFragment onActivityCreated");
    }

    @Override
    public void onStart() {
        super.onStart();
        System.out.println("RequestTinyUrlFragment onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        System.out.println("RequestTinyUrlFragment onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        System.out.println("RequestTinyUrlFragment onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        System.out.println("RequestTinyUrlFragment onStop");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println("RequestTinyUrlFragment onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if(this.activity!=null){
            this.activity=null;
        }
        System.out.println("RequestTinyUrlFragment onDetach");
    }
}
