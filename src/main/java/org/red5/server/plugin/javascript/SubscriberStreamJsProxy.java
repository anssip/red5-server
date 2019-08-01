package org.red5.server.plugin.javascript;

import java.io.IOException;

import org.graalvm.polyglot.Value;
import org.red5.codec.IStreamCodecInfo;
import org.red5.server.api.scheduling.IScheduledJob;
import org.red5.server.api.scope.IScope;
import org.red5.server.api.stream.IStreamCapableConnection;
import org.red5.server.api.stream.ISubscriberStream;
import org.red5.server.api.stream.OperationNotSupportedException;
import org.red5.server.api.stream.StreamState;

public class SubscriberStreamJsProxy { // implements ISubscriberStream

  private final ISubscriberStream stream;
  private final JavaScriptPlugin plugin;
  private Value onChangeCb;

  public SubscriberStreamJsProxy(ISubscriberStream stream, JavaScriptPlugin plugin) {
    this.stream = stream;
    this.plugin = plugin;
  }

  public Number getStreamId() {
    return this.stream.getStreamId();
  }

  public IStreamCapableConnection getConnection() {
    return this.stream.getConnection();
  }

  public void setClientBufferDuration(int bufferTime) {
    this.stream.setClientBufferDuration(bufferTime);
  }

  public int getClientBufferDuration() {
    return this.stream.getClientBufferDuration();
  }

  public void setBroadcastStreamPublishName(String streamName) {
    this.stream.setBroadcastStreamPublishName(streamName);
  }

  public String getBroadcastStreamPublishName() {
    return this.stream.getBroadcastStreamPublishName();
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

  public void close() {
    this.stream.close();
  }

  public long getCreationTime() {
    return this.stream.getCreationTime();
  }

  public long getStartTime() {
    return this.stream.getStartTime();
  }

  public void play() throws IOException {
    this.stream.play();
  }

  public void pause(int position) {
    this.stream.pause(position);
  }

  public void resume(int position) {
    this.stream.resume(position);
  }

  public void stop() {
    this.stream.stop();
  }

  public void seek(int position) throws OperationNotSupportedException {
    this.stream.seek(position);
  }

  public boolean isPaused() {
    return this.stream.isPaused();
  }

  public void receiveVideo(boolean receive) {
    this.stream.receiveVideo(receive);
  }

  public void receiveAudio(boolean receive) {
    this.stream.receiveAudio(receive);
  }

  /**
   * 
   * @return one of INIT, UNINIT, OPEN, CLOSED, STARTED, STOPPED, PUBLISHING,
   *         PLAYING, PAUSED, RESUMED, END, SEEK
   */
  public String getState() {
    return this.stream.getState().toString();
  }

  /**
   * 
   * @param state one of INIT, UNINIT, OPEN, CLOSED, STARTED, STOPPED, PUBLISHING,
   *              PLAYING, PAUSED, RESUMED, END, SEEK
   */
  public void setState(String state) {
    this.stream.setState(StreamState.valueOf(state));
  }

  public void onChange(StreamState state, Object... changed) {
  }

  public String scheduleOnceJob(IScheduledJob job) {
    return null;
  }

  public String scheduleWithFixedDelay(IScheduledJob job, int interval) {
    return null;
  }

  public void cancelJob(String jobName) {

  }

}