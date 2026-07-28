package notification;

import org.example.lab03.domain.Show;
import java.util.List;

public interface ShowNotificationService {
    public void showsUpdated(Show[] shows);
}
