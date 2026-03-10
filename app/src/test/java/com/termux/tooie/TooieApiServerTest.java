package com.termux.tooie;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import android.os.Build;

import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.lang.reflect.Method;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = {Build.VERSION_CODES.P})
public class TooieApiServerTest {

    @Test
    public void getInstance_returnsNonNull() {
        TooieApiServer server = TooieApiServer.getInstance();
        assertNotNull(server);
    }

    @Test
    public void getInstance_returnsSameInstance() {
        TooieApiServer instance1 = TooieApiServer.getInstance();
        TooieApiServer instance2 = TooieApiServer.getInstance();
        assertSame(instance1, instance2);
    }

    @Test
    public void buildStatus_returnsValidJson() throws Exception {
        TooieApiServer server = TooieApiServer.getInstance();
        Method buildStatusMethod = TooieApiServer.class.getDeclaredMethod("buildStatus");
        buildStatusMethod.setAccessible(true);
        
        JSONObject status = (JSONObject) buildStatusMethod.invoke(server);
        
        assertNotNull(status);
        assertTrue(status.getBoolean("ok"));
        assertTrue(status.has("apiVersion"));
        assertTrue(status.has("backendType"));
        assertTrue(status.has("backendState"));
        assertTrue(status.has("backendVersion"));
        assertTrue(status.has("statusReason"));
        assertTrue(status.has("isPrivilegedAvailable"));
    }
}
