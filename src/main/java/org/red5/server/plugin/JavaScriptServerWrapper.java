package org.red5.server.plugin;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.graalvm.polyglot.Value;
import org.graalvm.polyglot.proxy.ProxyArray;
import org.red5.server.api.IConnection;
import org.red5.server.api.IServer;
import org.red5.server.api.listeners.IConnectionListener;
import org.red5.server.api.listeners.IScopeListener;
import org.red5.server.api.scope.IGlobalScope;
import org.red5.server.api.scope.IScope;

public class JavaScriptServerWrapper {
    protected static Logger log = LoggerFactory.getLogger(JavaScriptServerWrapper.class);
    private final IServer server;
    private final JavaScriptPlugin plugin;
    private List<IConnectionListener> connectionListeners = new ArrayList<IConnectionListener>();
    private List<IScopeListener> scopeListeners = new ArrayList<IScopeListener>();

    public JavaScriptServerWrapper(IServer server, JavaScriptPlugin plugin) {
        this.server = server;
        this.plugin = plugin;
    }

    // public IGlobalScope getGlobal(String name) {
    // return this.server.getGlobal(name);
    // }

    // public IGlobalScope lookupGlobal(String hostName, String contextPath) {
    // return null;
    // }

    // public boolean addMapping(String hostName, String contextPath, String
    // globalName) {
    // return false;
    // }

    // public boolean removeMapping(String hostName, String contextPath) {
    // return false;
    // }

    // public Map<String, String> getMappingTable() {
    // return null;
    // }

    public ProxyArray getGlobalNames() {
        Iterator<String> nameIterator = this.server.getGlobalNames();
        List<String> list = new ArrayList<>();
        nameIterator.forEachRemaining(list::add);

        return new ProxyArray() {
            @Override
            public void set(long index, Value value) {
                throw new UnsupportedOperationException();
            }

            @Override
            public long getSize() {
                return list.size();
            }

            @Override
            public Object get(long index) {
                return list.get((int) index);
            }
        };
    }

    public Iterator<IGlobalScope> getGlobalScopes() {
        return null;
    }

    public int addScopeListener(Value scopeCreatedCallback, Value scopeRemovedCallback) {
        log.debug("addScopeListener");
        IScopeListener listener = new IScopeListener() {

            @Override
            public void notifyScopeCreated(IScope scope) {
                try {
                    plugin.executeInContext(scopeCreatedCallback, scope);
                    // scopeCreatedCallback.execute(scope);
                } catch (Exception e) {
                    log.error("Failed to call scopeRemovedCallback", e);
                }
            }

            @Override
            public void notifyScopeRemoved(IScope scope) {
                plugin.executeInContext(scopeRemovedCallback, scope);
                // scopeRemovedCallback.execute(scope);
            }

        };
        this.server.addListener(listener);
        scopeListeners.add(listener);
        return scopeListeners.indexOf(listener);
    }

    public void removeScopeListener(int index) {
        log.debug("removeScopeListener");
        IScopeListener listener = scopeListeners.get(index);
        if (listener == null) {
            return;
        }
        this.server.removeListener(listener);
        scopeListeners.set(index, null); // don't remove because that would shift the indexes
    }

    public int addConnectListener(Value connectCallback, Value disconnectCallback) {
        log.debug("addConnectListener");
        IConnectionListener listener = new IConnectionListener() {

            @Override
            public void notifyDisconnected(IConnection conn) {
                plugin.executeInContext(disconnectCallback, conn);
                // disconnectCallback.execute(conn);
            }

            @Override
            public void notifyConnected(IConnection conn) {
                plugin.executeInContext(connectCallback, conn);
                // connectCallback.execute(conn);
            }
        };
        this.server.addListener(listener);
        connectionListeners.add(listener);
        return connectionListeners.indexOf(listener);
    }

    public void removeConnectListener(int index) {
        log.debug("removeConnectListener");
        IConnectionListener listener = connectionListeners.get(index);
        if (listener == null) {
            return;
        }
        this.server.removeListener(listener);
        connectionListeners.set(index, null); // don't remove because that would shift the indexes
    }

    public void addDisconnectListener(Value callback) {
        log.debug("addConnectionListener");
    }

    public void removeConnectionListener(Value notifyConnected) {

    }
}