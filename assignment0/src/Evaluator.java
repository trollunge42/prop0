import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Evaluator {
    HashMap<String, Double> resultMap = new HashMap<>();

    public Object evaluate(Object[] args) throws Exception {
        int startIndex = 0;
        String id = null;
        for(int i = 0; i<args.length-1;i++){
            if(args[i]==null)
                break;
            Lexeme lex = (Lexeme)args[i];
            if(i==startIndex) {
                id = (String) lex.value();
            }
            if(lex.token()==Token.SEMICOLON){
                lex = parenting(Arrays.copyOfRange(args, startIndex+2, i+0));
                Double val = (Double)lex.value();
                val = Math.round(val*100)/100D;
                resultMap.put(id, val);
                startIndex = i+1;
            }
        }
        StringBuilder str = new StringBuilder();
        for(Map.Entry<String, Double> entry : resultMap.entrySet()){
            str.append(entry.getKey() + " = " + entry.getValue() + "\n");
        }
        return str;
    }

    private Lexeme parenting(Object[] array){
        LinkedList<Lexeme> calcList = new LinkedList<>();
        for(int i = 0; i < array.length; i++){
                Lexeme lex = (Lexeme)array[i];
                if(lex.token()==Token.LEFT_PAREN){
                    for(int j = array.length-1; j > i; j--){
                        Lexeme lex2 = (Lexeme)array[j];
                        if(lex2.token()==Token.RIGHT_PAREN) {
                            lex = parenting(Arrays.copyOfRange(array, i + 1, j));
                            i = j;
                            break;
                        }
                    }
                }
            calcList.add(lex);
        }
        return calculate(calcList);
    }

    private Lexeme calculate(LinkedList<Lexeme> calcList) {
        LinkedList<Lexeme> nextList = new LinkedList<>();
        for(int i = 0; i<calcList.size(); i++){
            if(calcList.get(i).token()==Token.MULT_OP){
                calcList.set(++i, new Lexeme(getValue(calcList.get(i-2)) * getValue(calcList.get(i)),Token.INT_LIT));
                if(nextList.getFirst()==nextList.getLast()){
                    nextList = new LinkedList<>();
                }else
                    nextList.removeLast();
            } else if(calcList.get(i).token()==Token.DIV_OP){
                calcList.set(++i, new Lexeme(getValue(calcList.get(i-2)) / getValue(calcList.get(i)),Token.INT_LIT));
                if(nextList.getFirst()==nextList.getLast()){
                    nextList = new LinkedList<>();
                }else
                    nextList.removeLast();
            }
            nextList.add(calcList.get(i));
        }
        for(int i = 0; i<nextList.size();i++){
            if(nextList.get(i).token()==Token.ADD_OP){
                nextList.set(++i,new Lexeme(getValue(nextList.get(i-2)) + getValue(nextList.get(i)), Token.INT_LIT));
            } else if(nextList.get(i).token()==Token.SUB_OP){
                nextList.set(++i,new Lexeme(getValue(nextList.get(i-2)) - getValue(nextList.get(i)), Token.INT_LIT));
            }
        }
        return nextList.getLast();
    }

    private double getValue(Lexeme lex) {
        if(lex.token()==Token.IDENT){
            return resultMap.get(lex.value());
        }
        return (double)lex.value();
    }
}
