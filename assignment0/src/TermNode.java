import java.io.IOException;

public class TermNode implements INode {

    private INode factNode;
    private Lexeme multDiv;
    private INode termNode;

    public TermNode(Tokenizer t) throws TokenizerException, ParserException, IOException {
        factNode = new FactorNode(t);
        if(t.current().token()==Token.DIV_OP||t.current().token()==Token.MULT_OP){
            multDiv = t.current();
            t.moveNext();
            Parser.arraySize++;
            termNode = new TermNode(t);
        }
    }

    @Override
    public Object evaluate(Object[] args) throws Exception {
        factNode.evaluate(args);
        if(multDiv!=null){
            args[Parser.arrayPlace] = multDiv;
            Parser.arrayPlace++;
            termNode.evaluate(args);
        }
        return args;
    }

    @Override
    public void buildString(StringBuilder builder, int tabs) {
        for(int i = 0; i < tabs; i++)
            builder.append("\t");
        builder.append(getClass().getSimpleName() + "\n");
        tabs++;
        factNode.buildString(builder, tabs);
        if(multDiv!=null){
            for(int i = 0; i < tabs; i++)
                builder.append("\t");
            builder.append(multDiv.toString() + "\n");
            termNode.buildString(builder, tabs);
        }
    }
}
