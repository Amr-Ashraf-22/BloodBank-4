package com.elatienda.kaytamarka.bloodbank.view.fragment.home_cycle.post_cycle;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.elatienda.kaytamarka.bloodbank.R;
import com.elatienda.kaytamarka.bloodbank.adapter.PostAdapter;
import com.elatienda.kaytamarka.bloodbank.adapter.SpinnerAdapter;
import com.elatienda.kaytamarka.bloodbank.data.model.post.Post;
import com.elatienda.kaytamarka.bloodbank.data.model.post.PostData;
import com.elatienda.kaytamarka.bloodbank.util.HelperMethod;
import com.elatienda.kaytamarka.bloodbank.util.OnEndLess;
import com.elatienda.kaytamarka.bloodbank.view.fragment.BaseFragment;
import com.elatienda.kaytamarka.bloodbank.view.fragment.home_cycle.donation_cycle.CreateDonationFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.elatienda.kaytamarka.bloodbank.data.api.ApiClient.getClient;
import static com.elatienda.kaytamarka.bloodbank.util.GeneralRequest.getSpinnerData;

public class PostFragment extends BaseFragment {

    @BindView(R.id.fragment_post_et_search_word)
    EditText fragmentPostEtSearchWord;
    @BindView(R.id.fragment_post_sp_category)
    Spinner fragmentPostSpCategory;
    @BindView(R.id.fragment_post_rv_posts)
    RecyclerView fragmentPostRvPosts;

    private SpinnerAdapter categoriesAdapter;
    private PostAdapter postAdapter;
    private List<PostData> postDataList = new ArrayList<>();
    private Integer maxPage = 0;
    private OnEndLess onEndLess;
    private boolean Filter = false;

    public PostFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post, container, false);
        setUpActivity();
        ButterKnife.bind(this, view);

        BottomNavigationView navBar = Objects.requireNonNull(getActivity()).findViewById(R.id.activity_home_cycle_bn_nav);
        navBar.setVisibility(View.VISIBLE);

        categoriesAdapter = new SpinnerAdapter(getActivity());
        getSpinnerData(getActivity(), fragmentPostSpCategory, categoriesAdapter,
                getString(R.string.fragment_post_sp_category), getClient().getCategories());

        initRecyclerView();

        return view;
    }

    private void initRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        fragmentPostRvPosts.setHasFixedSize(true);
        fragmentPostRvPosts.setLayoutManager(layoutManager);

        onEndLess = new OnEndLess(layoutManager, 1) {
            @Override
            public void onLoadMore(int current_page) {

                if (current_page <= maxPage) {
                    if (maxPage != 0 && current_page != 1) {
                        onEndLess.previous_page = current_page;
                        if (Filter){
                            onFilter(current_page);
                        }else {
                            getPost(current_page);
                        }
                    } else {
                        onEndLess.current_page = onEndLess.previous_page;
                    }
                } else {
                    onEndLess.current_page = onEndLess.previous_page;
                }
            }
        };
        fragmentPostRvPosts.addOnScrollListener(onEndLess);

        postAdapter = new PostAdapter(getContext(), postDataList);
        fragmentPostRvPosts.setAdapter(postAdapter);
        getPost(1);
    }

    private void getPost(int page) {
        Call<Post> call = getClient().getAllPosts("Zz9HuAjCY4kw2Ma2XaA6x7T5O3UODws1UakXI9vgFVSoY3xUXYOarHX2VH27", page);
        startCall(call, page);
    }

    @OnClick({R.id.fragment_post_img_btn_search, R.id.fragment_post_fab_request_donation})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fragment_post_img_btn_search:
                onFilter(1);
                break;
            case R.id.fragment_post_fab_request_donation:
                if(getActivity()!=null){
                    CreateDonationFragment createDonationFragment = new CreateDonationFragment();
                    HelperMethod.replaceFragment(getActivity().getSupportFragmentManager(),R.id.activity_home_cycle_fl_container,createDonationFragment);
                }
                break;
        }
    }

    private void onFilter(int page) {
        Filter = true;

        onEndLess.previousTotal = 0;
        onEndLess.current_page = 1;
        onEndLess.previous_page = 1;
        postDataList = new ArrayList<>();

        postAdapter = new PostAdapter(getActivity(), postDataList);
        fragmentPostRvPosts.setAdapter(postAdapter);

        Call<Post> call = getClient().getAllPostsFilter("Zz9HuAjCY4kw2Ma2XaA6x7T5O3UODws1UakXI9vgFVSoY3xUXYOarHX2VH27",
                page, fragmentPostEtSearchWord.getText().toString(), categoriesAdapter.selectedId );
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
                        postDataList.addAll(response.body().getData().getData());
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
        if(getActivity()!=null){
            getActivity().moveTaskToBack(true);
            getActivity().finish();
        }
    }

}