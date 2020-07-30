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

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import hcmute.edu.vn.nhom_06_foody.Model.DbModelImage;

public class FragmentImageAdapter extends RecyclerView.Adapter<FragmentImageAdapter.MyViewHolder> {

    private final static String TAG = "ImgRecyclerViewAdapter";
    private Context mContext;
    private List<DbModelImage> mData;
    private AssetManager assetManager;

    public FragmentImageAdapter(Context mContext, List<DbModelImage> mData) {
        this.mContext = mContext;
        this.mData = mData;
        assetManager = mContext.getAssets();
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.cardview_fragment_image, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        try{
            InputStream is = assetManager.open(mData.get(position).getLink());
            Bitmap bitmap = BitmapFactory.decodeStream(is);
            holder.img_book_thumbnail.setImageBitmap(bitmap);
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
        ImageView img_book_thumbnail;

        public MyViewHolder(View itemView){
            super(itemView);
            img_book_thumbnail = (ImageView) itemView.findViewById(R.id.book_img_id);

        }
    }
}
