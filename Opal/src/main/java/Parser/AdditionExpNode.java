package Parser;

public class AdditionExpNode extends SequenceExpNode {

    public AdditionExpNode() {
        super();
    }

    public AdditionExpNode(ExpNode a,
                                  boolean positive) {
        super(a, positive);
    }

    @Override
    public int getType() {
        return ExpNode.ADDITION_NODE;
    }

    @Override
    public double getValue() {
        double sum = 0.0;
        for (Term t : terms) {
            if (t.positive)
                sum += t.expression.getValue();
            else
                sum -= t.expression.getValue();
        }
        return sum;
    }
}
