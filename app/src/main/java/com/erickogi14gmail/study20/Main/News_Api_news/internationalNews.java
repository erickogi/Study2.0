package com.erickogi14gmail.study20.Main.News_Api_news;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.erickogi14gmail.study20.Main.Configs.api;
import com.erickogi14gmail.study20.Main.News_Api_news.utils.ArticlesJsonParser;
import com.erickogi14gmail.study20.Main.News_Api_news.utils.ArticlesModel;
import com.erickogi14gmail.study20.Main.News_Api_news.utils.ArticlesModelAdapter;
import com.erickogi14gmail.study20.Main.News_Api_news.utils.ReadArticle;
import com.erickogi14gmail.study20.Main.News_Api_news.utils.SorcesModelAdapter;
import com.erickogi14gmail.study20.Main.News_Api_news.utils.SourcesJsonParser;
import com.erickogi14gmail.study20.Main.News_Api_news.utils.SourcesModel;
import com.erickogi14gmail.study20.Main.utills.RecyclerTouchListener;
import com.erickogi14gmail.study20.Main.utills.StaggeredHiddingScrollListener;
import com.erickogi14gmail.study20.Main.volley.IResult;
import com.erickogi14gmail.study20.Main.volley.VolleyService;
import com.erickogi14gmail.study20.R;
import com.muddzdev.styleabletoastlibrary.StyleableToast;

import java.util.ArrayList;

/**
 * Created by kimani kogi on 5/17/2017.
 */

public class internationalNews extends android.support.v4.app.Fragment {

    static Context context;
    static RecyclerView.LayoutManager mLayoutManager;
    static View view;
    static View viewSource;
    static ArrayList<ArticlesModel> articles;
    private static boolean isListView;
    SwipeRefreshLayout swipe_refresh_layout;
    RecyclerView recyclerView_vertical;
    RecyclerView recyclerView_horizontal;

    ArrayList<SourcesModel> sources;
    IResult mResultCallback = null;
    VolleyService mVolleyService;
    private StaggeredGridLayoutManager mStaggeredLayoutManager;

    public void start() {
        refresh();

        getRecyclerView_sources();
    }



    private void refresh() {
        swipe_refresh_layout = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainer);
        swipe_refresh_layout.setProgressBackgroundColorSchemeResource(R.color.colorAccent);
        swipe_refresh_layout.setBackgroundResource(android.R.color.white);
        swipe_refresh_layout.setColorSchemeResources(android.R.color.white, android.R.color.holo_purple, android.R.color.white);

        swipe_refresh_layout.setRefreshing(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        /**
         * Inflate the layout for this fragment
         */


        view = inflater.inflate(R.layout.fragment_all_news, container, false);
        initVolleyCallback();
        mStaggeredLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL);

        recyclerView_horizontal = (RecyclerView) view.findViewById(R.id.all_news_horizontal_recyclerView);
        recyclerView_vertical = (RecyclerView) view.findViewById(R.id.all_news_vertical_recyclerView);
        refresh();

