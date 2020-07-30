package hcmute.edu.vn.nhom_06_foody;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import hcmute.edu.vn.nhom_06_foody.Model.ModelRestaurant;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private final static String TAG = "RecyclerViewAdapter";
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    private Context mContext;
    private List<ModelRestaurant> mData;
    private AssetManager assetManager;

    public RecyclerViewAdapter(Context mContext, List<ModelRestaurant> mData) {
        this.mContext = mContext;
        this.mData = mData;
        assetManager = mContext.getAssets();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.cardview_restaurant, parent, false);
            return new RecyclerViewAdapter.MyViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false);
            return new RecyclerViewAdapter.LoadingViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof RecyclerViewAdapter.MyViewHolder) {
            populateItemRows((RecyclerViewAdapter.MyViewHolder) viewHolder, position);
        } else if (viewHolder instanceof RecyclerViewAdapter.LoadingViewHolder) {
            showLoadingView((RecyclerViewAdapter.LoadingViewHolder) viewHolder, position);
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
        TextView tv_title, tv_des;
        ImageView img_res_thumbnail;
        CardView cardView;

        public MyViewHolder(View itemView){
            super(itemView);

            tv_title = (TextView) itemView.findViewById(R.id.restaurant_title_id);
            tv_des = (TextView) itemView.findViewById(R.id.restaurant_des_id);
            img_res_thumbnail = (ImageView) itemView.findViewById(R.id.restaurant_img_id);
            cardView = (CardView) itemView.findViewById(R.id.cardview_id);
        }
    }

    private void populateItemRows(RecyclerViewAdapter.MyViewHolder viewHolder, final int position) {
        viewHolder.tv_title.setText(mData.get(position).getName());
        viewHolder.tv_des.setText(mData.get(position).getDescription());
        try{
            InputStream is = assetManager.open(mData.get(position).getAvatar());
            Bitmap bitmap = BitmapFactory.decodeStream(is);
            viewHolder.img_res_thumbnail.setImageBitmap(bitmap);
        }
        catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }
        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, DetailRestaurantActivity.class);
                intent.putExtra("RestaurantID", mData.get(position).getId());
                intent.putExtra("Province", mData.get(position).getProvince());
                //intent.putExtra("Distance", "5 km");
                intent.putExtra("Category", mData.get(position).getCategory());
                mContext.startActivity(intent);
            }
        });
    }

    public class LoadingViewHolder extends RecyclerView.ViewHolder {

        ProgressBar progressBar;
        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressBar);
        }
    }

    private void showLoadingView(RecyclerViewAdapter.LoadingViewHolder viewHolder, int position) {
        //ProgressBar would be displayed

    }
}
