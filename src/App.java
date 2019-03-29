
import VK.VK;

public class App {

    public static void main(String args[]){

        int groupID = 7959134;

        VK vk = new VK(groupID);
        System.out.println(vk.getUpdates(10));

    }
}
