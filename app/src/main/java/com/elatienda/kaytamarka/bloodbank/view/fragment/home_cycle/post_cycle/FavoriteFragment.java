package com.elatienda.kaytamarka.bloodbank.view.fragment.home_cycle.post_cycle;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.elatienda.kaytamarka.bloodbank.R;
import com.elatienda.kaytamarka.bloodbank.adapter.PostAdapter;
import com.elatienda.kaytamarka.bloodbank.adapter.SpinnerAdapter;
import com.elatienda.kaytamarka.bloodbank.data.model.post.Post;
import com.elatienda.kaytamarka.bloodbank.data.model.post.PostData;
import com.elatienda.kaytamarka.bloodbank.util.OnEndLess;
import com.elatienda.kaytamarka.bloodbank.view.fragment.BaseFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.elatienda.kaytamarka.bloodbank.data.api.ApiClient.getClient;

public class FavoriteFragment extends BaseFragment {

    @BindView(R.id.fragment_favorite_toolbar)
    Toolbar fragmentFavoriteToolbar;
    @BindView(R.id.fragment_favorite_rv_fav_posts)
    RecyclerView fragmentFavoriteRvFavPosts;

    private PostAdapter postAdapter;
    private List<PostData> favPostsDataList = new ArrayList<>();
    private Integer maxPage = 0;
    private OnEndLess onEndLess;

    public FavoriteFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        setUpActivity();
        ButterKnife.bind(this, view);


        ((AppCompatActivity) Objects.requireNonNull(getActivity())).setSupportActionBar(fragmentFavoriteToolbar);
        fragmentFavoriteToolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        fragmentFavoriteToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity() != null) {
                    getActivity().onBackPressed();
                }
            }
        });

        BottomNavigationView navBar = getActivity().findViewById(R.id.activity_home_cycle_bn_nav);
        navBar.setVisibility(View.GONE);

        initRecyclerView();

        return view;
    }

    private void initRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        fragmentFavoriteRvFavPosts.setHasFixedSize(true);
        fragmentFavoriteRvFavPosts.setLayoutManager(layoutManager);

        onEndLess = new OnEndLess(layoutManager, 1) {
            @Override
            public void onLoadMore(int current_page) {

                if (current_page <= maxPage) {
                    if (maxPage != 0 && current_page != 1) {
                        onEndLess.previous_page = current_page;
                            getPost(current_page);
                    } else {
                        onEndLess.current_page = onEndLess.previous_page;
                    }
                } else {
                    onEndLess.current_page = onEndLess.previous_page;
                }
            }
        };
        fragmentFavoriteRvFavPosts.addOnScrollListener(onEndLess);

        postAdapter = new PostAdapter(getContext(), favPostsDataList);
        fragmentFavoriteRvFavPosts.setAdapter(postAdapter);
        getPost(1);
    }

    private void getPost(int page) {
        Call<Post> call = getClient().getAllPosts("Zz9HuAjCY4kw2Ma2XaA6x7T5O3UODws1UakXI9vgFVSoY3xUXYOarHX2VH27", page);
        startCall(call, page);
    }

    private void startCall(Call<Post> call, int page) {
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(@NonNull Call<Post> call, @NonNull Response<Post> response) {
                try {
                    assert response.body() != null;
                    if (response.body().getStatus() == 1) {
                        maxPage = response.body().getData().getLastPage();
                        favPostsDataList.addAll(response.body().getData().getData());
                        postAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(getActivity(), "onResponse status-0: \n" + response.body().getMsg(), Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(getActivity(), "Exception : \n" + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Post> call, @NonNull Throwable t) {
                Toast.makeText(getActivity(), "onFailure : \n" + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onBack() {
        super.onBack();
    }
}
