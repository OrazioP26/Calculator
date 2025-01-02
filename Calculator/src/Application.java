package cs2110;

import java.util.Set;

public class Application implements Expression{
    private final UnaryFunction func;
    private final Expression argument;

    /**
     * Constructs an Application instance with a specified UnaryFunction and its argument.
     */
    public Application(UnaryFunction u, Expression a) {
        this.func = u;
        this.argument = a;
    }

    /**
     * Returns value if the variable is bound in the VarTable, Otherwise, an
     * UnboundVariableException is thrown.
     */
    @Override
    public double eval(VarTable vars) throws UnboundVariableException {
        double subExp = argument.eval(vars);
        return func.apply(subExp);
    }

    /**
     * Returns The total number of operations.
     */
    @Override
    public int opCount() {
        return 1 + argument.opCount();
    }

    /**
     *  Returns a string representing the operation in infix notation of the UnaryFunction
     *  application.
     */
    @Override
    public String infixString() {
        return this.func.name() + "(" + this.argument.infixString() + ")";
    }

    /**
     * Returns String that represents the postfix notation of the UnaryFunction application.
     */
    @Override
    public String postfixString() {
        return this.argument.postfixString() + " " + this.func.name() + "()";
    }

    /**
     * Returns an optimized Expression, either a Constant if evaluation succeeds or a new
     * Application with an optimized argument if any variable in the argument is unbound.
     */
    @Override
    public Expression optimize(VarTable vars) {
        Expression optExpr;
        try{
            optExpr = new Constant(eval(vars));
            return optExpr;
        } catch (UnboundVariableException e) {
            Application a = new Application(this.func, this.argument.optimize(vars));
            return a;
        }

    }

    /**
     * Returns A Set<String> containing the names of variables upon which the argument depends.
     */
    @Override
    public Set<String> dependencies() {
        return this.argument.dependencies();
    }

    /**
     * Returns true if the specified object is an equal operation, otherwise false.
     */
    public boolean equals(Object other){
        if (other == null || other.getClass() != getClass()) {
            return false;
        }
        Application a = (Application) other;
        return (a.func.equals(this.func) && a.argument.equals(this.argument));
    }
}
