package hcmute.edu.vn.nhom_06_foody;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import hcmute.edu.vn.nhom_06_foody.Model.DbModelProduct;
import hcmute.edu.vn.nhom_06_foody.Model.DbModelProduct_Category;
import hcmute.edu.vn.nhom_06_foody.Model.ModelMenuDetail;

public class FragmentMenu extends Fragment {

    private View v;
    private static final String TAG = "HomeActivity";
    private ExpandableListView eplMonAn;
    private HashMap<String, List<ModelMenuDetail>> mData;
    private MyDbClass myDbClass;
    private List<DbModelProduct_Category> listCategory;
    private List<DbModelProduct> listProduct;
    private List<ModelMenuDetail> listMenu;

    public FragmentMenu() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.menu_fragment, container, false);
        eplMonAn = (ExpandableListView) v.findViewById(R.id.eplChat);

        Bundle bundle = getActivity().getIntent().getExtras();
        int id = bundle.getInt("RestaurantID");

        myDbClass = new MyDbClass(getActivity());
        listCategory = new ArrayList<>();
        mData = new HashMap<>();

        try{
            listCategory = myDbClass.getProductCategoryOfRestaurant(id);
        }
        catch(Exception ex){
            Toast.makeText(getActivity(), "showData:-" + ex.getMessage(), Toast.LENGTH_SHORT).show();
        }

        final List<String> listHeader = new ArrayList<>();

        if(listCategory != null){
            //data for header group
            for (int i = 0; i < listCategory.size(); i++){

                listHeader.add(listCategory.get(i).getName());
//            mData = new HashMap<>();
                listMenu = new ArrayList<>();
                listProduct = new ArrayList<>();

                try{
                    listProduct = myDbClass.getProductByCategory(listCategory.get(i).getId());
                }
                catch(Exception ex){
                    Toast.makeText(getActivity(), "showData:-" + ex.getMessage(), Toast.LENGTH_SHORT).show();
                }

                for (int j = 0; j < listProduct.size(); j++){
                    listMenu.add(new ModelMenuDetail(listProduct.get(j).getName(), listProduct.get(j).getPrice()));
                }

                mData.put(listHeader.get(i), listMenu);
            }
            //setup adapter for ExpandableListView
            MenuAndImageExpandableListAdapter adapter = new MenuAndImageExpandableListAdapter(getContext(), listHeader, mData);
            eplMonAn.setAdapter(adapter);
        }


        eplMonAn.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Log.e(TAG, "onChildClick: " + listHeader.get(groupPosition) + ", " + mData.get(listHeader.get(groupPosition)).get(childPosition).getName());
                return false;
            }
        });

        eplMonAn.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                Log.e(TAG, "onGroupClick: " + groupPosition);
                return false;
            }
        });

        eplMonAn.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
                Log.e(TAG, "onGroupCollapse: " + groupPosition);
            }
        });

        eplMonAn.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                Log.e(TAG, "onGroupExpand: " + groupPosition);
            }
        });

        int width = getResources().getDisplayMetrics().widthPixels;
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
            eplMonAn.setIndicatorBounds(width - getPixelValue(40), width - getPixelValue(10));
        } else {
            eplMonAn.setIndicatorBoundsRelative(width - getPixelValue(40), width - getPixelValue(10));
        }

        return v;
    }

    public int getPixelValue(int dp) {

        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

}
