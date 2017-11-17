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
        while(Character.isWhitespace(ch)) {
            scanner.moveNext();
            ch = scanner.current();
        }
        if(Character.isLetter(ch)) {
            String str = "";
            while(Character.isLetter(ch)){
                str += ch;
                scanner.moveNext();
                ch=scanner.current();
            }
            current = new Lexeme(str, Token.IDENT);
            str = "";
        } else if(Character.isDigit(ch)) {
            String str = "";
            while(Character.isDigit(ch)){
                str += ch;
                scanner.moveNext();
                ch = scanner.current();
            }
            current = new Lexeme(Double.valueOf(str).doubleValue(), Token.INT_LIT);
        }else {
            switch(ch) {
                case Scanner.EOF:
                    current = new Lexeme(ch, Token.EOF);
                    break;
                case '=':
                    current = new Lexeme(ch, Token.ASSIGN_OP);
                    scanner.moveNext();
                    break;
                case '-':
                    current = new Lexeme(ch, Token.SUB_OP);
                    scanner.moveNext();
                    break;
                case '+':
                    current = new Lexeme(ch, Token.ADD_OP);
                    scanner.moveNext();
                    break;

                case '*':
                    current = new Lexeme(ch, Token.MULT_OP);
                    scanner.moveNext();
                    break;

                case '/':
                    current = new Lexeme(ch, Token.DIV_OP);
                    scanner.moveNext();
                    break;
                case '(':
                    current = new Lexeme(ch, Token.LEFT_PAREN);
                    scanner.moveNext();
                    break;

                case ')':
                    current = new Lexeme(ch, Token.RIGHT_PAREN);
                    scanner.moveNext();
                    break;
                case ';':
                    current = new Lexeme(ch, Token.SEMICOLON);
                    scanner.moveNext();
                    break;
                case '{':
                    current = new Lexeme(ch, Token.LEFT_CURLY);
                    scanner.moveNext();
                    break;
                case '}':
                    current = new Lexeme(ch, Token.RIGHT_CURLY);
                    break;
                default:
                    throw new TokenizerException("Can't tokenize");
            }
        }
    }

    @Override
    public void close() throws IOException {
        scanner.close();
    }
}
