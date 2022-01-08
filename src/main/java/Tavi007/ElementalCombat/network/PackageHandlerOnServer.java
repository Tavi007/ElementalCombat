package Tavi007.ElementalCombat.network;

import Tavi007.ElementalCombat.init.StartupCommon;

public class PackageHandlerOnServer {

    public static boolean isThisProtocolAcceptedByServer(String protocolVersion) {
        return StartupCommon.MESSAGE_PROTOCOL_VERSION.equals(protocolVersion);
    }
}
