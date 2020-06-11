package model.expression;

import exceptions.interpreterRuntimeExceptions.InvalidOperatorException;
import utils.interfaces.IHeap;
import utils.interfaces.ISymTable;

public class BooleanExp extends Expression
{
    private String operator;
    private Expression operand1;
    private Expression operand2;

    public BooleanExp(String operator, Expression op1, Expression op2) {
        this.operator = operator;
        this.operand1 = op1;
        this.operand2 = op2;
    }


    @Override
    public int evaluate(ISymTable<String, Integer> symtable, IHeap<Integer> heap) {
        int result1 = this.operand1.evaluate(symtable,heap);
        int result2 = this.operand2.evaluate(symtable,heap);
        switch(this.operator) {
            case "<" : return result1 < result2 ? 1 : 0 ;
            case "<=" : return result1 <= result2 ? 1 : 0;
            case "==" : return result1 == result2 ? 1 : 0;
            case "!=" : return result1 != result2 ? 1 : 0;
            case ">" : return result1 > result2 ? 1 : 0;
            case ">=" : return result1 >= result2 ? 1 : 0;
            default: throw new InvalidOperatorException();
        }
    }

    @Override
    public String toString() {
        return this.operand1.toString() + this.operator + this.operand2.toString();
    }
}
