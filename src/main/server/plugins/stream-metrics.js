class StreamMetrics {
  log(msg) {
    console.log(`JavaScript StreamMetrics plugin: ${msg}`);
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

  onScopeCreated(scope) {
    this.log(`onScopeCreated ${scope.getType()}`);

    const app = scope.getApplication();

    const isPublishAllowed = () => {
      this.log("Publishing is about to start");
      const stream = app.getBroadcastStream(scope.getJavaScope(), "anssi");
      return true;
    };

    scope.getApplication().registerStreamPublishSecurity(isPublishAllowed);
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
    return "StreamMetrics JS Plugin";
  }
}

const red5Plugin = new StreamMetrics();
