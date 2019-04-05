
import VK.VK;

public class App {

    public static void main(String args[]){

        int groupID = 7959134;
        String adminToken = "e8b4a1fbec1a44aa58ca03250b235a04ecfa49859998ff79f3c6a6cadb453d7af81be92f92797a7d9804d";
        String botToken = "4246ab150b02d75359c4c99029e1ba2e427352c1a9a594e2e16c3d440867f5165ab9f45a16c1b895ec094";
        VK vk = new VK(groupID, adminToken, botToken);

        vk.getUpdates(90);

    }
}