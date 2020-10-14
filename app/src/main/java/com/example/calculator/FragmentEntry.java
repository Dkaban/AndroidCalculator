package com.example.calculator;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.res.Resources;
import android.widget.ScrollView;
import android.widget.TextView;

//This Fragment is used to display the current calculations with numbers and operators
public class FragmentEntry extends Fragment
{
    //Variables for updating the information
    private ScrollView frag_scrollView;
    private TextView frag_entryInformationText;

    public FragmentEntry()
    {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_entry, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Resources res = getResources();
        frag_entryInformationText = (TextView) getActivity().findViewById(R.id.textView_entry);
        frag_scrollView = (ScrollView) getActivity().findViewById(R.id.entry_scrollView);
    }

    //We want to set the Entry text to be the value we send
    public void updateText(String value)
    {
        //We want to update the displayed information
        frag_entryInformationText.setText(value);

        //This makes sure the entry info is always displayed, but forcing the scrollview down
        frag_scrollView.fullScroll(ScrollView.FOCUS_DOWN);
    }

    public void clearValue()
    {
        //Clear the information displayed to the user
        frag_entryInformationText.setText("");
    }
}