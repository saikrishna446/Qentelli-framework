package com.qentelli.automation.stepdefs.common.application;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import com.qentelli.automation.common.TBBBaseElements;
import com.qentelli.automation.exceptions.base.BaseByCssSelector;
import com.qentelli.automation.exceptions.base.BaseByXpath;
import com.qentelli.automation.utilities.PageObject;

public class HomePageElements extends TBBBaseElements {
    protected Logger logger = LogManager.getLogger(HomePageElements.class);
    public BannerHero bannerHero;
    public ButtonBuyNowPiYoDeluxeKit buttonBuyNowPiYoDeluxeKit;
    public ButtonChocolatePlantBasedVeganBuyNow buttonChocolatePlantBasedVeganBuyNow;
    public ButtonCizeShakeology buttonCizeShakeology;
    public ButtonPiYoShakeology buttonPiYoShakeology;
    public ButtonPreferredCustomerAddToCart btnAddPC;
    public ButtonChinUpBarAddToCart btnChupBarAddToCart;
    public ButtonBeachbarChocolateCherryAlmond btnBeachbarChocolateCherryAlmond;
    public ButtonCafeLateShakeology btnCafeLateShakeology;
    public ButtonCloseCommunityPopup buttonCloseCommunityPopup;
    public ButtonCoachSignup btnCoachSignup;
    public ButtonPreferredCustomerSignUp buttonPreferredCustomerSignUp;
    public ButtonSearch btnSearch;
    public ButtonShopNow buttonShopNow;
    public ButtonSignIn btnSignIn;
    public Button21DayFixExtremeAccessoriesBundle button21DayFixExtremeAccessoriesBundle;
    public ButtonBODMemberShip buttonBODMemberShip;
    public ButtonBODPreLaunchPack buttonBODPreLaunchPack;
    public ButtonBuyOfAnyProductWithPCPriceInCrossSell buttonBuyOfAnyProductWithPCPriceInCrossSell;
    public ButtonBuyOfProductWithBothPCAndPromotionPriceInCrossSell buttonBuyOfProductWithBothPCAndPromotionPriceInCrossSell;
    public ButtonLetsGetUpVIPEarlyAccessPack buttonLetsGetUpVIPEarlyAccessPack;
    public ButtonPCSignUpAndSave buttonPCSignUpAndSave;
    public ButtonQuarterlyBODShakeologyCP buttonQuarterlyBODShakeologyCP;
    public ButtonSeeMoreProductInCrossSell buttonSeeMoreProductInCrossSell;
    public ButtonFirstBuyNow buttonFirstBuyNow;
    public ButtonAcceptAllCookies buttonAcceptAllCookies;
    public ButtonBuyNow buttonBuyNow;
    public ButtonPaginationRightArrow buttonPaginationRightArrow;
    public ButtonProductFirstBuyNow buttonProductFirstBuyNow;
    public ButtonBecomeACoach buttonBecomeACoach;
    public ImageSpinner imgSpinner;
    public ImageViewCart imgViewCart;
    public LabelPCPriceInCrossSell labelPCPriceInCrossSell;
    public LabelPVPOintsInCrossSell labelPVPOintsInCrossSell;
    public LabelPromotionPriceInCrossSell labelPromotionPriceInCrossSell;
    public LabelRetailPriceInCrossSell labelRetailPriceInCrossSell;
    public LabelSearchResults labelSearchResults;
    public Link21DFAccessoryBundleDeluxe link21DFAccessoryBundleDeluxe;
    public LinkOnDemand linkOnDemand;
    public LinkTotalSolutionPacks linkTotalSolutionPacks;
    public LinkChinUpBarBuyNow lnkChinUpBarBuyNow;
    public LinkCoach lnkCoach;
    public LinkFirstViewProductDetails linkFirstViewProductDetails;
    public LinkSelectFirstProductSubType linkSelectFirstProductSubType;
    public LinkFooterShop lnkFooterShop;
    public LinkGearTab lnkGearTab;
    public LinkSignIn lnkSignIn;
    public LinkShakeologyChocolatePack linkShakeologyChocolatePack;
    public SectionCoachInfoHeader sectionCoachInfoHeader;
    public TextBSKTitleInMiniCart textBSKTitleInMiniCart;
    public TextPCPriceForAProductWithPCAndPromotionPriceInCrossSell textPCPriceForAProductWithPCAndPromotionPriceInCrossSell;
    public TextPCPriceForAProductWithPCPriceInCrossSell textPCPriceForAProductWithPCPriceInCrossSell;
    public TextBoxSearch txtSearch;
    public TextEstimatedTotalOrderSummary textEstimatedTotalOrderSummary;
    public TextRetailPriceOrderSummary textRetailPriceOrderSummary;
    public TextPiYoChallengePackYourPriceInCDP textPiYoChallengePackYourPriceInCDP;
    public TextPiYoChallengePackRetailPriceInCDP textPiYoChallengePackRetailPriceInCDP;
    public TextHomePageTitle textHomePageTitle;
    public TextPopupDisclaimerMessage textPopupDisclaimerMessage;
    public TilePiYoShakeologyChallengePackInCDP tilePiYoShakeologyChallengePackInCDP;
    public TileBODCategoryPack tileBodCategoryPack;
    public TileCizeCategoryPack tileCizeCategoryPack;
    public TilePiYoCategoryPack tilePiYoCategoryPack;
    public Tile21DayFixCategoryPack tile21DayFixCategoryPack;
    public Tile2BMindsetCategoryPack tile2BMindsetCategoryPack;
    public TextCoachTitleWithNameInCoachInfoHeader textCoachTitleWithNameInCoachInfoHeader;
    public TextCoachEmailInCoachInfoHeader textCoachEmailInCoachInfoHeader;
    public TextCoachPhoneInCoachInfoHeader textCoachPhoneInCoachInfoHeader;
    public TextCountryLanguageUpdatedDisclaimer textCountryLanguageUpdatedDisclaimer;
    public LogoTeam logoTeam;
    public ButtonYesCountryLanguagePopup buttonYesCountryLanguagePopup;
    public ButtonNoCountryLanguagePopup buttonNoCountryLanguagePopup;
    public TextCountryLanguagePopup textCountryLanguagePopup;
    public TextYourCoachHeader textYourCoachHeader;

