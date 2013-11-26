/* Use the default unnamed package, i.e. do not use the "package" statement. */

import java.io.*;
import java.math.BigDecimal;

/** DU 1. 
 * 
 * @author Juraj Masar <mail@jurajmasar.com>
 * @date 26 November 2013
 */
public class CodEx {
    /**
     * Result of the evaluation of the last expression.
     */
    static BigDecimal last = new BigDecimal(0);
    
    /** 
     * Rounding mode used for printing out the number.
     */
    static final int ROUNDING_MODE = BigDecimal.ROUND_HALF_UP;
    
    /**
     * Precision to be used in the next evaluation.
     */
    static int precision = 20;    

    /**
     * Type of a node in an expression evaluation tree.
     */
    enum ExpressionType {
        VALUE, ADDITION, SUBTRACTION, MULTIPLICATION, DIVISION
    }

    /**
     * Expression to be thrown if an invalid expression is being evaluated.
     */
    static class InvalidExpressionException extends Exception {}
    
    
    /**
     * Class representing a node in an tree for evaluation of a mathematical expression.
     */
    static class Expression {
        /**
         * Type of the expression.
         */
        ExpressionType type;
        
        /**
         * Left and right sons of the node being used if the ExpressionType is a binomial operation (+,-,*,/).
         */
        Expression left, right;
        
        /**
         * Numeric value of the node if the ExpressionType being used is VALUE.
         */
        BigDecimal value;
               
        /**
         * Recursively parses a given String expression into a tree of Expression classes.
         */
        public Expression(String input) throws InvalidExpressionException {
            // remove whitespace 
            StringBuilder sb = new StringBuilder(input.trim());

            // validate input
            if (sb == null || sb.length() == 0) throw new InvalidExpressionException();                       
            
            // number of opening brackets that we encountered
            int bracketLevel = 0;
            
            //
            // remove enclosing brackets
            //
            
            boolean isEnclosedInBrackets = true;
                        
            while (isEnclosedInBrackets && sb.charAt(0) == '(' && sb.charAt(sb.length()-1) == ')') {
                bracketLevel = 0;
                
                // iterate over expression and decide whether it is enclosed in brackets
                // e.g. (a) is OK but (a)/(b) is not, although both pass the condition in while                
                for (int i = 0; i < sb.length(); i++) {
                    if (sb.charAt(i) == '(') bracketLevel++;
                    if (sb.charAt(i) == ')') bracketLevel--;
                    
                    if (bracketLevel == 0 && i != 0 && i != sb.length() - 1) {
                        isEnclosedInBrackets = false;
                        break;
                    }
                }
                
                if (isEnclosedInBrackets) {
                    // remove brackets                    
                    sb.deleteCharAt(0).deleteCharAt(sb.length()-1);
                }
            }
            
            //
            // check for + and -
            //
            
            bracketLevel = 0;
            for (int i = 0; i < sb.length(); i++) {
                    if (sb.charAt(i) == '(') bracketLevel++;
                    if (sb.charAt(i) == ')') bracketLevel--;

                    if ((sb.charAt(i) == '+' || sb.charAt(i) == '-') && bracketLevel == 0 && i > 0) {
                        if (sb.charAt(i) == '+') type = ExpressionType.ADDITION;
                        if (sb.charAt(i) == '-') type = ExpressionType.SUBTRACTION;
                        
                        left = new Expression(sb.substring(0, i));
                        right = new Expression(sb.substring(i+1, sb.length()));
                        
                        return;
                    }
            }
            
            //
            // check for * and /
            //
            
            bracketLevel = 0;
            for (int i = 0; i < sb.length(); i++) {
                    if (sb.charAt(i) == '(') bracketLevel++;
                    if (sb.charAt(i) == ')') bracketLevel--;

                    if ((sb.charAt(i) == '*' || sb.charAt(i) == '/') && bracketLevel == 0) {
                        if (sb.charAt(i) == '*') type = ExpressionType.MULTIPLICATION;
                        if (sb.charAt(i) == '/') type = ExpressionType.DIVISION;
                        
                        left = new Expression(sb.substring(0, i));
                        right = new Expression(sb.substring(i+1, sb.length()));
                        
                        return;
                    }
            }            
            
            //
            // check for "last"
            //
            
            if ("last".equals(sb.toString())) {
               type = ExpressionType.VALUE;
               value = last;
               
               return;
            }
            
            //
            // check if the expression is non-empty alphanumeric string
            //
            
            try {
                type = ExpressionType.VALUE;
                value = new BigDecimal(sb.toString()).setScale(precision, ROUNDING_MODE);
            } catch (NumberFormatException ex) {
                throw new InvalidExpressionException();
            }
        }
        
        /**
         * Return numeric value of the Expression tree with the root in the current node.
         */
        public BigDecimal getResult() {
            switch (type) {
                case ADDITION:
                    return left.getResult().add(right.getResult());
                case SUBTRACTION:
                    return left.getResult().subtract(right.getResult());
                case MULTIPLICATION:
                    return left.getResult().multiply(right.getResult());
                case DIVISION:
                    return left.getResult().divide(right.getResult(), precision, ROUNDING_MODE);
                default:
                    return value;
            }
        }
    }

    /**
     * Entry point for the program.
     * 
     * Reads the standard input line by line and tries to evaluate the given mathematical expression on every line.
     */
    public static void main(String[] argv) {
        try {
            BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
            String line;
            while ((line = input.readLine()) != null) {
                try {
                    line = line.trim();                    
                    if (line.length() > 9 && "precision".equals(line.substring(0,9))) {
                        // change precision
                        precision = Integer.parseInt(line.substring(9, line.length()).trim());
                    } else {
                        // handle arithmetic expression
                        last = new Expression(line).getResult().stripTrailingZeros();
                        // do not use scientific notation e.g. "1E2"
                        System.out.println(last.toPlainString());
                    }
                } catch (InvalidExpressionException e) {
                    System.out.println("CHYBA");
                    last = new BigDecimal(0);
                }
            }
            System.out.println();
        } catch (IOException ex) {
            System.out.println("IOException occured, exiting.");
        }
    }
}

