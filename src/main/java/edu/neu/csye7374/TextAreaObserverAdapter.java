package edu.neu.csye7374;

import javax.swing.JTextArea;

/**
 * Adapter that allows a Swing JTextArea to act as a GameObserver.
 * This demonstrates the Adapter pattern on top of the existing Observer pattern.
 */
public class TextAreaObserverAdapter implements GameObserver {

    private final JTextArea textArea;

    public TextAreaObserverAdapter(JTextArea textArea) {
        this.textArea = textArea;
    }

    @Override
    public void onEvent(String message) {
        // Strip ANSI color codes so GUI text looks clean
        String clean = message.replaceAll("\\u001B\\[[;\\d]*m", "");
        textArea.append(clean + "\n");
        textArea.setCaretPosition(textArea.getDocument().getLength());
    }
}
