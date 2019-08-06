
import VK.VK;

public class App {

    public static void main(String args[]){

        int groupID = 7959134;
        String adminToken = "Эти поля нужно скрывать, иначе взломают тебя и твоего бота, страничку вк и собаку";
        String botToken = "это также подлежит скрытию";
        VK vk = new VK(groupID, adminToken, botToken);

        vk.getUpdates(90);

    }
}
