package hcmute.edu.vn.nhom_06_foody;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import hcmute.edu.vn.nhom_06_foody.Model.ModelMenuDetail;

public class MenuAndImageExpandableListAdapter extends BaseExpandableListAdapter {
    private static final String TAG = "CustomAdapter";
    private Context mContext;
    private List<String> mHeaderGroup;
    private HashMap<String, List<ModelMenuDetail>> mDataChild;

    public MenuAndImageExpandableListAdapter(Context context, List<String> headerGroup, HashMap<String, List<ModelMenuDetail>> datas) {
        mContext = context;
        mHeaderGroup = headerGroup;
        mDataChild = datas;
    }

    @Override
    public int getGroupCount() {
        return mHeaderGroup == null ? 0 : mHeaderGroup.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mDataChild.get(mHeaderGroup.get(groupPosition)) == null ? 0 : mDataChild.get(mHeaderGroup.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mHeaderGroup.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mDataChild.get(mHeaderGroup.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater li = LayoutInflater.from(mContext);
            convertView = li.inflate(R.layout.expandable_list_group, parent, false);
        }

        TextView tvHeader = (TextView) convertView.findViewById(R.id.tv_header);
        tvHeader.setText(mHeaderGroup.get(groupPosition));
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater li = LayoutInflater.from(mContext);
            convertView = li.inflate(R.layout.expandable_list_item, parent, false);
        }

        TextView tvTenMon = (TextView) convertView.findViewById(R.id.tv_ten_mon);
        TextView tvGia = (TextView) convertView.findViewById(R.id.tv_gia);
        tvTenMon.setText(((ModelMenuDetail) getChild(groupPosition, childPosition)).getName());
        tvGia.setText(String.valueOf(((ModelMenuDetail) getChild(groupPosition, childPosition)).getPrice()) + ",000 đ");
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
