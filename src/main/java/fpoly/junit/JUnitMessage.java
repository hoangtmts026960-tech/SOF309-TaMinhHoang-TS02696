package fpoly.junit;

public class JUnitMessage {
    private String message;

    public JUnitMessage(String message) {
        this.message = message;
    }

    public void printMessage() {
        System.out.println(message);
        int divide = 1 / 0; // Gây ra ArithmeticException theo yêu cầu test case
    }

    public String printHiMessage() {
        String hiMessage = "Hi!" + message;
        System.out.println(hiMessage);
        return hiMessage;
    }
}
