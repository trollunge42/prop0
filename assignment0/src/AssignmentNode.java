import java.io.IOException;

public class AssignmentNode implements INode {
    private Lexeme id;
    private Lexeme ass;
    private Lexeme semi;
    private INode exprNode;

    public AssignmentNode(Tokenizer t) throws ParserException, IOException, TokenizerException {
        id=t.current();
        if (id.token()==Token.IDENT){
            t.moveNext();
            Parser.arraySize++;
        } else throw new ParserException("ID missing");
        ass=t.current();
        if(ass.token()==Token.ASSIGN_OP){
            t.moveNext();
            Parser.arraySize++;
        } else throw new ParserException("Assignment missing");
        exprNode = new ExpressionNode(t);
        semi = t.current();
        if(semi.token()==Token.SEMICOLON){
            t.moveNext();
            Parser.arraySize++;
        } else throw new ParserException("Semicolon missing");
        /*if(t.current().token()!=Token.EOF){
            INode assNode = new AssignmentNode(t);
        }
        */
    }

    @Override
    public Object evaluate(Object[] args) throws Exception {
        args[Parser.arrayPlace] = id;
        Parser.arrayPlace++;
        args[Parser.arrayPlace] = ass;
        Parser.arrayPlace++;
        exprNode.evaluate(args);
        args[Parser.arrayPlace] = semi;
        Parser.arrayPlace++;
        return args;
    }

    @Override
    public void buildString(StringBuilder builder, int tabs) {
        for(int i = 0; i < tabs; i++)
            builder.append("\t");
        builder.append(getClass().getSimpleName() + "\n");
        tabs++;
        for(int i = 0; i < tabs; i++)
            builder.append("\t");
        builder.append(id.toString() + "\n");
        for(int i = 0; i < tabs; i++)
            builder.append("\t");
        builder.append(ass.toString() + "\n");
        exprNode.buildString(builder, tabs);
        for(int i = 0; i < tabs; i++)
            builder.append("\t");
        builder.append(semi.toString() +"\n");
    }
}
