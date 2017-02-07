/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.view;

import java.awt.Color;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.DocumentFilter;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.Utilities;

import server.model.Range;

/**
 *
 * @author al1as
 */
public class StyledDocumentWithLocks extends DefaultStyledDocument
{
    SimpleAttributeSet lockAttributeSet = new SimpleAttributeSet();
    Range lockedSymbolRange = new Range(0, 0);
    Range lockedLinesRange = new Range(0, 0);
    boolean isActivated = false;
    
    public StyledDocumentWithLocks() 
    {
        StyleConstants.setForeground(lockAttributeSet, Color.GRAY);
    }
    
    public void setLockedRange(Range lockedSymbolRange) 
    {
        this.lockedSymbolRange = lockedSymbolRange;
    }

    @Override
    public void insertString(final int offset, final String string, AttributeSet a) throws BadLocationException 
    {
        if (offset >= lockedSymbolRange.getStart() && offset + string.length() <= lockedSymbolRange.getEnd())
            super.insertString(offset, string, null);
    }
    
    public void remove(final int offset, final int length) throws BadLocationException 
    {
        if (offset >= lockedSymbolRange.getStart() &&  offset + length <= lockedSymbolRange.getEnd()) 
                super.remove(offset, length);
    }

    @Override
    public void replace(final int offset, final int length, final String text, AttributeSet a) throws BadLocationException 
    {
        if (offset >= lockedSymbolRange.getStart() && offset - length + text.length() <= lockedSymbolRange.getEnd() &&
                offset + length <= lockedSymbolRange.getEnd())
            super.replace(offset, length, text, null);
    }
    
    // loads document with styles
    public void loadFileContent(ArrayList<TextFragment> fragments) throws BadLocationException 
    {
        int start = 0;
        int end = 0;
        super.remove(0, this.getLength());
        for(int i = 0; i < fragments.size(); i++) {
            TextFragment curFragment = fragments.get(i);
            if(curFragment.isLocked) {
                end = start + curFragment.text.length() - 1;
                super.insertString(start, curFragment.text, lockAttributeSet);
                start = end + 1;
            } else {
                end = start + curFragment.text.length() - 1;
                super.insertString(start, curFragment.text, null);
                start = end + 1; 
            }
        }
    }
}