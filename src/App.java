
import VK.VK;

public class App {

    public static void main(String args[]){

        int groupID = 7959134;
        String token = "code here user scopes: groups,offline";

        VK vk = new VK(groupID, token);

        System.out.println(vk.getUpdates(10));

    }
}
