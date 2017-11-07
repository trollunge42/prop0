import java.io.IOException;

public class Parser implements IParser {

    Tokenizer tokenizer;

    public Parser(){
    }

    @Override
    public void open(String fileName) throws IOException, TokenizerException {
        tokenizer = new Tokenizer();
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
