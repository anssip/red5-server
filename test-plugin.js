class TestPlugin {
  log(msg) {
    console.log(`JavaScript TestPlugin:: ${msg}`);
  }

  onConnect(conn) {
    this.log(`onConnect ${conn.toString()}`);
    this.connection = conn;
  }

  onDisconnect(conn) {
    this.log("onDisconnect");
    this.connection = null;
    this.server.removeConnectListener(this.connectListenerId);
  }

  onScopeCreated(scope) {
    this.log(`onScopeCreated ${scope.toString()}`);
    this.scope = scope;
  }

  onScopeRemoved(scope) {
    this.log("onScopeRemoved");
    this.scope = null;
  }

  doStart() {
    this.log("doStart");
    const onConnect = this.onConnect.bind(this);
    const onDisconnect = this.onConnect.bind(this);

    this.connectListenerId = this.server.addConnectListener(
      onConnect,
      onDisconnect
    );

    const onScopeCreated = this.onScopeCreated.bind(this);
    const onScopeRemoved = this.onScopeRemoved.bind(this);

    this.scopeListenerId = this.server.addScopeListener(
      onScopeCreated,
      onScopeRemoved
    );
  }

  doStop() {
    this.log("doStop");
    this.server.removeConnectListener(this.connectListenerId);
    this.server.removeScopeListener(this.scopeListenerId);
  }

  getName() {
    this.log("getName");
    return "JS Plugin X";
  }

  setServer(server) {
    this.log("setServer");
    this.server = server;
    // this.log("Global names: " + JSON.stringify(server.getGlobalNames()));s
  }

  setApplicationContext(context) {
    this.log("setApplicationContext");
  }
}

const red5Plugin = new TestPlugin();
