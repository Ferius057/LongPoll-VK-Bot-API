
import VK.VK;

public class App {

    public static void main(String args[]){

        int groupID;
        String adminToken;
        String botToken;
        VK vk = new VK(groupID, adminToken, botToken);

        vk.getUpdates(90);

    }
}
