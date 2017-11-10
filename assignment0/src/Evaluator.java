import java.util.Arrays;
import java.util.LinkedList;

public class Evaluator {

    public Object evaluate(Object[] args) throws Exception {
        Lexeme result = parenting(Arrays.copyOfRange(args, 2, Parser.arraySize));
        Lexeme id = (Lexeme)args[0];
        String str = id.value() + " = " + result.value();
        return str;
    }

    private Lexeme parenting(Object[] array){
        LinkedList<Lexeme> calcList = new LinkedList<>();
        for(int i = 0; i < array.length; i++){
            Lexeme lex = (Lexeme)array[i];
            if(lex.token()==Token.LEFT_PAREN){
                for(int j = array.length; j > i; j++){
                    Lexeme lex2 = (Lexeme)array[j];
                    if(lex2.token()==Token.RIGHT_PAREN){
                        Object[] subArray = Arrays.copyOfRange(array, i+1, j-1);
                        lex2 = parenting(subArray);
                        lex = lex2;
                        i=j+1;
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
                int a = (int)calcList.get(i-1).value();
                int b = (int)calcList.get(i+1).value();
                i++;
                calcList.set(i, new Lexeme(a*b,Token.INT_LIT));
                nextList.removeLast();
            } else if(calcList.get(i).token()==Token.DIV_OP){
                int a = (int)calcList.get(i-1).value();
                int b = (int)calcList.get(i+1).value();
                i++;
                calcList.set(i, new Lexeme(a/b, Token.INT_LIT));
                nextList.removeLast();
            }
            nextList.add(calcList.get(i));
        }
        for(int i = 0; i<nextList.size();i++){
            if(nextList.get(i).token()==Token.ADD_OP){
                double a = (double)nextList.get(i-1).value();
                double b = (double)nextList.get(i+1).value();
                i++;
                nextList.set(i,new Lexeme(a+b, Token.INT_LIT));
            } else if(nextList.get(i).value()==Token.SUB_OP){
                double a = (double)nextList.get(i-1).value();
                double b = (double)nextList.get(i+1).value();
                i++;
                nextList.set(i, new Lexeme(a-b, Token.INT_LIT));
            }
        }
        result = nextList.getLast();
        return result;
    }
}
