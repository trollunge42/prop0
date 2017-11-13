import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class ExpressionNode implements INode {
    private INode term;
    private Lexeme addSub;
    private INode express;

    public ExpressionNode(Tokenizer t) throws IOException, TokenizerException, ParserException {
        term = new TermNode(t);
        if(t.current().token()==Token.SUB_OP||t.current().token()==Token.ADD_OP){
            addSub = t.current();
            t.moveNext();
            Parser.arraySize++;
            express = new ExpressionNode(t);
        }
    }
    @Override
    public Object evaluate(Object[] args) throws Exception {
        term.evaluate(args);
        if(addSub!=null){
            args[Parser.arrayPlace] = addSub;
            Parser.arrayPlace++;
            express.evaluate(args);
        }
        return args;
    }

    @Override
    public void buildString(StringBuilder builder, int tabs) {
        for(int i = 0; i < tabs; i++)
            builder.append("\t");
        builder.append(getClass().getSimpleName() + "\n");
        tabs++;
        term.buildString(builder, tabs);
        if(addSub!=null){
            for(int i = 0; i < tabs; i++)
                builder.append("\t");
            builder.append(addSub.toString()+"\n");
            express.buildString(builder,tabs);
        }
    }
}
