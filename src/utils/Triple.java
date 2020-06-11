package utils;

public class Triple<A,B,C>
{
    private A first;
    private B second;
    private C third;

    public Triple(A first, B second, C third) {
        this.first = first;
        this.second = second;
        this.third = third;
    }

    public A getFirst() {
        return this.first;
    }
    public void setFirst(A first) {
        this.first = first;
    }

    public B getSecond() {
        return this.second;
    }
    public void setSecond(B second) {
        this.second = second;
    }


    public C getThird() {
        return this.third;
    }
    public void setThird(C third) {
        this.third = third;
    }


    @Override
    public String toString() {
        return "(" + this.first + "," + this.second + "," + this.third + ')';
    }
}
