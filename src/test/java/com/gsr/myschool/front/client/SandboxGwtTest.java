package com.gsr.myschool.front.client;

import com.google.gwt.junit.client.GWTTestCase;

public class SandboxGwtTest extends GWTTestCase {
    @Override
    public String getModuleName() {
        return "com.gsr.myschool.front.client.MySchoolFront";
    }

    public void testSandbox() {
        assertTrue(true);
    }
}
