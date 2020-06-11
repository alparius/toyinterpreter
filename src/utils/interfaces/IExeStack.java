package utils.interfaces;

import java.util.ArrayList;
import java.util.Stack;

public interface IExeStack<T>
{
    void push(T item);
    T pop();
    boolean isEmpty();
    int size();

    Stack<T> getclone();

    Stack<T> getContent ();
    void setContent(Stack<T> stack);
}
