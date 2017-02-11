package com.example.nero.calculatorapp;

import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.*;
import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.Queue;

public class MainActivity extends AppCompatActivity {

    private Button clearButton, memoryClear, memoryStore, deleteLastNumberButton,reduceMemoryButton, addToMemoryButton, percentageButton, sevenButton, eightButton, nineButton, divideOperationButton, fourButton, fiveButton, sixButton, multiplicationButton, oneButton, twoButton, threeButton, subtractButton, decimalButton, zeroButton, equalButton, plusButton, plusMinus;
    private TextView currentTextField, historyText;
    private String textEnteredByUser = "";
    private double result;
    private boolean isFirstDigit = true, openSwitchOperatorForCalculation = false;
    private String operator = "";
    private double number;
    private String items = "";
    private Queue<String> storeData;
    private String divideByZeroError = "Cannot Divide By 0";
    private String memoryVariable = "";
    private boolean isMemoryCreated = false;
    private String resultData = "";
    DecimalFormat decimalFormat = new DecimalFormat("#.####");
    boolean memoryToAdd = true;
    int numberOfLines = 1;
    String lastElement = "";
    String saveHistory = "";
    final String dataForHistory = "dataForHistory";
    String saveResult = "";
    final String dataForResult = "dataForResult";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        clearButton = (Button) findViewById(R.id.clearButton);
        memoryClear = (Button) findViewById(R.id.memoryClear);
        memoryStore = (Button) findViewById(R.id.memoryCreator);
        deleteLastNumberButton = (Button) findViewById(R.id.deleteLastItem);
        reduceMemoryButton = (Button) findViewById(R.id.memoryReductorButton);
        addToMemoryButton = (Button) findViewById(R.id.memoryIncrementor);
        percentageButton = (Button) findViewById(R.id.percentageButton);
        sevenButton = (Button) findViewById(R.id.numberSeven);
        eightButton = (Button) findViewById(R.id.numberEight);
        nineButton = (Button) findViewById(R.id.numberNine);
        divideOperationButton = (Button) findViewById(R.id.divideButton);
        fourButton = (Button) findViewById(R.id.numberFour);
        fiveButton = (Button) findViewById(R.id.numberFive);
        sixButton = (Button) findViewById(R.id.numberSix);
        multiplicationButton = (Button) findViewById(R.id.multiplicationButton);
        oneButton = (Button) findViewById(R.id.clearButton);
        twoButton = (Button) findViewById(R.id.numberTwo);
        threeButton = (Button) findViewById(R.id.clearButton);
        subtractButton = (Button) findViewById(R.id.subtractButton);
        decimalButton = (Button) findViewById(R.id.decimalButton);
        zeroButton = (Button) findViewById(R.id.buttonZero);
        equalButton = (Button) findViewById(R.id.equalButton);
        plusButton = (Button) findViewById(R.id.plusButton);
        plusMinus = (Button) findViewById(R.id.plusMinus);
        currentTextField = (TextView) findViewById(R.id.currentTextView);
        historyText = (TextView) findViewById(R.id.historyView);
        storeData = new LinkedList<String>();
        currentTextField.setSingleLine(false);
        currentTextField.setEllipsize(TextUtils.TruncateAt.START);
         // the exact number of lines you want to display
        currentTextField.setLines(numberOfLines);
        historyText.setSingleLine(false);
        historyText.setEllipsize(TextUtils.TruncateAt.START);
        historyText.setLines(numberOfLines);

