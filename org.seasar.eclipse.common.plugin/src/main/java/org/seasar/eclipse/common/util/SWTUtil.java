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

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

/**
 * @author y-komori
 * 
 */
public class SWTUtil {
    protected static Map<String, Integer> constants = new HashMap<String, Integer>();

    protected static Map<String, Color> colors = new HashMap<String, Color>();

    static {
        Field[] fields = SWT.class.getFields();
        for (Field field : fields) {
            if (Modifier.isStatic(field.getModifiers())
                    && (field.getType() == Integer.TYPE)) {
                try {
                    String name = field.getName();
                    constants.put(name, field.getInt(null));

                    if (name.startsWith("COLOR_")) {
                        String colorName = name.substring(6);
                        Color color = Display.getCurrent().getSystemColor(
                                getSWTConstant(name));
                        colors.put(colorName, color);
                        // TODO Colorオブジェクトのdispose処理
                    }
                } catch (Exception ignore) {
                }
            }
        }
    }

    /**
     * <code>SWT</code>クラスの持つ定数を返します。<br>
     * 
     * @param name
     *            定数名
     * @return 値。存在しない定数名が指定された場合、<code>SWT.NONE</code>を返します。
     */
    public static int getSWTConstant(String name) {
        int constant = SWT.NONE;
        Integer constantObj = constants.get(name);
        if (constantObj != null) {
            constant = constantObj.intValue();
        }
        return constant;
    }

    /**
     * カンマ区切りの定数からSWTのスタイルを計算します。<br>
     * 例えば以下のような入力に対して、本メソッドは
     * <code>SWT.HORIZONTAL | SWT.SHADOW_IN | SWT.CENTER</code>の計算結果を
     * 戻り値として返します。<br>
     * <code>SWT</code>クラスに定義されていない定数が指定された場合、無視されます。
     * 
     * 入力例:<code>"HORIZONTAL, SHADOW_IN, CENTER"</code><br>
     * 
     * @param styles
     *            カンマ区切りの定数。
     * @param defaultStyle
     *            <code>styles</code> が <code>null</code> だった場合に返すデフォルト値。
     * @return スタイル値。 引数が <code>null</code> の場合は <code>defalutStyle</code>
     *         を返します。
     */
    public static int getStyle(String styles, int defaultStyle) {
        int result = 0;
        if (styles != null) {
            StringTokenizer st = new StringTokenizer(styles, ",");
            while (st.hasMoreTokens()) {
                String style = st.nextToken().trim();
                Integer constant = getSWTConstant(style);
                if (constant != null) {
                    result |= constant.intValue();
                }
            }
        }
        else
        {
            result = defaultStyle;
        }
        return result;
    }

    /**
     * カンマ区切りの定数からSWTのスタイルを計算します。<br>
     * 例えば以下のような入力に対して、本メソッドは
     * <code>SWT.HORIZONTAL | SWT.SHADOW_IN | SWT.CENTER</code>の計算結果を
     * 戻り値として返します。<br>
     * <code>SWT</code>クラスに定義されていない定数が指定された場合、無視されます。
     * 
     * 入力例:<code>"HORIZONTAL, SHADOW_IN, CENTER"</code><br>
     * 
     * @param styles
     *            カンマ区切りの定数。
     * @return スタイル値。 引数が <code>null</code> の場合は <code>SWT.NONE</code>
     *         を返します。
     */
    public static int getStyle(String styles) {
        return getStyle(styles, SWT.NONE);
    }

    /**
     * <code>Color</code>オブジェクトを生成します。<br>
     * <code>colorString</code> で指定された文字列から <code>Color</code> オブジェクトを生成します。<br>
     * <code>colorString</code> は #RGB 形式または <code>red</code>、<code>blue</code>
     * 等 <code>SWT</code>クラスの <code>COLOR_*</code>
     * 定数で用意された文字列が指定できます(いずれも、大文字・小文字どちらも使用可能)。<br>
     * 例: <code>#FF0000</code> を指定した場合、赤を表します。
     * 
     * @param colorString
     *            色を表す文字列。
     * @return <code>Color</code> オブジェクト
     */
    public static Color getColor(String colorString) {
        Color color = null;
        if ((colorString.startsWith("#")) && (colorString.length() == 7)) {
            int red = Integer.parseInt(colorString.substring(1, 3), 16);
            int green = Integer.parseInt(colorString.substring(3, 5), 16);
            int blue = Integer.parseInt(colorString.substring(5, 7), 16);
            color = new Color(Display.getCurrent(), red, green, blue);
        } else {
            color = colors.get(colorString.toUpperCase());
        }
        return color;
    }
}
