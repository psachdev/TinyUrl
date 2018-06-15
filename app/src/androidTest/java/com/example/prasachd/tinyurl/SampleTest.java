package com.example.prasachd.tinyurl;

import org.junit.Test;
import junit.framework.Assert;

public class SampleTest {

    @Test
    public void testSuccessfull() {
        int x = 10;
        Assert.assertEquals(10, x);
    }

    @Test
    public void testFail() {
        int x = 11;
        Assert.assertEquals(10, x);
    }
}
