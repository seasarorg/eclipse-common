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

import junit.framework.TestCase;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.seasar.framework.exception.ResourceNotFoundRuntimeException;

/**
 * @author y-komori
 * 
 */
public class ImageManagerTest extends TestCase {
    private Display display;

    protected void setUp() throws Exception {
        display = Display.getCurrent();
        if (display == null) {
            display = new Display();
        }
    }

    protected void tearDown() throws Exception {
        ImageManager.dispose();
        if (display != null) {
            display.dispose();
        }
    }

    public void testPutImage() {
        Image image = ImageManager.putImage("ARG_IMG", "images/arg.gif");
        Image getImage = ImageManager.getImage("ARG_IMG");
        assertNotNull("1", getImage);
        assertEquals("2", image, getImage);

        // 登録した Image を ImageDecriptor として取得
        assertNotNull("3", ImageManager.getImageDescriptor("ARG_IMG"));

        ImageManager.putImage("COMPONENT_IMG", "/images/component.gif");
        assertNotNull("4", ImageManager.getImage("ARG_IMG"));

        try {
            ImageManager.putImage("DUMMY_IMG", "dummy");
            fail("5");
        } catch (ResourceNotFoundRuntimeException ex) {
            assertTrue(true);
        }
    }

    public void testPutImageDescriptor() {
        ImageDescriptor desc = ImageManager.putImageDescriptor("ARG_IMG",
                "images/arg.gif");
        ImageDescriptor getDesc = ImageManager.getImageDescriptor("ARG_IMG");
        assertNotNull("1", desc);
        assertEquals("2", desc, getDesc);

        // 登録した ImageDecriptor を Image として取得
        assertNotNull("3", ImageManager.getImage("ARG_IMG"));

        try {
            ImageManager.putImageDescriptor("DUMMY_IMG", "dummy");
            fail();
        } catch (ResourceNotFoundRuntimeException ex) {
            assertTrue("4", true);
        }
    }

    public void testLoadImage() {
        Image argImg1 = ImageManager.loadImage("images/arg.gif");
        assertNotNull("1", argImg1);
        Image argImg2 = ImageManager.loadImage("images/arg.gif");
        assertNotNull("2", argImg2);
        assertSame("3", argImg1, argImg2);
    }

    public void testLoadImageDescriptor() {
        ImageDescriptor desc1 = ImageManager
                .loadImageDescriptor("images/arg.gif");
        assertNotNull("1", desc1);
        ImageDescriptor desc2 = ImageManager
                .loadImageDescriptor("images/arg.gif");
        assertNotNull("2", desc2);
        assertSame("3", desc1, desc2);
    }

    public void testGetImage() {
        assertNotNull("1", ImageManager.loadImage("images/container.gif"));

        try {
            ImageManager.loadImage("DUMMY_IMG");
            fail("2");
        } catch (ResourceNotFoundRuntimeException ex) {
            assertTrue(true);
        }
    }

    public void testLoadImages() {
        loadImages();

        assertNotNull("1", ImageManager.getImage("ARG_IMG"));
        assertNotNull("2", ImageManager.getImage("COMPONENT_IMG"));
        assertNotNull("3", ImageManager.getImage("CONTAINER_IMG"));
        assertNotNull("4", ImageManager.getImage("INCLUDE_IMG"));
        assertNotNull("5", ImageManager.getImage("PROPERTY_IMG"));
    }

    public void testInjectImages() {
        loadImages();
        ImageManager.injectImages(Images.class);

        assertNotNull("1", Images.ARG_IMG);
        assertEquals("2", ImageManager.getImage("ARG_IMG"), Images.ARG_IMG);

        assertNotNull("3", Images.COMPONENT_IMG);

        assertNotNull("4", Images.CONTAINER_IMG);
        assertEquals("5", ImageManager.getImage("CONTAINER_IMG"),
                Images.CONTAINER_IMG);

        assertNotNull("6", Images.INCLUDE_IMG);
    }

    public void testDispose() {
        loadImages();
        Image argImage = ImageManager.getImage("ARG_IMG");
        Image containerImage = ImageManager.getImage("CONTAINER_IMG");
        assertNotNull("1", argImage);
        assertNotNull("2", containerImage);

        ImageManager.dispose();

        assertNull("3", ImageManager.getImage("ARG_IMG"));
        assertNull("4", ImageManager.getImage("CONTAINER_IMG"));
    }

    public void testNormalizePath() {
        assertEquals(
                "1",
                "org/seasar/eclipse/common/util/ImageManager",
                ImageManager
                        .normalizePath("/org/seasar/eclipse/common/util/ImageManager"));
        assertEquals(
                "2",
                "org/seasar/eclipse/common/util/ImageManager",
                ImageManager
                        .normalizePath("org/seasar/eclipse/common/util/ImageManager"));
        assertEquals("3", "", ImageManager.normalizePath("/"));
        assertNull("4", ImageManager.normalizePath(null));
    }

    protected void loadImages() {
        ImageManager
                .loadImages("org/seasar/eclipse/common/util/ImageManagerTest");
    }

    public static class Images {
        public static Image ARG_IMG;

        public static ImageDescriptor COMPONENT_IMG;

        public static Image CONTAINER_IMG;

        public static ImageDescriptor INCLUDE_IMG;

        public static Image DUMMY_IMAGE;

        public static ImageDescriptor DUMMY_IMAGE_DESC;

        public static final Image NO_TARGET_1 = null;

        private Image NO_TARGET_2;

        private static Image NO_TARGET_3;

        protected Image NO_TARGET_4;

        protected static Image NO_TARGET_5;

        Image NO_TARGET_6;

        static Image NO_TARGET_7;
    }
}
