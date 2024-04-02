package ch1;

public class BritzSpeaker implements Speaker {
    public BritzSpeaker() {
        System.out.println("BritzSpeaker 생성");
    }

    @Override
    public void volumeUP() {
        System.out.println("BritzSpeaker volume up");
    }

    @Override
    public void volumeDown() {
        System.out.println("BritzSpeaker volume down");
    }

}
