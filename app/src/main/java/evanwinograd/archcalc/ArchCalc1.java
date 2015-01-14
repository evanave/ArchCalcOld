package evanwinograd.archcalc;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.os.Vibrator;
import java.util.LinkedList;
import java.util.List;

public class ArchCalc1 extends Activity implements OnItemSelectedListener {

    String Operation;
    String Steps;
    String Steps1;
    String Steps2;
    int FinalTotalInches;
    int MDfactor;
    int TempTotalInches;
    int TempEighths;
    int TempTotalEighths;
    int FinalEighths;
    int FinalTotalEighths;
    int LastTotalEighths;
    int GetFeet;
    int GetInches;
    int GetEighths;
    int FF;
    int FI;
    int F8;
    int TotalF8;
    int TF;
    int TI;
    int T8;
    int TotalT8;
    private Vibrator myVib;
    int EvenOdd;
    String LastTotal;
    String TempSteps;
    int CalcStep;
    int CalcNumber;
    List<String[]> HistList;
    String[][][] HistoryArray = new String[5][50][6];

    /**
     * Each row is an operation
     * 0th column: Step #
     * First column: Operation
     * Second column: Feet input
     * Third column: Inch Input
     * Fourth column: # of eighths
     * Fifth column: factor if multiply or divide
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arch_calc1);
        FinalTotalInches = 0;
        MDfactor=1;
        CalcNumber = 0;
        CalcStep = 0;
        Steps = "";
        Steps1 = "Steps";
        Steps2 = "Total";
        EvenOdd = 0;
        TempTotalInches = 0;
        TempEighths = 0;
        FinalEighths = 0;
        LastTotalEighths = 0;
        LastTotal = "";
        HistoryArray[CalcNumber][CalcStep][0] = "0";
        HistoryArray[CalcNumber][CalcStep][2] = "0";
        HistoryArray[CalcNumber][CalcStep][3] = "0";

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);


        List<String[]> HistList = new LinkedList<String[]>();


        TotalReset(null);

        add(null);
        //((TextView) findViewById(R.id.StepText)).setText(Steps);
        ((TextView) findViewById(R.id.StepText)).setText(Steps1);
        ((TextView) findViewById(R.id.StepTextFinal)).setText(Steps2);
        FillTotalSpinner();
        myVib = (Vibrator) this.getSystemService(VIBRATOR_SERVICE);


    }

    // Fill options for spinners
    public void FillTotalSpinner() {
        Spinner MorDFinal = (Spinner) findViewById(R.id.TotalSpinner); 
        Spinner MorDTemp = (Spinner) findViewById(R.id.TempSpinner);
        String[] myOptions = getResources().getStringArray(R.array.total_spinner_array);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, myOptions);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        MorDFinal.setAdapter(dataAdapter);
        MorDTemp.setAdapter(dataAdapter);
        MorDFinal.setOnItemSelectedListener(this);
        MorDTemp.setOnItemSelectedListener(this);
    }

    // Switch for showing history / going to main page
    public void ShowHistory(View view) {
        LinearLayout layoutTop = (LinearLayout) findViewById(R.id.TopLayout);
        LinearLayout layoutBottom = (LinearLayout) findViewById(R.id.BottomHalf);


        if (EvenOdd == 0) {

            showView(layoutTop);
            // swapView(layoutTop, layoutBottom);
            EvenOdd = 1;
        } else {
            hideView(layoutTop);

            // swapView(layoutBottom, layoutTop);
            EvenOdd = 0;
        }

    }

    /**

    private void swapView(final View view, final View view2) {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.layout_slide_down);
        Animation animation2 = AnimationUtils.loadAnimation(this, R.anim.layout_slide_up);

        //use this to make it longer:  animation.setDuration(1000);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.setVisibility(View.VISIBLE);
                view2.setVisibility(View.GONE);
            }
        });
        AnimationSet set1 = new AnimationSet(true);
        view.startAnimation(animation2);
        view.startAnimation(animation);

    }
   **/

    private void hideView(final View view) {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.layout_slide_up);

