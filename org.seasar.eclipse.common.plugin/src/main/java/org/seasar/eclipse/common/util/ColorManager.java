/*
 * Copyright 2004-2007 the Seasar Foundation and the Others.
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

import org.eclipse.jface.resource.ColorDescriptor;
import org.eclipse.jface.resource.ColorRegistry;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;

/**
 * {@link Color} オブジェクトを管理するためのユーティリティクラスです。<br />
 * 
 * @author y-komori
 */
public class ColorManager {
	private static ColorRegistry registry;

	private ColorManager() {
	}

	/**
	 * {@link ColorManager} を初期化します。<br />
	 * 使用前に一度呼び出してください。<br />
	 * 
	 * @param display
	 *            {@link Display} オブジェクト
	 */
	public void init(final Display display) {
		registry = new ColorRegistry(display);
	}

	/**
	 * カラーを登録します。<br />
	 * 
	 * @param symbolicName
	 *            カラー名称
	 * @param colorData
	 *            カラーデータ
	 * @see ColorRegistry#put(String, RGB)
	 */
	public void putColor(final String symbolicName, final RGB colorData) {
		registry.put(symbolicName, colorData);
	}

	/**
	 * カラーを登録します。<br />
	 * カラー名称は <code>colorData</code> をもとに、<code>#RRGGBB</code> となります。<br />
	 * 
	 * @param colorData
	 *            カラーデータ
	 * @see ColorRegistry#put(String, RGB)
	 */
	public void putColor(final RGB colorData) {
		StringBuffer symbolicName = new StringBuffer(7);
		symbolicName.append("#").append(toFixedHexString(colorData.red))
				.append(toFixedHexString(colorData.green)).append(
						toFixedHexString(colorData.blue));
		putColor(symbolicName.toString(), colorData);
	}

	private String toFixedHexString(final int value) {
		return (value < 0x10) ? "0" + Integer.toHexString(value) : Integer
				.toHexString(value);
	}

	/**
	 * 指定された名称の {@link Color} オブジェクトを返します。<br />
	 * 
	 * @param symbolicName
	 *            カラー名称
	 * @return {@link Color} オブジェクト
	 * @see ColorRegistry#get(String)
	 */
	public Color getColor(final String symbolicName) {
		return registry.get(symbolicName);
	}

	/**
	 * 指定された名称の {@link ColorDescriptor} オブジェクトを返します。<br />
	 * 
	 * @param symbolicName
	 *            カラー名称
	 * @return {@link ColorDescriptor} オブジェクト
	 * @see ColorRegistry#getColorDescriptor(String)
	 */
	public ColorDescriptor getColorDescriptor(final String symbolicName) {
		return registry.getColorDescriptor(symbolicName);
	}
}
