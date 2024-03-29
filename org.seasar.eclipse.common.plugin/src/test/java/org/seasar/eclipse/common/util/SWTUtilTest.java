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

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;

/**
 * {@link SWTUtil} のためのテストクラスです。<br />
 * 
 * @author y-komori
 */
public class SWTUtilTest extends AbstractShellTest {

    public void testConvertConstantName() {
        assertEquals("Aaa", SWTUtil.convertConstantName("AAA"));
        assertEquals("AaaBbb", SWTUtil.convertConstantName("AAA_BBB"));
        assertEquals("AaaBbbCcc", SWTUtil.convertConstantName("AAA_BBB_CCC"));

    }

    public void testGetSWTConstant() {
        assertEquals(SWT.ABORT, SWTUtil.getSWTConstant("ABORT"));
        assertEquals(SWT.BOLD, SWTUtil.getSWTConstant("BOLD"));
        assertEquals(SWT.CLOSE, SWTUtil.getSWTConstant("CLOSE"));
        assertEquals(SWT.NONE, SWTUtil.getSWTConstant("NONE"));
        assertEquals(SWT.NONE, SWTUtil.getSWTConstant("dummy"));
    }

    public void testGetStyle() {
        assertEquals(SWT.RIGHT, SWTUtil.getStyle("RIGHT"));
        assertEquals(SWT.HORIZONTAL | SWT.SHADOW_IN | SWT.CENTER, SWTUtil
                .getStyle("HORIZONTAL,SHADOW_IN,CENTER"));
        assertEquals(SWT.HORIZONTAL | SWT.SHADOW_IN | SWT.CENTER, SWTUtil
                .getStyle("SHADOW_IN,CENTER,HORIZONTAL"));
        assertEquals(SWT.VERTICAL | SWT.SHADOW_OUT | SWT.LEFT, SWTUtil
                .getStyle(" VERTICAL  , SHADOW_OUT ,  LEFT  "));
        assertEquals(SWT.VERTICAL | SWT.LEFT, SWTUtil
                .getStyle(" VERTICAL  , dummy ,  LEFT  "));
        assertEquals(SWT.NONE, SWTUtil.getStyle(""));
        assertEquals(SWT.NONE, SWTUtil.getStyle(", dummy , , ,"));
        assertEquals(SWT.NONE, SWTUtil.getStyle(null));
    }

    public void testGetColor() {
        assertEquals((new Color(display, 255, 0, 0)).toString(), SWTUtil
                .getColor("#FF0000").toString());
        assertEquals((new Color(display, 0, 255, 0)).toString(), SWTUtil
                .getColor("#00FF00").toString());
        assertEquals((new Color(display, 0, 0, 255)).toString(), SWTUtil
                .getColor("#0000FF").toString());
    }
}
