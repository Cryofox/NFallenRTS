package coffeefoxstudios.tests;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertNotNull;

/**
 * Created by Ryder Stancescu on 4/16/2017.
    Wrapper for TestNG Asserts. This way an assertionCount can be maintained
 and an AfterSuite can be used to throw a non-zero StatusCode.

 */
public class CustomAssertions {
    @AfterSuite
    public void ThrowBuildError()
    {
        System.out.println("AssertCount:"+assertionCount);
        if(assertionCount>0)
            System.exit(-1);
    }

    private static int assertionCount = 0;

    public static<T> void assertEquals(T obj1, T obj2) {
        try {
            Assert.assertEquals(obj1, obj2);
        } catch (AssertionError e) {
            assertionCount++;
            throw e;
        }
    }
    public static<T> void assertNotEquals(T obj1, T obj2) {
        try {
            Assert.assertNotEquals(obj1, obj2);
        } catch (AssertionError e) {
            assertionCount++;
            throw e;
        }
    }
    public static<T> void assertSame(T obj1, T obj2) {
        try {
            Assert.assertSame(obj1, obj2);
        } catch (AssertionError e) {
            assertionCount++;
            throw e;
        }
    }
    public static<T> void assertNull(T obj) {
        try {
            Assert.assertNull(obj);
        } catch (AssertionError e) {
            assertionCount++;
            throw e;
        }
    }
    public static<T> void assertNotNull(T obj) {
        try {
            Assert.assertNotNull(obj);
        } catch (AssertionError e) {
            assertionCount++;
            throw e;
        }
    }
    public static void assertTrue(boolean condition) {
        try {
            Assert.assertTrue(condition);
        } catch (AssertionError e) {
            assertionCount++;
            throw e;
        }
    }
    public static void assertFalse(boolean condition) {
        try {
            Assert.assertFalse(condition);
        } catch (AssertionError e) {
            assertionCount++;
            throw e;
        }
    }
}



