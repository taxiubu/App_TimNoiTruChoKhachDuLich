package com.example.myapplication.View.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;

import com.example.myapplication.MyWebViewClient;
import com.example.myapplication.R;

public class FragmentWebView extends Fragment {
    WebView webView;
    ImageView btBack;
    public static FragmentWebView newInstance(String url) {
        FragmentWebView fragment = new FragmentWebView();
        Bundle args = new Bundle();
        args.putSerializable("url", url);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_web_view, container, false);
        btBack =view.findViewById(R.id.btBack);
        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        webView =view.findViewById(R.id.webView);
        webView.setWebViewClient(new MyWebViewClient());
        go();
        return view;
    }
    private void go()  {
        String url = (String) getArguments().getSerializable("url");
        if(!url.isEmpty())  {
            webView.getSettings().setLoadsImagesAutomatically(true);
            webView.getSettings().setJavaScriptEnabled(true);
            webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
            webView.loadUrl(url);
        }
    }
}