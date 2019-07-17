package org.red5.server.plugin.javascript;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.graalvm.polyglot.Value;
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

public class ApplicationJs {

    private final MultiThreadedApplicationAdapter app;
    private final JavaScriptPlugin plugin;

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

    public void registerStreamPublishSecurity(Value isPublishAllowedCallback) {
        this.app.registerStreamPublishSecurity(new IStreamPublishSecurity() {
            @Override
            public boolean isPublishAllowed(IScope scope, String name, String mode) {
                return plugin.executeInContext(isPublishAllowedCallback).asBoolean();
            }
        });
    }

    public void unregisterStreamPublishSecurity(IStreamPublishSecurity handler) {

    }

    public void registerStreamPlaybackSecurity(Value isPlaybackAllowedCallback) {
        this.app.registerStreamPlaybackSecurity(new IStreamPlaybackSecurity() {
            @Override
            public boolean isPlaybackAllowed(IScope scope, String name, int start, int length, boolean flushPlaylist) {
                return plugin.executeInContext(isPlaybackAllowedCallback).asBoolean();
            }
        });
    }

    public void unregisterStreamPlaybackSecurity(IStreamPlaybackSecurity handler) {

    }

    public Set<IStreamPublishSecurity> getStreamPublishSecurity() {
        return null;
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

}