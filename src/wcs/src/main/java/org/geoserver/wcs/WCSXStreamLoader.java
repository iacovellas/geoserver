/* Copyright (c) 2012 TOPP - www.openplans.org. All rights reserved.
 * This code is licensed under the GPL 2.0 license, available at the root
 * application directory.
 */
package org.geoserver.wcs;

import java.util.ArrayList;

import org.geoserver.config.GeoServer;
import org.geoserver.config.util.XStreamPersister;
import org.geoserver.config.util.XStreamServiceLoader;
import org.geoserver.platform.GeoServerResourceLoader;
import org.geotools.util.Version;

/**
 * Loads and persist the {@link WCSInfo} object to and from xstream persistence.
 * 
 * @author Justin Deoliveira, The Open Planning Project
 * 
 */
public class WCSXStreamLoader extends XStreamServiceLoader<WCSInfo> {

    public WCSXStreamLoader(GeoServerResourceLoader resourceLoader) {
        super(resourceLoader, "wcs");

    }

    public Class<WCSInfo> getServiceClass() {
        return WCSInfo.class;
    }

    protected WCSInfo createServiceFromScratch(GeoServer gs) {

        WCSInfoImpl wcs = new WCSInfoImpl();
        wcs.setName("WCS");

        return wcs;
    }

    @Override
    protected void initXStreamPersister(XStreamPersister xp, GeoServer gs) {
        super.initXStreamPersister(xp, gs);
        xp.getXStream().alias("wcs", WCSInfo.class, WCSInfoImpl.class);
    }

    @Override
    protected WCSInfo initialize(WCSInfo service) {
		super.initialize(service);
        if (service.getExceptionFormats() == null) {
            ((WCSInfoImpl) service).setExceptionFormats(new ArrayList<String>());
        }   
        if (service.getVersions().isEmpty()) {
            service.getVersions().add(new Version("1.0.0"));
            service.getVersions().add(new Version("1.1.1"));
        } 
        Version v201 = new Version("2.0.1");        
        if(!service.getVersions().contains(v201)) {
            service.getVersions().add(v201);
        }
        return service;
    }

}
