package com.qentelli.automation.pages.common;

import com.qentelli.automation.common.BaseElements;
import com.qentelli.automation.exceptions.base.BaseByCssSelector;
import com.qentelli.automation.exceptions.base.BaseByXpath;
import com.qentelli.automation.utilities.PageObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

public class GlobalNavigationPageElements extends BaseElements {
    protected Logger logger = LogManager.getLogger(GlobalNavigationPageElements.class);
    public ButtonSearch buttonSearch;
    public ButtonSearchBoxReset buttonSearchBoxReset;
    public CartBadge cartBadge;
    public CartContainer cartContainer;
    public ContainerFooter containerFooter;
    public DropdownFlagMenuInGlobalNav dropdownFlagMenuInGlobalNav;
    public DropdownMenusInGlobalNav dropdownMenusInGlobalNav;
    public FieldSearchBox fieldSearchBox;
    public IconCart iconCart;
    public IconFlag iconFlag;
    public IconHelp iconHelp;
    public IconUser iconUser;
    public ImageProfile imageProfile;
    public LinktestOnDemandGlobal linktestOnDemandGlobal;
    public LinkBecomePreferredCustomer linkBecomePreferredCustomer;
    public LinkCartItemCount linkCartItemCount;
    public LinkCartValue linkCartValue;
    public LinkCoachToolsGlobal linkCoachToolsGlobal;
    public LinkCoachingGlobal linkCoachingGlobal;
    public LinkGlobalBecomeACoach  linkGlobalBecomeACoach;
    public LinkGlobalCoachOffice  linkGlobalCoachOffice;
    public LinkFavorites linkFavorites;
    public LinkFooterAppStore linkFooterAppStore;
    public LinkFooterBOD linkFooterBOD;
    public LinkFooterBODLifeBlog linkFooterBODLifeBlog;
    public LinkFootertestChallenge linkFootertestChallenge;
    public LinkFootertestHome linkFootertestHome;
    public LinkFooterCoaching linkFooterCoaching;
    public LinkFooterContactUsFAQ linkFooterContactUsFAQ;
    public LinkFooterCorporate linkFooterCorporate;
    public LinkFooterFacebook linkFooterFacebook;
    public LinkFooterGroups linkFooterGroups;
    public LinkFooterInstagram linkFooterInstagram;
    public LinkFooterNutrition linkFooterNutrition;
    public LinkFooterPlayStore linkFooterPlayStore;
    public LinkFooterPreferredCustomer linkFooterPreferredCustomer;
    public LinkFooterShop linkFooterShop;
    public LinkFooterTwitter linkFooterTwitter;
    public LinkFooterYoutube linkFooterYoutube;
    public LinkGroupsGlobal linkGroupsGlobal;
    public LinkJoinUsGlobal linkJoinUsGlobal;
    public LinkLogIn linkLogIn;
    public LinkLogInGlobal linkLogInGlobal;
    public LinkLogOut linkLogOut;
    public LinkMoreGlobal linkMoreGlobal;
    public LinkMyAccount linkMyAccount;
    public LinkMyOrders linkMyOrders;
    public LinkMyProgress linkMyProgress;
    public LinkNutritionGlobal linkNutritionGlobal;
    public LinkNutritionPrograms linkNutritionPrograms;
    public LinkNutritionSupplements linkNutritionSupplements;
    public LinkShopGlobal linkShopGlobal;
    public LinkSignUp linkSignUp;
    public LinkTotalSolutionPacks linkTotalSolutionPacks;
    public LinkTracking linkTracking;
    public LinkUSAEnglishFlag linkUSAEnglishFlag;
    public LinkUKEnglishFlag linkUKEnglishFlag;
    public LinkUSASpanishFlag linkUSASpanishFlag;
    public LinkCanadaEnglishFlag linkCanadaEnglishFlag;
    public LinkCanadaFrenchFlag linkCanadaFrenchFlag;
    public LinkFranceFlag linkFranceFlag;
    public Logotest logotest;
    public PlaceholderLocaleFlag placeholderLocaleFlag;
    public PromoBanner promoBanner;
    public ShakeologyMenuItem shakeologyMenuItem;
    public SubNavComponent subNavComponent;
    public SubNavMenuBar subNavMenuBar;
    public SubNavMenuItem subNavMenuItem;
    public TextFooter textFooter;
    public TextHomePageTitle textHomePageTitle;
    public TextUserName textUserName;
    public TopNavHeader topNavHeader;
    public TopNavMenuBar topNavMenuBar;
    public TopNavMenuItem topNavMenuItem;
    public linktestPerformance linktestPerformance;
    public LinkCountryLangPreferenceOption linkCountryLangPreferenceOption;
    public LinkUpdatedLocale linkUpdatedLocale;
    public PlaceholderTopNavMenuItemHref placeholderTopNavMenuItemHref;
    public ImageSelectedFlag imageSelectedFlag;
    public TextUserID textUserID;

