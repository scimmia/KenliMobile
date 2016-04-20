package com.jiayusoft.mobile.kenli.utils.app.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import com.jiayusoft.mobile.kenli.utils.eventbus.BusProvider;
import com.jiayusoft.mobile.kenli.utils.eventbus.event.SingleChooseEvent;
import org.apache.commons.lang3.ArrayUtils;

/**
 * Created by Hi on 2016-1-17.
 */
public class SignleChooseDialogFragment extends DialogFragment {
    public static SignleChooseDialogFragment newInstance(int viewID, String selectedItem, String details) {
        SignleChooseDialogFragment frag = new SignleChooseDialogFragment();
        Bundle args = new Bundle();
        args.putInt("viewID", viewID);
        args.putString("selectedItem", selectedItem);
        args.putString("details", details);
        frag.setArguments(args);
        return frag;
    }


    private String[] mItems;
    private String[] mIDs;
    private int mViewID;
    private String mSelectedItem;
    private String mDetails;
    private String mDialogTitle;
    private int mSelectedPosition;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mViewID = getArguments().getInt("viewID",0);
        mSelectedItem = getArguments().getString("selectedItem");
        mDetails = getArguments().getString("details");
        initDatas();


        return new AlertDialog.Builder(getActivity()).setTitle(mDialogTitle)
                .setSingleChoiceItems(mItems, mSelectedPosition, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int selectedPosition) {
                        BusProvider.getInstance().post(new SingleChooseEvent(mViewID,mItems[selectedPosition], mIDs[selectedPosition]));
                        dialog.dismiss();
                    }
                })
                .create();
    }

    void initDatas(){
        mIDs = getArguments().getStringArray("codes");
        mItems = getArguments().getStringArray("names");
        mSelectedPosition = ArrayUtils.indexOf(mItems, mSelectedItem);
    }
}