        swipe_refresh_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipe_refresh_layout.setRefreshing(true);
                getRecyclerView_sources();

            }
        });

        // fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        recyclerView_horizontal.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView_horizontal, new RecyclerTouchListener.ClickListener() {

            @Override
            public void onClick(View view, int position) {
                if (viewSource != null) {
                    viewSource.setBackgroundColor(Color.WHITE);
                }
                viewSource = view;
                view.setBackgroundColor(Color.rgb(255, 144, 64));
                swipe_refresh_layout.setRefreshing(true);
                getRecyclerView_articles(sources.get(position).getId());
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


        recyclerView_vertical.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView_vertical, new RecyclerTouchListener.ClickListener() {

            @Override
            public void onClick(View view, int position) {
                Intent intent = new Intent(getActivity(), ReadArticle.class);
                intent.putExtra(api.KEY_URL_TAG, articles.get(position).getUrl());
                intent.putExtra(api.KEY_URL_TO_IMAGE_TAG, articles.get(position).getUrlToImage());

                startActivity(intent);


            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        recyclerView_vertical.setOnScrollListener(new StaggeredHiddingScrollListener() {
            @Override
            public void onHide() {
                hideViews();
            }

            @Override
            public void onShow() {
                showViews();
            }
        });
        isListView = true;
        getRecyclerView_sources();

        return view;
    }


    private void hideViews() {

    }

    private void showViews() {


    }

    public void setLayout(boolean isListView) {
        try {
            if (articles.size() > 1) {
                setRecyclerView_articles(articles);
            }
        } catch (Exception nl) {

        }
    }

    void getRecyclerView_sources() {
        if (News.category.equals("")) {
            requestDataSources(api.SOURCES_END_POINT);
        } else {
            // requestDataSources(api.SOURCES_END_POINT );
            requestDataSources(api.SOURCES_END_POINT + "?category=" + News.category);
        }
        // requestDataSources(api.SOURCES_END_POINT);
    }

    void getRecyclerView_sourcesC() {
        if (News.category.equals("")) {
            requestDataSourcesC(api.SOURCES_END_POINT);
        } else {

            requestDataSourcesC(api.SOURCES_END_POINT + "?category=" + News.category);
            Log.d("newsss", "" + api.SOURCES_END_POINT + "?category=" + News.category);
        }
        // requestDataSources(api.SOURCES_END_POINT);
    }

    public void setRecyclerView_sources(ArrayList<SourcesModel> sourcesModelArrayList) {
        SorcesModelAdapter adapter;
        this.sources = sourcesModelArrayList;
        adapter = new SorcesModelAdapter(sourcesModelArrayList, getContext());
        SourcesModel model = sourcesModelArrayList.get(0);

        adapter.notifyDataSetChanged();
        swipe_refresh_layout = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainer);
        recyclerView_horizontal = (RecyclerView) view.findViewById(R.id.all_news_horizontal_recyclerView);
        mStaggeredLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());

        recyclerView_horizontal.setLayoutManager(mStaggeredLayoutManager);
        recyclerView_horizontal.setItemAnimator(new DefaultItemAnimator());


        recyclerView_horizontal.setAdapter(adapter);

        swipe_refresh_layout.setRefreshing(false);

        getRecyclerView_articles(model.getId());
    }

    void getRecyclerView_articles(String name) {

        requestDataArticles(api.ARTICLES_END_POINT + name + "&apiKey=" + api.API_KEY);

    }

    public void setRecyclerView_articles(ArrayList<ArticlesModel> articlesModelArrayList) {
        articles = articlesModelArrayList;
        ArticlesModelAdapter adapter;

        adapter = new ArticlesModelAdapter(articlesModelArrayList, getContext());
        adapter.notifyDataSetChanged();

        swipe_refresh_layout = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainer);
        recyclerView_vertical = (RecyclerView) view.findViewById(R.id.all_news_vertical_recyclerView);

        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        if (isListView) {

            mStaggeredLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);


        } else {

            mStaggeredLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);


        }


        recyclerView_vertical.setLayoutManager(mStaggeredLayoutManager);

        recyclerView_vertical.setItemAnimator(new DefaultItemAnimator());


        recyclerView_vertical.setAdapter(adapter);
        swipe_refresh_layout.setRefreshing(false);
    }

    void initVolleyCallback() {
        mResultCallback = new IResult() {
            @Override
            public void notifySuccess(String requestType, String response) {

                ArrayList<SourcesModel> sourcesModelArrayList;
                ArrayList<ArticlesModel> articlesModelArrayList;
                ArrayList<SourcesModel> sourcesModelArrayListC;
                if (requestType.equals("GETCALL_NEWS_SOURCES")) {
                    sourcesModelArrayList = SourcesJsonParser.parseData(response, api.ALL_SORUCES_PARSING_CODE);
                    Log.d("newsss", "" + response);
                    setRecyclerView_sources(sourcesModelArrayList);
                } else if (requestType.equals("GETCALL_NEWS_ARTICLES")) {
                    articlesModelArrayList = ArticlesJsonParser.parseData(response);

                    setRecyclerView_articles(articlesModelArrayList);
                } else if (requestType.equals("GETCALL_NEWS_SOURCESC")) {
                    sourcesModelArrayListC = SourcesJsonParser.parseData(response, api.ALL_SORUCES_PARSING_CODE);

                    setRecyclerView_sources(sourcesModelArrayListC);
                }


            }

            @Override
            public void notifyError(String requestType, VolleyError error) {


                try {
                    toast("Network Error");
                    swipe_refresh_layout.setRefreshing(false);
                } catch (Exception cont) {
                    cont.printStackTrace();
                }
            }
        };
    }

    public void requestDataSources(String uri) {

        mVolleyService = new VolleyService(mResultCallback, getActivity());
        mVolleyService.getDataVolley("GETCALL_NEWS_SOURCES", uri);


    }

    public void requestDataArticles(String uri) {
        mVolleyService = new VolleyService(mResultCallback, getActivity());
        mVolleyService.getDataVolley("GETCALL_NEWS_ARTICLES", uri);


    }

    public Context getApplicationContext() {
        Context applicationContext = getContext();
        context = applicationContext;
        return applicationContext;
    }

    public void requestDataSourcesC(String uri) {

        mVolleyService = new VolleyService(mResultCallback, getApplicationContext());
        mVolleyService.getDataVolley("GETCALL_NEWS_SOURCESC", uri);

    }

    private void toast(String msg) {
        StyleableToast st = new StyleableToast(getContext(), msg, Toast.LENGTH_SHORT);
        st.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

        st.setTextColor(getResources().getColor(R.color.colorIcons));
        st.setIcon(R.drawable.ic_error_outline_white_24dp);

        st.setMaxAlpha();
        st.show();
        //  swipe_refresh_layout.setRefreshing(false);
    }


}



