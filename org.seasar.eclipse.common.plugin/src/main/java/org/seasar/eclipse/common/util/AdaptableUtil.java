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

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.ui.texteditor.ITextEditor;

/**
 * @author taichi
 * 
 */
public class AdaptableUtil {

    public static ITextEditor toTextEditor(IAdaptable adaptable) {
        ITextEditor result = null;
        if (adaptable instanceof ITextEditor) {
            result = (ITextEditor) adaptable;
        } else if (adaptable != null) {
            result = (ITextEditor) adaptable.getAdapter(ITextEditor.class);
        }
        return result;
    }

    public static IResource toResource(IAdaptable adaptable) {
        IResource result = null;
        if (adaptable instanceof IResource) {
            result = (IResource) adaptable;
        } else if (adaptable != null) {
            result = (IResource) adaptable.getAdapter(IResource.class);
        }
        return result;
    }

    public static IProject toProject(IAdaptable adaptable) {
        IProject result = null;
        if (adaptable instanceof IProject) {
            result = (IProject) adaptable;
        } else if (adaptable != null) {
            result = (IProject) adaptable.getAdapter(IProject.class);
        }
        return result;
    }
}
