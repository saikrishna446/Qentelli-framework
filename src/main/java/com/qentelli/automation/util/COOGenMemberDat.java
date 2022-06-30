package com.qentelli.automation.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.qentelli.automation.utilities.CommonUtilities;

public class COOGenMemberDat {
	private static final String CUSTOMER = "2356270~FIRST~LAST~(762) 736-1273~~~~16504 Atkinson Ave.~~~~TORRANCE~CA~90504~EMAILHERE@yopmail.com~~~840~12/10/2020~NO~Regular~My Site~16504 Atkinson Ave~~~~TORRANCE~CA~90504~~17666654~0~Y~en_US~Unrestricted~0~";
	private static final String MEMBER = "2453838~2322781~2322781~A~FIRST~LAST~8588639478~~~~3301 Exposition Blvd~~~~Santa Monica~CA~90404~EMAILHERE@yopmail.com~~~840~09/01/2020~1~1~~~1~L~2322781~3301 Exposition Blvd~~~~Santa Monica~CA~90404~~~~Mail US Physical Check~~No~en_US~";
	public static final String EXT = ".dat";

	static Logger logger = LogManager.getLogger(IngestAPI.class);
	public static String TMP = System.getProperty("java.io.tmpdir");

	String timeStamp = null;
	int limit = 1;
	public String name = null;
	public String path = null;
	public String member;
	public String first;
	public String last;
	public String email;
	public String file;

	public COOGenMemberDat(String s) {
		logger.info("starting |"+s+ "| data generation");
		file = genFileName(s);


		first = CommonUtilities.randomString(new Random().nextInt(11) + 4);
		last = CommonUtilities.randomString(new Random().nextInt(11) + 4);
		email = first + "_" + last + timeStamp;// + "@yopmail.com";
		logger.info("#################");

	}


	public String genFileName(String s) {
		// customer_20210302 20 53 00 902

		long val = System.currentTimeMillis();
		Date date = new Date(val);
		SimpleDateFormat df2 = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		timeStamp = df2.format(date);
		System.out.println(timeStamp);
		String name = s + "_" + timeStamp;
		return name;
	}

	public String genBatchNumber() {

		long val = System.currentTimeMillis();
		Date date = new Date(val);

		SimpleDateFormat df2 = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		timeStamp = "162993063022055555" + df2.format(date);
		System.out.println(timeStamp);
		System.out.println("16299306302205555523210109109006767");

		return timeStamp;
	}

	public String genCustomer() {
		String member = CUSTOMER;
		String first = CommonUtilities.randomString(new Random().nextInt(11) + 4);
		String last = CommonUtilities.randomString(new Random().nextInt(11) + 4);
		String email = first + "_" + last + timeStamp;
		member = member.replace("FIRST", first);
		member = member.replace("LAST", last);
		member = member.replace("EMAILHERE", email);

		return member;
	}

	public String genMember() {
		String member = MEMBER;
		String first = CommonUtilities.randomString(new Random().nextInt(11) + 1);
		String last = CommonUtilities.randomString(new Random().nextInt(11) + 1);
		String email = first + "_" + last + timeStamp;
		member = member.replace("FIRST", first);
		member = member.replace("LAST", last);
		member = member.replace("EMAILHERE", email);

		return member;
	}

	public void writeCustomerTmp(String fileName, String content) {
		File f = null;
		try {

			f = new File(TMP, fileName + EXT);
			path = f.getAbsolutePath();
			FileWriter fw = new FileWriter(f.getAbsoluteFile());

			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(content);
			bw.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		f = new File(path);
		System.out.println(path + " exists " + f.exists());

	}

	public String dataFilePath() {
		return path;
	}

}
