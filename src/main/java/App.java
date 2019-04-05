
import VK.VK;

public class App {

    public static void main(String args[]){

        int groupID = 1234;
        String adminToken = "token here";
        String botToken = "token here";
        VK vk = new VK(groupID, adminToken, botToken);

        vk.getUpdates(90);

    }
}