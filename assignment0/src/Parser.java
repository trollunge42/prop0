import java.io.IOException;

public class Parser implements IParser {

    Tokenizer tokenizer = new Tokenizer();

    public Parser(){
    }

    @Override
    public void open(String fileName) throws IOException, TokenizerException {
        tokenizer.open(fileName);
    }

    @Override
    public INode parse() throws IOException, TokenizerException, ParserException {
        return null;
    }

    @Override
    public void close() throws IOException {

    }
}
