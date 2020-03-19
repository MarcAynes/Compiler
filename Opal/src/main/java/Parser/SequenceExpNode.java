package Parser;

import java.util.LinkedList;

public abstract class SequenceExpNode extends ExpNode {

    protected LinkedList<Term> terms;

    public SequenceExpNode() {
        this.terms = new LinkedList<Term>();
    }

    public SequenceExpNode(ExpNode a, boolean positive) {
        this.terms = new LinkedList<Term>();
        this.terms.add(new Term(positive, a));
    }

    public void add(ExpNode a, boolean positive) {
        this.terms.add(new Term(positive, a));
    }

}
