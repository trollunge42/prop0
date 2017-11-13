import java.io.IOException;

public class BlockNode implements INode {

    private Evaluator e;
    private Lexeme leftCurly;
    private Lexeme rightCurly;
    private StatementNode stateNode;

    public BlockNode(Tokenizer t) throws IOException, TokenizerException, ParserException {
        leftCurly = t.current();
        if(leftCurly.token()==Token.LEFT_CURLY){
            t.moveNext();
            Parser.arraySize++;
        } else throw new ParserException("Left curly is missing");
        stateNode = new StatementNode(t);
        rightCurly = t.current();
        if(rightCurly.token()==Token.RIGHT_CURLY){
            Parser.arraySize++;
        } else throw new ParserException("Right curly is missing");
    }

    @Override
    public Object evaluate(Object[] args) throws Exception {
        e = new Evaluator();
        if (args==null){
            args=new Object[Parser.arraySize];
        }
        stateNode.evaluate(args);
        return e.evaluate(args);
    }

    @Override
    public void buildString(StringBuilder builder, int tabs) {
        for(int i = 0; i < tabs; i++)
            builder.append("\t");
        builder.append(getClass().getSimpleName() + "\n");
        tabs++;
        for(int i = 0; i < tabs; i++)
            builder.append("\t");
        builder.append(leftCurly.toString() + "\n");
        stateNode.buildString(builder, tabs);
        for(int i = 0; i < tabs; i++)
            builder.append("\t");
        builder.append(rightCurly.toString() +"\n");
    }
}