    public GlobalNavigationPageElements(WebDriver driver) {
        super(driver);
        logger.info(this.getClass().getSimpleName());
        objects = new PageObject(driver, this.getClass().getSimpleName());
        buttonSearch = new ButtonSearch(objects);
        buttonSearchBoxReset = new ButtonSearchBoxReset(objects);
        cartBadge = new CartBadge(objects);
        cartContainer = new CartContainer(objects);
        containerFooter = new ContainerFooter(objects);
        dropdownFlagMenuInGlobalNav = new DropdownFlagMenuInGlobalNav(objects);
        dropdownMenusInGlobalNav = new DropdownMenusInGlobalNav(objects);
        fieldSearchBox = new FieldSearchBox(objects);
        iconCart = new IconCart(objects);
        iconFlag = new IconFlag(objects);
        iconHelp = new IconHelp(objects);
        iconUser = new IconUser(objects);
        imageProfile = new ImageProfile(objects);
        linktestOnDemandGlobal = new LinktestOnDemandGlobal(objects);
        linkBecomePreferredCustomer = new LinkBecomePreferredCustomer(objects);
        linktestPerformance = new linktestPerformance(objects);
        linkCountryLangPreferenceOption = new LinkCountryLangPreferenceOption(objects);
        linkUpdatedLocale = new LinkUpdatedLocale(objects);
        linkCanadaEnglishFlag = new LinkCanadaEnglishFlag(objects);
        linkCanadaFrenchFlag = new LinkCanadaFrenchFlag(objects);
        linkCartItemCount = new LinkCartItemCount(objects);
        linkCoachToolsGlobal = new LinkCoachToolsGlobal(objects);
        linkCoachingGlobal = new LinkCoachingGlobal(objects);
        linkFavorites = new LinkFavorites(objects);
        linkFooterAppStore = new LinkFooterAppStore(objects);
        linkFooterBOD = new LinkFooterBOD(objects);
        linkFooterBODLifeBlog = new LinkFooterBODLifeBlog(objects);
        linkFootertestChallenge = new LinkFootertestChallenge(objects);
        linkFootertestHome = new LinkFootertestHome(objects);
        linkFooterCoaching = new LinkFooterCoaching(objects);
        linkFooterContactUsFAQ = new LinkFooterContactUsFAQ(objects);
        linkFooterCorporate = new LinkFooterCorporate(objects);
        linkFooterFacebook = new LinkFooterFacebook(objects);
        linkFooterGroups = new LinkFooterGroups(objects);
        linkFooterInstagram = new LinkFooterInstagram(objects);
        linkFooterNutrition = new LinkFooterNutrition(objects);
        linkFooterPlayStore = new LinkFooterPlayStore(objects);
        linkFooterPreferredCustomer = new LinkFooterPreferredCustomer(objects);
        linkFooterShop = new LinkFooterShop(objects);
        linkFooterTwitter = new LinkFooterTwitter(objects);
        linkFooterYoutube = new LinkFooterYoutube(objects);
        linkGlobalBecomeACoach = new LinkGlobalBecomeACoach(objects);
        linkGlobalCoachOffice = new LinkGlobalCoachOffice(objects);
        linkGroupsGlobal = new LinkGroupsGlobal(objects);
        linkJoinUsGlobal = new LinkJoinUsGlobal(objects);
        linkLogIn = new LinkLogIn(objects);
        linkLogInGlobal = new LinkLogInGlobal(objects);
        linkLogOut = new LinkLogOut(objects);
        linkMoreGlobal = new LinkMoreGlobal(objects);
        linkMyAccount = new LinkMyAccount(objects);
        linkMyOrders = new LinkMyOrders(objects);
        linkMyProgress = new LinkMyProgress(objects);
        linkNutritionGlobal = new LinkNutritionGlobal(objects);
        linkNutritionPrograms = new LinkNutritionPrograms(objects);
        linkNutritionSupplements = new LinkNutritionSupplements(objects);
        linkShopGlobal = new LinkShopGlobal(objects);
        linkSignUp = new LinkSignUp(objects);
        linkTotalSolutionPacks = new LinkTotalSolutionPacks(objects);
        linkTracking = new LinkTracking(objects);
        linkUSAEnglishFlag = new LinkUSAEnglishFlag(objects);
        linkUKEnglishFlag = new LinkUKEnglishFlag(objects);
        linkUSASpanishFlag = new LinkUSASpanishFlag(objects);
        linkFranceFlag = new LinkFranceFlag(objects);
        logotest = new Logotest(objects);
        placeholderLocaleFlag = new PlaceholderLocaleFlag(objects);
        placeholderTopNavMenuItemHref = new PlaceholderTopNavMenuItemHref(objects);
        promoBanner = new PromoBanner(objects);
        shakeologyMenuItem = new ShakeologyMenuItem(objects);
        subNavComponent = new SubNavComponent(objects);
        subNavMenuBar = new SubNavMenuBar(objects);
        subNavMenuItem = new SubNavMenuItem(objects);
        textFooter = new TextFooter(objects);
        textHomePageTitle = new TextHomePageTitle(objects);
        textUserName = new TextUserName(objects);
        topNavHeader = new TopNavHeader(objects);
        topNavMenuBar = new TopNavMenuBar(objects);
        topNavMenuItem = new TopNavMenuItem(objects);
        imageSelectedFlag = new ImageSelectedFlag(objects);
        textUserID = new TextUserID(objects);
    }


