package com.example.prasachd.tinyurl;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by prasachd on 2/5/16.
 */
public class ShowTinyUrlFragment extends Fragment {


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("ShowTinyUrlFragment onCreate");

        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        System.out.println("ShowTinyUrlFragment onCreateView");

        View fragmentView = inflater.inflate(R.layout.result_window, null);
        String tinyUrl = getArguments().getString("url");
        TextView textView = (TextView)fragmentView.findViewById(R.id.tinyUrl);
        textView.setText(tinyUrl);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tinyUrl = ((TextView)v).getText().toString();
                Uri uri = Uri.parse(tinyUrl);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        return fragmentView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        System.out.println("ShowTinyUrlFragment onActivityCreated");

    }

    @Override
    public void onStart() {
        super.onStart();
        System.out.println("ShowTinyUrlFragment onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        System.out.println("ShowTinyUrlFragment onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        System.out.println("ShowTinyUrlFragment onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        System.out.println("ShowTinyUrlFragment onStop");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println("ShowTinyUrlFragment onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        System.out.println("ShowTinyUrlFragment onDetach");
    }
}
