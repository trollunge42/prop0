import java.io.IOException;

public class Parser implements IParser {
    static int arraySize = 0;
    static int arrayPlace = 0;
    private INode blockNode;
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
        blockNode = new BlockNode(tokenizer);
        //om prog2, blockNode
        return blockNode;
    }

    @Override
    public void close() throws IOException {
        tokenizer.close();
    }
}
