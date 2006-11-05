package org.seasar.eclipse.common;

import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;

/**
 * The main plugin class to be used in the desktop.
 */
public class CommonPlugin extends Plugin {

    // The shared instance.
    private static CommonPlugin plugin;

    /**
     * The constructor.
     */
    public CommonPlugin() {
        plugin = this;
    }

    /**
     * This method is called upon plug-in activation
     */
    public void start(BundleContext context) throws Exception {
        super.start(context);
    }

    /**
     * This method is called when the plug-in is stopped
     */
    public void stop(BundleContext context) throws Exception {
        super.stop(context);
        plugin = null;
    }

    /**
     * Returns the shared instance.
     */
    public static CommonPlugin getDefault() {
        return plugin;
    }

}
