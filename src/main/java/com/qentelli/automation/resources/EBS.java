package com.qentelli.automation.resources;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.qentelli.automation.exceptions.base.DBConnection;
import com.qentelli.automation.pages.BasePage;
import com.qentelli.automation.utilities.EBSProps;

public class EBS {
	static Logger logger = LogManager.getLogger(EBS.class);

	public DBConnection db;
	public EBSProps props;

	public EBS() {
		db = new DBConnection(getClass().getSimpleName());
		props = new EBSProps();
	}

	String buildQueryOrder() {
		return "select PROCESS_FLAG,ERROR_MESSAGE,SOURCE_SYSTEM from xxdaz_order_import_headers where orig_sys_document_ref='"
				+ db.props.getOrderNumber() + "' ";
	}

	}


