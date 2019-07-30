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
import org.red5.server.api.event.IEvent;
import org.red5.server.api.scheduling.IScheduledJob;
import org.red5.server.api.scheduling.ISchedulingService;
import org.red5.server.api.scope.IBasicScope;
import org.red5.server.api.scope.IScope;
import org.red5.server.api.service.IBroadcastStreamService;
import org.red5.server.api.service.IOnDemandStreamService;
import org.red5.server.api.service.IServiceCall;
import org.red5.server.api.service.IStreamSecurityService;
import org.red5.server.api.service.ISubscriberStreamService;
import org.red5.server.api.so.ISharedObject;
import org.red5.server.api.so.ISharedObjectSecurity;
import org.red5.server.api.so.ISharedObjectSecurityService;
import org.red5.server.api.so.ISharedObjectService;
import org.red5.server.api.stream.IBroadcastStream;
import org.red5.server.api.stream.IOnDemandStream;
import org.red5.server.api.stream.IPlayItem;
import org.red5.server.api.stream.IStreamAwareScopeHandler;
import org.red5.server.api.stream.IStreamPlaybackSecurity;
import org.red5.server.api.stream.IStreamPublishSecurity;
import org.red5.server.api.stream.ISubscriberStream;
import org.red5.server.jmx.mxbeans.ApplicationMXBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApplicationJs {
    protected static Logger log = LoggerFactory.getLogger(ApplicationJs.class);
    private final MultiThreadedApplicationAdapter app;
    private final JavaScriptPlugin plugin;
    private final List<IStreamPublishSecurity> publishSecurities = new ArrayList<IStreamPublishSecurity>();
    private final List<IStreamPlaybackSecurity> playbackSecurities = new ArrayList<IStreamPlaybackSecurity>();

    public ApplicationJs(MultiThreadedApplicationAdapter app, JavaScriptPlugin plugin) {
        this.app = app;
        this.plugin = plugin;
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
    public void addListener(IApplication listener) {
    }

    /**
     * Unregister handler that will not get notified about application events any
     * longer.
     * 
     * @param listener object to unregister
     */
    public void removeListener(IApplication listener) {
    }

    public boolean connect(IConnection conn, IScope scope, Object[] params) {
        return false;
    }

    public void disconnect(IConnection conn, IScope scope) {

    }

    public boolean addChildScope(IBasicScope scope) {
        return false;
    }

    public void removeChildScope(IBasicScope scope) {

    }

    public boolean join(IClient client, IScope scope) {
        return false;
    }

    public void leave(IClient client, IScope scope) {

    }

    public boolean serviceCall(IConnection conn, IServiceCall call) {
        return false;
    }

    public boolean handleEvent(IEvent event) {
        return false;
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

    public IBroadcastStream getBroadcastStream(IScope scope, String name) {
        return null;
    }

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