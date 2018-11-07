package com.example.owner.myreposapplication.api;

import android.arch.lifecycle.LiveData;

import com.example.owner.myreposapplication.dto.RepoDto;
import com.example.owner.myreposapplication.dto.ApiResponse;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RepoApi {

    @GET("users/{user}/repos")
    LiveData<ApiResponse<List<RepoDto>>> getRepoLiveData(@Path("user") String user, @Query("page") int page, @Query("per_page") int limit);

}
