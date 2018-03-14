package com.example.fready.moneychoo;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebViewFragment;
import android.widget.Button;
import android.widget.Toast;

public class EventFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ViewGroup rootView ;
    WebView webView ;

    private OnFragmentInteractionListener mListener;
    FragmentManager manager;

    public EventFragment() {

    }

    public static EventFragment newInstance(String param1, String param2) {
        EventFragment fragment = new EventFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = (ViewGroup) inflater.inflate(R.layout.fragment_event, container, false);
        webView = rootView.findViewById(R.id.webView);

        WebSettings settings= webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDisplayZoomControls(true);
        settings.setAllowContentAccess(true);
        settings.setAllowContentAccess(true);
        webView.setWebViewClient(new WebViewClient()); //이걸안해주면 새창이 뜸
        webView.setWebChromeClient(new WebChromeClient());
        Button mallButton = rootView.findViewById(R.id.btnMalltail);
        Button nygirlzButton = rootView.findViewById(R.id.btnNygirlz);
        Button iporterButton = rootView.findViewById(R.id.btnIporter);
        Button yogirlooButton = rootView.findViewById(R.id.btnYogirloo);
        mallButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* FramgentMallTailActivity mallActivity =new FramgentMallTailActivity();
                FragmentTransaction ft = manager.beginTransaction();
                ft.addToBackStack(null);
               //ft.replace(R.id.webViewFragment, mallActivity);
                ft.commit();
                */
                webView.loadUrl("http://post.malltail.com/eventservices/open_events");
            }
        });
        nygirlzButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                webView.loadUrl("http://www.nygirlz.co.kr/mobile/event/dailyattend1803.php");
            }
        });
        iporterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                webView.loadUrl("https://m.iporter.com/ko/event");

            }
        });
        yogirlooButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                webView.loadUrl("http://www.yogirloo.com/m/board/event/list.asp");
            }
        });
        return rootView;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @SuppressLint("ValidFragment")
    class FramgentMallTailActivity extends WebViewFragment{
        @Override
        public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            WebViewFragment f= new WebViewFragment();
            WebView webView= f.getWebView();
            WebSettings settings = webView.getSettings();
            settings.setJavaScriptEnabled(true);
            webView.setWebViewClient(new WebViewClient());
            webView.loadUrl("http://post.malltail.com/eventservices/open_events");
        }
    }
}