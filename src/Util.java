import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.Iterator;
import java.util.Stack;

/**
 * Mobile Apps and Open Source Final Project: 
 * JavaCalc - a GUI-based Java calculator
 * 
 * @author Patrick Korkuch
 * @version 1.0
 */
public class Util {
    private static BigDecimal ANS = null;
    
    /**
     * Converts a valid mathematical expression in infix notation
     * to postfix notation. <br />
     * Valid operators: +, -, *, /, % (modulo), (, )
     * @param input - the expression in infix notation to be converted
     * @return the inputted expression in postfix notation as an ArrayList
     * of BigDecimal and Character objects
     * @throws IllegalArgumentException if the inputted expression is
     * invalid or malformed
     */
    public static ArrayList<Object> shuntingYard(String input) throws IllegalArgumentException {
        ArrayList<Object> lexInput = lex(input);
        ArrayList<Object> expr = new ArrayList<Object>();
        Iterator<Object> it = lexInput.iterator();
        Stack<Character> operatorStack = new Stack<Character>();
        
        while (it.hasNext()) {
            Object o = it.next();
            if (o instanceof BigDecimal) {
                expr.add(o);
            } else if (o instanceof Character) {
                char currentChar = ((Character) o).charValue();
                if (isOperator(currentChar)) {
                    while (!operatorStack.empty() && 
                            ((operatorIsLeftAssociative(currentChar) && operatorPrecedence(currentChar) <= operatorPrecedence(operatorStack.peek())) || 
                             (operatorPrecedence(currentChar) < operatorPrecedence(operatorStack.peek())))) {
                        expr.add(operatorStack.pop());
                    }
                    operatorStack.push((Character)currentChar);
                } else if (isParen(currentChar)) {
                    if (currentChar == ')') {
                        while (!operatorStack.empty() && operatorStack.peek() != '(') {
                            expr.add(operatorStack.pop());
                        }
                        if (!operatorStack.empty()) {
                            operatorStack.pop();
                            if (!operatorStack.empty() && isOperator(operatorStack.peek())) {
                                expr.add(operatorStack.pop());
                            }
                        } else {
                            throw new IllegalArgumentException("Mismatched parenthesis");
                        }
                    } else {
                        operatorStack.push((Character)currentChar);
                    }
                } else {
                    throw new IllegalArgumentException("Illegal operator or control character: " + currentChar);
                }
            } else if (o instanceof String && ((String)o).equals("ANS")) {
                if (ANS == null) {
                    throw new IllegalArgumentException("Illegal previous answer token");
                } else {
                    expr.add(ANS);
                }
            } else {
                throw new IllegalArgumentException("Illegal token: " + o.getClass());
            }
        }
        while (!operatorStack.empty()) {
            char tempChar = operatorStack.pop();
            if (isParen(tempChar)) {
                throw new IllegalArgumentException("Mismatched parenthesis");
            } else {
                expr.add((Character)tempChar);
            }
        }
        
        return expr;
    }
    
    /**
     * Evaulates a mathematical expression in postfix notation.
     * 
     * @param expr the expression to evaluate
     * @return the result of the evaluation
     * @throws IllegalArgumentException if the expression is in invalid
     * postfix notation or is malformed
     */
    public static BigDecimal evaluatePostfix(ArrayList<Object> expr) throws IllegalArgumentException {
        Stack<BigDecimal> stack = new Stack<BigDecimal>();
        Iterator<Object> it = expr.iterator();
        
        while (it.hasNext()) {
            Object o = it.next();
            if (o instanceof Character) {
                char current = ((Character) o).charValue();
                if (isOperator(current)) {
                    BigDecimal x, y;
                    try {
                        y = stack.pop();
                        x = stack.pop();
                        stack.push(eval(x, y, current));
                    } catch (EmptyStackException ese) {
                        throw new IllegalArgumentException("Malformed postfix expression");
                    }
                }
            } else if (o instanceof BigDecimal) {
                stack.push((BigDecimal)o);
            } else if (o instanceof String && ((String)o).equals("ANS")) {
                if (ANS == null) {
                    throw new IllegalArgumentException("Illegal previous answer token");
                } else {
                    stack.push(ANS);
                }
            } else {
                throw new IllegalArgumentException("Illegal token: " + o.getClass());
            }
        }
        
        if (stack.size() != 1) {
            throw new IllegalArgumentException("Invalid number of operators");
        }
        
        ANS = stack.peek();
        return stack.pop();
    }
    
    /**
     * Evaluates one mathematical operation.
     * 
     * @param x the first (leftmost) operand
     * @param y the second (rightmost) operand
     * @param operator the operator
     * @return the result of the evaluation
     * @throws UnsupportedOperationException if the character operator
     * is not on operator or is not a supported operator
     */
    private static BigDecimal eval(BigDecimal x, BigDecimal y, char operator) throws UnsupportedOperationException {
        switch (operator) {
            case '%':
                return x.remainder(y);
            case '*':
                return x.multiply(y);
            case '/':
                return x.divide(y, 6, BigDecimal.ROUND_HALF_EVEN);
            case '+':
                return x.add(y);
            case '-':
                return x.subtract(y);
            case '^':
            default:
                throw new UnsupportedOperationException("Illegal operator: " + operator);
        }
    }
    
