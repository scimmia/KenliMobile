package com.jiayusoft.mobile.kenli.utils.app.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

/**
 * Created by ASUS on 14-4-2.
 */
public class JiayuSpinner extends Spinner implements JiaYuWidget {
    public JiayuSpinner(Context context) {
        super(context);
    }

    public JiayuSpinner(Context context, int mode) {
        super(context, mode);
    }

    public JiayuSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public JiayuSpinner(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public JiayuSpinner(Context context, AttributeSet attrs, int defStyle, int mode) {
        super(context, attrs, defStyle, mode);
    }

    String[] ids;
    @Override
    public  void init(int contentResource,int idResource){
        ids = getResources().getStringArray(idResource);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                contentResource, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.setAdapter(adapter);
    }


    @Override
    public String getResult() {
        int tempID = this.getSelectedItemPosition();
        if (tempID>=0 && tempID<ids.length){
            return ids[tempID];
        }
        return null;
    }

    @Override
    public void setResult(String id) {
        for (int i=0;i<ids.length;i++){
            if (id.equalsIgnoreCase(ids[i])){
                this.setSelection(i);
                break;
            }
        }
    }
}
