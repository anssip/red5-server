/**
 * An example plugin receiving Application level callbacks. The actual callbacks
 * are received in the Application class, see below.
 */
class AppListener {
  log(msg) {
    console.log(`JavaScript AppListener plugin: ${msg}`);
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
     * Register an Application listener
     */
    scope.getApplication().addListener(new Application());
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
    return "AppListener JS Plugin";
  }
}

class Application {
  log(msg) {
    console.log(`JavaScript Application: ${msg}`);
  }

  appStart(app) {
    this.log("appStart()");
    // returning false would prevent the app from starting
    return true;
  }

  appConnect(conn, params) {
    this.log("appConnect()");
    // returning false would reject the connection
    return true;
  }

  appJoin(client, app) {
    this.log("appJoin())");
    return true;
  }

  appDisconnect(conn) {
    this.log("appDisconnect())");
  }

  appLeave(client, app) {
    this.log("appLeave())");
  }

  appStop(app) {
    this.log("appStop())");
  }

  roomStart(room) {
    this.log("roomStart())");
    return true;
  }

  roomConnect(conn, params) {
    this.log("roomConnect()");
    return true;
  }

  roomJoin(client, room) {
    this.log("roomJoin()");
    return true;
  }

  roomDisconnect(conn) {
    this.log("roomDisconnect())");
  }

  roomLeave(client, room) {
    this.log("roomLeave())");
  }

  roomStop(room) {
    this.log("roomStop())");
  }
}

const red5Plugin = new AppListener();
