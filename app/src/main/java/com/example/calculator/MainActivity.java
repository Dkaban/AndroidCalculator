package com.example.calculator;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import android.os.Bundle;
import android.widget.Toast;

//This enumeration helps control memory functionality
enum MemoryOption
{
    Clear,
    Store,
    Recall,
    Delete
}

public class MainActivity extends AppCompatActivity implements FragmentButtons.NumberCommunicator, FragmentButtons.OperatorCommunicator, FragmentButtons.MemoryCommunicator
{
    //Fragment Variables
    FragmentManager fragmentManager;
    FragmentEntry fragmentEntry;
    Calculation fragmentCalculation;

    //Variables to keep track of calculations and restrictions on user input
    private String lastEntry = "";
    private String lastOperator = "";
    private String currentEntry = "";
    private double result = 0;
    private String storedEntry = "";
    private int digitCount = 0;
    private final int DIGIT_MAX = 15;
    private int decimalDigitCount = 0;
    private final int DECIMAL_DIGIT_MAX = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toast toast = Toast.makeText(getApplicationContext(),"Welcome to the Calculator", Toast.LENGTH_LONG);
        toast.show();

        //We need to be able to access the different fragments for updating purposes
        fragmentManager = getSupportFragmentManager();
        fragmentEntry = (FragmentEntry) fragmentManager.findFragmentById(R.id.fragment_entry);
        fragmentCalculation = (Calculation) fragmentManager.findFragmentById(R.id.fragment_calculation);
    }

    public void fragmentNumberActivity(String b)
    {
        //We want to check if a negative sign was pressed and the last entry was not a decimal.
        if(b.equals("-(") && !lastEntry.equals("."))
        {
            //We only want to allow a negative if the last entry was an operator
            if(lastEntry.equals("+") || lastEntry.equals("-") || lastEntry.equals("*")
                    || lastEntry.equals("/") || currentEntry.equals(""))
            {
                currentEntry += b;
                lastEntry = b;
                fragmentEntry.updateText(currentEntry);
            }
        }
        else
        {
            //Can't have more than 5 decimal places
            if(decimalDigitCount >= DECIMAL_DIGIT_MAX)
            {
                Toast toast = Toast.makeText(getApplicationContext(),"Can't have more than " + DECIMAL_DIGIT_MAX + " digits after the decimal.", Toast.LENGTH_SHORT);
                toast.show();
                return;
            }

            //Can't have more than DIGIT_MAX digits
            if(digitCount >= DIGIT_MAX)
            {
                Toast toast = Toast.makeText(getApplicationContext(),"Can't have more than " + DIGIT_MAX + " digits in a number.", Toast.LENGTH_SHORT);
                toast.show();
                return;
            }

            if(lastOperator.equals("."))
            {
                decimalDigitCount++;
            }

            digitCount++;
            currentEntry += b;
            lastEntry = b;
            fragmentEntry.updateText(currentEntry);

            //Calculate the current value
            updateCalculation();
        }
    }

    public void fragmentOperatorActivity(String s)
    {
        //We do not want a number to have multiple decimal points
        if(lastOperator.equals(".") && s.equals(".")) return;

        //We don't want the user to enter 2 operators back to back
        if(!lastEntry.equals("+") && !lastEntry.equals("-") && !lastEntry.equals("*")
                && !lastEntry.equals("/") && !lastEntry.equals("."))
        {
            //If equals is pressed, we want to display final result
            if(!s.equals("="))
            {
                lastEntry = s;
                lastOperator = s;
                currentEntry += s;
                fragmentEntry.updateText(currentEntry);

                //Need to make sure we reset the decimal digit counter
                decimalDigitCount = 0;
                digitCount = 0;
            }
            else
            {
                displayEquals();
            }
        }
    }

    public void fragmentMemoryActivity(MemoryOption option)
    {
        //We use this to determine which memory option is used
        switch(option)
        {
            case Clear:
                clearEntry();
                break;

            case Recall:
                recallMemoryEntry();
                break;

            case Store:
                storedEntry = currentEntry;
                break;

            case Delete:
                deleteLastEntry();
                break;

            default:
                break;
        }
    }

    private void computeCalculation(String s)
    {
        String parsedDouble = "";
        String operator = "";
        double aggregate = 0;
        boolean negative = false;

        //We want to loop through the entire string
        //It's a bit inefficient, but do the calculation each time fully
        for (int i = 0; i < s.length(); i++)
        {
            char c = s.charAt(i);

            //If the Char is a number or it's the decimal operator, we just combine it with the number essentially
            if (Character.isDigit(c) || c.equals('.'))
            {
                parsedDouble += c;
            }

            //Since we're using -( as a negative symbol, we want to * -1 to the number when we detect a (
            //This would cause problems with brackets later, but for now it works.
            if(c == '(')
            {
                //Set as a Negative
                negative = true;
            }

            //We need to verify that we're looking at an operator, or else we want to skip it
            if ((!Character.isDigit(c) || i == s.length()-1) && !c.equals('.') && !c.equals('(') && !parsedDouble.equals(""))
            {
                double parsed = Double.parseDouble(parsedDouble);

                if(negative) parsed *= -1.0f;

                if (operator == "")
                {
                    aggregate = parsed;
                }
                else
                {
                    if (operator.equals("+"))
                    {
                        aggregate += parsed;
                    }
                    else if (operator.equals("-"))
                    {
                        aggregate -= parsed;
                    }
                    else if(operator.equals("*"))
                    {
                        aggregate *= parsed;
                    }
                    else if(operator.equals("/"))
                    {
                        //Checks to see if the user is trying to divide by 0
                        if(parsed != 0)
                            aggregate /= parsed;

                    }
                }
                parsedDouble ="";
                operator = ""+c;
                negative = false;
            }
        }

        result = aggregate;
    }

    //We want to change the main entry text to be the Result when the user presses =
    private void displayEquals()
    {
        computeCalculation(currentEntry);
        fragmentEntry.updateText(Double.toString(result));
    }

    //This handles the deleting functionality
    private void deleteLastEntry()
    {
        //There's no point trying to delete something if there is no values
        if(currentEntry.length() > 0)
        {
            //This does a check to see if there is a negative, if so, we want to delete the ( and the -
            if(currentEntry.charAt(currentEntry.length()-1).equals('('))
            {
                currentEntry = currentEntry.substring(0,currentEntry.length()-2);
            }
            else
            {
                currentEntry = currentEntry.substring(0,currentEntry.length()-1);
            }
            fragmentEntry.updateText(currentEntry);
        }
    }

    private void updateCalculation()
    {
        //Do the calculation then update the running calculation text
        computeCalculation(currentEntry);
        fragmentCalculation.updateText(Double.toString(result));
    }

    //TODO: Clean up this function (there has to be a way to clear easier)
    private void clearEntry()
    {
        //We want to reset all the values back to empty strings or 0
        lastEntry = "";
        lastOperator = "";
        fragmentEntry.clearValue();
        currentEntry = "";
        result = 0;
        fragmentCalculation.clearText();
        decimalDigitCount = 0;
        digitCount = 0;
    }

    private void recallMemoryEntry()
    {
        //Check to see if we have stored anything, if not no need to recall
        if(storedEntry.length() > 0)
        {
            //If the last entry was an operator, allow placing the memory item
            if(lastEntry.equals("+") || lastEntry.equals("-") || lastEntry.equals("*")
                    || lastEntry.equals("/"))
            {
                currentEntry += storedEntry;
                fragmentEntry.updateText(currentEntry);
                updateCalculation();
            }
            else if(currentEntry.equals("")) //If we haven't entered anything yet, recall the data
            {
                currentEntry = storedEntry;
                fragmentEntry.updateText(currentEntry);
                updateCalculation();
            }
        }
    }
}
