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

import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.Enumeration;
import java.util.ResourceBundle;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.framework.log.Logger;
import org.seasar.framework.util.FieldUtil;
import org.seasar.framework.util.ResourceUtil;

/**
 * <code>Image</code> オブジェクトを管理するためのユーティリティクラスです。
 * <p>
 * 本クラスは、クラスパス上のリソースとして存在するイメージファイルを読み込み、管理する機能を提供します。</br>
 * また、本クラスのメソッドに対してリソースのパスを指定する場合、先頭にスラッシュ(/)がついていてもいなくても同じパスとして扱います。</br>
 * たとえば、<code>&quot;org/seasar/jface/images/xxxImage.png&quot;</code> と
 * <code>&quot;/org/seasar/jface/images/xxxImage.png&quot;</code>
 * は同じものとして扱います。
 * </p>
 * 
 * @author y-komori
 * 
 */
public class ImageManager {
    protected static final ImageRegistry imageRegistry = new ImageRegistry();

    protected static final Logger logger = Logger.getLogger(ImageManager.class);

    private ImageManager() {
    }

    /**
     * 指定されたキーで登録された画像の <code>Image</code> オブジェクトを返します。<br>
     * 
     * @param key
     *            キー
     * @return 見つかった <code>Image</code> オブジェクト。見つからない場合は<code>null</code>。
     */
    public static Image getImage(final String key) {
        return imageRegistry.get(key);
    }

    /**
     * 指定されたキーで登録された画像の <code>ImageDescriptor</code> オブジェクトを返します。<br>
     * 
     * @param key
     *            キー
     * @return 見つかった <code>ImageDescriptor</code> オブジェクト。見つからない場合は
     *         <code>null</code>。
     */
    public static ImageDescriptor getImageDescriptor(final String key) {
        return imageRegistry.getDescriptor(key);
    }

    /**
     * <code>path</code> で指定された <code>Image</code>
     * オブジェクトを検索し、存在しなければクラスパスからロードします。<br>
     * <p>
     * 本メソッドでは、まず <code>path</code> をキーと見なしてレジストリから <code>Image</code>
     * オブジェクトを検索します。 <code>Image</code> オブジェクトが見つからない場合、<code>path</code>
     * で示されるリソースをクラスパスからロードして <code>path</code> をキーとしてレジストリに登録します。</br> この際、<code>path</code>
     * は <code>/</code>(スラッシュ)で始まっていてもいなくても構いません。</br>
     * </p>
     * <dl>
     * <dt>【例】
     * <dd> loadImage("icons/app.png");
     * </dl>
     * <ul>
     * <li>まず、レジストリに対して <code>icons/app.png</code> というキーで <code>Image</code>
     * オブジェクトを検索します。
     * <li>見つからない場合、クラスパスから <code>icons/app.png</code> というリソースをロードします。
     * <li>ロードに成功すれば、<code>icons/app.png</code> というキーでレジストリに登録します。
     * <li>ここで見つからない場合は、{@link org.seasar.framework.exception.ResourceNotFoundRuntimeException}
     * をスローします。
     * </ul>
     * 
     * @param path
     *            イメージのパス/キー
     * @return 見つかった <code>Image</code> オブジェクト
     * @throws org.seasar.framework.exception.ResourceNotFoundRuntimeException
     *             指定されたリソースが見つからなかった場合
     */
    public static Image loadImage(final String path) {
        Image image = getImage(path);
        if (image == null) {
            image = putImage(path, path);
        }
        return image;
    }

    /**
     * <code>path</code> で指定された <code>ImageDescriptor</code>
     * オブジェクトを検索し、存在しなければクラスパスからロードします。<br>
     * <p>
     * <code>Image</code> オブジェクトではなく <code>ImageDescriptor</code>
     * オブジェクトを返すという点を除き、本メソッドは loadImage() メソッドと同じです。<br />
     * 詳細は loadImage() メソッドの説明をご覧ください。
     * </p>
     * 
     * @param path
     *            イメージのパス/キー
     * @return 見つかった <code>ImageDescriptor</code> オブジェクト
     * @throws org.seasar.framework.exception.ResourceNotFoundRuntimeException
     *             指定されたリソースが見つからなかった場合
     */
    public static ImageDescriptor loadImageDescriptor(final String path) {
        ImageDescriptor descriptor = getImageDescriptor(path);
        if (descriptor == null) {
            descriptor = putImageDescriptor(path, path);
        }
        return descriptor;
    }

    /**
     * <code>Image</code> オブジェクトを登録します。<br>
     * <p>
     * <code>path</code> で示されるリソースをクラスパス上から読み込み、<code>key</code>
     * で示されるキーでレジストリに登録します。</br> 既に同じキーで <code>Image</code>
     * オブジェクトが登録されている場合、上書きします。</br>
     * </p>
     * 
     * @param key
     *            キー
     * @param path
     *            イメージのパス
     * @return 登録した <code>Image</code> オブジェクト
     * @throws org.seasar.framework.exception.ResourceNotFoundRuntimeException
     *             指定されたリソースが見つからなかった場合
     */
    public static Image putImage(final String key, final String path) {
        checkKey(key);

        InputStream is = ResourceUtil.getResourceAsStream(normalizePath(path));
        Image image = new Image(Display.getCurrent(), is);
        imageRegistry.put(key, image);
        return image;
    }

