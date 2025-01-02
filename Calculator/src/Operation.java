package cs2110;

import java.util.HashSet;
import java.util.Set;

public class Operation implements Expression{
    private final Operator op;
    private final Expression leftOperand;
    private final Expression rightOperand;

    public Operation(Operator o, Expression left, Expression right){
        this.op = o;
        this.leftOperand = left;
        this.rightOperand = right;
    }
    /**
     *  Returns the result of applying the operation to the evaluated operands.
     */
    @Override
    public double eval(VarTable vars) throws UnboundVariableException {
        double leftValue = leftOperand.eval(vars);
        double rightValue = rightOperand.eval(vars);
        return op.operate(leftValue,rightValue);
    }

    /**
     *  Returns he total number of operations required to evaluate this operation and its operands.
     */
    @Override
    public int opCount() {
        return 1+leftOperand.opCount()+rightOperand.opCount();
    }

    /**
     *  Returns a string representing the operation in infix notation of the UnaryFunction
     *  application.
     */
    @Override
    public String infixString() {
        return "(" + this.leftOperand.infixString()+ " " + this.op.symbol() + " "+
                this.rightOperand.infixString() + ")";
    }

    /**
     * Returns String that represents the postfix notation of the UnaryFunction application.
     */
    @Override
    public String postfixString() {
        return this.leftOperand.postfixString() + " " + this.rightOperand.postfixString() + " " +
                this.op.symbol();
    }

    /**
     * Returns a Constant expression with the evaluated result if possible, otherwise this operation.
     */
    @Override
    public Expression optimize(VarTable vars) {
        Expression optExpr;
        try {
            optExpr = new Constant(eval(vars));
            return optExpr;
        } catch (UnboundVariableException e) {
            Operation o = new Operation(this.op, this.leftOperand, this.rightOperand);
            return o;
        }
    }

    /**
     * Returns a Set of strings representing the unique variable names upon which this operation'
     * operands depend.
     */
    @Override
    public Set<String> dependencies() {
        Set<String> depend = new HashSet<>();
        depend.addAll(rightOperand.dependencies());
        depend.addAll(leftOperand.dependencies());
        return depend;
    }

    /**
     * Returns true if the specified object is an equal operation, otherwise false.
     */
    public boolean equals(Object other) {
        if (other == null || other.getClass() != getClass()) {
            return false;
        }

        Operation o = (Operation) other;
        return (o.op.equals(this.op) && o.leftOperand.equals(this.leftOperand) &&
                o.rightOperand.equals(this.rightOperand));
    }
}
