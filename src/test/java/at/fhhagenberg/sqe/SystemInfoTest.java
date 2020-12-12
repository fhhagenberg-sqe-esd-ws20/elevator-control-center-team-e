package at.fhhagenberg.sqe;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class SystemInfoTest {
    @Test
    public void testJavaVersion() {
    	assertTrue(SystemInfo.javaVersion().startsWith("13"));
    }

    @Test
    public void testJavafxVersion() {
    	assertTrue(SystemInfo.javafxVersion().startsWith("13"));
    }
}