/**
 * An example plugin that determines the connection count of an application.
 */
class ConnectionCount {
  log(msg) {
    console.log(`JavaScript ConnectionCount plugin: ${msg}`);
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

  onScopeCreated(scope) {
    this.log(
      `onScopeCreated name = ${scope.getName()} type = ${scope.getType()} `
    );

    /*
     * Register a connection count listener
     */
    scope.getApplication().addListener(new ConnectionListener(scope));
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
    return "ConnectionCount JS Plugin";
  }
}

class ConnectionListener {
  constructor(scope) {
    this.scope = scope;
  }

  log(msg) {
    console.log(`JavaScript ConnectionListener: ${msg}`);
  }

  appConnect(conn, params) {
    this.log("appConnect()");

    const connections = [...this.scope.getClientConnections(), conn];
    this.log(`num connections == ${connections.length}`);

    this.log(`remote addresses ${conn.getRemoteAddresses()}`);
    // this.log(`protocol ${conn.getProtocol()}`);

    // NOTE: forEach() etc. do not work yet in GraalVM
    // https://github.com/graalvm/graaljs/issues/88

    // new Array(connections).forEach(logIt);

    return true;
  }
}

const red5Plugin = new ConnectionCount();
