package com.example.owner.myreposapplication.guiView;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.owner.myreposapplication.R;
//import com.example.owner.myreposapplication.databinding.ActivityMainBinding;
import com.example.owner.myreposapplication.databinding.ActivityMainBinding;
import com.example.owner.myreposapplication.dto.RepoDto;
import com.example.owner.myreposapplication.ui.DetailedActivity;
import com.example.owner.myreposapplication.ui.listeners.ExtendableList;
import com.example.owner.myreposapplication.ui.listeners.RecyclerViewLoadMoreScrollListener;

import java.util.ArrayList;
import java.util.List;

public class MainView implements GuiView {
    private View rootView;
    private ActivityMainBinding binding;
    private RecyclerView listView;
    private EventHandler handler;

    public MainView(LayoutInflater inflater, ViewGroup vg, final Listener listener) {
        this.handler = new EventHandler(listener);
        binding = DataBindingUtil.inflate(inflater, R.layout.activity_main, vg, false);
        binding.setHandler(handler);
        rootView = binding.getRoot();
        listView = rootView.findViewById(R.id.list);
        listView.setLayoutManager(new LinearLayoutManager(rootView.getContext(), LinearLayoutManager.VERTICAL, false));
        listView.setAdapter(new RepoAdapter(inflater));

        RecyclerViewLoadMoreScrollListener loadMoreScrollListener = new RecyclerViewLoadMoreScrollListener(new ExtendableList() {
            @Override
            public void loadMore() {
                if (listener != null) {
                    listener.loadMore();
                }
            }
        }, 100, 3);
        listView.addOnScrollListener(loadMoreScrollListener);
        loadMoreScrollListener.loadMoreIfneeded(listView);
    }

    @Override
    public View getRootView() {
        return rootView;
    }

    @Override
    public Bundle getViewState() {
        return null;
    }

    public void loading(boolean loading) {
        binding.setShowLoadMoreSpinner(loading);

    }

    public void refreshing(boolean refreshing) {
        binding.setRefreshing(refreshing);

    }

    public void bind(List<RepoDto> list) {
        if (list == null) return;

        ((RepoAdapter) listView.getAdapter()).getRepoDtoList().addAll(list);
       // ((RepoAdapter) listView.getAdapter()).notifyItemRangeChanged();
        if (((RepoAdapter) listView.getAdapter()).getRepoDtoList().size() != listView.getAdapter().getItemCount()) {
            listView.getAdapter().notifyDataSetChanged();
        }
    }

    public interface Listener {

        void loadMore();

        void clearCache();
    }

    public class EventHandler {
        final private Listener listener;

        public EventHandler(Listener listener) {
            this.listener = listener;
        }

        public void onClearCacheClicked(View v) {
            if (listener != null) listener.clearCache();
        }
    }

    private class RepoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        final private LayoutInflater inflater;
        private List<RepoDto> repoDtoList = new ArrayList<>();

        public List<RepoDto> getRepoDtoList() {
            return repoDtoList;
        }

        public void setRepoDtoList(List<RepoDto> repoDtoList) {
            this.repoDtoList = repoDtoList;
        }

        private RepoAdapter(LayoutInflater inflater) {
            this.inflater = inflater;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            ItemView view = new ItemView(inflater, parent, new ItemView.Listener() {
            });
            ItemHolder holder = new ItemHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            RepoDto repoDto = repoDtoList.get(position);
            ((ItemHolder) holder).bind(repoDto);

            holder.itemView.setOnClickListener(v -> {
                String name = ((TextView) listView.findViewHolderForAdapterPosition(position).itemView.findViewById(R.id.txtName)).getText().toString();
                Intent intent = new Intent(rootView.getContext(), DetailedActivity.class);
                intent.putExtra("name", name);

                for (RepoDto item : repoDtoList) {
                    if (item.getName().equals(name)) {

                        intent.putExtra("description", item.getDescription());
                        intent.putExtra("html_url", item.getHtml_url());
                        intent.putExtra("pushed_at", item.getPushed_at());

                        break;
                    }
                }

                rootView.getContext().startActivity(intent);
            });
        }

        @Override
        public int getItemCount() {
            return repoDtoList.size();
        }

        public class ItemHolder extends RecyclerView.ViewHolder {
            ItemView holder;

            public ItemHolder(ItemView holder) {
                super(holder.getRootView());
                this.holder = holder;
            }

            public void bind(RepoDto repoDto) {
                holder.bind(repoDto);
            }
        }
    }


}