        if(savedInstanceState != null){
           saveHistory = savedInstanceState.getString(dataForHistory);
            saveResult = savedInstanceState.getString(dataForResult);

            historyText.setText(saveHistory);
            currentTextField.setText(saveResult);
        }

    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(dataForHistory, historyText.getText().toString());
        outState.putString(dataForResult, currentTextField.getText().toString());
    }

    public void onNumberClick(View view){

        switch (view.getId()){

            case R.id.numberOne:
                textEnteredByUser += 1;
                break;
            case R.id.numberTwo:
                textEnteredByUser += 2;
                break;
            case R.id.numberThree:
                textEnteredByUser += 3;
                break;
            case R.id.numberFour:
                textEnteredByUser += 4;
                break;
            case R.id.numberFive:
                textEnteredByUser += 5;
                break;
            case R.id.numberSix:
                textEnteredByUser += 6;
                break;
            case R.id.numberSeven:
                textEnteredByUser += 7;
                break;
            case R.id.numberEight:
                textEnteredByUser += 8;
                break;
            case R.id.numberNine:
                textEnteredByUser += 9;
                break;
            case R.id.buttonZero:
                    textEnteredByUser += 0;
                break;
            case R.id.decimalButton:
                if(lastElement.length() > 0){
                if(lastElement.charAt(lastElement.length()-1) != '.' && !containsDot()){
                textEnteredByUser += ".";
                }
                }

                break;
        }

        historyText.setText(textEnteredByUser);
        emptyAllQueue();
        addDataFromTextViewToQueue();
        calculate();
        updateResultToResultField();

    }

    public boolean isOperator(){
        if(lastElement == "-" || lastElement == "*" || lastElement == "/" || lastElement == "+"){
            return true;
        }

        return false;
    }
    public boolean containsDot(){

        for(int i = 0; i < lastElement.length(); i++){
            char a = lastElement.charAt(i);
            if(a == '.'){
                return true;
            }
        }
        return false;
    }
    public boolean containsJustDigit(){

        for(int i = 0; i < lastElement.length(); i++){
            char a = lastElement.charAt(i);
            if(a == '.'){
                return true;
            }
        }
        return false;
    }
    public void onOperationClick(View view){


        switch (view.getId()){

            case R.id.divideButton:
                if(currentTextField.getText().toString().length() > 0 && historyText.getText().toString().length() == 0){
                    textEnteredByUser += Double.parseDouble(currentTextField.getText().toString());
                    textEnteredByUser += "/";
                }
                if (historyText.getText().length()>0) {
                    if(Character.isDigit(historyText.getText().toString().charAt(historyText.getText().length() -1))) {
                        textEnteredByUser += "/";
                    }
                }


                break;
            case R.id.subtractButton:
                if(currentTextField.getText().toString().length() > 0 && historyText.getText().toString().length() == 0){
                    textEnteredByUser += Double.parseDouble(currentTextField.getText().toString());
                    textEnteredByUser += "-";
                }
                if (historyText.getText().length()>0) {
                    if(Character.isDigit(historyText.getText().toString().charAt(historyText.getText().length() -1))) {
                        textEnteredByUser += "-";
                    }
                }

                break;
            case R.id.multiplicationButton:
                if(currentTextField.getText().toString().length() > 0 && historyText.getText().toString().length() == 0){
                    textEnteredByUser += Double.parseDouble(currentTextField.getText().toString());
                    textEnteredByUser += "*";
                }
                if (historyText.getText().length()>0) {
                    if(Character.isDigit(historyText.getText().toString().charAt(historyText.getText().length() -1))) {
                        textEnteredByUser += "*";
                    }
                }

                break;
            case R.id.plusButton:
                if(currentTextField.getText().toString().length() > 0 && historyText.getText().toString().length() == 0){
                    textEnteredByUser += Double.parseDouble(currentTextField.getText().toString());
                    textEnteredByUser += "+";
                }
                if (historyText.getText().length()>0) {
                    if(Character.isDigit(historyText.getText().toString().charAt(historyText.getText().length() -1))) {
                        textEnteredByUser += "+";
                    }
                }

                break;
        }
        if(textEnteredByUser.length() > 0){
        historyText.setText(textEnteredByUser);
        emptyAllQueue();
        addDataFromTextViewToQueue();
        calculate();
        updateResultToResultField();
    }
}

    public void onEqualButtonClick(View view){

        historyText.setText("");
        updateResultToResultField();
        textEnteredByUser = "";
    }

    public void onClearScreenClick(View view){

        currentTextField.setText("");
        textEnteredByUser = "";
        resultData = "";
        emptyAllQueue();
        historyText.setText("");
    }

    public void addDataFromTextViewToQueue(){

        String data = "";
        String tempDataHolder = "";
        data = historyText.getText().toString();

        for(int i = 0; i < data.length(); i++){
            char valueFromString = data.charAt(i);

            if(Character.isDigit(valueFromString) || valueFromString == '.'){
                tempDataHolder += valueFromString;
            }
            else {
                if(tempDataHolder.length() > 0){
                    storeData.add(tempDataHolder);
                }
                storeData.add(String.valueOf(valueFromString));
                tempDataHolder = "";
            }
        }
        if(tempDataHolder.length() > 0){
            storeData.add(tempDataHolder);
            tempDataHolder = "";
        }
        System.out.println(storeData);
        if(!storeData.isEmpty()){
            lastElement = storeData.toArray()[storeData.size()-1].toString();
        }
    }

    public void calculate() {
        number = 0;
        if (!storeData.isEmpty()) {
            for (String item : storeData) {
                try {
                    number = Double.parseDouble(item);
                    openSwitchOperatorForCalculation = true;
                } catch (NumberFormatException nfe) {
                    operator = item;
                    openSwitchOperatorForCalculation = false;
                }
                if (isFirstDigit) {
                    if (operator.equalsIgnoreCase("-") && number > 0) {
                        result = (0 - number);
                        isFirstDigit = false;
                        operator = "";
                    } else if (operator != "-" && number > 0) {
                        result = number;
                        isFirstDigit = false;
                    }
                }

                if (openSwitchOperatorForCalculation) {
                    switch (operator) {
                        case "+":
                            result += number;
                            break;
                        case "-":
                            result -= number;
                            break;
                        case "/":
                                result /= number;
                            break;
                        case "*":
                            result *= number;
                            break;
                        default:
                            break;
                    }

                }

            }

        }
        isFirstDigit = true;
        openSwitchOperatorForCalculation = false;
        operator = "";
        number = 0;
        //if(result > 0 || result < 0 ){
        resultData = String.valueOf(decimalFormat.format(result));
        result = 0;
        emptyAllQueue();
    }
    public void onLastDeleteClick(View view){

        String data = "";


        data = historyText.getText().toString();
        if(data.length() > 0) {
            if ((data.length() - 1) == 0) {
                onClearScreenClick(view);
            }else{
            StringBuilder sb = new StringBuilder(data);
            sb.deleteCharAt(sb.length() - 1);
            data = sb.toString();

        textEnteredByUser = data;
        historyText.setText(textEnteredByUser);
        emptyAllQueue();
        addDataFromTextViewToQueue();
        calculate();
        updateResultToResultField();
            }
    }else{
            onClearScreenClick(view);
        }
    }

    public void assignNegativePositive(View view){
       String holdTempData =  textEnteredByUser;
       String negativeNumber = "-";
        if(holdTempData.length() > 0){
        if(checkIfTheNumberIsSigned(holdTempData)){
            if(holdTempData.charAt(0) == '-'){
                StringBuilder stringData = new StringBuilder(holdTempData);
                stringData.deleteCharAt(0);
                holdTempData = stringData.toString();
                textEnteredByUser = holdTempData;

            }else{
                negativeNumber += holdTempData;
                holdTempData = negativeNumber;
                negativeNumber = "-";
                textEnteredByUser = holdTempData;
            }
        }

            historyText.setText(textEnteredByUser);
           resultData = textEnteredByUser;
            updateResultToResultField();
    }
    }

    public boolean checkIfTheNumberIsSigned(String data){

        char valueFromTheString = data.charAt(0);
        String subStringFromData;
            if(valueFromTheString == '-'){
                subStringFromData = data.substring(1, (data.length()-1));

                return checkIfItsNumber(subStringFromData);
        }
        return checkIfItsNumber(data);
    }


    public void calculatePercentageOfResult(View view){

        double percent = 0;
        double valueToCalculate = 0;
        if(currentTextField.getText().length() > 0) {
            valueToCalculate = Double.parseDouble(currentTextField.getText().toString());
        }
        percent = Math.floor(valueToCalculate * 1) / 100;
        if(percent != 0) {
            resultData = decimalFormat.format(percent);
        }
        updateResultToResultField();
    }

    public void emptyAllQueue(){
        if(!storeData.isEmpty()){
            while(!storeData.isEmpty()){
                storeData.poll();
            }
        }
        storeData = new LinkedList<String>();
    }



    public void updateResultToResultField(){
            currentTextField.setText(resultData);

    }

    public boolean checkIfItsNumber(String data){
        for(int i = 0; i < data.length(); i++){
            char valueFromString = data.charAt(i);
            if(valueFromString == '-' || valueFromString == '/' || valueFromString == '*' || valueFromString == '+'){
                return false;
            }
        }
        return true;
    }


    public void onMemoryButtonClick(View view){
        double currentNumber = 0;
        String tempStringDataHolder = "";

        switch (view.getId()){
            case R.id.memoryCreator:
                if(currentTextField.getText().toString().length() > 0 && checkIfTheNumberIsSigned(currentTextField.getText().toString()) && memoryVariable.length() == 0) {
                    memoryVariable += currentTextField.getText().toString();
                    isMemoryCreated = true;
                }else{
                    onClearScreenClick(view);
                    currentTextField.setText(memoryVariable);
                }
                break;
            case R.id.memoryClear:
                memoryVariable = "";
                isMemoryCreated = false;
                break;

            case R.id.memoryReductorButton:
                if(isMemoryCreated){
                    if(currentTextField.getText().toString().length() > 0){
                        tempStringDataHolder = currentTextField.getText().toString();
                        currentNumber = Double.parseDouble(memoryVariable) - Double.parseDouble(tempStringDataHolder);
                        memoryVariable = String.valueOf(currentNumber);
                    }
                }
                currentNumber = 0;
                break;

            case R.id.memoryIncrementor:
                if(isMemoryCreated){
                    if(currentTextField.getText().toString().length() > 0) {
                        tempStringDataHolder = currentTextField.getText().toString();
                        currentNumber = Double.parseDouble(memoryVariable) + Double.parseDouble(tempStringDataHolder);
                        memoryVariable = String.valueOf(currentNumber);
                    }
                }
                currentNumber = 0;
                break;
        }

    }

    public boolean memoryCanBeAdded(String data){
        if(data.charAt(data.length()-1) == '-' || data.charAt(data.length()-1) == '*' || data.charAt(data.length()-1) == '/' || data.charAt(data.length()-1) == '+'){
            return true;
        }
        return false;
    }

}
