/*
 * Copyright 2004-2006 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.seasar.eclipse.common.util;

import java.net.URL;

import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.browser.IWebBrowser;
import org.eclipse.ui.browser.IWorkbenchBrowserSupport;
import org.seasar.eclipse.common.CommonPlugin;

/**
 * @author taichi
 * 
 */
public class WorkbenchUtil {

    public static void openUrl(String url) {
        try {
            IWorkbenchBrowserSupport support = PlatformUI.getWorkbench()
                    .getBrowserSupport();
            IWebBrowser browser = support.getExternalBrowser();
            browser.openURL(new URL(url));
        } catch (Exception e) {
            CommonPlugin.log(e);
        }
    }

    public static IWorkbenchWindow getWorkbenchWindow() {
        IWorkbench workbench = PlatformUI.getWorkbench();
        IWorkbenchWindow result = workbench.getActiveWorkbenchWindow();
        if (result == null && 0 < workbench.getWorkbenchWindowCount()) {
            IWorkbenchWindow[] ws = workbench.getWorkbenchWindows();
            result = ws[0];
        }
        return result;
    }

}
