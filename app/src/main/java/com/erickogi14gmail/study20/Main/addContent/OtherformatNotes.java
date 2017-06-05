package com.erickogi14gmail.study20.Main.addContent;

import android.app.DownloadManager;
import android.app.DownloadManager.Query;
import android.app.DownloadManager.Request;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.erickogi14gmail.study20.Main.Adapters.FilesAdapter;
import com.erickogi14gmail.study20.Main.Adapters.FilesJsonParser;
import com.erickogi14gmail.study20.Main.Configs.api;
import com.erickogi14gmail.study20.Main.DB.DBOperations;
import com.erickogi14gmail.study20.Main.models.Files_model;
import com.erickogi14gmail.study20.Main.utills.RecyclerTouchListener;
import com.erickogi14gmail.study20.Main.volley.IResult;
import com.erickogi14gmail.study20.Main.volley.VolleyService;
import com.erickogi14gmail.study20.R;
import com.muddzdev.styleabletoastlibrary.StyleableToast;

import java.io.File;
import java.util.ArrayList;

import static android.content.Context.DOWNLOAD_SERVICE;
import static com.google.android.gms.internal.zzir.runOnUiThread;

/**
 * Created by kimani kogi on 5/31/2017.
 */

public class OtherformatNotes extends Fragment {


    static View view;

    static RecyclerView.LayoutManager mLayoutManager;
    static ArrayList<Files_model> files_model;
    static ArrayList<Files_model> temp_model;

    static FilesAdapter adapter;
    static IResult mResultCallback = null;
    static VolleyService mVolleyService;
    DBOperations dbOperations;
    SwipeRefreshLayout swipe_refresh_layout;
    RecyclerView recyclerView_vertical;
    ProgressDialog progressDialog;
    private StaggeredGridLayoutManager mStaggeredLayoutManager;
    private long enqueue;
    private DownloadManager dm;
    private int positionClicked = 0;

