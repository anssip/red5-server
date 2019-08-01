package org.red5.server.plugin.javascript;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.graalvm.polyglot.Value;
import org.graalvm.polyglot.proxy.ProxyArray;
import org.red5.codec.IStreamCodecInfo;
import org.red5.server.api.scope.IScope;
import org.red5.server.api.stream.IBroadcastStream;
import org.red5.server.api.stream.IStreamListener;
import org.red5.server.api.stream.IStreamPacket;
import org.red5.server.api.stream.ResourceExistException;
import org.red5.server.api.stream.ResourceNotFoundException;
import org.red5.server.messaging.IProvider;
import org.red5.server.net.rtmp.event.Notify;

public class BroadcastStreamJsProxy { // implements IBroadcastStream

  private final IBroadcastStream stream;
  private final JavaScriptPlugin plugin;
  private final List<IStreamListener> listeners = new ArrayList<IStreamListener>();

  public BroadcastStreamJsProxy(IBroadcastStream stream, JavaScriptPlugin plugin) {
    this.stream = stream;
    this.plugin = plugin;
  }

  public String getName() {
    return this.stream.getName();
  }

  public IStreamCodecInfo getCodecInfo() {
    return this.stream.getCodecInfo();
  }

  public ScopeJs getScope() {
    return new ScopeJs(this.stream.getScope(), this.plugin);
  }

  public void start() {
    this.stream.start();
  }

  public void stop() {
    this.stream.stop();
  }

  public void close() {
    this.stream.close();
  }

  public long getCreationTime() {
    return this.stream.getCreationTime();
  }

  public long getStartTime() {
    return this.stream.getStartTime();
  }

  public void saveAs(String filePath, boolean isAppend)
      throws IOException, ResourceNotFoundException, ResourceExistException {
    this.stream.saveAs(filePath, isAppend);
  }

  public String getSaveFilename() {
    return this.stream.getSaveFilename();
  }

  public IProvider getProvider() {
    return this.stream.getProvider();
  }

  public String getPublishedName() {
    return this.stream.getPublishedName();
  }

  public void setPublishedName(String name) {
    this.stream.setPublishedName(name);
  }

  public int addStreamListener(Value packetReceivedCallback) {
    IStreamListener adapter = new IStreamListener() {

      @Override
      public void packetReceived(IBroadcastStream stream, IStreamPacket packet) {
        plugin.executeInContext(packetReceivedCallback, new BroadcastStreamJsProxy(stream, plugin), packet);
      }
    };
    this.stream.addStreamListener(adapter);
    this.listeners.add(adapter);
    return this.listeners.indexOf(adapter);
  }

  public void removeStreamListener(int index) {
    IStreamListener listener = this.listeners.get(index);
    if (listener == null) {
      return;
    }
    this.stream.removeStreamListener(listener);
    this.listeners.set(index, null); // don't remove because that would shift the indexes
  }

  public ProxyArray getStreamListeners() {
    return JsUtil.createProxyArray(this.stream.getStreamListeners());
  }

  public Notify getMetaData() {
    return this.stream.getMetaData();
  }
}