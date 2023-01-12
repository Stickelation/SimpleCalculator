package com.example.assign1;
/*
Justin Stickel
October 13th 2022

NOTES:
 - Adv mode is not currently functioning, the button was added as per the requirements, please use basic mode for testing purposes
 - Del is the 'correct button' and C is the 'All clear' button
*/
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener
{
    String inputString = "";
    TextView resultDisplay;
    Boolean standardMode = true;
    private ToggleButton toggleButton;
    Double result = null; // For adv mode
    double term1=0.0;
    double term2=0.0;
    int numOps=0;
    String lastOp="";
    String lastPress="";
    Boolean decimalPossible = true;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toggleButton=findViewById(R.id.modeButton);
        toggleButton.setOnCheckedChangeListener(this);
        initTextViews();
    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) // Saves data when rotated
    {
        savedInstanceState.putDouble("termOne", term1);
        savedInstanceState.putDouble("termTwo", term2);
        savedInstanceState.putString("inpString",inputString);
        savedInstanceState.putString("opLast",lastOp);
        savedInstanceState.putString("pressLast",lastPress);
        savedInstanceState.putBoolean("decBool",decimalPossible);
        savedInstanceState.putBoolean("modeStand",standardMode);
        super.onSaveInstanceState(savedInstanceState);
    }
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) // Loads saved data when rotated
    {
        super.onRestoreInstanceState(savedInstanceState);
      term1 = savedInstanceState.getDouble("term1");
      term2 = savedInstanceState.getDouble("term2");
      inputString = savedInstanceState.getString("inpString");
      lastOp = savedInstanceState.getString("opLast");
      lastPress = savedInstanceState.getString("pressLast");
      decimalPossible = savedInstanceState.getBoolean("decBool");
      standardMode = savedInstanceState.getBoolean("modeStand");
    }

    private void initTextViews()
    {
        resultDisplay = (TextView) findViewById(R.id.resultID);
    }
    public void modeClick(View view) //Switches from Basic to Advanced math mode and vice versa (Currently disabled)
    {
        /*
        if (standardMode)
            standardMode = false;
        else
            standardMode = true;
         */
    }

    void addInput (String passed)
    {
        inputString+=passed;
    }
    public void clearClick(View view) // Clears all displayed and working operands
    {
        lastPress="c";
        resultDisplay.setText("");
        inputString="";
        numOps=0;
    }

    public void decimalClick(View view)
    {
        if (standardMode)
        {
            if (decimalPossible) // Checks if a decimal can logically be placed
            {
                lastPress=".";
                addInput(".");
                decimalPossible=false;
            }
          else //Does nothing if it can't be placed (Disabled throwing an error)
            {
                /*
                resultDisplay.setText("ERROR");
                term1=0.0;
                term2=0.0;
                inputString="";
                lastPress="";
                decimalPossible=true;
                */
            }
        }
        else
        addInput(".");
    }

    public void delClick(View view) // Deletes current operand (shown value is untouched)
    {
        if (standardMode)
        {
            lastPress="d";
            if (numOps==1)
            {
                numOps=1;
                inputString="";
            }
        }
        else
        addInput("d");
    }

    public void equalClick(View view)
    {
        if (standardMode)
        {
            decimalPossible=true;
            if (lastPress.equals("^")|lastPress.equals("x")|lastPress.equals("/")|lastPress.equals("-")|lastPress.equals("+"))
            {
                resultDisplay.setText("ERROR");
                term1=0.0;
                term2=0.0;
                inputString="";
                lastPress="";
            }
            else if (inputString.equals(""))
            {
                lastPress="=";
                inputString="0";
                term1=0;
                numOps++;
                lastOp="=";
            }
            else
            {
                numOps++;
                if (numOps==1)
                {
                    term1=Double.parseDouble(inputString);
                    inputString="";
                }
                else
                {
                    lastClick();
                }
            }
        }
        else
        {

        }
        lastOp="=";
    }
    public void powerClick(View view)
    {
        decimalPossible=true;
        if (standardMode)
        {
            if (lastPress.equals("^")|lastPress.equals("x")|lastPress.equals("/")|lastPress.equals("-")|lastPress.equals("+"))
            {
                resultDisplay.setText("ERROR");
                term1=0.0;
                term2=0.0;
                inputString="";
                lastPress="";
            }
            else if (inputString.equals("")) // Checks if nothing was entered first and treats it as a 0 to the power of x
            {
                /*
                lastPress="^";
                inputString="0";
                term1=0;
                numOps++;
                lastOp="^";
                 */
            }
            else
            {
                lastPress="^";
                numOps++;
                if(numOps==1)
                {
                    term1=Double.parseDouble(inputString);
                    inputString="";
                }
                else if (numOps==2)
                {
                    lastClick();
                }
            }
            lastOp="^";
        }
        else
            addInput("^");
    }
    public void multiplyClick(View view)
    {
        decimalPossible=true;
        if (standardMode)
        {
            if (lastPress.equals("^")|lastPress.equals("x")|lastPress.equals("/")|lastPress.equals("-")|lastPress.equals("+"))
            {
                resultDisplay.setText("ERROR");
                term1=0.0;
                term2=0.0;
                inputString="";
                lastPress="";
            }
            else if (inputString.equals("")) // Checks if nothing was entered first and treats it as a 0 multiplication
            {
              /*
                lastPress="x";
                inputString="0";
                term1=0;
                numOps++;
                lastOp="x";
               */
            }
            else
            {
                lastPress="x";
                numOps++;
                if(numOps==1)
                {
                    term1=Double.parseDouble(inputString);
                    inputString="";
                }
                else if (numOps==2)
                {
                    lastClick();
                }
            }
            lastOp="x";
        }
        else
            addInput("/");
    }
    public void divideClick(View view)
    {
        decimalPossible=true;
        if (standardMode)
        {
            if (lastPress.equals("^")|lastPress.equals("x")|lastPress.equals("/")|lastPress.equals("-")|lastPress.equals("+"))
            {
                resultDisplay.setText("ERROR");
                term1=0.0;
                term2=0.0;
                inputString="";
                lastPress="";
            }
            else if (inputString.equals("")) // Checks if nothing was entered first and treats it as a 0 division
            {
              /*  lastPress="/";
                inputString="0";
                term1=0;
                numOps++;
                lastOp="/";
                */
            }
            else
            {
                lastPress="/";
                numOps++;
                if(numOps==1)
                {
                    term1=Double.parseDouble(inputString);
                    inputString="";
                }
                else if (numOps==2)
                {
                    lastClick();
                }
            }
            lastOp="/";
        }
        else
            addInput("/");
    }
    public void plusClick(View view)
    {
        decimalPossible=true;
        lastPress="+";
        if (standardMode)
        {
            if (inputString.equals("")) // Checks if nothing was entered first and treats it as a positive sign
            {

            }
            else
            {
                numOps++;
                if(numOps==1)
                {
                    term1=Double.parseDouble(inputString);
                    inputString="";
                }
                else if (numOps==2)
                {
                    lastClick();
                }
            }
            lastOp="+";
        }
        else
        addInput("+");
    }
    public void minusClick(View view)
    {
        decimalPossible=true;
        lastPress="-";

        if (standardMode)
        {
            if (inputString.equals("-"))
            {
                inputString="";
            }
            else if (inputString.equals("")) // Checks if a negative is being entered first
            {
              //  inputString="-";
            }
            else
            {
                numOps++;
                if(numOps==1)
                {
                    term1=Double.parseDouble(inputString);
                    inputString="";
                }
                else if (numOps==2)
                {
                    lastClick();
                }
            }
            lastOp="-";
        }
        else
        addInput("-");
    }

    void minusExe()
    {
        term2=Double.parseDouble(inputString);
        double temp = term1-term2;
        String tempString = Double.toString(temp);
        resultDisplay.setText(tempString);
        inputString="";
        term1=temp;
    }

    void addExe()
    {
        term2=Double.parseDouble(inputString);
        double temp = term1+term2;
        String tempString = Double.toString(temp);
        resultDisplay.setText(tempString);
        inputString="";
        term1=temp;
    }
    void multiExe()
    {
        term2=Double.parseDouble(inputString);
        double temp = term1*term2;
        String tempString = Double.toString(temp);
        resultDisplay.setText(tempString);
        inputString="";
        term1=temp;
    }
    void divExe()
    {
        term2=Double.parseDouble(inputString);
        if (term2==0.0) // Throws error if trying to divide by 0
        {
            resultDisplay.setText("ERROR");
            term1=0.0;
            term2=0.0;
            inputString="";
            lastPress="";
        }
        else
        {
            double temp = term1/term2;
            String tempString = Double.toString(temp);
            resultDisplay.setText(tempString);
            inputString="";
            term1=temp;
        }
    }

    void powExe()
    {
        term2=Double.parseDouble(inputString);
        double temp = Math.pow(term1,term2);
        String tempString = Double.toString(temp);
        resultDisplay.setText(tempString);
        inputString="";
        term1=temp;
    }

    void equalExe() // added for consistency/clarity
    {
        double temp = term1;
        String tempString = Double.toString(temp);
        resultDisplay.setText(tempString);
        inputString="";
        term1=temp;
    }

    void lastClick()
    {
        if (lastOp.equals("-"))
        {
            minusExe();
            numOps=1;
        }
        else if (lastOp.equals("+"))
        {
            addExe();
            numOps=1;
        }
        else if (lastOp.equals("x"))
        {
            multiExe();
            numOps=1;
        }
        else if (lastOp.equals("/"))
        {
            divExe();
            numOps=1;
        }
        else if (lastOp.equals("^"))
        {
            powExe();
            numOps=1;
        }
        else if (lastOp.equals("="))
        {
            equalExe();
            numOps=0;
        }

    }
    public void zeroClick(View view)
    {
        addInput("0");
        lastPress="0";
    }
    public void oneClick(View view)
    {
        addInput("1");
        lastPress="1";
    }

    public void twoClick(View view)
    {
        addInput("2");
        lastPress="2";
    }

    public void threeClick(View view)
    {
        addInput("3");
        lastPress="3";
    }

    public void fourClick(View view)
    {
        addInput("4");
        lastPress="4";
    }

    public void fiveClick(View view)
    {
        addInput("5");
        lastPress="5";
    }

    public void sixClick(View view)
    {
        addInput("6");
        lastPress="6";
    }
    public void sevenClick(View view)
    {
        addInput("7");
        lastPress="7";
    }
    public void eightClick(View view)
    {
        addInput("8");
        lastPress="8";
    }

    public void nineClick(View view)
    {
        addInput("9");
        lastPress="9";
    }


    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) // For toggle button
    {
            if (compoundButton.isChecked()) // Determines if the user has switched the mode to advanced or not
            {
                standardMode=false;
            }
            else
            {
                standardMode=true;
            }

    }
}