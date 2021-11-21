package me.mappy.ui;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;

public class OutputDevice extends JTextPane {
    private final SimpleAttributeSet attrs = new SimpleAttributeSet();

    public OutputDevice() {
        setEditable(false);
        setFont(new Font("monospaced", Font.PLAIN, 16));
        setBackground(new Color(57, 57, 57, 255));
        setForeground(new Color(179, 168, 168, 255));
        this.setEditorKit(new WrapEditorKit());
    }

    public OutputDevice setColor(Color c) {
        StyleConstants.setForeground(this.attrs, c);
        return this;
    }

    public OutputDevice setBold(boolean b) {
        StyleConstants.setBold(this.attrs, b);
        return this;
    }

    @Override
    public boolean getScrollableTracksViewportWidth() {
        return true;
    }

    public void write(String str) {
        try {
            this.getDocument().insertString(this.getDocument().getLength(), str + "\n", null);
            this.setCaretPosition(this.getDocument().getLength());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // https://stackoverflow.com/a/13375811
    @SuppressWarnings("serial")
    class WrapEditorKit extends StyledEditorKit {
        ViewFactory defaultFactory = new WrapColumnFactory();

        public ViewFactory getViewFactory() {
            return defaultFactory;
        }

    }

    class WrapColumnFactory implements ViewFactory {
        public View create(Element elem) {
            String kind = elem.getName();
            if (kind != null) {
                switch (kind) {
                    case AbstractDocument.ContentElementName:
                        return new WrapLabelView(elem);
                    case AbstractDocument.ParagraphElementName:
                        return new ParagraphView(elem);
                    case AbstractDocument.SectionElementName:
                        return new BoxView(elem, View.Y_AXIS);
                    case StyleConstants.ComponentElementName:
                        return new ComponentView(elem);
                    case StyleConstants.IconElementName:
                        return new IconView(elem);
                }
            }

            // default to text display
            return new LabelView(elem);
        }
    }

    class WrapLabelView extends LabelView {
        public WrapLabelView(Element elem) {
            super(elem);
        }

        public float getMinimumSpan(int axis) {
            switch (axis) {
                case View.X_AXIS:
                    return 0;
                case View.Y_AXIS:
                    return super.getMinimumSpan(axis);
                default:
                    throw new IllegalArgumentException("Invalid axis: " + axis);
            }
        }

    }
}
