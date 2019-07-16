package org.red5.server.plugin.javascript;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.graalvm.polyglot.Value;
import org.graalvm.polyglot.proxy.ProxyArray;
import org.graalvm.polyglot.proxy.ProxyObject;
import org.red5.server.api.IConnection;
import org.red5.server.api.IServer;
import org.red5.server.api.listeners.IConnectionListener;
import org.red5.server.api.listeners.IScopeListener;
import org.red5.server.api.scope.IGlobalScope;
import org.red5.server.api.scope.IScope;

public class ServerJs {
    protected static Logger log = LoggerFactory.getLogger(ServerJs.class);
    private final IServer server;
    private final JavaScriptPlugin plugin;
    private List<IConnectionListener> connectionListeners = new ArrayList<IConnectionListener>();
    private List<IScopeListener> scopeListeners = new ArrayList<IScopeListener>();

    public ServerJs(IServer server, JavaScriptPlugin plugin) {
        this.server = server;
        this.plugin = plugin;
    }

    public ProxyArray getGlobalNames() {
        return this.createProxyArray(this.server.getGlobalNames());
    }

    private <T> ProxyArray createProxyArray(Iterator<T> iterator) {
        List<T> list = new ArrayList<>();
        iterator.forEachRemaining(list::add);

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

    public int addScopeListener(Value scopeCreatedCallback, Value scopeRemovedCallback) {
        log.debug("addScopeListener");
        IScopeListener listener = new IScopeListener() {

            @Override
            public void notifyScopeCreated(IScope scope) {
                try {
                    plugin.executeInContext(scopeCreatedCallback, scope);
                } catch (Exception e) {
                    log.error("Failed to call scopeRemovedCallback", e);
                }
            }

            @Override
            public void notifyScopeRemoved(IScope scope) {
                plugin.executeInContext(scopeRemovedCallback, scope);
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
            }

            @Override
            public void notifyConnected(IConnection conn) {
                plugin.executeInContext(connectCallback, conn);
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

    public IGlobalScope getGlobal(String name) {
        // TODO: return a Scope wrapper
        return null;
    }

    // public void registerGlobal(IGlobalScope scope) {

    // }

    public IGlobalScope lookupGlobal(String hostName, String contextPath) {
        return null;
    }

    public boolean addMapping(String hostName, String contextPath, String globalName) {
        return this.server.addMapping(hostName, contextPath, globalName);
    }

    public boolean removeMapping(String hostName, String contextPath) {
        return this.server.removeMapping(hostName, contextPath);
    }

    public ProxyObject getMappingTable() {
        Map<String, String> table = this.server.getMappingTable();

        return new ProxyObject() {

            @Override
            public void putMember(String key, Value value) {
                throw new UnsupportedOperationException();
            }

            @Override
            public boolean hasMember(String key) {
                return table.containsKey(key);
            }

            @Override
            public Object getMemberKeys() {
                return new ArrayList(table.keySet());
            }

            @Override
            public Object getMember(String key) {
                return table.get(key);
            }
        };
    }

    // TODO: unable to get this work
    // public ProxyArray getGlobalScopes() {
    // // return createProxyArray(this.server.getGlobalScopes());
    // }

}