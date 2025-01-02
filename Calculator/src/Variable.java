package cs2110;

import java.util.Set;

public class Variable implements Expression{

    private final String name;

    public Variable(String v) {
        this.name = v;
    }

    /**
     * Returns corresponding name in VarTable if it exists. If not, throws an
     * UnboundVariableException indicating the variable is unbound.
     */

    @Override
    public double eval(VarTable vars) throws UnboundVariableException {
        if (vars.contains(name)) {
            return vars.get(name);
        } else{
            throw new UnboundVariableException("Variable " + name + " is not in VarTable.");
        }
    }

    /**
     * No operations are required to evaluate a Constant's value.
     */
    @Override
    public int opCount() {
        return 0;
    }

    /**
     * Returns The name of the variable.
     */
    @Override
    public String infixString() {
        return this.name;
    }

    /**
     * Returns the name of the variable.
     */
    @Override
    public String postfixString() {
        return this.name;
    }

    /**
     * Returns A Constant expression with the evaluated value if the variable is bound, or this
     * Variable instance if the variable is unbound.
     */
    @Override
    public Expression optimize(VarTable vars) {
        Expression optExpr;
        try {
            optExpr = new Constant(eval(vars));
            return optExpr;
        } catch (UnboundVariableException e) {
            return this;
        }
    }

    /**
     * Returns a Set<String> containing the name of this variable.
     */
    @Override
    public Set<String> dependencies() {
        return Set.of(this.name);
    }

    /**
     * Returns true if the other object is a Variable with the same name, otherwise false.
     */
    @Override
    public boolean equals(Object other){
        if (other.getClass() != getClass()){
            return false;
        }
        Variable x= (Variable) other;
        return x.name.equals(this.name);
    }
}
