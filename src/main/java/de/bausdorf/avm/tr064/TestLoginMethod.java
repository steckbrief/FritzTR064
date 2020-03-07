package de.bausdorf.avm.tr064;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

public class TestLoginMethod {
    private static final Logger LOG = LoggerFactory.getLogger(TestLoginMethod.class);

    public static final String SERVICE_LAN_CONFIG_SECURITY = "LANConfigSecurity:1";
    public static final String ACTION_GET_ANONYMOUS_LOGIN = "X_AVM-DE_GetAnonymousLogin";
    public static final String PROPERTY_ANONYMOUS_LOGIN_ENABLED = "NewX_AVM-DE_AnonymousLoginEnabled";
    public static final String PROPERTY_BUTTON_LOGIN_ENABLED = "NewX_AVM-DE_ButtonLoginEnabled";
    public static final String ACTION_GET_CURRENT_USER = "X_AVM-DE_GetCurrentUser";
    public static final String PROPERTY_CURRENT_USER_RIGHTS = "NewX_AVM-DE_CurrentUserRights";

    public static void main(String[] args) {
        String address = null;
        String password = null;
        String user = "..";
        if (args.length < 1 || args.length > 3) {
            System.err.println("args: <ip> '<password>' '<user>' (password and user optional)");
            System.err.println("e.g.: fritz.box");
            System.err.println("  or: fritz.box 'myPassword'");
            System.err.println("  or: fritz.box 'myPassword' 'hans'");
            System.err.println("  or: 192.168.178.1");
            System.err.println("  or: 192.168.178.1 'myPassword'");
            System.err.println("  or: 192.168.178.1 'myPassword' 'hans'");
            System.exit(1);
        } else {
            address = args[0];
            if (args.length > 1) {
                password = args[1];
            }
            if (args.length > 2) {
                user = args[2];
            }
        }

        try {
            FritzConnection fc = new FritzConnection(address, user, password);
            LOG.info("Connecting to FritzBox at " + address);
            fc.init(null);
            LOG.info("Detecting login method");
            detectLoginMethod(fc);
        } catch (NoSuchAlgorithmException | KeyStoreException | KeyManagementException | IOException | ParseException | UnauthorizedException e) {
            e.printStackTrace();
        }
    }

    private static void detectLoginMethod(final FritzConnection fc) throws IOException, UnauthorizedException {
        Service service = fc.getService(SERVICE_LAN_CONFIG_SECURITY);
        if (service == null) {
            LOG.warn(String.format("Cannot detect login method. Service '%s' not available. Returning LoginMethod.UNKNOWN", SERVICE_LAN_CONFIG_SECURITY));
            return;
        } else {
            Action action = service.getAction(ACTION_GET_ANONYMOUS_LOGIN);
            if (action == null) {
                LOG.warn(String.format("Cannot detect login method. Action '%s' of service '%s' not available.", ACTION_GET_ANONYMOUS_LOGIN, SERVICE_LAN_CONFIG_SECURITY));
            } else {
                LOG.info("Executing action: " + action.getName());
                Response response1 = action.execute();
                if (response1 != null) {
                    printResponse(response1);

                    try {
                        LOG.info("Anonymous login enabled: " + response1.getValueAsBoolean(PROPERTY_ANONYMOUS_LOGIN_ENABLED));
                    } catch (NoSuchFieldException e) {
                        LOG.warn(String.format("Field '%s' of action '%s' not available.", PROPERTY_ANONYMOUS_LOGIN_ENABLED, ACTION_GET_ANONYMOUS_LOGIN));
                    }
                    try {
                        LOG.info("Button login enabled: " + response1.getValueAsBoolean(PROPERTY_BUTTON_LOGIN_ENABLED));
                    } catch (NoSuchFieldException e) {
                        LOG.warn(String.format("Field '%s' of action '%s' not available.", PROPERTY_BUTTON_LOGIN_ENABLED, ACTION_GET_ANONYMOUS_LOGIN));
                    }
                }
            }

            LOG.info("");
            action = service.getAction(ACTION_GET_CURRENT_USER);
            if (action == null) {
                LOG.warn(String.format("Cannot detect login method. Action '%s' of service '%s' not available.", ACTION_GET_CURRENT_USER, SERVICE_LAN_CONFIG_SECURITY));
            } else {
                try {
                    LOG.info("Executing action: " + action.getName());
                    Response response1 = action.execute();
                    if (response1 != null) {
                        printResponse(response1);
                        try {
                            LOG.info("Current user rights: " + response1.getValueAsString(PROPERTY_CURRENT_USER_RIGHTS));
                        } catch (NoSuchFieldException e) {
                            LOG.warn(String.format("Field '%s' of action '%s' not available.", PROPERTY_CURRENT_USER_RIGHTS, ACTION_GET_CURRENT_USER));
                        }
                    }
                } catch (IOException e) {
                    if (e.getMessage().contains("401")) {
                        LOG.info("Detected HTTP status code 401 (unauthorized). Returning LoginMethod.PASSWORD_ONLY");
                    } else {
                        throw e;
                    }
                }
            }
        }
    }

    private static void printResponse(final Response response) {
        for (String key: response.getData().keySet()) {
            LOG.info(key + ": " + response.getData().get(key));
        }
    }
}
