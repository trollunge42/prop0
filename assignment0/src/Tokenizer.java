import java.io.IOException;


public class Tokenizer implements ITokenizer{

    Scanner scanner;
    private Lexeme current;

    @Override
    public void open(String fileName) throws IOException, TokenizerException {
        scanner = new Scanner();
        scanner.open(fileName);
        scanner.moveNext();
        moveNext();
    }

    @Override
    public Lexeme current() {
        return current;
    }

    @Override
    public void moveNext() throws IOException, TokenizerException {
        char ch = scanner.current();
        while(Character.isSpaceChar(ch)) {
            scanner.moveNext();
            ch = scanner.current();
        }
        if(ch==Scanner.EOF){
            current = new Lexeme(ch, Token.EOF);
        }else if(Character.isLetter(ch)){
            String str = "";
            while(Character.isLetter(ch)){
                str += ch;
                scanner.moveNext();
                ch=scanner.current();
            }
            current = new Lexeme(str, Token.IDENT);
        } else if(Character.isDigit(ch)){
            String str = "";
            while(Character.isDigit(ch)){
                str += ch;
                scanner.moveNext();
                ch = scanner.current();
            }
            current = new Lexeme(Double.valueOf(str).doubleValue(), Token.INT_LIT);
        } else if(ch=='='){
            current = new Lexeme(ch, Token.ASSIGN_OP);
            scanner.moveNext();
        } else if(ch=='-'){
            current = new Lexeme(ch, Token.SUB_OP);
            scanner.moveNext();
        } else if(ch=='+'){
            current = new Lexeme(ch, Token.ADD_OP);
            scanner.moveNext();
        } else if(ch=='*'){
            current = new Lexeme(ch, Token.MULT_OP);
            scanner.moveNext();
        } else if(ch=='/'){
            current = new Lexeme(ch, Token.DIV_OP);
            scanner.moveNext();
        } else if(ch=='('){
            current = new Lexeme(ch, Token.LEFT_PAREN);
            scanner.moveNext();
        } else if(ch==')'){
            current = new Lexeme(ch, Token.RIGHT_PAREN);
            scanner.moveNext();
        } else if(ch==';'){
            current = new Lexeme(ch, Token.SEMICOLON);
            scanner.moveNext();
        } else throw new TokenizerException("Can't tokenize");
        //TODO curlys?
     }

    @Override
    public void close() throws IOException {
        scanner.close();
    }
}
