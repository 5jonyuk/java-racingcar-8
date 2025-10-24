package racingcar;

public class Car {
    private String name;
    private int position;

    public Car(String name) {
        checkCarNameLength(name);
        this.name = name;
        this.position = 0;
    }
    private static void checkCarNameLength(String name){
        if (name.length() > 5) {
            throw new IllegalArgumentException("자동차 이름은 5자 이하만 가능합니다");
        }
    }
    public void moveForward(){
        this.position++;
    }
    public String getName() {
        return name;
    }
    public int getPosition() {
        return position;
    }
}
