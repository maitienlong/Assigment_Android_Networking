package com.example.assigment_longmt.Retrofit;

import com.example.assigment_longmt.Model.Post;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RetrofitService {

    @POST("/services/rest")
    @FormUrlEncoded
    Call<Post> getAllPost(@Field("method") String method,
                          @Field("api_key") String api_key,
                          @Field("user_id") String user_id,
                          @Field("extras") String extras,
                          @Field("format") String format,
                          @Field("per_page") int per_page,
                          @Field("page") int page,
                          @Field("nojsoncallback") int nojsoncallback
    );

    @GET("/services/rest?method=flickr.favorites.getList&api_key=6f3c4406142208714992a9337af3fe3e&user_id=185894931@N02&extras=url_sq&format=json&per_page=10&page=1&nojsoncallback=1")
    Call<Post> getAllPosts();

}
