package hcmute.edu.vn.nhom_06_foody;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import hcmute.edu.vn.nhom_06_foody.Model.DbModelProvince;

public class ProvinceAdapter extends RecyclerView.Adapter<ProvinceAdapter.MyViewHolder> {

    private Context mContext;
    private List<DbModelProvince> mData;
    private int checkedPosition = ProvinceActivity.provinceID - 1;

    public ProvinceAdapter(Context mContext, List<DbModelProvince> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public ProvinceAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.cardview_province,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ProvinceAdapter.MyViewHolder holder, final int position) {
        //Toast.makeText(mContext, String.valueOf(ProvinceActivity.provinceID), Toast.LENGTH_SHORT).show();
        holder.bind(mData.get(position));
        holder.tv_province.setText(mData.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tv_id, tv_province;
        ImageView ivChecked;
        CardView cardView;

        public MyViewHolder(View itemView){
            super(itemView);

            tv_province = (TextView) itemView.findViewById(R.id.province_name);
            ivChecked = (ImageView) itemView.findViewById(R.id.image_checked);
            cardView = (CardView) itemView.findViewById(R.id.cardview_province);
        }

        void bind(final DbModelProvince province) {
            if (checkedPosition == -1) {
                tv_province.setTextColor(Color.BLACK);
                ivChecked.setVisibility(View.INVISIBLE);
            } else {
                if (checkedPosition == getAdapterPosition()) {
                    tv_province.setTextColor(Color.BLUE);
                    ivChecked.setVisibility(View.VISIBLE);
                } else {
                    tv_province.setTextColor(Color.BLACK);
                    ivChecked.setVisibility(View.INVISIBLE);
                }
            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    tv_province.setTextColor(Color.BLUE);
                    ivChecked.setVisibility(View.VISIBLE);
                    if (checkedPosition != getAdapterPosition()) {
                        notifyItemChanged(checkedPosition);
                        checkedPosition = getAdapterPosition();
                        //restaurantID = province.getId();
                        ProvinceActivity.provinceID = province.getId();
                    }
                }
            });
        }
    }

}