    public HomePageElements(WebDriver driver) {
        super(driver);
        // this happens in a base class
        // objects = new PageObject(driver, this.getClass().getSimpleName());

      //  btnSignIn = new ButtonSignIn(objects);

    }

    public class ButtonBuyNowPiYoDeluxeKit extends BaseByXpath {
        ButtonBuyNowPiYoDeluxeKit(PageObject objects) {
            super(objects);
        }
    }

    public class ButtonChocolatePlantBasedVeganBuyNow extends BaseByXpath {
        ButtonChocolatePlantBasedVeganBuyNow(PageObject objects) {
            super(objects);
        }
    }

    public class BannerHero extends BaseByXpath {
        BannerHero(PageObject objects) {
            super(objects);
        }
    }

    public class ButtonCizeShakeology extends BaseByXpath {
        ButtonCizeShakeology(PageObject objects) {
            super(objects);
        }
    }

    public class ButtonPiYoShakeology extends BaseByXpath {
        ButtonPiYoShakeology(PageObject objects) {
            super(objects);
        }
    }

    public class ButtonSignIn extends BaseByXpath {
        ButtonSignIn(PageObject objects) {
            super(objects);
        }
    }

    public class ButtonCloseCommunityPopup extends BaseByXpath {
        ButtonCloseCommunityPopup(PageObject objects) {
            super(objects);
        }
    }

    public class ButtonCoachSignup extends BaseByXpath {
        ButtonCoachSignup(PageObject objects) {super(objects);}
    }

    public class ButtonPreferredCustomerSignUp extends BaseByXpath {
        ButtonPreferredCustomerSignUp(PageObject objects) {super(objects);}
    }

    public class ButtonBeachbarChocolateCherryAlmond extends BaseByCssSelector {
        ButtonBeachbarChocolateCherryAlmond(PageObject objects) {
            super(objects);
        }
    }

    public class ButtonCafeLateShakeology extends BaseByCssSelector {
        ButtonCafeLateShakeology(PageObject objects) {
            super(objects);
        }
    }

    public class ButtonQuarterlyBODShakeologyCP extends BaseByCssSelector {
        ButtonQuarterlyBODShakeologyCP(PageObject objects) {
            super(objects);
        }
    }

    public class ButtonBODPreLaunchPack extends BaseByCssSelector {
        ButtonBODPreLaunchPack(PageObject objects) {
            super(objects);
        }
    }

    public class Button21DayFixExtremeAccessoriesBundle extends BaseByCssSelector {
        Button21DayFixExtremeAccessoriesBundle(PageObject objects) {
            super(objects);
        }
    }

    public class ButtonBODMemberShip extends BaseByXpath {
        ButtonBODMemberShip(PageObject objects) {
            super(objects);
        }
    }

    public class ButtonLetsGetUpVIPEarlyAccessPack extends BaseByCssSelector {
        ButtonLetsGetUpVIPEarlyAccessPack(PageObject objects) {
            super(objects);
        }
    }

    public class ButtonSeeMoreProductInCrossSell extends BaseByCssSelector {
        ButtonSeeMoreProductInCrossSell(PageObject objects) {
            super(objects);
        }
    }

