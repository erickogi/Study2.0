package com.erickogi14gmail.study20.Main.News_Api_news;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
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
import com.erickogi14gmail.study20.R;
import com.muddzdev.styleabletoastlibrary.StyleableToast;

import java.util.ArrayList;

/**
 * Created by kimani kogi on 5/17/2017.
 */

public class localNews extends android.support.v4.app.Fragment {
    static RequestQueue queue;
    static Context context;
    static RecyclerView.LayoutManager mLayoutManager;
    static View view;
    static View viewSource;
    static ArrayList<ArticlesModel> articles;
    private static boolean isListView;
    SwipeRefreshLayout swipe_refresh_layout;
    RecyclerView recyclerView_vertical;
    RecyclerView recyclerView_horizontal;
    FloatingActionButton fab;
    ArrayList<SourcesModel> sources;
    private StaggeredGridLayoutManager mStaggeredLayoutManager;
    private boolean loadLocal = false;

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
        mStaggeredLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL);

        recyclerView_horizontal = (RecyclerView) view.findViewById(R.id.all_news_horizontal_recyclerView);
        recyclerView_vertical = (RecyclerView) view.findViewById(R.id.all_news_vertical_recyclerView);
        //refresh();
        if (loadLocal) {
            refresh();


            swipe_refresh_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    swipe_refresh_layout.setRefreshing(true);
                    getRecyclerView_sources();

                }
            });

            fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
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
        } else {
            refresh();
            swipe_refresh_layout.setRefreshing(false);
            swipe_refresh_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    swipe_refresh_layout.setRefreshing(true);
                    StyleableToast st = new StyleableToast(getApplicationContext(), "LOCAL NEWS SOURCES NOT READY YET.. Slide Left to View International News ", Toast.LENGTH_LONG);
                    st.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    st.setTextColor(Color.WHITE);
                    st.setIcon(R.drawable.ic_error_outline_white_24dp);

                    st.setMaxAlpha();
                    st.show();
                    swipe_refresh_layout.setRefreshing(false);

                }
            });
        }
        return view;


    }

    public void start() {
        refresh();

        getRecyclerView_sourcesC();
    }

    private void hideViews() {
        fab.hide();
        // int fabBottomMargin = 45;
        // fab.animate().translationY(fab.getHeight() + fabBottomMargin).setInterpolator(new AccelerateInterpolator(2)).start();
    }

    private void showViews() {
        fab.hide();
        // fab.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();

    }

    public void setLayout(boolean isListView) {
        localNews.isListView = News.isListView;
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

            requestDataSources(api.SOURCES_END_POINT + "?category=" + News.category);
        }
        // requestDataSources(api.SOURCES_END_POINT);
    }

    void getRecyclerView_sourcesC() {
        if (News.category.equals("")) {
            requestDataSourcesC(api.SOURCES_END_POINT);
        } else {

            requestDataSourcesC(api.SOURCES_END_POINT + "?category=" + News.category);
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

    public void requestDataSources(String uri) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, uri,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ArrayList<SourcesModel> sourcesModelArrayList;

                        if (response != null || !response.isEmpty()) {

                            sourcesModelArrayList = SourcesJsonParser.parseData(response, api.ALL_SORUCES_PARSING_CODE);

                            setRecyclerView_sources(sourcesModelArrayList);

                        } else {
                            swipe_refresh_layout.setRefreshing(false);
                        }

                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        StyleableToast st = new StyleableToast(getApplicationContext(), "Network error", Toast.LENGTH_SHORT);
                        st.setBackgroundColor(Color.parseColor("#ff9040"));
                        st.setTextColor(Color.WHITE);
                        st.setIcon(R.drawable.ic_error_outline_white_24dp);

                        st.setMaxAlpha();
                        st.show();
                        swipe_refresh_layout.setRefreshing(false);


                    }
                });
        queue = Volley.newRequestQueue(getContext());
        queue.add(stringRequest);
        context = getContext();
    }

    public void requestDataArticles(String uri) {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, uri,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        ArrayList<ArticlesModel> articlesModelArrayList;

                        if (response != null || !response.isEmpty()) {

                            articlesModelArrayList = ArticlesJsonParser.parseData(response);


                            setRecyclerView_articles(articlesModelArrayList);


                        }


                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        StyleableToast st = new StyleableToast(getApplicationContext(), "Network error", Toast.LENGTH_SHORT);
                        st.setBackgroundColor(Color.parseColor("#ff9040"));
                        st.setTextColor(Color.WHITE);
                        st.setIcon(R.drawable.ic_error_outline_white_24dp);

                        st.setMaxAlpha();
                        st.show();
                        swipe_refresh_layout.setRefreshing(false);
                    }
                });

        queue.add(stringRequest);


    }

    public Context getApplicationContext() {
        Context applicationContext = getContext();
        context = applicationContext;
        return applicationContext;
    }

    public void requestDataSourcesC(String uri) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, uri,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ArrayList<SourcesModel> sourcesModelArrayList;

                        if (response != null || !response.isEmpty()) {

                            sourcesModelArrayList = SourcesJsonParser.parseData(response, api.ALL_SORUCES_PARSING_CODE);

                            setRecyclerView_sources(sourcesModelArrayList);

                        } else {
                            swipe_refresh_layout.setRefreshing(false);
                        }

                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        StyleableToast st = new StyleableToast(getContext(), "Network error", Toast.LENGTH_SHORT);
//                        st.setBackgroundColor(Color.parseColor("#ff9040"));
//                        st.setTextColor(Color.WHITE);
//                        st.setIcon(R.drawable.ic_error_outline_white_24dp);
//
//                        st.setMaxAlpha();
//                        st.show();
                        swipe_refresh_layout.setRefreshing(false);


                    }
                });
        queue.add(stringRequest);
    }
}



