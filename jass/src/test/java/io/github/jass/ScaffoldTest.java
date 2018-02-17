package io.github.jass;
import java.io.File;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

/**
 * Created by jpenren on 26/1/18.
 */
public class ScaffoldTest {

    @Test
    public void test(){

        String out="/Users/jpenren/eclipse-workspace/sb-demo";
        List<String> strings = Collections.singletonList( new File("/Users/jpenren/eclipse-workspace/sb-demo/src").getAbsolutePath());
        new Scaffolder(strings, out).generate();
    }
}
