package org.red5.server.plugin.javascript;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.graalvm.polyglot.proxy.ProxyArray;
import org.red5.server.adapter.MultiThreadedApplicationAdapter;
import org.red5.server.api.IClient;
import org.red5.server.api.IConnection;
import org.red5.server.api.IContext;
import org.red5.server.api.event.IEvent;
import org.red5.server.api.event.IEventListener;
import org.red5.server.api.persistence.IPersistenceStore;
import org.red5.server.api.scope.IBasicScope;
import org.red5.server.api.scope.IBroadcastScope;
import org.red5.server.api.scope.IScope;
import org.red5.server.api.scope.IScopeHandler;
import org.red5.server.api.scope.IScopeSecurityHandler;
import org.red5.server.api.scope.ScopeType;
import org.red5.server.api.statistics.IScopeStatistics;
import org.springframework.core.io.Resource;

public class ScopeJs {

    private final IScope scope;
    private final JavaScriptPlugin plugin;

    public ScopeJs(IScope scope, JavaScriptPlugin plugin) {
        this.scope = scope;
        this.plugin = plugin;
    }

    /**
     * Get scope names having the specified type.
     * 
     * @param type one of UNDEFINED, GLOBAL, APPLICATION, ROOM, BROADCAST,
     *             SHARED_OBJECT
     * @return
     */
    public ProxyArray getBasicScopeNames(String type) {
        Set<String> scopes = this.scope.getBasicScopeNames(ScopeType.valueOf(type));
        return JsUtil.createProxyArray(scopes);
    }

    public ApplicationJs getApplication() {
        return new ApplicationJs((MultiThreadedApplicationAdapter) this.scope.getHandler(), this.plugin);
    }

    public IScopeHandler getHandler() {
        return null;
    }

    public boolean hasParent() {
        return false;
    }

    public IScope getParent() {
        return null;
    }

    public int getDepth() {
        return 0;
    }

    public String getName() {
        return this.scope.getName();
    }

    public IPersistenceStore getStore() {
        return null;
    }

    public String getPath() {
        return null;
    }

    public ScopeType getType() {
        return this.scope.getType();
    }

    public void setKeepDelay(int keepDelay) {

    }

    public boolean isValid() {
        return false;
    }

    public boolean isConnectionAllowed(IConnection conn) {
        return false;
    }

    public boolean isScopeAllowed(IScope scope) {
        return false;
    }

    public void setSecurityHandlers(Set<IScopeSecurityHandler> securityHandlers) {

    }

    public void dispatchEvent(IEvent event) {

    }

    public boolean handleEvent(IEvent event) {
        return false;
    }

    public void notifyEvent(IEvent event) {

    }

    public boolean addEventListener(IEventListener listener) {
        return false;
    }

    public boolean removeEventListener(IEventListener listener) {
        return false;
    }

    public Set<IEventListener> getEventListeners() {
        return null;
    }

    public Resource[] getResources(String arg0) throws IOException {
        return null;
    }

    public ClassLoader getClassLoader() {
        return null;
    }

    public Resource getResource(String arg0) {
        return null;
    }

    public void registerServiceHandler(String name, Object handler) {

    }

    public void unregisterServiceHandler(String name) {

    }

    public Object getServiceHandler(String name) {
        return null;
    }

    public Set<String> getServiceHandlerNames() {
        return null;
    }

    public boolean hasChildScope(String name) {
        return false;
    }

    public boolean hasChildScope(ScopeType type, String name) {
        return false;
    }

    public boolean createChildScope(String name) {
        return false;
    }

    public boolean addChildScope(IBasicScope scope) {
        return false;
    }

    public void removeChildScope(IBasicScope scope) {

    }

    public void removeChildren() {

    }

    public Set<String> getScopeNames() {
        return null;
    }

    public IBroadcastScope getBroadcastScope(String name) {
        return null;
    }

    public IBasicScope getBasicScope(String name) {
        return null;
    }

    public IBasicScope getBasicScope(ScopeType type, String name) {
        return null;
    }

    public IScope getScope(String name) {
        return null;
    }

    public Set<IClient> getClients() {
        return null;
    }

    public Collection<Set<IConnection>> getConnections() {
        return null;
    }

    public Set<IConnection> getClientConnections() {
        return null;
    }

    public Set<IConnection> lookupConnections(IClient client) {
        return null;
    }

    public IConnection lookupConnection(IClient client) {
        return null;
    }

    public IContext getContext() {
        return null;
    }

    public boolean hasHandler() {
        return false;
    }

    public String getContextPath() {
        return null;
    }

    public boolean connect(IConnection conn) {
        return false;
    }

    public boolean connect(IConnection conn, Object[] params) {
        return false;
    }

    public void disconnect(IConnection conn) {

    }

    public IScopeStatistics getStatistics() {
        return null;
    }

    public boolean setAttribute(String name, Object value) {
        return false;
    }

    public Object getAttribute(String name) {
        return null;
    }

    public boolean hasAttribute(String name) {
        return false;
    }

    public boolean removeAttribute(String name) {
        return false;
    }

    public Set<String> getAttributeNames() {
        return null;
    }

    public Map<String, Object> getAttributes() {
        return null;
    }

}