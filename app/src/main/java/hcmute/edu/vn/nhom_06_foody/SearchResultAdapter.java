package hcmute.edu.vn.nhom_06_foody;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Locale;

import hcmute.edu.vn.nhom_06_foody.Model.ModelRestaurant;

public class SearchResultAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private final static String TAG = "SearchResultAdapter";
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    private Context mContext;
    private List<ModelRestaurant> mData;
    private AssetManager assetManager;
    private Location restaurantLocation = new Location("Restaurant");

    public SearchResultAdapter(Context mContext, List<ModelRestaurant> mData) {
        this.mContext = mContext;
        this.mData = mData;
        assetManager = mContext.getAssets();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.cardview_searchresult, parent, false);
            return new MyViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false);
            return new LoadingViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof MyViewHolder) {
            populateItemRows((MyViewHolder) viewHolder, position);
        } else if (viewHolder instanceof LoadingViewHolder) {
            showLoadingView((LoadingViewHolder) viewHolder, position);
        }
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mData.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tv_title, tv_address, tv_category, tv_distance;
        ImageView img_res_thumbnail;
        CardView cardView;

        public MyViewHolder(View itemView){
            super(itemView);

            tv_title = (TextView) itemView.findViewById(R.id.search_result_title);
            tv_address = (TextView) itemView.findViewById(R.id.search_result_address);
            tv_category = (TextView) itemView.findViewById(R.id.search_result_category);
            tv_distance = (TextView) itemView.findViewById(R.id.search_result_distance);
            img_res_thumbnail = (ImageView) itemView.findViewById(R.id.search_result_img);
            cardView = (CardView) itemView.findViewById(R.id.cardview_search_result);
        }
    }

    private void populateItemRows(MyViewHolder viewHolder, final int position) {

        viewHolder.tv_title.setText(mData.get(position).getName());
        viewHolder.tv_address.setText(mData.get(position).getAddress());
        viewHolder.tv_category.setText(mData.get(position).getCategory());

        try{
            LatLng restaurantLatLng = getLocationFromAddress(mContext, mData.get(position).getAddress());
            restaurantLocation.setLatitude(restaurantLatLng.latitude);
            restaurantLocation.setLongitude(restaurantLatLng.longitude);

            if(HomeActivity.currentLocation != null){
                viewHolder.tv_distance.setText(Math.round(HomeActivity.currentLocation.distanceTo(restaurantLocation)/1000) + " km (Từ vị trí hiện tại)");
            }
        }
        catch(Exception ex){
            Log.d("SearchResultAdapter", ex.getMessage());
        }
        //Toast.makeText(DetailRestaurantActivity.this, String.valueOf(currentLocation.distanceTo(restaurantLocation)/1000), Toast.LENGTH_SHORT).show();

        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, DetailRestaurantActivity.class);
                intent.putExtra("RestaurantID", mData.get(position).getId());
                intent.putExtra("Province", mData.get(position).getProvince());
                //intent.putExtra("Distance", "");
                intent.putExtra("Category", mData.get(position).getCategory());
                mContext.startActivity(intent);
            }
        });

        //holder.tv_distance.setText("5km");

        try{
            InputStream is = assetManager.open(mData.get(position).getAvatar());
            Bitmap bitmap = BitmapFactory.decodeStream(is);
            viewHolder.img_res_thumbnail.setImageBitmap(bitmap);
        }
        catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }

    }

    public class LoadingViewHolder extends RecyclerView.ViewHolder {

        ProgressBar progressBar;
        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressBar);
        }
    }

    private void showLoadingView(LoadingViewHolder viewHolder, int position) {
        //ProgressBar would be displayed

    }

    public LatLng getLocationFromAddress(Context context, String strAddress) {

        Geocoder coder = new Geocoder(context, Locale.getDefault());
        List<Address> address;
        LatLng p1 = null;

        try {
            // May throw an IOException
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }

            Address location = address.get(0);
            p1 = new LatLng(location.getLatitude(), location.getLongitude() );

        } catch (IOException ex) {

            ex.printStackTrace();
        }

        return p1;
    }
}
