package org.red5.server.plugin.javascript;

import org.red5.server.Server;
import org.red5.server.api.plugin.IRed5Plugin;
import org.springframework.context.ApplicationContext;

import org.graalvm.polyglot.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JavaScriptPlugin implements IRed5Plugin {
    protected static Logger log = LoggerFactory.getLogger(JavaScriptPlugin.class);

    private Context ctx;
    private Value plugin;

    public JavaScriptPlugin(String pluginFile) {
        this.load(pluginFile);
    }

    private void load(String pluginFile) {
        this.ctx = Context.newBuilder("js").allowIO(true).allowAllAccess(true).build();
        String jsCode = "load('" + pluginFile + "');";
        // String jsCode = "const dostart = () => { return 'haloo'};";
        Value eval = this.ctx.eval("js", jsCode);
        this.plugin = this.ctx.getBindings("js").getMember("red5Plugin");
    }

    private Value executeJs(String functionName, Object... args) {
        Value member = this.plugin.getMember(functionName);
        if (member == null) {
            log.warn("Function " + functionName + " missing from JavaScript plugin");
            return null;
            // throw new IllegalStateException("Function " + functionName + " missing from
            // JavaScript plugin");
        }
        return this.executeInContext(member, args);
    }

    public Value executeInContext(Value member, Object... args) {
        Value result;
        synchronized (this.ctx) {
            this.ctx.enter();
            result = member.execute(args);
            this.ctx.leave();
        }
        return result;
    }

    private Value executeJs(String functionName) {
        Value result = this.executeJs(functionName, new Object[] { null });
        System.out.println(result.toString());
        return result;
    }

    @Override
    public String getName() {
        Value jsValue = this.executeJs("getName");
        return jsValue.asString();
    }

    @Override
    public void setApplicationContext(ApplicationContext context) {
        this.executeJs("setApplicationContext", context);
    }

    @Override
    public void setServer(Server server) {
        this.executeJs("setServer", new ServerJs(server, this));
    }

    // for testing purposes
    public void setServerWrapper(ServerJs server) {
        this.executeJs("setServer", server);
    }

    @Override
    public void doStart() throws Exception {
        Value resp = this.executeJs("doStart");
        System.out.println(resp.toString());
    }

    @Override
    public void doStop() throws Exception {
        this.executeJs("doStop");
        ctx.close();
    }
}