package com.example.prayertimes.activity.dua;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.prayertimes.R;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ListAdapter extends BaseExpandableListAdapter {

    private Activity context;
    private Map<String, List<String>> ParentListDuas;
    private List<String> Duas;

    ListAdapter(Activity context, List<String> Duas, Map<String, List<String>> ParentListDuas){

        this.context = context;
        this.ParentListDuas = ParentListDuas;
        this.Duas = Duas;

    }

    //parent
    @Override
    public int getGroupCount() {
        return Duas.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return Duas.get(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        String string = (String) getGroup(groupPosition);

        if(convertView == null){

            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            assert layoutInflater != null;
            convertView = layoutInflater.inflate(R.layout.parent_item_duas, null);

        }

        TextView textView = convertView.findViewById(R.id.textParent);
        textView.setText(string);

        return convertView;

    }


    //child
    @Override
    public int getChildrenCount(int groupPosition) {
        return Objects.requireNonNull(ParentListDuas.get(Duas.get(groupPosition))).size();
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return Objects.requireNonNull(ParentListDuas.get(Duas.get(groupPosition))).get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final String childName = (String) getChild(groupPosition, childPosition);

        LayoutInflater inflater = context.getLayoutInflater();

        if(convertView == null){

            convertView = inflater.inflate(R.layout.child_item_duas, null);

        }

        TextView item = convertView.findViewById(R.id.textChild);
        item.setText(childName);
        return convertView;

    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {

        return true;

    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

}
