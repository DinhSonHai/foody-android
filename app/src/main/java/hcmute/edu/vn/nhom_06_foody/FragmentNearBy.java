package hcmute.edu.vn.nhom_06_foody;

import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import hcmute.edu.vn.nhom_06_foody.Model.ModelRestaurant;

public class FragmentNearBy extends Fragment {

    private View v;
    private MyDbClass myDbClass;
    private List<ModelRestaurant> listNearByRestaurant;
    private String keyword = "";
    private int provinceID;

    public FragmentNearBy(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.nearby_fragment, container, false);

        Bundle bundle = this.getArguments();
        keyword = bundle.getString("Keyword");
        provinceID = bundle.getInt("ProvinceID");

        myDbClass = new MyDbClass(getActivity());
        listNearByRestaurant = new ArrayList<>();

        if(FragmentMostRight.listSearchResult != null){

            for(int i = 0; i < FragmentMostRight.listSearchResult.size(); i++){
                LatLng restaurantLatLng = new LatLng(FragmentMostRight.listSearchResult.get(i).getLatitude(), FragmentMostRight.listSearchResult.get(i).getLongtitude());

                Location restaurantLocation = new Location("Restaurant");
                restaurantLocation.setLatitude(restaurantLatLng.latitude);
                restaurantLocation.setLongitude(restaurantLatLng.longitude);
                if(HomeActivity.currentLocation != null){
                    if (Math.round(HomeActivity.currentLocation.distanceTo(restaurantLocation)/1000) < 10){
                        listNearByRestaurant.add(FragmentMostRight.listSearchResult.get(i));
                    }
                }
            }
            if(listNearByRestaurant != null){
                RecyclerView myrv = (RecyclerView) v.findViewById(R.id.recyclerview_id);
                SearchResultAdapter myAdapter = new SearchResultAdapter(getContext(), listNearByRestaurant);
                myrv.setLayoutManager(new GridLayoutManager(getContext(),1));
                myrv.setAdapter(myAdapter);
            }
        }
        return v;
    }
}
