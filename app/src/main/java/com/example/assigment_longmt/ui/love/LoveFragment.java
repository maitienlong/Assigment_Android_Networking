package com.example.assigment_longmt.ui.love;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.assigment_longmt.Adapter.EndlessLovePhoto;
import com.example.assigment_longmt.Adapter.YeuThichAdapterRC;
import com.example.assigment_longmt.Model.Photo;
import com.example.assigment_longmt.Model.Photos;
import com.example.assigment_longmt.Model.Post;
import com.example.assigment_longmt.R;
import com.example.assigment_longmt.Retrofit.MyRetrofit;
import com.example.assigment_longmt.Retrofit.RetrofitService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoveFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private LoveViewModel loveViewModel;
    private String method = "flickr.favorites.getList",
            api_key = "6f3c4406142208714992a9337af3fe3e",
            user_id = "185894931@N02",
            extras = "views, media, path_alias, url_sq, url_t, url_s, url_q, url_m, url_n, url_z, url_c, url_l, url_o\n",
            format = "json";
    private int per_page = 10,
            page = 1,
            nojsoncallback = 1;

    private RecyclerView rcLove;
    private SwipeRefreshLayout swipeRefreshLayout;
    StaggeredGridLayoutManager staggeredGridLayoutManager;

    List<Photo> photoList;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        loveViewModel = ViewModelProviders.of(this).get(LoveViewModel.class);
        View root = inflater.inflate(R.layout.fragment_love, container, false);
        return root;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rcLove = view.findViewById(R.id.rcLove);
        swipeRefreshLayout = view.findViewById(R.id.swiperefreshlayout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);


        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
                LoadImage(page);


            }
        });

    }

    @Override
    public void onRefresh() {
        LoadImage(page);
    }

    public void LoadImage(int pages){

        Retrofit retrofit = MyRetrofit.getInstance("https://www.flickr.com");
        RetrofitService retrofitService = retrofit.create(RetrofitService.class);
        retrofitService.getAllPost(method,api_key,user_id,extras,format,per_page,pages,nojsoncallback).enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {

                Post post = response.body();
                Photos photos = post.getPhotos();
                photoList = photos.getPhoto();
                ListView(photoList);
                Log.i("Photo_Dai",photoList.size()+"");

            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {

            }
        });

    }

    private void ListView(List<Photo> photoList){

        swipeRefreshLayout.setRefreshing(true);

        rcLove.setHasFixedSize(true);
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        staggeredGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        rcLove.setLayoutManager(staggeredGridLayoutManager);
        YeuThichAdapterRC adapterRecycleView = new YeuThichAdapterRC(getActivity(), photoList);
        rcLove.setAdapter(adapterRecycleView);
        YeuThichAdapterRC.ItemClickSupport.addTo(rcLove).setOnItemClickListener(new YeuThichAdapterRC.ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                Toast.makeText(getActivity(), "Clicked", Toast.LENGTH_SHORT).show();
            }
        });
        rcLove.addOnScrollListener(new EndlessLovePhoto(staggeredGridLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
  
                Toast.makeText(getActivity(), "AKKKKKKK", Toast.LENGTH_SHORT).show();
            }
        });

        swipeRefreshLayout.setRefreshing(false);
    }


}