package com.example.owner.myreposapplication.viewmodel;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.example.owner.myreposapplication.dto.RepoDto;
import com.example.owner.myreposapplication.dto.Resource;
import com.example.owner.myreposapplication.repo.RepoRepository;

import java.util.List;
import javax.inject.Inject;

public class MainActivityViewModel extends ViewModel {
    private RepoRepository repository;

    final private MutableLiveData<Request> request = new MutableLiveData();
    final private LiveData<Resource<List<RepoDto>>> result = Transformations.switchMap(request, new Function<Request, LiveData<Resource<List<RepoDto>>>>() {
        @Override
        public LiveData<Resource<List<RepoDto>>> apply(final Request input) {
            LiveData<Resource<List<RepoDto>>> resourceLiveData = repository.browseRepo(input.page, input.limit);
            final MediatorLiveData<Resource<List<RepoDto>>> mediator = new MediatorLiveData<>();
            mediator.addSource(resourceLiveData, repoDtos -> {
                List<RepoDto> resp = repoDtos.getData();
                Resource<List<RepoDto>> response = null;
                switch (repoDtos.getStatus()) {
                    case LOADING:
                        response = Resource.loading(resp);
                        break;
                    case SUCCESS:
                        Log.i("myapp","inside MainActivityViewModel@SUCCESS");
                        response = Resource.success(resp);
                        break;
                    case ERROR:
                        response = Resource.error(repoDtos.getException(), null);
                        break;

                }
                mediator.setValue(response);
            });
            return mediator;
        }
    });

    public static MainActivityViewModel create(FragmentActivity activity) {
        MainActivityViewModel viewModel = ViewModelProviders.of(activity).get(MainActivityViewModel.class);
        return viewModel;
    }

    public void load(int page, int limit) {
        request.setValue(new Request(page, limit));
    }

    @Inject
    public void setRepository(RepoRepository repository) {
        this.repository = repository;
    }

    public LiveData<Resource<List<RepoDto>>> getResult() {

        return result;
    }

    public void clearCache() {
        repository.clearCache();
    }

    public static class Request {
        final private int page, limit;

        public Request(int page, int limit) {
            this.page = page;
            this.limit = limit;
        }

        public int getLimit() {
            return limit;
        }
    }

}
