package utils;

import model.statement.CompoundStmt;
import utils.interfaces.IExeStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

public class ExeStack<T> implements IExeStack<T> {
    private Stack<T> stack;

    public ExeStack() {
        this.stack = new Stack<>();
    }


    @Override
    public void push(T item) {
        this.stack.push((item));
    }

    @Override
    public T pop() {
        return this.stack.pop();
    }

    @Override
    public boolean isEmpty() {
        return this.stack.isEmpty();
    }

    @Override
    public int size() {
        return this.stack.size();
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("{ ");

        Stack<T> rev = (Stack<T>) this.stack.clone();
        Collections.reverse(rev);
        for (T item : rev) {
            result.append(item.toString()).append(" | ");
        }

        if (result.length() > 4)
            result = new StringBuilder(result.substring(0, result.length() - 3));
        result.append(" }");
        return result.toString();

//        String result = "{ ";
//
//        Stack<T> rev = (Stack<T>) this.stack.clone();
//        Collections.reverse(rev);
//        for (T item : rev) {
//            result += item.toString() + " | ";
//        }
//
//        if (result.length() > 4)
//            result = result.substring(0, result.length() - 3);
//        result += " }";
//        return result;
    }


    @Override
    public Stack<T> getclone() {
        return (Stack<T>) this.stack.clone();
    }


    @Override
    public Stack<T> getContent() {
        return this.stack;
    }

    @Override
    public void setContent(Stack<T> stack) {
        this.stack = stack;
    }
}


