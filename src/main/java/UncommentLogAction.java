import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.vfs.VirtualFile;

public class UncommentLogAction extends AnAction {
    @Override
    public void actionPerformed(AnActionEvent e) {
        //Get all the required data from data keys
        final Editor editor = e.getRequiredData(CommonDataKeys.EDITOR);
        if (editor!=null) {
            final Project project = e.getProject();
            //Access document, caret, and selection
            final Document document = editor.getDocument();

            VirtualFile currentFile = e.getData(PlatformDataKeys.VIRTUAL_FILE);

            ConsoleLogConfig consoleLogConfig = ConsoleLogConfig.findConfByNameFile(currentFile.getName());


            if (consoleLogConfig != null) {
                //percore linha a linha2
                for (int i = 0; i < document.getLineCount(); i++) {
                    //pega o começo da linha
                    int offsetFinalDaLinha = document.getLineEndOffset(i);
                    //pega o final da linha
                    final int offsetFinalDaLinhaANterior = i == 0 ? 0 : document.getLineEndOffset(i - 1);
                    //isola o texto da linha e tira os espaços no começo e no final
                    String stringDeTeste = "";
                    stringDeTeste = document.getText(new TextRange(offsetFinalDaLinhaANterior, offsetFinalDaLinha));
                    stringDeTeste = stringDeTeste.trim();
                    stringDeTeste = stringDeTeste.replaceAll(" ", "");
                    if (stringDeTeste.matches(consoleLogConfig.regexUnComent)) {
                        WriteCommandAction.runWriteCommandAction(project, () -> {
                                    String stringFinal = document.getText(new TextRange(offsetFinalDaLinhaANterior, offsetFinalDaLinha));
                                    stringFinal = stringFinal.replace(consoleLogConfig.comment, "");
                                    document.replaceString(offsetFinalDaLinhaANterior, offsetFinalDaLinha, stringFinal);
                                }

                        );
                    }




                }
            }
        }


    }
}
