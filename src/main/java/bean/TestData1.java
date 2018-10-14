package bean;

public class TestData1 {
    private int real;
    private int expect;
    public TestData1(int in_real, int in_expect) {
        real = in_real;
        expect = in_expect;
    }

    public int getReal() {
        return real;
    }

    public void setReal(int real) {
        this.real = real;
    }



    public int getExpect() {
        return expect;
    }

    public void setExpect(int expect) {
        this.expect = expect;
    }




}
