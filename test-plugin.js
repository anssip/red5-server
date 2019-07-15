class TestPlugin {
  log(msg) {
    console.log(`JavaScript TestPlugin:: ${msg}`);
  }

  onConnect(conn) {
    this.connection = conn;
    this.log("onConnect");
  }

  onDisconnect(conn) {
    this.connection = null;
    this.log("onDisconnect");
    this.server.removeConnectListener(this.connectListenerId);
  }

  doStart() {
    this.log("doStart");
    const log = this.log.bind(this);
    const onConnect = this.onConnect.bind(this);
    const onDisconnect = this.onConnect.bind(this);

    this.connectListenerId = this.server.addConnectListener(
      onConnect,
      onDisconnect
    );
  }

  doStop() {
    this.log("doStop");
    this.server.removeConnectListener(this.connectListenerId);
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
