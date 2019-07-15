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
  }

  doStart() {
    this.log("doStart");
    return this.server.addConnectListener(
      this.onConnect.bind(this),
      this.onDisconnect.bind(this)
    );
  }

  doStop() {
    this.log("doStop");
    // return this.server.removeConnectListener();
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
