package Parser;

public class Term {

    public boolean positive;
    public ExpNode expression;

    public Term(boolean positive, ExpNode expression) {
        super();
        this.positive = positive;
        this.expression = expression;
    }

}
