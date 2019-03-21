package mediathek.tool.notification;

import javafx.application.Platform;
import mSearch.tool.ApplicationConfiguration;
import mediathek.tool.notification.thrift.NotificationMessage;
import org.controlsfx.control.Notifications;

/**
 * Implements notification based on controlsfx JavaFX implemention.
 */
public class GenericNotificationCenter implements INotificationCenter {
    @Override
    public void displayNotification(NotificationMessage notificationMessage) {
        if (!ApplicationConfiguration.getConfiguration().getBoolean(ApplicationConfiguration.APPLICATION_SHOW_NOTIFICATIONS,true))
            return;

        Platform.runLater(() -> {
            try {
                final Notifications msg = Notifications.create();
                msg.text(notificationMessage.getMessage());
                msg.title(notificationMessage.getTitle());

                switch (notificationMessage.getType()) {
                    case INFO:
                        msg.showInformation();
                        break;

                    case ERROR:
                        msg.showError();
                        break;
                }
            }
            catch (NullPointerException ignored) {
                //the generic version might fail to display a notification with NPE
            }
        });
    }
}
