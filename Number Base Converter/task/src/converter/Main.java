package converter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.util.Scanner;

public class Main {

    public static StringBuilder convert(String[] sourceNum, int sourceBase, int targetBase) throws ArrayIndexOutOfBoundsException{

        BigInteger integerPart = new BigInteger(String.valueOf(sourceNum[0]), sourceBase); // Quotient part conversion to DECIMAL
        StringBuilder quotient = new StringBuilder(integerPart.toString(targetBase));      // Quotient part conversion to target base
        try {
            String a = sourceNum[1];
        } catch (ArrayIndexOutOfBoundsException e) {
            return quotient;
        }

        String a = sourceNum[1];
        BigDecimal buffer = BigDecimal.ZERO;
        int n = -1;
        for (int i = 0; i < a.length(); i++) {
            buffer = buffer.add(BigDecimal.valueOf(Character.getNumericValue(a.charAt(i))).multiply(BigDecimal.valueOf(Math.pow(sourceBase, n))));
            n--;
        }
        StringBuilder fraction = new StringBuilder();
        MathContext precision = new MathContext(5);
        buffer = buffer.round(precision);
        int counter = 0;
        while (counter < 5) {
            buffer = buffer.multiply(BigDecimal.valueOf(targetBase)).stripTrailingZeros(); // 16 - targetBase
            fraction.append(Character.forDigit((int) buffer.longValue(), targetBase));
            buffer = buffer.subtract(BigDecimal.valueOf(buffer.longValue()));
            counter++;
        }
        return quotient.append(".").append(fraction);

    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("\nEnter two numbers in format: {source base} {target base} (To quit type /exit) > ");
            String first = scanner.next();
            if (first.equals("/exit")) {
                break;
            }
            String second = scanner.next();
            if (second.equals("/exit")) {
                break;
            }
            while (true) {
                System.out.printf("\nEnter number in base %s to convert to base %s (To go back type /back) > ", first, second);
                String input = scanner.next();
                if (input.equals("/back")) {
                    break;
                }
                int sourceBase = Integer.parseInt(first);
                int targetBase = Integer.parseInt(second);
                String[] sourceNum = input.split("\\.");
                System.out.println("\nConversion result: " + convert(sourceNum, sourceBase, targetBase));
            }
        }
    }
}
