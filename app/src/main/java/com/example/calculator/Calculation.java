package com.example.calculator;
import android.content.res.Resources;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.math.BigDecimal;
import java.text.DecimalFormat;

//This Fragment handles the Running Calculation
public class Calculation extends Fragment
{
    DecimalFormat form;
    private TextView frag_calculation;
    public Calculation()
    {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_calculation, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Resources res = getResources();
        frag_calculation = (TextView) getActivity().findViewById(R.id.textView_calculation);
        form = new DecimalFormat("#.00000");
    }

    public void updateText(String value)
    {
        System.out.println("Value: " + value);
        try {
            //frag_calculation.setText(String.format("%.5f",Double.parseDouble(value)));
            BigDecimal bd = new BigDecimal(value);
            frag_calculation.setText(bd.toString());
        }
        catch(Exception e)
        {
            //Could not set Calculation text because parsing failed
        }
    }

    public void clearText()
    {
        frag_calculation.setText("");
    }
}