    public class ButtonBuyOfAnyProductWithPCPriceInCrossSell extends BaseByXpath {
        ButtonBuyOfAnyProductWithPCPriceInCrossSell(PageObject objects) {
            super(objects);
        }
    }

    public class ButtonBuyOfProductWithBothPCAndPromotionPriceInCrossSell extends BaseByXpath {
        ButtonBuyOfProductWithBothPCAndPromotionPriceInCrossSell(PageObject objects) {
            super(objects);
        }
    }

    public class ButtonPreferredCustomerAddToCart extends BaseByXpath {
        ButtonPreferredCustomerAddToCart(PageObject objects) {
            super(objects);
        }
    }

    public class ButtonChinUpBarAddToCart extends BaseByXpath {
        ButtonChinUpBarAddToCart(PageObject objects) {
            super(objects);
        }
    }

    public class ButtonPCSignUpAndSave extends BaseByCssSelector {
        ButtonPCSignUpAndSave(PageObject objects) {
            super(objects);
        }
    }

    public class ButtonSearch extends BaseByXpath {
        ButtonSearch(PageObject objects) {
            super(objects);
        }
    }

    public class ButtonShopNow extends BaseByXpath {
        ButtonShopNow(PageObject objects) {
            super(objects);
        }
    }

    public class ButtonFirstBuyNow extends BaseByXpath {
        ButtonFirstBuyNow(PageObject objects) {
            super(objects);
        }
    }

    public class ButtonAcceptAllCookies extends BaseByXpath {
        ButtonAcceptAllCookies(PageObject objects) {
            super(objects);
        }
    }

    public class ButtonBuyNow extends BaseByXpath {
        ButtonBuyNow(PageObject objects) {
            super(objects);
        }
    }

    public class ButtonPaginationRightArrow extends BaseByXpath {
        ButtonPaginationRightArrow(PageObject objects) {
            super(objects);
        }
    }

    public class ButtonProductFirstBuyNow extends BaseByXpath {
        ButtonProductFirstBuyNow(PageObject objects) {
            super(objects);
        }
    }

    public class ButtonBecomeACoach extends BaseByXpath {
        ButtonBecomeACoach(PageObject objects) {
            super(objects);
        }
    }

    public class ImageSpinner extends BaseByXpath {
        ImageSpinner(PageObject objects) {
            super(objects);
        }
    }

    public class ImageViewCart extends BaseByXpath {
        ImageViewCart(PageObject objects) {
            super(objects);
        }
    }

    public class LabelPCPriceInCrossSell extends BaseByXpath {
        LabelPCPriceInCrossSell(PageObject objects) {
            super(objects);
        }
    }

    public class LabelRetailPriceInCrossSell extends BaseByXpath {
        LabelRetailPriceInCrossSell(PageObject objects) {
            super(objects);
        }
    }

    public class LabelPVPOintsInCrossSell extends BaseByXpath {
        LabelPVPOintsInCrossSell(PageObject objects) {
            super(objects);
        }
    }

    public class LabelPromotionPriceInCrossSell extends BaseByXpath {
        LabelPromotionPriceInCrossSell(PageObject objects) {
            super(objects);
        }
    }

    public class LabelSearchResults extends BaseByXpath {
        LabelSearchResults(PageObject objects) {
            super(objects);
        }
    }

    public class LinkSignIn extends BaseByXpath {
        LinkSignIn(PageObject objects) {
            super(objects);
        }
    }

    public class LinkCoach extends BaseByXpath {
        LinkCoach(PageObject objects) {
            super(objects);
        }
    }

    public class LinkFirstViewProductDetails extends BaseByXpath {
        LinkFirstViewProductDetails(PageObject objects) {
            super(objects);
        }
    }

    public class LinkSelectFirstProductSubType extends BaseByXpath {
        LinkSelectFirstProductSubType(PageObject objects) {
            super(objects);
        }
    }

    public class LinkTotalSolutionPacks extends BaseByXpath {
        LinkTotalSolutionPacks(PageObject objects) {
            super(objects);
        }
    }

    public class LinkChinUpBarBuyNow extends BaseByXpath {
        LinkChinUpBarBuyNow(PageObject objects) {
            super(objects);
        }
    }

    public class LinkGearTab extends BaseByXpath {
        LinkGearTab(PageObject objects) {
            super(objects);
        }
    }

    public class LinkFooterShop extends BaseByXpath {
        LinkFooterShop(PageObject objects) {
            super(objects);
        }
    }

    public class SectionCoachInfoHeader extends BaseByCssSelector {
        SectionCoachInfoHeader(PageObject objects) {
            super(objects);
        }
    }

    public class LinkShakeologyChocolatePack extends BaseByXpath {
        LinkShakeologyChocolatePack(PageObject objects) {
            super(objects);
        }
    }

