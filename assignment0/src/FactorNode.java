import java.io.IOException;

public class FactorNode implements INode {

    private Lexeme intOrId;
    private Lexeme leftParent;
    private INode expressNode;
    private Lexeme rightParent;

    public FactorNode(Tokenizer t) throws ParserException, IOException, TokenizerException {
        if(t.current().token()==Token.INT_LIT || t.current().token()==Token.IDENT){
            intOrId = t.current();
            t.moveNext();
            Parser.arraySize++;
        } else if(t.current().token()==Token.LEFT_PAREN){
            leftParent=t.current();
            t.moveNext();
            Parser.arraySize++;
            expressNode = new ExpressionNode(t);
            rightParent = t.current();
            if(rightParent.token()!=Token.RIGHT_PAREN){
                throw new ParserException("Right parenthesis missing");
            }
            t.moveNext();
            Parser.arraySize++;
        } else throw new ParserException("Empty FactorNode");
    }

    @Override
    public Object evaluate(Object[] args) throws Exception {
        if(intOrId!=null){
            args[Parser.arrayPlace] = intOrId;
            Parser.arrayPlace++;
        } else if(leftParent!=null){
            args[Parser.arrayPlace] = leftParent;
            Parser.arrayPlace++;
            expressNode.evaluate(args);
            args[Parser.arrayPlace] = rightParent;
            Parser.arrayPlace++;
        } else throw new Exception("Felfelfel");
        return args;
    }

    @Override
    public void buildString(StringBuilder builder, int tabs){
        for(int i = 0; i < tabs; i++)
            builder.append("\t");
        builder.append(getClass().getSimpleName() + "\n");
        tabs++;
        if(intOrId!=null){
            for(int i = 0; i < tabs; i++)
                builder.append("\t");
            builder.append(intOrId.toString() + "\n");
        }else if(leftParent!=null) {
            for (int i = 0; i < tabs; i++)
                builder.append("\t");
            builder.append(leftParent.toString() + "\n");
            expressNode.buildString(builder, tabs);
            for (int i = 0; i < tabs; i++)
                builder.append("\t");
            builder.append(rightParent.toString() + "\n");
        }
    }
}
