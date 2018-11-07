package com.example.owner.myreposapplication.repo;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.owner.myreposapplication.api.RepoApi;
import com.example.owner.myreposapplication.dao.RepoDao;
import com.example.owner.myreposapplication.dto.RepoDto;
import com.example.owner.myreposapplication.dto.ApiResponse;
import com.example.owner.myreposapplication.dto.Resource;
import com.example.owner.myreposapplication.network.NetworkBoundResource;
import com.example.owner.myreposapplication.utils.adapters.Constants;

import java.util.List;
import java.util.concurrent.Executor;

import javax.inject.Inject;

/**
 * This the Single source of truth!
 * This one will use the NetworkBoundResource
 */
public class
RepoRepository {
    private static final String TAG = "RepoRepository";
    final private RepoApi api;
    final private RepoDao dao;
    final private Executor executor;

    @Inject
    public RepoRepository(RepoApi api, RepoDao dao, Executor executor) {
        this.api = api;
        this.dao = dao;
        this.executor = executor;

    }

    public LiveData<Resource<List<RepoDto>>> browseRepo(final int page, final int limit) {
        final int offset = (page - 1) * limit;
        LiveData<Resource<List<RepoDto>>> liveData = new NetworkBoundResource<List<RepoDto>, List<RepoDto>>() {
            @Override
            protected void saveCallResult(@NonNull List<RepoDto> response) {
                RepoDto[] arr = new RepoDto[response.size()];
                response.toArray(arr);
                dao.insertAll(arr);

            }

            @Override
            protected boolean shouldFetch(@Nullable List<RepoDto> data) {
                return data == null || data.isEmpty();//let's always refresh to be up to date. data == null || data.isEmpty();
            }

            @NonNull
            @Override
            protected LiveData<List<RepoDto>> loadFromDb() {
                return dao.get(offset, limit);
            }


            @NonNull
            @Override
            protected LiveData<ApiResponse<List<RepoDto>>> createCall() {
                LiveData<ApiResponse<List<RepoDto>>> response = api.getRepoLiveData(Constants.USERNAME, page, limit);
                return response;
            }
        }.getAsLiveData();

        return liveData;
    }

    public void clearCache() {
        executor.execute(() -> dao.deleteAll());
    }


}
