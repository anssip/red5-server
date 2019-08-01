package org.red5.server.plugin.javascript;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.graalvm.polyglot.Value;
import org.red5.server.adapter.IApplication;
import org.red5.server.adapter.MultiThreadedApplicationAdapter;
import org.red5.server.api.IClient;
import org.red5.server.api.IConnection;
import org.red5.server.api.scheduling.IScheduledJob;
import org.red5.server.api.scope.IScope;
import org.red5.server.api.so.ISharedObject;
import org.red5.server.api.so.ISharedObjectSecurity;
import org.red5.server.api.stream.IBroadcastStream;
import org.red5.server.api.stream.IOnDemandStream;
import org.red5.server.api.stream.IPlayItem;
import org.red5.server.api.stream.IStreamPlaybackSecurity;
import org.red5.server.api.stream.IStreamPublishSecurity;
import org.red5.server.api.stream.ISubscriberStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApplicationJs {
    protected static Logger log = LoggerFactory.getLogger(ApplicationJs.class);
    private final MultiThreadedApplicationAdapter app;
    private final JavaScriptPlugin plugin;
    private final List<IStreamPublishSecurity> publishSecurities = new ArrayList<IStreamPublishSecurity>();
    private final List<IStreamPlaybackSecurity> playbackSecurities = new ArrayList<IStreamPlaybackSecurity>();
    private final List<IApplication> listeners = new ArrayList<IApplication>();
    private final IScope handledScope;

    public ApplicationJs(MultiThreadedApplicationAdapter app, JavaScriptPlugin plugin, IScope scope) {
        this.app = app;
        this.plugin = plugin;
        this.handledScope = scope;
    }

    public boolean start(IScope scope) {
        throw new UnsupportedOperationException();
    }

    public void stop(IScope scope) {
        throw new UnsupportedOperationException();
    }

    public int registerStreamPublishSecurity(Value isPublishAllowedCallback) {
        IStreamPublishSecurity security = new IStreamPublishSecurity() {
            @Override
            public boolean isPublishAllowed(IScope scope, String name, String mode) {
                boolean allowed = plugin.executeInContext(isPublishAllowedCallback).asBoolean();
                log.debug("Is publish allowed by the JS plugin? " + allowed);
                return allowed;
            }
        };
        this.app.registerStreamPublishSecurity(security);
        publishSecurities.add(security);
        return publishSecurities.indexOf(security);
    }

    public void unregisterStreamPublishSecurity(int index) {
        log.debug("unregisterStreamPublishSecurity");
        IStreamPublishSecurity security = publishSecurities.get(index);
        if (security == null) {
            return;
        }
        this.app.unregisterStreamPublishSecurity(security);
        publishSecurities.set(index, null); // don't remove because that would shift the indexes
    }

    public int registerStreamPlaybackSecurity(Value isPlaybackAllowedCallback) {
        IStreamPlaybackSecurity security = new IStreamPlaybackSecurity() {
            @Override
            public boolean isPlaybackAllowed(IScope scope, String name, int start, int length, boolean flushPlaylist) {
                return plugin.executeInContext(isPlaybackAllowedCallback).asBoolean();
            }
        };
        this.app.registerStreamPlaybackSecurity(security);
        playbackSecurities.add(security);
        return playbackSecurities.indexOf(security);
    }

    public void unregisterStreamPlaybackSecurity(int index) {
        log.debug("unregisterStreamPlaybackSecurity");
        IStreamPlaybackSecurity security = playbackSecurities.get(index);
        if (security == null) {
            return;
        }
        this.app.unregisterStreamPlaybackSecurity(security);
        playbackSecurities.set(index, null); // don't remove because that would shift the indexes
    }

    public Set<IStreamPublishSecurity> getStreamPublishSecurity() {
        return new HashSet<IStreamPublishSecurity>(publishSecurities);
    }

    /**
     * Register a listener that will get notified about application events.
     * 
     * TODO: to intercept connections you can add an IApplication listener.
     * https://github.com/rajdeeprath/red5-development-series/blob/master/code-examples/server-side/red5-plugin-examples/hello-red5-plugin/src/main/java/org/red5/server/plugin/examples/hellored5/HelloRed5Plugin.java
     * 
     * @param listener object to register
     */
    public int addListener(Value listener) {

        IApplication adapter = new IApplication() {
            private boolean execute(Value jsObject, String memberName, Object... args) {
                if (jsObject == null)
                    return true;
                Value member = jsObject.getMember(memberName);
                if (member == null) {
                    return true;
                }
                Value result = plugin.executeInContext(member, app);
                return result != null && result.isBoolean() ? result.asBoolean() : true;
            }

            @Override
            public boolean appStart(IScope app) {
                return execute(listener, "appStart", app);
            }

            @Override
            public boolean appConnect(IConnection conn, Object[] params) {
                return execute(listener, "appConnect", conn, params);
            }

            @Override
            public boolean appJoin(IClient client, IScope app) {
                return execute(listener, "appJoin", client, app);
            }

            @Override
            public void appDisconnect(IConnection conn) {
                execute(listener, "appDisconnect", conn);
            }

            @Override
            public void appLeave(IClient client, IScope app) {
                execute(listener, "appLeave", client, app);
            }

            @Override
            public void appStop(IScope app) {
                execute(listener, "appStop", app);
            }

            @Override
            public boolean roomStart(IScope room) {
                return execute(listener, "roomStart", room);
            }

            @Override
            public boolean roomConnect(IConnection conn, Object[] params) {
                return execute(listener, "roomConnect", conn, params);
            }

            @Override
            public boolean roomJoin(IClient client, IScope room) {
                return execute(listener, "roomJoin", client, room);
            }

            @Override
            public void roomDisconnect(IConnection conn) {
                execute(listener, "roomDisconnect", conn);
            }

            @Override
            public void roomLeave(IClient client, IScope room) {
                execute(listener, "roomLeave", client, room);
            }

            @Override
            public void roomStop(IScope room) {
                execute(listener, "roomStop", room);
            }
        };
        this.app.addListener(adapter);
        this.listeners.add(adapter);
        return this.listeners.indexOf(adapter);
    }

    /**
     * Unregister handler that will not get notified about application events any
     * longer.
     * 
     * @param listener object to unregister
     */
    public void removeListener(int index) {
        IApplication listener = this.listeners.get(index);
        if (listener == null) {
            return;
        }
        this.app.removeListener(listener);
        this.listeners.set(index, null); // don't remove because that would shift the indexes
    }

    public void streamPublishStart(IBroadcastStream stream) {
    }

    public void streamRecordStart(IBroadcastStream stream) {
    }

    public void streamRecordStop(IBroadcastStream stream) {
    }

    public void streamBroadcastStart(IBroadcastStream stream) {
    }

    public void streamBroadcastClose(IBroadcastStream stream) {
    }

    public void streamSubscriberStart(ISubscriberStream stream) {
    }

    public void streamSubscriberClose(ISubscriberStream stream) {
    }

    public void streamPlayItemPlay(ISubscriberStream stream, IPlayItem item, boolean isLive) {
    }

    public void streamPlayItemStop(ISubscriberStream stream, IPlayItem item) {
    }

    public void streamPlayItemPause(ISubscriberStream stream, IPlayItem item, int position) {
    }

    public void streamPlayItemResume(ISubscriberStream stream, IPlayItem item, int position) {
    }

    public void streamPlayItemSeek(ISubscriberStream stream, IPlayItem item, int position) {
    }

    public void registerSharedObjectSecurity(ISharedObjectSecurity handler) {
    }

    public void unregisterSharedObjectSecurity(ISharedObjectSecurity handler) {
    }

    public Set<ISharedObjectSecurity> getSharedObjectSecurity() {
        return null;
    }

    public Set<IStreamPlaybackSecurity> getStreamPlaybackSecurity() {
        return null;
    }

    public String addScheduledJob(int interval, IScheduledJob job) {
        return null;
    }

    public String addScheduledOnceJob(long timeDelta, IScheduledJob job) {
        return null;
    }

    public String addScheduledOnceJob(Date date, IScheduledJob job) {
        return null;
    }

    public String addScheduledJobAfterDelay(int interval, IScheduledJob job, int delay) {
        return null;
    }

    public void pauseScheduledJob(String name) {

    }

    public void resumeScheduledJob(String name) {

    }

    public void removeScheduledJob(String name) {

    }

    public List<String> getScheduledJobNames() {
        return null;
    }

    public ISubscriberStream getSubscriberStream(IScope scope, String name) {
        return null;
    }

    public boolean hasOnDemandStream(IScope scope, String name) {
        return false;
    }

    public IOnDemandStream getOnDemandStream(IScope scope, String name) {
        return null;
    }

    public boolean hasBroadcastStream(IScope scope, String name) {
        return false;
    }

    public BroadcastStreamJsProxy getBroadcastStream(IScope scope, String name) {
        return new BroadcastStreamJsProxy(this.app.getBroadcastStream(scope, name), this.plugin);
    }

    // public BroadcastStreamJsProxy getBroadcastStream(String name) {
    // return new BroadcastStreamJsProxy(this.app.getBroadcastStream(handledScope,
    // name), this.plugin);
    // }

    public Set<String> getBroadcastStreamNames(IScope scope) {
        return null;
    }

    public Set<String> getSharedObjectNames(IScope scope) {
        return null;
    }

    public boolean createSharedObject(IScope scope, String name, boolean persistent) {
        return false;
    }

    public ISharedObject getSharedObject(IScope scope, String name) {
        return null;
    }

    public ISharedObject getSharedObject(IScope scope, String name, boolean persistent) {
        return null;
    }

    public boolean hasSharedObject(IScope scope, String name) {
        return false;
    }

    public boolean clearSharedObjects(IScope scope, String name) {
        return false;
    }

    public boolean appStart(IScope app) {
        return false;
    }

    public boolean appConnect(IConnection conn, Object[] params) {
        return false;
    }

    public boolean appJoin(IClient client, IScope app) {
        return false;
    }

    public void appDisconnect(IConnection conn) {

    }

    public void appLeave(IClient client, IScope app) {

    }

    public void appStop(IScope app) {

    }

    public boolean roomStart(IScope room) {
        return false;
    }

    public boolean roomConnect(IConnection conn, Object[] params) {
        return false;
    }

    public boolean roomJoin(IClient client, IScope room) {
        return false;
    }

    public void roomDisconnect(IConnection conn) {

    }

    public void roomLeave(IClient client, IScope room) {

    }

    public void roomStop(IScope room) {

    }

}