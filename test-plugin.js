class TestPlugin {
  log(msg) {
    console.log(`JavaScript TestPlugin:: ${msg}`);
  }

  doStart() {
    this.log("doStart");
  }

  doStop() {
    this.log("doStop");
  }

  getName() {
    this.log("getName");
    return this.server ? this.server.toString() : "JS Plugin X";
  }

  setServer(server) {
    this.log("setServer");
    this.server = server;
    this.log("Global names: " + JSON.stringify(server.getGlobalScopeNames()));
  }

  setApplicationContext(context) {
    this.log("setApplicationContext");
  }
}

const red5Plugin = new TestPlugin();
