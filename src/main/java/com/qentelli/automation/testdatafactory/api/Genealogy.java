package com.qentelli.automation.testdatafactory.api;

import com.qentelli.automation.common.World;
import com.qentelli.automation.testdatafactory.config.FactoryConfig;
import com.qentelli.automation.testdatafactory.data.User;
import com.qentelli.automation.testdatafactory.dataUtils.DataCreation;
import com.qentelli.automation.testdatafactory.exceptions.GNCIDNotFoundException;
import com.qentelli.automation.testdatafactory.exceptions.UserListEmptyException;
import io.cucumber.datatable.DataTable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.invoke.MethodHandles;
import java.util.*;

import static java.lang.Thread.sleep;

public class Genealogy {

    private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    private OIMService oimUser;

    private TBBOrder tbbOrder;

    BYDPlacement bydPlacement;

    private FactoryConfig factoryConfig;
    private World world;
    public static String coachEmail;

    public Genealogy(World world) {
        this.world = world;
        factoryConfig = world.factoryConfig;
        bydPlacement = new BYDPlacement(world);
        oimUser = new OIMService(world);
        tbbOrder = new TBBOrder(world);
    }

    public void createGenealogyUserWithPurchase(String userType, String email, String genealogy, String product, String direction) {
        String coachGenealogy = genealogy.substring(0, genealogy.length() - 1);
        String sponsorID = null;
        int count = 300;
        if (genealogy.equals("A")) {
            sponsorID = factoryConfig.getDefaultSponsor();
        } else {
            try {
                sponsorID = world.factoryData.getUserFromList(coachGenealogy).getGncID();
            } catch (UserListEmptyException e) {
                e.printStackTrace();
            }
            while (sponsorID.equals(null)) {
                try {
                    sponsorID = world.factoryData.getUserFromList(coachGenealogy).getGncID();
                } catch (UserListEmptyException e) {
                    e.printStackTrace();
                }
                try {
                    sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    if (count == 0) {
                        throw new GNCIDNotFoundException("Coach ID for " + email + " was not returned in time");
                    } else {
                        count--;
                        int tempCount = 300 - count;
                        LOGGER.debug("Failed to find coach " + tempCount + " times.");
                    }
                } catch (GNCIDNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }

        bydPlacement.setSponsorDirection(sponsorID, direction);
        world.factoryData.setGeneology(genealogy);
        switch (userType.toLowerCase()) {
            case "freeuser":
                oimUser.createFreeOIMUser(email, sponsorID);
                break;
            case "coach":
                if (genealogy.equals("A")) {
                    oimUser.createCoachOIMUser(email, sponsorID);
                    coachEmail = email;
                } else {
                    oimUser.createFreeOIMUser("freeUser-"+email, sponsorID);
                    oimUser.createPCOIMUser("pcUser-"+email, sponsorID);
                    oimUser.createCoachOIMUser(email, sponsorID);
                }
                break;
            case "club":
                oimUser.createClubOIMUser(email, sponsorID);
                break;
            case "clubcoach":
                oimUser.createClubCoachOIMUser(email, sponsorID);
                break;
            case "pc":
                oimUser.createPCOIMUser("pcUser-"+email, sponsorID);
                oimUser.createFreeOIMUser("freeUser-"+email, sponsorID);
                break;
        }

//        String orderNumber = tbbOrder.purchaseGivenProductWithCurrentUser(product);
//        world.factoryData.getUser().setOrderNumber(orderNumber);

        world.factoryData.addToUserList(genealogy, world.factoryData.getUser());
    }

    public void createGenealogyUserWithPurchase(DataTable dataTable) {
        List<Map<String, String>> list = dataTable.asMaps(String.class, String.class);
        for (int i = 0; i < list.size(); i++) {
            createGenealogyUserWithPurchase(list.get(i).get("userType"), list.get(i).get("email"), list.get(i).get("genealogy"), list.get(i).get("product"), list.get(i).get("direction"));
        }
    }

    public void createGenealogyUserWithPurchaseCreateEmailFromOwnerString(DataTable dataTable) {
        List<Map<String, String>> list = dataTable.asMaps(String.class, String.class);
        for (int i = 0; i < list.size(); i++) {
            String userName = DataCreation.createUserName(list.get(i).get("ownerString"));
            createGenealogyUserWithPurchase(list.get(i).get("userType"), userName + list.get(i).get("genealogy") + "@testtest.com", list.get(i).get("genealogy"), list.get(i).get("product"), list.get(i).get("direction"));
        }
    }

    public void createGenealogyUserWithPurchaseCreateEmailFromOwnerProperty(DataTable dataTable) {
        String owner = Optional.ofNullable(System.getProperty("owner.string")).orElse("").toLowerCase();

        List<Map<String, String>> list = dataTable.asMaps(String.class, String.class);

        for (int i = 0; i < list.size(); i++) {
            String userName = DataCreation.createUserName(owner);
            createGenealogyUserWithPurchase(list.get(i).get("userType"), userName + list.get(i).get("genealogy") + "@yopmail.com", list.get(i).get("genealogy"), list.get(i).get("product"), list.get(i).get("direction"));
        }
    }

    public void logFullGenealogyList() {
        HashMap<String, User> map = world.factoryData.getUserMap();
        TreeMap<String, User> tree = new TreeMap<>(map);
        String logString = "";
        for (Map.Entry<String, User> entry :
                tree.entrySet()) {
            logString = logString + "** " + String.format("%7s", entry.getKey()) + "\t| " + entry.getValue().toString() + "\n";
        }
        LOGGER.info("\n" + logString);
    }
}
