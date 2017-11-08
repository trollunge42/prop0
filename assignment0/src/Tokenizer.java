import java.io.IOException;


public class Tokenizer implements ITokenizer{

    Scanner scanner;

    @Override
    public void open(String fileName) throws IOException, TokenizerException {
        scanner = new Scanner();
        scanner.open(fileName);
    }

    @Override
    public Lexeme current() {
        char ch = scanner.current();
        if(Character.isSpaceChar(ch)) {
            try {
                scanner.moveNext();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if(ch==Scanner.EOF){
            return new Lexeme(ch, Token.EOF);
        }else if(Character.isLetter(ch)){
            String str = "";
            while(Character.isLetter(ch)){
                str += ch;
                try {
                    scanner.moveNext();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ch=scanner.current();
            }
            return new Lexeme(str, Token.IDENT);
        } else if(Character.isDigit(ch)){
            String str = "";
            while(Character.isDigit(ch)){
                str += ch;
                try {
                    scanner.moveNext();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ch = scanner.current();
            }
            return new Lexeme(ch, Token.INT_LIT);
        } else if(ch=='='){
            return new Lexeme(ch, Token.ASSIGN_OP);
        } else if(ch=='-'){
            return new Lexeme(ch, Token.SUB_OP);
        } else if(ch=='+'){
            return new Lexeme(ch, Token.ADD_OP);
        } else if(ch=='*'){
            return new Lexeme(ch, Token.MULT_OP);
        } else if(ch=='/'){
            return new Lexeme(ch, Token.DIV_OP);
        } else if(ch=='('){
            return new Lexeme(ch, Token.LEFT_PAREN);
        } else if(ch==')'){
            return new Lexeme(ch, Token.RIGHT_PAREN);
        } else if(ch==';'){
            return new Lexeme(ch, Token.SEMICOLON);
        }
        //TODO curlys?
        return null;
    }

    @Override
    public void moveNext() throws IOException, TokenizerException {
        scanner.moveNext();
     }

    @Override
    public void close() throws IOException {

    }
}
