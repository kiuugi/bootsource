package ch1;

public class TvMain {
    public static void main(String[] args) {
        // tv 객체 생성
        // LgTv lgTv = new LgTv();
        // SamsungTv SamsungTv = new SamsungTv();
        // 부모를 왼쪽에 자식을 왼쪽에
        // 범위의 차이가 있다 / 다형성
        // 한 타입의 참조 변수를 통해 여러 타입의 객체를 참조할 수 있도록 하는 것이다. 즉, 상위 클래스 타입의 참조 변수를 통해서 하위 클래스의
        // 객체를 참조할 수 있도록 허용하여 상위 클래스가 동일한 메시지로 하위 클래스들이 서로 다른 동작을 할 수 있도록 한다.

        // 상속(ex/impl) => 부모보다 자식의 범위가 크다.

        LgTv tv = new LgTv(); // => 자식객체로 생성 자식 클래스와 부모클래스 모두 접근가능
        // Tv tv = new LgTv(); // => 부모객체의 Tv 범위만 접근가능 1) 오버라이딩 시 자식 실행
        // Tv tv = new LgTv(new BritzSpeaker());
        tv.setSpeaker(new BritzSpeaker()); // setter 메소드가 있는 클래스는 LgTv라서 Tv tv = new LgTv();를 이용해서 생성하면 setter에 접근 불가능함

        tv.powerOn();
        // NullPointerException
        tv.volumeUP();
        tv.volumeDown();
        tv.powerOff();

    }
}
