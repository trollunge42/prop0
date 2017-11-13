import java.util.Arrays;
import java.util.LinkedList;

public class Evaluator {

    public Object evaluate(Object[] args) throws Exception {
        Lexeme result = parenting(Arrays.copyOfRange(args, 2, args.length-1));
        Lexeme id = (Lexeme)args[0];
        String str = id.value() + " = " + result.value() + ";";
        return str;
    }

    private Lexeme parenting(Object[] array){
        LinkedList<Lexeme> calcList = new LinkedList<>();
        for(int i = 0; i < array.length; i++){
            Lexeme lex = (Lexeme)array[i];
            if(lex.token()==Token.LEFT_PAREN){
                for(int j = array.length-1; j > i; j--){
                    Lexeme lex2 = (Lexeme)array[j];
                    if(lex2.token()==Token.RIGHT_PAREN){
                        Object[] subArray = Arrays.copyOfRange(array, i+1, j);
                        lex2 = parenting(subArray);
                        lex = lex2;
                        i=j;
                        break;
                    }
                }
            }
            calcList.add(lex);
        }
        Lexeme result = calculate(calcList);
        return result;
    }

    private Lexeme calculate(LinkedList<Lexeme> calcList) {
        Lexeme result;
        LinkedList<Lexeme> nextList = new LinkedList<>();
        for(int i = 0; i<calcList.size(); i++){
            if(calcList.get(i).token()==Token.MULT_OP){
                double a = (double)calcList.get(i-1).value();
                double b = (double)calcList.get(i+1).value();
                i++;
                calcList.set(i, new Lexeme(a*b,Token.INT_LIT));
                nextList.removeLast();
            } else if(calcList.get(i).token()==Token.DIV_OP){
                double a = (double)calcList.get(i-1).value();
                double b = (double)calcList.get(i+1).value();
                i++;
                calcList.set(i, new Lexeme(a/b, Token.INT_LIT));
                nextList.removeLast();
            }
            nextList.add(calcList.get(i));
        }
        for(int i = 0; i<nextList.size();i++){
            System.out.println("current thing: " + nextList.get(i));
            if(nextList.get(i).token()==Token.ADD_OP){
                System.out.println("i-1: " + nextList.get(i-1) + " i+1: " + nextList.get(i+1));
                double a = (double)nextList.get(i-1).value();
                double b = (double)nextList.get(i+1).value();
                i++;
                nextList.set(i,new Lexeme(a+b, Token.INT_LIT));
            } else if(nextList.get(i).token()==Token.SUB_OP){
                double a = (double)nextList.get(i-1).value();
                double b = (double)nextList.get(i+1).value();
                System.out.println("a=" + a + " b=" + b);
                i++;
                nextList.set(i, new Lexeme(a-b, Token.INT_LIT));
            }
            System.out.println("last set: " + nextList.get(i));
        }
        result = nextList.getLast();
        return result;
    }
}
