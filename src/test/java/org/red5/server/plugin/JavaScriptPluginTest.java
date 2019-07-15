package org.red5.server.plugin;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.assertEquals;
import org.red5.server.Server;
import org.red5.server.api.listeners.IScopeListener;
import org.graalvm.polyglot.*;

public class JavaScriptPluginTest {

    private JavaScriptPlugin plugin;

    @Before
    public void setUp() {
        this.plugin = new JavaScriptPlugin("./test-plugin.js");
    }

    @Test
    public void testGetName() {
        String name = this.plugin.getName();
        assertEquals("JS Plugin X", name);
    }

    @Test
    public void testCanCacheAndUseServerInstance() throws Exception {
        this.plugin.setServerWrapper(new JavaScriptServerWrapper(null) {
        });
        this.plugin.doStart();
        this.plugin.doStop();
    }

}