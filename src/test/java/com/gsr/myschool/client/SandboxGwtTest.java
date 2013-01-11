package com.gsr.myschool.client;

import com.google.gwt.junit.client.GWTTestCase;

public class SandboxGwtTest extends GWTTestCase {
    @Override
    public String getModuleName() {
        return "com.gsr.myschool.client.MySchool";
    }

    public void testSandbox() {
        assertTrue(true);
    }
}
