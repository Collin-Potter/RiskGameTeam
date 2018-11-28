package BaseGameEssentials;

import org.junit.Rule;
import org.junit.Test;
import static org.junit.contrib.java.lang.system.TextFromStandardInputStream.*;
import org.junit.contrib.java.lang.system.TextFromStandardInputStream;
import static org.junit.Assert.*;

public class HelperClassTest {
    @Rule
    public final TextFromStandardInputStream systemInMock
            = emptyStandardInputStream();
    @Test
    public void undo() {
        HelperClass test = new HelperClass();
        systemInMock.provideLines("1");
        test.undo();
        systemInMock.provideLines("3","2");
        test.undo();
    }
}