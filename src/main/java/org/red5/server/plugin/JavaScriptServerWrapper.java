package org.red5.server.plugin;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.graalvm.polyglot.Value;
import org.graalvm.polyglot.proxy.ProxyArray;
import org.red5.server.api.IServer;
import org.red5.server.api.listeners.IConnectionListener;
import org.red5.server.api.listeners.IScopeListener;
import org.red5.server.api.scope.IGlobalScope;

public class JavaScriptServerWrapper implements IServer {
    private final IServer server;

    public JavaScriptServerWrapper(IServer server) {
        this.server = server;
    }

    public IGlobalScope getGlobal(String name) {
        return this.server.getGlobal(name);
    }

    public void registerGlobal(IGlobalScope scope) {

    }

    public IGlobalScope lookupGlobal(String hostName, String contextPath) {
        return null;
    }

    public boolean addMapping(String hostName, String contextPath, String globalName) {
        return false;
    }

    public boolean removeMapping(String hostName, String contextPath) {
        return false;
    }

    public Map<String, String> getMappingTable() {
        return null;
    }

    public ProxyArray getGlobalScopeNames() {
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

    public void addListener(IScopeListener listener) {

    }

    public void addListener(IConnectionListener listener) {

    }

    public void removeListener(IScopeListener listener) {

    }

    public void removeListener(IConnectionListener listener) {

    }

    @Override
    public Iterator<String> getGlobalNames() {
        return null;
    }
}