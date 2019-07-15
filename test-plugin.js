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
    const log = this.log.bind(this);

    return this.server.addConnectListener(
      conn => log(`Connected: protocol is ${conn.getProtocol()}`),
      conn =>
        log(`Disconnected: number of read messages  ${conn.getReadMessages()}`)
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