    public class ButtonSearch extends BaseByXpath {
        ButtonSearch(PageObject objects) {
            super(objects);
        }
    }

    public class ButtonSearchBoxReset extends BaseByXpath {
        ButtonSearchBoxReset(PageObject objects) {
            super(objects);
        }
    }

    public class CartContainer extends BaseByXpath {
        CartContainer(PageObject objects) {
            super(objects);
        }
    }

    public class CartBadge extends BaseByXpath {
        CartBadge(PageObject objects) {
            super(objects);
        }
    }

    public class DropdownMenusInGlobalNav extends BaseByXpath {
        DropdownMenusInGlobalNav(PageObject objects) {
            super(objects);
        }
    }

    public class DropdownFlagMenuInGlobalNav extends BaseByXpath {
        DropdownFlagMenuInGlobalNav(PageObject objects) {
            super(objects);
        }
    }

    public class FieldSearchBox extends BaseByXpath {
        FieldSearchBox(PageObject objects) {
            super(objects);
        }
    }

    public class IconFlag extends BaseByCssSelector {
        IconFlag(PageObject objects) {
            super(objects);
        }
    }

    public class IconHelp extends BaseByXpath {
        IconHelp(PageObject objects) {
            super(objects);
        }
    }

    public class IconCart extends BaseByXpath {
        IconCart(PageObject objects) {
            super(objects);
        }
    }

    public class IconUser extends BaseByXpath {
        IconUser(PageObject objects) {
            super(objects);
        }
    }

    public class ImageProfile extends BaseByXpath {
        ImageProfile(PageObject objects) {
            super(objects);
        }
    }

    public class LinkMyAccount extends BaseByXpath {
        LinkMyAccount(PageObject objects) {
            super(objects);
        }
    }

    public class LinkMyOrders extends BaseByXpath {
        LinkMyOrders(PageObject objects) {
            super(objects);
        }
    }

    public class LinkLogOut extends BaseByXpath {
        LinkLogOut(PageObject objects) {
            super(objects);
        }
    }

    public class LinkTotalSolutionPacks extends BaseByXpath {
        LinkTotalSolutionPacks(PageObject objects) {
            super(objects);
        }
    }

    public class LinkTracking extends BaseByXpath {
        LinkTracking(PageObject objects) {
            super(objects);
        }
    }

    public class LinkFavorites extends BaseByXpath {
        LinkFavorites(PageObject objects) {
            super(objects);
        }
    }

    public class LinkMyProgress extends BaseByXpath {
        LinkMyProgress(PageObject objects) {
            super(objects);
        }
    }

    public class LinkLogInGlobal extends BaseByCssSelector {
        LinkLogInGlobal(PageObject objects) {
            super(objects);
        }
    }

