package racingcar;

import camp.nextstep.edu.missionutils.Console;
import camp.nextstep.edu.missionutils.Randoms;

import java.util.ArrayList;
import java.util.List;

public class Application {
    public static void main(String[] args) {
        // TODO: 프로그램 구현
        System.out.println("경주할 자동차 이름을 입력하세요.(이름은 쉼표(,) 기준으로 구분)\n");
        String input = Console.readLine();
        validateCarNameInputRegex(input);

        System.out.println("시도할 횟수는 몇 회인가요?\n");
        int tryNum = Integer.parseInt(Console.readLine());

        List<String> names = splitCarName(input);
        List<Car> cars = createCars(names);

        for (int i = 0; i < tryNum; i++) {
            int randNum = Randoms.pickNumberInRange(0, 9);
            if(canMove(randNum)){
                // 게임실행 로직 실행
            }
        }

    }
    private static List<String> splitCarName(String input){
        List<String> names = new ArrayList<>();
        for (String name : input.split(",")){
            names.add(name.trim());
        }
        return names;
    }
    private static List<Car> createCars(List<String> names) {
        List<Car> cars = new ArrayList<>();
        for (String name : names) {
            validateCarNamesNotBlank(name);
            cars.add(new Car(name));
        }
        return cars;
    }
    private static boolean canMove(int randNum) {
        return randNum >= 4;
    }

    private static void validateCarNameInputRegex(String input){
        if(!input.contains(",")){
            throw new IllegalArgumentException("자동차 이름은 쉼표(,)로 구분되어야 합니다.");
        }
    }
    private static void validateCarNamesNotBlank(String name){
        if(name.trim().isBlank()){
            throw new IllegalArgumentException("구분자 사이에 자동차 이름이 없습니다.");
        }
    }

}