    public class TextBSKTitleInMiniCart extends BaseByXpath {
        TextBSKTitleInMiniCart(PageObject objects) {
            super(objects);
        }
    }

    public class TextYourCoachHeader extends BaseByXpath {
        TextYourCoachHeader(PageObject objects) {
            super(objects);
        }
    }

    public class Link21DFAccessoryBundleDeluxe extends BaseByXpath {
        Link21DFAccessoryBundleDeluxe(PageObject objects) {
            super(objects);
        }
    }

    public class TextCoachTitleWithNameInCoachInfoHeader extends BaseByCssSelector {
        TextCoachTitleWithNameInCoachInfoHeader(PageObject objects) {
            super(objects);
        }
    }

    public class TextCoachEmailInCoachInfoHeader extends BaseByCssSelector {
        TextCoachEmailInCoachInfoHeader(PageObject objects) {
            super(objects);
        }
    }

    public class TextCoachPhoneInCoachInfoHeader extends BaseByCssSelector {
        TextCoachPhoneInCoachInfoHeader(PageObject objects) {
            super(objects);
        }
    }

    public class TextCountryLanguageUpdatedDisclaimer extends BaseByXpath {
        TextCountryLanguageUpdatedDisclaimer(PageObject objects) {
            super(objects);
        }
    }

    public class TextPCPriceForAProductWithPCPriceInCrossSell extends BaseByXpath {
        TextPCPriceForAProductWithPCPriceInCrossSell(PageObject objects) {
            super(objects);
        }
    }

    public class TextPCPriceForAProductWithPCAndPromotionPriceInCrossSell extends BaseByXpath {
        TextPCPriceForAProductWithPCAndPromotionPriceInCrossSell(PageObject objects) {
            super(objects);
        }
    }

    public class TextBoxSearch extends BaseByXpath {
        TextBoxSearch(PageObject objects) {
            super(objects);
        }
    }

    public class LinkOnDemand extends BaseByXpath {
        LinkOnDemand(PageObject objects) {
            super(objects);
        }
    }

    public class TextEstimatedTotalOrderSummary extends BaseByXpath {
        TextEstimatedTotalOrderSummary(PageObject objects) {
            super(objects);
        }
    }

    public class TextRetailPriceOrderSummary extends BaseByXpath {
        TextRetailPriceOrderSummary(PageObject objects) {
            super(objects);
        }
    }

    public class TextPiYoChallengePackYourPriceInCDP extends BaseByXpath {
        TextPiYoChallengePackYourPriceInCDP(PageObject objects) {
            super(objects);
        }
    }

    public class TextPiYoChallengePackRetailPriceInCDP extends BaseByXpath {
        TextPiYoChallengePackRetailPriceInCDP(PageObject objects) {
            super(objects);
        }
    }

    public class TextHomePageTitle extends BaseByXpath {
        TextHomePageTitle(PageObject objects) {
            super(objects);
        }
    }

    public class TextPopupDisclaimerMessage extends BaseByXpath {
        TextPopupDisclaimerMessage(PageObject objects) {
            super(objects);
        }
    }

    public class TilePiYoShakeologyChallengePackInCDP extends BaseByXpath {
        TilePiYoShakeologyChallengePackInCDP(PageObject objects) {
            super(objects);
        }
    }

    public class TileBODCategoryPack extends BaseByCssSelector {
        TileBODCategoryPack(PageObject objects) {
            super(objects);
        }
    }

    public class TileCizeCategoryPack extends BaseByCssSelector {
        TileCizeCategoryPack(PageObject objects) {
            super(objects);
        }
    }

    public class TilePiYoCategoryPack extends BaseByCssSelector {
        TilePiYoCategoryPack(PageObject objects) {
            super(objects);
        }
    }

    public class Tile21DayFixCategoryPack extends BaseByCssSelector {
        Tile21DayFixCategoryPack(PageObject objects) {
            super(objects);
        }
    }

    public class Tile2BMindsetCategoryPack extends BaseByCssSelector {
        Tile2BMindsetCategoryPack(PageObject objects) {
            super(objects);
        }
    }

    public class LogoTeam extends BaseByXpath {
        LogoTeam(PageObject objects) {
            super(objects);
        }
    }

    public class ButtonYesCountryLanguagePopup extends BaseByXpath {
        ButtonYesCountryLanguagePopup(PageObject objects) {
            super(objects);
        }
    }

    public class ButtonNoCountryLanguagePopup extends BaseByXpath {
        ButtonNoCountryLanguagePopup(PageObject objects) {
            super(objects);
        }
    }

    public class TextCountryLanguagePopup extends BaseByXpath {
        TextCountryLanguagePopup(PageObject objects) {
            super(objects);
        }
    }
}
