package org.arzzcorp.barisystem.services;

import java.util.Timer;
import java.util.TimerTask;

public class SessionManager {
    private static Timer logoutTimer;

    public static void startSessionTimeout(Runnable onTimeout, long delayMillis) {
        cancelSessionTimeout(); // cancelar anterior si existe

        logoutTimer = new Timer();
        logoutTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                onTimeout.run();
            }
        }, delayMillis);
    }

    public static void cancelSessionTimeout() {
        if (logoutTimer != null) {
            logoutTimer.cancel();
            logoutTimer = null;
        }
    }
}

