package HomeGrownGrades;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/*
COPYRIGHTED 2002 BY STEPHEN MOURING JR

ALL RIGHTS RESERVED
*/

class PasswordDialog extends JDialog
   {
   private JLabel message;
   private JPasswordField passwordEntry;
   private String entry = null;

   PasswordDialog (String text)
      {
      super ();
      Container ROOT = getContentPane ();
      ROOT.setLayout (new BorderLayout ());
      ROOT.setBackground (Color.black);
      setModal (true);
      setLocationRelativeTo (null);
      setTitle (Constants.title);
      setSize (300, 125);
      setResizable (false);
      addWindowListener
         (
         new WindowAdapter ()
            {
            public void windowClosing (WindowEvent event) { dispose (); }
            public void windowClosed (WindowEvent event) {}
            }
         );

      CustomPanel messageHolder = new CustomPanel ();
         message = new JLabel (text);
            message.setForeground (Color.black);
            messageHolder.add (message);
         ROOT.add (messageHolder, BorderLayout.NORTH);

      CustomPanel controls = new CustomPanel ();
         passwordEntry = new JPasswordField (10);
            passwordEntry.setBackground (Color.white);
            passwordEntry.setForeground (Color.black);
            controls.add (passwordEntry);
         JButton ok = new JButton ("OK");
            ok.setMnemonic ('O');
            ok.addActionListener
               (
               new ActionListener ()
                  {
                  public void actionPerformed (ActionEvent event)
                     {
                     char [] temp = passwordEntry.getPassword ();
                     entry = new String (temp);
                     if (entry == null) { dispose (); }
                     if (entry.equals ("") == true)
                        {
                        entry = null;
                        message.setText ("That is not a valid password");
                        }
                     else { dispose (); }
                     }
                  }
               );
            controls.add (ok);
         JButton cancel = new JButton ("CANCEL");
            cancel.addActionListener
               (
               new ActionListener () { public void actionPerformed (ActionEvent event) { dispose (); } }
               );
            controls.add (cancel);
         ROOT.add (controls, BorderLayout.CENTER);

      show ();
      }

   String getEntry () { return entry; }
   }