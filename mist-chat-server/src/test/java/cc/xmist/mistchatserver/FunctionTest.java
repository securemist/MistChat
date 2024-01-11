package cc.xmist.mistchatserver;

import org.junit.Test;

import java.util.function.Function;

public class FunctionTest {

    @Test
    public void testFunction(){
        Function<String,String > function = a -> a + " is ok";
        function.apply("hello")
;    }
}
