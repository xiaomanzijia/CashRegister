package com.licheng.github.cashregister.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.licheng.github.cashregister.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by licheng on 3/3/16.
 */
public class ExpandBaseAdapter extends BaseExpandableListAdapter {

    private static final String G_TEXT	= "G_TEXT";
    //private static final String G_CB	= "G_CB";
    private static final String C_TITLE = "C_TITLE";
    private static final String C_TEXT	= "C_TEXT";
    private static final String C_CB	= "C_CB";

    List<Map<String, String>> groupData = new ArrayList<Map<String, String>>();
    List<List<Map<String, String>>> childData = new ArrayList<List<Map<String, String>>>();

    List<Map<String, Boolean>> groupCheckBox = new ArrayList<Map<String,Boolean>>();
    List<List<Map<String, Boolean>>> childCheckBox = new ArrayList<List<Map<String,Boolean>>>();

    private Context context;
    //ExListView exlistview;
    ViewHolder holder;

    public ExpandBaseAdapter(Context context,
                             List<Map<String, String>> groupData, List<List<Map<String, String>>> childData,
                             List<Map<String, Boolean>> groupCheckBox, List<List<Map<String, Boolean>>> childCheckBox) {
        this.groupData = groupData;
        this.childData = childData;
        this.groupCheckBox = groupCheckBox;
        this.childCheckBox = childCheckBox;
        this.context = context;
    }

    @Override
    public int getGroupCount() {
        return groupData.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return childData.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groupData.get(groupPosition).get(G_TEXT).toString();
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childData.get(groupPosition).get(childPosition).get(C_TITLE)
                .toString();
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
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.groupitem, null);
        }
        TextView title = (TextView) view.findViewById(R.id.groupText);
        title.setText(getGroup(groupPosition).toString());
        ImageView image = (ImageView) view.findViewById(R.id.groupBox);
        if (isExpanded)
            image.setBackgroundResource(R.drawable.groupdown);
        else
            image.setBackgroundResource(R.drawable.groupleft);

        return view;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.childitem, null);
            holder = new ViewHolder();
            holder.cTitle = (TextView) convertView
                    .findViewById(R.id.child_title);
            holder.cText = (TextView) convertView.findViewById(R.id.child_text);
            holder.checkBox = (CheckBox) convertView
                    .findViewById(R.id.multiple_checkbox);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.cTitle.setText(childData.get(groupPosition).get(childPosition)
                .get(C_TITLE).toString());
        holder.cText.setText(childData.get(groupPosition).get(childPosition)
                .get(C_TEXT).toString());
        holder.checkBox.setChecked(childCheckBox.get(groupPosition)
                .get(childPosition).get(C_CB));
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    static class ViewHolder {
        TextView cTitle;
        TextView cText;
        CheckBox checkBox;
    }
}
