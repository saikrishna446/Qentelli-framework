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

import java.sql.ResultSet;
import java.sql.Statement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.qentelli.automation.exceptions.base.DBConnection;

/**
 * 
 * @version $Id$
 */
public class ATG
{
  static Logger logger = LogManager.getLogger(ATG.class);
  public DBConnection db;

    public ATG()
    {
      db = new DBConnection(getClass().getSimpleName());
    }

    private String buildQueryOrder() {
      String q =
          "SELECT * FROM BB_CORE.DCSPP_ORDER WHERE ORDER_ID='" + db.props.getOrderNumber() + "'";
      logger.info("Query: " + q);

      return q;

    }


}