    public class LinkLogIn extends BaseByCssSelector {
        LinkLogIn(PageObject objects) {
            super(objects);
        }
    }

    public class LinkSignUp extends BaseByXpath {
        LinkSignUp(PageObject objects) {
            super(objects);
        }
    }

    public class LinkShopGlobal extends BaseByXpath {
        LinkShopGlobal(PageObject objects) {
            super(objects);
        }
    }

    public class LinktestOnDemandGlobal extends BaseByXpath {
        LinktestOnDemandGlobal(PageObject objects) {
            super(objects);
        }
    }

    public class LinkBecomePreferredCustomer extends BaseByXpath {
        LinkBecomePreferredCustomer(PageObject objects) {
            super(objects);
        }
    }

    public class LinkNutritionGlobal extends BaseByXpath {
        LinkNutritionGlobal(PageObject objects) {
            super(objects);
        }
    }

    public class LinkGroupsGlobal extends BaseByXpath {
        LinkGroupsGlobal(PageObject objects) {
            super(objects);
        }
    }

    public class LinkCoachingGlobal extends BaseByXpath {
        LinkCoachingGlobal(PageObject objects) {
            super(objects);
        }
    }

    public class LinkCoachToolsGlobal extends BaseByXpath {
        LinkCoachToolsGlobal(PageObject objects) {
            super(objects);
        }
    }

    public class LinkJoinUsGlobal extends BaseByXpath {
        LinkJoinUsGlobal(PageObject objects) {
            super(objects);
        }
    }

    public class LinkMoreGlobal extends BaseByXpath {
        LinkMoreGlobal(PageObject objects) {
            super(objects);
        }
    }

    public class LinkCartValue extends BaseByXpath {
        LinkCartValue(PageObject objects) {
            super(objects);
        }
    }

    public class LinkCartItemCount extends BaseByXpath {
        LinkCartItemCount(PageObject objects) {
            super(objects);
        }
    }

    public class LinkUSAEnglishFlag extends BaseByXpath {
        LinkUSAEnglishFlag(PageObject objects) {
            super(objects);
        }
    }

    public class LinkCanadaEnglishFlag extends BaseByXpath {
        LinkCanadaEnglishFlag(PageObject objects) {
            super(objects);
        }
    }
    public class LinkCanadaFrenchFlag extends BaseByXpath {
        LinkCanadaFrenchFlag(PageObject objects) {
            super(objects);
        }
    }

    public class LinkUKEnglishFlag extends BaseByXpath {
        LinkUKEnglishFlag(PageObject objects) {
            super(objects);
        }
    }

    public class LinkUSASpanishFlag extends BaseByXpath {
        LinkUSASpanishFlag(PageObject objects) {
            super(objects);
        }
    }

    public class LinkFranceFlag extends BaseByXpath {
        LinkFranceFlag(PageObject objects) {
            super(objects);
        }
    }

    public class Logotest extends BaseByXpath {
        Logotest(PageObject objects) {
            super(objects);
        }
    }

    public class PlaceholderLocaleFlag extends BaseByXpath {
        PlaceholderLocaleFlag(PageObject objects) {
            super(objects);
        }
    }

    public class PlaceholderTopNavMenuItemHref extends BaseByXpath {
        PlaceholderTopNavMenuItemHref(PageObject objects) {
            super(objects);
        }
    }

    public class PromoBanner extends BaseByXpath {
        PromoBanner(PageObject objects) {
            super(objects);
        }
    }

    public class SubNavComponent extends BaseByXpath {

      SubNavComponent(PageObject objects) {
            super(objects);
        }
    }

    public class SubNavMenuBar extends BaseByXpath {
        SubNavMenuBar(PageObject objects) {
            super(objects);
        }
    }

    public class SubNavMenuItem extends BaseByXpath {
        SubNavMenuItem(PageObject objects) {
            super(objects);
        }
    }

    public class ShakeologyMenuItem extends BaseByXpath {
        ShakeologyMenuItem(PageObject objects) {
            super(objects);
        }
    }

    public class TextHomePageTitle extends BaseByXpath {
        TextHomePageTitle(PageObject objects) {
            super(objects);
        }
    }

    public class TextUserName extends BaseByXpath {
        TextUserName(PageObject objects) {
            super(objects);
        }
    }

    public class TopNavHeader extends BaseByXpath {
        TopNavHeader(PageObject objects) {
            super(objects);
        }
    }