    public static String getMimeType(String url) {
        String type = null;
        String extension = MimeTypeMap.getFileExtensionFromUrl(url);
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        }
        return type;
    }

    static void getRecyclerView_sources(Context context) {
        requestDataSources(api.FILES_END_POINT, context);
    }

    static void filter(String text) {
        try {
            ArrayList<Files_model> temp = new ArrayList();
            for (Files_model d : files_model) {

                if (d.getPost_title().toLowerCase().contains(text.toLowerCase())
                        || d.getPost_author().toLowerCase().contains(text.toLowerCase())) {
                    temp.add(d);
                }

            }
            temp_model = temp;
            adapter.updateList(temp);
        } catch (Exception nm) {
            nm.printStackTrace();
        }

    }

    public static void requestDataSources(String uri, Context context) {


        mVolleyService = new VolleyService(mResultCallback, context);
        mVolleyService.getDataVolley("GETCALL_REVISION_SOURCES", uri);


    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        /**
         * Inflate the layout for this fragment
         */

        view = inflater.inflate(R.layout.fragment_courses, container, false);
        progressDialog = new ProgressDialog(view.getContext());
        initVolleyCallback();


        recyclerView_vertical = (RecyclerView) view.findViewById(R.id.recycle_view);
        swipe_refresh_layout = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainer);
        swipe_refresh_layout.setProgressBackgroundColorSchemeResource(R.color.colorAccent);
        swipe_refresh_layout.setBackgroundResource(android.R.color.white);
        swipe_refresh_layout.setColorSchemeResources(android.R.color.white, android.R.color.holo_purple, android.R.color.white);

        swipe_refresh_layout.setRefreshing(true);

        swipe_refresh_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipe_refresh_layout.setRefreshing(true);
                getRecyclerView_sources(getActivity());

            }
        });
        getRecyclerView_sources(getActivity());


        recyclerView_vertical.addOnItemTouchListener(new RecyclerTouchListener(getContext(), recyclerView_vertical, new RecyclerTouchListener.ClickListener() {

            @Override
            public void onClick(View view, int position) {
                try {


                    dm = (DownloadManager) getActivity().getSystemService(DOWNLOAD_SERVICE);
                    Request request = new Request(
                            Uri.parse(temp_model.get(position).getPost_pdf()));
                    request.allowScanningByMediaScanner();
                    request.setNotificationVisibility(Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                    enqueue = dm.enqueue(request);

                    final DownloadManager manager = (DownloadManager) getActivity().getSystemService(Context.DOWNLOAD_SERVICE);

                    final long downloadId = manager.enqueue(request);

                    //  final ProgressBar mProgressBar = (ProgressBar) view.findViewById(R.id.progressBar1);
                    final ProgressDialog progressDialog = new ProgressDialog(view.getContext());
                    new Thread(new Runnable() {

                        @Override
                        public void run() {

                            boolean downloading = true;

                            while (downloading) {

                                Query q = new Query();
                                q.setFilterById(downloadId);

                                Cursor cursor = manager.query(q);
                                cursor.moveToFirst();
                                int bytes_downloaded = cursor.getInt(cursor
                                        .getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                                int bytes_total = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));

                                if (cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)) == DownloadManager.STATUS_SUCCESSFUL) {
                                    downloading = false;
                                }

                                // final double dl_progress = (bytes_downloaded / bytes_total) * 100;
                                final int dl_progress = (int) ((bytes_downloaded * 100l) / bytes_total);
                                Log.d("intt", "" + dl_progress);

                                runOnUiThread(new Runnable() {

                                    @Override
                                    public void run() {
                                        progressDialog.setCancelable(false);
                                        progressDialog.setMessage("Downloading content.....");
                                        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                                        progressDialog.setMax(100);

                                        //progressDialog.setIndeterminate(false);
                                        progressDialog.setProgress(dl_progress);

                                        progressDialog.show();
                                        //mProgressBar.setProgress((int) dl_progress);

                                    }
                                });

                                //  Log.d(Constants.MAIN_VIEW_ACTIVITY, statusMessage(cursor));
                                cursor.close();
                            }
                            progressDialog.dismiss();


                        }
                    }).start();
                } catch (Exception m) {
                    Toast.makeText(getContext(), "Experiencing some Errors :Error 200-OFN", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onLongClick(View view, int position) {


            }
        }));
        BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(action)) {
                    long downloadId = intent.getLongExtra(
                            DownloadManager.EXTRA_DOWNLOAD_ID, 0);
                    Query query = new Query();
                    query.setFilterById(enqueue);
                    Cursor c = dm.query(query);
                    if (c.moveToFirst()) {
                        int columnIndex = c
                                .getColumnIndex(DownloadManager.COLUMN_STATUS);
                        if (DownloadManager.STATUS_SUCCESSFUL == c
                                .getInt(columnIndex)) {
                            Toast.makeText(context, "Download complete", Toast.LENGTH_SHORT).show();

                            //column for status

                            //column for reason code if the download failed or paused
                            int columnReason = c.getColumnIndex(DownloadManager.COLUMN_REASON);
                            int reason = c.getInt(columnReason);
                            //get the download filename
                            int filenameIndex = c.getColumnIndex(DownloadManager.COLUMN_LOCAL_FILENAME);
                            String filename = c.getString(filenameIndex);
                            openFile(filename);
//                            ImageView view = (ImageView) findViewById(R.id.imageView1);
//                            String uriString = c
//                                    .getString(c
//                                            .getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
//                            view.setImageURI(Uri.parse(uriString));
                        }
                    }
                }
            }
        };

        getActivity().registerReceiver(receiver, new IntentFilter(
                DownloadManager.ACTION_DOWNLOAD_COMPLETE));

        return view;
    }

    protected void openFile(String fileName) {
        try {


            Intent install = new Intent(Intent.ACTION_VIEW);

            install.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);


            install.setDataAndType(Uri.fromFile(new File(fileName)),
                    getMimeType(fileName));
            startActivity(install);
        } catch (Exception m) {
            Toast.makeText(getContext(), "Experiencing Opening this file." + "\n" + "Make sure you have a document reader installed :Error 2008-OTF", Toast.LENGTH_SHORT).show();
        }
    }

    private String statusMessage(Cursor c) {
        String msg = "???";

        switch (c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS))) {
            case DownloadManager.STATUS_FAILED:
                msg = "Download failed!";
                break;

            case DownloadManager.STATUS_PAUSED:
                msg = "Download paused!";
                break;

            case DownloadManager.STATUS_PENDING:
                msg = "Download pending!";
                break;

            case DownloadManager.STATUS_RUNNING:
                msg = "Download in progress!";
                break;

            case DownloadManager.STATUS_SUCCESSFUL:
                msg = "Download complete!";
                break;

            default:
                msg = "Download is nowhere in sight";
                break;
        }

        return (msg);
    }

    public void onClick(View view) {
//        dm = (DownloadManager)getSystemService(DOWNLOAD_SERVICE);
//        Request request = new Request(
//                Uri.parse("url for file to download"));
//        enqueue = dm.enqueue(request);

    }

    public void showDownload(View view) {
        Intent i = new Intent();
        i.setAction(DownloadManager.ACTION_VIEW_DOWNLOADS);
        startActivity(i);
    }

    public void setRecyclerView_courses(ArrayList<Files_model> files_modelArrayList) {
        try {
            files_model.clear();
        } catch (Exception MN) {
            MN.printStackTrace();
        }
        files_model = files_modelArrayList;
        temp_model = files_model;
//        Log.d("kj",""+assignment_model.get(1).getASSIGNMENT_COURSE_NAME());

        adapter = new FilesAdapter(getContext(), files_modelArrayList);
        adapter.notifyDataSetChanged();

        recyclerView_vertical = (RecyclerView) view.findViewById(R.id.recycle_view);

        mLayoutManager = new LinearLayoutManager(getContext());


        mStaggeredLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);


        recyclerView_vertical.setLayoutManager(mStaggeredLayoutManager);

        recyclerView_vertical.setItemAnimator(new DefaultItemAnimator());


        recyclerView_vertical.setAdapter(adapter);
        swipe_refresh_layout.setRefreshing(false);
    }

    void initVolleyCallback() {
        mResultCallback = new IResult() {
            @Override
            public void notifySuccess(String requestType, String response) {

                ArrayList<Files_model> files_modelArrayList = new ArrayList<>();
                ArrayList<Files_model> files_modelArrayListContent;
                if (requestType.equals("GETCALL_REVISION_SOURCES")) {
                    files_modelArrayList = FilesJsonParser.parseData(response);
                    Log.d("asdd", "" + files_modelArrayList);
                    setRecyclerView_courses(files_modelArrayList);
                } else if (requestType.equals("GETCALL_REVISION_CONTENT")) {
                    files_modelArrayListContent = FilesJsonParser.parseData(response);
                    // insert(files_modelArrayListContent, positionClicked);
                }


            }

            @Override
            public void notifyError(String requestType, VolleyError error) {

                //progressDialog.dismiss();
                try {
                    //  toast("Network Error");
                    swipe_refresh_layout.setRefreshing(false);
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                } catch (Exception cont) {
                    cont.printStackTrace();
                }
            }
        };
    }

    public void requestDataContent(String uri, final int position) {
        positionClicked = position;

        mVolleyService = new VolleyService(mResultCallback, getActivity());
        mVolleyService.getDataVolley("GETCALL_REVISION_CONTENT", uri);

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

