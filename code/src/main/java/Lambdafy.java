import java.util.function.Function;

public class Lambdafy {

    public static void main(String[] args){
        final Function<String, String> f = a -> a;
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
//                0: invokedynamic #2,  0              // InvokeDynamic #0:apply:()Ljava/util/function/Function;
//                5: astore_1
//                6: return
//
//        private static java.lang.String lambda$main$0(java.lang.String);
//        Code:
//                0: aload_0
//                1: areturn
//    }
}
