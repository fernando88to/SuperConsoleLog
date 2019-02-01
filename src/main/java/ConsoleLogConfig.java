import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class ConsoleLogConfig {
    public String regexComent;
    public String regexUnComent;
    public String funcao;
    public String tipoArquivo;
    public String target;
    public String replacement;
    public String comment;

    public ConsoleLogConfig(String regexComent, String regexUnComent, String funcao, String tipoArquivo, String target, String replacement, String comment) {
        this.regexComent = regexComent;
        this.regexUnComent = regexUnComent;
        this.funcao = funcao;
        this.tipoArquivo = tipoArquivo;
        this.target = target;
        this.replacement = replacement;
        this.comment = comment;
    }

    public static List<ConsoleLogConfig> init() {
        List<ConsoleLogConfig> arrayList = new ArrayList<>();
        arrayList.add(
                new ConsoleLogConfig("^System.out.print.*",
                        "^//System.out.print.*",
                        "System.out.println();", ".java",
                        "S", "//S", "//"));
        arrayList.add(
                new ConsoleLogConfig("^console.log.*",
                        "^//console.log.*",
                        "console.log();",
                        ".js", "c", "//c",
                        "//"));
        arrayList.add(
                new ConsoleLogConfig("^println.*",
                        "^//println.*", "println()",
                        ".groovy", "p",
                        "//p", "//"));


        arrayList.add(
                new ConsoleLogConfig("^echo.*",
                        "^//echo.*", "echo();",
                        ".php", "e",
                        "//e", "//"));
        return arrayList;

    }

    public static ConsoleLogConfig findConfByNameFile(String fileName) {
        for (ConsoleLogConfig consoleLogConfig : ConsoleLogConfig.init()) {
            if (fileName != null & fileName.endsWith(consoleLogConfig.tipoArquivo)) {
                return consoleLogConfig;
            }

        }
        return null;

    }


}
