package com.qentelli.automation.utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ApplicationsEndpointObject {
	protected static Logger logger = LogManager.getLogger(ApplicationsEndpointObject.class);

	public static TBBEndpoints tbb = new TBBEndpoints();
	public static COOEndpoints coo = new COOEndpoints();
	public static HCE2EndpointObject hce2e = new HCE2EndpointObject();
	public static SHACEndpoints shac = new SHACEndpoints();
	public static BAMIEndpoints bami = new BAMIEndpoints();
    public static MYXEndpoints myx = new MYXEndpoints();
    public static BBEndpoints bb = new BBEndpoints();
    public static E2EEndpoints e2e = new E2EEndpoints();
	public static BODEndpoints bod = new BODEndpoints();
	public static BYDEndpoints byd = new BYDEndpoints();


	public ApplicationsEndpointObject() {
//		// tbb = new TBBEndpoints();
//
//		logger.info("tbb home " + tbb.home);
//		logger.info("tbb enroll" + tbb.enrollment);
//		logger.info("tbb bypass" + tbb.homeBypass);

	}

}
