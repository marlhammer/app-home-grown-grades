package HomeGrownGrades;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

/*
COPYRIGHTED 2002 BY STEPHEN MOURING JR

ALL RIGHTS RESERVED
*/

class HelpDialog extends JDialog
   {
   HelpDialog (JFrame host)
      {
      super (host, Constants.title);
      Container ROOT = getContentPane ();
      ROOT.setLayout (new BorderLayout ());
      ROOT.setBackground (Color.black);
      setModal (false);
      setSize (500, 400);
      addWindowListener
         (
         new WindowAdapter ()
            {
            public void windowClosing (WindowEvent event) { dispose (); }
            public void windowClosed (WindowEvent event) {}
            }
         );

      CustomPanel helpDisplay = new CustomPanel ();
         helpDisplay.setLayout (new GridLayout (1, 1));
         JEditorPane help = new JEditorPane ();
            help.setBorder
               (
               new CompoundBorder
                  (
                  new BevelBorder (BevelBorder.LOWERED),
                  new LineBorder (Color.white, 7)
                  )
               );
            help.setContentType ("text/html");
            help.setEditable (false);
            try { help.setPage ("file:helpManual.html"); }
            catch (IOException e) { }
         JScrollPane helpPane = new JScrollPane (help, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            helpPane.setBorder (new EmptyBorder (10, 10, 10, 10));
            ColorManager.addMember (helpPane);
            helpDisplay.add (helpPane);
         ROOT.add (helpDisplay, BorderLayout.CENTER);

      CustomPanel controls = new CustomPanel ();
         JButton close = new JButton ("CLOSE");
            close.addActionListener
               (
               new ActionListener () { public void actionPerformed (ActionEvent event) { dispose (); } }
               );
            controls.add (close);
         ROOT.add (controls, BorderLayout.SOUTH);
      }
   }