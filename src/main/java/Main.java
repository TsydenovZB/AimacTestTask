import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

public class Main {
    private String operationType;
    private String input;
    private String output;

    static String errorMessage;
    static boolean isError = false;

    public Main(String[] args) {
        this.operationType = args[0];
        this.input = args[1];
        this.output = args[2];
    }

    public static void main(String[] args) throws IOException {
        if (args.length != 3) {
            isError = true;
            errorMessage = "Количество аргументов должно быть равна 3";
        } else {
            Main main = new Main(args);
            JsonWorker jsonWorker = new JsonWorker();
            String outputJson = jsonWorker.jsonReader(main.input, main.operationType);

            try (PrintWriter printWriter = new PrintWriter(main.output)) {
                printWriter.print(outputJson);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }

        }
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }
}