    /**
     * <code>ImageDescriptor</code> オブジェクトを登録します。<br>
     * <p>
     * <code>path</code> で示されるリソースをクラスパス上から読み込み、<code>ImageDescriptor</code>
     * オブジェクトとして <code>key</code> で示されるキーでレジストリに登録します。</br> 既に同じキーで
     * <code>ImageDescriptor</code> オブジェクトが登録されている場合、上書きします。</br>
     * </p>
     * 
     * @param key
     *            キー
     * @param path
     *            リソースのパス
     * @return 登録した <code>ImageDescriptor</code> オブジェクト
     * @throws org.seasar.framework.exception.ResourceNotFoundRuntimeException
     *             指定されたリソースが見つからなかった場合
     */
    public static ImageDescriptor putImageDescriptor(final String key,
            final String path) {
        checkKey(key);

        URL url = ResourceUtil.getResource(normalizePath(path));
        ImageDescriptor descriptor = ImageDescriptor.createFromURL(url);
        imageRegistry.put(key, descriptor);
        return descriptor;
    }

    /**
     * <code>ResourceBundle</code> からイメージを読み込み、一括登録します。</br>
     * <p>
     * 「key=path」の形式で記述されたプロパティファイルを元にした <code>ResourceBundle</code> から
     * <code>Image</code> オブジェクトを一括して読み込みます。
     * </p>
     * <p>
     * 本メソッドではイメージを <code>ImageDescriptor</code> として登録します。
     * </p>
     * 
     * <p>
     * コーディング例
     * </p>
     * 
     * <pre>
     * ResourceBundle imageResources = ResourceBundle.getBundle(&quot;s2JFaceImages&quot;);
     * ImageManager.loadImages(imageResources);
     * </pre>
     * 
     * @param bundle
     *            リソースバンドルの参照
     */
    public static void loadImages(final ResourceBundle bundle) {
        Enumeration keys = bundle.getKeys();
        while (keys.hasMoreElements()) {
            String key = (String) keys.nextElement();
            String path = bundle.getString(key);
            putImageDescriptor(key, path);
        }
    }

    /**
     * <code>ResourceBundle</code> からイメージを読み込み、一括登録します。</br>
     * <p>
     * 「key=path」の形式で記述されたプロパティファイルを元にした <code>ResourceBundle</code> から
     * <code>Image</code> オブジェクトを一括して読み込みます。
     * </p>
     * <p>
     * 本メソッドではイメージを <code>ImageDescriptor</code> として登録します。
     * </p>
     * 
     * @param baseName
     *            リソースバンドルの基底名
     */
    public static void loadImages(final String baseName) {
        ResourceBundle imageResources = ResourceBundle.getBundle(baseName);
        loadImages(imageResources);
    }

    /**
     * 指定されたクラスの定数フィールドに対して、<code>ImageManager</code>が
     * 管理するオブジェクトをインジェクションします。</br> インジェクション対象となるのは、以下の条件を満たすフィールドです。</br>
     * <p>
     * <ol>
     * <li><code>public static</code> な定数フィールドであること
     * <li><code>Image</code>または <code>ImageDescriptor</code> 型のフィールドであること
     * </ol>
     * </p>
     * 以上の条件を満たすフィールドに対して、フィールド名をキーとして <code>ImageManager</code> が登録する
     * <code>Image</code> または <code>ImageDescriptor</code>
     * を検索し、見つかればインジェクションを行います。</br> 見つからなかった場合は、Warning ログを出力します。
     * <p>
     * <b>【例】</b> </br> 以下の例では、<code>ImageHolder</code> クラスの フィールド、<code>IMAGE_A</code>
     * と <code>IMAGE_B</code> に対して、<code>ImageManager</code>
     * が管理するオブジェクトの中から、<code>IMAGE_A</code>、<code>IMAGE_B</code>
     * という名前のキーで登録されたオブジェクトをインジェクションします。
     * 
     * <pre>
     *                           public class ImageHolder() {
     *                               public static Image IMAGE_A;
     *                               public static ImageDescriptor IMAGE_B;
     *                           }
     * </pre>
     * <pre>
     * ImageManager.injectImages(ImageHolder.class);
     * </pre>
     * 
     * </p>
     * 
     * @param clazz
     *            対象クラス
     */
    public static void injectImages(final Class clazz) {
        BeanDesc beanDesc = BeanDescFactory.getBeanDesc(clazz);
        for (int i = 0; i < beanDesc.getFieldSize(); i++) {
            Field field = beanDesc.getField(i);
            String key = field.getName();
            if (!validateMask(field)) {
                continue;
            }

            if (isAssignableFrom(Image.class, field)) {
                injectField(clazz, field, imageRegistry.get(key));
            } else if (isAssignableFrom(ImageDescriptor.class, field)) {
                injectField(clazz, field, imageRegistry.getDescriptor(key));
            }
        }
    }

    /**
     * <code>ImageManager</code> が管理する <code>ImageRegistry</code> を破棄します。
     * 
     */
    public static void dispose() {
        imageRegistry.dispose();
    }

    protected static void injectField(final Class clazz, final Field field,
            final Object o) {
        if (o != null) {
            FieldUtil.set(field, null, o);
        }
    }

    protected static boolean validateMask(Field field) {
        final int MOD_EXPECTED = Modifier.PUBLIC | Modifier.STATIC;
        final int MOD_MASK = MOD_EXPECTED | Modifier.FINAL;
        return (field.getModifiers() & MOD_MASK) == MOD_EXPECTED;
    }

    protected static boolean isAssignableFrom(final Class clazz,
            final Field target) {
        return clazz.isAssignableFrom(target.getType());
    }

    protected static void checkKey(String key) {
        if (imageRegistry.get(key) != null) {
            imageRegistry.remove(key);
        }
    }

    protected static String normalizePath(final String path) {
        if ((path != null) && (path.startsWith("/"))) {
            if (path.length() > 1) {
                return path.substring(1);
            } else {
                return "";
            }
        } else {
            return path;
        }
    }
}
