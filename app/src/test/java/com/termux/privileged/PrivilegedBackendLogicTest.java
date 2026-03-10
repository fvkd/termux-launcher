package com.termux.privileged;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import android.os.Build;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = {Build.VERSION_CODES.P})
public class PrivilegedBackendLogicTest {

    @Test
    public void shellBackend_reportsCorrectType() {
        ShellBackend backend = new ShellBackend();
        assertEquals(PrivilegedBackend.Type.SHELL, backend.getType());
    }

    @Test
    public void shellBackend_returnsMinusOneVersion() {
        ShellBackend backend = new ShellBackend();
        assertEquals(-1, backend.getVersion());
    }

    @Test
    public void manager_startsUninitialized() {
        PrivilegedBackendManager manager = PrivilegedBackendManager.getInstance();
        assertEquals(PrivilegedBackendManager.BackendState.UNINITIALIZED, manager.getBackendState());
        assertEquals(-1, manager.getVersion());
    }
}