    /**
     * Lexically analyzes an infix mathematical expression, tokenizing it
     * where each token is one of: <br />
     * BigDecimal - for numerical values <br />
     * Character - for mathematical operands
     * @param input the mathematical expression
     * @return an ArrayList of BigDecimal and Character objects representing
     * a tokenized version of the inputted String
     * @throws IllegalArgumentException if the inputted String is malformed 
     * (Note: this exception is not thrown if a token is misplaced, only if
     * a token is malformed and unable to be identified by the lexer)
     */
    private static ArrayList<Object> lex(String input) throws IllegalArgumentException {
        char[] expr = input.toCharArray();
        StringBuilder digitTemp = new StringBuilder();
        StringBuilder tokenTemp = new StringBuilder();
        boolean tempHasDecimalPoint = false;
        ArrayList<Object> out = new ArrayList<Object>();

        for (int i = 0; i < expr.length; i++) {
            char current = expr[i];
            if (Character.isDigit(current)) {
                if (tokenTemp.length() > 0) {
                    throw new IllegalArgumentException("Illegal control sequence");
                }
                digitTemp.append(current);
            } else if (current == '.') {
                if (tokenTemp.length() > 0) {
                    throw new IllegalArgumentException("Illegal control sequence");
                }
                if (tempHasDecimalPoint) {
                    throw new IllegalArgumentException("Malformed decimal number token at index: " + i);
                } else {
                    digitTemp.append(current);
                    tempHasDecimalPoint = true;
                }
            } else if (isOperator(current) || isParen(current)) {
                if (tokenTemp.length() > 0) {
                    throw new IllegalArgumentException("Illegal control sequence");
                }
                if (digitTemp.length() > 0) {
                    if (tempHasDecimalPoint && digitTemp.length() == 1) {
                        throw new IllegalArgumentException("Illegal decimal point at index: " + i);
                    }
                    out.add(new BigDecimal(digitTemp.toString()));
                    digitTemp.delete(0, digitTemp.length());
                    tempHasDecimalPoint = false;
                }
                out.add((Character)current);
            } else if (Character.isLetter(current)) {
                if (digitTemp.length() > 0) {
                    throw new IllegalArgumentException("Malformed decimal number token at index: " + i);
                }
                switch (current) {
                    case 'A':
                        if (tokenTemp.length() == 0) {
                            tokenTemp.append(current);
                        } else {
                            throw new IllegalArgumentException("Illegal control sequence A");
                        }
                        break;
                    case 'N':
                        if (tokenTemp.length() == 1) {
                            tokenTemp.append(current);
                        } else {
                            throw new IllegalArgumentException("Illegal control sequence N");
                        }
                        break;
                    case 'S':
                        if (tokenTemp.length() == 2) {
                            tokenTemp.append(current);
                            out.add(tokenTemp.toString());
                            tokenTemp.delete(0, tokenTemp.length());
                        } else {
                            throw new IllegalArgumentException("Illegal control sequence S");
                        }
                        break;
                }
            } else {
                throw new IllegalArgumentException("Illegal token at index: " + i);
            }
        }
        if (digitTemp.length() > 0) {
            if (tempHasDecimalPoint && digitTemp.length() == 1) {
                throw new IllegalArgumentException("Illegal decimal point");
            }
            out.add(new BigDecimal(digitTemp.toString()));
            digitTemp.delete(0, digitTemp.length());
            tempHasDecimalPoint = false;
        }
        
        return out;
    }
    
    /**
     * Determines if a given character is a mathematical operator.
     * 
     * @param input the input character
     * @return true if the character is an operator; false otherwise
     */
    public static boolean isOperator(char input) {
        switch (input) {
            case '%':
            case '*':
            case '/':
            case '+':
            case '-':
                return true;
            default:
                return false;
        }
    }
    
    /**
     * Determines if a given character is a parenthesis.
     * 
     * @param input the input character
     * @return true if the character is a left or right parenthesis; false otherwise
     */
    public static boolean isParen(char input) {
        switch (input) {
            case ')':
            case '(':
                return true;
            default:
                return false;
        }
    }
    
    /**
     * Returns a numerical value indicating the precedence of an operator.
     * 
     * @param operator the input operator
     * @return a number representing the precedence of the operator;
     * higher values indicate a higher precedence; equal values indicate
     * equal precedence
     */
    private static int operatorPrecedence(char operator) {
        switch (operator) {
            case '^':
                return 4;
            case '*':
            case '/':
                return 3;
            case '+':
            case '-':
                return 2;
            default:
                return 0;
        }
    }
    
    /**
     * Determines if a given operator is left associative. 
     * (If leftmost instances of the operator are evaluated before
     * rightmost instances)
     * @param operator the input operator
     * @return true if the operator is left associative; false otherwise
     */
    private static boolean operatorIsLeftAssociative(char operator) {
        switch (operator) {
            case '*':
            case '/':
            case '+':
            case '-':
            case '%':
                return true;
            case '^':
            default:
                return false;
        }
    }
}