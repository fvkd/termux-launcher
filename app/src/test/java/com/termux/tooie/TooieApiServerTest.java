package com.termux.tooie;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

import android.os.Build;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

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
}
