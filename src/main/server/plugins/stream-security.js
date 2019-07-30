class StreamSecurity {
  log(msg) {
    console.log(`JavaScript StreamSecurity plugin: ${msg}`);
  }

  isPublishAllowed() {
    this.log("isPublishAllowed");
    // change following to return false if you want to prevent publishing
    return true;
  }

  isPlaybackAllowed() {
    this.log("isPlaybackAllowed");
    return false;
  }

  onScopeCreated(scope) {
    this.log(`onScopeCreated ${scope.getType()}`);

    /*
     * Register the publish permission checker to all scopes.
     */
    const checkIsPublishAllowed = this.isPublishAllowed.bind(this);
    scope.getApplication().registerStreamPublishSecurity(checkIsPublishAllowed);

    /*
     * Register a playback permission checker to all scopes.
     */
    const checkIsPlaybackAllowed = this.isPlaybackAllowed.bind(this);
    scope
      .getApplication()
      .registerStreamPlaybackSecurity(checkIsPlaybackAllowed);
  }

  onScopeRemoved(scope) {
    this.log("onScopeRemoved");
  }

  setServer(server) {
    this.log("setServer");
    this.server = server;
  }

  doStart() {
    this.log("doStart");

    const onScopeCreated = this.onScopeCreated.bind(this);
    const onScopeRemoved = this.onScopeRemoved.bind(this);

    this.scopeListenerId = this.server.addScopeListener(
      onScopeCreated,
      onScopeRemoved
    );

    // this.log("Basic scope names: " + this.scope.getBasicScopeNames());
  }

  getName() {
    this.log("getName");
    return "StreamSecurity JS Plugin";
  }

  setApplicationContext(context) {
    this.log("setApplicationContext");
  }
}

const red5Plugin = new StreamSecurity();
