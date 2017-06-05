package com.erickogi14gmail.study20.Main.News_Api_news;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
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
import com.erickogi14gmail.study20.R;
import com.muddzdev.styleabletoastlibrary.StyleableToast;

import java.util.ArrayList;

public class Categories extends AppCompatActivity {
    static RequestQueue queue;
    static Context context;
    static RecyclerView.LayoutManager mLayoutManager;
    static View view;
    static View viewSource;
    static ArrayList<ArticlesModel> articles;
    SwipeRefreshLayout swipe_refresh_layout;
    RecyclerView recyclerView_vertical;
    RecyclerView recyclerView_horizontal;
    FloatingActionButton fab;
    ArrayList<SourcesModel> sources;
    String category;
    private StaggeredGridLayoutManager mStaggeredLayoutManager;
    private boolean isListView = true;
    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);


        Intent intent = getIntent();

        category = intent.getStringExtra(api.KEY_CATEGORY_INTENT);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(intent.getStringExtra(api.KEY_CATEGORY_LABEL));
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        recyclerView_horizontal = (RecyclerView) findViewById(R.id.all_news_horizontal_recyclerView);
        recyclerView_vertical = (RecyclerView) findViewById(R.id.all_news_vertical_recyclerView);
        swipe_refresh_layout = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        swipe_refresh_layout.setProgressBackgroundColorSchemeResource(R.color.colorAccent);
        swipe_refresh_layout.setBackgroundResource(android.R.color.white);
        swipe_refresh_layout.setColorSchemeResources(android.R.color.white, android.R.color.holo_purple, android.R.color.white);

        swipe_refresh_layout.setRefreshing(true);

        swipe_refresh_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipe_refresh_layout.setRefreshing(true);
                getRecyclerView_sources();

            }
        });
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
                Intent intent = new Intent(Categories.this, ReadArticle.class);
                intent.putExtra(api.KEY_URL_TAG, articles.get(position).getUrl());
                intent.putExtra(api.KEY_URL_TO_IMAGE_TAG, articles.get(position).getUrlToImage());

                startActivity(intent);


            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    void getRecyclerView_sources() {


        requestDataSources(api.SOURCES_END_POINT + "?category=" + category);
    }

    public void setRecyclerView_sources(ArrayList<SourcesModel> sourcesModelArrayList) {
        SorcesModelAdapter adapter;
        this.sources = sourcesModelArrayList;
        adapter = new SorcesModelAdapter(sourcesModelArrayList, getApplicationContext());
        SourcesModel model = sourcesModelArrayList.get(0);

        adapter.notifyDataSetChanged();
        swipe_refresh_layout = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        recyclerView_horizontal = (RecyclerView) findViewById(R.id.all_news_horizontal_recyclerView);
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
        adapter = new ArticlesModelAdapter(articlesModelArrayList, getApplicationContext());
        adapter.notifyDataSetChanged();
        swipe_refresh_layout = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        recyclerView_vertical = (RecyclerView) findViewById(R.id.all_news_vertical_recyclerView);

        mLayoutManager = new LinearLayoutManager(getApplicationContext());

        if (this.isListView) {

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

                            sourcesModelArrayList = SourcesJsonParser.parseData(response, api.ALL_TECH_SOURCES_PARSING_CODE);

                            setRecyclerView_sources(sourcesModelArrayList);

                        } else {
                            swipe_refresh_layout.setRefreshing(false);
                        }

                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        toast("Network error");
                        swipe_refresh_layout.setRefreshing(false);


                    }
                });
        queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(stringRequest);
        context = getApplicationContext();
    }

//    public Context getApplicationContext() {
//        Context applicationContext = getContext();
//        context = applicationContext;
//        return applicationContext;
//    }

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
                        toast("Network error");
                        swipe_refresh_layout.setRefreshing(false);
                    }
                });

        queue.add(stringRequest);


    }

    private void toast(String msg) {
        StyleableToast st = new StyleableToast(getApplicationContext(), msg, Toast.LENGTH_SHORT);
        st.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

        st.setTextColor(getResources().getColor(R.color.colorIcons));
        st.setIcon(R.drawable.ic_error_outline_white_24dp);

        st.setMaxAlpha();
        st.show();
        //  swipe_refresh_layout.setRefreshing(false);
    }
}
