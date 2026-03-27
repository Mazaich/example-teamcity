package plaindoll;

import java.util.Random;

public class Welcomer {
    // Если хочешь больше веселья и информации про ДевОпс - приходи в мои каналы NotOps (telegram, YT, Boosty, Patreon)
    // https://t.me/notopsofficial
    public String sayWelcome() {
        return "Welcome home, good hunter. What is it your desire?";
    }
    public String sayFarewell() {
        return "Farewell, good hunter. May you find your worth in waking world.";
    }
    public String sayNeedGold(){
        return "Not enough gold";
    }
    public String saySome(){
        return "something in the way";
    }

    public String getRandomHunterReply() {
        String[] replies = {
            "The hunter is ready!",
            "I'm a hunter, not a gatherer.",
            "Every hunter needs a prey.",
            "Hunter's instinct never fails."
        };
        Random random = new Random();
        return replies[random.nextInt(replies.length)];
    }
}
