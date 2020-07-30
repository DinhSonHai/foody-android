package hcmute.edu.vn.nhom_06_foody;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import hcmute.edu.vn.nhom_06_foody.Model.DbModelImage;

public class FragmentImage extends Fragment {

    private View v;
    private MyDbClass myDbClass;
    private List<DbModelImage> listImage;

    public FragmentImage() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.image_fragment, container, false);

        Bundle bundle = getActivity().getIntent().getExtras();
        int id = bundle.getInt("RestaurantID");

        myDbClass = new MyDbClass(getActivity());
        listImage = new ArrayList<>();

        try{
            listImage = myDbClass.getAllRestaurantImage(id);
        }
        catch(Exception ex){
            Toast.makeText(getActivity(), "showData:-" + ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
        if(listImage != null){
            RecyclerView myrv = (RecyclerView) v.findViewById(R.id.recyclerview_id);
            FragmentImageAdapter myAdapter = new FragmentImageAdapter(getContext(), listImage);
            myrv.setLayoutManager(new GridLayoutManager(getContext(), 3));
            myrv.setAdapter(myAdapter);
        }

        return v;
    }
}
