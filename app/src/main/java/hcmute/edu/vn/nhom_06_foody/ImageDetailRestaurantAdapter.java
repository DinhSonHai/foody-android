package hcmute.edu.vn.nhom_06_foody;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import hcmute.edu.vn.nhom_06_foody.Model.DbModelProduct;

public class ImageDetailRestaurantAdapter extends RecyclerView.Adapter<ImageDetailRestaurantAdapter.MyViewHolder> {

    private final static String TAG = "RecyclerViewAdapterImg";
    private Context mContext;
    private List<DbModelProduct> mData;
    private AssetManager assetManager;

    public ImageDetailRestaurantAdapter(Context mContext, List<DbModelProduct> mData) {
        this.mContext = mContext;
        this.mData = mData;
        assetManager = mContext.getAssets();
    }

    @Override
    public ImageDetailRestaurantAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.cardview_restaurant,parent,false);
        return new ImageDetailRestaurantAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageDetailRestaurantAdapter.MyViewHolder holder, int position) {
        holder.tv_title.setText(mData.get(position).getName());
        holder.tv_des.setText(mData.get(position).getDescription());
        try{
            InputStream is = assetManager.open(mData.get(position).getImage());
            Bitmap bitmap = BitmapFactory.decodeStream(is);
            holder.img_res_thumbnail.setImageBitmap(bitmap);
        }
        catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tv_title, tv_des;
        ImageView img_res_thumbnail;

        public MyViewHolder(View itemView){
            super(itemView);

            tv_title = (TextView) itemView.findViewById(R.id.restaurant_title_id);
            tv_des = (TextView) itemView.findViewById(R.id.restaurant_des_id);
            img_res_thumbnail = (ImageView) itemView.findViewById(R.id.restaurant_img_id);
        }
    }
}