        //use this to make it longer:  animation.setDuration(1000);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.setVisibility(View.GONE);

            }
        });
        view.startAnimation(animation);
    }

    private void showView(final View view) {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.layout_slide_down);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.setVisibility(View.VISIBLE);
            }
        });
        view.startAnimation(animation);
    }

    /**
     *
     * Responds to spinner selection - multiply or divide the input line or total line.
     */
    public void onItemSelected(AdapterView<?> parent, View arg1, int pos,
                               long arg3) {
        Spinner MorDFinal = (Spinner) findViewById(R.id.TotalSpinner);
        Spinner MorDTemp = (Spinner) findViewById(R.id.TempSpinner);
        String thefunction = parent.getItemAtPosition(pos).toString();
        int p1 = parent.getId();
        int p2 = R.id.TotalSpinner;
        int p3 = R.id.TempSpinner;
        if (thefunction.length() >= 2) {
            if (parent.getId() == R.id.TotalSpinner) {
                if (thefunction.substring(0, 1).matches("[2-9]")) {
                    // This is multiplying the final
                    MDfactor=Integer.parseInt(thefunction.substring(0, 1));
                    MultiplyTotal(null, MDfactor);
                    MorDFinal.setSelection(0);
                } else {
                    MDfactor=Integer.parseInt(thefunction.substring(2));

                    DivideTotal(null, MDfactor);
                    MorDFinal.setSelection(0);
                }
            } else {
                if (thefunction.substring(0, 1).matches("[2-9]")) {
                    // This is multiplying the final
                    MultiplyTemp(null, Integer.parseInt(thefunction.substring(0, 1)));
                    MorDTemp.setSelection(0);
                } else {
                    DivideTemp(null, Integer.parseInt(thefunction.substring(2)));
                    MorDTemp.setSelection(0);
                }

            }

        }

        // TODO Auto-generated method stub

    }

    public void onNothingSelected(AdapterView parent) {
        // TODO Auto-generated method stub

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_arch_calc1, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        // if (id == R.id.action_settings) {
        //     return true;
        // }

        if (id == R.id.action_help) {
            ((FrameLayout) findViewById(R.id.HelpOverlay)).setVisibility(View.VISIBLE);
            return true;
        }

        if (id == R.id.action_FAQ) {

            Intent loadFAQ = new Intent(ArchCalc1.this, FAQ.class);
            startActivity(loadFAQ);
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    public void HideHelp(View view) {
        ((FrameLayout) findViewById(R.id.HelpOverlay)).setVisibility(View.INVISIBLE);
    }

    public void ClickedFeet(View view) {  // Changes the temporary feet value
        Button F = (Button) view;
        TextView FValue = (TextView) findViewById(R.id.FeetValue);
        if (Integer.parseInt(FValue.getText().toString()) == 0) {
            FValue.setText(F.getText().toString());
        } else {
            if (FValue.getText().toString().length() < 7) {
                FValue.setText(FValue.getText().toString() + F.getText().toString());
            }
        }
        Vibrate();
    }

    public void Vibrate() {
        myVib.vibrate(100);
    }

    public void ClickedInches(View view) { // Changes the temporary inches value
        Button I = (Button) view;
        TextView IValue = (TextView) findViewById(R.id.InchValue);
        if (Integer.parseInt(IValue.getText().toString()) == 0) {
            IValue.setText(I.getText().toString());
        } else {
            if (IValue.getText().toString().length() < 7) {
                IValue.setText(IValue.getText().toString() + I.getText().toString());
            }
        }
        Vibrate();
    }

    public void ClickedFrac(View view) { //changes the temporary fraction
        Button Frac = (Button) view;
        TextView TheFrac = (TextView) findViewById(view.getId());
        TempEighths = CalcEighths(TheFrac.getText().toString());

        TextView Fraction = (TextView) findViewById(R.id.FracValue);
        Fraction.setText(TheFrac.getText().toString());
        Vibrate();
    }
    /*
    Returns the number of eights from a fraction
     */
    public int CalcEighths(String TheFraction) {  //Calculates the number of eighths with a fraction input
        if (TheFraction == "") {
            return 0;
        }
        String num = TheFraction.substring(0, 1);
        String den = TheFraction.substring(2);

        int denom = Integer.parseInt(den);
        int numer = Integer.parseInt(num);

        return ((8 / denom) * numer);

    }

    public void ResetFrac(View view) { // Clear temporary fraction
        TextView FracValue = (TextView) findViewById(R.id.FracValue);
        TempEighths = 0;
        FracValue.setText("");
    }

    public void ResetFeet(View view) { // Clear temporary feet
        TextView FValue = (TextView) findViewById(R.id.FeetValue);
        FValue.setText("0");
    }

    public void ResetInches(View view) {  // Clear temporary inches
        TextView IValue = (TextView) findViewById(R.id.InchValue);
        IValue.setText("0");
    }
     /*
    First step when an operation is pressed: gets Temp and Final values,
      */
    public void Calculate() {

        getFinalValues(); // sets values FF, FI, F8, and TotalF8
        getTempValues(); // sets values TF, TI, T8, and TotalT8

        updateHistoryArray();


        if (((TextView) findViewById(R.id.FinalSign)).getText().toString() == "") {
            FinalTotalEighths = TotalF8;
        } else {
            FinalTotalEighths = -TotalF8;
        }

        TempTotalEighths = TF + TI + T8;
             /*  Redundant code - done in Breakdown
                    if (Operation == "add") {
                        LastTotalEighths = TotalT8 + FinalTotalEighths;
                    } else {
                        LastTotalEighths = FinalTotalEighths - TotalT8;
                    }
                    */
    }

    private void updateHistoryArray() {
        /**
         Each row is an operation
         0th column: Step #
         First column: Operation
         Second column: Feet input
         Third column: Inch Input
         Fourth column: # of eighths
         Fifth column: factor if multiply or divide

        if (Operation == "add" || Operation == "subtract") {
           //String[][] HistoryArray[CalcNumber][CalcStep] =  {Integer.toString(CalcStep), Operation, Integer.toString(TF), Integer.toString(TI), Integer.toString(T8), ""};
            HistList.add(new String[] {Integer.toString(CalcStep), Operation, Integer.toString(TF), Integer.toString(TI), Integer.toString(T8), ""});
            ArrayAdapter<String[]> adapter = new ArrayAdapter<String[]>(this, android.R.layout.simple_list_item_2, android.R.id.text1, HistList) {

                @Override
                public View getView(int position, View convertView, ViewGroup parent){
                    View view = super.getView(position, convertView, parent);

                    String[] entry = HistList.get(position);
                    TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                    TextView text2 = (TextView) view.findViewById(android.R.id.text2);

                    text1.setText(entry[0]);
                    text2.setText(entry[1]);

                    return view;
                }
            };
            setListAdapter(adapter);
            updateHistoryArray2(CalcStep,Operation,TF,TI,T8,1);
        } else {
            updateHistoryArray2(CalcStep,Operation,TF,TI,T8,MDfactor);
        }
         **/
    }

    public void updateHistoryArray2(int step, String oper, int TempF, int TempI, int Temp8, int factor){
        HistoryArray[CalcNumber][CalcStep][0] = Integer.toString(CalcStep);
        HistoryArray[CalcNumber][CalcStep][1] = oper;
        HistoryArray[CalcNumber][CalcStep][2] = Integer.toString(TempF);
        HistoryArray[CalcNumber][CalcStep][3] = Integer.toString(TempI);
        HistoryArray[CalcNumber][CalcStep][4] = Integer.toString(Temp8);
        HistoryArray[CalcNumber][CalcStep][5] = Integer.toString(factor);
    }
    /*
    Defines the total value before breaking down into components and setting textviews.
     */
    public void Breakdown() {

        int TheTotal = 0;
        if (Operation == "add") {
            TheTotal = FinalTotalEighths + TotalT8;
        } else {
            TheTotal = FinalTotalEighths - TotalT8;
        }

        // TheTotal now represents what the answer will be.

        if (TheTotal >= 0) {
            ((TextView) findViewById(R.id.FinalSign)).setText("");
        } else {
            ((TextView) findViewById(R.id.FinalSign)).setText("-");
        }

        TheTotal = Math.abs(TheTotal);

        BreakDown2(TheTotal);
        setFinalValues();
    }
    /*
    defines LastTotal = the total that is being modified by the calculation
     */
    public void getLastTotal() {
        LastTotal = ((TextView) findViewById(R.id.FinalSign)).getText().toString() + ((TextView) findViewById(R.id.FinalFeetValue)).getText().toString() + "\' " + ((TextView) findViewById(R.id.FinalInchValue)).getText().toString() + " " + ((TextView) findViewById(R.id.FinalFracValue)).getText().toString() + "\" ";
    }
    /*
    Adds steps to history
     */
    public void updateHistory() {
        Steps = Steps + "\n" + TempSteps + "      Total: " + LastTotal;
        Steps1 = Steps1 + "\n" + TempSteps;
        Steps2 = Steps2 + "\n" + LastTotal;


        //   ((TextView) findViewById(R.id.StepText)).setText(Steps.toString());
        ((TextView) findViewById(R.id.StepText)).setText(Steps1.toString());
        ((TextView) findViewById(R.id.StepTextFinal)).setText(Steps2.toString());
    }
        /*
        Returns the remaining fraction in reduced form
         */
    public String ReduceFrac(int num8s) {
        String FinalF = "";
        if (num8s % 2 == 1) {
            FinalF = Integer.toString(num8s) + "/8";
        } else if (num8s == 2) {
            FinalF = "1/4";
        } else if (num8s == 4) {
            FinalF = "1/2";
        } else if (num8s == 6) {
            FinalF = "3/4";
        }
        return FinalF;
    }
    /*
 Equals button pressed - performs function
  */
    public void ClickedEquals(View view) {
        Calculate();
        Breakdown();
        TempSteps = ((TextView) findViewById(R.id.TempSign)).getText().toString() + ((TextView) findViewById(R.id.FeetValue)).getText().toString() + "\' " + ((TextView) findViewById(R.id.InchValue)).getText().toString() + " " + ((TextView) findViewById(R.id.FracValue)).getText().toString() + "\" ";
        if (TotalT8 != 0) {
            getLastTotal(); // updates LastTotal
            updateHistory();
        }
        ResetFeet(null);
        ResetInches(null);
        ResetFrac(null);

    }
    /*
    Add button pressed - performs function and changes the sign to "+"
     */
    public void add(View view) {
        ClickedEquals(null);
        Operation = "add";
        Button addBtn = (Button) findViewById(R.id.add);
        Button subBtn = (Button) findViewById(R.id.subtract);
        ((TextView) findViewById(R.id.TempSign)).setText("+");
        addBtn.setSelected(true);
        subBtn.setSelected(false);

    }

    /*
    Subtract button pressed - performs function and changes the sign to "-"
     */
    public void subtract(View view) {
        ClickedEquals(null);
        Operation = "subtract";
        Button addBtn = (Button) findViewById(R.id.add);
        Button subBtn = (Button) findViewById(R.id.subtract);
        ((TextView) findViewById(R.id.TempSign)).setText("-");
        addBtn.setSelected(false);
        subBtn.setSelected(true);
    }

    /*
    All clear button has been pressed - resets calculator
     */
    public void TotalReset(View view) {
        ResetTemp(null);
        ((TextView) findViewById(R.id.StepText)).setText("0");
        ((TextView) findViewById(R.id.FinalFeetValue)).setText("0");
        ((TextView) findViewById(R.id.FinalInchValue)).setText("0");
        ((TextView) findViewById(R.id.FinalFracValue)).setText("");
        ((TextView) findViewById(R.id.FinalSign)).setText("");
        ((TextView) findViewById(R.id.InchSummary)).setText("(0" + "\"" + ")");
        Steps = "";
        Steps1 = "Steps" + "\n";
        Steps2 = "Total" + "\n0\' 0\"";
        ((TextView) findViewById(R.id.StepText)).setText(Steps1);
        ((TextView) findViewById(R.id.StepTextFinal)).setText(Steps2);
        add(null);

    }
    /*
    Clears the input line
     */
    public void ResetTemp(View view) {
        ResetFeet(null);
        ResetInches(null);
        ResetFrac(null);
    }
    /*
    Used to divide value for the final line - also adds to history
     */
    public void DivideTotal(View view, int thefactor) {
        getFinalValues();
        BreakDown2(TotalF8 / thefactor);
        setFinalValues();
        getLastTotal();
        TempSteps = "/" + thefactor + "  ";
        updateHistory();
    }
    /*
    Used to multiply values for the final line - also adds to history
     */
    public void MultiplyTotal(View view, int thefactor) {
        getFinalValues();
        BreakDown2(TotalF8 * thefactor);
        setFinalValues();
        getLastTotal();
        TempSteps = "x" + thefactor + "  ";
        updateHistory();


    }
    /*
    Used to divide values for the temp line - calls BreakDown2
     */
    public void DivideTemp(View view, int thefactor) {
        getTempValues();
        BreakDown2(TotalT8 / thefactor);
        setTempValues();
    }
    /*
    Used to multiply values for the temp line - calls BreakDown2
     */
    public void MultiplyTemp(View view, int thefactor) {
        getTempValues();
        BreakDown2(TotalT8 * thefactor);
        setTempValues();
    }
    /*
    Given an input of eighths, break down into feet (GetFeet), inches(GetInches), and eighths(GetEighths)
     */
    public void BreakDown2(int Eighths) {
        //  getFinalValues();
        GetFeet = Eighths / 96;
        GetInches = (Eighths - 96 * GetFeet) / 8;
        GetEighths = (Eighths - GetFeet * 96 - GetInches * 8);


    }
/*    old function, not used
    public String EighthsToString(boolean ShowPlus, String TempOrFinal) {

        if (TempOrFinal == "Final") {
            BreakDown2(TotalF8);
        } else {
            BreakDown2(TotalT8);
        }

        String Sign = "";
        if (GetFeet + GetInches + GetEighths >= 0) {
            if (ShowPlus == true) {
                Sign = "+";
            }
        } else {
            Sign = "-";
        }
        return Sign + Integer.toString(GetFeet) + "\' " + Integer.toString(GetInches) + " " + ReduceFrac(GetEighths) + "\" ";

    }
 */

    /*
    Read the final line and set the values FF (Final Feet), FI (FinalInches), F8 (FinalEighths), and TotalF8 (total final value in eighths)
     */
    public void getFinalValues() {
        TextView FFValue = (TextView) findViewById(R.id.FinalFeetValue);
        TextView FIValue = (TextView) findViewById(R.id.FinalInchValue);
        TextView FinalFrac = (TextView) findViewById(R.id.FinalFracValue);

        FF = Integer.parseInt(FFValue.getText().toString()) * 96;
        FI = Integer.parseInt(FIValue.getText().toString()) * 8;
        F8 = CalcEighths(FinalFrac.getText().toString());
        TotalF8 = FF + FI + F8;
    }

    /*
    Read the input line and set the values TF (Temp Feet), TI (Temp Inches), T8 (Temp Eighths), and TotalT8 (total input line value in eighths)
     */
    public void getTempValues() {
        TextView FValue = (TextView) findViewById(R.id.FeetValue);
        TextView IValue = (TextView) findViewById(R.id.InchValue);
        TextView TheFrac = (TextView) findViewById(R.id.FracValue);

        TF = Integer.parseInt(FValue.getText().toString()) * 96;
        TI = Integer.parseInt(IValue.getText().toString()) * 8;
        T8 = CalcEighths(TheFrac.getText().toString());
        TotalT8 = TF + TI + T8;
    }

    /*
    Used for the history page - building string for the cumulative total
     */
    public void setFinalValues() {
        ((TextView) findViewById(R.id.FinalFeetValue)).setText(Integer.toString(GetFeet));
        ((TextView) findViewById(R.id.FinalInchValue)).setText(Integer.toString(GetInches));
        String Frac = ReduceFrac(GetEighths);
        ((TextView) findViewById(R.id.FinalFracValue)).setText(Frac);
        TextView FinalFrac = (TextView) findViewById(R.id.FinalFracValue);
        //   String LastFraction = "";
        int FullInches = GetFeet * 12 + GetInches;
        if (GetEighths == 0) {
            FinalFrac.setText("");
            ((TextView) findViewById(R.id.InchSummary)).setText("(" + ((TextView) findViewById(R.id.FinalSign)).getText().toString() + FullInches + "\")");
        } else {
            FinalFrac.setText(ReduceFrac(GetEighths));
            ((TextView) findViewById(R.id.InchSummary)).setText("(" + ((TextView) findViewById(R.id.FinalSign)).getText().toString() + FullInches + " " + ReduceFrac(GetEighths) + "\")");
        }
    }

    /*
    Used for the history page - building string for the input value
     */
    public void setTempValues() {
        ((TextView) findViewById(R.id.FeetValue)).setText(Integer.toString(GetFeet));
        ((TextView) findViewById(R.id.InchValue)).setText(Integer.toString(GetInches));
        String Frac = ReduceFrac(GetEighths);
        ((TextView) findViewById(R.id.FracValue)).setText(Frac);
        TextView TempFrac = (TextView) findViewById(R.id.FracValue);
        //   String LastFraction = "";
        int FullInches = GetFeet * 12 + GetInches;
        if (GetEighths == 0) {
            TempFrac.setText("");
        } else {
            TempFrac.setText(ReduceFrac(GetEighths));
        }
    }



}
