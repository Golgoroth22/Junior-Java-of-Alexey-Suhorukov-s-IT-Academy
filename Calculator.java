/**
 * Created by Админ on 07.07.2016.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Kassa on 06.07.16.
 */
public class Calculator {
    //"1 + (2 + 3 * 4) * (5 + 6)";
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String s = reader.readLine();

        Stack<String> ourStack = new Stack<String>();
        String numberStr = "";

        for (int i = 0; i < s.length(); i++) {
            char symbol = s.charAt(i);

            if (symbol == ' ') {
                continue;
            }

            switch (symbol) {
                case '+':
                    ourStack.push(numberStr);
                    ourStack.push("" + symbol);
                    numberStr = "";
                    break;
                case '-':
                    ourStack.push(numberStr);
                    numberStr = "";
                    break;
                case '*':
                    ourStack.push(numberStr);
                    if (checkNumbers(String.valueOf(s.charAt(i + 2)))) {
                        String nextInt = "";
                        int count = 0;
                        while (true) {
                            if ((i + 2 + count) == s.length()) {
                                break;
                            } else if (checkNumbers(String.valueOf(s.charAt(i + 2 + count)))) {
                                nextInt += String.valueOf(s.charAt(i + 2 + count));
                                count++;
                            } else {
                                break;
                            }
                        }
                        String testString = ourStack.pop();
                        ourStack.push(String.valueOf(processOperator(nextInt, testString, "*")));
                        i = i + 1 + nextInt.length();
                    }
                    numberStr = "";
                    break;
                case '/':
                    ourStack.push(numberStr);
                    numberStr = "";
                    break;
                case '(':
                    break;
                case ')':
                    ourStack.push(numberStr);
                    numberStr = "";
                    break;
                default:
                    numberStr += symbol;
                    break;
            }
        }
        if (s.charAt(s.length() - 3) != '*') {
            ourStack.push(numberStr);
        }

        for (int i = 0; i < ourStack.size(); i++) {
            String firstNumber = ourStack.pop();
            String strangeSymbol = ourStack.pop();
            if (strangeSymbol.equals("")) {
                strangeSymbol = ourStack.pop();
            }
            if (!strangeSymbol.equals("(")) {
                String secondNumber = ourStack.pop();
                if (secondNumber.equals("")) {
                    secondNumber = ourStack.pop();
                }
                ourStack.push(String.valueOf(processOperator(firstNumber, secondNumber, strangeSymbol)));
            }
        }

        while (!ourStack.empty()) {
            String stackObjekt;
            stackObjekt = ourStack.pop();
            System.out.println(stackObjekt);
        }
    }

    static int processOperator(String number1, String number2, String operator) {
        int numb1 = Integer.parseInt(number1);
        int numb2 = Integer.parseInt(number2);
        int result;
        switch (operator) {
            case "+":
                result = numb1 + numb2;
                break;
            case "*":
                result = numb1 * numb2;
                break;
            default:
                result = -1;
        }
        return result;
    }

    public static boolean checkNumbers(String userNameString) {
        Pattern p = Pattern.compile("[0-9]");
        Matcher m = p.matcher(userNameString);
        return m.matches();
    }
}

