import java.util.function.Function;

public class Lambdafy {

    public static void main(String[] args){
        final Function<String, String> f = a -> a;
        f.apply("hello");
    }

//    Compiled from "Lambdafy.java"
//    public class Lambdafy {
//        public Lambdafy();
//        Code:
//                0: aload_0
//                1: invokespecial #1                  // Method java/lang/Object."<init>":()V
//                4: return
//
//        public static void main(java.lang.String[]);
//        Code:
//                0: invokedynamic #2,  0              // InvokeDynamic #0:apply:()Ljava/util/function/Function; create new function instance
//                5: astore_1                          // store function instance in local variable 1
//                6: aload_1                           // load  onto the stack
//                7: ldc           #3                  // String hello; push constant on the stack
//                9: invokeinterface #4,  2            // InterfaceMethod java/util/function/Function.apply:(Ljava/lang/Object;)Ljava/lang/Object;
//                14: pop
//                15: return
//
//        private static java.lang.String lambda$main$0(java.lang.String);
//        Code:
//                0: aload_0 // load reference of local variable 0 onto the stack
//                1: areturn // take reference form stack and return it
//    }
}
