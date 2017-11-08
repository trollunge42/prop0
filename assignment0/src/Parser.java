import java.io.IOException;

public class Parser implements IParser {
    static int arraySize = 0;
    static int arrayPlace = 0;
    private INode assNode;
    Tokenizer tokenizer;

    public Parser() throws Exception {
    }

    @Override
    public void open(String fileName) throws IOException, TokenizerException {
        tokenizer = new Tokenizer();
        tokenizer.open(fileName);
    }

    @Override
    public INode parse() throws IOException, TokenizerException, ParserException {
        assNode = new AssignmentNode(tokenizer);
        //om prog2, blockNode
        return assNode;
    }

    @Override
    public void close() throws IOException {
        tokenizer.close();
    }
}
