package mobile.authentication.validations;

import config.TOMException;
import enums.PlatformType;
import framework.actions.Extract;
import framework.actions.Tap;
import general.screens.LoginScreen;
import interfaces.platform.IPlatformSpecific;
import interfaces.validations.IValidation;
import utils.BaseLogger;

public class IsAuthErrorMessageDisplayed extends BaseLogger implements IValidation<IsAuthErrorMessageDisplayed>, IPlatformSpecific {

    @Override
    public PlatformType getPlatform() {
        return PlatformType.MOBILE;
    }

    @Override
    public boolean validate(Object... args) {
        if (args.length == 0 || args[0] == null) {
            throw new TOMException("Expected error message data, but got none");
        }

        String errorMessage = (String) args[0];
        String platformVariant = args[1] != null
                ? args[1].toString().toLowerCase()
                : "android"; // default fallback

        LoginScreen loginScreen = new LoginScreen();

        try{
            return errorMessage.contains(Extract.text(loginScreen.getErrorLabel()));
        }catch (Exception e){
            return errorMessage.replace(",","").contains(Extract.text(loginScreen.getLockedUserErrorLabel()));
        }finally {
            if(platformVariant.equalsIgnoreCase("ios")){
                Tap.on(loginScreen.getOkButton());
            }
        }


    }
}