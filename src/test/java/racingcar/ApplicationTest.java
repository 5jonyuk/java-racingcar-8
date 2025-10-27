package racingcar;

import camp.nextstep.edu.missionutils.test.NsTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static camp.nextstep.edu.missionutils.test.Assertions.assertRandomNumberInRangeTest;
import static camp.nextstep.edu.missionutils.test.Assertions.assertSimpleTest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static racingcar.Application.splitCarName;

class ApplicationTest extends NsTest {
    private static final int MOVING_FORWARD = 4;
    private static final int STOP = 3;

    @Test
    void 기능_테스트() {
        assertRandomNumberInRangeTest(
                () -> {
                    run("pobi,woni", "1");
                    assertThat(output()).contains("pobi : -", "woni : ", "최종 우승자 : pobi");
                },
                MOVING_FORWARD, STOP
        );
    }

    @Test
    void 예외_테스트() {
        assertSimpleTest(() ->
                assertThatThrownBy(() -> runException("pobi,javaji", "1"))
                        .isInstanceOf(IllegalArgumentException.class)
        );
    }

    @Override
    public void runMain() {
        Application.main(new String[]{});
    }

    @Test
    @DisplayName("입력받은 문자열을 쉼표로 구분하여 공백을 제거한 이름 리스트로 변환한다.")
    void splitCarName_shouldReturnTrimmedNames() {
        // given
        String input = "pobi, jun , honux";

        // when
        List<String> result = splitCarName(input);

        // then
        assertThat(result).containsExactly("pobi", "jun", "honux");
    }

    @Test
    @DisplayName("자동차 이름 리스트로 자동차 객체를 생성한다.")
    void createCars_shouldCreateCarObjects() {
        // given
        List<String> names = List.of("pobi", "jun", "honux");

        // when
        List<Car> cars = Application.createCars(names);

        // then
        assertThat(cars).hasSize(3);
        assertThat(cars.get(0).getName()).isEqualTo("pobi");
    }

    @Test
    @DisplayName("랜덤값이 4 이상이면 자동차가 전진한다")
    void moveCarIfPossible_shouldMove_whenRandNumIs4OrGreater() {
        // given
        Car car = new Car("pobi");
        int randNum = 4; // 직접 4를 줌

        // when
        if (Application.canMove(randNum)) {
            car.moveForward();
        }

        // then
        assertThat(car.getPosition()).isEqualTo(1);
    }

    @Test
    @DisplayName("랜덤값이 3 이하이면 자동차가 전진하지 않는다")
    void moveCarIfPossible_shouldNotMove_whenRandNumIs3OrLess() {
        // given
        Car car = new Car("pobi");
        int randNum = 3;

        // when
        if (Application.canMove(randNum)) {
            car.moveForward();
        }

        // then
        assertThat(car.getPosition()).isEqualTo(0);
    }

    @Test
    @DisplayName("가장 멀리 간 자동차를 우승자로 선정한다.")
    void findWinner_shouldReturnCarWithMaxPosition() {
        // given
        Car car1 = new Car("pobi");
        Car car2 = new Car("jun");
        Car car3 = new Car("honux");

        // when
        // 임의로 전진 값 조정
        car1.moveForward();
        car1.moveForward();
        car3.moveForward();

        List<Car> cars = List.of(car1, car2, car3);

        int maxPosition = cars.stream().mapToInt(Car::getPosition).max().orElse(0);
        List<Car> winner = cars.stream().filter(car -> car.getPosition() == maxPosition).toList();
        String winnerName = winner.stream().map(Car::getName).reduce((a, b) -> a + ", " + b).orElse("");

        // then
        assertThat(winnerName).isEqualTo("pobi");
    }

    @Test
    @DisplayName("자동차 이름에 쉼표가 없으면 예외가 발생한다.")
    void validateCarNameInputRegex_shouldThrowException_whenCommaMissing() {
        // given
        String input = "pobi jun honux";

        // then
        assertThatThrownBy(() -> Application.validateCarNameInputRegex(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("쉼표(,)");
    }

    @Test
    @DisplayName("자동차 이름이 공백일 경우 예외가 발생한다.")
    void validateCarNamesNotBlank_shouldThrowException_whenNameIsBlank() {
        // given
        List<String> names = List.of("pobi", " ", "honux");

        // then
        assertThatThrownBy(() -> names.forEach(Application::validateCarNamesNotBlank))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("자동차 이름이 없습니다");
    }
}
