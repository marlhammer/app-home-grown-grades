package HomeGrownGrades;

import javax.swing.*;
import java.awt.event.*;

/*
COPYRIGHTED 2002 BY STEPHEN MOURING JR

ALL RIGHTS RESERVED
*/

class PasswordManager
   {
   private boolean passwordStatus = false;
   private String password = null;
   private JMenu passwordMenu;
   private JMenuItem toggle;
   private JMenuItem change;

   PasswordManager (String password, boolean passwordStatus)
      {
      this.password = password;
      this.passwordStatus = passwordStatus;

      passwordMenu = new JMenu ("Password Controls");
         toggle = new JMenuItem ();
            if (passwordStatus == true) { toggle.setText ("Turn Password OFF"); }
            else { toggle.setText ("Turn Password ON"); }
            toggle.addActionListener
               (
               new ActionListener () { public void actionPerformed (ActionEvent event) { togglePassword (); } }
               );
            passwordMenu.add (toggle);
         change = new JMenuItem ("Change Password");
            if (passwordStatus == true) { change.setEnabled (true); }
            else { change.setEnabled (false); }
            change.addActionListener
               (
               new ActionListener () { public void actionPerformed (ActionEvent event) { changePassword (); } }
               );
            passwordMenu.add (change);
      }

   boolean getPasswordStatus () { return passwordStatus; }

   String getPassword () { return password; }

   JMenu getPasswordMenu () { return passwordMenu; }

   void startupPassword ()
      {
      if (passwordStatus == true)
         {
         if (checkPassword () == false) { System.exit (0); }
         }
      }

   private void togglePassword ()
      {
      if (password == null)
         {
         changePassword ();
         if (password == null) { return; }
         }
      if (passwordStatus == false)
         {
         passwordStatus = true;
         toggle.setText ("Turn Password OFF");
         change.setEnabled (true);
         UserInterface.showMessage ("Password has been turned ON");
         }
      else
         {
         if (checkPassword () == false) { return; }
         password = null;
         passwordStatus = false;
         toggle.setText ("Turn Password ON");
         change.setEnabled (false);
         UserInterface.showMessage ("Password has been turned OFF");
         }
      }

   private void changePassword ()
      {
      if (passwordStatus == true)
         {
         if (checkPassword () == false) { return; }
         }
      boolean cleared = false;
      while (cleared == false)
         {
         PasswordDialog PDNew = new PasswordDialog ("Please ENTER your NEW password");
         String newPassword = PDNew.getEntry ();
         if (newPassword == null) { return; }
         PasswordDialog PDConfirm = new PasswordDialog ("Please CONFIRM your NEW password");
         String confirmedPassword = PDConfirm.getEntry ();
         if (confirmedPassword == null) { return; }
         if (newPassword.equals (confirmedPassword) == true)
            {
            password = newPassword;
            cleared = true;
            if (passwordStatus == true) { UserInterface.showMessage ("Password has been changed"); }
            }
         else { UserInterface.showMessage ("New passwords do not match"); }
         }
      }

   private boolean checkPassword ()
      {
      boolean cleared = false;
      while (cleared == false)
         {
         PasswordDialog PD = new PasswordDialog ("Please enter your CURRENT password");
         String candidatePassword = PD.getEntry ();
         if (candidatePassword != null)
            {
            if (candidatePassword.equals (password) == true) { cleared = true; }
            else { UserInterface.showMessage ("The password you entered is INCORRECT"); }
            }
         else { return false; }
         }
      return true;
      }
   }