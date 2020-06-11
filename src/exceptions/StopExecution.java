package exceptions;

public class StopExecution extends RuntimeException
{
    private String message;

    public StopExecution(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}


// TODO rediscover exception handling

/*
 // exception principles

 - exceptional situations for your 3 ADTs (Stack, Dictionary and List) operations (e.g.
 writing into a full collection, reading from an empty collection, etc)

 DONE - expressions evaluation: Division by zero, variable not defined in symbol table

 DONE - statements execution: trying to execute when the execution stack is empty

 - you may want to create exception chains, that means you have special exceptions for
 each level of your application architecture: domain exceptions,
                                              repository exceptions,
                                              controller exceptions and
                                              view exceptions.
 When an exception cannot be treated (the situation cannot be recovered) at one level, it is thrown
 an exception to the upper level: for example in controller you can catch the repository exceptions
 and then if it necessary you can throw again the controller exceptions to the view/main
 */