import java.io.IOException;

public class StatementNode implements INode {

    private AssignmentNode assNode;
    private StatementNode stateNode;

    public StatementNode(Tokenizer t) throws TokenizerException, ParserException, IOException {
        assNode = new AssignmentNode(t);
        if(t.current().token()!=Token.RIGHT_CURLY){
            stateNode = new StatementNode(t);
        }
    }

    @Override
    public Object evaluate(Object[] args) throws Exception {
        assNode.evaluate(args);
        if(stateNode!=null)
            stateNode.evaluate(args);
        return args;
    }

    @Override
    public void buildString(StringBuilder builder, int tabs) {
        for(int i = 0; i < tabs; i++)
            builder.append("\t");
        builder.append(getClass().getSimpleName() + "\n");
        assNode.buildString(builder, tabs+1);
        if(stateNode!=null){
            stateNode.buildString(builder, tabs+1);
        }
    }
}
