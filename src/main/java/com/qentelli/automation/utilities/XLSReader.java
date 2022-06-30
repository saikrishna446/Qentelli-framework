package com.qentelli.automation.utilities;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class XLSReader {
	public class BeachBar {
		public String sku = null;
		public String boxsku = null;
		public String name = null;
		public String quantity = "1";
		public String bMonthy = null; // false;
		public String type = null;
		public String subtype = null;

	}

	Set<BeachBar> lsProducts = new HashSet<BeachBar>();

	public XLSReader() {
		try {
			ClassLoader classLoader = getClass().getClassLoader();
			readFromExcel(classLoader.getResource("beachbar/BEACHBAR_10.22.19.xlsx").getFile());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void readFromExcel(String file) throws IOException {
		// HSSFWorkbook myExcelBook = new HSSFWorkbook(new FileInputStream(file));
		XSSFWorkbook myExcelBook = new XSSFWorkbook(new FileInputStream(file));

		// myExcelBook.getS
		for (int i = 0; i < myExcelBook.getNumberOfSheets(); i++) {
			XSSFSheet myExcelSheet = myExcelBook.getSheetAt(i);
			String sheetName = myExcelSheet.getSheetName();
			System.out.println(sheetName);
			String boxSku = null;
			String comboType = null;
			String combosubType = null;

			for (int j = 0; j < myExcelSheet.getLastRowNum(); j++) {
				BeachBar bar = new BeachBar();
				XSSFRow myrow = myExcelSheet.getRow(j);

				// System.out.println(myrow.getLastCellNum());
				if (myrow.getLastCellNum() < 10) {
					continue;
				}
				for (int k = 0; k < myrow.getLastCellNum(); k++) {
					XSSFCell c = myrow.getCell(k);
					if (c == null) {
						continue;
					}
					if (c.getCellType() == CellType.STRING.getCode()) {
						String s = c.toString();

						if (s.contains("FLAVOROC")) {
							s = s.substring(s.lastIndexOf(" ") + 1);
							System.out.println(">>>" + s + "<<<");

							combosubType = s;
							k = myrow.getLastCellNum();
							continue;
						}
						if (s.matches("\\w*([A-Z][A-Z][A-Z][A-Z])\\w*")) {
							System.out.println(">>>" + s + "<<<");
							boxSku = s;
							XSSFCell mySku = myrow.getCell(k + 1);
							comboType = mySku.toString();

							k = myrow.getLastCellNum();
							continue;
						}
						if (s.contains("BEACHBAR") && !s.contains("Click folder")) {
							s = s.replaceAll(" \\d.*Packs$", "");

							if (s.startsWith("BEACHBAR") && !s.contains("Combo")) {
								XSSFCell mySku = myrow.getCell(k - 1);
								s = s.replace("BEACHBAR ", "");
								String itmSku = mySku.toString();
								int index = itmSku.lastIndexOf(" ");
								itmSku = itmSku.substring(index + 1);
								System.out.println(itmSku + "|" + s);
								bar.type = sheetName;
								bar.boxsku = boxSku;
								bar.name = s;
								bar.sku = itmSku;
								bar.bMonthy = comboType;
								bar.subtype = combosubType;
								if (!bar.bMonthy.contains("Box Combo C")) {
									lsProducts.add(bar);
								}

								k = myrow.getLastCellNum();
							}
						}

					}
				}
			}
		}

		System.out.println(lsProducts.size());
//		myExcelBook.close();
	}

	public BeachBar[] products() {
		System.out.println("--------------------------------------------------------------------");
		for (BeachBar s : lsProducts) {
			System.out
					.println(s.sku + "|" + s.name + "|" + s.type + "|" + s.boxsku + "|" + s.bMonthy + "|" + s.subtype);
		}
		System.out.println("--------------------------------------------------------------------");

		return lsProducts.toArray(new BeachBar[lsProducts.size()]);
	}

}
