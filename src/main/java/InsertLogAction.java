import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.*;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;

public class InsertLogAction extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        //Get all the required data from data keys
        final Editor editor = e.getRequiredData(CommonDataKeys.EDITOR);
        if (editor != null) {
            final Project project = e.getProject();

            final SelectionModel selectionModel = editor.getSelectionModel();


            //Access document, caret, and selection
            final Document document = editor.getDocument();
            CaretModel caretModel = editor.getCaretModel();
            LogicalPosition logicalPosition = caretModel.getLogicalPosition();

            VirtualFile currentFile = e.getData(PlatformDataKeys.VIRTUAL_FILE);
            ConsoleLogConfig consoleLogConfig = ConsoleLogConfig.findConfByNameFile(currentFile.getName());


            final int start = selectionModel.getSelectionStart();
            final int end = selectionModel.getSelectionEnd();




            String stringSelected = document.getText(new TextRange(start, end));

            if(stringSelected!= null && stringSelected!=""){
                WriteCommandAction.runWriteCommandAction(project, () -> {


                            String stringLinhaAtual = getContentOfString(document, selectionModel.getSelectionStartPosition().line);
                            int quantOfSpace = countOfSpace(stringLinhaAtual);
                            int offsetFinalDaLinhaSelecionada = document.getLineEndOffset(logicalPosition.line);
                            String log = consoleLogConfig.funcao;
                            log = log.replace("()", "(" + stringSelected + ")");
                            log = addSpaceInString(log, quantOfSpace);
                            log = "\n" +log;
                            document.insertString(offsetFinalDaLinhaSelecionada, log);


                        }
                );

                //coloca o cursor no final da linha do log
                int lineEndOffset = document.getLineEndOffset(selectionModel.getSelectionEndPosition().line + 1);
                caretModel.moveToOffset(lineEndOffset);
            }




        }


    }



    private String getContentOfString(Document document, int linhaAtual) {

        int offsetInicialDaLinha = document.getLineStartOffset(linhaAtual);
        int offsetFinalDaLinha = document.getLineEndOffset(linhaAtual);


        String stringLinhaSelecao = document.getText(new TextRange(offsetInicialDaLinha, offsetFinalDaLinha));
        return stringLinhaSelecao;
    }

    private int countOfSpace(String string) {
        int quant = 0;
        for (char ch : string.toCharArray()) {
            if (ch == ' ') {
                quant++;
            } else {
                break;
            }

        }

        return quant;


    }

    private String addSpaceInString(String string, int quant) {

        for (int i = 1; i <= quant; i++) {
            string = " " + string;

        }

        return string;

    }


}