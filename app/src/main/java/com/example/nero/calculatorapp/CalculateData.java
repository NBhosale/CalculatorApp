package com.example.nero.calculatorapp;
import java.util.LinkedList;
import java.util.Queue;
/**
 * Created by Nero on 24/10/2016.
 */

public class CalculateData {

    Queue<String> queueForOperatorsAndNumbers;
    double result;
    boolean isFirstDigit = true, openSwitchOperatorForCalculation = false;
    String operator = "";
    double number;
    String items = "";

    public CalculateData(Queue<String> dataReciverQueue){
        queueForOperatorsAndNumbers = new LinkedList<String>();
        queueForOperatorsAndNumbers = dataReciverQueue;
        number = 0;
        result = 0;
    }


    public double calculate(){

        for(String item: queueForOperatorsAndNumbers){
            try{
                number = Double.parseDouble(item);
                openSwitchOperatorForCalculation = true;
            }catch(NumberFormatException nfe){
                operator = item;
                openSwitchOperatorForCalculation = false;
            }
            if(isFirstDigit){
                if(operator.equalsIgnoreCase("-") && number > 0){
                    result = (0 - number);
                    isFirstDigit = false;

                    operator = "";
                }else if(operator != "-" && number > 0){
                    result = number;
                    isFirstDigit = false;
                }
            }

            if(openSwitchOperatorForCalculation){
                switch(operator){
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

        return result;
    }

}
