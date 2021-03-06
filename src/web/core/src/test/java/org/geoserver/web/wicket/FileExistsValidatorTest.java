/* Copyright (c) 2012 TOPP - www.openplans.org. All rights reserved.
 * This code is licensed under the GPL 2.0 license, available at the root
 * application directory.
 */
package org.geoserver.web.wicket;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.geoserver.platform.GeoServerResourceLoader;
import org.geoserver.test.GeoServerSystemTestSupport;
import org.geoserver.web.StringValidatable;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.vfny.geoserver.global.GeoserverDataDirectory;

import com.google.common.io.Files;

public class FileExistsValidatorTest {

    private static File root;
    private static FileExistsValidator validator;

    @BeforeClass
    public static void init() throws IOException {
        root = File.createTempFile("file", "tmp", new File("target"));
        root.delete();
        root.mkdirs();

        File wcs = new File(root, "wcs"); 
        wcs.mkdir();

        Files.touch(new File(wcs, "BlueMarble.tiff"));

        GeoserverDataDirectory.setResourceLoader(new GeoServerResourceLoader(root));
        validator = new FileExistsValidator();
    }

    @AfterClass
    public static void destroy() {
        GeoserverDataDirectory.setResourceLoader(null);
    }

    @Test
    public void testAbsoluteRaw() throws Exception {
        File tazbm = new File(root, "wcs/BlueMarble.tiff");
        StringValidatable validatable = new StringValidatable(tazbm.getAbsolutePath());
        
        validator.validate(validatable);
        assertTrue(validatable.isValid());
    }

    @Test
    public void testAbsoluteURI() throws Exception {
        File tazbm = new File(root, "wcs/BlueMarble.tiff");
        StringValidatable validatable = new StringValidatable(tazbm.toURI().toString());
        
        validator.validate(validatable);
        assertTrue(validatable.isValid());
    }

    @Test
    public void testRelative() throws Exception {
        StringValidatable validatable = new StringValidatable("file:wcs/BlueMarble.tiff");
        
        validator.validate(validatable);
        assertTrue(validatable.isValid());
    }
    

}
