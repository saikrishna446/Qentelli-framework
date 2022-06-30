/*
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package com.qentelli.automation.resources;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.qentelli.automation.utilities.AbstractResourceBundle;

/**
 * 
 * @version $Id$
 */

public class DataBaseResourceBundle extends AbstractResourceBundle
{
    protected static Logger logger = LogManager.getLogger(DataBaseResourceBundle.class);

    private static String basename = "databases.";
    private final static String HOST = "HOST";
    private final static String SERVICE = "SERVICE";
    private final static String PORT = "PORT" ;
    private final static String USER = "USER" ;
    private final static String PWD = "PWD" ;
    public String host;
    public String service;
    public String port;
    public String user;
    public String password;


    public DataBaseResourceBundle(String objN)
    {
        super(setName(objN));
        logger.info(">> DB <> " + setName(objN));
        logger.info(getClass().getSimpleName());
        host = bundle.getString(HOST);
        service = bundle.getString(SERVICE);
        port = bundle.getString(PORT);
        user = bundle.getString(USER);
        password = bundle.getString(PWD);
    }

    private static String setName(String objN)
    {
      logger.info("info:\t" + basename + objN + "." + getEnv() + "." + objN);
        return basename + objN + "." + getEnv() + "." + objN;

    }



}
