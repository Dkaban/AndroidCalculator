package com.example.calculator;
import android.app.Activity;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

//This fragment is used to display all the buttons and attach listeners for functionality
public class FragmentButtons extends Fragment implements View.OnClickListener
{
    //Events for listening to the different types of buttons and features
    public NumberCommunicator nComm;
    public OperatorCommunicator oComm;
    public MemoryCommunicator mComm;

    //Digit Buttons
    Button btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9,btn0;

    //Operator / Extra Buttons
    Button btnDecimal,btnEquals,btnDivide,
            btnMultiply,btnSubtract,btnAdd,btnPositiveNegative;

    //Memory Buttons
    Button btnClear,btnRecall,btnStore, btnBack;

    public FragmentButtons()
    {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_buttons, container, false);
    }

    //We create the activities, making sure to catch exceptions
    @Override
    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);
        try {
            nComm = (NumberCommunicator) activity;
        }
        catch (ClassCastException e)
        {
            throw new ClassCastException(activity.toString()
                    + " must implement FragmentCommunicator");
        }

        try {
            oComm = (OperatorCommunicator) activity;
        }
        catch(ClassCastException e)
        {
            throw new ClassCastException(activity.toString()
                    + " must implement OperatorCommunicator");
        }

        try {
            mComm = (MemoryCommunicator) activity;
        }
        catch(ClassCastException e)
        {
            throw new ClassCastException(activity.toString()
                    + " must implement MemoryCommunicator");
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        //Adding all the digit button listeners
        btn0 = (Button)getActivity().findViewById(R.id.button_0);
        btn0.setOnClickListener(this);
        btn1 = (Button)getActivity().findViewById(R.id.button_1);
        btn1.setOnClickListener(this);
        btn2 = (Button)getActivity().findViewById(R.id.button_2);
        btn2.setOnClickListener(this);
        btn3 = (Button)getActivity().findViewById(R.id.button_3);
        btn3.setOnClickListener(this);
        btn4 = (Button)getActivity().findViewById(R.id.button_4);
        btn4.setOnClickListener(this);
        btn5 = (Button)getActivity().findViewById(R.id.button_5);
        btn5.setOnClickListener(this);
        btn6 = (Button)getActivity().findViewById(R.id.button_6);
        btn6.setOnClickListener(this);
        btn7 = (Button)getActivity().findViewById(R.id.button_7);
        btn7.setOnClickListener(this);
        btn8 = (Button)getActivity().findViewById(R.id.button_8);
        btn8.setOnClickListener(this);
        btn9 = (Button)getActivity().findViewById(R.id.button_9);
        btn9.setOnClickListener(this);

        //Adding all the operator button listeners
        btnAdd = (Button)getActivity().findViewById(R.id.button_addition);
        btnAdd.setOnClickListener(this);
        btnSubtract = (Button)getActivity().findViewById(R.id.button_subtraction);
        btnSubtract.setOnClickListener(this);
        btnDivide = (Button)getActivity().findViewById(R.id.button_divide);
        btnDivide.setOnClickListener(this);
        btnMultiply = (Button)getActivity().findViewById(R.id.button_multiply);
        btnMultiply.setOnClickListener(this);
        btnDecimal = (Button)getActivity().findViewById(R.id.button_decimal);
        btnDecimal.setOnClickListener(this);
        btnPositiveNegative = (Button)getActivity().findViewById(R.id.button_positiveNegative);
        btnPositiveNegative.setOnClickListener(this);
        btnEquals = (Button)getActivity().findViewById(R.id.button_equal);
        btnEquals.setOnClickListener(this);

        //Memory Buttons
        btnBack = (Button)getActivity().findViewById(R.id.button_back);
        btnBack.setOnClickListener(this);
        btnClear = (Button)getActivity().findViewById(R.id.button_clear);
        btnClear.setOnClickListener(this);
        btnRecall = (Button)getActivity().findViewById(R.id.button_memoryRecall);
        btnRecall.setOnClickListener(this);
        btnStore = (Button)getActivity().findViewById(R.id.button_memoryStore);
        btnStore.setOnClickListener(this);
    }

    public void onClick(final View v)
    {
        //We perform these commands when a button is clicked, each button has specific instructions
        switch(v.getId())
        {
            //---------------  Digit buttons
            case R.id.button_0:
                nComm.fragmentNumberActivity("0");
                break;

            case R.id.button_1:
                nComm.fragmentNumberActivity("1");
                break;

            case R.id.button_2:
                nComm.fragmentNumberActivity("2");
                break;

            case R.id.button_3:
                nComm.fragmentNumberActivity("3");
                break;

            case R.id.button_4:
                nComm.fragmentNumberActivity("4");
                break;

            case R.id.button_5:
                nComm.fragmentNumberActivity("5");
                break;

            case R.id.button_6:
                nComm.fragmentNumberActivity("6");
                break;

            case R.id.button_7:
                nComm.fragmentNumberActivity("7");
                break;

            case R.id.button_8:
                nComm.fragmentNumberActivity("8");
                break;

            case R.id.button_9:
                nComm.fragmentNumberActivity("9");
                break;

            //--------------- Operator / Memory Buttons
            case R.id.button_addition:
                oComm.fragmentOperatorActivity("+");
                break;

            case R.id.button_subtraction:
                oComm.fragmentOperatorActivity("-");
                break;

            case R.id.button_divide:
                oComm.fragmentOperatorActivity("/");
                break;

            case R.id.button_multiply:
                oComm.fragmentOperatorActivity("*");
                break;

            case R.id.button_decimal:
                oComm.fragmentOperatorActivity(".");
                break;

            case R.id.button_positiveNegative:
                nComm.fragmentNumberActivity("-(");
                break;

            case R.id.button_equal:
                oComm.fragmentOperatorActivity("=");
                break;

            case R.id.button_clear:
                mComm.fragmentMemoryActivity(MemoryOption.Clear);
                break;

            case R.id.button_back:
                mComm.fragmentMemoryActivity(MemoryOption.Delete);
                break;

            case R.id.button_memoryRecall:
                mComm.fragmentMemoryActivity(MemoryOption.Recall);
                break;

            case R.id.button_memoryStore:
                mComm.fragmentMemoryActivity(MemoryOption.Store);
                break;

            default:
                //Default case to catch extra entries
                break;
        }
    }

    //The Interfaces for handling button inputs (Separated due to functionality required)
    public interface NumberCommunicator
    {
        public void fragmentNumberActivity(String b);
    }

    public interface OperatorCommunicator
    {
        public void fragmentOperatorActivity(String s);
    }

    public interface MemoryCommunicator
    {
        public void fragmentMemoryActivity(MemoryOption option);
    }
}