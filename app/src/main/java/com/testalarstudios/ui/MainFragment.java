package com.testalarstudios.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.testalarstudios.R;
import com.testalarstudios.adapter.ListAdapter;
import com.testalarstudios.databinding.FragmentMainBinding;
import com.testalarstudios.model.DataModel;
import com.testalarstudios.presenter.MainFragmentPresenter;
import com.testalarstudios.view.MainFragmentView;

import java.util.List;

import moxy.MvpAppCompatFragment;
import moxy.presenter.InjectPresenter;

public class MainFragment extends MvpAppCompatFragment implements MainFragmentView {
    @InjectPresenter
    public MainFragmentPresenter presenter;

    private FragmentMainBinding vb;

    private ListAdapter adapter;

    private int page = 1;
    private boolean isLoading = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        vb = FragmentMainBinding.bind(view);

        adapter = new ListAdapter(listAdapterListener);
        vb.recyclerView.setAdapter(adapter);

        vb.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager lm = (LinearLayoutManager) vb.recyclerView.getLayoutManager();
                int visibleItemCount = lm.getChildCount();
                int totalItemCount = lm.getItemCount();
                int pastVisibleItems = lm.findFirstVisibleItemPosition();

                if ((visibleItemCount + pastVisibleItems) >= totalItemCount && pastVisibleItems >= 0) {
                    if (totalItemCount / 10 > page) {
                        ++page;
                        return;
                    }
                    if (!isLoading) {
                        isLoading = true;
                        ++page;
                        presenter.loadMore(page);
                    }
                }
            }
        });
    }

    @Override
    public void setData(List<DataModel> data) {
        adapter.setData(data);
        vb.progress.getRoot().setVisibility(View.GONE);
        isLoading = false;
    }

    private final ListAdapter.ListAdapterListener listAdapterListener = (item) -> {
        NavController nav = Navigation.findNavController(getView());
        Bundle bundle = new Bundle();
        bundle.putSerializable("args", item);
        nav.navigate(R.id.action_mainFragment_to_detailsFragment, bundle);
    };

    @Override
    public void onLoading() {
        vb.progress.getRoot().setVisibility(View.VISIBLE);
    }

    @Override
    public void onError(String message) {
        vb.progress.getRoot().setVisibility(View.GONE);
        Snackbar.make(vb.getRoot(), message, Snackbar.LENGTH_LONG).show();
    }
}