    public class TopNavMenuBar extends BaseByXpath {
        TopNavMenuBar(PageObject objects) {
            super(objects);
        }
    }

    public class TopNavMenuItem extends BaseByXpath {
        TopNavMenuItem(PageObject objects) {
            super(objects);
        }
    }

    public class LinkFootertestHome extends BaseByXpath {
        LinkFootertestHome(PageObject objects) {
            super(objects);
        }
    }

    public class LinkFooterShop extends BaseByXpath {
        LinkFooterShop(PageObject objects) {
            super(objects);
        }
    }

    public class LinkFooterBOD extends BaseByXpath {
        LinkFooterBOD(PageObject objects) {
            super(objects);
        }
    }

    public class LinkFooterNutrition extends BaseByXpath {
        LinkFooterNutrition(PageObject objects) {
            super(objects);
        }
    }

    public class LinkFooterGroups extends BaseByXpath {
        LinkFooterGroups(PageObject objects) {
            super(objects);
        }
    }

    public class LinkFooterCoaching extends BaseByXpath {
        LinkFooterCoaching(PageObject objects) {
            super(objects);
        }
    }

    public class LinkFooterBODLifeBlog extends BaseByXpath {
        LinkFooterBODLifeBlog(PageObject objects) {
            super(objects);
        }
    }

    public class LinkFootertestChallenge extends BaseByXpath {
        LinkFootertestChallenge(PageObject objects) {
            super(objects);
        }
    }

    public class LinkFooterContactUsFAQ extends BaseByXpath {
        LinkFooterContactUsFAQ(PageObject objects) {
            super(objects);
        }
    }

    public class LinkFooterCorporate extends BaseByXpath {
        LinkFooterCorporate(PageObject objects) {
            super(objects);
        }
    }

    public class LinkFooterFacebook extends BaseByXpath {
        LinkFooterFacebook(PageObject objects) {
            super(objects);
        }
    }

    public class LinkFooterInstagram extends BaseByXpath {
        LinkFooterInstagram(PageObject objects) {
            super(objects);
        }
    }

    public class LinkFooterTwitter extends BaseByXpath {
        LinkFooterTwitter(PageObject objects) {
            super(objects);
        }
    }

    public class LinkFooterYoutube extends BaseByXpath {
        LinkFooterYoutube(PageObject objects) {
            super(objects);
        }
    }

    public class LinkFooterAppStore extends BaseByXpath {
        LinkFooterAppStore(PageObject objects) {
            super(objects);
        }
    }

    public class LinkFooterPlayStore extends BaseByXpath {
        LinkFooterPlayStore(PageObject objects) {
            super(objects);
        }
    }

    public class LinkFooterPreferredCustomer extends BaseByXpath {
        LinkFooterPreferredCustomer(PageObject objects) {
            super(objects);
        }
    }

    public class TextFooter extends BaseByXpath {
        TextFooter(PageObject objects) {
            super(objects);
        }
    }

    public class ContainerFooter extends BaseByXpath {
        ContainerFooter(PageObject objects) {
            super(objects);
        }
    }

    public class LinkNutritionPrograms extends BaseByXpath {
        LinkNutritionPrograms(PageObject objects) {
            super(objects);
        }
    }

    public class LinkNutritionSupplements extends BaseByXpath {
        LinkNutritionSupplements(PageObject objects) {
            super(objects);
        }
    }

    public class LinkCountryLangPreferenceOption extends BaseByXpath {
        LinkCountryLangPreferenceOption(PageObject objects) {
            super(objects);
        }
    }

    public class LinkUpdatedLocale extends BaseByXpath {
        LinkUpdatedLocale(PageObject objects) {
            super(objects);
        }
    }

    public class linktestPerformance extends BaseByXpath {
        linktestPerformance(PageObject objects) {
            super(objects);
        }
    }
    public class LinkGlobalBecomeACoach extends BaseByXpath {
        LinkGlobalBecomeACoach(PageObject objects) {
            super(objects);
        }
    }

    public class LinkGlobalCoachOffice extends BaseByXpath {
        LinkGlobalCoachOffice(PageObject objects) {
            super(objects);
        }
    }

    public class ImageSelectedFlag extends BaseByXpath {
        ImageSelectedFlag(PageObject objects) {
            super(objects);
        }
    }

    public class TextUserID extends BaseByXpath {
        TextUserID(PageObject objects) {
            super(objects);
        }
    }
}
