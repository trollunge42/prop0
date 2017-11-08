import java.util.LinkedList;

public class Evaluator {

    public Object evaluate(Object[] args) throws Exception {
        int index = 0;
        Lexeme[] calcArray = new Lexeme[Parser.arraySize];
        LinkedList<Lexeme> calcList = new LinkedList<Lexeme>();
        for(int i = 2; i<Parser.arraySize; i++){
            Lexeme lex = (Lexeme)args[i];
            if(lex.token()==Token.LEFT_PAREN){
                Lexeme
                findParen();
                startIndex = i;
                args[i] = new Lexeme(null, null);
                int k = 0;
                Lexeme[] calcArray2 = new Lexeme[Parser.arraySize-i];
                for(int j = i++; j<Parser.arraySize;i++){
                    Lexeme lex2 = (Lexeme)args[j];
                    k++;
                    if(lex2.token()==Token.RIGHT_PAREN){
                        args[j] = new Lexeme(null,null);
                        break;
                    }
                    calcArray2[k] = lex2;
                    args[j] = new Lexeme(null,null);
                }
                lex = calculate(calcArray2, k);
            }
            calcArray[index] = lex;
            index++;
        }
        String str = "hej";
        return str;
    }

    private Lexeme calculate(Lexeme[] calcArray, int arraySize) {
        Lexeme result;
            for(int i = 0; i<arraySize; i++){
                if(calcArray[i].token()==Token.MULT_OP){
                    int a = (int)calcArray[i-1].value();
                    calcArray[i-1] = new Lexeme(null,null);
                    int b = (int)calcArray[i+1].value();
                    calcArray[i+1] = new Lexeme(null,null);
                    calcArray[i] = new Lexeme(a*b,Token.INT_LIT);
                } else if(calcArray[i].token()==Token.DIV_OP){
                    int a = (int)calcArray[i-1].value();
                    calcArray[i-1] = new Lexeme(null,null);
                    int b = (int)calcArray[i+1].value();
                    calcArray[i+1] = new Lexeme(null,null);
                    calcArray[i] = new Lexeme(a/b,Token.INT_LIT);
                }
            }
            for(int i = 0; i<arraySize;i++){
                if(calcArray[i])
            }
        return result;
    }

}
