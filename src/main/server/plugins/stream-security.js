class StreamSecurity {
  log(msg) {
    console.log(`JavaScript StreamSecurity plugin: ${msg}`);
  }

  doStart() {
    this.log("doStart");

    /*
     * Register a listener for scope creation and removal.
     * We can tap into the Applications' event handlers through the scope that we receive in the listener.
     */
    const onScopeCreated = this.onScopeCreated.bind(this);
    const onScopeRemoved = this.onScopeRemoved.bind(this);

    this.scopeListenerId = this.server.addScopeListener(
      onScopeCreated,
      onScopeRemoved
    );
  }

  isPublishAllowed() {
    this.log("isPublishAllowed");
    // change following to return false if you want to prevent publishing
    return true;
  }

  isPlaybackAllowed() {
    // randomly return true or false
    const result = new Date().getTime() % 2 === 0;
    this.log("isPlaybackAllowed? " + result);
    return result;
  }

  onScopeCreated(scope) {
    this.log(`onScopeCreated ${scope.getType()}`);

    /*
     * Register a publish permission checker to all scopes.
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
    // store the server object as we need it in the other methods.
    this.server = server;
  }

  /**
   * Gets the name of this plugin. This function needs to present.
   */
  getName() {
    this.log("getName");
    return "StreamSecurity JS Plugin";
  }
}

const red5Plugin = new StreamSecurity();
