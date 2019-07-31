package org.red5.server.plugin.javascript;

import org.graalvm.polyglot.proxy.ProxyArray;
import org.red5.server.api.IClient;
import org.red5.server.api.IConnection;
import org.red5.server.api.listeners.IConnectionListener;

public class ConnectionJsProxy { //

  private final IConnection conn;
  private final JavaScriptPlugin plugin;

  public ConnectionJsProxy(IConnection conn, JavaScriptPlugin plugin) {
    this.conn = conn;
    this.plugin = plugin;
  }

  public Boolean getBoolAttribute(String name) {
    return this.conn.getBoolAttribute(name);
  }

  public Byte getByteAttribute(String name) {
    return this.conn.getByteAttribute(name);
  }

  public Double getDoubleAttribute(String name) {
    return this.conn.getDoubleAttribute(name);
  }

  public Integer getIntAttribute(String name) {
    return this.conn.getIntAttribute(name);
  }

  public ProxyArray getListAttribute(String name) {
    return JsUtil.createProxyArray(this.conn.getListAttribute(name));
  }

  public Long getLongAttribute(String name) {
    return this.conn.getLongAttribute(name);
  }

  public ProxyArray getSetAttribute(String name) {
    return JsUtil.createProxyArray(this.conn.getSetAttribute(name));
  }

  public Short getShortAttribute(String name) {
    return this.conn.getShortAttribute(name);
  }

  public String getStringAttribute(String name) {
    return this.conn.getStringAttribute(name);
  }

  public ProxyArray getAttributeNames() {
    return JsUtil.createProxyArray(this.conn.getAttributeNames());
  }

  public boolean setAttribute(String name, Object value) {
    return this.conn.setAttribute(name, value);
  }

  public Object getAttribute(String name) {
    return this.conn.getAttribute(name);
  }

  public boolean hasAttribute(String name) {
    return this.conn.hasAttribute(name);
  }

  public boolean removeAttribute(String name) {
    return this.conn.removeAttribute(name);
  }

  public void removeAttributes() {
    this.conn.removeAttributes();
  }

  public int size() {
    return this.conn.size();
  }

  public String getEncoding() {
    return this.conn.getEncoding().toString();
  }

  public String getDuty() {
    return this.conn.getDuty().toString();
  }

  public boolean isConnected() {
    return this.conn.isConnected();
  }

  public void close() {
    this.conn.close();
  }

  public IClient getClient() {
    return this.conn.getClient();
  }

  public String getHost() {
    return this.conn.getHost();
  }

  public String getRemoteAddress() {
    return this.conn.getRemoteAddress();
  }

  public ProxyArray getRemoteAddresses() {
    return JsUtil.createProxyArray(this.conn.getRemoteAddresses());
  }

  public int getRemotePort() {
    return this.conn.getRemotePort();
  }

  public String getPath() {
    return this.conn.getPath();
  }

  public String getSessionId() {
    return this.conn.getSessionId();
  }

  public long getReadBytes() {
    return this.conn.getReadBytes();
  }

  public long getWrittenBytes() {
    return this.conn.getWrittenBytes();
  }

  public long getReadMessages() {
    return this.conn.getReadMessages();
  }

  public long getWrittenMessages() {
    return this.conn.getWrittenMessages();
  }

  public long getDroppedMessages() {
    return this.conn.getDroppedMessages();
  }

  public long getPendingMessages() {
    return this.conn.getPendingMessages();
  }

  public long getClientBytesRead() {
    return this.conn.getClientBytesRead();
  }

  public void ping() {
    this.conn.ping();
  }

  public int getLastPingTime() {
    return this.conn.getLastPingTime();
  }

  public ScopeJs getScope() {
    return new ScopeJs(this.conn.getScope(), this.plugin);
  }

  public ProxyArray getBasicScopes() {
    return JsUtil.createProxyArray(this.conn.getBasicScopes());
  }

  public void setBandwidth(int mbits) {
    this.conn.setBandwidth(mbits);
  }

  public void addListener(IConnectionListener listener) {

  }

  public void removeListener(IConnectionListener listener) {

  }

  public Number getStreamId() {
    return this.conn.getStreamId();
  }

  public void setStreamId(int id) {
    this.conn.setStreamId(id);
  }

  public String getProtocol() {
    return this.conn.getProtocol();
  }

}