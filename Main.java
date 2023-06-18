import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static int firstNum;
    public static int secondNum;
    public static String signature;
    public static final String correctInputRegex = "^[0-9]+\\s\\D\\s[0-9]+$";

    public static final String correctRomanInputRegex = "^M{0,4}(CM|CD|D?C{0,3})(XC|XL|L?X{0,3})(IX|IV|V?I{0,3})\\s\\D\\sM{0,4}(CM|CD|D?C{0,3})(XC|XL|L?X{0,3})(IX|IV|V?I{0,3})$";

    public static boolean isRoman;

    public static void main(String[] args) {

        Pattern correctInputPattern = Pattern.compile(correctInputRegex);

        String[] partsOfExpression;

        String splitChar = "";

        String input = "";

        Scanner scanner = new Scanner(System.in);

        String transmittedValue = "";

        Map<Character, Integer> romanValuesMap = new HashMap<>();
        romanValuesMap.put('I', 1);
        romanValuesMap.put('V', 5);
        romanValuesMap.put('X', 10);
        romanValuesMap.put('L', 50);
        romanValuesMap.put('C', 100);
        romanValuesMap.put('D', 500);
        romanValuesMap.put('M', 1000);


        while (true) {

            input = scanner.nextLine();

            transmittedValue = input;

            isRoman = input.matches(correctRomanInputRegex);

            boolean defaultMatcherFind = true;

            if (isRoman) {

                defaultMatcherFind = false;

                input = input.replaceAll("\\s+", "");
                splitChar = input.replaceAll("[A-Z]+", "");

                if (splitChar.equals("*")) {
                    splitChar = "\\*";
                }
                if (splitChar.equals("+")) {
                    splitChar = "\\+";
                }

                partsOfExpression = input.split(splitChar);

                for (int i = 0; i < partsOfExpression[0].length(); i++) {//Преобразование к номально виду первого числа
                    int value = romanValuesMap.get(partsOfExpression[0].charAt(i));
                    if (i == partsOfExpression[0].length() - 1) {
                        firstNum += value;
                    } else if (value < romanValuesMap.get(partsOfExpression[0].charAt(i + 1))) {
                        firstNum -= value;
                    } else {
                        firstNum += value;
                    }
                }//Преобразование к номально виду первого числа

                for (int i = 0; i < partsOfExpression[1].length(); i++) {//Преобразование к нормальному виду второго числа
                    int value = romanValuesMap.get(partsOfExpression[1].charAt(i));
                    if (i == partsOfExpression[1].length() - 1) {
                        secondNum += value;
                    } else if (value < romanValuesMap.get(partsOfExpression[1].charAt(i + 1))) {
                        secondNum -= value;
                    } else {
                        secondNum += value;
                    }
                }//Преобразование к нормальному виду второго числа

                if (firstNum > 10 || secondNum > 10) {
                    System.out.println("throws Exception");
                    return;
                }
                if (firstNum - secondNum < 1) {
                    System.out.println("throws Exception");
                    return;
                }

                setSign(splitChar);
                setFirstNum(firstNum);
                setSecondNum(secondNum);

                calc(transmittedValue);

                firstNum = 0;
                secondNum = 0;

            } else {

                Matcher matcher = correctInputPattern.matcher(input);

                if (!matcher.find() && defaultMatcherFind) {
                    System.out.println("throws Exception");
                    return;
                }

                input = input.replaceAll("\\s+", "");
                splitChar = input.replaceAll("[0-9]+", "");

                if (splitChar.equals("*")) {
                    splitChar = "\\*";
                }
                if (splitChar.equals("+")) {
                    splitChar = "\\+";
                }

                partsOfExpression = input.split(splitChar);

                firstNum = Integer.parseInt(partsOfExpression[0].trim());
                secondNum = Integer.parseInt(partsOfExpression[1].trim());

                if (firstNum > 10 || secondNum > 10) {
                    System.out.println("throws Exception");
                    return;
                }

                setSign(splitChar);
                setFirstNum(firstNum);
                setSecondNum(secondNum);

                calc(transmittedValue);

                firstNum = 0;
                secondNum = 0;
            }
        }
    }

    public static String calc(String input) {

        Map<Integer, String> secondRomanValuesMap = new LinkedHashMap<>();
        secondRomanValuesMap.put(1000, "M");
        secondRomanValuesMap.put(900, "CM");
        secondRomanValuesMap.put(500, "D");
        secondRomanValuesMap.put(400, "CD");
        secondRomanValuesMap.put(100, "C");
        secondRomanValuesMap.put(90, "XC");
        secondRomanValuesMap.put(50, "L");
        secondRomanValuesMap.put(40, "XL");
        secondRomanValuesMap.put(10, "X");
        secondRomanValuesMap.put(9, "IX");
        secondRomanValuesMap.put(5, "V");
        secondRomanValuesMap.put(4, "IV");
        secondRomanValuesMap.put(1, "I");

        String output = "";

        if (signature.equals("/")) {
            if (getSecondNum() != 0) {
                output = String.valueOf(firstNum / secondNum);
            } else System.out.print("throws Exception");
        }
        if (signature.equals("\\+")) {
            output = String.valueOf(firstNum + secondNum);
        }
        if (signature.equals("-")) {
            output = String.valueOf(firstNum - secondNum);
        }
        if (signature.equals("\\*")) {
            output = String.valueOf(firstNum * secondNum);
        }

        if (isRoman) {

            int arabicNumber = Integer.parseInt(output);

            StringBuilder romanNumber = new StringBuilder();
            for (Map.Entry<Integer, String> entry : secondRomanValuesMap.entrySet()) {
                int number = entry.getKey();
                String symbol = entry.getValue();
                while (arabicNumber >= number) {
                    romanNumber.append(symbol);
                    arabicNumber -= number;
                }
            }
            output = romanNumber.toString();
        }

        System.out.println(output);

        return output;
    }

    public static void setFirstNum(int num) {
        firstNum = num;
    }

    public static void setSecondNum(int num) {
        secondNum = num;
    }

    public static void setSign(String sign) {
        signature = sign;
    }

    public static int getSecondNum() {
        return secondNum;
    }
}

