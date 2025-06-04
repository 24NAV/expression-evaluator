import java.util.Scanner;
import java.util.Stack;

public class ArithmeticExpressionEvaluator {

    // Method to check if parentheses in the expression are balanced
    public static boolean isValidExpression(String expression) {
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < expression.length(); i++) {
            char ch = expression.charAt(i);
            if (ch == '(') {
                stack.push(ch);
            } else if (ch == ')') {
                if (stack.isEmpty() || stack.pop() != '(') {
                    return false;
                }
            }
        }
        return stack.isEmpty();
    }

    // Method to determine precedence of operators
    public static int precedence(char ch) {
        switch (ch) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
            case '%':
                return 2;
            default:
                return -1;
        }
    }

    // Method to convert infix expression to postfix expression
    public static String infixToPostfix(String expression) {
        Stack<Character> stack = new Stack<>();
        StringBuilder result = new StringBuilder();
        Scanner tokens = new Scanner(expression);

        while (tokens.hasNext()) {
            String token = tokens.next();

            // If the token is a number, append it to the result
            if (token.matches("\\d+")) {
                result.append(token).append(" ");
            }
            // If the token is '(', push it to the stack
            else if (token.equals("(")) {
                stack.push('(');
            }
            // If the token is ')', pop and append until '(' is found
            else if (token.equals(")")) {
                while (!stack.isEmpty() && stack.peek() != '(') {
                    result.append(stack.pop()).append(" ");
                }
                stack.pop(); // Remove '('
            }
            // If the token is an operator
            else {
                char op = token.charAt(0);
                while (!stack.isEmpty() && precedence(op) <= precedence(stack.peek())) {
                    result.append(stack.pop()).append(" ");
                }
                stack.push(op);
            }
        }

        // Pop all the operators from the stack
        while (!stack.isEmpty()) {
            result.append(stack.pop()).append(" ");
        }

        return result.toString().trim();
    }

    // Method to evaluate the postfix expression
    public static int evaluatePostfix(String postfix) {
        Stack<Integer> stack = new Stack<>();
        Scanner tokens = new Scanner(postfix);

        while (tokens.hasNext()) {
            String token = tokens.next();

            // If the token is a number, push it to the stack
            if (token.matches("\\d+")) {
                stack.push(Integer.parseInt(token));
            }
            // Otherwise, the token is an operator
            else {
                int b = stack.pop();
                int a = stack.pop();
                switch (token.charAt(0)) {
                    case '+':
                        stack.push(a + b);
                        break;
                    case '-':
                        stack.push(a - b);
                        break;
                    case '*':
                        stack.push(a * b);
                        break;
                    case '/':
                        stack.push(a / b);
                        break;
                    case '%':
                        stack.push(a % b);
                        break;
                }
            }
        }

        return stack.pop();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String choice;

        do {
            System.out.print("Enter the expression: ");
            String expression = sc.nextLine();

            // Check if the expression is valid in terms of parentheses
            if (!isValidExpression(expression)) {
                System.out.println("The expression is invalid.");
            } else {
                // Convert infix to postfix
                String postfix = infixToPostfix(expression);
                System.out.println("The postfix expression: " + postfix);

                // Evaluate the postfix expression
                int result = evaluatePostfix(postfix);
                System.out.println("The final result: " + result);
            }

            // Ask if user wants to continue
            System.out.print("Do you want to continue (Y/ N)? ");
            choice = sc.nextLine().trim();

        } while (!choice.equalsIgnoreCase("N"));
        
        sc.close();
    }
}
