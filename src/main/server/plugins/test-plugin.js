class TestPlugin {
  constructor() {
    this.scopes = [];
  }

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
    this.log(`onScopeCreated ${scope.getType()}`);
    this.scopes.push(scope);

    if (scope.getName() === "oflaDemo") {
      this.scopes.forEach(scope => {
        this.log(
          `Under ${scope.getName()}: basic scope names: ${scope.getBasicScopeNames(
            "APPLICATION"
          )}`
        );
        this.log(
          `${scope.getName()} handler application ${scope.getApplication()}`
        );
      });
    }
  }

  onScopeRemoved(scope) {
    this.log("onScopeRemoved");
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

    // this.log("Basic scope names: " + this.scope.getBasicScopeNames());
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
    this.log("Global names: " + JSON.stringify(server.getGlobalNames()));
    this.log("Mapping table: " + server.getMappingTable());
    this.log("Default scope: " + server.getGlobalScope("default"));
    this.log("Global scopes: " + server.getGlobalScopes());
  }

  setApplicationContext(context) {
    this.log("setApplicationContext");
  }
}

const red5Plugin = new TestPlugin();
