package run;

import controller.MainController;

/**
 * 프로그램 진입점
 * MainController를 생성하고 게임을 시작한다
 */
public class Application {

    public static void main(String[] args) {
        MainController mainController = new MainController();
        mainController.start();
    }
}