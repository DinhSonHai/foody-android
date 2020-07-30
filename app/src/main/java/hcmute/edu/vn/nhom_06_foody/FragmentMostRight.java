package hcmute.edu.vn.nhom_06_foody;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import hcmute.edu.vn.nhom_06_foody.Model.ModelRestaurant;

public class FragmentMostRight extends Fragment {

    private View v;
    private MyDbClass myDbClass;
    private RecyclerView recyclerView;
    private SearchResultAdapter searchResultAdapter;

    public static List<ModelRestaurant> listSearchResult;
    String keyword = "";
    int provinceID;
    int startPosition;
    boolean isLoading = false;

    public FragmentMostRight() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.mostright_fragment, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerview_id);

        Bundle bundle = this.getArguments();
        keyword = bundle.getString("Keyword");
        provinceID = bundle.getInt("ProvinceID");

        myDbClass = new MyDbClass(getActivity());
        listSearchResult = new ArrayList<>();

        populateData();
        initAdapter();
        initScrollListener();

        return v;
    }

    private void populateData() {
        startPosition = 0;
        try{
            listSearchResult = myDbClass.getSearchLazyData(keyword, provinceID, startPosition, 5);
        }
        catch(Exception ex){
            Toast.makeText(getActivity(), "showData:-" + ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
        if(listSearchResult != null){
            if(provinceID == 65){
                SearchResultsActivity.tvProvince.setText("Địa điểm ở Toàn Quốc");
            }
            else {
                SearchResultsActivity.tvProvince.setText("Địa điểm ở " + listSearchResult.get(0).getProvince());
            }
            startPosition += listSearchResult.size();
        }
    }

    private void initAdapter() {
        if(listSearchResult != null){
            searchResultAdapter = new SearchResultAdapter(getContext(), listSearchResult);
            recyclerView.setAdapter(searchResultAdapter);
        }
    }

    private void initScrollListener() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                if(listSearchResult != null){
                    if (!isLoading) {
                        if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == listSearchResult.size() - 1) {
                            //bottom of list!
                            loadMore();
                            isLoading = true;
                        }
                    }
                }
            }
        });
    }

    private void loadMore() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                List<ModelRestaurant> listNew = new ArrayList<>();
                listNew = myDbClass.getSearchLazyData(keyword, provinceID, startPosition, 5);
                if(listNew != null){
                    for(int i = 0; i< listNew.size(); i++){
                        listSearchResult.add(listNew.get(i));
                    }
                    startPosition += listNew.size();
                }
                searchResultAdapter.notifyDataSetChanged();
                isLoading = false;
            }
        }, 2000);
    }
}
