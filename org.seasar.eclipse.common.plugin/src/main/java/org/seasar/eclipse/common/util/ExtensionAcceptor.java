/*
 * Copyright 2004-2008 the Seasar Foundation and the Others.
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

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;

/**
 * 拡張ポイント読み出しアクセプタ。
 * @author taichi
 */
public class ExtensionAcceptor {

	/**
	 * 拡張ポイントに対して設定された拡張に対するビジターアクセプタ。
	 * @param namespace 名前空間＝プラグインID
	 * @param extensionPointName 拡張ポイント名
	 * @param visitor 拡張ポイントビジター
	 */
    public static void accept(String namespace, String extensionPointName,
            ExtensionVisitor visitor) {
        IExtensionRegistry registry = Platform.getExtensionRegistry();
        IExtensionPoint point = registry.getExtensionPoint(namespace,
                extensionPointName);
        
        for (IExtension extension : point.getExtensions()) {
            for (IConfigurationElement element : extension.getConfigurationElements()) {
                visitor.visit(element);
            }
        }

    }

	/**
	 * 拡張ポイントビジターインターフェイス。
	 * @author taichi
	 */
    public interface ExtensionVisitor {

    	/**
		 * 処理内容を記述するメソッド。
		 * @param e 拡張定義
		 */
        void visit(IConfigurationElement e);

    }

}
